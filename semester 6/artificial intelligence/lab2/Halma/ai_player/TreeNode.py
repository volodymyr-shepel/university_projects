from ai_player.Move import Move


class Node:
    def __init__(self, current_player, player1, player2, move: Move):
        self.current_player = current_player
        self.player1 = player1
        self.player2 = player2
        self.value = 0
        self.move = move # this is the move which has been done from previous node to current node
