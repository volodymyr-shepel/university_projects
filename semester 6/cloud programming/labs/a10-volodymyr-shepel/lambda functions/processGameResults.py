import json
import boto3
import os

# Initialize the DynamoDB and SNS resources
dynamodb = boto3.resource('dynamodb')
sns = boto3.client('sns')

# Fetch the table names and SNS topic ARN from environment variables
game_history_table_name = os.environ['GAME_HISTORY_TABLE']
rating_table_name = os.environ['RATING_TABLE']
sns_topic_arn = os.environ['SNS_TOPIC_ARN']

game_history_table = dynamodb.Table(game_history_table_name)
rating_table = dynamodb.Table(rating_table_name)

def send_sns_notification():
    message = "Leaderboard has been updated."
    sns.publish(
        TopicArn=sns_topic_arn,
        Message=message,
        Subject="Rating Update"
    )

def update_rating(player_email, increment):
    # Fetch current score
    response = rating_table.get_item(Key={'playerId': player_email})
    if 'Item' in response:
        current_score = response['Item']['score']
    else:
        current_score = 0  # Assume a new player with a score of 0 if not found
    
    # Update the score
    new_score = current_score + increment

    # Save the updated score back to the Rating table
    rating_table.put_item(
        Item={
            'playerId': player_email,
            'score': new_score
        }
    )

def lambda_handler(event, context):
    try:
        # Log the entire event for debugging purposes
        print("Received event: " + json.dumps(event))

        # Extract the request body
        if 'body' in event:
            body = json.loads(event['body'])
        else:
            raise KeyError("The 'body' key is missing from the event object")

        # Extracting required fields from the body
        game_id = body['gameId']
        player1 = body['player1']
        player2 = body['player2']
        result = body['result']

        # Log the parsed body for debugging purposes
        print("Parsed body: ", body)

        # Put the item into the GameHistory table
        game_history_table.put_item(
            Item={
                'gameId': game_id,
                'player1': player1,
                'player2': player2,
                'result': result
            }
        )

        # Update the ratings based on the result
        if result == player1:
            update_rating(player1, 1)
            update_rating(player2, -1)
            leaderboard_updated = True
        elif result == player2:
            update_rating(player1, -1)
            update_rating(player2, 1)
            leaderboard_updated = True
        elif result == "Tie":
            leaderboard_updated = False

        # Send notification via SNS if the leaderboard was updated
        if leaderboard_updated:
            send_sns_notification()

        response = {
            'statusCode': 200,
            'body': json.dumps({'message': 'Game history recorded and ratings updated successfully.'})
        }
    except KeyError as e:
        response = {
            'statusCode': 400,
            'body': json.dumps({'message': 'Bad Request', 'error': str(e)})
        }
    except Exception as e:
        response = {
            'statusCode': 500,
            'body': json.dumps({'message': 'Error processing request.', 'error': str(e)})
        }

    return response
