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
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static com.pairing4good.petclinic.message.Level.danger;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PetControllerTests {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private PetRepository petRepository;
    @Mock
    private PetTypeRepository petTypeRepository;

    @Mock
    private ModelMap modelMap;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    private PetController controller;

    @Before
    public void setUp() {
        controller = new PetController(petRepository, petTypeRepository, ownerRepository);
    }

    @Test
    public void shouldSetupAnEmptyNewPet() {
        Owner owner = new Owner();

        String actual = controller.setupSave(owner, modelMap);

        assertEquals("pets/createOrUpdatePetForm", actual);
        verify(modelMap).put(eq("pet"), isA(Pet.class));
        assertNotNull(owner.getPets());
    }

    @Test
    public void shouldSaveValidPet() {
        Owner owner = new Owner();

        Pet pet = new Pet();

        when(bindingResult.hasErrors()).thenReturn(false);

        String actual = controller.save(owner, pet, bindingResult, modelMap);

        assertNotNull(owner.getPets());
        assertTrue(owner.getPets().iterator().hasNext());
        assertSame(pet, owner.getPets().iterator().next());

        verify(petRepository).save(pet);
        assertEquals("redirect:/owners/{ownerId}", actual);
    }

    @Test
    public void shouldNotSaveInvalidPet() {
        Owner owner = new Owner();

        Pet pet = new Pet();

        when(bindingResult.hasErrors()).thenReturn(true);

        String actual = controller.save(owner, pet, bindingResult, modelMap);

        assertNotNull(owner.getPets());
        assertTrue(owner.getPets().iterator().hasNext());
        assertSame(pet, owner.getPets().iterator().next());

        verify(modelMap).put("pet", pet);
        verify(petRepository, never()).save(pet);
        assertEquals("pets/createOrUpdatePetForm", actual);
    }

    @Test
    public void shouldSetupUpdateWithValidPet() {
        Pet pet = new Pet();

        when(petRepository.findById(1)).thenReturn(Optional.of(pet));

        String actual = controller.setupUpdate(1, 2, modelMap, redirectAttributes);

        verify(modelMap).put("pet", pet);
        assertEquals("pets/createOrUpdatePetForm", actual);
    }

    @Test
    public void shouldSetupUpdateWithOutValidPet() {
        Message message = new Message(danger, "Please select an existing pet.");

        when(petRepository.findById(1)).thenReturn(Optional.empty());

        String actual = controller.setupUpdate(1, 2, modelMap, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute("message", message);
        assertEquals("redirect:/owners/2", actual);
    }

    @Test
    public void shouldUpdateValidPet() {
        Owner owner = new Owner();
        Pet pet = new Pet();

        when(bindingResult.hasErrors()).thenReturn(false);

        String actual = controller.update(pet, bindingResult, owner, modelMap);

        assertNotNull(owner.getPets());
        assertSame(pet, owner.getPets().iterator().next());
        verify(petRepository).save(pet);

        assertEquals("redirect:/owners/{ownerId}", actual);
    }

    @Test
    public void shouldNotUpdateInvalidPet() {
        Owner owner = new Owner();
        Pet pet = new Pet();

        when(bindingResult.hasErrors()).thenReturn(true);

        String actual = controller.update(pet, bindingResult, owner, modelMap);

        assertNotNull(pet.getOwner());
        assertSame(owner, pet.getOwner());
        verify(modelMap).put("pet", pet);
        verify(petRepository, never()).save(pet);

        assertEquals("pets/createOrUpdatePetForm", actual);
    }
}
