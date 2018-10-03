package acceptance;

import acceptance.page.AddOrUpdateOwnerPage;
import acceptance.page.FindOwnerPage;
import acceptance.page.OwnerInfromationPage;
import acceptance.page.WelcomePage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pairing4good.petclinic.PetclinicApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

import static acceptance.HtmlUnitUtil.hrefForId;
import static acceptance.HtmlUnitUtil.textForId;
import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = PetclinicApplication.class)
public class BaseAcceptanceTests {

    private static final String SERVER = "http://localhost";
    @Value("${local.server.port}")
    private String PORT;
    private WebClient webClient;


    public void createWebClient() {
        setUpWebClient();
    }

    public void setUpWebClient() {
        webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
    }

    protected void assertRow(HtmlPage page, int ownerId, String ownerName, String ownerAddress, String ownerCity,
                             String ownerTelephone, String ownerPets) {
        assertEquals(ownerName, textForId(page, "name-field-" + ownerId));
        assertEquals("/owners/" + ownerId, hrefForId(page, "name-link-" + ownerId));
        assertEquals(ownerAddress, textForId(page, "address-field-" + ownerId));
        assertEquals(ownerCity, textForId(page, "city-field-" + ownerId));
        assertEquals(ownerTelephone, textForId(page, "telephone-field-" + ownerId));
        assertEquals(ownerPets, textForId(page, "pets-field-" + ownerId));
    }

    protected HtmlPage getPage(String path) throws java.io.IOException {
        return webClient.getPage(SERVER + ":" + PORT + path);
    }

    protected WelcomePage navigateToWelcome() throws IOException {
        HtmlPage page = getPage("/");
        return new WelcomePage(page);
    }

    protected FindOwnerPage navigateToFindOwner() throws IOException {
        HtmlPage page = getPage("/owners/find");
        return new FindOwnerPage(page);
    }

    protected OwnerInfromationPage navigateToOwnerInformation(int ownerId) throws IOException {
        HtmlPage page = getPage("/owners/" + ownerId);
        return new OwnerInfromationPage(ownerId, page);
    }

    protected AddOrUpdateOwnerPage navigateToAddOwner() throws IOException {
        HtmlPage page = getPage("/owners/new");
        return new AddOrUpdateOwnerPage(page);
    }
}
