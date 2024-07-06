from bs4 import BeautifulSoup
import requests
import re
import json
import os 
import time
 
class hltv_parser:
    @staticmethod
    def get_top_30_teams():
        
        home_page_link = "https://www.hltv.org"
        ranking_page_url = "https://www.hltv.org/ranking/teams/2023/june/5"
        player_stats_names = ["Rating","kills_per_round","headshots","maps_played","deaths_per_round","rounds_contributed"]
        
        players = {}
        players_stats = {}
        teams = {}
        
        headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
    }
        req = requests.get(ranking_page_url,headers=headers)
        req.raise_for_status();
        soup = BeautifulSoup(req.text, "html.parser")
        ranked_teams = soup.find_all('div',class_="ranked-team standard-box")
        
        # this is the variable to write the rank of the team in world ranking
        for team in ranked_teams:
            
            # here is the link to the page of the team
            team_page_url = team.find("div",class_= "more").find("a")["href"]
            
            # here is the team_id and team_name
            team_id = int(team_page_url.split("/")[1:][1])
            
            # team_name
            team_name = team.find("span",class_ = "name").text.strip()
            
            # Get the number of points of team in world ranking
            team_points = team.find("span",class_ = "points").text.strip()
            team_points = int(re.search(r'\d+', team_points).group())
            
            teams[int(team_id)] = {}
            teams[team_id]["team_name"] = team_name
            teams[team_id]["team_points"] = team_points
            
            
            # contains the link to the pages of players
            team_members = team.find_all("a",class_ = "pointer")
            team_members = [member.get('href') for member in team_members]
            
            for member in team_members:
                
        
        
                player_id = int( member.split("/")[1:][1])
                
                player_page_url = home_page_link + member
                
                r = requests.get(player_page_url,headers=headers)
                player_soup = BeautifulSoup(r.content,"html.parser")
                
                # Here I am collecting general info about the player
                
                # block where there is information about the player
                player_info = player_soup.find("div",class_= "playerInfoWrapper")
                
                print(player_page_url)
                player_nickname = player_info.find("h1",class_="playerNickname").text.strip()
                player_realname = player_info.find("div",class_="playerRealname").text.strip()
                player_nationality = player_info.find("div",class_="playerRealname").img["title"]
                
                
                
                # Here I am collecting player's statistics 
                stat_info = player_soup.find("div",class_="playerpage-container")
                
                player_statistics = stat_info.find_all("span",class_="statsVal")
                
                # contains the value of the statistics(names in player_stats_names)
                player_statistics = [float(stat.text.strip().strip("%")) for stat in player_statistics]
                
                players_stats[player_id] = {}
                print(player_statistics)
                    # adding information about player into dictionary
                players[player_id] = {}
                players[player_id]["player_nickname"] = player_nickname
                players[player_id]["player_realname"] = player_realname
                players[player_id]["player_nationality"] = player_nationality
                players[player_id]["team_id"] = team_id
                    
                for i in range(len(player_stats_names)):
                    column_name = player_stats_names[i]
                        
                    players_stats[player_id][column_name] = player_statistics[i]

                time.sleep(1)
        
        # writing data to json files
        hltv_parser.write_to_json(players,"players")   
        hltv_parser.write_to_json(players_stats,"players_stats") 
        hltv_parser.write_to_json(teams,"teams")      
        
    @staticmethod
    def write_to_json(dictionary,filename):
        with open(f"{filename}.json", "w",encoding="utf-8") as outfile:
            json.dump(dictionary, outfile,indent=4,ensure_ascii=False)
                
            
        
            
            
        