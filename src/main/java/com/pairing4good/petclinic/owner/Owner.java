package com.pairing4good.petclinic.owner;

import com.pairing4good.petclinic.model.BaseEntity;

import javax.persistence.Entity;

@Entity
public class Owner extends BaseEntity {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public String getCity() {
        return this.city;
    }

    public String getTelephone() {
        return this.telephone;
    }
}
