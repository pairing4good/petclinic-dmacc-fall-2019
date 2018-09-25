package com.pairing4good.petclinic.model;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class BaseEntityTest {

    @Test
    public void shouldReturnNewWhenIdIsNull() {
        assertTrue(new BaseEntity().isNew());
    }

    @Test
    public void shouldReturnNotNewWhenIdIsNotNull() {
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setId(1);

        assertFalse(baseEntity.isNew());
    }
}
