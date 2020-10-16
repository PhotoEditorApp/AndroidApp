# Android App
## Android client for photo editor app

The easiest way to test that android app with
connection to server-side, without deploying:

1. Install Android Studio and Android Device Emulator, if 
you need it

1. Clone those two repositories, if you have an opportunity -
on separate computers (it's optional, but launched local server and android studio, mobile device emulator can be resource-intensive) 

1. Run WebApp on **localhost:{port_number}**

1. Install **ngrok** (https://ngrok.com) to be able to create temporary
direct access to your localhost from outer networks

1. Execute command in command prompt 
`ngrok.exe http {port_number}`

1. Copy printed link, which allows connecting to localhost 

1. Open AndroidApp source code and go to `library\src\main\java\com\netcracker_study_autumn_2020\library\network\NetworkHelper.java`

1. Change **API_ADDRESS** constant to copied link

1. Launch AndroidApp on the emulator or mobile device which have access to the Internet

**Notice:** every time you running ngrok it generates new link 