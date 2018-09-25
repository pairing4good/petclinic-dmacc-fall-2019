package com.pairing4good.petclinic.owner;

import com.pairing4good.petclinic.pet.Pet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class OwnerTests {

    @Test
    public void shouldAddPetToListWhenPetMissingId() {
        Pet pet = new Pet();

        Owner owner = new Owner();

        owner.addPet(pet);

        assertNotNull(owner.getPets());
        assertTrue(owner.getPets().iterator().hasNext());
        assertSame(owner, pet.getOwner());
    }

    @Test
    public void shouldNotAddPetToListWhenPetHasId() {
        Pet pet = new Pet();
        pet.setId(1);

        Owner owner = new Owner();

        owner.addPet(pet);

        assertNull(owner.getPets());
        assertSame(owner, pet.getOwner());
    }

    @Test
    public void shouldAddMultiplePetsToTheSameList() {
        Pet firstPet = new Pet();
        firstPet.setName("nameOne");

        Pet secondPet = new Pet();
        secondPet.setName("nameTwo");

        Owner owner = new Owner();

        owner.addPet(firstPet);
        owner.addPet(secondPet);

        List<Pet> sortedPets = new ArrayList<>();

        for (Pet pet : owner.getPets()) {
            sortedPets.add(pet);
        }

        Collections.sort(sortedPets, Comparator.comparing(Pet::getName));

        assertNotNull(owner.getPets());
        assertSame(firstPet, sortedPets.get(0));
        assertSame(secondPet, sortedPets.get(1));
        assertSame(owner, firstPet.getOwner());
        assertSame(owner, secondPet.getOwner());
    }
}
