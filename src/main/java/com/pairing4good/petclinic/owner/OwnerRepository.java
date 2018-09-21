package com.pairing4good.petclinic.owner;

import org.springframework.data.repository.Repository;

public interface OwnerRepository extends Repository<Owner, Integer> {

    void save(Owner owner);
}
