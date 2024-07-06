# Define a random_pet resource to generate a unique identifier for the S3 bucket name
resource "random_pet" "ebs_bucket_name" {}

# Define a data source to read an existing IAM instance profile
data "aws_iam_instance_profile" "existing_profile" {
  name = var.existing_profile_name
}

# Define a data source to read an existing IAM role
data "aws_iam_role" "existing_role" {
  name = var.existing_role_name
}

# Define a data source to read the contents of Dockerrun.aws.json file
data "template_file" "ebs_config" {
  template = file("${path.module}/Dockerrun.aws.json")
}

# Define an S3 bucket resource
resource "aws_s3_bucket" "ebs" {
  # Concatenate the backend application name with the randomly generated bucket name
  bucket = "${var.backend_app_name}-${random_pet.ebs_bucket_name.id}"
}

# Define a local file resource to create Dockerrun.aws.json file
resource "local_file" "ebs_config" {
  content  = data.template_file.ebs_config.rendered
  filename = "${path.module}/Dockerrun.aws.json"
}

# Define an S3 object resource to upload Dockerrun.aws.json to the S3 bucket
resource "aws_s3_object" "ebs_deployment" {
  depends_on = [local_file.ebs_config]
  bucket     = aws_s3_bucket.ebs.id
  key        = "Dockerrun.aws.json"
  source     = "${path.module}/Dockerrun.aws.json"
  lifecycle {
    # Trigger replacement of the S3 object when the local file changes
    replace_triggered_by = [ local_file.ebs_config ]
  }
}

# Define an Elastic Beanstalk application resource
resource "aws_elastic_beanstalk_application" "back_app" {
  name = var.backend_app_name
}

# Define an Elastic Beanstalk application version resource
resource "aws_elastic_beanstalk_application_version" "app_version" {
  name        = "${var.backend_app_name}-version"
  application = aws_elastic_beanstalk_application.back_app.name
  bucket      = aws_s3_bucket.ebs.id
  key         = aws_s3_object.ebs_deployment.id
}

# Define an Elastic Beanstalk environment resource
resource "aws_elastic_beanstalk_environment" "backend" {
  name                = "${var.backend_app_name}-env"
  application         = aws_elastic_beanstalk_application.back_app.name
  version_label       = aws_elastic_beanstalk_application_version.app_version.name
  solution_stack_name = var.solution_stack_name
  tier                = "WebServer"
  wait_for_ready_timeout  = "15m"

  setting {
    name      = "InstancePort"
    namespace = "aws:cloudformation:template:parameter"
    value     = var.backend_container_port
  }

  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name      = "IamInstanceProfile"
    value     = data.aws_iam_instance_profile.existing_profile.name
  }

  setting {
    namespace = "aws:elasticbeanstalk:environment"
    name      = "ServiceRole"
    value     = data.aws_iam_role.existing_role.arn
  }
}
