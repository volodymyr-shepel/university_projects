variable "back_app_name" {
  type = string
}

variable "backend_container_port" {
  type = number
}

variable "vpc_id" {
  type = string
}

variable "subnet" {
  type = string
}

variable "backend_cname_prefix" {
  type = string

}

variable "backend_process_game_results_url" {
  type = string
}

variable "amazon_region" {
  type = string
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
variable "aws_s3_bucket_name" {
  type = string
}
