package acceptance;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PetclinicAcceptanceTests extends BaseAcceptanceTests {

    @Before
    public void setUp() {
        createWebClient();
    }

    @Test
    public void shouldShowWelcomePage() throws Exception {
        navigateToWelcome()
                .withoutFlashMessage()
                .withWelcomeMessage("Welcome");
    }

    @Test
    public void shouldFindAllOwners() throws Exception {
        navigateToFindOwner()
                .witoutFlashMessage()
                .withNoLastName()
                .findOwners()
                .returnsResults(10)
                .with(1, "George Franklin", "110 W. Liberty St.",
                        "Madison", "6085551023", "Leo")
                .and(2, "Betty Davis", "638 Cardinal Ave.",
                        "Sun Prairie", "6085551749", "Basilz")
                .and(3, "Eduardo Rodriquez", "2693 Commerce St.",
                        "McFarland", "6085558763", "Rosy, Jewel")
                .and(4, "Harold Davis", "563 Friendly St.",
                        "Windsor", "6085553198", "Iggy")
                .and(5, "Peter McTavish", "2387 S. Fair Way",
                        "Madison", "6085552765", "George")
                .and(6, "Jean Coleman", "105 N. Lake St.",
                        "Monona", "6085552654", "Samantha, Max")
                .and(7, "Jeff Black", "1450 Oak Blvd.",
                        "Monona", "6085555387", "Lucky")
                .and(8, "Maria Escobito", "345 Maple St.",
                        "Madison", "6085557683", "Mulligan")
                .and(9, "David Schroeder", "2749 Blackhawk Trail",
                        "Madison", "6085559435", "Freddy")
                .and(10, "Carlos Estaban", "2335 Independence La.",
                        "Waunakee", "6085555487", "Lucky, Sly");
    }

    @Test
    public void shouldDisplayOwner() throws Exception {
        navigateToOwnerInformation(1)
                .withoutFlashMessage()
                .withName("George Franklin")
                .andAddress("110 W. Liberty St.")
                .andCity("Madison")
                .andTelephone("6085551023")
                .pet(1)
                .withName("Leo")
                .withBirthDate("2010-09-07")
                .withType("cat");
    }

    @Test
    public void shouldAddOwner() throws Exception {
        navigateToAddOwner()
                .withoutFlashMessage()
                .withFirstName("Ftest")
                .withLastName("Ltest")
                .withAddress("100 Test Street")
                .withCity("Test")
                .withTelephone("1111111111")
                .addNewOwner()
                .andVerifyOwnerInformation()
                .withoutFlashMessage()
                .withName("Ftest Ltest")
                .andAddress("100 Test Street")
                .andCity("Test")
                .andTelephone("1111111111")
                .withoutPets();
    }

    @Test
    public void shouldEditOwner() throws Exception {
        navigateToOwnerInformation(1)
                .editOwner()
                .hasFirstName("George")
                .hasLastName("Franklin")
                .hasAddress("110 W. Liberty St.")
                .hasCity("Madison")
                .hasTelephone("6085551023")
                .updateFirstNameTo("Georgie")
                .updateOwner()
                .nameIsNow("Georgie Franklin");
    }

    @Test
    public void shouldAddPetToOwner() throws Exception {
        navigateToOwnerInformation(2)
                .addNewPet()
                .withName("Ntest")
                .withBirthDate("2001-01-01")
                .withType("bird")
                .addNewPet()
                .withoutFlashMessage()
                .withName("Betty Davis")
                .andAddress("638 Cardinal Ave.")
                .andCity("Sun Prairie")
                .andTelephone("6085551749")
                .pet(14)
                .withName("Ntest")
                .withBirthDate("2001-01-01")
                .withType("bird");
    }

    @Test
    public void shouldEditPet() throws Exception {
        navigateToOwnerInformation(2)
                .editPet(2)
                .hasName("Basil")
                .hasBirthDate("2012-08-06")
                .hasType("hamster")
                .updateNameTo("Basilz")
                .updatePet()
                .pet(2)
                .withName("Basilz");
    }

    @Test
    public void shouldAddNewPetClinicVisit() throws Exception {
        navigateToOwnerInformation(3)
                .addNewVisit(4)
                .forPetName("Jewel")
                .andForPetBirthDate("2010-03-07")
                .andForPetType("dog")
                .andForPetOwner("Eduardo Rodriquez")
                .withVisitDate("2018-08-08")
                .andVisitDescription("Test Description")
                .addNewVisit()
                .visit(1)
                .withDate("2018-08-08")
                .andDescription("Test Description");
    }
}
