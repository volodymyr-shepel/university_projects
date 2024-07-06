locals {
  backend_app_env = {
    PROCESS_GAME_RESULTS_URL = var.backend_process_game_results_url
    AMAZON_REGION            = var.amazon_region           
    AWS_ACCESS_KEY           = var.aws_access_key          
    AWS_SECRET_KEY           = var.aws_secret_key               
    AWS_SESSION_TOKEN        = var.aws_session_token            
    AWS_S3_BUCKET            = var.aws_s3_bucket_name          
  }
  common_name = var.back_app_name
}

resource "aws_acm_certificate" "ssl_certificate" {
  private_key     = file("${path.module}/certs/privatekey.pem")
  certificate_body = file("${path.module}/certs/public.crt")
}

resource "random_pet" "ebs_bucket_name" {}

resource "aws_s3_bucket" "ebs" {
  bucket = "${local.common_name}-${random_pet.ebs_bucket_name.id}"
}

data "template_file" "ebs_config" {
  template = file("${path.module}/Dockerrun.aws.json")
}

resource "local_file" "ebs_config" {
  content  = data.template_file.ebs_config.rendered
  filename = "${path.module}/Dockerrun.aws.json"
}

resource "aws_s3_object" "ebs_deployment" {
  depends_on = [local_file.ebs_config]
  bucket     = aws_s3_bucket.ebs.id
  key        = "Dockerrun.aws.json"
  source     = "${path.module}/Dockerrun.aws.json"
  lifecycle {
    replace_triggered_by = [ local_file.ebs_config ]
  }
}

data "aws_iam_instance_profile" "existing_instance_profile" {
  name = "LabInstanceProfile"
}

data "aws_iam_role" "existing_instance_role" {
  name = "LabRole"
}

resource "aws_elastic_beanstalk_application" "back_app" {
  name = local.common_name
}

resource "aws_elastic_beanstalk_application_version" "app_version" {
  name        = "${local.common_name}-version"
  application = aws_elastic_beanstalk_application.back_app.name
  bucket      = aws_s3_bucket.ebs.id
  key         = aws_s3_object.ebs_deployment.id
}

resource "aws_elastic_beanstalk_environment" "backend" {
  name                = "${local.common_name}-env"
  application         = aws_elastic_beanstalk_application.back_app.name
  version_label       = aws_elastic_beanstalk_application_version.app_version.name
  solution_stack_name = "64bit Amazon Linux 2 v3.8.1 running Docker"
  tier                = "WebServer"
  cname_prefix        = var.backend_cname_prefix
  wait_for_ready_timeout = "10m"

  setting {
    namespace = "aws:elasticbeanstalk:environment"
    name      = "LoadBalancerType"
    value     = "application"
  }

  setting {
    namespace = "aws:elasticbeanstalk:environment:process:default"
    name      = "Protocol"
    value     = "HTTP"
  }

  setting {
    namespace = "aws:elbv2:listener:443"
    name      = "Protocol"
    value     = "HTTPS"
  }

  setting {
    namespace = "aws:elbv2:listener:443"
    name      = "SSLCertificateArns"
    value     = aws_acm_certificate.ssl_certificate.arn
  }

  setting {
    name      = "InstancePort"
    namespace = "aws:elb:listener:443"
    value     = var.backend_container_port
  }

  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name      = "IamInstanceProfile"
    value     = data.aws_iam_instance_profile.existing_instance_profile.name
  }

  setting {
    namespace = "aws:elasticbeanstalk:environment"
    name      = "ServiceRole"
    value     = data.aws_iam_role.existing_instance_role.arn
  }

  setting {
    namespace = "aws:ec2:instances"
    name = "InstanceTypes"
    value = "t2.micro"
  }

    dynamic "setting" {
    for_each = local.backend_app_env
    content {
      namespace = "aws:elasticbeanstalk:application:environment"
      name      = setting.key
      value     = setting.value
    }
  }
}
