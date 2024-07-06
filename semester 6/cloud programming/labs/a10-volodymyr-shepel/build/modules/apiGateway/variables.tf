variable "api_name" {
  description = "The name of the API Gateway"
  type        = string
}

variable "api_description" {
  description = "The description of the API Gateway"
  type        = string
}

variable "lambda_function_invoke_arn" {
  description = "The ARN of the Lambda function to integrate with"
  type        = string
}

variable "function_name"{
  type = string
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
