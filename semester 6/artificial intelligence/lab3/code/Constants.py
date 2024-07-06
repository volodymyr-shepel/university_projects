
class Constants:
    # Constants
    EDGE_CPU_USAGE = 80
    NORMAL_CPU_USAGE = 50

    POOR_BATTERY_STATE = 70
    NORMAL_BATTERY_STATE = 100

    HIGH_CPU_TEMPERATURE = 75
    NORMAL_CPU_TEMPERATURE = 50

    NORMAL_STORAGE_USAGE = 30



    # MESSAGES
    HIGH_CPU_USAGE_MESSAGE = ("Your CPU LOAD IS TOO HIGH. IT MIGHT BE THE REASON OF PROBLEM.If the phone's CPU load is \n"
                              "consistently high, it suggests that there might be several apps \n"
                              "running in the background or a single app consuming excessive CPU resources. \n"
                              "Check for apps with high CPU usage or excessive background activity. Close \n"
                              "unnecessary apps or consider uninstalling those that are resource-intensive")

    DEGRADED_BATTERY_HEALTH = (
        "The phone's battery health is degraded, it may not hold a charge as effectively as before.\n"
        "Consider replacing the battery if it is old or significantly degraded.")

    NOT_HEALTHY_SYSTEM_STATUS_MESSAGE = ("there are software issues or bugs in the operating system, it can lead to \n"
                                         "excessive battery drain due to inefficient resource management.Consider \n"
                                         "restarting the phone or performing a software update/reset to resolve any \n"
                                         "software-related issues.")

    LAST_RESORT_OVERHEATING = (
        "Urgent Attention Required: Your device is experiencing overheating issues. "
        "Please power off your device immediately and allow it to cool down. "
        "Repeated overheating can lead to serious damage. "
        "For further assistance, please contact our technical support."
    )

    LAST_RESORT_MESSAGE = (
        "Attention: Please consider restarting your device. "
        "Your issue has been duly reported. "
        "For any further assistance or inquiries, "
        "kindly contact our support team."
    )

    HIGH_CPU_TEMPERATURE_MESSAGE = "The CPU temperature is too high. Please try to blow on it"

    AVAILABLE_UPDATE_MESSAGE = ("There is an update available. Please consider updating your system software")

    CONSIDER_USING_AUTOMATIC_BRIGHTNESS_MESSAGE = "Consider using automatic brightness"

    NORMAL_CPU_TEMPERATURE_MESSAGE = "The cpu temperature is normal one. "

    STORAGE_SHORTAGE_MESSAGE = (
        "Consider taking the following steps to address storage shortage:\n"
        "- Uninstall unused applications.\n"
        "- Clear cache and temporary files.\n"
        "- Move media files such as photos, audio, and videos to external storage or cloud storage solutions.\n"
        "These actions can help free up space on your device and improve performance."
    )


