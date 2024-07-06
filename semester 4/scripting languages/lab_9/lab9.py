import smtplib
import json
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from datetime import datetime
import argparse
import requests 
from bs4 import BeautifulSoup

def send_mail(receivers,mail_content):
    sender_name = "Volodymyr Shepel"
    current_time = datetime.now()
    date_string = current_time.strftime("%H:%M %Y-%m-%d")
    
    with open("login_data.json","r") as f:
        login_data = json.load(f)
    
    s = smtplib.SMTP('smtp.gmail.com', 587)
    s.ehlo()
    s.starttls()
    s.login(login_data["login"], login_data["password"])
    message = MIMEText(mail_content)
    email = login_data["login"]
    message['From'] = f"{sender_name} <{email}>"
    
    message['To'] = ', '.join(receivers)
    message['Subject'] = f'Lab message sent on {date_string}'   #The subject line
    

    
    s.sendmail(login_data["login"], receivers, message.as_string())
    s.quit()
    print("Mail sent")

def use_web_api(amount): # some default number of facts
    request = f"https://cat-fact.herokuapp.com/facts/random?animal_type=cat&amount={amount}" 
    r = requests.get(request)
    
    data = r.json()
    
    for fact in data:
        #formatted_fact = json.dumps(fact, indent=4)
        #print(formatted_fact)
        print(fact["text"])
def parse_pwr(letter):
    # What if there is no researchers? I will print only the header and that's it
    iterator = 1
    print(f"The list of researchers - {letter.upper()}")
    while True:
        url = f"https://wit.pwr.edu.pl/wydzial/struktura-organizacyjna/pracownicy/page{iterator}.html?letter={letter.upper()}"
        r = requests.get(url) 
        if r.status_code == 200:
            soup = BeautifulSoup(r.text,"html.parser")
            researchers = soup.find_all(class_ = "col-text text-content")
            for researcher in researchers:
                print(researcher.text)
            iterator += 1
        else: 
            break
    
def main():
    args = parser.parse_args()
    receivers = ["wojciech.thomas@pwr.edu.pl"]
    
    if args.mail:
        send_mail(receivers,args.mail)
    
    if args.cat_facts:
        use_web_api(args.cat_facts)
    
    if args.teachers:
        if len(args.teachers) == 1 and args.teachers.isalpha(): 
            parse_pwr(args.teachers)
        else:
            print("The input is not a letter.")
    
        
if __name__ == "__main__":
    
    parser = argparse.ArgumentParser(description="Send an email")   
    parser.add_argument('--cat-facts', type=int, help='Number of cat facts')
    parser.add_argument("--mail",type = str,help = "Content of the message")
    parser.add_argument('--teachers', type=str, help='First last of the teacher\'s last name')
    
    main()
    
    # python lab9.py --mail "My message to the teacher"
    # python lab9.py --cat-facts 5
    # python lab9.py --teachers E
