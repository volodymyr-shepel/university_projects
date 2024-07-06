import copy
import logging

from ai_player.MiniMax import MiniMax
from ai_player.TreeNode import Node
from exceptions.InvalidInputError import InvalidInputError
from util.MovesUtil import MovesUtil
from util.Player import Player
from util.PlayerType import PlayerType
from util.Position import Position

log_file = 'game.log'

# Configure logging to write logs to both console and the file
logging.basicConfig(filename=log_file, format='%(asctime)s - %(message)s', level=logging.INFO,filemode='w')


class GameBoard:
    alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
    MINIMAX_DEPTH = 1
    OPPONENT_CAMP_LOCATIONS = dict()

    def __init__(self, player1: Player, player2: Player, dimension: int = 16):
        self.dimension = dimension
        self.player1: Player = player1
        self.player2: Player = player2
        self.gameBoard = self.create_game_board()
        self.current_player: Player = self.player1
        self.winner = None
        self.movesUtil: MovesUtil = MovesUtil(self.player1.symbol, self.player2.symbol, self.dimension)

    def create_game_board(self):
        game_board = []
        for _ in range(self.dimension):
            row = [' '] * self.dimension
            game_board.append(row)
        self.fill_game_board(game_board)
        return game_board

    # used to fill game board with pieces
    def fill_game_board(self, game_board):
        # Fill the top left corner (Player 1's starting area)
        GameBoard.OPPONENT_CAMP_LOCATIONS[
            self.player2.symbol] = set()  # here will be stored locations of cells of player 1's opponent
        for j in range(5):
            game_board[0][j] = self.player1.symbol
            self.player1.pieces_location.add(Position(0, j))
            GameBoard.OPPONENT_CAMP_LOCATIONS[self.player2.symbol].add(Position(0, j))

        for i in range(1, 5):
            for j in range(5 - i + 1):
                game_board[i][j] = self.player1.symbol
                self.player1.pieces_location.add(Position(i, j))
                GameBoard.OPPONENT_CAMP_LOCATIONS[self.player2.symbol].add(Position(i, j))

        GameBoard.OPPONENT_CAMP_LOCATIONS[
            self.player1.symbol] = set()  # here will be stored locations of cells of player 2's opponent


        # Fill the bottom right corner (Player 2's starting area)
        for j in range(self.dimension - 1, self.dimension - 6, -1):
            game_board[self.dimension - 1][j] = self.player2.symbol
            self.player2.pieces_location.add(Position(self.dimension - 1, j))
            GameBoard.OPPONENT_CAMP_LOCATIONS[self.player1.symbol].add(Position(self.dimension - 1, j))
        for i in range(self.dimension - 2, self.dimension - 6, -1):
            for j in range(self.dimension - 1, self.dimension - 6 + abs(self.dimension - i - 2), -1):
                game_board[i][j] = self.player2.symbol
                self.player2.pieces_location.add(Position(i, j))
                GameBoard.OPPONENT_CAMP_LOCATIONS[self.player1.symbol].add(Position(i, j))

    def start_game(self):
        logging.info("Game started")
        while not self.winner:
            self.display_game_board()
            # self.movesUtil.display_possible_moves(self.current_player, self.gameBoard)
            self.make_move()
            if self.movesUtil.check_win_condition(self.current_player, self.gameBoard):
                self.winner = self.current_player
                break
            self.switch_player()
        logging.info(f"GAME IS FINISHED. WINNER IS {self.winner.symbol}")
        print(f"GAME IS FINISHED. WINNER IS {self.winner.symbol}")
        self.display_game_board()


    def switch_player(self):
        self.current_player = self.player1 if self.current_player == self.player2 else self.player2

    def make_move(self):
        if self.current_player.player_type == PlayerType.HUMAN:
            self.human_make_move()
        else:
            self.ai_make_move()

    def human_make_move(self):
        start_piece_position, end_piece_position = self.movesUtil.ask_user_for_move(self.current_player.symbol)
        try:
            start_position = self.movesUtil.translate_user_provided_position_to_coordinates_position(
                start_piece_position)
            end_position = self.movesUtil.translate_user_provided_position_to_coordinates_position(end_piece_position)
            self.movesUtil.validate_move(self.gameBoard, self.current_player, start_position, end_position)
            self.update_board_after_move(start_position, end_position)
        except InvalidInputError as e:
            logging.info(f"Invalid move. Err: {e.message}\n")
            # if some error happens user should try to move once more
            self.human_make_move()

    def ai_make_move(self):
        logging.info("ENTER AI MOVE METHOD")
        player1_copy = copy.deepcopy(self.player1)
        player2_copy = copy.deepcopy(self.player2)

        new_current_player = player1_copy if self.current_player == self.player1 else player2_copy
        new_node = Node(new_current_player, player1_copy, player2_copy, None)

        miniMax = MiniMax(self.movesUtil,GameBoard.OPPONENT_CAMP_LOCATIONS)
        is_maximizing_player = self.current_player == self.player1
        #result, best_move = miniMax.minimax(new_node, GameBoard.MINIMAX_DEPTH, is_maximizing_player)
        result, best_move = miniMax.alpha_beta_pruning(new_node, GameBoard.MINIMAX_DEPTH, is_maximizing_player,-float("inf"), float("inf"))
        logging.info(
            f"RESULT OF AI MOVE METHOD FOR PLAYER {self.current_player.symbol}: {result}. Best Move: {best_move}")
        self.update_board_after_move(best_move.start_position, best_move.end_position)

    def update_board_after_move(self, start_position, end_position):
        player = self.gameBoard[start_position.row][start_position.column]
        self.gameBoard[start_position.row][start_position.column] = " "
        self.gameBoard[end_position.row][end_position.column] = player
        self.current_player.pieces_location.remove(start_position)
        self.current_player.pieces_location.add(end_position)

    def display_game_board(self):
        for i in range(self.dimension):
            print(self.alphabet[i], end=" ")
        print()
        for i in range(1, self.dimension + 1):
            print('|'.join(str(cell) for cell in self.gameBoard[i - 1]) + " " + str(i))
            print("-" * self.dimension * 2)
