package integration.repository;

import com.pairing4good.petclinic.PetclinicApplication;
import com.pairing4good.petclinic.visit.Visit;
import com.pairing4good.petclinic.visit.VisitRepository;
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
public class VisitRepositoryTest {

    @Autowired
    private VisitRepository visitRepository;

    @Test
    public void shouldSaveVisit() {
        assertEquals(0, visitRepository.count());

        LocalDate date = LocalDate.now();

        Visit visit = new Visit();
        visit.setDate(date);
        visit.setDescription("test description");
        visit.setPetId(1);

        visitRepository.save(visit);

        assertEquals(1, visitRepository.count());

        assertEquals("test description", visit.getDescription());
        assertSame(date, visit.getDate());
        assertEquals(new Integer(1), visit.getPetId());
    }
}
