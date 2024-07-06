from EnumHelpers import ProblemsToSolve
from FactsDefinition import Problem
from PhoneSupport import PhoneSupport


def get_problem():
    # Display all enum members
    print("Choose a problem to solve:")
    for problem in ProblemsToSolve:
        print(f"{problem.value}: {problem.name}")

    # Let the user choose a number
    user_choice = input("Enter the number corresponding to the problem to solve: ")

    # Convert the user input to an integer
    try:
        user_choice = int(user_choice)
        if user_choice in [member.value for member in ProblemsToSolve]:
            selected_problem: ProblemsToSolve = ProblemsToSolve(user_choice)
            print(f"You selected: {selected_problem.name}")
            return selected_problem
        else:
            print("Invalid input. Please enter a valid number.")
    except ValueError:
        print("Invalid input. Please enter a valid number.")


def main():
    engine = PhoneSupport()
    engine.reset()
    selected_problem = get_problem()
    engine.declare(Problem(problem_type=selected_problem, is_solved=False))

    engine.run()


if __name__ == '__main__':
    main()
