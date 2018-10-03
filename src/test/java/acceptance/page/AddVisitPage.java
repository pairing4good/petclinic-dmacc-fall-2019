package acceptance.page;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

import static acceptance.HtmlUnitUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddVisitPage {

    private final HtmlForm form;
    private HtmlPage page;

    public AddVisitPage(HtmlPage page) {
        this.page = page;

        assertEquals("New Visit", textForId(page, "page-title"));
        assertEquals("Pet", textForId(page, "pet-subtitle"));
        assertEquals("Name", textForId(page, "pet-name-heading"));
        assertEquals("Birth Date", textForId(page, "pet-birth-date-heading"));
        assertEquals("Type", textForId(page, "pet-type-heading"));
        assertEquals("Owner", textForId(page, "pet-owner-heading"));

        assertTrue(containsLabelWithText(page, "Date"));
        assertTrue(containsLabelWithText(page, "Description"));

        assertEquals("Date", textForId(page, "pet-visit-date-column"));
        assertEquals("Description", textForId(page, "pet-visit-description-column"));


        form = page.getHtmlElementById("visit-form");
    }

    public AddVisitPage forPetName(String petName) {
        assertEquals(petName, textForId(page, "pet-name"));
        return this;
    }

    public AddVisitPage andForPetBirthDate(String petBirthDate) {
        assertEquals(petBirthDate, textForId(page, "pet-birth-date"));
        return this;
    }

    public AddVisitPage andForPetType(String type) {
        assertEquals(type, textForId(page, "pet-type"));
        return this;
    }

    public AddVisitPage andForPetOwner(String petOwner) {
        assertEquals(petOwner, textForId(page, "pet-owner"));
        return this;
    }

    public AddVisitPage withVisitDate(String visitDate) {
        populate(form, "date", visitDate);
        return this;
    }

    public AddVisitPage andVisitDescription(String visitDescription) {
        populate(form, "description", visitDescription);
        return this;
    }

    public OwnerInfromationPage addNewVisit() throws IOException {
        HtmlButton button = form.getButtonByName("add-visit-button");
        HtmlPage ownerInformation = button.click();

        return new OwnerInfromationPage(ownerInformation);
    }
}
