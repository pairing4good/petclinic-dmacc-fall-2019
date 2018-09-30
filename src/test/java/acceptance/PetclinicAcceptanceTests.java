package acceptance;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pairing4good.petclinic.PetclinicApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static com.gargoylesoftware.htmlunit.WebAssert.assertElementNotPresent;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = PetclinicApplication.class)
public class PetclinicAcceptanceTests {

    private static final String SERVER = "http://localhost";
    @Value("${local.server.port}")
    private String PORT;
    private WebClient webClient;

    @Before
    public void createWebClient() {
        webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
    }

    @Test
    public void shouldShowWelcomePage() throws Exception {
        HtmlPage page = getPage("/");

        assertElementNotPresent(page, "flash-message");

        assertEquals("Welcome", textForId(page, "welcome-message"));
    }

    @Test
    public void shouldShowFindOwnerPage() throws Exception {
        HtmlPage page = getPage("/owners/find");

        assertElementNotPresent(page, "flash-message");

        assertEquals("Find Owners", textForId(page, "page-title"));
        assertEquals("Last name", textForId(page, "search-label"));
        assertEquals("Find Owner", textForId(page, "find-owner-button"));
    }

    @Test
    public void shouldFindAllOwners() throws Exception {
        HtmlPage page = getPage("/owners?lastName=");

        assertElementNotPresent(page, "flash-message");

        assertEquals("Owners", textForId(page, "page-title"));

        assertEquals("Name", textForId(page, "name-column"));
        assertEquals("Address", textForId(page, "address-column"));
        assertEquals("City", textForId(page, "city-column"));
        assertEquals("Telephone", textForId(page, "telephone-column"));
        assertEquals("Pets", textForId(page, "pets-column"));

        assertRow(page, 1, "George Franklin", "110 W. Liberty St.",
                "Madison", "6085551023", "Leo");
        assertRow(page, 2, "Betty Davis", "638 Cardinal Ave.",
                "Sun Prairie", "6085551749", "Basil");
        assertRow(page, 3, "Eduardo Rodriquez", "2693 Commerce St.",
                "McFarland", "6085558763", "Rosy, Jewel");
        assertRow(page, 4, "Harold Davis", "563 Friendly St.",
                "Windsor", "6085553198", "Iggy");
        assertRow(page, 5, "Peter McTavish", "2387 S. Fair Way",
                "Madison", "6085552765", "George");
        assertRow(page, 6, "Jean Coleman", "105 N. Lake St.",
                "Monona", "6085552654", "Samantha, Max");
        assertRow(page, 7, "Jeff Black", "1450 Oak Blvd.",
                "Monona", "6085555387", "Lucky");
        assertRow(page, 8, "Maria Escobito", "345 Maple St.",
                "Madison", "6085557683", "Mulligan");
        assertRow(page, 9, "David Schroeder", "2749 Blackhawk Trail",
                "Madison", "6085559435", "Freddy");
        assertRow(page, 10, "Carlos Estaban", "2335 Independence La.",
                "Waunakee", "6085555487", "Lucky, Sly");
    }

    @Test
    public void shouldDisplayOwner() throws Exception {
        HtmlPage page = getPage("/owners/1");

        assertElementNotPresent(page, "flash-message");

        assertEquals("Owner Information", textForId(page, "page-title"));

        assertEquals("Name", textForId(page, "name-label"));
        assertEquals("George Franklin", textForId(page, "name-value"));

        assertEquals("Address", textForId(page, "address-label"));
        assertEquals("110 W. Liberty St.", textForId(page, "address-value"));

        assertEquals("City", textForId(page, "city-label"));
        assertEquals("Madison", textForId(page, "city-value"));

        assertEquals("Telephone", textForId(page, "telephone-label"));
        assertEquals("6085551023", textForId(page, "telephone-value"));

        assertEquals("Edit Owner", textForId(page, "edit-owner-link"));
        assertEquals("1/edit", hrefForId(page, "edit-owner-link"));

        assertEquals("Add New Pet", textForId(page, "add-new-pet-link"));
        assertEquals("1/pets/new", hrefForId(page, "add-new-pet-link"));

        assertEquals("Pets and Visits", textForId(page, "page-sub-title"));

        assertEquals("Name", textForId(page, "pet-name-label-1"));
        assertEquals("Leo", textForId(page, "pet-name-value-1"));

        assertEquals("Birth Date", textForId(page, "pet-birth-date-label-1"));
        assertEquals("2010-09-07", textForId(page, "pet-birth-date-value-1"));

        assertEquals("Type", textForId(page, "pet-type-label-1"));
        assertEquals("cat", textForId(page, "pet-type-value-1"));

        assertEquals("Visit Date", textForId(page, "visit-date-label"));
        assertEquals("Description", textForId(page, "visit-description-label"));

        assertEquals("Edit Pet", textForId(page, "edit-pet-link"));
        assertEquals("1/pets/1/edit", hrefForId(page, "edit-pet-link"));

        assertEquals("Add Visit", textForId(page, "add-visit-link"));
        assertEquals("1/pets/1/visits/newz", hrefForId(page, "add-visit-link"));
    }

    private void assertRow(HtmlPage page, int ownerId, String ownerName, String ownerAddress, String ownerCity,
                           String ownerTelephone, String ownerPets) {
        assertEquals(ownerName, textForId(page, "name-field-" + ownerId));
        assertEquals("/owners/" + ownerId, hrefForId(page, "name-link-" + ownerId));
        assertEquals(ownerAddress, textForId(page, "address-field-" + ownerId));
        assertEquals(ownerCity, textForId(page, "city-field-" + ownerId));
        assertEquals(ownerTelephone, textForId(page, "telephone-field-" + ownerId));
        assertEquals(ownerPets, textForId(page, "pets-field-" + ownerId));
    }

    private HtmlPage getPage(String path) throws java.io.IOException {
        return webClient.getPage(SERVER + ":" + PORT + path);
    }

    private String textForId(HtmlPage page, String elementId) {
        DomElement elementById = page.getElementById(elementId);
        return elementById.asText();
    }

    private String hrefForId(HtmlPage page, String elementId) {
        HtmlAnchor elementById = (HtmlAnchor) page.getElementById(elementId);
        return elementById.getHrefAttribute();
    }

}
