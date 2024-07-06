import math

from util.Player import Player
from util.Position import Position


class HeuristicFunctions:
    @staticmethod
    def euclidean_distance(start_position: Position, end_position: Position):
        # Calculate Euclidean distance between two points
        distance = math.sqrt(
            (start_position.row - end_position.row) ** 2 + (start_position.column - end_position.column) ** 2)
        return distance

    # THIS IS HEURISTIC FUNCTION WHICH EVALUATES EVERY MOVE. Since one player1 wants to maximize the distance from
    # the point 0,0 and another player wants to minimize the distance to 0,0 each will be evaluated based on how much it
    # increases(for p1)/decreases(for p2) total distance

    # Also it takes into account whether piece arrived to opponents camp
    @staticmethod
    def evaluate_move_distance_progress(start_position, end_position, current_player: Player, opponent_camp_locations):
        corner_position = Position(0, 0)
        current_distance = HeuristicFunctions.euclidean_distance(start_position, corner_position)
        distance_after_move = HeuristicFunctions.euclidean_distance(end_position, corner_position)
        extra_points = 1
        if (start_position not in opponent_camp_locations[current_player.symbol] and
                end_position in opponent_camp_locations[current_player.symbol]):
            extra_points = 2
        return extra_points * (distance_after_move - current_distance)

    # priority of pieces which are closer to opponents camp
    @staticmethod
    def evaluate_move_pieces_closer_to_opponent_camp(start_position, end_position, current_player: Player,
                                                     opponent_camp_locations):
        corner_position = Position(0, 0)
        current_distance = HeuristicFunctions.euclidean_distance(start_position, corner_position)
        distance_after_move = HeuristicFunctions.euclidean_distance(end_position, corner_position)
        extra_points = 1
        if (start_position not in opponent_camp_locations[current_player.symbol] and
                end_position in opponent_camp_locations[current_player.symbol]):
            extra_points = 2
        return extra_points * (distance_after_move - 1.5 * current_distance)

    @staticmethod
    def evaluate_weighted_average_distance(current_player : Player, opponent_camp_locations):
        corner_position = Position(0, 0)
        total_distance = 0
        for location in current_player.pieces_location:
            current_distance = HeuristicFunctions.euclidean_distance(location, corner_position)
            extra_points = 1
            if location in opponent_camp_locations[current_player.symbol]:
                extra_points = 2
            total_distance += current_distance * extra_points
        return total_distance / len(current_player.pieces_location)



