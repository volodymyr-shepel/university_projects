import sys
from statistics import mean
import logging

logging.basicConfig(format='%(levelname)s - %(asctime)s - %(message)s', level=logging.INFO)


class Entity:
    def __init__(self,path,result_code,number_of_bytes,processing_time):
        self._path = path
        self._result_code = int(result_code)
        self._number_of_bytes = int(number_of_bytes)
        self._processing_time = int(processing_time)
        self._if_exist = int(result_code) not in [404,500]
    
    
    
    @property
    def path(self):
        return self._path if self.if_exist else f"!{self._path}"
    @property
    def result_code(self):
        return self._result_code
    
    @property
    def number_of_bytes(self):
        return self._number_of_bytes
    
    @property
    def processing_time(self):
        return self._processing_time
    
    @property
    def if_exist(self):
        return self._if_exist
    
    

def main():
    logging.info("Start")
    elements = []
    for line in sys.stdin:
        line_splitted = line.split()
        elements.append(Entity(*line_splitted))
        
    print("Largest element:") 
    # largest element
    largest_element = sorted(elements, key=lambda x: x.number_of_bytes)[0]
    print(f"Path:{largest_element.path}\nProcessing time:{largest_element.processing_time}")
    print("----------------------------------")
    # number_of_failed_requests
    logging.debug("Number of failed requests should be equal to 2")
    print(f"Number of failed_request:{len(elements) - sum([el.if_exist for el in elements])}")
    
    #total number of bytes
    total_number_of_bytes = sum([el.number_of_bytes for el in elements])
    print(f"Total number of bytes : {total_number_of_bytes} bytes")
    
   #total number of kilobytes
   
    print(f"Total number of kilobytes : {round(total_number_of_bytes / 1024,3)} kilobytes")
    
    #average processing time
    logging.debug("Average processing time should be equal to 2.75")
    avg_time = mean([el.processing_time for el in elements])
    print(f"Average processing time : {avg_time} ms")
    
    logging.info("Finish")
    
if __name__ == "__main__":
    main()
    
"""*args  I also noticed that if you run the script by clicking on the debug button, then the arguments are not passed. However, using Run ->
Start Debugging (or its shortcut F5) passed the arguments successfully.
"""