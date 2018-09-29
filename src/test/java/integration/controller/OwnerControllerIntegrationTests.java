package integration.controller;

import com.pairing4good.petclinic.PetclinicApplication;
import com.pairing4good.petclinic.message.Message;
import com.pairing4good.petclinic.owner.Owner;
import com.pairing4good.petclinic.owner.OwnerController;
import com.pairing4good.petclinic.owner.OwnerRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static com.pairing4good.petclinic.message.Level.danger;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(OwnerController.class)
@ContextConfiguration(classes = PetclinicApplication.class)
public class OwnerControllerIntegrationTests {

    private static final int TEST_OWNER_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerRepository ownerRepository;

    private Owner owner;

    @Before
    public void setup() {
        owner = new Owner();
        owner.setId(TEST_OWNER_ID);
        owner.setFirstName("George");
        owner.setLastName("Franklin");
        owner.setAddress("110 W. Liberty St.");
        owner.setCity("Madison");
        owner.setTelephone("6085551023");
        Optional<Owner> optionalOwner = Optional.of(owner);
        given(ownerRepository.findById(TEST_OWNER_ID)).willReturn(optionalOwner);
    }

    @Test
    public void shouldProvideAnEmptyOwner() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    public void shouldSaveValidOwners() throws Exception {
        mockMvc.perform(post("/owners/new")
                .param("firstName", "Joe")
                .param("lastName", "Bloggs")
                .param("address", "123 Caramel Street")
                .param("city", "London")
                .param("telephone", "01316761638")
        )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void shouldNotSaveInalidOwners() throws Exception {
        mockMvc.perform(post("/owners/new")
                .param("firstName", "")
                .param("lastName", "Bloggs")
                .param("address", "123 Caramel Street")
                .param("city", "London")
                .param("telephone", "01316761638")
        )
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    public void shouldSetupOwnerFind() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    public void shouldRetrieveOwnerById() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}", TEST_OWNER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("owner", hasProperty("lastName", is("Franklin"))))
                .andExpect(model().attribute("owner", hasProperty("firstName", is("George"))))
                .andExpect(model().attribute("owner", hasProperty("address", is("110 W. Liberty St."))))
                .andExpect(model().attribute("owner", hasProperty("city", is("Madison"))))
                .andExpect(model().attribute("owner", hasProperty("telephone", is("6085551023"))))
                .andExpect(view().name("owners/ownerDetails"));
    }

    @Test
    public void shouldRedirectWhenOwnerIdMissing() throws Exception {
        Message expectedMessage = new Message(danger, "Please select an existing owner.");

        mockMvc.perform(get("/owners/{ownerId}", 99))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners"))
                .andExpect(MockMvcResultMatchers.flash().attribute("message", expectedMessage));
    }

    @Test
    public void shouldSetupOwnerEdit() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}/edit", TEST_OWNER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attribute("owner", hasProperty("lastName", is("Franklin"))))
                .andExpect(model().attribute("owner", hasProperty("firstName", is("George"))))
                .andExpect(model().attribute("owner", hasProperty("address", is("110 W. Liberty St."))))
                .andExpect(model().attribute("owner", hasProperty("city", is("Madison"))))
                .andExpect(model().attribute("owner", hasProperty("telephone", is("6085551023"))))
                .andExpect(model().attribute("owner", hasProperty("id", is(TEST_OWNER_ID))))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    public void shouldRedirectMissingOwnerId() throws Exception {
        Message expectedMessage = new Message(danger, "Please select an existing owner.");

        mockMvc.perform(get("/owners/{ownerId}/edit", 99))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners"))
                .andExpect(MockMvcResultMatchers.flash().attribute("message", expectedMessage));
    }

    @Test
    public void shouldUpdateValidOwner() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID)
                .param("firstName", "Joe")
                .param("lastName", "Bloggs")
                .param("address", "123 Caramel Street")
                .param("city", "London")
                .param("telephone", "01616291589")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    public void shouldNotUpdateInvalidOwner() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID)
                .param("firstName", "Joe")
                .param("lastName", "Bloggs")
                .param("city", "London")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner", "address"))
                .andExpect(model().attributeHasFieldErrorCode("owner", "address", "NotEmpty"))
                .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
                .andExpect(model().attributeHasFieldErrorCode("owner", "telephone", "NotEmpty"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }


    @Test
    public void testProcessFindFormByLastName() throws Exception {
        given(ownerRepository.findByLastNameContainingIgnoreCase(owner.getLastName())).willReturn(Lists.newArrayList(owner));
        mockMvc.perform(get("/owners")
                .param("lastName", "Franklin")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + TEST_OWNER_ID));
    }

    @Test
    public void testProcessFindFormNoOwnersFound() throws Exception {
        mockMvc.perform(get("/owners")
                .param("lastName", "Unknown Surname")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("owner", "lastName"))
                .andExpect(model().attributeHasFieldErrorCode("owner", "lastName", "notFound"))
                .andExpect(view().name("owners/findOwners"));
    }
}
