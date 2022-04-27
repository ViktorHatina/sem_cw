package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class App
{
    public static void main(String[] args) {
        // Create new Application and connect to database
        App a = new App();

        if(args.length < 1){
            a.connect("localhost:3307", 30000);
        }else{
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        a.getWorldPopulation();

        /*6
        ArrayList<Country> countries = a.getTopNPopCountriesByReg(10, "Western Europe");
        a.printAllCountries(countries);
        */

        /*7
        ArrayList<City> cities = a.getCityPopulationDesc();
        a.printAllCities(cities);
        */

        ArrayList<City> cities = a.getCityContinentPopulationDesc("Africa");
        a.printAllCities(cities);

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect(String location, int delay) {
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
                System.out.println("Failed to connect to database attempt " +                                  Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
    /**
     * Get the World's Population
     */
    public long getWorldPopulation()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT SUM(country.Population) "
                            + "FROM country";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next())
            {
                return rset.getLong(1);
            }
            else
                return 0;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get world population");
            return 0;
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
     * Get Reports on the percentages of worlds population speeking the worlds most used languages
     */
    public ArrayList<String> getLanguageReports() {
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

    /**
     * Print Countries from functions
     *
     * @param countries: a list of countries extracted from the database
     */
    public void printAllCountries(ArrayList<Country> countries) {
        if (countries != null && !countries.isEmpty()) {
            System.out.printf("%-4s %-44s %-13s %-25s %-10s %-5s%n", "Code", "Name", "Continent", "Region", "Population", "CapitalID");
            for (Country c : countries) {
                if (c != null) {
                    System.out.printf("%-4s %-44s %-13s %-25s %-10s %-5s%n", c.code, c.name, c.continent, c.region, c.population, c.capital_id);
                } else {
                    System.out.println("Missing element in data!");
                }
            }
        } else {
            System.out.println("No list to print, there was no argument provided.");
        }
    }

    /**
     * Print cities from functions
     *
     * @param cities: a list of cities extracted from the database
     */
    public void printAllCities(ArrayList<City> cities) {
        if (cities != null && !cities.isEmpty()) {
            System.out.printf("%-44s %-44s %-25s %-10s%n", "Name", "Country", "District", "Population");
            for (City c : cities) {
                if (c != null) {
                    System.out.printf("%-44s %-44s %-25s %-10s%n", c.name, c.country, c.district,c.population);
                } else {
                    System.out.println("Missing element in data!");
                }
            }
        } else {
            System.out.println("No list to print, there was no argument provided.");
        }
    }


    /**
     * 1. All the countries in the world organised by largest population to smallest.
     */
    public ArrayList<Country> getAllCountriesByPopDesc() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT code, name, continent, region, population, capital "
                            + "FROM country "
                            + "ORDER BY population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next()) {
                Country c = new Country();
                c.code = rset.getString("country.Code");
                c.name = rset.getString("country.Name");
                c.continent = rset.getString("country.Continent");
                c.region = rset.getString("country.Region");
                c.population = rset.getInt("country.Population");
                c.capital_id = rset.getInt("country.Capital");
                countries.add(c);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * 2. All the countries in a continent organised by largest population to smallest..
     */
    public ArrayList<Country> getAllCountriesContPopDesc(String continent) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT code, name, continent, region, population, capital "
                            + "FROM country "
                            + "WHERE continent = '" + continent + "' "
                            + " ORDER BY population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next()) {
                Country c = new Country();
                c.code = rset.getString("country.Code");
                c.name = rset.getString("country.Name");
                c.continent = rset.getString("country.Continent");
                c.region = rset.getString("country.Region");
                c.population = rset.getInt("country.Population");
                c.capital_id = rset.getInt("country.Capital");
                countries.add(c);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details by given continent");
            return null;
        }
    }
    /**
     * 3. All the countries in a region organised by largest population to smallest.
     */
    public ArrayList<Country> getAllCountriesRegByPopDesc(String region) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT code, name, continent, region, population, capital "
                            + "FROM country "
                            + "WHERE region = '" + region + "' "
                            + "ORDER BY population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next()) {
                Country c = new Country();
                c.code = rset.getString("country.Code");
                c.name = rset.getString("country.Name");
                c.continent = rset.getString("country.Continent");
                c.region = rset.getString("country.Region");
                c.population = rset.getInt("country.Population");
                c.capital_id = rset.getInt("country.Capital");
                countries.add(c);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries with highest populationz by given region");
            return null;
        }
    }

    /**
     * 4.The top N populated countries in the world where N is provided by the user.
     */
    public ArrayList<Country> getTopNPopCountries(int n) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT code, name, continent, region, population, capital "
                            + "FROM country "
                            + "ORDER BY population DESC "
                            + "LIMIT " + n + " ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next()) {
                Country c = new Country();
                c.code = rset.getString("country.Code");
                c.name = rset.getString("country.Name");
                c.continent = rset.getString("country.Continent");
                c.region = rset.getString("country.Region");
                c.population = rset.getInt("country.Population");
                c.capital_id = rset.getInt("country.Capital");
                countries.add(c);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get top N contries in the world");
            return null;
        }
    }
    /**
     * 5.The top N populated countries in a continent where N is provided by the user.
     */
    public ArrayList<Country> getTopNPopCountriesByCont(int n, String continent) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT code, name, continent, region, population, capital "
                            + "FROM country "
                            + "WHERE continent = '" + continent + "' "
                            + "ORDER BY population DESC "
                            + "LIMIT " + n + " ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next()) {
                Country c = new Country();
                c.code = rset.getString("country.Code");
                c.name = rset.getString("country.Name");
                c.continent = rset.getString("country.Continent");
                c.region = rset.getString("country.Region");
                c.population = rset.getInt("country.Population");
                c.capital_id = rset.getInt("country.Capital");
                countries.add(c);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get top N contries in given continent");
            return null;
        }
    }

    /**
     * 6.The top N populated countries in a region where N is provided by the user.
     */
    public ArrayList<Country> getTopNPopCountriesByReg(int n, String region) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT code, name, continent, region, population, capital "
                            + "FROM country "
                            + "WHERE region = '" + region + "' "
                            + "ORDER BY population DESC "
                            + "LIMIT " + n + " ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next()) {
                Country c = new Country();
                c.code = rset.getString("country.Code");
                c.name = rset.getString("country.Name");
                c.continent = rset.getString("country.Continent");
                c.region = rset.getString("country.Region");
                c.population = rset.getInt("country.Population");
                c.capital_id = rset.getInt("country.Capital");
                countries.add(c);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get top N contries in given region");
            return null;
        }
    }

    /**
     * 7.All the cities in the world organised by largest population to smallest.
     */
    public ArrayList<City> getCityPopulationDesc() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.name, country.name, city.district, city.population "
                            + "FROM city "
                            + "JOIN country ON city.countrycode=country.code "
                            + "ORDER BY city.population DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<City> cities = new ArrayList<City>();
            while (rset.next()) {
                City c = new City();
                c.name = rset.getString("city.name");
                c.country = rset.getString("country.name");
                c.district = rset.getString("city.district");
                c.population = rset.getInt("city.population");
                cities.add(c);
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get list of cities by population");
            return null;
        }
    }

    /**
     * 8.All the cities in a continent organised by largest population to smallest.
     */
    public ArrayList<City> getCityContinentPopulationDesc(String continent) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.name, country.name, city.district, city.population "
                            + "FROM city "
                            + "JOIN country ON city.countrycode=country.code "
                            + "WHERE continent = '" + continent + "' "
                            + "ORDER BY city.population DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract country information
            ArrayList<City> cities = new ArrayList<City>();
            while (rset.next()) {
                City c = new City();
                c.name = rset.getString("city.name");
                c.country = rset.getString("country.name");
                c.district = rset.getString("city.district");
                c.population = rset.getInt("city.population");
                cities.add(c);
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get list of cities in a continent by population");
            return null;
        }
    }
}

/**
 * :^)
 */

