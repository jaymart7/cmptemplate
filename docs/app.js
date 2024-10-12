const webcamElement = document.getElementById('webcam');
const canvasElement = document.getElementById('canvas');
const snapSoundElement = document.getElementById('snapSound');

const captureImage = document.getElementById('capture-image');
captureImage.style.display = "none";

const webcam = new Webcam(webcamElement, 'user', canvasElement, snapSoundElement);

function startWebcam() {
    captureImage.style.display = "block";
    webcam.start()
        .then(result => {
            console.log("Webcam started");
        })
        .catch(err => {
            console.error(err);
        });
}

function takePicture() {
    const picture = webcam.snap();
    const byteArray = convertDataURLToByteArray(picture);

    captureImage.style.display = "none";

    console.log(byteArray)
    webcam.stop();
    return byteArray
}

function convertDataURLToByteArray(dataURL) {
    const byteString = atob(dataURL.split(',')[1]);
    const ab = new ArrayBuffer(byteString.length);
    const ia = new Uint8Array(ab);

    for (let i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }

    return Array.from(ia); // Convert Uint8Array to regular array for easier handling
}

function stopWebcam() {
    webcam.stop();
}