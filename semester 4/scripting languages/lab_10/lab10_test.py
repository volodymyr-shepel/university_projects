import pytest
from main import *

def test_reqst1():
    with pytest.raises(TypeError):
        reqst2obj(10)

def test_reqst2():
    request_string = "GET / HTTP1.1"
    result = reqst2obj(request_string)
    assert isinstance(result,Request_Line)
# Does the function have to return an object with correctly set attributes to pass this test?

# No  
def test_reqst3():
    
    request_string = "GET / HTTP1.1"
    result = reqst2obj(request_string)
    assert result.request_type == "GET"
    assert result.resource_path == "/"
    assert result.http_type == "HTTP1.1"

# What would happen if you end the function with a statement: return <CLASS>("GET", "/", "HTTP1.1")
# (where <CLASS> is your class developed in task ??)? Will such a function pass this test?

# yes.It would pass

@pytest.mark.parametrize("request_string, expected_request_type, expected_resource_path, expected_http_type",[
        ("GET /pub/WWW/TheProject.html HTTP1.1", "GET", "/pub/WWW/TheProject.html", "HTTP1.1"),
        ("POST /api/data HTTP1.1", "POST", "/api/data", "HTTP1.1"),
        ("PUT /users/123 HTTP2.0", "PUT", "/users/123", "HTTP2.0")
    ])

def test_reqst4(request_string, expected_request_type, expected_resource_path, expected_http_type):  
    result = reqst2obj(request_string)
    assert result.request_type == expected_request_type
    assert result.resource_path == expected_resource_path
    assert result.http_type == expected_http_type


def test_reqst5():
    invalid_request_string = "GET HTTP1.1"
    result = reqst2obj(invalid_request_string)
    assert result is None, "The function did not return None for an invalid request string."


    
def test_reqst6():
    bad_request = "DOWNLOAD /movie.mp4 HTTP1.1" 
    with pytest.raises(BadRequestTypeError):
        reqst2obj(bad_request)
    
def test_reqst7():
    bad_request = "GET / HTTP2.2" 
    with pytest.raises(BadHTTPVersion):
        reqst2obj(bad_request)
    
def test_reqst8():
    request_string = "GET pub/WWW/TheProject.html HTTP1.1"
    with pytest.raises(ValueError):
        reqst2obj(request_string)