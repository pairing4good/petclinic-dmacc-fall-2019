package com.pairing4good.petclinic.visit;

import com.pairing4good.petclinic.message.Level;
import com.pairing4good.petclinic.message.Message;
import com.pairing4good.petclinic.pet.Pet;
import com.pairing4good.petclinic.pet.PetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class VisitController {

    private VisitRepository visitRepository;
    private PetRepository petRepository;

    public VisitController(VisitRepository visitRepository, PetRepository petRepository) {

        this.visitRepository = visitRepository;
        this.petRepository = petRepository;
    }

    @GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String setupNew(@PathVariable("petId") int petId, @PathVariable("ownerId") int ownerId, Visit visit,
                           ModelMap model, RedirectAttributes redirectAttributes) {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            model.put("pet", pet);
            pet.addVisit(visit);
            return "visits/createOrUpdateVisitForm";
        } else {
            Message message = new Message(Level.danger, "Please select an existing pet.");
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/owners/" + ownerId;
        }
    }
}
