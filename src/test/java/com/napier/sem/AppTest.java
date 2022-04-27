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
    void printReportsNull() {
        app.printReport(null);
    }

    @Test
    void printReportEmpty() {
        app.printReport(new ArrayList<>());
    }

    @Test
    void printReportNull() {
        ArrayList<String> s = new ArrayList<>();
        s.add(null);
        app.printReport(s);
    }

    @Test
    void printAllCountriesNull() {
        app.printAllCountries(null);
    }

    @Test
    void printAllCountriesEmpty() {
        app.printAllCountries(new ArrayList<>());
    }

    @Test
    void printAllCountriesContainsNull() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        app.printAllCountries(countries);
    }

    @Test
    void getTopNPopCountriesNegative() {app.getTopNPopCountries(-1);}

    @Test
    void getTopNPopCountriesByContNegative() {app.getTopNPopCountriesByCont(-1, "Europe");}

    @Test
    void getTopNPopCountriesByContInvalidString() {app.getTopNPopCountriesByCont(10, "s");}

    @Test
    void getTopNPopCountriesByContNullString() {app.getTopNPopCountriesByCont(10, null);}

    @Test
    void getTopNPopCountriesByRegNegative() {app.getTopNPopCountriesByReg(-1, "")}
}
