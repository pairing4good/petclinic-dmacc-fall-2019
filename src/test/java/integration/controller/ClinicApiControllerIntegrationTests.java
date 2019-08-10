package integration.controller;

import com.pairing4good.petclinic.PetclinicApplication;
import com.pairing4good.petclinic.api.ClinicApiController;
import com.pairing4good.petclinic.owner.Owner;
import com.pairing4good.petclinic.owner.OwnerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClinicApiController.class)
@ContextConfiguration(classes = PetclinicApplication.class)
public class ClinicApiControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerRepository ownerRepository;

    @Before
    public void setup() {
        Owner firstOwner = new Owner();
        firstOwner.setId(1);
        firstOwner.setFirstName("FirstOne");
        firstOwner.setLastName("LastOne");
        firstOwner.setAddress("1 Address");
        firstOwner.setCity("CityOne");
        firstOwner.setTelephone("1111111111");

        Owner secondOwner = new Owner();
        secondOwner.setId(2);
        secondOwner.setFirstName("FirstTwo");
        secondOwner.setLastName("LasTwo");
        secondOwner.setAddress("2 Address");
        secondOwner.setCity("CityTwo");
        secondOwner.setTelephone("2222222222");

        given(ownerRepository.findAll()).willReturn(Arrays.asList(firstOwner, secondOwner));
    }

    @Test
    public void shouldListOwnersAsJson() throws Exception {
        mockMvc.perform(get("/api/owners"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{\"id\":1," +
                        "\"firstName\":\"FirstOne\"," +
                        "\"lastName\":\"LastOne\"," +
                        "\"address\":\"1 Address\"," +
                        "\"city\":\"CityOne\"," +
                        "\"telephone\":\"1111111111\"," +
                        "\"new\":false}," +
                        "{\"id\":2," +
                        "\"firstName\":\"FirstTwo\"," +
                        "\"lastName\":\"LasTwo\"," +
                        "\"address\":\"2 Address\"," +
                        "\"city\":\"CityTwo\"," +
                        "\"telephone\":\"2222222222\"," +
                        "\"new\":false}" +
                        "]"));
    }
}
