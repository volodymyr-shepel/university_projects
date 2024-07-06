resource "aws_dynamodb_table" "Rating" {
  name         = var.rating_table_name
  billing_mode = "PAY_PER_REQUEST"

  hash_key = "playerId"

  attribute {
    name = "playerId"
    type = "S"
  }

  attribute {
    name = "score"
    type = "N"
  }

  global_secondary_index {
    name            = "ScoreIndex"
    hash_key        = "score"
    projection_type = "ALL"
  }
  tags = {
    Name = var.rating_table_name
  }
}

resource "aws_dynamodb_table" "GameHistory" {
  name         = var.game_history_table_name
  billing_mode = "PAY_PER_REQUEST"

  hash_key = "gameId"

  attribute {
    name = "gameId"
    type = "S"
  }

  attribute {
    name = "player1"
    type = "S"
  }

  attribute {
    name = "player2"
    type = "S"
  }

  attribute {
    name = "result"
    type = "S"
  }

  global_secondary_index {
    name               = "Player1Index"
    hash_key           = "player1"
    projection_type    = "ALL"
  }

  global_secondary_index {
    name               = "Player2Index"
    hash_key           = "player2"
    projection_type    = "ALL"
  }

  global_secondary_index {
    name               = "ResultIndex"
    hash_key           = "result"
    projection_type    = "ALL"
  }

  tags = {
    Name = var.game_history_table_name
  }
}
