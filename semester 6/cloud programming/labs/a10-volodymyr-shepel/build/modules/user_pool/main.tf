
resource "aws_cognito_user_pool" "user_pool" {
  name = var.user_pool_name

  alias_attributes = ["preferred_username", "email"]

  mfa_configuration = "OFF"

  password_policy {
    minimum_length    = 8
    require_lowercase = true
    require_numbers   = true
    require_symbols   = true
    require_uppercase = true
    temporary_password_validity_days = 7
  }

  account_recovery_setting {
    recovery_mechanism {
      name     = "verified_email"
      priority = 1
    }
  }

  email_configuration {
    email_sending_account = "COGNITO_DEFAULT"
  }

  verification_message_template {
    default_email_option = "CONFIRM_WITH_CODE"
    email_message = <<EOF
Your verification code is {####}
EOF
    email_subject = "Verify your email address"
  }

  admin_create_user_config {
    allow_admin_create_user_only = false
  }

  user_attribute_update_settings {
    attributes_require_verification_before_update = ["email"]
  }

  schema {
    attribute_data_type = "String"
    name                = "email"
    required            = true
    mutable             = true
  }

  auto_verified_attributes = ["email"]

  lambda_config {
    post_confirmation = var.lambda_function_post_confirmation
  }
}

resource "aws_cognito_user_pool_domain" "domain" {
  domain  = var.domain_name
  user_pool_id = aws_cognito_user_pool.user_pool.id
}

resource "aws_cognito_user_pool_client" "client" {
  name         = var.client_name
  user_pool_id = aws_cognito_user_pool.user_pool.id
  generate_secret = true

  allowed_oauth_flows_user_pool_client = true
  callback_urls = ["${var.callback_url}/login/oauth2/code/cognito"]
  logout_urls = ["${var.callback_url}"]

  prevent_user_existence_errors = "ENABLED"

  allowed_oauth_flows = ["code"]
  allowed_oauth_scopes = ["phone", "email", "openid", "profile", "aws.cognito.signin.user.admin"]

  enable_token_revocation = true

  supported_identity_providers = ["COGNITO"]

}





