package com.napier.sem;

//importing libraries
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

//class for our application running mongo-dbserver
public class App {
    /**
     Reference and project explanation. These will be displayed in java doc. (or hover over main)
     * @param args
     */
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("mongo-dbserver");
        // Get a database - will create when we use it
        MongoDatabase database = mongoClient.getDatabase("mydb");
        // Get a collection from the database
        MongoCollection<Document> collection = database.getCollection("test");
        // Create a document to store
        Document doc = new Document("name", "Group 4")
                .append("class", "Software Engineering Methods")
                .append("year", "2022")
                .append("result", new Document("CW", 95).append("EX", 85));
        // Add document to collection
        collection.insertOne(doc);

        // Check document in collection
        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());
    }
}
