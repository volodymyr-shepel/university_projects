data "aws_iam_role" "existing_role" {
  name = var.existing_role_name
}

data "archive_file" "lambda" {
  type        = "zip"
  source_file = "${path.module}/processGameResults.py"
  output_path = "${path.module}/processGameResults.zip"
}

resource "aws_lambda_function" "lambda" {
  filename         = "${path.module}/processGameResults.zip"
  function_name    = var.function_name
  role             = data.aws_iam_role.existing_role.arn
  handler          = "processGameResults.lambda_handler"
  runtime          = "python3.12"
  source_code_hash = data.archive_file.lambda.output_base64sha256

  environment {
    variables = {
      SNS_TOPIC_ARN = var.sns_topic_arn,
      RATING_TABLE = var.rating_table_name,
      GAME_HISTORY_TABLE = var.game_history_table_name
    }
  }
}
