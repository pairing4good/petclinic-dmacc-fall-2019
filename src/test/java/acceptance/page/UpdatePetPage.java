package acceptance.page;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class UpdatePetPage extends AddPetPage {

    public UpdatePetPage(HtmlPage page) {
        super(page);
    }

    @Override
    protected String pageTitle() {
        return "Pet";
    }
}
