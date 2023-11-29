package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Iterator;
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
                    e.setName(doc.getString("name"));
                    e.setMaxStreams(doc.getInteger("maxStreams"));
                    e.setRelease(doc.getInteger("release"));
                    e.setLength(doc.getInteger("length"));
                    e.setTitles(doc.getInteger("titles"));
                    value_db.add(e);
                }
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
        return value_db;
    }

    public static Question getRandomEntry(String category) {
        ArrayList<EntryClass> entryList = getDatabase();
        if (entryList == null || entryList.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(entryList.size());
        EntryClass randomEntry = entryList.get(randomIndex);
        entryList.remove(randomIndex);
        Question question = new Question();
        ArrayList<QuestionValue> values = new ArrayList<>();
        switch (category) {
            case "Streams" -> {
                question.setQuestion("How many streams does the most streamed song on " + randomEntry.getName() + " have (in Millions)?");
                values.add(new QuestionValue(randomEntry.getMaxStreams(), true));
                while (values.size() < 3) {
                    int r = random.nextInt(entryList.size());
                    EntryClass re = entryList.get(r);
                    if ((re.getMaxStreams() * 0.5) <= randomEntry.getMaxStreams() || randomEntry.getMaxStreams() <= (re.getMaxStreams() * 1.5)) {
                        values.add(new QuestionValue(re.getMaxStreams(), false));
                        entryList.remove(r);
                    }
                }
            }
            case "Release" -> {
                question.setQuestion("In which year got the album " + randomEntry.getName() + " released?");
                values.add(new QuestionValue(randomEntry.getRelease(), true));
                while (values.size() < 3) {
                    int r = random.nextInt(entryList.size());
                    EntryClass re = entryList.get(r);
                    if ((re.getRelease() * 0.5) <= randomEntry.getRelease() || randomEntry.getRelease() <= (re.getRelease() * 1.5)) {
                        values.add(new QuestionValue(re.getRelease(), false));
                        entryList.remove(r);
                    }
                }
            }
            case "Length" -> {
                question.setQuestion("How long is the album " + randomEntry.getName() + " from start to end (in Minutes)?");
                values.add(new QuestionValue(randomEntry.getLength(), true));
                while (values.size() < 3) {
                    int r = random.nextInt(entryList.size());
                    EntryClass re = entryList.get(r);
                    if ((re.getLength() * 0.5) <= randomEntry.getLength() || randomEntry.getLength() <= (re.getLength() * 1.5)) {
                        values.add(new QuestionValue(re.getLength(), false));
                        entryList.remove(r);
                    }
                }
            }
            case "Titles" -> {
                question.setQuestion("How many titles are on the album " + randomEntry.getName() + "?");
                values.add(new QuestionValue(randomEntry.getTitles(), true));
                while (values.size() < 3) {
                    int r = random.nextInt(entryList.size());
                    EntryClass re = entryList.get(r);
                    if ((re.getTitles() * 0.5) <= randomEntry.getTitles() || randomEntry.getTitles() <= (re.getTitles() * 1.5)) {
                        values.add(new QuestionValue(re.getTitles(), false));
                        entryList.remove(r);
                    }
                }
            }
            default -> {
                return null;
            }
        }

        question.setValues(values);
        return question;
    }
}
