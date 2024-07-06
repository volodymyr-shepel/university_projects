from util import read_json_file

DATA_FILE_PATH = "data.json"

DATA = read_json_file(DATA_FILE_PATH)

print(list(DATA.values()))