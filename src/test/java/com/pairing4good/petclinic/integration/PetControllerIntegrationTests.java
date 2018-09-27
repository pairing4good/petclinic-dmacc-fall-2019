package com.pairing4good.petclinic.integration;

import com.pairing4good.petclinic.message.Message;
import com.pairing4good.petclinic.owner.Owner;
import com.pairing4good.petclinic.owner.OwnerRepository;
import com.pairing4good.petclinic.pet.*;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static com.pairing4good.petclinic.message.Level.danger;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PetController.class,
        includeFilters = @ComponentScan.Filter(
                value = PetTypeFormatter.class,
                type = FilterType.ASSIGNABLE_TYPE))
public class PetControllerIntegrationTests {

    private static final int TEST_OWNER_ID = 1;
    private static final int TEST_PET_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerRepository ownerRepository;

    @MockBean
    private PetRepository petRepository;

    @MockBean
    private PetTypeRepository petTypeRepository;


    @Before
    public void setup() {
        PetType hamster = new PetType();
        hamster.setId(3);
        hamster.setName("hamster");

        Owner owner = new Owner();
        owner.setId(1);

        given(petTypeRepository.findAll()).willReturn(Lists.newArrayList(hamster));
        given(ownerRepository.findById(TEST_OWNER_ID)).willReturn(Optional.of(owner));
        given(petRepository.findById(TEST_PET_ID)).willReturn(Optional.of(new Pet()));
    }

    @Test
    public void shouldHaveDefaultModelAttributes() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}/pets/new", TEST_OWNER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    public void shouldProvideAnEmptyPet() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}/pets/new", TEST_OWNER_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    public void shouldSetupPetEdit() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    public void shouldRedirectWhenPetIdDoesNotExist() throws Exception {
        Message expectedMessage = new Message(danger, "Please select an existing pet.");

        mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, 9))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + TEST_OWNER_ID))
                .andExpect(MockMvcResultMatchers.flash().attribute("message", expectedMessage));
    }

    @Test
    public void shouldSaveValidPets() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID)
                .param("name", "Betty")
                .param("type", "hamster")
                .param("birthDate", "2015-02-12")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    public void shouldNotSaveInvalidPetWithoutType() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID)
                .param("name", "Betty")
                .param("birthDate", "2015-02-12")
        )
                .andExpect(model().attributeHasErrors("pet"))
                .andExpect(model().attributeHasFieldErrors("pet", "type"))
                .andExpect(model().attributeHasFieldErrorCode("pet", "type", "NotNull"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    public void shouldNotSaveInvalidPetWithoutName() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID)
                .param("birthDate", "2015-02-12")
                .param("type", "hamster")
        )
                .andExpect(model().attributeHasErrors("pet"))
                .andExpect(model().attributeHasFieldErrors("pet", "name"))
                .andExpect(model().attributeHasFieldErrorCode("pet", "name", "NotEmpty"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    public void shouldNotSaveInvalidPetWithoutBirthDate() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID)
                .param("name", "Betty")
                .param("type", "hamster")
        )
                .andExpect(model().attributeHasErrors("pet"))
                .andExpect(model().attributeHasFieldErrors("pet", "birthDate"))
                .andExpect(model().attributeHasFieldErrorCode("pet", "birthDate", "NotNull"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    public void shouldUpdateValidPets() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID)
                .param("name", "Betty")
                .param("type", "hamster")
                .param("birthDate", "2015-02-12")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    public void shouldNotUpdateInvalidPets() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID)
                .param("name", "Betty")
                .param("birthDate", "2015/02/12")
        )
                .andExpect(model().attributeHasErrors("pet"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }
}
