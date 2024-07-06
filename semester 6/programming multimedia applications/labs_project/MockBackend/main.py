from flask import Flask, jsonify, request, send_file, send_from_directory
import os
from util import SightDataHelper


app = Flask(__name__)

IMAGE_FOLDER = "images"
VIDEO_FOLDER = "videos"
AUDIO_FOLDER = "audios"
IP_ADDRESS = "192.168.137.2"
PORT = 8080

sight_data_helper = SightDataHelper(ip_address=IP_ADDRESS,
                                    port=PORT,
                                    image_folder=IMAGE_FOLDER,
                                    video_folder=VIDEO_FOLDER,
                                    audio_folder=AUDIO_FOLDER)


@app.route('/sights', methods=['GET'])
def get_sights():
    # Parse query parameters
    page = int(request.args.get('page', 1))
    per_page = int(request.args.get('per_page', 20))
    
    # Return paginated data as JSON
    return jsonify(sight_data_helper.get_sights_preview_data(per_page * (page - 1)))

@app.route('/sight/location', methods=['GET'])
def get_sights_location():
    
    # Return paginated data as JSON
    return jsonify(sight_data_helper.get_sights_location())

@app.route('/sight', methods=['GET'])
def get_sight():
    # Parse query parameters
    index = int(request.args.get('index', 1))
    
    # Return paginated data as JSON
    return jsonify(sight_data_helper.get_sight_detailed_data(index))

@app.route('/images/<image_name>', methods=['GET'])
def get_image(image_name):
    # Get the absolute path of the image
    image_path = os.path.join(IMAGE_FOLDER, image_name)
    print(image_path)
    # Check if the image exists
    if os.path.isfile(image_path):
        # Return the image
        return send_file(image_path, mimetype='image/jpeg')
    else:
        # Return a 404 error if the image does not exist
        return "Image not found", 404

@app.route('/videos/<filename>')
def video(filename):
    video_path = os.path.join(VIDEO_FOLDER, filename)
    print(video_path)
    if os.path.isfile(video_path):
    # Serve video segments
        return send_from_directory(VIDEO_FOLDER, filename)
    else:
        # Return a 404 error if the image does not exist
        return "Video not found", 404

@app.route('/audios/<filename>')
def audio(filename):
    audio_path = os.path.join(AUDIO_FOLDER, filename)
    print(audio_path)
    if os.path.isfile(audio_path):
    # Serve video segments
        return send_from_directory(AUDIO_FOLDER, filename)
    else:
        # Return a 404 error if the image does not exist
        return "Audio not found", 404



if __name__ == '__main__':
    app.run(debug=True, host=IP_ADDRESS, port=PORT)