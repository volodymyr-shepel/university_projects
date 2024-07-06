variable "backend_url" {
  description = "URL of the backend service."
  type        = string
}

variable "frontend_app_name" {
  description = "The name of the Elastic Beanstalk application for the frontend service."
  type        = string
  default     = "a7-frontend"
}

variable "frontend_container_port" {
  description = "The port on which the frontend container listens for incoming traffic."
  type        = number
  default     = 8081
}

variable "existing_role_name" {
  type        = string
  default     = "LabRole"
  description = "The name of an existing IAM role to assign to the EC2 instances in the Elastic Beanstalk environment."
}

variable "existing_profile_name" {
  type        = string
  default     = "LabInstanceProfile"
  description = "The name of an existing IAM instance profile to assign to the EC2 instances in the Elastic Beanstalk environment."
}

variable "solution_stack_name" {
  type        = string
  default     = "64bit Amazon Linux 2 v3.8.1 running Docker"
  description = "The name of the Elastic Beanstalk solution stack to use for the environment."
}