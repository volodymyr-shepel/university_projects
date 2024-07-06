provider "aws" {
  region = var.amazon_region
}

resource "aws_sns_topic" "sns_topic" {
  name = var.sns_topic_name
}

module "lambda_trigger_function" {
  source             = "./modules/cognitoLambdaFunction"
  existing_role_name = var.existing_role_name
  function_name      = var.subscribe_to_sns_function_name
  sns_topic_arn      = aws_sns_topic.sns_topic.arn
}


# Backend Module
module "vpc_created" {
  source = "./modules/vpc"
}


module "user_pool" {
  source                            = "./modules/user_pool"
  callback_url                      = "https://${var.frontend_cname_prefix}.us-east-1.elasticbeanstalk.com"
  client_name                       = var.client_name
  domain_name                       = var.domain_name
  user_pool_name                    = var.user_pool_name
  lambda_function_post_confirmation = module.lambda_trigger_function.lambda_function_arn
}

resource "aws_lambda_permission" "allow_cognito_pool2" {
  statement_id  = "AllowExecutionFromCognitoPool2"
  action        = "lambda:InvokeFunction"
  function_name = var.subscribe_to_sns_function_name
  principal     = "cognito-idp.amazonaws.com"
  source_arn    = module.user_pool.user_pool_arn
}

module "s3" {
  source      = "./modules/s3"
  bucket_name = var.s3_bucket_name
}

module "dynamoDB" {
  source                  = "./modules/dynamoDB"
  game_history_table_name = var.game_history_table_name
  rating_table_name       = var.rating_table_name
}

module "lambda_process_results_function" {
  source                  = "./modules/processGameResultsLambdaFunction"
  existing_role_name      = var.existing_role_name
  function_name           = var.process_game_results_function_name
  sns_topic_arn           = aws_sns_topic.sns_topic.arn
  game_history_table_name = var.game_history_table_name
  rating_table_name       = var.rating_table_name
}

module "api_gateway" {
  source                     = "./modules/apiGateway"
  api_description            = var.api_description
  api_name                   = var.api_name
  lambda_function_invoke_arn = module.lambda_process_results_function.lambda_function_invoke_arn
  resource_path              = var.resource_path
  stage_name                 = var.stage_name
  function_name              = var.process_game_results_function_name
}

module "backend" {
  source = "./modules/backend"
  vpc_id = module.vpc_created.vpc_with_https_id
  subnet = module.vpc_created.subnet
  backend_process_game_results_url = module.api_gateway.api_url
  amazon_region            = var.amazon_region
  aws_access_key           = var.aws_access_key
  aws_secret_key           = var.aws_secret_key
  aws_session_token        = var.aws_session_token
  aws_s3_bucket_name       = var.s3_bucket_name
  backend_cname_prefix             = var.backend_cname_prefix
  back_app_name = var.back_app_name
  backend_container_port = var.backend_container_port
}

module "front" {
  source    = "./modules/frontend"
  vpc_id    = module.vpc_created.vpc_with_https_id
  subnet    = module.vpc_created.subnet
  sec_group = module.vpc_created.sec_group

  back_url                        = module.backend.backend_ebs_url
  cognito_user_pool_id            = module.user_pool.user_pool_id
  cognito_user_pool_client_id     = module.user_pool.client_id
  cognito_user_pool_client_secret = module.user_pool.client_secret
  cognito_user_pool_client_name   = var.client_name
  cognito_user_pool_domain        = module.user_pool.user_pool_domain
  aws_s3_bucket                   = var.s3_bucket_name
  amazon_region                   = var.amazon_region
  cname_prefix                    = var.frontend_cname_prefix
  front_app_name                  = var.front_app_name
}

