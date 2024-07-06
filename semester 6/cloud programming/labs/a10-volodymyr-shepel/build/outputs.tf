#output "client_id" {
#  value = module.user_pool.client_id
#}
#
#output "client_secret" {
#  value = module.user_pool.client_secret
#  sensitive = true
#}
#
#output "user_pool_id" {
#  value = module.user_pool.user_pool_id
#}

output "url" {
  value = module.api_gateway.api_url
}