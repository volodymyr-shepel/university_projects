variable "function_name" {
  description = "The name of the Lambda function."
  type        = string
}

variable "sns_topic_arn" {
  description = "The ARN of the SNS topic."
  type        = string
}

variable "existing_role_name" {
  description = "The name of the existing IAM role to be used by the Lambda function."
  type        = string
}

variable "rating_table_name" {
  type = string
}

variable "game_history_table_name" {
  type = string
}