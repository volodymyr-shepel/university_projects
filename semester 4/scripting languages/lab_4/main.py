from datetime import datetime
import ipaddress


def convert_to_datetime(time_obj):
    return datetime.strptime(time_obj,"%d/%b/%Y:%H:%M:%S")

def read_file(filename):
    with open(filename,"r") as f:
        content = f.readlines()
    return content

def create_entity(line):
    return logEntry(*line.split())

def read_and_create(data):
    return [create_entity(line) for line in data]

def find_elements_by_time(elements,lower_bound,upper_bound):
    if upper_bound < lower_bound:
        print("Upper bound can not be smaller than lower bound")
    return [element.request for element in elements if element.time >= lower_bound and element.time < upper_bound]

class logEntry:
    def __init__(self,ip_address,time,request,code,bytes_send,processing_time):
        self.ip_address = ipaddress.ip_address(ip_address)
        self.request = request
        self.time = convert_to_datetime(time)
        self.code = int(code)
        self.bytes_send = int(bytes_send)
        self.processing_time = int(processing_time)
    def __str__(self):
        return f"IPv4 address:{self.ip_address}\nTime:{self.time}\nCode:{self.code}\nBytes send:{self.bytes_send}\nProcessing_time:{self.processing_time}"
    
    def __repr__(self):
        return f"IPv4 address:{self.ip_address}\nTime:{self.time}\nCode:{self.code}\nBytes send:{self.bytes_send}\nProcessing_time:{self.processing_time}"


def main():
    data = read_file("access_log_lab4.log")
    elems = read_and_create(data)
    lower_bound = convert_to_datetime("18/Oct/2020:00:00:00")
    upper_bound = convert_to_datetime("18/Oct/2021:02:00:00")
    elements_found = find_elements_by_time(elems,lower_bound,upper_bound)
    print(elements_found)
if __name__ == "__main__":
    main()
    
# https://docs.python.org/3/library/ipaddress.html
# https://docs.python.org/3/library/datetime.html