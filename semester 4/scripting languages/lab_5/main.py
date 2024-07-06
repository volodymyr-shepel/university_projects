
from datetime import datetime
import re


def convert_to_datetime(time_obj):
    return datetime.strptime(time_obj, "[%d/%b/%Y:%H:%M:%S %z]")

# there is no field which can uniquely identify the line


def read_log(filename):

    data = {}
    with open(filename) as f:
        content = f.readlines()

    for index in range(len(content)):
        # I don't like this one :(

        splitted_line = content[index].split(" ", 3)
        part = re.split(r' "|" ', splitted_line[-1])
        part[2:3] = part[2].split(" ")
        splitted_line[3:10] = part
        # ---------------------------------

        data[index] = {}
        data[index]["ip_address"] = splitted_line[0]
        data[index]["remote_user_name"] = splitted_line[1]
        data[index]["user_identity"] = splitted_line[2]
        data[index]["date_and_time"] = convert_to_datetime(
            splitted_line[3])
        data[index]["http_request"] = splitted_line[4]
        data[index]["status_code"] = int(splitted_line[5])
        data[index]["size_of_responce"] = splitted_line[6]
        data[index]["referrer"] = splitted_line[7]
        data[index]["user_agent"] = splitted_line[8].strip().strip('"')

    return data


def ip_requests_number(data):
    counts = {}
    for value in data.values():
        counts.setdefault(value["ip_address"], 0)
        counts[value["ip_address"]] += 1

    return counts


def ip_address_count(counts, ip_address):
    return counts[ip_address]


def ip_find(counts, most_active=True):
    ips = []
    if most_active:
        threshold = max(counts.values())
    else:
        threshold = min(counts.values())

    for k, v in counts.items():
        if v == threshold:
            ips.append(k)
    return ips


def longest_request(data):
    longest_request = ""
    ip_address = None
    for k, v in data.items():
        if len(v["http_request"]) > len(longest_request):
            longest_request = v["http_request"]
            ip_address = v["ip_address"]
    return longest_request, ip_address


def non_existent(data):
    values = set()
    for k, v in data.items():
        if v["status_code"] == 404:
            values.add(v["http_request"])
    return values


def run():
    data = read_log("access_log-20230305")
    counts = ip_requests_number(data)
    count_ip_address = ip_address_count(counts, "45.83.64.107")
    extremum = ip_find(counts, False)
    find_longest_request = longest_request(data)
    elems_code_404 = non_existent(data)

    # print(elems_code_404)


if __name__ == "__main__":
    run()
