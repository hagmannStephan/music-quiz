package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.InsertOneOptions;
import org.bson.Document;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        // Set your MongoDB connection string
        String connectionString = "mongodb://root:root@localhost/";

        try (MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).build())) {

            String databaseName = "Quiz";
            MongoDatabase database = mongoClient.getDatabase(databaseName);

            // Specify the collection name
            String collectionName = "Questions";
            MongoCollection<Document> questionsCollection = database.getCollection(collectionName);

            // Insert a sample document into the "Questions" collection
            Document questionDocument = new Document("question", "What is the capital of France?")
                    .append("options", new Document("A", "Paris").append("B", "Berlin").append("C", "London"))
                    .append("correctAnswer", "A");

            // Specify options for the insert operation (optional)
            InsertOneOptions insertOneOptions = new InsertOneOptions().bypassDocumentValidation(false);

            // Insert the document into the collection
            questionsCollection.insertOne(questionDocument, insertOneOptions);

            System.out.println("Document inserted successfully.");

            // Now, you can perform a query or any other operations on the "Questions" collection.

        } catch (Exception e) {
            System.err.println("An error occurred: " + e);
        }
    }
}