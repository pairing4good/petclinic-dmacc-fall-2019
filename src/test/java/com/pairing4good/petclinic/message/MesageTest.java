package com.pairing4good.petclinic.message;

import org.junit.Test;

import java.util.Objects;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MesageTest {

    @Test
    public void shouldDetermineThatTheSameObjectIsEquivalent() {

        Message messageOne = new Message(Level.danger, "test message");

        assertTrue(messageOne.equals(messageOne));
    }

    @Test
    public void shouldDetermineThatTwoObjectsWithTheSameValuesAreEquivalent() {

        Message messageOne = new Message(Level.danger, "test message");
        Message messageTwo = new Message(Level.danger, "test message");

        assertTrue(messageOne.equals(messageTwo));
    }

    @Test
    public void shouldDetermineThatNullIsNotEquivalent() {

        Message messageOne = new Message(Level.danger, "test message");

        assertFalse(messageOne.equals(null));
    }

    @Test
    public void shouldDetermineThatDifferentClassIsNotEquivalent() {

        Message messageOne = new Message(Level.danger, "test message");

        assertFalse(messageOne.equals("a different class"));
    }

    @Test
    public void shouldHasTheLevelAndMessage() {

        int expected = Objects.hash(Level.danger, "test message");

        Message message = new Message(Level.danger, "test message");

        assertEquals(expected, message.hashCode());
    }

    @Test
    public void shouldReturnTheStringEquivalentOfTheLevelEnum() {
        Message message = new Message(Level.danger, "test message");

        assertEquals("danger", message.getLevel());
    }
}
