package integration.repository;

import com.pairing4good.petclinic.PetclinicApplication;
import com.pairing4good.petclinic.owner.Owner;
import com.pairing4good.petclinic.pet.Pet;
import com.pairing4good.petclinic.pet.PetRepository;
import com.pairing4good.petclinic.pet.PetType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = PetclinicApplication.class)
public class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;

    @Test
    public void shouldSavePet() {
        assertEquals(13, petRepository.count());

        Owner owner = new Owner();
        owner.setId(1);

        PetType petType = new PetType();
        petType.setName("testPetType");
        petType.setId(1);

        LocalDate birthDate = LocalDate.now();

        Pet entity = new Pet();
        entity.setName("testPetName");
        entity.setOwner(owner);
        entity.setBirthDate(birthDate);
        entity.setType(petType);

        Pet pet = petRepository.save(entity);

        assertEquals(14, petRepository.count());

        assertEquals("testPetName", pet.getName());
        assertSame(owner, pet.getOwner());
        assertSame(birthDate, pet.getBirthDate());
        assertSame(petType, pet.getType());
    }
}
