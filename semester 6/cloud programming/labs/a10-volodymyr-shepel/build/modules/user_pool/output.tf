output "client_id" {
  value = aws_cognito_user_pool_client.client.id
}

output "client_secret" {
  value = aws_cognito_user_pool_client.client.client_secret
  sensitive = true
}

output "user_pool_id" {
  value = aws_cognito_user_pool.user_pool.id
}

output "user_pool_arn"{
  value = aws_cognito_user_pool.user_pool.arn
}

output "user_pool_domain" {
  value = aws_cognito_user_pool_domain.domain.domain
}


