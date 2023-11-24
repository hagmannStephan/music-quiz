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
        System.out.println(QuizDBConnector.getDatabase());
    }
}