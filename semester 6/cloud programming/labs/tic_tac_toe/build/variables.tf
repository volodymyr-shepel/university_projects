variable "client_name" {
  type = string
}

variable "domain_name" {
  type = string
}

variable "user_pool_name" {
  type = string
}

variable "amazon_region" {
  type = string
}
variable "existing_role_name" {
  type = string
}

variable "subscribe_to_sns_function_name" {
  type = string
}

variable "process_game_results_function_name" {
  type = string
}
variable "sns_topic_name" {
  type = string
}

variable "s3_bucket_name" {
  type = string
}

variable "rating_table_name" {
  type = string
}
variable "game_history_table_name" {
  type = string
}

variable "api_name" {
  description = "The name of the API Gateway"
  type        = string
}

variable "api_description" {
  description = "The description of the API Gateway"
  type        = string
}

variable "stage_name" {
  description = "The name of the deployment stage"
  type        = string
}

variable "resource_path" {
  description = "The resource path for the API Gateway"
  type        = string
}

variable "http_method" {
  description = "The HTTP method for the API Gateway method"
  type        = string
  default     = "POST"
}

variable "aws_secret_key" {
  type = string
}

variable "aws_access_key" {
  type = string
}

variable "aws_session_token" {
  type = string
}

variable "frontend_cname_prefix" {
  type = string
}
variable "backend_cname_prefix" {
  type = string
}
variable "back_app_name" {
  type = string
}

variable "front_app_name" {
  type = string
}

variable "backend_container_port" {
  type = string
}
