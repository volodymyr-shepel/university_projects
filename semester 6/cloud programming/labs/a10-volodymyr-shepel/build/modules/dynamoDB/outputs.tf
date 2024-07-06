output "rating_table_arn" {
  value = aws_dynamodb_table.Rating.arn
}

output "game_history_table_arn" {
  value = aws_dynamodb_table.GameHistory.arn
}