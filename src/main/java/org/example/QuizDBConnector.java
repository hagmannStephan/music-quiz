package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.*;


public class QuizDBConnector {
    private static final String CONNECTION_STRING = "mongodb://root:root@localhost/";
    private static final String DATABASE_NAME = "Quiz";
    private static final String COLLECTION_NAME = "Questions";

    public static ArrayList<EntryClass> getDatabaseByCategory(String category) {
        ArrayList<EntryClass> value_db = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder().applyConnectionString(new ConnectionString(CONNECTION_STRING)).build()
        )) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> statDocs = database.getCollection(COLLECTION_NAME);

            List<Document> aggregationPipeline = Arrays.asList(
                    new Document("$match",
                    new Document(category,
                    new Document("$exists", true)))
            );

            AggregateIterable<Document> results = statDocs.aggregate(aggregationPipeline);
            for (Document result : results) {
                EntryClass e = new EntryClass();
                e.setName(result.getString("name"));
                e.setMaxStreams(result.getInteger("maxStreams"));
                e.setRelease(result.getInteger("release"));
                e.setLength(result.getInteger("length"));
                e.setTitles(result.getInteger("titles"));
                value_db.add(e);
            }

        } catch (MongoException me) {
            System.err.println("An error occurred while attempting to get top three statistics: " + me);
        }
        return value_db;
    }

    public static Question getRandomEntry(String category) {
        ArrayList<EntryClass> entryList = getDatabaseByCategory(category);
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
            case "maxStreams" -> {
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
            case "release" -> {
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
            case "length" -> {
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
            case "titles" -> {
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

    public static void addStatistic(Statistic statistic) {
        try (MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder().applyConnectionString(new ConnectionString(CONNECTION_STRING)).build()
        )) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> statDocs = database.getCollection("Statistic");

            Document statDoc = new Document()
                    .append("points", statistic.getPoints())
                    .append("duration", statistic.getDuration())
                    .append("user", statistic.getUser())
                    .append("category", statistic.getCategory());
            statDocs.insertOne(statDoc);
        } catch (MongoException me) {
            System.err.println("An error occurred while attempting to add a statistic entry: " + me);
        }
    }

    public static ArrayList<Statistic> getTopThree() {
        ArrayList<Statistic> topThree = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder().applyConnectionString(new ConnectionString(CONNECTION_STRING)).build()
        )) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> statDocs = database.getCollection("Statistic");

            List<Document> aggregationPipeline = Arrays.asList(
                    new Document("$sort", new Document("points", -1L).append("duration", 1L)),
                    new Document("$limit", 3L)
            );

            AggregateIterable<Document> results = statDocs.aggregate(aggregationPipeline);
            for (Document result : results) {
                Statistic statistic = new Statistic();
                statistic.setPoints(result.getInteger("points"));
                statistic.setDuration(result.getInteger("duration"));
                statistic.setUser(result.getString("user"));
                statistic.setCategory(result.getString("category"));
                topThree.add(statistic);
            }

        } catch (MongoException me) {
            System.err.println("An error occurred while attempting to get top three statistics: " + me);
        }

        return topThree;
    }
}
