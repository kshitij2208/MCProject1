CSE 535 - Mobile Computing - Assignment 1

STEPS TO RUN THE APP:

1. The app has been created using Android Studio, so the project can be directly imported into Android Studio.
2. Once imported, click on RUN -> Run 'app'. The app would start on emulator or an USB Device if connected.(Recommend using emulator for proper functioning of camera such as 5 second timeout)
3. Once the app is installed wait for few seconds for downloading video and allow requested permissions.
4. The app can also be installed using APK in /apk directory. 
5. The videos used to learn any gesture are stored in phone locally in /MCProject1 directory.

UPLOAD FUNCTIONALITY:

1. I have hosted a PHP + Apache server on a EC2 instance in Amazon Web Service. Since its on AWS the server url (http://18.217.240.188:80/uploads) is accessible from any network.
2. The app already includes the our server URL so building the app and clicking on the upload will directly upload it to our server and no changes are required.
3. Once the video is uploaded the screen displays the url of uploaded video and by clicking it, it will show the uploaded video on your browser.
