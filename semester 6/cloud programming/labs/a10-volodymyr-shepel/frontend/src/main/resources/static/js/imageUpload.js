// src/main/resources/static/js/imageUpload.js

document.addEventListener('DOMContentLoaded', function() {
    const currentImage = document.getElementById('currentImage');
    const s3Bucket = window.s3Bucket;

    currentImage.src = `https://${s3Bucket}.s3.amazonaws.com/${window.hashString(localStorage.getItem("playerName"))}.jpg`;

    document.getElementById('editImageButton').addEventListener('click', function() {
        document.getElementById('imageInput').click();
    });

    document.getElementById('imageInput').addEventListener('change', function(event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                currentImage.src = e.target.result;
                document.getElementById('imageForm').style.display = 'block';
            };
            reader.readAsDataURL(file);
        }
    });

    document.getElementById('cancelButton').addEventListener('click', function() {
        location.reload();
    });

    document.getElementById('imageForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const fileInput = document.getElementById('imageInput');
        const file = fileInput.files[0];

        if (file) {
            const formData = new FormData();
            formData.append('file', file);

            const playerName = localStorage.getItem("playerName");
            const filename = `${window.hashString(playerName)}.${file.name.split('.').pop()}`;
            formData.append('filename', filename);

            fetch(`https://${ipAddress}/api/v1/s3/upload`, {
                method: 'POST',
                body: formData
            })
                .then(response => response.text())
                .then(result => {
                    console.log(result);
                    location.reload();
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }
    });
});
