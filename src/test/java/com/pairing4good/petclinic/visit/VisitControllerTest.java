package com.pairing4good.petclinic.visit;

import com.pairing4good.petclinic.pet.Pet;
import com.pairing4good.petclinic.pet.PetRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VisitControllerTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private WebDataBinder webDataBinder;

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private BindingResult bindingResult;

    private VisitController controller;

    @Before
    public void setUp() {
        controller = new VisitController(visitRepository, petRepository);
    }

    @Test
    public void shouldLoadPetWithVisit() {
        Pet pet = new Pet();
        when(petRepository.findById(1)).thenReturn(Optional.of(pet));

        HashMap<String, Object> model = new HashMap<>();

        Visit visit = controller.loadPetWithVisit(1, model);

        assertSame(pet, model.get("pet"));
        assertEquals(visit, pet.getVisits().iterator().next());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotLoadPetWithVisitWhenPetMissing() {
        when(petRepository.findById(1)).thenReturn(Optional.empty());

        controller.loadPetWithVisit(1, new HashMap<>());
    }

    @Test
    public void shouldSetAllowedFields() {
        controller.setAllowedFields(webDataBinder);

        verify(webDataBinder).setDisallowedFields("id");
    }

    @Test
    public void shouldSetupNewVist() {
        String actual = controller.setupSave(1, new HashMap<>());

        assertEquals("visits/createOrUpdateVisitForm", actual);
    }

    @Test
    public void shouldReturnToPetVewWhenPetDoesNotExist() {
        String actual = controller.setupSave(1, new HashMap<>());

        assertEquals("visits/createOrUpdateVisitForm", actual);
    }

    @Test
    public void shouldSaveValidVisit() {
        Visit visit = new Visit();

        when(bindingResult.hasErrors()).thenReturn(false);

        String actual = controller.save(visit, bindingResult);

        verify(visitRepository).save(visit);

        assertEquals("redirect:/owners/{ownerId}", actual);
    }

    @Test
    public void shouldNotSaveInvalidVisit() {
        Visit visit = new Visit();

        when(bindingResult.hasErrors()).thenReturn(true);

        String actual = controller.save(visit, bindingResult);

        verify(visitRepository, never()).save(visit);

        assertEquals("visits/createOrUpdateVisitForm", actual);
    }
}
