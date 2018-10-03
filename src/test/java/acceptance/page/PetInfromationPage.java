package acceptance.page;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import static acceptance.HtmlUnitUtil.hrefForId;
import static acceptance.HtmlUnitUtil.textForId;
import static org.junit.Assert.assertEquals;


public class PetInfromationPage {
    private HtmlPage page;
    private int petId;

    public PetInfromationPage(int ownerId, int petId, HtmlPage page) {
        this.petId = petId;
        assertEquals("Edit Pet", textForId(page, "edit-pet-link-" + petId));
        assertEquals(ownerId + "/pets/" + petId + "/edit", hrefForId(page, "edit-pet-link-" + petId));

        assertEquals("Add Visit", textForId(page, "add-visit-link-" + petId));
        assertEquals(ownerId + "/pets/" + petId + "/visits/new", hrefForId(page,
                "add-visit-link-" + petId));
        this.page = page;
    }

    public PetInfromationPage withName(String petName) {
        assertEquals("Name", textForId(page, "pet-name-label-" + petId));
        assertEquals(petName, textForId(page, "pet-name-value-" + petId));
        return this;
    }

    public PetInfromationPage withBirthDate(String petBirthDate) {
        assertEquals("Birth Date", textForId(page, "pet-birth-date-label-" + petId));
        assertEquals(petBirthDate, textForId(page, "pet-birth-date-value-" + petId));
        return this;
    }

    public void withType(String petType) {
        assertEquals("Type", textForId(page, "pet-type-label-" + petId));
        assertEquals(petType, textForId(page, "pet-type-value-" + petId));
    }
}
