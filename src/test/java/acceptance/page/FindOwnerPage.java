package acceptance.page;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.List;

import static acceptance.HtmlUnitUtil.*;
import static com.gargoylesoftware.htmlunit.WebAssert.assertElementNotPresent;
import static org.junit.Assert.assertEquals;

public class FindOwnerPage {

    private final HtmlForm form;
    private HtmlPage page;

    public FindOwnerPage(HtmlPage page) {
        this.page = page;
        assertEquals("Find Owners", textForId(page, "page-title"));
        assertEquals("Last name", textForId(page, "search-label"));
        assertEquals("Find Owner", textForName(page, "find-owner-button"));

        form = page.getHtmlElementById("search-owner-form");
    }

    public FindOwnerPage witoutFlashMessage() {
        assertElementNotPresent(page, "flash-message");

        return this;
    }

    public FindOwnerPage withNoLastName() {
        populate(form, "lastName", "");
        return this;
    }


    public FindOwnerPage findOwners() throws IOException {
        HtmlButton button = form.getButtonByName("find-owner-button");
        page = button.click();
        return this;
    }

    public FindOwnerPage returnsResults(int expectedNumberOfResults) {
        List<DomElement> ownerRows = page.getElementsByName("owner-row");
        assertEquals(expectedNumberOfResults, ownerRows.size());
        return this;
    }

    public FindOwnerPage with(int ownerId, String ownerName, String ownerAddress, String ownerCity,
                              String ownerTelephone, String ownerPets) {
        assertEquals(ownerName, textForId(page, "name-field-" + ownerId));
        assertEquals("/owners/" + ownerId, hrefForId(page, "name-link-" + ownerId));
        assertEquals(ownerAddress, textForId(page, "address-field-" + ownerId));
        assertEquals(ownerCity, textForId(page, "city-field-" + ownerId));
        assertEquals(ownerTelephone, textForId(page, "telephone-field-" + ownerId));
        assertEquals(ownerPets, textForId(page, "pets-field-" + ownerId));
        return this;
    }

    public FindOwnerPage and(int ownerId, String ownerName, String ownerAddress, String ownerCity,
                             String ownerTelephone, String ownerPets) {
        return with(ownerId, ownerName, ownerAddress, ownerCity, ownerTelephone, ownerPets);
    }
}
