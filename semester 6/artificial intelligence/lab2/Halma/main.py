from GameBoard import GameBoard
from util.Player import Player
from util.PlayerSymbol import PlayerSymbol
from util.PlayerType import PlayerType


def main():
    p1: Player = Player(PlayerSymbol.O, PlayerType.AI)
    p2: Player = Player(PlayerSymbol.X, PlayerType.AI)
    board = GameBoard(p1, p2)
    board.start_game()



if __name__ == '__main__':
    main()

