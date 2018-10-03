package acceptance;

import com.gargoylesoftware.htmlunit.html.*;

import java.util.List;

public class HtmlUnitUtil {

    public static void populate(HtmlForm form, String fieldName, String value) {
        HtmlInput htmlElementById = form.getInputByName(fieldName);
        htmlElementById.setValueAttribute(value);
    }

    public static String textForId(HtmlPage page, String elementId) {
        DomElement elementById = page.getElementById(elementId);
        return elementById.asText();
    }

    public static String textForName(HtmlPage page, String elementName) {
        DomElement elementById = page.getElementByName(elementName);
        return elementById.asText();
    }

    public static String hrefForId(HtmlPage page, String elementId) {
        HtmlAnchor elementById = (HtmlAnchor) page.getElementById(elementId);
        return elementById.getHrefAttribute();
    }

    public static boolean containsLabelWithText(HtmlPage page, String text) {
        List byXPath = page.getByXPath("//label[contains(text(), '" + text + "')]");
        return byXPath.size() == 1;
    }
}
