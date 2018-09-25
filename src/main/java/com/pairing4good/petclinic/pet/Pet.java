package com.pairing4good.petclinic.pet;

import com.pairing4good.petclinic.model.BaseEntity;
import com.pairing4good.petclinic.owner.Owner;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Pet extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    private String name;

    private LocalDate birthDate;

    private PetType type;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }
}
