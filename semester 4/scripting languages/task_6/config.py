import json 
import re 
def set_up():
    settings = {}
    ipv4_regex = r'^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$'
    logging_levels = ["debug","info","warning","error","critical"]
    while True:
        try:
            file_name = input("Enter the filename:")
            if not file_name:
                raise ValueError("File name can not be empty")
            ip_address = input("Enter the ip address:")
            if not re.match(ipv4_regex,ip_address):
                raise ValueError("Provided ipv4 address in not correct")
            logging_level = input("Enter the logging level:")
            if logging_level.lower() not in logging_levels:
                raise ValueError("Incorrect level was provided ")
            number_of_lines = input("Enter the number of lines:")
            if not number_of_lines.isdigit():
                raise ValueError("Number of lines should be an integer greater than 0")             
            #my parameter
            responce_code = input("Enter the responce code:")
            if not number_of_lines.isdigit():
                raise ValueError("Number of lines should be an integer greater than 0")
            break
        except ValueError as e:
            print(e)
    
    settings["file_name"] = file_name
    settings["ip_address"] = ip_address
    settings["logging_level"] = logging_level
    settings["number_of_lines"] = int(number_of_lines)
    settings["responce_code"] = int(responce_code)
    
    json_string = json.dumps(settings,indent = 4)

    with open("settings.json",'w',encoding="utf-8") as file:
        file.write(json_string)


if __name__ == "__main__":
    set_up()