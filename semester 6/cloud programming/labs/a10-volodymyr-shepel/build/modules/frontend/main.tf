locals {
  frontend_app_env = {
    BACKEND_IP               = var.back_url
    CALLBACK_IP              = "${var.cname_prefix}.us-east-1.elasticbeanstalk.com"
    AWS_COGNITO_DOMAIN       = var.cognito_user_pool_domain
    AWS_COGNITO_LOGOUT_URI   = "https://${var.cname_prefix}.us-east-1.elasticbeanstalk.com"
    AMAZON_REGION            = var.amazon_region
    AWS_S3_BUCKET            = var.aws_s3_bucket
    USER_POOL_ID             = var.cognito_user_pool_id
    COGNITO_CLIENT_ID        = var.cognito_user_pool_client_id
    COGNITO_CLIENT_SECRET    = var.cognito_user_pool_client_secret
    CLIENT_NAME              = var.cognito_user_pool_client_name
  }
  frontend_common_name = var.front_app_name
}

resource "aws_acm_certificate" "ssl_certificate" {
  private_key     = file("${path.module}/certs/privatekey.pem")
  certificate_body = file("${path.module}/certs/public.crt")
}

resource "random_pet" "ebs_bucket_name" {}

data "template_file" "f_ebs_config" {
  template = file("${path.module}/Dockerrun.aws.json")
}

resource "local_file" "f_ebs_config" {
  content  = data.template_file.f_ebs_config.rendered
  filename = "${path.module}/Dockerrun.aws.json"
}

resource "aws_s3_bucket" "f_ebs" {
  bucket = "${local.frontend_common_name}-${random_pet.ebs_bucket_name.id}"
}

resource "aws_s3_object" "f_ebs_deployment" {
  depends_on = [local_file.f_ebs_config]
  bucket     = aws_s3_bucket.f_ebs.id
  key        = "Dockerrun.aws.json"
  source     = "${path.module}/Dockerrun.aws.json"
  lifecycle {
    replace_triggered_by = [ local_file.f_ebs_config ]
  }
}

data "aws_iam_instance_profile" "existing_instance_profile" {
  name = "LabInstanceProfile"
}

data "aws_iam_role" "existing_instance_role" {
  name = "LabRole"
}

resource "aws_elastic_beanstalk_application" "frontend_app" {
  name = local.frontend_common_name
}

resource "aws_elastic_beanstalk_application_version" "frontend_app_version" {
  name        = "${local.frontend_common_name}-version"
  application = aws_elastic_beanstalk_application.frontend_app.name
  bucket      = aws_s3_bucket.f_ebs.id
  key         = aws_s3_object.f_ebs_deployment.id
}

resource "aws_elastic_beanstalk_environment" "frontend" {
  name                = "${local.frontend_common_name}-env"
  application         = aws_elastic_beanstalk_application.frontend_app.name
  version_label       = aws_elastic_beanstalk_application_version.frontend_app_version.name
  solution_stack_name = "64bit Amazon Linux 2 v3.8.1 running Docker"
  cname_prefix        = var.cname_prefix
  tier                = "WebServer"
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

#   setting {
#     namespace = "aws:ec2:vpc"
#     name = "VPCId"
#     value = var.vpc_id
#   }

#   setting {
#     namespace = "aws:ec2:vpc"
#     name = "Subnets"
#     value = var.subnet
# #   }

#   setting {
#     namespace = "aws:ec2:vpc"
#     name = "AssociatePublicIpAddress"
#     value = "true"
#   }
  
#   setting {
#     namespace = "aws:elasticbeanstalk:environment"
#     name = "EnvironmentType"
#     value = "SingleInstance"
#   }

  setting {
    name      = "InstancePort"
    namespace = "aws:elb:listener:443"
    value     = var.frontend_container_port
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

#   setting {
#     namespace = "aws:autoscaling:launchconfiguration"
#     name = "SecurityGroups"
#     value = var.sec_group
#   }

  dynamic "setting" {
    for_each = local.frontend_app_env
    content {
      namespace = "aws:elasticbeanstalk:application:environment"
      name      = setting.key
      value     = setting.value
    }
  }
}

output "frontend_ebs_url" {
  value = aws_elastic_beanstalk_environment.frontend.cname
}
