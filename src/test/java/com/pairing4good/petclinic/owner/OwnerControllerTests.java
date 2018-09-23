package com.pairing4good.petclinic.owner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OwnerControllerTests {

    @Mock
    private BindingResult bindingResult;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private Model model;

    private OwnerController controller;
    private Map<String, Object> modelMap;

    @Before
    public void setUp() {
        controller = new OwnerController(ownerRepository);
        modelMap = new HashMap<>();
    }

    @Test
    public void shouldSetupAnEmptyNewOwner() {
        String actual = controller.setupSave(modelMap);

        assertTrue(modelMap.containsKey("owner"));
        assertTrue(modelMap.get("owner") instanceof Owner);
        assertEquals("owners/createOrUpdateOwnerForm", actual);
    }

    @Test
    public void shouldSaveValidOwner() {
        Owner owner = new Owner();
        owner.setId(1);

        when(bindingResult.hasErrors()).thenReturn(false);

        String actual = controller.save(owner, bindingResult);

        verify(ownerRepository).save(owner);
        assertEquals("redirect:/owners/1", actual);
    }

    @Test
    public void shouldNotSaveInvalidOwner() {
        Owner owner = new Owner();
        owner.setId(1);

        when(bindingResult.hasErrors()).thenReturn(true);

        String actual = controller.save(owner, bindingResult);

        verify(ownerRepository, never()).save(owner);
        assertEquals("owners/createOrUpdateOwnerForm", actual);
    }

    @Test
    public void shouldSetupOwnerFind() {
        String actual = controller.setupFind(modelMap);

        assertTrue(modelMap.containsKey("owner"));
        assertTrue(modelMap.get("owner") instanceof Owner);
        assertEquals("owners/findOwners", actual);
    }

    @Test
    public void shouldRetrieveExistingOwnerById() {
        Owner expected = new Owner();
        Optional<Owner> optionalExpected = Optional.of(expected);
        when(ownerRepository.findById(1)).thenReturn(optionalExpected);

        ModelAndView actual = controller.findById(1);

        Map<String, Object> model = actual.getModel();

        assertEquals("owners/ownerDetails", actual.getViewName());
        assertSame(expected, model.get("owner"));
    }

    @Test
    public void shouldEditExistingOwnerById() {
        Owner expected = new Owner();
        Optional<Owner> optionalExpected = Optional.of(expected);
        when(ownerRepository.findById(1)).thenReturn(optionalExpected);

        String actual = controller.setupEdit(1, model);

        verify(model).addAttribute(expected);
        assertEquals("owners/createOrUpdateOwnerForm", actual);
    }
}
