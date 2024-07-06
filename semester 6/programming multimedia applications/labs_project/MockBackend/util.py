import json

def read_json_file(file_path):
    """
    Reads a JSON file from the given file path and returns the parsed data.

    :param file_path: The path to the JSON file.
    :return: The data parsed from the JSON file as a Python dictionary.
    :raises: FileNotFoundError if the file does not exist.
             json.JSONDecodeError if the file content is not valid JSON.
    """
    try:
        with open(file_path, 'r', encoding='utf-8') as file:
            data = json.load(file)
            return data
    except FileNotFoundError as e:
        print(f"Error: The file at {file_path} was not found.")
        raise e
    except json.JSONDecodeError as e:
        print(f"Error: Failed to decode JSON from the file at {file_path}.")
        raise e
    
class SightDataHelper: 
    PAGE_SIZE = 20
    
    def __init__(self, ip_address: str, port: str, image_folder: str, video_folder: str, audio_folder: str):
        self.IP_ADDRESS = ip_address
        self.PORT = port
        self.IMAGE_FOLDER: str = image_folder
        self.VIDEO_FOLDER: str = video_folder
        self.AUDIO_FOLDER: str = audio_folder

    def get_sights_preview_data(self,x = 0):

        return [
            {
                "id": 1 + x,
                "name": "Wrocław Market Square",
                "description": "The Market Square is the heart of Wrocław, filled with historic buildings and vibrant energy.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/1_1.jpg",
                
                
            },
            {
                "id": 2 + x,
                "name": "Wrocław Cathedral",
                "description": "The Cathedral of St. John the Baptist is an iconic landmark in Wrocław, known for its stunning Gothic architecture.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/2_1.jpg",
                
                
            },
            {
                "id": 3 + x,
                "name": "Ostrów Tumski",
                "description": "Ostrów Tumski is the oldest part of Wrocław, featuring historic churches and charming cobblestone streets.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/3_1.jpg",
                
                
            },
            {
                "id": 4 + x,
                "name": "Centennial Hall",
                "description": "A UNESCO World Heritage Site, Centennial Hall is an architectural marvel and a symbol of Wrocław's resilience.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/4_1.jpg",
                
                
            },
            {
                "id": 5 + x,
                "name": "Racławice Panorama",
                "description": "Experience a piece of history at the Racławice Panorama, an enormous 19th-century painting depicting the Battle of Racławice.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/5_1.jpg",
            },
                
            {
                "id": 6 + x,
                "name": "Wrocław University",
                "description": "Wrocław University is one of Poland's oldest and most prestigious universities, located in the heart of the city.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/6_1.jpg",
                
                
            },
            {
                "id": 7 + x,
                "name": "Japanese Garden",
                "description": "Escape the hustle and bustle of the city at the serene Japanese Garden, a tranquil oasis of nature and beauty.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/7_1.jpg",
                
                
            },
            {
                "id": 8 + x,
                "name": "Wrocław Zoo",
                "description": "Explore the wonders of the animal kingdom at Wrocław Zoo, home to a diverse array of species from around the world.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/8_1.jpg",
                
                
            },
            {
                "id": 9 + x,
                "name": "Wrocław Multimedia Fountain",
                "description": "Marvel at the spectacular water and light displays at the Wrocław Multimedia Fountain, a must-see attraction for visitors of all ages.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/9_1.jpg",
                
               
            },
            {
                "id": 10 + x,
                "name": "Wrocław Aquapark",
                "description": "Cool off and have fun at the Wrocław Aquapark, featuring thrilling water slides, relaxing pools, and more.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/10_1.jpg",
                
                
            },
            {
                "id": 11 + x,
                "name": "Museum of illusions Wroclaw",
                "description": "The Museum of illusions Wroclaw is a fascinating attraction that plays with perception and reality. Visitors can explore various optical illusions, holograms, and interactive exhibits that challenge the mind and delight the senses.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/11_1.jpg",
                
                
            },
            {
                "id": 12 + x,
                "name": "Wrocław Opera",
                "description": "Experience world-class opera and ballet performances at the Wrocław Opera, housed in a magnificent historic building.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/12_1.jpg",
                
                
            },
            {
                "id": 13 + x,
                "name": "Wrocław Old Town Hall",
                "description": "Step back in time at the Wrocław Old Town Hall, a beautiful Gothic building with a rich history.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/13_1.jpg",
                
                
            },
            {
                "id": 14 + x,
                "name": "Wrocław Botanical Garden",
                "description": "Discover the beauty of nature at the Wrocław Botanical Garden.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/14_1.jpg",
                
                
            },
            {
                "id": 15 + x,
                "name": "Wrocław Szczytnicki Park",
                "description": "Relax and unwind in the picturesque surroundings of Wrocław Szczytnicki Park.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/15_1.jpg",
                
            },
            {
                "id": 16 + x,
                "name": "National Museum in Wrocław",
                "description": "Immerse yourself in art and culture at the National Museum in Wrocław, home to a vast collection of works spanning centuries.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/16_1.jpg",
                
            },
            {
                "id": 17 + x,
                "name": "Wrocław Contemporary Museum",
                "description": "Experience cutting-edge contemporary art at the Wrocław Contemporary Museum, housed in a former air-raid shelter.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/17_1.jpg",
                
            },
            {
                "id": 18 + x,
                "name": "Wrocław Palace",
                "description": "Admire the grandeur of Wrocław Palace, a magnificent Baroque residence surrounded by beautiful gardens.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/18_1.jpg",
                
            },
            {
                "id": 19 + x,
                "name": "Wrocław Hydropolis",
                "description": "Dive into the fascinating world of water at Wrocław Hydropolis, an interactive science center dedicated to the wonders of H2O.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/19_1.jpg",
                
            },
            {
                "id": 20 + x,
                "name": "Wrocław Puppet Theater",
                "description": "Experience the magic of puppetry at the Wrocław Puppet Theater, where imaginative performances delight audiences of all ages.",
                "imageUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/20_1.jpg",
            }
        ]
    
    def get_sight_detailed_data(self,index):
        x = index - index % 21
        data = {
            str(1 + x): {
                "id": 1 + x,
                "name": "Wrocław Market Square",
                "description": "The Market Square is the heart of Wrocław, filled with historic buildings and vibrant energy.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                "imageUrl": [
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/1_1.jpg",
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/1_2.jpg"
                ],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/1.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/1.mp3",
                "latitude": 51.1106,
                "longitude": 17.0332,
            },
            str(2 + x): {
                "id": 2 + x,
                "name": "Wrocław Cathedral",
                "description": "The Cathedral of St. John the Baptist is an iconic landmark in Wrocław, known for its stunning Gothic architecture.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                "imageUrl": [
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/2_1.jpg",
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/2_2.jpg"
                ],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/2.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/2.mp3",
		"latitude": 51.114167,
                "longitude": 17.046111,
            },
            str(3 + x): {
                "id": 3 + x,
                "name": "Ostrów Tumski",
                "description": "Ostrów Tumski is the oldest part of Wrocław, featuring historic churches and charming cobblestone streets.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/3_1.jpg",
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/3_2.jpg"
                ],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/3.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/3.mp3",
		"latitude": 51.1145,
                "longitude": 17.0467,
            },
            str(4 + x): {
                "id": 4 + x,
                "name": "Centennial Hall",
                "description": "A UNESCO World Heritage Site, Centennial Hall is an architectural marvel and a symbol of Wrocław's resilience.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/4_1.jpg",
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/4_2.jpg"
                ],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/4.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/4.mp3",
		"latitude": 51.1069,
                "longitude": 17.0773,
            },
            str(5 + x): {
                "id": 5 + x,
                "name": "Racławice Panorama",
                "description": "Experience a piece of history at the Racławice Panorama, an enormous 19th-century painting depicting the Battle of Racławice.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/5_1.jpg",
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/5_2.jpg"
                ],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/5.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/5.mp3",
		"latitude": 51.1101,
                "longitude": 17.044,
            },
            str(6 + x): {
                "id": 6 + x,
                "name": "Wrocław University",
                "description": "Wrocław University is one of Poland's oldest and most prestigious universities, located in the heart of the city.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/6_1.jpg",
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/6_2.jpg"
                ],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/6.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/5.mp3",
		"latitude": 51.1052,
                "longitude": 17.055,
            },
            str(7 + x): {
                "id": 7 + x,
                "name": "Japanese Garden",
                "description": "Escape the hustle and bustle of the city at the serene Japanese Garden, a tranquil oasis of nature and beauty.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/7_1.jpg",
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/7_2.jpg"
                ],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/1.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/1.mp3",
		"latitude": 51.110,
                "longitude": 17.079,
            },
            str(8 + x): {
                "id": 8 + x,
                "name": "Wrocław Zoo",
                "description": "Explore the wonders of the animal kingdom at Wrocław Zoo, home to a diverse array of species from around the world.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/8_1.jpg",
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/8_2.jpg"
                ],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/2.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/2.mp3",
		"latitude": 51.104,
                "longitude": 17.074,
            },
            str(9 + x): {
                "id": 9 + x,
                "name": "Wrocław Multimedia Fountain",
                "description": "Marvel at the spectacular water and light displays at the Wrocław Multimedia Fountain, a must-see attraction for visitors of all ages.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/9_1.jpg",
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/9_2.jpg"
                ],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/3.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/3.mp3",
		"latitude": 51.108,
                "longitude": 17.078,
            },
            str(10 + x): {
                "id": 10 + x,
                "name": "Wrocław Aquapark",
                "description": "Cool off and have fun at the Wrocław Aquapark, featuring thrilling water slides, relaxing pools, and more.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/10_1.jpg",
                    f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/10_2.jpg"
                ],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/4.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/4.mp3",
		"latitude": 51.090,
                "longitude": 17.032,
            },
            str(11 + x): {
                "id": 11 + x,
                "name": "Museum of illusions Wroclaw",
                "description": "The Museum of illusions Wroclaw is a fascinating attraction that plays with perception and reality. Visitors can explore various optical illusions, holograms, and interactive exhibits that challenge the mind and delight the senses.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/11_1.jpg", f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/11_2.jpg"],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/5.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/5.mp3",
		"latitude": 51.1153,
                "longitude": 17.0398,
            },
            str(12 + x): {
                "id": 12 + x,
                "name": "Wrocław Opera",
                "description": "Experience world-class opera and ballet performances at the Wrocław Opera, housed in a magnificent historic building.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/12_1.jpg", f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/12_2.jpg"],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/6.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/5.mp3",
		"latitude": 51.1056,
                "longitude": 17.0311,
            },
            str(13 + x): {
                "id": 13 + x,
                "name": "Wrocław Old Town Hall",
                "description": "Step back in time at the Wrocław Old Town Hall, a beautiful Gothic building with a rich history.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/13_1.jpg", f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/13_2.jpg"],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/1.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/1.mp3",
		"latitude": 51.1097,
                "longitude": 17.0316,
            },
            str(14 + x): {
                "id": 14 + x,
                "name": "Wrocław Botanical Garden",
                "description": "Discover the beauty of nature at the Wrocław Botanical Garden.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/14_1.jpg", f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/14_2.jpg"],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/2.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/2.mp3",
		"latitude": 51.1078,
                "longitude": 17.0385,
            },
            str(15 + x): {
                "id": 15 + x,
                "name": "Wrocław Szczytnicki Park",
                "description": "Relax and unwind in the picturesque surroundings of Wrocław Szczytnicki Park.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/15_1.jpg", f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/15_2.jpg"],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/3.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/3.mp3",
		"latitude": 51.1083,
                "longitude": 17.0756,
            },
            str(16 + x): {
                "id": 16 + x,
                "name": "National Museum in Wrocław",
                "description": "Immerse yourself in art and culture at the National Museum in Wrocław, home to a vast collection of works spanning centuries.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/16_1.jpg", f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/16_2.jpg"],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/4.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/4.mp3",
		"latitude": 51.11,
                "longitude": 17.0472,
            },
            str(17 + x): {
                "id": 17 + x,
                "name": "Wrocław Contemporary Museum",
                "description": "Experience cutting-edge contemporary art at the Wrocław Contemporary Museum, housed in a former air-raid shelter.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/17_1.jpg", f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/17_2.jpg"],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/5.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/5.mp3",
		"latitude": 51.1132,
                "longitude": 17.0050,
            },
            str(18 + x): {
                "id": 18 + x,
                "name": "Wrocław Palace",
                "description": "Admire the grandeur of Wrocław Palace, a magnificent Baroque residence surrounded by beautiful gardens.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/18_1.jpg", f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/18_2.jpg"],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/6.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/5.mp3",
		"latitude": 51.1044,
                "longitude": 17.0238,
            },
            str(19 + x): {
                "id": 19 + x,
                "name": "Wrocław Hydropolis",
                "description": "Dive into the fascinating world of water at Wrocław Hydropolis, an interactive science center dedicated to the wonders of H2O.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/19_1.jpg", f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/19_2.jpg"],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/5.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/5.mp3",
		"latitude": 51.1043,
                "longitude": 17.0566,
            },
            str(20 + x): {
                "id": 20 + x,
                "name": "Wrocław Puppet Theater",
                "description": "Experience the magic of puppetry at the Wrocław Puppet Theater, where imaginative performances delight audiences of all ages.",
                "longDescription" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut gravida et turpis eget accumsan. Aliquam accumsan dictum eros, vel accumsan neque accumsan sed. Praesent volutpat, purus feugiat consectetur commodo, mi ex pretium erat, eget viverra nunc lectus quis orci. Vestibulum sit amet lectus sed sapien porta consequat at non sem.",
                
                
                "imageUrl": [f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/20_1.jpg", f"http://{self.IP_ADDRESS}:{self.PORT}/{self.IMAGE_FOLDER}/20_2.jpg"],
                "videoUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.VIDEO_FOLDER}/6.mp4",
                "audioUrl": f"http://{self.IP_ADDRESS}:{self.PORT}/{self.AUDIO_FOLDER}/5.mp3",
		"latitude": 51.1051,
                "longitude": 17.033,
            }
        }
        return data[str(index)]
    
    def get_sights_location(self):
        return [
            {
                "id": 1,
                "name": "Wrocław Market Square",
                "latitude": 51.1106,
                "longitude": 17.0332,
            },
            {
                "id": 2,
                "name": "Wrocław Cathedral",
                "latitude": 51.114167,
                "longitude": 17.046111,
            },
            {
                "id": 3,
                "name": "Ostrów Tumski",
                "latitude": 51.1145,
                "longitude": 17.0467,
            },
            {
                "id": 4,
                "name": "Centennial Hall",
                "latitude": 51.1069,
                "longitude": 17.0773,
                
                
                
            },
            {
                "id": 5,
                "name": "Racławice Panorama",
                "latitude": 51.1101,
                "longitude": 17.044,
                
            },
                
            {
                "id": 6,
                "name": "Wrocław University",
                "latitude": 51.1052,
                "longitude": 17.055,
                
                
                
            },
            {
                "id": 7,
                "name": "Japanese Garden",
                "latitude": 51.110,
                "longitude": 17.079,
                
                
                
            },
            {
                "id": 8,
                "name": "Wrocław Zoo",
                "latitude": 51.104,
                "longitude": 17.074,
                
                
                
            },
            {
                "id": 9,
                "name": "Wrocław Multimedia Fountain",
                "latitude": 51.108,
                "longitude": 17.078,
                
                
               
            },
            {
                "id": 10,
                "name": "Wrocław Aquapark",
                "latitude": 51.090,
                "longitude": 17.032,
                
                
                
                
            },
            {
                "id": 11,
                "name": "Museum of illusions Wroclaw",
                "latitude": 51.1153,
                "longitude": 17.0398,
                
                
                
            },
            {
                "id": 12,
                "name": "Wrocław Opera",
                "latitude": 51.1056,
                "longitude": 17.0311,
                
                
                
            },
            {
                "id": 13,
                "name": "Wrocław Old Town Hall",
                "latitude": 51.1097,
                "longitude": 17.0316,
                
                
                
            },
            {
                "id": 14,
                "name": "Wrocław Botanical Garden",
                "latitude": 51.1078,
                "longitude": 17.0385,
               
                
                
            },
            {
                "id": 15,
                "name": "Wrocław Szczytnicki Park",
                "latitude": 51.1083,
                "longitude": 17.0756,
                
                
            },
            {
                "id": 16,
                "name": "National Museum in Wrocław",
                "latitude": 51.11,
                "longitude": 17.0472,
                
                
            },
            {
                "id": 17,
                "name": "Wrocław Contemporary Museum",
                "latitude": 51.1132,
                "longitude": 17.0050,
                
                
            },
            {
                "id": 18,
                "name": "Wrocław Palace",
                "latitude": 51.1044,
                "longitude": 17.0238,
                
                
            },
            {
                "id": 19,
                "name": "Wrocław Hydropolis",
                "latitude": 51.1043,
                "longitude": 17.0566,
            },
            {
                "id": 20,
                "name": "Wrocław Puppet Theater",
                "latitude": 51.1051,
                "longitude": 17.033,
            }
        ]

    
