variable "back_url" {
  description = "URL of the backend service"
  type        = string
}

variable "front_app_name" {
  type    = string
}

variable "frontend_container_port" {
  type    = number
  default = 8081
}

variable "cognito_user_pool_id" {
  description = "Cognito User Pool ID"
  type        = string
}

variable "cognito_user_pool_client_id" {
  description = "Cognito User Pool Client ID"
  type        = string
}

variable "cognito_user_pool_client_secret" {
  description = "Cognito User Pool Client Secret"
  type        = string
}


variable "cognito_user_pool_client_name" {
  description = "Cognito User Pool Client Name"
  type        = string
}

variable "cognito_user_pool_domain" {
  description = "Cognito User Pool Domain"
  type        = string
}

variable "vpc_id" {
  type = string
}

variable "subnet" {
  type = string
}

variable "sec_group" {
  type = string
}

variable "amazon_region" {
  type = string
}

variable "cname_prefix" {
  type = string
}


variable "aws_s3_bucket" {
  type = string
}

