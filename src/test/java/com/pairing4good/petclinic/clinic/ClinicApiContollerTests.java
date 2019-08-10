package com.pairing4good.petclinic.clinic;

import com.pairing4good.petclinic.api.ClinicApiController;
import com.pairing4good.petclinic.owner.Owner;
import com.pairing4good.petclinic.owner.OwnerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertSame;

@RunWith(MockitoJUnitRunner.class)
public class ClinicApiContollerTests {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private ClinicApiController controller;

    @Test
    public void shouldReturnOwners(){
        Iterable<Owner> expected = new ArrayList<>();

        Mockito.when(ownerRepository.findAll()).thenReturn(expected);

        Iterable<Owner> actual = controller.find();

        assertSame(expected, actual);
    }
}
