package com.it_uatech;

import com.it_uatech.newpack.Spirt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NurseTest {
    Nurse nurse;

    @Before
    public void setUp() {
        this.nurse = new Nurse();
    }


    @Test
    public void shouldBuildRegister() {
        Register register = nurse.register(PatientWithInjectAnnotation.class)
                .register(new Glucose())
                .register(new Water())
                .build();

        Glucose glucose = register.get(Glucose.class).get();
        PatientWithInjectAnnotation patient = register.get(PatientWithInjectAnnotation.class).get();
        Assert.assertEquals(glucose, patient.getGlucose());
    }

    @Test
    public void shouldScanPackagesForCures() {

        nurse.scan("com.it_uatech");

        Register reg = nurse.build();
        Assert.assertTrue(reg.get(Glucose.class).isPresent());
        Assert.assertTrue(reg.get(Water.class).isPresent());
    }

    @Test
    public void shouldScanPackagesRecursivelyForCures() {

        nurse.scan("com.it_uatech");

        Register reg = nurse.build();
        Assert.assertTrue(reg.get(Glucose.class).isPresent());
        Assert.assertTrue(reg.get(Water.class).isPresent());
        Assert.assertFalse((reg.get(PatientWithInjectAnnotation.class).isPresent()));
        Assert.assertTrue(reg.get(Spirt.class).isPresent());
    }

}
