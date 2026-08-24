/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.medicarehospital;

/**
 *
 * @author Student
 */
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final HospitalSystem hospital = new HospitalSystem();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            displayMenu();

            int choice = readInt("Enter your choice: ");

            try {

                switch (choice) {

                    case 1:
                        registerPatient();
                        break;

                    case 2:
                        searchPatient();
                        break;

                    case 3:
                        updatePatient();
                        break;

                    case 4:
                        deletePatient();
                        break;

                    case 5:
                        hospital.displayAllPatients();
                        break;

                    case 6:
                        allocateBed();
                        break;

                    case 7:
                        releaseBed();
                        break;

                    case 8:
                        hospital.displayWardLayout();
                        break;

                    case 9:
                        hospital.displayAvailableBeds();
                        break;

                    case 10:
                        hospital.displayOccupiedBeds();
                        break;

                    case 11:
                        hospital.generateReport();
                        break;

                    case 12:
                        sortPatients();
                        break;

                    case 0:
                        running = false;
                        System.out.println("Thank you for using MediCare Hospital System.");
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }

            } catch (Exception e) {

                System.out.println(
                        "An error occurred: " + e.getMessage()
                );
            }
        }

        scanner.close();
    }

    private static void displayMenu() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("       MEDICARE HOSPITAL SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Register Patient");
        System.out.println("2. Search Patient");
        System.out.println("3. Update Patient");
        System.out.println("4. Delete Patient");
        System.out.println("5. Display All Patients");
        System.out.println("6. Allocate Bed");
        System.out.println("7. Release Bed");
        System.out.println("8. Display Ward Layout");
        System.out.println("9. Display Available Beds");
        System.out.println("10. Display Occupied Beds");
        System.out.println("11. Generate Ward Report");
        System.out.println("12. Sort Patients");
        System.out.println("0. Exit");
        System.out.println("========================================");
    }

    private static void registerPatient() {

        System.out.println("\n========== REGISTER PATIENT ==========");

        String id = readString("Patient ID: ");

        if (hospital.searchPatient(id) != null) {
            System.out.println("Error: Patient ID already exists.");
            return;
        }

        String firstName = readString("First Name: ");
        String lastName = readString("Last Name: ");
        int age = readInt("Age: ");

        if (age < 0 || age > 120) {
            System.out.println("Invalid age.");
            return;
        }

        String gender = readString("Gender: ");
        String condition = readString("Medical Condition: ");

        PatientCategory category = readCategory();

        Patient patient;

        if (category == PatientCategory.INPATIENT) {

            patient = new Inpatient(
                    id,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    category,
                    1,
                    "Not Assigned"
            );

        } else {

            patient = new Patient(
                    id,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    category
            );
        }

        if (hospital.registerPatient(patient)) {
            System.out.println("Patient registered successfully.");
        } else {
            System.out.println("Patient registration failed.");
        }
    }

    private static void searchPatient() {

        String id = readString("Enter Patient ID: ");

        Patient patient = hospital.searchPatient(id);

        if (patient == null) {
            System.out.println("Patient not found.");
        } else {
            patient.displayDetails();
        }
    }

    private static void updatePatient() {

        String id = readString("Enter Patient ID to update: ");

        Patient patient = hospital.searchPatient(id);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("Enter the new patient information.");

        String firstName = readString("First Name: ");
        String lastName = readString("Last Name: ");
        int age = readInt("Age: ");
        String gender = readString("Gender: ");
        String condition = readString("Medical Condition: ");
        PatientCategory category = readCategory();

        boolean updated = hospital.updatePatient(
                id,
                firstName,
                lastName,
                age,
                gender,
                condition,
                category
        );

        if (updated) {
            System.out.println("Patient updated successfully.");
        } else {
            System.out.println("Patient update failed.");
        }
    }

    private static void deletePatient() {

        String id = readString("Enter Patient ID to delete: ");

        boolean deleted = hospital.deletePatient(id);

        if (deleted) {
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("Patient not found.");
        }
    }

    private static void allocateBed() {

        System.out.println("\n========== ALLOCATE BED ==========");

        String id = readString("Enter Inpatient ID: ");

        Patient patient = hospital.searchPatient(id);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        if (patient.getCategory() != PatientCategory.INPATIENT) {
            System.out.println(
                    "Only Inpatients may be allocated hospital beds."
            );
            return;
        }

        if (hospital.areAllBedsOccupied()) {
            System.out.println("No beds are available.");
            return;
        }

        hospital.displayAvailableBeds();

        String bedNumber =
                readString("Enter bed number (e.g. B01): ")
                .toUpperCase();

        if (hospital.allocateBed(id, bedNumber)) {
            System.out.println(
                    "Bed " + bedNumber + " allocated successfully."
            );
        } else {
            System.out.println(
                    "Bed allocation failed. The bed may be occupied "
                    + "or the patient may already have a bed."
            );
        }
    }

    private static void releaseBed() {

        String id = readString(
                "Enter Patient ID to release the bed: "
        );

        if (hospital.releaseBed(id)) {
            System.out.println("Bed released successfully.");
        } else {
            System.out.println("No bed found for this patient.");
        }
    }

    private static void sortPatients() {

        System.out.println("\n========== SORT PATIENTS ==========");
        System.out.println("1. Sort by Surname");
        System.out.println("2. Sort by Patient ID");

        int choice = readInt("Enter choice: ");

        switch (choice) {

            case 1:
                hospital.displaySortedPatientsBySurname();
                break;

            case 2:
                hospital.displaySortedPatientsById();
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    private static PatientCategory readCategory() {

        while (true) {

            System.out.println("\nPatient Category:");
            System.out.println("1. Inpatient");
            System.out.println("2. Outpatient");
            System.out.println("3. Emergency");

            int choice = readInt("Choose category: ");

            switch (choice) {

                case 1:
                    return PatientCategory.INPATIENT;

                case 2:
                    return PatientCategory.OUTPATIENT;

                case 3:
                    return PatientCategory.EMERGENCY;

                default:
                    System.out.println(
                            "Invalid category. Please try again."
                    );
            }
        }
    }

    private static String readString(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    private static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                String input = scanner.nextLine();

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}