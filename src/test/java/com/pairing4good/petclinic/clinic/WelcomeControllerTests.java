package com.pairing4good.petclinic.clinic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WelcomeControllerTests {

    @Test
    public void shouldWelcomeForBasePath(){
        WelcomeController controller = new WelcomeController();
        assertEquals("welcome", controller.welcome());
    }
}
