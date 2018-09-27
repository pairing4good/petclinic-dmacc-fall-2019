package com.pairing4good.petclinic.pet;

import com.pairing4good.petclinic.message.Message;
import com.pairing4good.petclinic.owner.Owner;
import com.pairing4good.petclinic.owner.OwnerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

import static com.pairing4good.petclinic.message.Level.danger;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
    private PetRepository petRepository;
    private PetTypeRepository petTypeRepository;
    private OwnerRepository ownerRepository;

    public PetController(PetRepository petRepository, PetTypeRepository petTypeRepository,
                         OwnerRepository ownerRepository) {
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
        this.ownerRepository = ownerRepository;
    }

    @ModelAttribute("types")
    public Iterable<PetType> populatePetTypes() {

        return petTypeRepository.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") int ownerId) {
        return ownerRepository.findById(ownerId).get();
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }


    @GetMapping("/pets/new")
    public String setupSave(Owner owner, ModelMap model) {
        Pet pet = new Pet();
        owner.addPet(pet);
        model.put("pet", pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/new")
    public String save(Owner owner, @Valid Pet pet, BindingResult result, ModelMap model) {
        owner.addPet(pet);
        if (result.hasErrors()) {
            model.put("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            petRepository.save(pet);
            return "redirect:/owners/{ownerId}";
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public String setupUpdate(@PathVariable("petId") int petId, @PathVariable("ownerId") int ownerId, ModelMap model,
                              RedirectAttributes redirectAttributes) {
        Optional<Pet> pet = petRepository.findById(petId);
        if (pet.isPresent()) {
            model.put("pet", pet.get());
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            Message message = new Message(danger, "Please select an existing pet.");
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/owners/" + ownerId;
        }
    }

    @PostMapping("/pets/{petId}/edit")
    public String update(@Valid Pet pet, BindingResult result, Owner owner, ModelMap model) {
        if (result.hasErrors()) {
            pet.setOwner(owner);
            model.put("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            owner.addPet(pet);
            petRepository.save(pet);
            return "redirect:/owners/{ownerId}";
        }
    }
}
