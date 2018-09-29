package integration.repository;

import com.pairing4good.petclinic.PetclinicApplication;
import com.pairing4good.petclinic.owner.Owner;
import com.pairing4good.petclinic.owner.OwnerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = PetclinicApplication.class)
public class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    public void shouldSaveOwner() {

        assertEquals(10, ownerRepository.count());

        Owner entity = new Owner();
        entity.setFirstName("testFirstName");
        entity.setLastName("testLastName");
        entity.setAddress("test address");
        entity.setCity("testCity");
        entity.setTelephone("1234567890");

        Owner owner = ownerRepository.save(entity);

        assertEquals(11, ownerRepository.count());

        assertEquals("testFirstName", owner.getFirstName());
        assertEquals("testLastName", owner.getLastName());
        assertEquals("test address", owner.getAddress());
        assertEquals("testCity", owner.getCity());
        assertEquals("1234567890", owner.getTelephone());
    }
}
