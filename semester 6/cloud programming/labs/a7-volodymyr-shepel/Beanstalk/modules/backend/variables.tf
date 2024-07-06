variable "backend_app_name" {
  type        = string
  default     = "a7-backend"
  description = "The name of the Elastic Beanstalk application for the backend service."
}

variable "backend_container_port" {
  type        = number
  default     = 8080
  description = "The port on which the backend container listens for incoming traffic."
}

variable "solution_stack_name" {
  type        = string
  default     = "64bit Amazon Linux 2 v3.8.1 running Docker"
  description = "The name of the Elastic Beanstalk solution stack to use for the environment."
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
