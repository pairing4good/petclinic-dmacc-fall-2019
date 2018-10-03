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

    public void withWelcomeMessage(String message) {
        assertEquals(message, textForId(page, "welcome-message"));
    }
}
