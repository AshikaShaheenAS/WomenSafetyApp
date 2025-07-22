# Women Safety App

This is a **Java-based console application** designed for women's safety. The app allows users to:

- Add emergency contacts
- View the list of emergency contacts
- Edit or delete contacts
- Send safety alert messages (SMS) to all listed contacts via **Twilio API**

## Features

- Store and manage multiple emergency contacts
- Send instant safety alert messages
- Persistent contact storage using a file system (`contacts.txt`)

## Technologies Used

- **Java**
- **Twilio API** (for sending SMS)
- **File I/O** for contact storage

## Prerequisites

- Java 8 or above installed
- Twilio Account SID, Auth Token, and Twilio phone number (replace in code)
- Twilio SDK library added to your project

## Setup Instructions

1. Clone this repository:
    ```
    git clone https://github.com/AshikaShaheenAS/WomenSafetyApp.git
    ```

2. Open the project in your preferred Java IDE (like VS Code, IntelliJ, or Eclipse).

3. Replace the Twilio configuration placeholders:
    ```java
    public static final String APP_IDENTIFIER = "Your_Twilio_SID";
    public static final String APP_CREDENTIAL = "Your_Twilio_Auth_Token";
    public static final String APP_CONTACT_NUMBER = "Your_Twilio_Number";
    ```

4. Build and run the program:
    ```
    javac WomenSafetyApp.java
    java WomenSafetyApp
    ```

## Future Improvements

- Add a GUI interface for ease of use
- Integrate location sharing in alerts
- Add email alert feature

## License

This project is for educational purposes only.
