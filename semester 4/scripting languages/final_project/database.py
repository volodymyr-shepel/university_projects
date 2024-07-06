import sqlite3
import json
from hltv_parser import hltv_parser
import os
class databaseCreation:
    
    
    @staticmethod
    def create_tables(conn):
        cursor = conn.cursor()
        
        cursor.execute('''CREATE TABLE IF NOT EXISTS teams(
                    team_id VARCHAR(10) PRIMARY KEY,
                    team_name VARCHAR(100),
                    team_points INTEGER)''')

        
        cursor.execute('''CREATE TABLE IF NOT EXISTS players (
                    player_id VARCHAR(10) PRIMARY KEY,
                    player_nickname VARCHAR(100),
                    player_realname VARCHAR(150),
                    player_nationality VARCHAR(100),
                    team_id VARCHAR(10),
  					FOREIGN key(team_id) REFERENCES teams(team_id)
                );''')

        cursor.execute('''CREATE TABLE IF NOT EXISTS players_stats(
                    player_id PRIMARY KEY,
  					Rating FLOAT,
  					kills_per_round FLOAT,
  					headshots FLOAT,
  					maps_played FLOAT,
  					deaths_per_round FLOAT,
  					rounds_contributed FLOAT,
					FOREIGN KEY(player_id) REFERENCES players(player_id)					
                    )''')

        conn.commit()
    
    @staticmethod
    def insert_data_from_json(json_file, table_name, conn):
        cursor = conn.cursor()

        with open(json_file, 'r',encoding="utf-8") as file:
            json_data = json.load(file)

            # Iterate through the JSON data
            for key, value in json_data.items():
                # Get the column names and values from the JSON data
                
                values = ', '.join('?' for _ in range(len(value.values()) + 1)) # +1 to insert primary key

                # Prepare the SQL statement
                sql = f"INSERT INTO {table_name} VALUES ({values})"

                
                # here the actual values(numerical or strings) are stored
                arguments = list(value.values())
                arguments.insert(0,key)
                # Extract the values and execute the SQL statement
                cursor.execute(sql,arguments)

        # Commit the changes and close the connection
        conn.commit()
        
    
    # Creates the database and tables needed
    @staticmethod
    def create_and_fill():
        print("CREATION")
        conn = sqlite3.connect(f'hltv.db') # hltv.db name of the database
        if not (os.path.isfile("teams.json") and os.path.isfile("players.json") and os.path.isfile("players_stats.json")):
            hltv_parser.get_top_30_teams()
        
        databaseCreation.create_tables(conn)
        
        databaseCreation.insert_data_from_json("teams.json", "teams", conn)
        
        databaseCreation.insert_data_from_json("players.json", "players", conn)
        
        databaseCreation.insert_data_from_json("players_stats.json", "players_stats", conn)
        
        conn.close()

 
    