package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.InsertOneOptions;
import org.bson.Document;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class QuizDBConnector {
    private static final String CONNECTION_STRING = "mongodb://root:root@localhost/";
    private static final String DATABASE_NAME = "Quiz";
    private static final String COLLECTION_NAME = "Questions";

    private static ArrayList<EntryClass> value_db;

    public static ArrayList<EntryClass> getDatabase () {
        try (MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder().applyConnectionString(new ConnectionString(CONNECTION_STRING)).build()
        )) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            try {
                value_db = new ArrayList<EntryClass>();
                MongoCollection<Document> carDocs = database.getCollection(COLLECTION_NAME);
                FindIterable<Document> iterDoc = carDocs.find();
                Iterator<Document> it = iterDoc.iterator();
                while(it.hasNext()){
                    Document doc = it.next();
                    EntryClass e = new EntryClass();
                    e.setQuestion(doc.getString("Question"));
                    e.setDescription(doc.getInteger("Description"));
                    value_db.add(e);
                }
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
        return value_db;
    }

    public static Question getRandomEntry() {
        ArrayList<EntryClass> entryList = getDatabase();
        if (entryList == null || entryList.isEmpty()) {
            return null; // Return null for an empty list
        }

        Random random = new Random();
        int randomIndex = random.nextInt(entryList.size());
        EntryClass randomEntry = entryList.get(randomIndex);
        Question question = new Question();
        question.setQuestion(randomEntry.getQuestion());
        ArrayList<QuestionValue> values = new ArrayList<>();
        values.add(new QuestionValue(randomEntry.getDescription(), true));
        values.add(new QuestionValue(1, false));
        values.add(new QuestionValue(1, false));
        values.add(new QuestionValue(1, false));
        question.setValues(values);
        return question;
    }
}
