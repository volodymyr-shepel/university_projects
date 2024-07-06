
from datetime import datetime
import re
import json
import logging
import os 
import sys
import ipaddress


def convert_to_datetime(time_obj):
    return datetime.strptime(time_obj, "[%d/%b/%Y:%H:%M:%S %z]")

class logEntry:
    def __init__(self,ip_address,remote_user_name,user_identity,time,http_request,status_code,size_of_responce,referrer,user_agent):
        self.ip_address = ipaddress.ip_address(ip_address)
        self.remote_user_name = remote_user_name
        self.user_identity = user_identity
        
        self.time = convert_to_datetime(time)
        self.http_request = http_request
        self.status_code = int(status_code) 
        self.size_of_responce = int(size_of_responce) if size_of_responce.isdigit() else "-"
        self.referrer = referrer
        self.user_agent = user_agent.strip().strip('"')
        
    def __str__(self):
        return f"IPv4 address:{self.ip_address}\nRemote User Name:{self.remote_user_name}\nUser Identity:{self.user_identity}\nTime:{self.time}\nHTTP Request:{self.http_request}\nStatus Code:{self.status_code}\nSize of Responce:{self.size_of_responce}\nReferrer:{self.referrer}\nUser Agent:{self.user_agent}"
    
    


def read_log(filename):

    data = {}
    with open(filename) as f:
        content = f.readlines()

    for line in content:
        
        # split the line
        splitted_line = line.split(" ", 3)
        part = re.split(r' "|" ', splitted_line[-1])
        part[2:3] = part[2].split(" ")
        splitted_line[3:10] = part
        # ---------
        ip_address = splitted_line[0]
        
        data.setdefault(ip_address,[])
        data[ip_address].append(logEntry(*splitted_line))
            
        
    return data


# returns the number of request for specific ip_address
def ip_address_count(data,ip_address):
    return len(data[ip_address])


# find list of most active or most passive addresses
def ip_find(data, most_active=True):
    
    if most_active:
        threshold = len(max(list(data.values()),key = lambda entry: len(entry)))
        
    else:
        threshold = len(min(list(data.values()),key = lambda entry: len(entry)))
        
    return [entry for entry in data if len(data[entry]) == threshold]
    
    
    



# find longest request
def longest_request(data):
    max_entry = None
    length = 0
    for arr in data.values():
        for el in arr:
            if len(el.http_request) > length:
                max_entry = el
                length = len(el.http_request)
    return max_entry.ip_address,max_entry.http_request
    

# return list of http request with status code 404
def non_existent(data):
    values = set()
    for arr in data.values():
        for el in arr:
            if el.status_code == 404:
                values.add(el.http_request)
    return values

def requests_sent_from_ip(data,ip_address):
    requests = [el.http_request for el in data[ip_address]]
    for request in requests:
        print(request)

def requests_sent_from_ip_modified(data,ip_address,number_of_lines):
    requests = [el.http_request for el in data[ip_address]]
    for i in range(0,len(requests),number_of_lines):
        chunk = requests[i:i+number_of_lines]
        for entry in chunk:
            print(entry)
        print()
        input("!!! Press the key to continue")
        print()

# my function (task 7)
def find_entries_by_status_code(data,responce_code):
    values = set()
    for arr in data.values():
        for el in arr:
            if el.status_code == responce_code:
                values.add(el)
    return values

def run():
    logger = logging.getLogger(__name__)  

    # set log level
    logger.setLevel(logging.INFO)

    # define file handler and set formatter
    file_handler = logging.FileHandler('logfile.log')
    formatter    = logging.Formatter('%(asctime)s : %(levelname)s : %(name)s : %(message)s')
    file_handler.setFormatter(formatter)

    # add file handler to logger
    logger.addHandler(file_handler)

    logger.info("Start of the application")


    
    # default values
    configuration_file_path = "settings.json"
    filename = "access_log-20230305"
    ip_address = "143.244.50.172"
    logging_level = "DEBUG"
    number_of_lines = 15
    responce_code = 200
    #------------------

    try:
        # load configuration
        with open(configuration_file_path,"r") as f:
            settings = json.load(f)

        values_needed = ["file_name","ip_address","logging_level","number_of_lines","responce_code"]
        if values_needed.sort() != list(settings.keys()).sort():
            raise  ValueError()
        
        filename = settings["file_name"]
        if not os.path.isfile(filename):
            print("Logfile with the given name does not exist")
            logger.error("Logfile with the given name does not exist")
            sys.exit("End of the program")
        
        ip_address = settings["ip_address"]
        logging_level = getattr(logging, settings["logging_level"].upper(), None)
        number_of_lines = settings["number_of_lines"]
        
        
        
        
    except FileNotFoundError:
        logger.info("File not found. Default values will be used")
    except ValueError:
        logger.info(f'Configuration file does not contain all values my application needs.Default values will be used')
    # -------------------
    
    # setting logging level
    logger.setLevel(logging_level)

    
    data = read_log(filename)
    count_ip_address = ip_address_count(data, ip_address)
    extremum = ip_find(data, True)
    find_longest_request = longest_request(data)
    elems_code_404 = non_existent(data)
    # check number of element with code 404
    assert len(elems_code_404) == 157
    
    logger.info("checked whether number of elements with code 404 is equal to 157.Everything is ok")
    #requests_sent_from_ip(data,ip_address)
    requests_sent_from_ip_modified(data,ip_address,10)

    logger.info("End of the program")


if __name__ == "__main__":
    run()
