package com.pairing4good.petclinic.owner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OwnerControllerTests {

    @Mock
    private BindingResult bindingResult;

    @Mock
    private OwnerRepository ownerRepository;

    private OwnerController controller;
    private Map<String, Object> model;

    @Before
    public void setUp() {
        controller = new OwnerController(ownerRepository);
        model = new HashMap<>();
    }

    @Test
    public void shouldSetupAnEmptyNewOwner() {
        String actual = controller.setupSave(model);

        assertTrue(model.containsKey("owner"));
        assertTrue(model.get("owner") instanceof Owner);
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
        String actual = controller.setupFind(model);

        assertTrue(model.containsKey("owner"));
        assertTrue(model.get("owner") instanceof Owner);
        assertEquals("owners/findOwners", actual);
    }
}
