import queue

from exceptions.InvalidInputError import InvalidInputError
from util.Position import Position


class MovesUtil:
    alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'

    def __init__(self, player1_symbol, player2_symbol, dimension=16):
        self.player1_symbol = player1_symbol
        self.player2_symbol = player2_symbol
        self.dimension = dimension

    @staticmethod
    def ask_user_for_move(player_symbol):
        print(f'{player_symbol} player move')
        start_piece_position = input("Enter the start piece position:")
        end_piece_position = input("Enter the end piece position:")
        return start_piece_position, end_piece_position

    def validate_move(self, game_board, current_player, start_position, end_position):
        if game_board[start_position.row][start_position.column] == ' ' \
                or game_board[start_position.row][start_position.column] != current_player.symbol:
            raise InvalidInputError("Provided start cell does not contain the current player's piece")
        if game_board[end_position.row][end_position.column] != ' ':
            raise InvalidInputError("End cell is occupied")

        move_queue = queue.Queue()
        enqueued = set()
        adjacent_positions = self.get_adjacent_positions(start_position)
        # add parent to enqueued, so we do not go back and go into deadlock
        enqueued.add(start_position)
        # used to check cells which are directly close to the start cell
        for adjacent_position in adjacent_positions:
            if game_board[adjacent_position.row][adjacent_position.column] == ' ':
                # this means that it will be just one cell move so after this move ends
                if adjacent_position == end_position:
                    return True
                enqueued.add(adjacent_position)
            else:
                possible_free_space_position = Position(2 * adjacent_position.row - start_position.row,
                                                        2 * adjacent_position.column - start_position.column)
                if self.validate_possible_position(game_board, possible_free_space_position):
                    if game_board[possible_free_space_position.row][possible_free_space_position.column] == ' ':
                        if possible_free_space_position == end_position:
                            return True
                        move_queue.put(possible_free_space_position)
                        enqueued.add(possible_free_space_position)

        # here in move queue I have got cells to which jump could be performed from start cell
        while not move_queue.empty():
            current_position = move_queue.get()
            adjacent_positions = self.get_adjacent_positions(current_position)
            for adjacent_position in adjacent_positions:
                # means that adjacent cell is occupied, and we can check whether one more jump can be performed
                if game_board[adjacent_position.row][adjacent_position.column] != ' ':
                    possible_free_space_position = Position(2 * adjacent_position.row - current_position.row,
                                                            2 * adjacent_position.column - current_position.column)
                    # second condition is to not add cell which was already added
                    if self.validate_possible_position(game_board, possible_free_space_position) \
                            and possible_free_space_position not in enqueued:
                        if possible_free_space_position == end_position:
                            return True
                        move_queue.put(possible_free_space_position)
        raise InvalidInputError("The move is invalid. End position can not be reached from the start position")

    def get_possible_moves_based_on_player(self, current_player, game_board):
        result = {}
        for piece_position in current_player.pieces_location:
            result[piece_position] = self.return_all_possible_moves_from_provided_position(game_board,
                                                                                           piece_position)
        return result

    def display_possible_moves(self, current_player, game_board):
        for piece, moves in self.get_possible_moves_in_human_readable_format(current_player, game_board).items():
            print(f"Piece: {piece}, Possible moves: {', '.join(moves)}")

    def get_possible_moves_in_human_readable_format(self, current_player, game_board):
        possible_moves = self.get_possible_moves_based_on_player(current_player, game_board)
        human_readable_moves = {}
        for piece_position, moves in possible_moves.items():
            human_readable_piece_position = MovesUtil.translate_coordinates_position_to_position(piece_position)
            human_readable_moves[human_readable_piece_position] = [
                MovesUtil.translate_coordinates_position_to_position(move) for move in moves]
        return human_readable_moves

    def return_all_possible_moves_from_provided_position(self, game_board, position):
        move_queue = queue.Queue()
        enqueued = set()
        possible_moves = set()
        adjacent_positions = self.get_adjacent_positions(position)

        # Add parent to enqueued, so we do not go back and go into deadlock
        enqueued.add(position)

        # Used to check cells which are directly close to the start cell
        for adjacent_position in adjacent_positions:
            if game_board[adjacent_position.row][adjacent_position.column] == ' ':
                # This means that it will be just one cell move so after this move ends
                possible_moves.add(adjacent_position)
                enqueued.add(adjacent_position)
            else:
                possible_free_space_position = Position(2 * adjacent_position.row - position.row,
                                                        2 * adjacent_position.column - position.column)
                if self.validate_possible_position(game_board, possible_free_space_position):
                    if game_board[possible_free_space_position.row][possible_free_space_position.column] == ' ':
                        possible_moves.add(possible_free_space_position)
                        move_queue.put(possible_free_space_position)
                        enqueued.add(possible_free_space_position)

        # Here in move queue, I have got cells to which jump could be performed from the start cell
        while not move_queue.empty():
            current_position = move_queue.get()
            adjacent_positions = self.get_adjacent_positions(
                Position(current_position.row, current_position.column))
            for adjacent_position in adjacent_positions:
                # Means that adjacent cell is occupied, and we can check whether one more jump can be performed
                if game_board[adjacent_position.row][adjacent_position.column] != ' ':
                    # Used to calculate possible free space
                    possible_free_space_position = Position(2 * adjacent_position.row - current_position.row,
                                                            2 * adjacent_position.column - current_position.column)
                    # Second condition is to not add cell which was already added
                    if self.validate_possible_position(game_board, possible_free_space_position) \
                            and possible_free_space_position not in enqueued:
                        possible_moves.add(possible_free_space_position)
                        move_queue.put(possible_free_space_position)
                        enqueued.add(possible_free_space_position)
        return possible_moves

    # position is provided in human-readable format like 'e4'

    def translate_user_provided_position_to_coordinates_position(self, position):
        provided_column = position[0].upper()
        if provided_column in MovesUtil.alphabet:
            column_index = MovesUtil.alphabet.index(position[0].upper())
        else:
            raise InvalidInputError(f'Provided position {position} is not present on this game board. Verify it')
        row_index = int(position[1:]) - 1
        if not self.dimension > row_index >= 1:
            raise InvalidInputError(f'Provided position {position} is not present on this game board. Verify it')
        return Position(row_index, column_index)

    @staticmethod
    def translate_coordinates_position_to_position(position):
        column_letter = MovesUtil.alphabet[position.column]
        row_number = position.row + 1
        return f"{column_letter.lower()}{row_number}"

    # this one is used when I need to check whether during jump end cell exists and if it is not occupied

    def validate_possible_position(self, game_board, possible_free_space_position):
        if (0 <= possible_free_space_position.row < self.dimension
                and 0 <= possible_free_space_position.column < self.dimension
                and game_board[possible_free_space_position.row][possible_free_space_position.column] == ' '):
            return True
        return False

    def get_adjacent_positions(self, position):
        adjacent_positions = []

        # Define the indices for adjacent cells including diagonals
        positions = [
            Position(position.row - 1, position.column),  # Up
            Position(position.row + 1, position.column),  # Down
            Position(position.row, position.column - 1),  # Left
            Position(position.row, position.column + 1),  # Right
            Position(position.row - 1, position.column - 1),  # Up-Left
            Position(position.row - 1, position.column + 1),  # Up-Right
            Position(position.row + 1, position.column - 1),  # Down-Left
            Position(position.row + 1, position.column + 1),  # Down-Right
        ]

        # Iterate through the adjacent indices
        for pos in positions:
            # Check if the indices are within the bounds of the matrix
            if 0 <= pos.row < self.dimension and 0 <= pos.column < self.dimension:
                adjacent_positions.append(pos)
        return adjacent_positions

    def check_win_condition(self, current_player, game_board):
        if current_player.symbol == self.player2_symbol:
            for j in range(5):
                if game_board[0][j] != current_player.symbol:
                    return False

            for i in range(1, 5):
                for j in range(5 - i + 1):
                    if game_board[i][j] != current_player.symbol:
                        return False
            return True
        else:
            for j in range(self.dimension - 1, self.dimension - 6, -1):
                if game_board[self.dimension - 1][j] != current_player.symbol:
                    return False

            for i in range(self.dimension - 2, self.dimension - 6, -1):
                for j in range(self.dimension - 1, self.dimension - 6 + abs(self.dimension - i - 2), -1):
                    if game_board[i][j] != current_player.symbol:
                        return False

            return True
