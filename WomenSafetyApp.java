import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class WomenSafetyApp {
    private static ArrayList<Contact> contacts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static final String APP_IDENTIFIER = "yours";
    public static final String APP_CREDENTIAL = "yours";
    public static final String APP_CONTACT_NUMBER = "yours";

    public static void main(String[] args) {
        loadContactsFromFile();

        boolean running = true;
        System.out.println("=== Women Safety App ===");

        while (running) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    sendAlert();
                    break;
                case 4:
                    editContact();
                    break;
                case 5:
                    deleteContact();
                    break;
                case 6:
                    saveContactsToFile();
                    running = false;
                    System.out.println("Exiting app. Stay safe!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1. Add Emergency Contact");
        System.out.println("2. View Contacts");
        System.out.println("3. Send Safety Alert (SMS)");
        System.out.println("4. Edit Contact");
        System.out.println("5. Delete Contact");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addContact() {
        System.out.print("Enter contact name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number (with country code): ");
        String phone = scanner.nextLine();
        contacts.add(new Contact(name, phone));
        System.out.println("Contact added successfully.");
    }

    private static void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts to display.");
            return;
        }

        System.out.println("Emergency Contacts:");
        for (int i = 0; i < contacts.size(); i++) {
            Contact c = contacts.get(i);
            System.out.println((i + 1) + ". Name: " + c.getName() + ", Phone: " + c.getPhoneNumber());
        }
    }

    private static void sendAlert() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts to alert.");
            return;
        }

        System.out.print("Enter your alert message: ");
        String alert = scanner.nextLine();

        Twilio.init(APP_IDENTIFIER, APP_CREDENTIAL);

        System.out.println("Sending SMS alerts...");
        for (Contact c : contacts) {
            try {
                Message message = Message.creator(
                        new PhoneNumber(c.getPhoneNumber()),
                        new PhoneNumber(APP_CONTACT_NUMBER),
                        alert
                ).create();

                System.out.println("Sent to " + c.getName() + ": " + message.getSid());
            } catch (Exception e) {
                System.out.println("Failed to send to " + c.getName() + ": " + e.getMessage());
            }
        }
    }

    private static void editContact() {
        viewContacts();
        if (contacts.isEmpty()) return;

        System.out.print("Enter the number of the contact to edit: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();

        if (index >= 0 && index < contacts.size()) {
            Contact contact = contacts.get(index);
            System.out.print("Enter new name (leave empty to keep '" + contact.getName() + "'): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                contact.setName(newName);
            }

            System.out.print("Enter new phone number (leave empty to keep '" + contact.getPhoneNumber() + "'): ");
            String newPhone = scanner.nextLine();
            if (!newPhone.isEmpty()) {
                contact.setPhoneNumber(newPhone);
            }

            System.out.println("Contact updated successfully.");
        } else {
            System.out.println("Invalid contact number.");
        }
    }

    private static void deleteContact() {
        viewContacts();
        if (contacts.isEmpty()) return;

        System.out.print("Enter the number of the contact to delete: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();

        if (index >= 0 && index < contacts.size()) {
            Contact removed = contacts.remove(index);
            System.out.println("Deleted contact: " + removed.getName());
        } else {
            System.out.println("Invalid contact number.");
        }
    }

    private static void loadContactsFromFile() {
        try {
            File file = new File("contacts.txt");
            if (!file.exists()) return;

            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    contacts.add(new Contact(parts[0], parts[1]));
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
    }

    private static void saveContactsToFile() {
        try {
            FileWriter writer = new FileWriter("contacts.txt");
            for (Contact contact : contacts) {
                writer.write(contact.getName() + "," + contact.getPhoneNumber() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }
}
