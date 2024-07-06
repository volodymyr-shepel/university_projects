from enum import Enum


class ConnectionStatus(Enum):
    TURNED_OFF = 1
    TURNED_ON = 2
    CONNECTED = 3


class DeviceHealth(Enum):
    HEALTHY = "Healthy"
    WARNING = "Warning"
    CRITICAL = "Critical"


class BluetoothVersion(Enum):
    BLUETOOTH_1_0 = 1.0
    BLUETOOTH_1_1 = 1.1
    BLUETOOTH_1_2 = 1.2
    BLUETOOTH_2_0 = 2.0
    BLUETOOTH_2_1 = 2.1
    BLUETOOTH_3_0 = 3.0
    BLUETOOTH_4_0 = 4.0
    BLUETOOTH_4_1 = 4.1
    BLUETOOTH_4_2 = 4.2
    BLUETOOTH_5_0 = 5.0
    BLUETOOTH_5_1 = 5.1
    BLUETOOTH_5_2 = 5.2
    BLUETOOTH_5_3 = 5.3


class IOSVersion(Enum):
    IOS_1 = 1
    IOS_2 = 2
    IOS_3 = 3
    IOS_4 = 4
    IOS_5 = 5
    IOS_6 = 6
    IOS_7 = 7
    IOS_8 = 8
    IOS_9 = 9
    IOS_10 = 10
    IOS_11 = 11
    IOS_12 = 12
    IOS_13 = 13
    IOS_14 = 14


class UpdateStatus(Enum):
    UP_TO_DATE = "Up to date"
    UPDATE_AVAILABLE = "Update available"


class ProblemsToSolve(Enum):
    OVERHEATING = 1
    DRAINING_BATTERY = 2
    SLOW_PERFORMANCE = 3
    STORAGE_SHORTAGE = 4
    # APP_CRASH = 5
    # WI_FI_CONNECTIVITY_PROBLEMS = 6
    # BLUETOOTH_CONNECTIVITY_PROBLEMS = 7
    # POOR_CALL_QUALITY = 8
    # SLOW_CELLULAR_CONNECTION_SPEED = 9


class MessageColor(Enum):
    RED = '31'
    GREEN = '32'
    YELLOW = '33'
