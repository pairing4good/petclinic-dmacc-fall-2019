package com.pairing4good.petclinic.owner;

import com.pairing4good.petclinic.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static com.pairing4good.petclinic.message.Level.danger;

@Controller
public class OwnerController {

    public static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private OwnerRepository ownerRepository;

    @Autowired
    public OwnerController(OwnerRepository ownerRepository) {

        this.ownerRepository = ownerRepository;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @GetMapping("/owners/new")
    public String setupSave(Map<String, Object> model) {
        model.put("owner", new Owner());
        return OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
    }

    @PostMapping("/owners/new")
    public String save(@Valid Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
        } else {
            ownerRepository.save(owner);
            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/owners/find")
    public String setupFind(Map<String, Object> model) {
        model.put("owner", new Owner());
        return "owners/findOwners";
    }

    @GetMapping("/owners")
    public String find(Owner owner, BindingResult result, Map<String, Object> model) {
        Collection<Owner> results = ownerRepository.findByLastName(owner.getLastName());
        if (results.isEmpty()) {
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else {
            model.put("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping("/owners/{ownerId}")
    public ModelAndView findById(@PathVariable("ownerId") int ownerId, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        if (optionalOwner.isPresent()) {
            modelAndView.addObject(optionalOwner.get());
            return modelAndView;
        } else {
            Message message = new Message(danger, "Please select an existing owner.");
            redirectAttributes.addFlashAttribute("message", message);
            return new ModelAndView("redirect:/owners");
        }
    }

    @GetMapping("/owners/{ownerId}/edit")
    public String setupUpdate(@PathVariable("ownerId") int ownerId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        if (optionalOwner.isPresent()) {
            model.addAttribute(optionalOwner.get());
            return OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
        } else {
            Message message = new Message(danger, "Please select an existing owner.");
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/owners";
        }
    }

    @PostMapping("/owners/{ownerId}/edit")
    public String update(@Valid Owner owner, BindingResult result, @PathVariable("ownerId") int ownerId) {
        if (result.hasErrors()) {
            return OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
        } else {
            owner.setId(ownerId);
            ownerRepository.save(owner);
            return "redirect:/owners/{ownerId}";
        }
    }
}
