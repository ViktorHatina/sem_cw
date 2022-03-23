package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    @Test
    void printReportNull()
    {
        app.printReport(null);
    }

    @Test
    void printReportEmpty()
    {
        ArrayList<String> s = new ArrayList<String>();
        app.printReport(null);
    }

    @Test
    void printReportTestContainsNull()
    {
        ArrayList<String> s = new ArrayList<String>();
        s.add(null);
        app.printReport(s);
    }

    @Test
    void printReport()
    {
        ArrayList<String> s = new ArrayList<String>();
        s.add("Test Report");
        app.printReport(s);
    }
}