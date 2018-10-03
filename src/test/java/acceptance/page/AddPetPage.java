package acceptance.page;

import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;

import static acceptance.HtmlUnitUtil.populate;
import static acceptance.HtmlUnitUtil.textForId;
import static com.gargoylesoftware.htmlunit.WebAssert.assertElementPresentByXPath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddPetPage {

    private final HtmlForm form;
    private HtmlPage page;

    public AddPetPage(HtmlPage page) {
        this.page = page;

        assertEquals(pageTitle(), textForId(page, "page-title"));
        assertElementPresentByXPath(page, "//label[contains(text(), 'Owner')]");
        assertElementPresentByXPath(page, "//label[contains(text(), 'Name')]");
        assertElementPresentByXPath(page, "//label[contains(text(), 'Birth Date')]");
        assertElementPresentByXPath(page, "//label[contains(text(), 'Type')]");

        form = page.getHtmlElementById("pet-form");
    }

    protected String pageTitle() {
        return "New Pet";
    }

    public AddPetPage updateNameTo(String name) {
        return withName(name);
    }

    public AddPetPage withName(String name) {
        populate(form, "name", name);
        return this;
    }

    public AddPetPage withBirthDate(String birthDate) {
        populate(form, "birthDate", birthDate);
        return this;
    }

    public AddPetPage withType(String type) {
        HtmlSelect select = (HtmlSelect) page.getElementById("type");
        HtmlOption option = select.getOptionByValue(type);
        select.setSelectedAttribute(option, true);
        return this;
    }

    public OwnerInfromationPage updatePet() throws IOException {
        return addNewPet();
    }

    public OwnerInfromationPage addNewPet() throws IOException {
        HtmlButton button = form.getButtonByName("add-pet-button");
        HtmlPage ownerInformation = button.click();

        return new OwnerInfromationPage(ownerInformation);
    }

    public AddPetPage hasName(String petName) {
        assertEquals(petName, form.getInputByName("name").getValueAttribute());
        return this;
    }

    public AddPetPage hasBirthDate(String birthDate) {
        assertEquals(birthDate, form.getInputByName("birthDate").getValueAttribute());
        return this;
    }

    public AddPetPage hasType(String type) {
        HtmlSelect select = (HtmlSelect) page.getElementById("type");
        HtmlOption option = select.getOptionByValue(type);
        assertTrue(option.isSelected());
        return this;
    }
}
