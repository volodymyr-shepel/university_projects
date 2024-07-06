resource "aws_api_gateway_rest_api" "api" {
  name        = var.api_name
  description = var.api_description
}

resource "aws_api_gateway_method" "method" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_rest_api.api.root_resource_id  # Use the root resource ID directly
  http_method   = var.http_method
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "integration" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_rest_api.api.root_resource_id  # Use the root resource ID directly
  http_method             = aws_api_gateway_method.method.http_method
  integration_http_method = "POST"
  uri                     = var.lambda_function_invoke_arn
  type                    = "AWS_PROXY"
}

resource "aws_api_gateway_deployment" "deployment" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  stage_name  = var.stage_name
  depends_on = [
    aws_api_gateway_integration.integration
  ]
}

resource "aws_lambda_permission" "api_gateway" {
  statement_id  = "AllowAPIGatewayInvoke"
  action        = "lambda:InvokeFunction"
  function_name = var.function_name
  principal     = "apigateway.amazonaws.com"
  source_arn    = "${aws_api_gateway_rest_api.api.execution_arn}/*/*"
}
