package com.it_uatech;

public class PatientWithInjectAnnotation {

    @Inject
    private Glucose glucose;

    public Glucose getGlucose() {
        return glucose;
    }

}
