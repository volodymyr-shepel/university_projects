from util.PlayerType import PlayerType


class Player:
    def __init__(self, symbol, player_type: PlayerType):
        self.symbol = symbol
        self.player_type = player_type
        self.pieces_location = set()
