package com.pairing4good.petclinic.pet;

import com.pairing4good.petclinic.model.BaseEntity;
import com.pairing4good.petclinic.owner.Owner;
import com.pairing4good.petclinic.visit.Visit;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Pet extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @NotEmpty
    private String name;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "type_id")
    private PetType type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "petId", fetch = FetchType.EAGER)
    private Set<Visit> visits = new LinkedHashSet<>();

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

    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }

    public void addVisit(Visit visit) {
        if (visits == null) {
            visits = new HashSet<>();
        }
        visits.add(visit);
    }
}
