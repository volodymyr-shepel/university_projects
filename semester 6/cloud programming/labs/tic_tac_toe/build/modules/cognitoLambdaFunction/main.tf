data "aws_iam_role" "existing_role" {
  name = var.existing_role_name
}

data "archive_file" "lambda" {
  type        = "zip"
  source_file = "${path.module}/subscribeToSnsTopic.py"
  output_path = "${path.module}/subscribeToSnsTopic.zip"
}

resource "aws_lambda_function" "lambda" {
  filename         = "${path.module}/subscribeToSnsTopic.zip"
  function_name    = var.function_name
  role             = data.aws_iam_role.existing_role.arn
  handler          = "subscribeToSnsTopic.lambda_handler"
  runtime          = "python3.12"
  source_code_hash = data.archive_file.lambda.output_base64sha256

  environment {
    variables = {
      SNS_TOPIC_ARN = var.sns_topic_arn
    }
  }
}