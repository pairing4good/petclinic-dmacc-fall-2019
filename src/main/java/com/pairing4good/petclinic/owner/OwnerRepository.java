package com.pairing4good.petclinic.owner;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface OwnerRepository extends CrudRepository<Owner, Integer>, JpaSpecificationExecutor<Owner> {

    Collection<Owner> findByLastNameContainingIgnoreCase(String lastNamePart);
}
