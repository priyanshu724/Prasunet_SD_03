package PrasunetInternship.Task3;

/* Implement a Simple Contact Management System */

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.ObjectOutputStream;

public class ContactManger {

    private static HashMap<String, Contact> contacts = new HashMap<>();
    private static final String FILE_NAME = "contact.ser";

    public static void main(String[] args) {
        loadContacts();

        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\nContact Manager");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");

            String choice = input.nextLine();
            switch (choice) {
                case "1":
                    addContact(input);
                    break;

                case "2":
                    viewContact();
                    break;

                case "3":
                    editContact(input);
                    break;

                case "4":
                    deleteContact(input);
                    break;

                case "5":
                    saveContacts();
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addContact(Scanner input) {
        System.out.println("Name : ");
        String name = input.nextLine();

        System.out.println("Email : ");
        String email = input.nextLine();

        System.out.println("Phone : ");
        String phone = input.nextLine();

        Contact contact = new Contact(name, email, phone);
        contacts.put(name, contact);
        System.out.println("Contact added.");
    }

    private static void viewContact() {
        if (contacts.isEmpty()) {
            System.out.println("No contact found");
        } else {
            contacts.values().forEach(System.out::println);
        }
    }

    private static void editContact(Scanner input) {
        System.out.println("Enter the name of the contact to edit: ");
        String name = input.nextLine();

        Contact contact = contacts.get(name);

        if (contact != null) {
            System.out.print("Enter new name (leave blank to keep unchanged): ");
            String newname = input.nextLine();

            if (!newname.isBlank()) {
                contacts.remove(name);
                contact.setName(newname);
                contacts.put(newname, contact);
            }

            System.out.print("Enter new phone number (leave blank to keep unchanged): ");
            String phone = input.nextLine();
            if (!phone.isBlank()) {
                contact.setMobileNumber(phone);
            }

            System.out.print("Enter new email address (leave blank to keep unchanged): ");
            String email = input.nextLine();
            if (!email.isBlank()) {
                contact.setEmail(email);
            }
            System.out.println("Contact updated.");
        } else {
            System.out.println("Contact not found!");
        }
    }

    private static void deleteContact(Scanner input) {
        System.out.print("Enter the name of the contact to delete: ");
        String name = input.nextLine();

        Contact contact = contacts.get(name);
        if (contact != null) {
            System.out.print("Are you sure you want to delete " + name + "? (yes/no): ");
            String confirm = input.nextLine();
            if (confirm.equalsIgnoreCase("yes")) {
                contacts.remove(name);
                System.out.println("Contact deleted.");
            }
        } else {
            System.out.println("Contact not found.");
        }
    }

    private static void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(contacts);
            System.out.println("Contact saved!");
        } catch (IOException e) {
            System.out.println("Error saving contacts : " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadContacts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            contacts = (HashMap<String, Contact>) ois.readObject();
            System.out.println("Contacts loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("No saved contacts found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
    }
}
