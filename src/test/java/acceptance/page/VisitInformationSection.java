package acceptance.page;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import static acceptance.HtmlUnitUtil.textForId;
import static org.junit.Assert.assertEquals;

public class VisitInformationSection {

    private int visitId;
    private HtmlPage page;

    public VisitInformationSection(int visitId, HtmlPage page) {
        this.visitId = visitId;
        this.page = page;
    }

    public VisitInformationSection withDate(String visitDate) {
        assertEquals(visitDate, textForId(page, "visit-date-value-" + visitId));
        return this;
    }

    public VisitInformationSection andDescription(String visitDescription) {
        assertEquals(visitDescription, textForId(page, "visit-description-value-" + visitId));
        return this;
    }
}
