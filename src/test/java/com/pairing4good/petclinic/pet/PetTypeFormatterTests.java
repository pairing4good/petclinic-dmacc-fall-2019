package com.pairing4good.petclinic.pet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PetTypeFormatterTests {

    @Mock
    private PetTypeRepository petTypeRepository;

    private PetTypeFormatter petTypeFormatter;

    @Before
    public void setup() {
        this.petTypeFormatter = new PetTypeFormatter(petTypeRepository);
    }

    @Test
    public void testPrint() {
        PetType petType = new PetType();
        petType.setName("Hamster");
        String petTypeName = this.petTypeFormatter.print(petType, Locale.ENGLISH);
        assertEquals("Hamster", petTypeName);
    }

    @Test
    public void shouldParse() throws ParseException {
        Mockito.when(petTypeRepository.findAll()).thenReturn(makePetTypes());
        PetType petType = petTypeFormatter.parse("Bird", Locale.ENGLISH);
        assertEquals("Bird", petType.getName());
    }

    @Test(expected = ParseException.class)
    public void shouldThrowParseException() throws ParseException {
        Mockito.when(petTypeRepository.findAll()).thenReturn(makePetTypes());
        petTypeFormatter.parse("Fish", Locale.ENGLISH);
    }

    private List<PetType> makePetTypes() {
        List<PetType> petTypes = new ArrayList<>();
        petTypes.add(new PetType() {
            {
                setName("Dog");
            }
        });
        petTypes.add(new PetType() {
            {
                setName("Bird");
            }
        });
        return petTypes;
    }

}
