package acceptance;

import com.gargoylesoftware.htmlunit.html.*;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class HtmlUnitUtil {

    public static void populate(HtmlForm form, String fieldName, String value) {
        HtmlInput inputByName = form.getInputByName(fieldName);
        assertInputByName(fieldName, inputByName);
        inputByName.setValueAttribute(value);
    }

    private static void assertInputByName(String elementName, HtmlInput inputByName) {
        assertNotNull("An HTML input with name='" + elementName + "' is missing.", inputByName);
    }

    public static String textForId(HtmlPage page, String elementId) {
        DomElement elementById = page.getElementById(elementId);
        assertNotNull("An HTML element with id='" + elementId + "' is missing.", elementById);
        return elementById.asText();
    }

    public static String textForName(HtmlPage page, String elementName) {
        DomElement elementById = page.getElementByName(elementName);
        assertNotNull("An HTML element with name='" + elementName + "' is missing.", elementById);
        return elementById.asText();
    }

    public static String hrefForId(HtmlPage page, String elementId) {
        HtmlAnchor elementById = (HtmlAnchor) page.getElementById(elementId);
        assertNotNull("An HTML anchor link with id='" + elementId + "' is missing.", elementById);
        return elementById.getHrefAttribute();
    }

    public static boolean containsLabelWithText(HtmlPage page, String text) {
        List byXPath = page.getByXPath("//label[contains(text(), '" + text + "')]");
        return byXPath.size() == 1;
    }
}
