package com.pairing4good.petclinic.api;

import com.pairing4good.petclinic.owner.Owner;
import com.pairing4good.petclinic.owner.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClinicApiController {

    private final OwnerRepository ownerRepository;

    @Autowired
    public ClinicApiController(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @RequestMapping("/owners")
    public Iterable<Owner> find(){
        return ownerRepository.findAll();
    }
}
