from experta import *
from experta.utils import unfreeze

from Constants import Constants
from EnumHelpers import ConnectionStatus, BluetoothVersion, UpdateStatus, DeviceHealth, IOSVersion, ProblemsToSolve, \
    MessageColor
from FactsDefinition import Phone, Problem


def is_problem_solved():
    """
    Asks the user whether the given problem is solved and accepts input.

    :param problem: The problem to solve (an enum member of ProblemsToSolve)
    :return: True if the problem is solved, False otherwise
    """
    user_input = input(f"Is problem solved? (yes/no): ").lower()
    while user_input.lower() not in ['yes', 'no']:
        print("Invalid input. Please enter 'yes' or 'no'.")
        user_input = input(f"Is problem solved? (yes/no): ").lower()
    return user_input.lower() == 'yes'


class PhoneSupport(KnowledgeEngine):

    def get_phone_fact(self):
        for _, fact in self.facts.items():
            if isinstance(fact, Phone):
                return fact

    def get_problem_fact(self):
        for _, fact in self.facts.items():
            if isinstance(fact, Problem):
                return fact

    @staticmethod
    def print_color_message(message, color_code):
        print(f"\033[{color_code}m{message}\033[0m")

    @DefFacts()
    def _initial_action(self):
        yield Phone(data={
            'hardware': {
                'display': {
                    'display_brightness': 80,  # in percentages,
                    'is_automatic': True
                },
                'battery': {
                    'battery_capacity': 50,  # in percentages
                    'battery_health_state': 40  # in percentages
                },
                'cpu': {
                    'cpu_temperature': 80,  # in celsius,
                    'cpu_load': 85  # in percentages
                },
                'ram': {
                    'ram_usage': 60  # in percentages
                }

            },
            'storage': {
                'storage_usage': 80,  # in percentages
            },
            'connectivity': {
                'wi_fi': {
                    'wi_fi_status': ConnectionStatus.TURNED_OFF,
                    'signal_strength': 25  # mb/s
                },
                'bluetooth': {
                    'bluetooth_status': ConnectionStatus.TURNED_ON,
                    'bluetooth_version': BluetoothVersion.BLUETOOTH_5_0
                },
                'cellular': {
                    'signal_strength': 25,
                    'data_usage': 2567,  # mb used during month,
                    'data_usage_limit': 4096  # mb limit during month
                }
            },
            'operating_system': {
                'update_status': UpdateStatus.UPDATE_AVAILABLE,
                'health_status': DeviceHealth.HEALTHY,
                'os_version': IOSVersion.IOS_12
            },
            'software': {
                'applications_installed':
                    [
                        {'app_name': "Instagram", 'required_os_version': IOSVersion.IOS_12},
                        {'app_name': "Safari", 'required_os_version': IOSVersion.IOS_10},
                        {'app_name': "Messenger", 'required_os_version': IOSVersion.IOS_10},

                    ]

            }
        })

    # draining battery
    @Rule(
        AND(
            OR(
                Problem(
                    is_solved=L(False),
                    problem_type=L(ProblemsToSolve.DRAINING_BATTERY)
                ),
                Problem(
                    is_solved=L(False),
                    problem_type=L(ProblemsToSolve.SLOW_PERFORMANCE)
                ),
                Problem(
                    is_solved=L(False),
                    problem_type=L(ProblemsToSolve.OVERHEATING)
                ),
            ),
            Phone(
                data__hardware__cpu__cpu_load=P(lambda load: load > Constants.EDGE_CPU_USAGE)
            )
        ),
        salience=3
    )
    def check_cpu_load(self):
        self.print_color_message(Constants.HIGH_CPU_USAGE_MESSAGE, MessageColor.RED.value)
        if is_problem_solved():
            problem_fact = self.get_problem_fact()
            self.modify(problem_fact, is_solved=True)
            self.print_color_message("Problem solved", MessageColor.GREEN.value)

            phone_fact = self.get_phone_fact()

            modified_data = unfreeze(phone_fact['data'])
            modified_data['hardware']['cpu']['cpu_load'] = Constants.NORMAL_CPU_USAGE
            self.modify(phone_fact, data=modified_data)
        else:
            self.print_color_message("Problem not yet solved", MessageColor.YELLOW.value)

    @Rule(
        AND(
            Problem(
                is_solved=L(False),
                problem_type=L(ProblemsToSolve.DRAINING_BATTERY)
            ),
            Phone(
                data__hardware__battery__battery_health_state=P(
                    lambda health_state: health_state < Constants.POOR_BATTERY_STATE)
            )
        ),
        salience=3
    )
    def check_buttery_health_state(self):
        self.print_color_message(Constants.DEGRADED_BATTERY_HEALTH, MessageColor.RED.value)
        if is_problem_solved():
            problem_fact = self.get_problem_fact()
            self.modify(problem_fact, is_solved=True)
            self.print_color_message("Problem solved", MessageColor.GREEN.value)
            phone_fact = self.get_phone_fact()
            modified_data = unfreeze(phone_fact['data'])
            modified_data['hardware']['battery']['battery_health_state'] = Constants.NORMAL_BATTERY_STATE
            modified_data['hardware']['cpu'][
                'cpu_temperature'] = Constants.NORMAL_CPU_TEMPERATURE  # since load decreased then temperature should be decreased
            self.modify(phone_fact, data=modified_data)
        else:
            self.print_color_message("Problem not yet solved", MessageColor.YELLOW.value)

    # overheating
    @Rule(
        AND(
            OR(
                Problem(
                    is_solved=L(False),
                    problem_type=L(ProblemsToSolve.OVERHEATING)
                ),
                Problem(
                    is_solved=L(False),
                    problem_type=L(ProblemsToSolve.SLOW_PERFORMANCE)
                )
            ),
            Phone(
                data__hardware__cpu__cpu_temperature=P(
                    lambda cpu_temperature: cpu_temperature > Constants.HIGH_CPU_TEMPERATURE)
            ),
        ),
        salience=3
    )
    def high_cpu_temperature(self):
        self.print_color_message(Constants.HIGH_CPU_TEMPERATURE_MESSAGE, MessageColor.RED.value)
        if is_problem_solved():
            problem_fact = self.get_problem_fact()
            self.modify(problem_fact, is_solved=True)
            self.print_color_message("Problem solved", MessageColor.GREEN.value)

            phone_fact = self.get_phone_fact()
            modified_data = unfreeze(phone_fact['data'])
            modified_data['hardware']['cpu']['cpu_load'] = Constants.NORMAL_CPU_USAGE
            modified_data['hardware']['cpu']['cpu_temperature'] = Constants.NORMAL_CPU_TEMPERATURE
            self.modify(phone_fact, data=modified_data)
        else:
            self.print_color_message("Problem not yet solved", MessageColor.YELLOW.value)

    @Rule(
        OR(
            Problem(
                is_solved=L(False),
                problem_type=L(ProblemsToSolve.OVERHEATING)
            ),
            Problem(
                is_solved=L(False),
                problem_type=L(ProblemsToSolve.SLOW_PERFORMANCE)
            ),
        ),
        salience=2

    )
    def last_resort_cpu_temperature(self):
        self.print_color_message(Constants.LAST_RESORT_OVERHEATING, MessageColor.RED.value)
        if is_problem_solved():
            problem_fact = self.get_problem_fact()
            self.modify(problem_fact, is_solved=True)
            self.print_color_message("Problem solved", MessageColor.GREEN.value)
            phone_fact = self.get_phone_fact()
            modified_data = unfreeze(phone_fact['data'])
            modified_data['hardware']['cpu']['cpu_temperature'] = Constants.NORMAL_CPU_TEMPERATURE
            self.modify(phone_fact, data=modified_data)
        else:
            self.print_color_message("Problem not yet solved", MessageColor.YELLOW.value)


    # STORAGE SHORTAGE
    @Rule(
        OR(
            Problem(
                is_solved=L(False),
                problem_type=L(ProblemsToSolve.STORAGE_SHORTAGE)
            ),
            Problem(
                is_solved=L(False),
                problem_type=L(ProblemsToSolve.SLOW_PERFORMANCE)
            ),
        ),
        salience=2

    )
    def solve_storage_shortage(self):
        self.print_color_message(Constants.STORAGE_SHORTAGE_MESSAGE, MessageColor.RED.value)
        if is_problem_solved():
            problem_fact = self.get_problem_fact()
            self.modify(problem_fact, is_solved=True)
            self.print_color_message("Problem solved", MessageColor.GREEN.value)
            phone_fact = self.get_phone_fact()
            modified_data = unfreeze(phone_fact['data'])
            modified_data['storage']['storage_usage'] = Constants.NORMAL_STORAGE_USAGE
            self.modify(phone_fact, data=modified_data)
        else:
            self.print_color_message("Problem not yet solved", MessageColor.YELLOW.value)

    # SOME ADDITIONAL CHECKS, WHICH DO NOT DEPEND ON SPECIFIC PROBLEM
    @Rule(
        AND(
            Phone(
                data__operating_system__health_status=~L(DeviceHealth.HEALTHY),

            ),
            Phone(
                data__operating_system__health_status=MATCH.current_status

            ),
        ),
        salience=1

    )
    def check_system_health_status(self, current_status):
        self.print_color_message(
            f"Current os health status is {current_status.value}.{Constants.NOT_HEALTHY_SYSTEM_STATUS_MESSAGE}",
            MessageColor.RED.value)
        if is_problem_solved():
            problem_fact = self.get_problem_fact()
            self.modify(problem_fact, is_solved=True)
            self.print_color_message("Problem solved", MessageColor.GREEN.value)
            phone_fact = self.get_phone_fact()
            modified_data = unfreeze(phone_fact['data'])
            modified_data['operating_system']['health_status'] = DeviceHealth.HEALTHY
            self.modify(phone_fact, data=modified_data)


    @Rule(
        Phone(
            data__operating_system__update_status=L(UpdateStatus.UPDATE_AVAILABLE)
        ),
        salience=1
    )
    def check_os_version(self):
        self.print_color_message(Constants.AVAILABLE_UPDATE_MESSAGE, MessageColor.RED.value)
        if is_problem_solved():
            self.print_color_message("Problem solved", MessageColor.GREEN.value)
            problem_fact = self.get_problem_fact()
            phone_fact = self.get_phone_fact()
            self.modify(problem_fact, is_solved=True)

            modified_data = unfreeze(phone_fact['data'])
            modified_data['operating_system']['update_status'] = UpdateStatus.UP_TO_DATE
            self.modify(phone_fact, data=modified_data)

    @Rule(
        Phone(data__hardware__display__display__is_automatic=L(False)
              )
        ,
        salience=1)
    def consider_using_automatic_brightness(self):
        self.print_color_message(Constants.CONSIDER_USING_AUTOMATIC_BRIGHTNESS_MESSAGE, MessageColor.RED.value)
        if is_problem_solved():
            self.print_color_message("Problem solved", MessageColor.GREEN.value)
            problem_fact = self.get_problem_fact()
            phone_fact = self.get_phone_fact()
            self.modify(problem_fact, is_solved=True)

            modified_data = unfreeze(phone_fact['data'])
            modified_data['hardware']['display']['is_automatic'] = True
            self.modify(phone_fact, data=modified_data)

    @Rule(
        AND(
            Problem(
                is_solved=L(False)
            )
        ),
        salience=0

    )
    def last_resort(self):  # will be invoked if other fixes will not help
        self.print_color_message(Constants.LAST_RESORT_MESSAGE, MessageColor.RED.value)
        if is_problem_solved():
            problem_fact = self.get_problem_fact()
            self.modify(problem_fact, is_solved=True)
            self.print_color_message("Problem solved", MessageColor.GREEN.value)
        else:
            self.print_color_message("BETTER LUCK NEXT LIFE", MessageColor.RED.value)
