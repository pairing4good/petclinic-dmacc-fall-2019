package com.pairing4good.petclinic.integration;

import com.pairing4good.petclinic.message.Message;
import com.pairing4good.petclinic.pet.Pet;
import com.pairing4good.petclinic.pet.PetRepository;
import com.pairing4good.petclinic.visit.VisitController;
import com.pairing4good.petclinic.visit.VisitRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static com.pairing4good.petclinic.message.Level.danger;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = VisitController.class)
public class VisitControllerIntegrationTests {

    private static final int TEST_OWNER_ID = 1;
    private static final int TEST_PET_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitRepository visitRepository;

    @MockBean
    private PetRepository petRepository;

    @Test
    public void shouldProvideAnEmptyVisit() throws Exception {
        given(petRepository.findById(TEST_PET_ID)).willReturn(Optional.of(new Pet()));

        mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/visits/new", TEST_OWNER_ID, TEST_PET_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("visits/createOrUpdateVisitForm"))
                .andExpect(model().attributeExists("visit"));
    }

    @Test
    public void shouldRedirectToOwnerViewWhenPetDoesNotExist() throws Exception {
        Message expectedMessage = new Message(danger, "Please select an existing pet.");


        given(petRepository.findById(TEST_PET_ID)).willReturn(Optional.empty());

        mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/visits/new", TEST_OWNER_ID, TEST_PET_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + TEST_OWNER_ID))
                .andExpect(MockMvcResultMatchers.flash().attribute("message", expectedMessage));
    }
}
