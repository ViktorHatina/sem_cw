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
    void printAllCountriesEmpty() {
        app.printReport(new ArrayList<>());
    }

    @Test
    void printReportContainsNull() {
        ArrayList<String> r = new ArrayList<>();
        r.add(null);
        app.printReport(r);
    }

    @Test
    void NCountriesinWorldByPopDesNegative() {
        app.NCountriesinWorldByPopDesc(-1);
    }

    @Test
    void NCountriesinContByPopDesNegative() {
        app.NCountriesinContByPopDesc(-1);
    }

    @Test
    void NCountriesinRegionByPopDesNegative() {
        app.NCountriesinRegionByPopDesc(-1);
    }

    @Test
    void NCitiesinWorldByPopDesc() {
        app.NCitiesinWorldByPopDesc(-1);
    }

    @Test
    void NCitiesinContByPopDesc() {
        app.NCitiesinContByPopDesc(-1);
    }

    @Test
    void NCitiesinRegionByPopDesc() {
        app.NCitiesinRegionByPopDesc(-1);
    }

    @Test
    void NCitiesinCountryByPopDesc() {
        app.NCitiesinCountryByPopDesc(-1);
    }

    @Test
    void NCitiesinDistrictByPopDesc() {
        app.NCitiesinDistrictByPopDesc(-1);
    }

    @Test
    void NCapsinWorldByPopDesc() {
        app.NCapsinWorldByPopDesc(-1);
    }

    @Test
    void NCapsinContByPopDesc() {
        app.NCapsinContByPopDesc(-1);
    }

    @Test
    void NCapsinRegionByPopDesc() {
        app.NCapsinRegionByPopDesc(-1);
    }

}
