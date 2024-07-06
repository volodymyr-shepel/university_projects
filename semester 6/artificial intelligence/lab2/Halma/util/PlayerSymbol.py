from enum import Enum


class PlayerSymbol(Enum):
    X = 1,
    O = 2

    def __str__(self):
        if self == PlayerSymbol.X:
            return "X"
        elif self == PlayerSymbol.O:
            return "O"

    def equals(self, other):
        return self == other

