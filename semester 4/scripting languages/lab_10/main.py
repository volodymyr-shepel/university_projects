class Request_Line:
    def __init__(self,request_type,resource_path,http_type):
        self.request_type = request_type
        self.resource_path = resource_path
        self.http_type = http_type

class BadRequestTypeError(Exception):
    pass

class BadHTTPVersion(Exception):
    pass
      
def reqst2obj(request_string):
    request_methods = [
    'GET',
    'POST',
    'PUT',
    'PATCH',
    'DELETE',
    'HEAD',
    'OPTIONS',
    'TRACE'
]
    http_versions = ["HTTP1.0","HTTP1.1","HTTP2.0"]

    if not isinstance(request_string,str):
        raise TypeError("Invalid argument.Expected the string")
    
    elements = request_string.split()
    if len(elements) != 3:
        return None
    if elements[0] not in request_methods:
        raise BadRequestTypeError
    if not elements[1].startswith("/"):
        raise ValueError
    if elements[2] not in http_versions:
        raise BadHTTPVersion
    
    obj = Request_Line(*elements)
    return obj

def main():
    pass

if __name__ == "__main__":
    main()