package com.pairing4good.petclinic.owner;

import com.pairing4good.petclinic.model.BaseEntity;
import com.pairing4good.petclinic.pet.Pet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Owner extends BaseEntity {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String address;
    @NotEmpty
    private String city;
    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    private String telephone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


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

    public Set<Pet> getPets() {
        return pets;
    }

    public void addPet(Pet pet) {
        if (pet.isNew()) {
            if (pets == null) {
                pets = new HashSet<>();
            }
            pets.add(pet);
        }
        pet.setOwner(this);
    }
}
