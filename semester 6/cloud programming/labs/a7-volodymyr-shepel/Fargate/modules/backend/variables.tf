variable "cl_id" {
    type = string
}

variable "existing_role_name" {
    description = "Name of the existing IAM role"
    type        = string
    default = "LabRole"
}

variable "subnet_id" {
    type = string
    default = "subnet-092234cba438c0a81"
}

variable "backend_port" {
    type = number
    default = 8080
}

variable "back_img_uri" {
    type = string
    default = "637423510497.dkr.ecr.us-east-1.amazonaws.com/tic-tac-toe-backend:v1"
}