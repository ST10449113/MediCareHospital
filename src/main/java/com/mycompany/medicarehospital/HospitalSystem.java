/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.medicarehospital;

/**
 *
 * @author Student
 */
import java.util.ArrayList;
import java.util.Comparator;

public class HospitalSystem {

    private final ArrayList<Patient> patients;

    // 4 rows x 5 columns = 20 beds
    private final Patient[][] beds;

    public HospitalSystem() {
        patients = new ArrayList<>();
        beds = new Patient[4][5];
    }

    // PATIENT MANAGEMENT
 
    /**
     *
     * @param patient
     * @return
     */

    public boolean registerPatient(Patient patient) {

        if (patient == null) {
            return false;
        }

        if (searchPatient(patient.getPatientId()) != null) {
            return false;
        }

        patients.add(patient);
        return true;
    }

    public Patient searchPatient(String patientId) {

        for (Patient patient : patients) {

            if (patient.getPatientId().equalsIgnoreCase(patientId)) {
                return patient;
            }
        }

        return null;
    }

    public boolean updatePatient(String patientId,
                                 String firstName,
                                 String lastName,
                                 int age,
                                 String gender,
                                 String medicalCondition,
                                 PatientCategory category) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(medicalCondition);
        patient.setCategory(category);

        return true;
    }

    public boolean deletePatient(String patientId) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        releaseBed(patientId);
        patients.remove(patient);

        return true;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void displayAllPatients() {

        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }

        System.out.println("\n========== ALL PATIENTS ==========");

        for (Patient patient : patients) {
            patient.displayDetails();
        }
    }

    // BED MANAGEMENT
    
    public String getBedNumber(int row, int column) {

        int bedNumber = row * 5 + column + 1;

        return String.format("B%02d", bedNumber);
    }

    private boolean isValidBed(String bedNumber) {

        if (bedNumber == null) {
            return false;
        }

        if (!bedNumber.matches("B\\d{2}")) {
            return false;
        }

        int number;

        try {
            number = Integer.parseInt(bedNumber.substring(1));
        } catch (NumberFormatException e) {
            return false;
        }

        return number >= 1 && number <= 20;
    }

    private int[] getBedPosition(String bedNumber) {

        int number = Integer.parseInt(bedNumber.substring(1));

        int row = (number - 1) / 5;
        int column = (number - 1) % 5;

        return new int[]{row, column};
    }

    public boolean allocateBed(String patientId, String bedNumber) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        // Only inpatients can receive beds
        if (patient.getCategory() != PatientCategory.INPATIENT) {
            return false;
        }

        if (!isValidBed(bedNumber)) {
            return false;
        }

        int[] position = getBedPosition(bedNumber);

        int row = position[0];
        int column = position[1];

        // Prevent occupied bed
        if (beds[row][column] != null) {
            return false;
        }

        // Prevent same inpatient from getting multiple beds
        if (findPatientBed(patientId) != null) {
            return false;
        }

        beds[row][column] = patient;

        if (patient instanceof Inpatient) {

            Inpatient inpatient = (Inpatient) patient;

            inpatient.setWardNumber(1);
            inpatient.setBedNumber(bedNumber);
        }

        return true;
    }

    public boolean releaseBed(String patientId) {

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                Patient patient = beds[row][column];

                if (patient != null
                        && patient.getPatientId().equalsIgnoreCase(patientId)) {

                    beds[row][column] = null;

                    if (patient instanceof Inpatient) {
                        Inpatient inpatient = (Inpatient) patient;
                        inpatient.setBedNumber("Not Assigned");
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private Patient findPatientBed(String patientId) {

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                Patient patient = beds[row][column];

                if (patient != null
                        && patient.getPatientId().equalsIgnoreCase(patientId)) {

                    return patient;
                }
            }
        }

        return null;
    }

    public boolean isBedOccupied(String bedNumber) {

        if (!isValidBed(bedNumber)) {
            return false;
        }

        int[] position = getBedPosition(bedNumber);

        return beds[position[0]][position[1]] != null;
    }

    public boolean areAllBedsOccupied() {

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (beds[row][column] == null) {
                    return false;
                }
            }
        }

        return true;
    }

    public int getOccupiedBedCount() {

        int count = 0;

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (beds[row][column] != null) {
                    count++;
                }
            }
        }

        return count;
    }

    public int getAvailableBedCount() {
        return 20 - getOccupiedBedCount();
    }

    // DISPLAY WARD
  
    public void displayWardLayout() {

        System.out.println("\n========== WARD LAYOUT ==========");

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                String bedNumber = getBedNumber(row, column);

                if (beds[row][column] == null) {
                    System.out.print("[" + bedNumber + ": Available] ");
                } else {
                    System.out.print("[" + bedNumber + ": Occupied] ");
                }
            }

            System.out.println();
        }
    }

    public void displayAvailableBeds() {

        System.out.println("\n========== AVAILABLE BEDS ==========");

        boolean found = false;

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (beds[row][column] == null) {

                    System.out.print(getBedNumber(row, column) + " ");
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No beds are available.");
        } else {
            System.out.println();
        }
    }

    public void displayOccupiedBeds() {

        System.out.println("\n========== OCCUPIED BEDS ==========");

        boolean found = false;

        for (int row = 0; row < beds.length; row++) {

            for (int column = 0; column < beds[row].length; column++) {

                if (beds[row][column] != null) {

                    System.out.println(
                            getBedNumber(row, column)
                            + " - "
                            + beds[row][column].getFirstName()
                            + " "
                            + beds[row][column].getLastName()
                            + " ("
                            + beds[row][column].getPatientId()
                            + ")"
                    );

                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No beds are occupied.");
        }
    }

    // SORTING
    
    public void sortBySurname() {

        patients.sort(
                Comparator.comparing(
                        Patient::getLastName,
                        String.CASE_INSENSITIVE_ORDER
                )
        );
    }

    public void sortByPatientId() {

        patients.sort(
                Comparator.comparing(
                        Patient::getPatientId,
                        String.CASE_INSENSITIVE_ORDER
                )
        );
    }

    public void displaySortedPatientsBySurname() {

        sortBySurname();

        System.out.println("\n========== PATIENTS SORTED BY SURNAME ==========");

        for (Patient patient : patients) {
            System.out.println(
                    patient.getPatientId()
                    + " - "
                    + patient.getLastName()
                    + ", "
                    + patient.getFirstName()
            );
        }
    }

    public void displaySortedPatientsById() {

        sortByPatientId();

        System.out.println("\n========== PATIENTS SORTED BY ID ==========");

        for (Patient patient : patients) {
            System.out.println(
                    patient.getPatientId()
                    + " - "
                    + patient.getFirstName()
                    + " "
                    + patient.getLastName()
            );
        }
    }

    // REPORTS
   
    public void generateReport() {

        System.out.println("\n========================================");
        System.out.println("       MEDICARE WARD REPORT");
        System.out.println("========================================");

        System.out.println(
                "Total Registered Patients: "
                + patients.size()
        );

        System.out.println(
                "Total Occupied Beds: "
                + getOccupiedBedCount()
        );

        System.out.println(
                "Total Available Beds: "
                + getAvailableBedCount()
        );

        double occupancyPercentage =
                (getOccupiedBedCount() / 20.0) * 100;

        System.out.printf(
                "Ward Occupancy: %.2f%%%n",
                occupancyPercentage
        );

        System.out.println("========================================");
    }
}
    