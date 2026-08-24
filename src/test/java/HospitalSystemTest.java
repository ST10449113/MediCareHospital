/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */
import com.mycompany.medicarehospital.HospitalSystem;
import com.mycompany.medicarehospital.Inpatient;
import com.mycompany.medicarehospital.PatientCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HospitalSystemTest {

    private Patient createOutpatient(String id) {

        return new Patient(
                id,
                "John",
                "Smith",
                30,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );
    }

    private Inpatient createInpatient(String id) {

        return new Inpatient(
                id,
                "Jane",
                "Doe",
                40,
                "Female",
                "Pneumonia",
                PatientCategory.INPATIENT,
                1,
                "Not Assigned"
        );
    }

    // REGISTER TEST
    
    @Test
    public void testRegisterPatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = createOutpatient("P001");

        assertTrue(
                hospital.registerPatient(patient)
        );

        assertNotNull(
                hospital.searchPatient("P001")
        );
    }

    // =========================
    // SEARCH TEST
    // =========================

    @Test
    public void testSearchPatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = createOutpatient("P002");

        hospital.registerPatient(patient);

        Patient result =
                hospital.searchPatient("P002");

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

   
    // UPDATE TEST
    

    @Test
    public void testUpdatePatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = createOutpatient("P003");

        hospital.registerPatient(patient);

        boolean result =
                hospital.updatePatient(
                        "P003",
                        "Peter",
                        "Jones",
                        35,
                        "Male",
                        "Asthma",
                        PatientCategory.OUTPATIENT
                );

        assertTrue(result);

        Patient updated =
                hospital.searchPatient("P003");

        assertEquals("Peter", updated.getFirstName());
        assertEquals("Jones", updated.getLastName());
        assertEquals(35, updated.getAge());
    }

    // DELETE TEST
    
    @Test
    public void testDeletePatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = createOutpatient("P004");

        hospital.registerPatient(patient);

        assertTrue(
                hospital.deletePatient("P004")
        );

        assertNull(
                hospital.searchPatient("P004")
        );
    }

    // ALLOCATE BED TEST
    
    @Test
    public void testAllocateBed() {

        HospitalSystem hospital = new HospitalSystem();

        Inpatient patient =
                createInpatient("P005");

        hospital.registerPatient(patient);

        assertTrue(
                hospital.allocateBed("P005", "B01")
        );

        assertTrue(
                hospital.isBedOccupied("B01")
        );

        assertEquals(
                "B01",
                patient.getBedNumber()
        );
    }

    // RELEASE BED TEST
    
    @Test
    public void testReleaseBed() {

        HospitalSystem hospital = new HospitalSystem();

        Inpatient patient =
                createInpatient("P006");

        hospital.registerPatient(patient);

        hospital.allocateBed("P006", "B02");

        assertTrue(
                hospital.releaseBed("P006")
        );

        assertFalse(
                hospital.isBedOccupied("B02")
        );
    }

    // DUPLICATE ID TEST

    @Test
    public void testDuplicatePatientId() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient1 =
                createOutpatient("P007");

        Patient patient2 =
                createOutpatient("P007");

        assertTrue(
                hospital.registerPatient(patient1)
        );

        assertFalse(
                hospital.registerPatient(patient2)
        );
    }
    
    // OCCUPIED BED TEST

    @Test
    public void testPreventOccupiedBed() {

        HospitalSystem hospital = new HospitalSystem();

        Inpatient patient1 =
                createInpatient("P008");

        Inpatient patient2 =
                createInpatient("P009");

        hospital.registerPatient(patient1);
        hospital.registerPatient(patient2);

        assertTrue(
                hospital.allocateBed("P008", "B03")
        );

        assertFalse(
                hospital.allocateBed("P009", "B03")
        );
    }

    // FULL WARD TEST
    
    @Test
    public void testPreventAllocationWhenWardIsFull() {

        HospitalSystem hospital = new HospitalSystem();

        for (int i = 1; i <= 20; i++) {

            String id = String.format("P%03d", i);

            Inpatient patient =
                    createInpatient(id);

            hospital.registerPatient(patient);

            String bed =
                    String.format("B%02d", i);

            assertTrue(
                    hospital.allocateBed(id, bed)
            );
        }

        assertTrue(
                hospital.areAllBedsOccupied()
        );

        Inpatient extraPatient =
                createInpatient("P021");

        hospital.registerPatient(extraPatient);

        assertFalse(
                hospital.allocateBed("P021", "B01")
        );
    }

    // SORT BY SURNAME TEST
    
    @Test
    public void testSortPatientsBySurname() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient1 =
                new Patient(
                        "P003",
                        "Zanele",
                        "Zulu",
                        25,
                        "Female",
                        "Flu",
                        PatientCategory.OUTPATIENT
                );

        Patient patient2 =
                new Patient(
                        "P001",
                        "Alice",
                        "Adams",
                        30,
                        "Female",
                        "Cold",
                        PatientCategory.OUTPATIENT
                );

        Patient patient3 =
                new Patient(
                        "P002",
                        "John",
                        "Brown",
                        35,
                        "Male",
                        "Asthma",
                        PatientCategory.OUTPATIENT
                );

        hospital.registerPatient(patient1);
        hospital.registerPatient(patient2);
        hospital.registerPatient(patient3);

        hospital.sortBySurname();

        assertEquals(
                "Adams",
                hospital.getPatients().get(0).getLastName()
        );

        assertEquals(
                "Brown",
                hospital.getPatients().get(1).getLastName()
        );

        assertEquals(
                "Zulu",
                hospital.getPatients().get(2).getLastName()
        );
    }

    // SORT BY ID TEST
    

    @Test
    public void testSortPatientsById() {

        HospitalSystem hospital = new HospitalSystem();

        hospital.registerPatient(
                createOutpatient("P003")
        );

        hospital.registerPatient(
                createOutpatient("P001")
        );

        hospital.registerPatient(
                createOutpatient("P002")
        );

        hospital.sortByPatientId();

        assertEquals(
                "P001",
                hospital.getPatients().get(0).getPatientId()
        );

        assertEquals(
                "P002",
                hospital.getPatients().get(1).getPatientId()
        );

        assertEquals(
                "P003",
                hospital.getPatients().get(2).getPatientId()
        );
    }

    // ONLY INPATIENT TEST
   
    @Test
    public void testOnlyInpatientCanGetBed() {

        HospitalSystem hospital = new HospitalSystem();

        Patient outpatient =
                createOutpatient("P010");

        hospital.registerPatient(outpatient);

        assertFalse(
                hospital.allocateBed("P010", "B04")
        );
    }

    // 20 BED COUNT TEST
   
    @Test
    public void testWardHas20Beds() {

        HospitalSystem hospital = new HospitalSystem();

        assertEquals(
                20,
                hospital.getAvailableBedCount()
        );
    }
}