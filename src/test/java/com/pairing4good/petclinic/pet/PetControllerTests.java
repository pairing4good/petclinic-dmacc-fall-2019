package com.pairing4good.petclinic.pet;

import com.pairing4good.petclinic.message.Message;
import com.pairing4good.petclinic.owner.Owner;
import com.pairing4good.petclinic.owner.OwnerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.pairing4good.petclinic.message.Level.danger;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PetControllerTests {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private ModelMap model;

    @Mock
    private RedirectAttributes redirectAttributes;

    private PetController controller;
    private Map<String, Object> modelMap;

    @Before
    public void setUp() {
        controller = new PetController(petRepository, ownerRepository);
        modelMap = new HashMap<>();
    }

    @Test
    public void shouldSetupAnEmptyNewPet() {
        Owner owner = new Owner();

        String actual = controller.setupSave(owner, modelMap);

        assertEquals("pets/createOrUpdatePetForm", actual);
        assertTrue(modelMap.containsKey("pet"));
        assertNotNull(modelMap.get("pet"));
        assertNotNull(owner.getPets());
    }

    @Test
    public void shouldSetupUpdateWithValidPet() {
        Pet pet = new Pet();

        when(petRepository.findById(1)).thenReturn(Optional.of(pet));

        String actual = controller.setupUpdate(1, 2, model, redirectAttributes);

        verify(model).put("pet", pet);
        assertEquals("pets/createOrUpdatePetForm", actual);
    }

    @Test
    public void shouldSetupUpdateWithOutValidPet() {
        Message message = new Message(danger, "Please select an existing pet.");

        when(petRepository.findById(1)).thenReturn(Optional.empty());

        String actual = controller.setupUpdate(1, 2, model, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute("message", message);
        assertEquals("redirect:/owners/2", actual);
    }
}
