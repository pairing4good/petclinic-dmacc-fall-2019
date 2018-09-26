package com.pairing4good.petclinic.owner;

import com.pairing4good.petclinic.message.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

import static com.pairing4good.petclinic.message.Level.danger;
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

    @Mock
    private WebDataBinder webDataBinder;

    @Mock
    private RedirectAttributes redirectAttributes;

    private OwnerController controller;
    private Map<String, Object> modelMap;

    @Before
    public void setUp() {
        controller = new OwnerController(ownerRepository);
        modelMap = new HashMap<>();
    }

    @Test
    public void shouldNotBindId() {
        controller.setAllowedFields(webDataBinder);

        verify(webDataBinder).setDisallowedFields("id");

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

        ModelAndView actual = controller.findById(1, redirectAttributes);

        Map<String, Object> model = actual.getModel();

        assertEquals("owners/ownerDetails", actual.getViewName());
        assertSame(expected, model.get("owner"));
    }

    @Test
    public void shouldSetupCreateWithOutValidOwner() {
        Message message = new Message(danger, "Please select an existing owner.");

        when(ownerRepository.findById(1)).thenReturn(Optional.empty());


        ModelAndView actual = controller.findById(1, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute("message", message);
        assertEquals("redirect:/owners", actual.getViewName());
    }

    @Test
    public void shouldEditExistingOwnerById() {
        Owner expected = new Owner();
        Optional<Owner> optionalExpected = Optional.of(expected);
        when(ownerRepository.findById(1)).thenReturn(optionalExpected);

        String actual = controller.setupUpdate(1, model, redirectAttributes);

        verify(model).addAttribute(expected);
        assertEquals("owners/createOrUpdateOwnerForm", actual);
    }

    @Test
    public void shouldRedirectWhenNonExistingOwnerSelected() {
        Message message = new Message(danger, "Please select an existing owner.");

        when(ownerRepository.findById(1)).thenReturn(Optional.empty());

        String actual = controller.setupUpdate(1, model, redirectAttributes);
        verify(redirectAttributes).addFlashAttribute("message", message);
        assertEquals("redirect:/owners", actual);
    }

    @Test
    public void shouldUpdateValidOwner() {
        Owner owner = new Owner();

        when(bindingResult.hasErrors()).thenReturn(false);

        String actual = controller.update(owner, bindingResult, 1);

        verify(ownerRepository).save(owner);
        assertEquals("redirect:/owners/{ownerId}", actual);
        assertEquals(new Integer(1), owner.getId());
    }

    @Test
    public void shouldNotUpdateInvalidOwner() {
        Owner owner = new Owner();

        when(bindingResult.hasErrors()).thenReturn(true);

        String actual = controller.update(owner, bindingResult, 1);

        verify(ownerRepository, never()).save(owner);
        assertEquals("owners/createOrUpdateOwnerForm", actual);
        assertNull(owner.getId());
    }

    @Test
    public void shouldRejectWhenNoOwnersAreFound() {
        Owner owner = new Owner();
        owner.setLastName("testLastName");

        when(ownerRepository.findByLastNameContainingIgnoreCase("testLastName")).thenReturn(new ArrayList());

        String actual = controller.find(owner, bindingResult, modelMap);

        assertEquals("owners/findOwners", actual);
        verify(bindingResult).rejectValue("lastName", "notFound", "not found");
    }

    @Test
    public void shouldConvertNullLastNameToEmptyString() {
        Owner owner = new Owner();
        owner.setLastName(null);

        String actual = controller.find(owner, bindingResult, modelMap);

        verify(ownerRepository).findByLastNameContainingIgnoreCase("");
    }

    @Test
    public void shouldRedirectToOwnersWhenOneOwnerFound() {
        Owner owner = new Owner();
        owner.setLastName("testLastName");

        List<Owner> foundOwners = new ArrayList();
        Owner foundOwner = new Owner();
        foundOwner.setId(1);
        foundOwners.add(foundOwner);

        when(ownerRepository.findByLastNameContainingIgnoreCase("testLastName")).thenReturn(foundOwners);

        String actual = controller.find(owner, bindingResult, modelMap);

        assertEquals("redirect:/owners/1", actual);
    }

    @Test
    public void shouldRedirectToOwnerListWhenOwnersAreFound() {
        Owner owner = new Owner();
        owner.setLastName("testLastName");

        List<Owner> foundOwners = new ArrayList();

        Owner foundOwnerOne = new Owner();
        foundOwnerOne.setId(1);

        foundOwners.add(foundOwnerOne);

        Owner foundOwnerTwo = new Owner();
        foundOwnerTwo.setId(2);

        foundOwners.add(foundOwnerTwo);

        when(ownerRepository.findByLastNameContainingIgnoreCase("testLastName")).thenReturn(foundOwners);

        String actual = controller.find(owner, bindingResult, modelMap);

        assertEquals("owners/ownersList", actual);
        assertTrue(modelMap.containsKey("selections"));
        assertSame(foundOwners, modelMap.get("selections"));
    }
}
