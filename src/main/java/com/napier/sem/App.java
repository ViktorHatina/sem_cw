package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class App
{
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

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
    public static void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);

                // Connect to database
                /*

                We will have to find the correct path (means of accessing) the database
                db/world - attempted path
                Questions:
                    - Does our database require a password and user input?
                    - The database will require unique URL (the one we using now is invalid
                        - form: jdbc:subprotocol:subname
                */
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public static void disconnect()
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
    public static long getWorldPopulation()
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
     * Get the World's Population
     */
    public static void printReport(ArrayList<String> s)
    {
        if (s == null)
        {
            System.out.println("No Report to Print");
        } else {
             for (int i=0; i< s.size(); i++) {
                System.out.println(s.get(i));
             }
        }
    }

    public static ArrayList<String> getLanguageReports() {
        ArrayList<String> output = new ArrayList<String>();
        try
        {
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

            while (rset.next())
            {
                String n = rset.getString(1);
                Long p = rset.getLong(2);
                Double pc = (Double.valueOf(p)/Double.valueOf(wpop))*100;
                output.add(n + ": " + p + " speakers, " + pc + "% of the population.");
            }
            return output;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get language reports");
            return output;
        }
    }
}