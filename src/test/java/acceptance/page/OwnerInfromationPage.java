package acceptance.page;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import static acceptance.HtmlUnitUtil.hrefForId;
import static acceptance.HtmlUnitUtil.textForId;
import static com.gargoylesoftware.htmlunit.WebAssert.assertElementNotPresent;
import static org.junit.Assert.assertEquals;

public class OwnerInfromationPage {

    private int ownerId;
    private HtmlPage page;

    public OwnerInfromationPage(HtmlPage page) {
        this.page = page;
        String path = page.getUrl().getPath();
        String[] pathParts = StringUtils.split(path, "/");
        this.ownerId = Integer.parseInt(pathParts[pathParts.length - 1]);
    }

    public OwnerInfromationPage(int ownerId, HtmlPage page) {
        this.ownerId = ownerId;
        this.page = page;

        assertEquals("Owner Information", textForId(page, "page-title"));
        assertEquals("Edit Owner", textForId(page, "edit-owner-link"));
        assertEquals(ownerId + "/edit", hrefForId(page, "edit-owner-link"));

        assertEquals("Add New Pet", textForId(page, "add-new-pet-link"));
        assertEquals(ownerId + "/pets/new", hrefForId(page, "add-new-pet-link"));

        assertEquals("Visit Date", textForId(page, "visit-date-label"));
        assertEquals("Description", textForId(page, "visit-description-label"));
    }

    public OwnerInfromationPage withName(String ownerName) {
        assertEquals("Name", textForId(page, "name-label"));
        assertEquals(ownerName, textForId(page, "name-value"));
        return this;
    }

    public OwnerInfromationPage withoutFlashMessage() {
        assertElementNotPresent(page, "flash-message");
        return this;
    }

    public OwnerInfromationPage andAddress(String ownerAddress) {
        assertEquals("Address", textForId(page, "address-label"));
        assertEquals(ownerAddress, textForId(page, "address-value"));
        return this;
    }

    public OwnerInfromationPage andCity(String ownerCity) {
        assertEquals("City", textForId(page, "city-label"));
        assertEquals(ownerCity, textForId(page, "city-value"));
        return this;
    }

    public OwnerInfromationPage andTelephone(String ownerTelephone) {
        assertEquals("Telephone", textForId(page, "telephone-label"));
        assertEquals(ownerTelephone, textForId(page, "telephone-value"));
        return this;
    }

    public PetInfromationPage pet(int petId) {
        withoutPets();
        return new PetInfromationPage(ownerId, petId, page);
    }

    public OwnerInfromationPage andVerifyOwnerInformation() {
        return this;
    }

    public void withoutPets() {
        assertEquals("Pets and Visits", textForId(page, "page-sub-title"));
    }

    public AddOrUpdateOwnerPage editOwner() throws IOException {
        HtmlAnchor editOwner = page.getAnchorByText("Edit Owner");
        return new AddOrUpdateOwnerPage(editOwner.click());
    }

    public OwnerInfromationPage nameIsNow(String ownerName) {
        return withName(ownerName);
    }

    public AddPetPage addNewPet() throws IOException {
        HtmlAnchor editOwner = page.getAnchorByText("Add New Pet");
        return new AddPetPage(editOwner.click());
    }

    public UpdatePetPage editPet(int petId) throws IOException {
        HtmlAnchor editPet = (HtmlAnchor) page.getElementById("edit-pet-link-" + petId);
        return new UpdatePetPage(editPet.click());
    }
}
