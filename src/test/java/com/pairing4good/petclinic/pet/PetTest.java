package com.pairing4good.petclinic.pet;

import com.pairing4good.petclinic.visit.Visit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PetTest {

    @Test
    public void shouldAddVisitWhenVisitSetIsNull() {
        Pet pet = new Pet();

        pet.addVisit(new Visit());

        assertEquals(1, pet.getVisits().size());
    }

    @Test
    public void shouldAddVisitToExistingVisitsSet() {
        Pet pet = new Pet();

        pet.addVisit(new Visit());
        pet.addVisit(new Visit());

        assertEquals(2, pet.getVisits().size());
    }
}
