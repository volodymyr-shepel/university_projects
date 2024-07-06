variable "callback_url" {
    type = string

}
variable "user_pool_name" {
    type = string
}

# name of the application
variable "client_name" {
    type = string
}
variable "domain_name"{
    type = string
}

variable "lambda_function_post_confirmation"{
    type = string
}