# MediCare Hospital Patient Admission System

## PROG6112 – Programming 1B

### Project Overview

The MediCare Hospital Patient Admission System is a Java console-based application developed for MediCare Hospital.

The purpose of the system is to replace the hospital's paper-based patient admission process with a simple computerised system. The system allows hospital administrative staff to manage patient information and hospital bed allocations efficiently.

## System Features

The system provides the following features:

* Register a new patient
* Search for a patient using Patient ID
* Update patient details
* Delete patient records
* Display all registered patients
* Allocate hospital beds
* Release hospital beds
* Display the ward layout
* Display available beds
* Display occupied beds
* Generate ward reports
* Calculate ward occupancy percentage
* Sort patients by surname
* Sort patients by Patient ID
* Prevent duplicate Patient IDs
* Prevent allocation of occupied beds
* Prevent non-inpatients from receiving beds
* Prevent bed allocation when all beds are occupied
* Perform unit testing using JUnit 5

## Patient Categories

The system supports three patient categories:

1. Inpatient
2. Outpatient
3. Emergency

The `PatientCategory` enum is used to represent these categories.

## Hospital Ward

The hospital contains one ward with 20 beds.

The beds are arranged in a 4 x 5 layout:

```text
B01 B02 B03 B04 B05
B06 B07 B08 B09 B10
B11 B12 B13 B14 B15
B16 B17 B18 B19 B20
```

Only patients classified as **Inpatients** can be allocated hospital beds.

## Object-Oriented Programming Concepts

The project demonstrates several Java object-oriented programming concepts.

### Encapsulation

Patient and inpatient attributes are declared as private. Getters and setters are used to access and modify the attributes.

### Inheritance

The `Inpatient` class extends the `Patient` class.

```java
public class Inpatient extends Patient
```

### Constructor Chaining

The `Inpatient` class uses `super()` to initialise attributes inherited from the `Patient` class.

### Method Overriding

The `Inpatient` class overrides the `displayDetails()` method to display additional ward and bed information.

### Enum

The `PatientCategory` enum represents the three patient categories.

## Data Structures

The application uses an `ArrayList` to store registered patients.

```java
ArrayList<Patient>
```

A two-dimensional array is used to represent the hospital ward:

```java
Patient[][] beds = new Patient[4][5];
```

Nested loops are used to display and manage the 20 beds.

## Sorting

The system allows patient records to be sorted by:

* Patient surname
* Patient ID

## Exception Handling

Exception handling is used to prevent invalid user input from causing the application to terminate unexpectedly.

For example, invalid numerical input is handled using `try-catch`.

## Unit Testing

JUnit 5 tests are included to test the following functionality:

* Registering a patient
* Searching for a patient
* Updating patient details
* Deleting a patient
* Allocating a bed
* Releasing a bed
* Preventing duplicate Patient IDs
* Preventing allocation of an occupied bed
* Preventing allocation when all 20 beds are occupied
* Sorting patients by surname
* Sorting patients by Patient ID
* Preventing Outpatients from receiving beds
* Confirming the ward contains 20 beds

## Technologies Used

* Java
* Maven
* JUnit 5
* NetBeans IDE
* GitHub

## Project Structure

```text
MediCareHospital/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── medicare/
│   │               ├── Main.java
│   │               ├── Patient.java
│   │               ├── Inpatient.java
│   │               ├── PatientCategory.java
│   │               └── HospitalSystem.java
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── medicare/
│                   └── HospitalSystemTest.java
│
├── pom.xml
├── README.md
└── .gitignore
```

## How to Run the Application

1. Open the project in NetBeans.
2. Ensure that Java is installed and configured.
3. Open the Maven project.
4. Build the project.
5. Run `Main.java`.
6. Follow the instructions displayed in the console menu.

## How to Run Unit Tests

In NetBeans:

1. Right-click the project.
2. Select **Test**.
3. JUnit 5 will execute the tests.
4. Check that all tests pass successfully.

Maven can also be used to run the tests:

```text
mvn test
```

## Assumptions

The system operates according to the following assumptions:

* The hospital has one ward.
* The ward contains exactly 20 beds.
* Each inpatient can occupy only one bed.
* A bed can only be assigned to one inpatient at a time.
* Outpatients do not require hospital beds.
* Emergency patients do not require hospital beds.
* Patient information is stored in memory while the application is running.
* The application is console-based and menu-driven.

## Author

**Ntuli Siziphiwe**

### Academic Year

2026
