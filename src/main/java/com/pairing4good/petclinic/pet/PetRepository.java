package com.pairing4good.petclinic.pet;

import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Integer> {
}
