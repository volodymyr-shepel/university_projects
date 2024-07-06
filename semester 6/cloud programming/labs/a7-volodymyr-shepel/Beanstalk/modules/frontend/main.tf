# Define local values for frontend application environment variables
locals {
  frontend_app_env = {
    IP_ADDRESS = var.backend_url
  }
}

# Define a random_pet resource to generate a unique identifier for the S3 bucket name
resource "random_pet" "ebs_bucket_name" {}

# Define a data source to read the contents of Dockerrun.aws.json file for the frontend
data "template_file" "frontend_ebs_config" {
  template = file("${path.module}/Dockerrun.aws.json")
}

# Define a data source to read an existing IAM instance profile
data "aws_iam_instance_profile" "existing_profile" {
  name = var.existing_profile_name
}

# Define a data source to read an existing IAM role
data "aws_iam_role" "existing_role" {
  name = var.existing_role_name
}

# Define a local file resource to create Dockerrun.aws.json file for the frontend
resource "local_file" "frontend_ebs_config" {
  content  = data.template_file.frontend_ebs_config.rendered
  filename = "${path.module}/Dockerrun.aws.json"
}

# Define an S3 bucket resource for the frontend
resource "aws_s3_bucket" "frontend_ebs" {
  bucket = "${var.frontend_app_name}-${random_pet.ebs_bucket_name.id}"
}

# Define an S3 object resource to upload Dockerrun.aws.json to the S3 bucket for the frontend
resource "aws_s3_object" "frontend_ebs_deployment" {
  depends_on = [local_file.frontend_ebs_config]
  bucket     = aws_s3_bucket.frontend_ebs.id
  key        = "Dockerrun.aws.json"
  source     = "${path.module}/Dockerrun.aws.json"
  lifecycle {
    replace_triggered_by = [ local_file.frontend_ebs_config ]
  }
}

# Define an Elastic Beanstalk application resource for the frontend
resource "aws_elastic_beanstalk_application" "frontend_app" {
  name = var.frontend_app_name
}

# Define an Elastic Beanstalk application version resource for the frontend
resource "aws_elastic_beanstalk_application_version" "frontend_app_version" {
  name        = "${var.frontend_app_name}-version"
  application = aws_elastic_beanstalk_application.frontend_app.name
  bucket      = aws_s3_bucket.frontend_ebs.id
  key         = aws_s3_object.frontend_ebs_deployment.id
}

# Define an Elastic Beanstalk environment resource for the frontend
resource "aws_elastic_beanstalk_environment" "frontend" {
  name                = "${var.frontend_app_name}-env"
  application         = aws_elastic_beanstalk_application.frontend_app.name
  version_label       = aws_elastic_beanstalk_application_version.frontend_app_version.name
  solution_stack_name = var.solution_stack_name
  tier                = "WebServer"
  wait_for_ready_timeout  = "15m"

  # Set the port for the frontend container
  setting {
    name      = "InstancePort"
    namespace = "aws:cloudformation:template:parameter"
    value     = var.frontend_container_port
  }

  # Set the IAM instance profile for the frontend environment
  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name      = "IamInstanceProfile"
    value     = data.aws_iam_instance_profile.existing_profile.name
  }

  # Set the IAM role for the frontend environment
  setting {
    namespace = "aws:elasticbeanstalk:environment"
    name      = "ServiceRole"
    value     = data.aws_iam_role.existing_role.arn
  }

  # Set dynamic environment variables for the frontend application
  dynamic "setting" {
    for_each = local.frontend_app_env
    content {
      namespace = "aws:elasticbeanstalk:application:environment"
      name      = setting.key
      value     = setting.value
    }
  }
}
