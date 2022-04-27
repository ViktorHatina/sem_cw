package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    static App app;

    @BeforeAll
    static void init() {
        app = new App();
    }

    @Test
    void printReportNull() {
        app.printReport(null);
    }

    @Test
    void printAllCountriesTestNull() {
        app.printAllCountries(null);
    }

    @Test
    void printAllCountriesTestEmpty() {
        app.printAllCountries(new ArrayList<>());
    }

    @Test
    void printAllCountriesTestContainsNull() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        app.printAllCountries(countries);
    }
}
