package acceptance.page;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

import static acceptance.HtmlUnitUtil.populate;
import static acceptance.HtmlUnitUtil.textForId;
import static com.gargoylesoftware.htmlunit.WebAssert.assertElementNotPresent;
import static com.gargoylesoftware.htmlunit.WebAssert.assertElementPresentByXPath;
import static org.junit.Assert.assertEquals;

public class AddOrUpdateOwnerPage {

    private HtmlPage page;
    private HtmlForm form;

    public AddOrUpdateOwnerPage(HtmlPage page) {
        this.page = page;
        assertEquals("Owner", textForId(page, "page-title"));
        assertElementPresentByXPath(page, "//label[contains(text(), 'First Name')]");
        assertElementPresentByXPath(page, "//label[contains(text(), 'Last Name')]");
        assertElementPresentByXPath(page, "//label[contains(text(), 'Address')]");
        assertElementPresentByXPath(page, "//label[contains(text(), 'City')]");
        assertElementPresentByXPath(page, "//label[contains(text(), 'Telephone')]");

        form = page.getHtmlElementById("add-owner-form");
    }

    public AddOrUpdateOwnerPage withoutFlashMessage() {
        assertElementNotPresent(page, "flash-message");
        return this;
    }

    public AddOrUpdateOwnerPage updateFirstNameTo(String ownerFirstName) {
        return withFirstName(ownerFirstName);
    }

    public AddOrUpdateOwnerPage withFirstName(String ownerFirstName) {
        populate(form, "firstName", ownerFirstName);
        return this;
    }

    public AddOrUpdateOwnerPage withLastName(String ownerLastName) {
        populate(form, "lastName", ownerLastName);
        return this;
    }

    public AddOrUpdateOwnerPage withAddress(String ownerAddress) {
        populate(form, "address", ownerAddress);
        return this;
    }

    public AddOrUpdateOwnerPage withCity(String ownerCity) {
        populate(form, "city", ownerCity);
        return this;
    }

    public AddOrUpdateOwnerPage withTelephone(String ownerTelephone) {
        populate(form, "telephone", ownerTelephone);
        return this;
    }

    public OwnerInfromationPage updateOwner() throws IOException {
        return addNewOwner();
    }

    public OwnerInfromationPage addNewOwner() throws IOException {
        HtmlButton button = form.getButtonByName("add-owner-button");
        HtmlPage ownerInformation = button.click();

        return new OwnerInfromationPage(ownerInformation);
    }

    public AddOrUpdateOwnerPage hasFirstName(String owenrFirstName) {
        assertEquals(owenrFirstName, form.getInputByName("firstName").getValueAttribute());
        return this;
    }

    public AddOrUpdateOwnerPage hasLastName(String ownerLastName) {
        assertEquals(ownerLastName, form.getInputByName("lastName").getValueAttribute());
        return this;
    }

    public AddOrUpdateOwnerPage hasAddress(String ownerAddress) {
        assertEquals(ownerAddress, form.getInputByName("address").getValueAttribute());
        return this;
    }

    public AddOrUpdateOwnerPage hasCity(String ownerCity) {
        assertEquals(ownerCity, form.getInputByName("city").getValueAttribute());
        return this;
    }

    public AddOrUpdateOwnerPage hasTelephone(String ownerTelephone) {
        assertEquals(ownerTelephone, form.getInputByName("telephone").getValueAttribute());
        return this;
    }
}
