package com.pairing4good.petclinic.visit;

import com.pairing4good.petclinic.message.Message;
import com.pairing4good.petclinic.pet.Pet;
import com.pairing4good.petclinic.pet.PetRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static com.pairing4good.petclinic.message.Level.danger;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class VisitControllerTest {


    @Mock
    private PetRepository petRepository;

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private ModelMap modelMap;

    private VisitController controller;

    @Before
    public void setUp() {
        controller = new VisitController(visitRepository, petRepository);
    }

    @Test
    public void shouldSetupNewVist() {
        Visit visit = new Visit();
        Pet pet = new Pet();

        when(petRepository.findById(1)).thenReturn(Optional.of(pet));

        String actual = controller.setupNew(1, 2, visit, modelMap, redirectAttributes);

        assertEquals("visits/createOrUpdateVisitForm", actual);
    }

    @Test
    public void shouldReturnToPetVewWhenPetDoesNotExist() {
        Message message = new Message(danger, "Please select an existing pet.");

        Visit visit = new Visit();

        when(petRepository.findById(1)).thenReturn(Optional.empty());

        String actual = controller.setupNew(1, 2, visit, modelMap, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute("message", message);
        assertEquals("owners/2", actual);
    }
}
