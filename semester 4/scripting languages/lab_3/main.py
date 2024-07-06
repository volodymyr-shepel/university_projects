import sys
from statistics import mean
import logging

logging.basicConfig(
    format='%(levelname)s - %(asctime)s - %(message)s', level=logging.DEBUG)


def read_log():
    elements = []
    number_of_lines = 0

    for line in sys.stdin:
        number_of_lines += 1
        line_splitted = line.split()
        if len(line_splitted) != 0:
            line_splitted[1:] = [int(el) for el in line_splitted[1:]]
            elements.append(tuple(line_splitted))
    logging.debug(f"Number of lines read is {number_of_lines}")
    logging.debug(f"Number of entries in list: {len(elements)}")

    return elements


def successful_reads(elements):
    logging.info(f"Number of entries in list: {len(elements)}")
    elements_200 = [el for el in elements if el[1] in range(200, 300)]
    logging.debug(f"Number of entries with code 2xx:{len(elements_200)}")
    return


def failed_reads(elements):
    elements_400 = [el for el in elements if el[1] in range(400, 500)]
    elements_500 = [el for el in elements if el[1] in range(500, 600)]
    merged = elements_400 + elements_500
    logging.debug(f"Number of entries with code 4xx:{len(elements_400)}")
    logging.debug(f"Number of entries with code 5xx:{len(elements_500)}")
    return merged


def html_entries(elements):
    return [el for el in elements if el[0].endswith(".html")]


def print_html_entries(html_elements):
    for el in html_elements:
        print(el)


def run():
    logging.info("Start")
    elements = read_log()
    successful_elements = successful_reads(elements=elements)
    failed_elements = failed_reads(elements)
    html_elements = html_entries(elements)

    print_html_entries(html_elements)
    logging.info("Finish")


if __name__ == "__main__":

    run()

"""*args  I also noticed that if you run the script by clicking on the debug button, then the arguments are not passed. However, using Run ->
Start Debugging (or its shortcut F5) passed the arguments successfully.
"""
