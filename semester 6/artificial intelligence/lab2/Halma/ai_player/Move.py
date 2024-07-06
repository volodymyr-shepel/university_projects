from util.Position import Position


class Move:
    def __init__(self, start_position: Position, end_position: Position):
        self.start_position = start_position
        self.end_position = end_position

    def __str__(self):
        return f"Move from {self.start_position} to {self.end_position}"
