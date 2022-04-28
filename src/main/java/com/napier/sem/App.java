package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Create new Application and connect to database
        App a = new App();

        if (args.length < 1) {
            a.connect("localhost:3307", 30000);
        } else {
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        //SPECIFY LIMIT FOR REPORTS
        int n  = 3;

        a.printReport(CountriesinWorldByPopDesc());
        a.printReport(CountriesinContByPopDesc());
        a.printReport(CountriesinRegionByPopDesc());

        a.printReport(NCountriesinWorldByPopDesc(n));
        a.printReport(NCountriesinContByPopDesc(n));
        a.printReport(NCountriesinRegionByPopDesc(n));

        a.printReport(CitiesinWorldByPopDesc());
        a.printReport(CitiesinContByPopDesc());
        a.printReport(CitiesinRegionByPopDesc());
        a.printReport(CitiesinCountryByPopDesc());
        a.printReport(CitiesinDistrictByPopDesc());

        a.printReport(NCitiesinWorldByPopDesc(n));
        a.printReport(NCitiesinContByPopDesc(n));
        a.printReport(NCitiesinRegionByPopDesc(n));
        a.printReport(NCitiesinCountryByPopDesc(n));
        a.printReport(NCitiesinDistrictByPopDesc(n));

        a.printReport(CapsinWorldByPopDesc());
        a.printReport(CapsinContByPopDesc());
        a.printReport(CapsinRegionByPopDesc());

        a.printReport(NCapsinWorldByPopDesc(n));
        a.printReport(NCapsinContByPopDesc(n));
        a.printReport(NCapsinRegionByPopDesc(n));


        a.printReport(PopulationBreakdownCont());
        a.printReport(PopulationBreakdownRegion());
        a.printReport(PopulationBreakdownCountry());

        System.out.println("World Population: " + getWorldPopulation());
        a.printReport(PopulationContinents());
        a.printReport(PopulationRegions());
        a.printReport(PopulationCountry());
        a.printReport(PopulationDistrict());
        a.printReport(PopulationCities());

        a.printReport(getLanguageReports());

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    private static Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public static void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public static void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Get the World's Population
     */
    public static long getWorldPopulation() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT SUM(country.Population) "
                            + "FROM country";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next()) {
                return rset.getLong(1);
            } else
                return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get world population");
            return 0;
        }
    }

    /**
     * get a list of all region names
     * @return
     */
    public static ArrayList<String> getRegions() {
        ArrayList<String> c = new ArrayList<String>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Region "
                            + "FROM country "
                            + "GROUP BY Region "
                            + "ORDER BY Region DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next()) {
                String s = rset.getString(1);
                c.add(s);
            }
            return c;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Regions");
            return c;
        }
    }

    /**
     * get a list of all continent names
     * @return
     */
    public static ArrayList<String> getContinents() {
        ArrayList<String> c = new ArrayList<String>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Continent "
                            + "FROM country "
                            + "GROUP BY Continent "
                            + "ORDER BY Continent DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next()) {
                String s = rset.getString(1);
                c.add(s);
            }
            return c;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Continents");
            return c;
        }
    }

    /**
     * get a list of all country codes
     * @return
     */
    public static ArrayList<String> getCountries() {
        ArrayList<String> c = new ArrayList<String>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT name "
                            + "FROM country "
                            + "ORDER BY name DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next()) {
                String s = rset.getString(1);
                c.add(s);
            }
            return c;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Countries");
            return c;
        }
    }

    /**
     * get a list of all Districts
     * @return
     */
    public static ArrayList<String> getDistricts() {
        ArrayList<String> c = new ArrayList<String>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT district "
                            + "FROM city "
                            + "GROUP BY district "
                            + "ORDER BY district DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next()) {
                String s = rset.getString(1);
                c.add(s);
            }
            return c;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Districts");
            return c;
        }
    }

    /**
     * Output Reports
     */
    public static void printReport(ArrayList<String> s) {
        if (s == null) {
            System.out.println("No Report to Print");
        } else {
            for (int i = 0; i < s.size(); i++) {
                System.out.println(s.get(i));
            }
        }
    }

    /**
     * Generate reports for each country sorted by population size
     * @return
     */
    public static ArrayList<String> CountriesinWorldByPopDesc() {
        ArrayList<String> output = new ArrayList<String>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Code, Name, Continent, Region, Population, Capital "
                            + "FROM country "
                            + "ORDER BY Population DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            output.add("Countries in the world: ");

            while (rset.next()) {
                String c = rset.getString(1);
                String n = rset.getString(2);
                String con = rset.getString(3);
                String r = rset.getString(4);
                int p = rset.getInt(5);
                String cap = rset.getString(6);
                output.add(c + " | " + n + " | " + con + " | " + r + " | " + p + " | " + cap);
            }
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Countries  in World By Population Descending");
            return output;
        }
    }

    /**
     * Generate reports for each country sorted by population size with limit n
     * @return
     */
    public static ArrayList<String> NCountriesinWorldByPopDesc(int x) {
        ArrayList<String> output = new ArrayList<String>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Code, Name, Continent, Region, Population, Capital "
                            + "FROM country "
                            + "ORDER BY Population DESC "
                            + "LIMIT " + x + " ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            output.add("Countries in the world with limit " + x + ": ");

            while (rset.next()) {
                String c = rset.getString(1);
                String n = rset.getString(2);
                String con = rset.getString(3);
                String r = rset.getString(4);
                int p = rset.getInt(5);
                String cap = rset.getString(6);
                output.add(c + " | " + n + " | " + con + " | " + r + " | " + p + " | " + cap);
            }
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Countries  in World By Population Descending");
            return output;
        }
    }

    /**
     * Generate reports for each country in each continent sorted by population size
     * @return
     */
    public static ArrayList<String> CountriesinContByPopDesc() {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> cont = getContinents();
        for (int i = 0; i < cont.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + "WHERE Continent=\""+cont.get(i)+"\" "
                                + "ORDER BY Population DESC ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Countries in " + cont.get(i) + ":");

                while (rset.next()) {
                    String c = rset.getString(1);
                    String n = rset.getString(2);
                    String con = rset.getString(3);
                    String r = rset.getString(4);
                    int p = rset.getInt(5);
                    String cap = rset.getString(6);
                    output.add(c + " | " + n + " | " + con + " | " + r + " | " + p + " | " + cap);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Countries in Continent By Population Descending");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate reports for each country in each continent sorted by population size with limit n
     * @return
     */
    public static ArrayList<String> NCountriesinContByPopDesc(int x) {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> cont = getContinents();
        for (int i = 0; i < cont.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + "WHERE Continent=\""+cont.get(i)+"\" "
                                + "ORDER BY Population DESC "
                                + "LIMIT " + x + " ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Countries in " + cont.get(i) + "with limit " + x + ":");

                while (rset.next()) {
                    String c = rset.getString(1);
                    String n = rset.getString(2);
                    String con = rset.getString(3);
                    String r = rset.getString(4);
                    int p = rset.getInt(5);
                    String cap = rset.getString(6);
                    output.add(c + " | " + n + " | " + con + " | " + r + " | " + p + " | " + cap);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Countries in Continent By Population Descending");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate reports for each country in each region sorted by population size
     * @return
     */
    public static ArrayList<String> CountriesinRegionByPopDesc() {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> reg = getRegions();
        for (int i = 0; i < reg.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + "WHERE Region=\""+reg.get(i)+"\" "
                                + "ORDER BY Population DESC ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Countries in " + reg.get(i) + ":");

                while (rset.next()) {
                    String c = rset.getString(1);
                    String n = rset.getString(2);
                    String con = rset.getString(3);
                    String r = rset.getString(4);
                    int p = rset.getInt(5);
                    String cap = rset.getString(6);
                    output.add(c + " | " + n + " | " + con + " | " + r + " | " + p + " | " + cap);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Countries in Region By Population Descending");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate reports for each country in each region sorted by population size with limit n
     * @return
     */
    public static ArrayList<String> NCountriesinRegionByPopDesc(int x) {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> reg = getRegions();
        for (int i = 0; i < reg.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + "WHERE Region=\""+reg.get(i)+"\" "
                                + "ORDER BY Population DESC "
                                + "LIMIT " + x + " ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Countries in " + reg.get(i) + " with limit " + x + ": ");

                while (rset.next()) {
                    String c = rset.getString(1);
                    String n = rset.getString(2);
                    String con = rset.getString(3);
                    String r = rset.getString(4);
                    int p = rset.getInt(5);
                    String cap = rset.getString(6);
                    output.add(c + " | " + n + " | " + con + " | " + r + " | " + p + " | " + cap);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Countries in Region By Population Descending");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate reports for each city sorted by population size
     * @return
     */
    public static ArrayList<String> CitiesinWorldByPopDesc() {
        ArrayList<String> output = new ArrayList<String>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.name, country.name, city.district, city.population "
                            + "FROM city "
                            + "JOIN country ON city.countrycode=country.code "
                            + "ORDER BY city.Population DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            output.add("Cities in the world: ");

            while (rset.next()) {
                String n = rset.getString(1);
                String c = rset.getString(2);
                String d = rset.getString(3);
                int p = rset.getInt(4);
                output.add(n + " | " + c + " | " + d + " | " + p);
            }
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Cities in World By Population Descending");
            return output;
        }
    }

    /**
     * Generate reports for each city sorted by population size with limit n
     * @return
     */
    public static ArrayList<String> NCitiesinWorldByPopDesc(int x) {
        ArrayList<String> output = new ArrayList<String>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.name, country.name, city.district, city.population "
                            + "FROM city "
                            + "JOIN country ON city.countrycode=country.code "
                            + "ORDER BY city.Population DESC "
                            + "LIMIT " + x + " ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            output.add("Cities in the world with limit " + x + ": ");

            while (rset.next()) {
                String n = rset.getString(1);
                String c = rset.getString(2);
                String d = rset.getString(3);
                int p = rset.getInt(4);
                output.add(n + " | " + c + " | " + d + " | " + p);
            }
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Cities in World By Population Descending");
            return output;
        }
    }

    /**
     * Generate reports for each city in each continent sorted by population size
     * @return
     */
    public static ArrayList<String> CitiesinContByPopDesc() {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> cont = getContinents();
        for (int i = 0; i < cont.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT city.name, country.name, city.district, city.population "
                                + "FROM city "
                                + "JOIN country ON city.countrycode=country.code "
                                + "WHERE Continent=\""+cont.get(i)+"\" "
                                + "ORDER BY city.Population DESC ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Cities in " + cont.get(i) + ":");

                while (rset.next()) {
                    String n = rset.getString(1);
                    String c = rset.getString(2);
                    String d = rset.getString(3);
                    int p = rset.getInt(4);
                    output.add(n + " | " + c + " | " + d + " | " + p);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Cities in Continent By Population Descending");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate reports for each city in each continent sorted by population size with limit n
     * @return
     */
    public static ArrayList<String> NCitiesinContByPopDesc(int x) {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> cont = getContinents();
        for (int i = 0; i < cont.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT city.name, country.name, city.district, city.population "
                                + "FROM city "
                                + "JOIN country ON city.countrycode=country.code "
                                + "WHERE Continent=\""+cont.get(i)+"\" "
                                + "ORDER BY city.Population DESC "
                                + "LIMIT " + x + " ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Cities in " + cont.get(i) + " with limit " + x + ": ");

                while (rset.next()) {
                    String n = rset.getString(1);
                    String c = rset.getString(2);
                    String d = rset.getString(3);
                    int p = rset.getInt(4);
                    output.add(n + " | " + c + " | " + d + " | " + p);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Cities in Continent By Population Descending");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate reports for each city in each region sorted by population size
     * @return
     */
    public static ArrayList<String> CitiesinRegionByPopDesc() {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> reg = getRegions();
        for (int i = 0; i < reg.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT city.name, country.name, city.district, city.population "
                                + "FROM city "
                                + "JOIN country ON city.countrycode=country.code "
                                + "WHERE Region=\""+reg.get(i)+"\" "
                                + "ORDER BY city.Population DESC ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Cities in " + reg.get(i) + ":");

                while (rset.next()) {
                    String n = rset.getString(1);
                    String c = rset.getString(2);
                    String d = rset.getString(3);
                    int p = rset.getInt(4);
                    output.add(n + " | " + c + " | " + d + " | " + p);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Cities in Region By Population Descending");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate reports for each city in each region sorted by population size with limit n
     * @return
     */
    public static ArrayList<String> NCitiesinRegionByPopDesc(int x) {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> reg = getRegions();
        for (int i = 0; i < reg.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT city.name, country.name, city.district, city.population "
                                + "FROM city "
                                + "JOIN country ON city.countrycode=country.code "
                                + "WHERE Region=\""+reg.get(i)+"\" "
                                + "ORDER BY city.Population DESC "
                                + "LIMIT " + x + " ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Cities in " + reg.get(i) + " with limit " + x + ": ");

                while (rset.next()) {
                    String n = rset.getString(1);
                    String c = rset.getString(2);
                    String d = rset.getString(3);
                    int p = rset.getInt(4);
                    output.add(n + " | " + c + " | " + d + " | " + p);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Cities in Region By Population Descending");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate reports for each city in each country sorted by population size
     * @return
     */
    public static ArrayList<String> CitiesinCountryByPopDesc() {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> c = getCountries();
        for (int i = 0; i < c.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT city.name, country.name, city.district, city.population "
                                + "FROM city "
                                + "JOIN country ON city.countrycode=country.code "
                                + "WHERE country.name=\""+c.get(i)+"\" "
                                + "ORDER BY city.Population DESC ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Cities in " + c.get(i) + ":");

                while (rset.next()) {
                    String n = rset.getString(1);
                    String cou = rset.getString(2);
                    String d = rset.getString(3);
                    int p = rset.getInt(4);
                    output.add(n + " | " + cou + " | " + d + " | " + p);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Cities in Country By Population Descending");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate reports for each city in each country sorted by population size with limit n
     * @return
     */
    public static ArrayList<String> NCitiesinCountryByPopDesc(int x) {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> c = getCountries();
        for (int i = 0; i < c.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT city.name, country.name, city.district, city.population "
                                + "FROM city "
                                + "JOIN country ON city.countrycode=country.code "
                                + "WHERE country.name=\""+c.get(i)+"\" "
                                + "ORDER BY city.Population DESC "
                                + "LIMIT " + x + " ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Cities in " + c.get(i) + " with limit " + x + ": ");

                while (rset.next()) {
                    String n = rset.getString(1);
                    String cou = rset.getString(2);
                    String d = rset.getString(3);
                    int p = rset.getInt(4);
                    output.add(n + " | " + cou + " | " + d + " | " + p);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Cities in Country By Population Descending");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate reports for each city in each district sorted by population size
     * @return
     */
    public static ArrayList<String> CitiesinDistrictByPopDesc() {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> c = getDistricts();
        for (int i = 0; i < c.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT city.name, country.name, city.district, city.population "
                                + "FROM city "
                                + "JOIN country ON city.countrycode=country.code "
                                + "WHERE city.district=\""+c.get(i)+"\" "
                                + "ORDER BY city.Population DESC ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Cities in " + c.get(i) + ":");

                while (rset.next()) {
                    String n = rset.getString(1);
                    String cou = rset.getString(2);
                    String d = rset.getString(3);
                    int p = rset.getInt(4);
                    output.add(n + " | " + cou + " | " + d + " | " + p);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Cities in District By Population Descending");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate reports for each city in each district sorted by population size with limit n
     * @return
     */
    public static ArrayList<String> NCitiesinDistrictByPopDesc(int x) {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> c = getDistricts();
        for (int i = 0; i < c.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT city.name, country.name, city.district, city.population "
                                + "FROM city "
                                + "JOIN country ON city.countrycode=country.code "
                                + "WHERE city.district=\""+c.get(i)+"\" "
                                + "ORDER BY city.Population DESC "
                                + "LIMIT " + x + " ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Cities in " + c.get(i) + " with limit " + x + ": ");

                while (rset.next()) {
                    String n = rset.getString(1);
                    String cou = rset.getString(2);
                    String d = rset.getString(3);
                    int p = rset.getInt(4);
                    output.add(n + " | " + cou + " | " + d + " | " + p);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Cities in District By Population Descending");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate reports for each capital city sorted by population size
     * @return
     */
    public static ArrayList<String> CapsinWorldByPopDesc() {
        ArrayList<String> output = new ArrayList<String>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                     "SELECT city.name, country.name, city.population "
                    + "FROM country "
                    + "JOIN city ON country.capital=city.ID "
                    + "ORDER BY city.population DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            output.add("Capital Cities in the world: ");

            while (rset.next()) {
                String n = rset.getString(1);
                String c = rset.getString(2);
                int p = rset.getInt(3);
                output.add(n + " | " + c + " | " + p);
            }
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Capitals in World By Population Descending");
            return output;
        }
    }

    /**
     * Generate reports for each capital city sorted by population size with limit n
     * @return
     */
    public static ArrayList<String> NCapsinWorldByPopDesc(int x) {
        ArrayList<String> output = new ArrayList<String>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.name, country.name, city.population "
                            + "FROM country "
                            + "JOIN city ON country.capital=city.ID "
                            + "ORDER BY city.population DESC "
                            + "LIMIT " + x + " ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            output.add("Capital Cities in the world with limit " + x + ": ");

            while (rset.next()) {
                String n = rset.getString(1);
                String c = rset.getString(2);
                int p = rset.getInt(3);
                output.add(n + " | " + c + " | " + p);
            }
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Capitals in World By Population Descending");
            return output;
        }
    }

    /**
     * Generate reports for each capital city in each continent sorted by population size
     * @return
     */
    public static ArrayList<String> CapsinContByPopDesc() {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> cont = getContinents();
        for (int i = 0; i < cont.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT city.name, country.name, city.population "
                                + "FROM country "
                                + "JOIN city ON country.capital=city.ID "
                                + "WHERE Continent=\""+cont.get(i)+"\" "
                                + "ORDER BY city.Population DESC ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Capital Cities in " + cont.get(i) + ":");

                while (rset.next()) {
                    String n = rset.getString(1);
                    String c = rset.getString(2);
                    int p = rset.getInt(3);
                    output.add(n + " | " + c + " | " + p);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Capital Cities in Continent By Population Descending");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate reports for each capital city in each continent sorted by population size
     * @return
     */
    public static ArrayList<String> NCapsinContByPopDesc(int x) {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> cont = getContinents();
        for (int i = 0; i < cont.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT city.name, country.name, city.population "
                                + "FROM country "
                                + "JOIN city ON country.capital=city.ID "
                                + "WHERE Continent=\""+cont.get(i)+"\" "
                                + "ORDER BY city.Population DESC "
                                + "LIMIT " + x + " ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Capital Cities in " + cont.get(i) + " with limit " + x + ": ");

                while (rset.next()) {
                    String n = rset.getString(1);
                    String c = rset.getString(2);
                    int p = rset.getInt(3);
                    output.add(n + " | " + c + " | " + p);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Capital Cities in Continent By Population Descending");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate reports for each capital city in each Region sorted by population size
     * @return
     */
    public static ArrayList<String> CapsinRegionByPopDesc() {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> reg = getRegions();
        for (int i = 0; i < reg.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT city.name, country.name, city.population "
                                + "FROM country "
                                + "JOIN city ON country.capital=city.ID "
                                + "WHERE Region=\""+reg.get(i)+"\" "
                                + "ORDER BY city.Population DESC ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Capital Cities in " + reg.get(i) + ":");

                while (rset.next()) {
                    String n = rset.getString(1);
                    String c = rset.getString(2);
                    int p = rset.getInt(3);
                    output.add(n + " | " + c + " | " + p);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Capital Cities in Region By Population Descending");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate reports for each capital city in each Region sorted by population size with limit n
     * @return
     */
    public static ArrayList<String> NCapsinRegionByPopDesc(int x) {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> reg = getRegions();
        for (int i = 0; i < reg.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT city.name, country.name, city.population "
                                + "FROM country "
                                + "JOIN city ON country.capital=city.ID "
                                + "WHERE Region=\""+reg.get(i)+"\" "
                                + "ORDER BY city.Population DESC "
                                + "LIMIT " + x + " ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Capital Cities in " + reg.get(i) + " with limit " + x + ": ");

                while (rset.next()) {
                    String n = rset.getString(1);
                    String c = rset.getString(2);
                    int p = rset.getInt(3);
                    output.add(n + " | " + c + " | " + p);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Capital Cities in Region By Population Descending");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate population reports each continent
     * @return
     */
    public static ArrayList<String> PopulationBreakdownCont() {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> cont = getContinents();
        for (int i = 0; i < cont.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT Continent, "
                                + "(SELECT SUM(population) FROM country WHERE continent=\""+cont.get(i)+"\"), "
                                + "(SELECT SUM(city.population) FROM city JOIN country ON city.countrycode=country.code WHERE continent=\""+cont.get(i)+"\") "
                                + "FROM country "
                                + "WHERE continent=\""+cont.get(i)+"\" "
                                + "GROUP BY continent ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Population Breakdown for " + cont.get(i) + ":");

                while (rset.next()) {
                    String co = rset.getString(1);
                    long t = rset.getLong(2);
                    long c = rset.getLong(3);
                    output.add(co + " | " + t + " | " + c + " | " + (t-c));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Continent population report");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate population reports each region
     * @return
     */
    public static ArrayList<String> PopulationBreakdownRegion() {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> reg = getRegions();
        for (int i = 0; i < reg.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT Region, "
                                + "(SELECT SUM(population) FROM country WHERE Region=\""+reg.get(i)+"\"), "
                                + "(SELECT SUM(city.population) FROM city JOIN country ON city.countrycode=country.code WHERE Region=\""+reg.get(i)+"\") "
                                + "FROM country "
                                + "WHERE Region=\""+reg.get(i)+"\" "
                                + "GROUP BY Region ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Population Breakdown for " + reg.get(i) + ":");

                while (rset.next()) {
                    String co = rset.getString(1);
                    long t = rset.getLong(2);
                    long c = rset.getLong(3);
                    output.add(co + " | " + t + " | " + c + " | " + (t-c));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Region population report");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate population reports each Country
     * @return
     */
    public static ArrayList<String> PopulationBreakdownCountry() {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<String> co = getCountries();
        for (int i = 0; i < co.size(); i++) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT name, "
                                + "(SELECT SUM(population) FROM country WHERE name=\""+co.get(i)+"\"), "
                                + "(SELECT SUM(city.population) FROM city JOIN country ON city.countrycode=country.code WHERE country.name=\""+co.get(i)+"\") "
                                + "FROM country "
                                + "WHERE name=\""+co.get(i)+"\" "
                                + "GROUP BY name ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Population Breakdown for " + co.get(i) + ":");

                while (rset.next()) {
                    String contr = rset.getString(1);
                    long t = rset.getLong(2);
                    long c = rset.getLong(3);
                    output.add(contr + " | " + t + " | " + c + " | " + (t-c));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Country population report");
                return output;
            }
        }
        return output;
    }

    /**
     * Generate total population of each continent
     * @return
     */
    public static ArrayList<String> PopulationContinents() {
        ArrayList<String> output = new ArrayList<String>();
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT Continent, SUM(country.population) "
                                + "FROM country "
                                + "GROUP BY Continent ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);

                output.add("Population of all Continents:");

                while (rset.next()) {
                    String c = rset.getString(1);
                    long p = rset.getLong(2);
                    output.add(c + " | " + p);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get Population of Continents");
                return output;
            }
        return output;
    }

    /**
     * Generate total population of each Region
     * @return
     */
    public static ArrayList<String> PopulationRegions() {
        ArrayList<String> output = new ArrayList<String>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Region, SUM(country.population) "
                            + "FROM country "
                            + "GROUP BY Region ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            output.add("Population of all Regions:");

            while (rset.next()) {
                String c = rset.getString(1);
                long p = rset.getLong(2);
                output.add(c + " | " + p);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Population of Regions");
            return output;
        }
        return output;
    }

    /**
     * Generate total population of each Country
     * @return
     */
    public static ArrayList<String> PopulationCountry() {
        ArrayList<String> output = new ArrayList<String>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT name, population "
                            + "FROM country ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            output.add("Population of all Countries:");

            while (rset.next()) {
                String c = rset.getString(1);
                long p = rset.getLong(2);
                output.add(c + " | " + p);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Population of Countries");
            return output;
        }
        return output;
    }

    /**
     * Generate total population of each District
     * @return
     */
    public static ArrayList<String> PopulationDistrict() {
        ArrayList<String> output = new ArrayList<String>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT District, SUM(city.population) "
                            + "FROM city "
                            + "GROUP BY District ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            output.add("Population of all Districts:");

            while (rset.next()) {
                String c = rset.getString(1);
                long p = rset.getLong(2);
                output.add(c + " | " + p);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Population of Districts");
            return output;
        }
        return output;
    }

    /**
     * Generate total population of each City
     * @return
     */
    public static ArrayList<String> PopulationCities() {
        ArrayList<String> output = new ArrayList<String>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT name, population "
                            + "FROM city ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            output.add("Population of all Cities:");

            while (rset.next()) {
                String c = rset.getString(1);
                long p = rset.getLong(2);
                output.add(c + " | " + p);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Population of Cities");
            return output;
        }
        return output;
    }

    /**
     * Get Reports on the percentages of worlds population speaking the worlds most used languages
     */
    public static ArrayList<String> getLanguageReports() {
        ArrayList<String> output = new ArrayList<String>();
        try {
            long wpop = getWorldPopulation();
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT countrylanguage.Language, SUM((country.Population/100)*countrylanguage.Percentage) "
                            + "FROM countrylanguage "
                            + "LEFT JOIN country ON countrylanguage.CountryCode=country.Code "
                            + "WHERE Language=\"Chinese\" OR Language=\"English\" OR Language=\"Hindi\" OR Language=\"Spanish\" OR Language=\"Arabic\" "
                            + "GROUP BY countrylanguage.Language "
                            + "ORDER BY SUM((country.Population/100)*countrylanguage.Percentage) DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next()) {
                String n = rset.getString(1);
                Long p = rset.getLong(2);
                Double pc = (Double.valueOf(p) / Double.valueOf(wpop)) * 100;
                output.add(n + ": " + p + " speakers, " + pc + "% of the population.");
            }
            return output;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get language reports");
            return output;
        }
    }
}
