import copy

from ai_player.HeuristicFunctions import HeuristicFunctions
from ai_player.Move import Move
from ai_player.TreeNode import Node
from util.MovesUtil import MovesUtil
from util.Player import Player


class MiniMax:
    def __init__(self, moves_util: MovesUtil, opponent_camp_locations):
        self.moves_util = moves_util
        self.opponent_camp_locations = opponent_camp_locations

    def minimax(self, node: Node, depth: int, maximizing_player: bool):
        game_board = MiniMax.create_game_board_based_on_players(node.player1, node.player2)

        if depth == 0 or self.moves_util.check_win_condition(node.current_player, game_board):
            player_to_pass = node.player1 if node.current_player == node.player2 else node.player2
            return (HeuristicFunctions.evaluate_move_distance_progress(node.move.start_position, node.move.end_position,
                                                                       player_to_pass, self.opponent_camp_locations),
                    node.move)

        all_moves = self.moves_util.get_possible_moves_based_on_player(node.current_player, game_board).items()

        edge_evaluation = -float("inf") if maximizing_player else float("inf")

        best_move = None
        for start_position, possible_moves in all_moves:
            for possible_move in possible_moves:
                player1_copy = copy.deepcopy(node.player1)
                player2_copy = copy.deepcopy(node.player2)

                if node.current_player == node.player1:
                    player1_copy.pieces_location.remove(start_position)
                    player1_copy.pieces_location.add(possible_move)
                    new_current_player = player2_copy
                else:
                    player2_copy.pieces_location.remove(start_position)
                    player2_copy.pieces_location.add(possible_move)
                    new_current_player = player1_copy

                performed_move = Move(start_position, possible_move)
                new_node = Node(new_current_player, player1_copy, player2_copy, performed_move)
                evaluation, move = self.minimax(new_node, depth - 1, not maximizing_player)
                if maximizing_player:
                    if evaluation > edge_evaluation:
                        edge_evaluation = evaluation
                        best_move = performed_move

                else:
                    if evaluation < edge_evaluation:
                        edge_evaluation = evaluation
                        best_move = performed_move
        return edge_evaluation, best_move

    # Alpha is the best value that the maximizer currently can guarantee at that level or above.
    # Beta is the best value that the minimizer currently can guarantee at that level or below.
    def alpha_beta_pruning(self, node: Node, depth: int, maximizing_player: bool, alpha, beta):
        game_board = MiniMax.create_game_board_based_on_players(node.player1, node.player2)

        if depth == 0 or self.moves_util.check_win_condition(node.current_player, game_board):
            player_to_pass = node.player1 if node.current_player == node.player2 else node.player2
            return HeuristicFunctions.evaluate_weighted_average_distance(current_player = player_to_pass,
                                                                         opponent_camp_locations = self.opponent_camp_locations), node.move

            # another way for heuristic functions
            # return (HeuristicFunctions.evaluate_move_distance_progress(node.move.start_position, node.move.end_position,
            #                                                            player_to_pass, self.opponent_camp_locations),
            #         node.move)

        all_moves = self.moves_util.get_possible_moves_based_on_player(node.current_player, game_board).items()

        edge_evaluation = -float("inf") if maximizing_player else float("inf")

        best_move = None
        outer_loop_break = False  # Flag to break the outer loop

        # Labeled loop
        for start_position, possible_moves in all_moves:
            for possible_move in possible_moves:
                player1_copy = copy.deepcopy(node.player1)
                player2_copy = copy.deepcopy(node.player2)

                if node.current_player == node.player1:
                    player1_copy.pieces_location.remove(start_position)
                    player1_copy.pieces_location.add(possible_move)
                    new_current_player = player2_copy
                else:
                    player2_copy.pieces_location.remove(start_position)
                    player2_copy.pieces_location.add(possible_move)
                    new_current_player = player1_copy

                performed_move = Move(start_position, possible_move)
                new_node = Node(new_current_player, player1_copy, player2_copy, performed_move)
                evaluation, move = self.alpha_beta_pruning(new_node, depth - 1, not maximizing_player, alpha, beta)
                if maximizing_player:
                    if evaluation > edge_evaluation:
                        edge_evaluation = evaluation
                        best_move = performed_move
                        alpha = max(alpha, edge_evaluation)
                        if beta <= alpha:
                            outer_loop_break = True  # Set flag to break outer loop
                            break
                else:
                    if evaluation < edge_evaluation:
                        edge_evaluation = evaluation
                        best_move = performed_move
                        beta = min(beta, edge_evaluation)
                        if beta <= alpha:
                            outer_loop_break = True  # Set flag to break outer loop
                            break

            if outer_loop_break:
                break  # Break outer loop
        return edge_evaluation, best_move

    @staticmethod
    def create_game_board_based_on_players(player1: Player, player2: Player):
        game_board = []
        dimension = 16
        for _ in range(dimension):
            row = [' '] * dimension
            game_board.append(row)

        for piece_location in player1.pieces_location:
            game_board[piece_location.row][piece_location.column] = player1.symbol

        for piece_location in player2.pieces_location:
            game_board[piece_location.row][piece_location.column] = player2.symbol

        return game_board
