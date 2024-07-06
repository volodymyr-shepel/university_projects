import os
import sys
import re
import logging
from datetime import datetime
import ipaddress
import requests
from bs4 import BeautifulSoup

# The match() function matches a pattern only at the beginning of the string


def convert_to_datetime(time_obj):
    return datetime.strptime(time_obj, "%d/%b/%Y:%H:%M:%S %z")


class logEntry:
    def __init__(
        self, ip_address, time, http_request, status_code,
        size_of_responce, user_agent
    ):
        self.ip_address = ipaddress.ip_address(ip_address)
        self.time = convert_to_datetime(time)
        self.http_request = http_request
        self.status_code = int(status_code) if status_code.isdigit() else "-"
        self.size_of_responce = (
            int(size_of_responce) if size_of_responce.isdigit() else "-"
        )
        self.user_agent = user_agent

    def __str__(self):
        return f"IPv4 address:{self.ip_address}\nTime:{self.time}\n" +\
            f"Request:{self.http_request}\nStatus Code:{self.status_code}\n" +\
            f"Resp_Size:{self.size_of_responce}\nUser Agent:{self.user_agent}"


def create_logger(logging_level):
    default_values = {
        "filename": "logfile.log",
        "format": "%(asctime)s : %(levelname)s : %(name)s : %(message)s",
    }

    logger = logging.getLogger(__name__)
    log_level = getattr(logging, logging_level.upper(), None)
    # set log level
    logger.setLevel(logging_level)

    # define file handler and set formatter
    file_handler = logging.FileHandler(default_values["filename"])
    formatter = logging.Formatter(default_values["format"])
    file_handler.setFormatter(formatter)

    # add file handler to logger
    logger.addHandler(file_handler)

    return logger


def create_display_map(display_settings):
    settings = {}
    for setting in display_settings:
        key, value = setting.split("=")
        settings[key.strip()] = value.strip()
    return settings


def read_config(config_file):
    # default values
    logfile = "access_log-20201025.txt"
    log_level = "INFO"

    if not os.path.exists(config_file):
        sys.exit("Configuration file does not exist")

    with open(config_file) as config_file:
        content = config_file.read()

    # patterns
    display_pattern = re.compile(r"\[Display\]\s+([\w\s=|]+)")
    logfile_pattern = r"\[LogFile\]\s+name\s*=([\w\d\-\.]+)"
    config_pattern = r"\[Config\]\s+debug\s*=(DEBUG|INFO|"\
        r"WARNING|ERROR|CRITICAL)"

    # matches
    logfile_match = re.search(logfile_pattern, content)
    if logfile_match:
        logfile = logfile_match.group(1)

    config_match = re.search(config_pattern, content, re.IGNORECASE)
    if config_match:
        log_level = config_match.group(1)

    logger = create_logger(log_level)

    display_matches = display_pattern.findall(content)
    if display_matches:
        display_matches = display_matches[0].strip().split()

    # config_pattern = re.compile(r'\[Config\]\s+([\w\s=|]+)')
    # this pattern assumes that can be multiple config setting

    # config_matches = config_pattern.findall(content)[0].strip().split()
    # return findall for log_config since there can be multiple settings

    # the logging level was passed,default values:filename,format
    display_settings = create_display_map(display_matches)
    print(logfile)
    return logfile, logger, display_settings


def read_log(log_filename):
    if not os.path.exists(log_filename):
        sys.exit("Log file does not exist")

    with open(log_filename) as log_file:
        content = log_file.readlines()
    return content


def parse_line(line):
    # (?:)  non capturing group
    """
    Using a non-capturing group instead of a capturing group can be useful
    when you want to group
    subexpressions together for purposes of repetition or
    alternation,but you don't need to refer to the matched
    characters later in the regular expression.
    This can help to keep your regular expressions
    shorter, more efficient, and easier to read.
    """

    ipv4_regex = r"((?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}" +\
        r"(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))"
    complete_regex = (
        ipv4_regex +
        r'.+\[(.*)\]\s+"(.*)"\s+(\d{3}|-)\s+(\d+|-)\s+"(?:.*)"\s+"(.*)"'
    )

    mat = re.search(complete_regex, line)
    return mat.groups()


def create_entries(log_lines):
    entries = []
    for line in log_lines:
        entries.append(logEntry(*parse_line(line)))

    return entries
    # return list of objects representing log entries


def requests_from_subnet(entry, entries, number_of_lines):
    student_id = 266617
    mask_length = student_id % 16 + 8
    ipv4_network = ipaddress.IPv4Network(
        f"{entry.ip_address}/{mask_length}", strict=False
    )
    iterator = 0

    for record in entries:
        if if_belongs_to_subnet(record.ip_address, ipv4_network):
            print(record.http_request)
            iterator += 1
        if iterator == number_of_lines:
            input("\n!!! Press the ENTER key to continue")
            print()
            iterator = 0


def if_belongs_to_subnet(ip_address, network_address):
    return ip_address in network_address

def collect_user_agents():
    user_agents = []
    page = requests.get('https://www.useragentstring.com/pages/Chrome/')
    soup = BeautifulSoup(page.text,"html.parser")
    verisons = soup.find_all("li")
    for version in verisons:
        
        agents = version.find_all("a")
        for agent in agents:
            user_agents.append(agent.text)
    return user_agents

def requests_chrome(entries):
    user_agents = collect_user_agents()
    for entry in entries:
        if entry.user_agent in user_agents:
            print(entry.http_request)    
    
    


def bytes_by_request(entries, filter, separator):
    count = 0
    operation_regex = r"^{}".format(filter)
    for entry in entries:
        match = re.search(operation_regex, entry.http_request, re.IGNORECASE)
        if match:
            count += entry.size_of_responce

    print(f"{filter}{separator}{count}")


def main():
    # display_matches,logfile_matches,config_matches
    logfile, logger, display_settings = read_config("lab.config")
    logger.info("Reading the file")
    log_content = read_log(logfile)
    logger.info("Creating entries")
    entries = create_entries(log_content)
    logger.info("Number of bytes by the type of HTTP request")
    #bytes_by_request(
    #    entries, display_settings["filter"], display_settings["separator"])

    # requests_from_subnet(entries[66],entries,5)
    #print(collect_user_agents())
    requests_chrome(entries)
    
    logger.info("End")


if __name__ == "__main__":
    main()

"""
First run:
    main.py:17:80: E501 line too long (87 > 79 characters)
    main.py:22:80: E501 line too long (87 > 79 characters)
    main.py:29:80: E501 line too long (204 > 79 characters)
    main.py:76:80: E501 line too long (81 > 79 characters)
    main.py:94:80: E501 line too long (91 > 79 characters)
    main.py:99:80: E501 line too long (82 > 79 characters)
    main.py:117:80: E501 line too long (151 > 79 characters)
    main.py:118:80: E501 line too long (152 > 79 characters)
    main.py:122:80: E501 line too long (111 > 79 characters)
    main.py:124:80: E501 line too long (85 > 79 characters)
    main.py:191:80: E501 line too long (88 > 79 characters)
Last run: 
    
"""