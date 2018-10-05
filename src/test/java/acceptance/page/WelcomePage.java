package acceptance.page;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import static acceptance.HtmlUnitUtil.textForId;
import static com.gargoylesoftware.htmlunit.WebAssert.assertElementNotPresent;
import static org.junit.Assert.assertEquals;

public class WelcomePage {

    private HtmlPage page;

    public WelcomePage(HtmlPage page) {
        this.page = page;
    }

    public WelcomePage withoutFlashMessage() {
        assertElementNotPresent(page, "flash-message");
        return this;
    }

    public WelcomePage withWelcomeMessage(String message) {
        assertEquals(message, textForId(page, "welcome-message"));
        return this;
    }

    public WelcomePage withClinicName(String clinicName) {
        assertEquals(clinicName, textForId(page, "clinic-name"));
        return this;
    }

    public WelcomePage withClinicAddress(String clinicAddress) {
        assertEquals(clinicAddress, textForId(page, "clinic-address"));
        return this;
    }

    public WelcomePage withClinicCityStateZip(String clinicCityStateZip) {
        assertEquals(clinicCityStateZip, textForId(page, "clinic-city-state-zip"));
        return this;
    }

    public WelcomePage withClinicTelephone(String clinicTelephone) {
        assertEquals(clinicTelephone, textForId(page, "clinic-telephone"));
        return this;
    }
}
