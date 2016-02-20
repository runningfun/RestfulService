package runningfun.restfulservice;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.util.Date;

/**
 * Created by Herschbach.Stefan on 10.08.2015.
 */
public class MongoDBHandler {

    private static MongoCollection<Document> gasCollection;
    private static MongoCollection<Document> temperatureCollection;
    private static MongoCollection<Document> humidityCollection;

    static synchronized MongoCollection<Document> getGasCollection() {
        if (gasCollection == null) {
            MongoClient mongoClient = new MongoClient();
            MongoDatabase db = mongoClient.getDatabase("local");
            if (db.getCollection("Gas") == null) {
                db.createCollection("Gas");
            }
            gasCollection = db.getCollection("Gas");
        }
        return gasCollection;
    }

    public FindIterable<Document> getGasValues() {
        FindIterable<Document> iterable = getGasCollection().find();
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        return iterable;
    }

    public void setGasValue(int gasValue, String date) {
        getGasCollection().insertOne(createNewGasEntry(gasValue, date));
    }

    MongoCollection<Document> getTemperatureCollection() {
        if (temperatureCollection == null) {
            MongoClient mongoClient = new MongoClient();
            MongoDatabase db = mongoClient.getDatabase("local");
            if (db.getCollection("Temperature") == null) {
                db.createCollection("Temperature");
            }
            temperatureCollection = db.getCollection("Temperature");
        }
        return temperatureCollection;
    }

    MongoCollection<Document> getHumidityCollection() {
        if (humidityCollection == null) {
            MongoClient mongoClient = new MongoClient();
            MongoDatabase db = mongoClient.getDatabase("local");
            if (db.getCollection("Humidity") == null) {
                db.createCollection("Humidity");
            }
            humidityCollection = db.getCollection("Humidity");
        }
        return humidityCollection;
    }



    Document createNewGasEntry(int gasMeterValue, String date) {
        if (date == null) {
            date = new Date().toString();
        }
        Document dbObject = new Document("date", date).append("value", gasMeterValue);
        return dbObject;
    }

    public DeleteResult removeGasEntry(String deleteDate) {
        System.out.println("try to delete document for date " + deleteDate);
        MongoCollection<Document> gasCollection = getGasCollection();

        BasicDBObject document = new BasicDBObject();
        document.put("date", deleteDate);
        DeleteResult deleteResult = gasCollection.deleteMany(document);
        System.out.println("documents deleted " + deleteResult.getDeletedCount());
        return deleteResult;
    }

    public void createTemperatureValue(double temperatureValue) {
        getTemperatureCollection().insertOne(new Document("date", new Date()).append("value", temperatureValue));
    }

    public void createHumidityValue(double humidityValue) {
        getHumidityCollection().insertOne(new Document("date", new Date()).append("value", humidityValue));
    }

    public FindIterable<Document> getTemperatureValues() {
        FindIterable<Document> documents = getTemperatureCollection().find();
//        iterable.forEach(new Block<Document>() {
//            @Override
//            public void apply(final Document document) {
//                System.out.println(document);
//            }
//        });
        return documents;
    }

    public FindIterable<Document> getTemperatureValues(int number) {

        FindIterable<Document> documents = getTemperatureCollection().find().sort(new Document("_id", -1)).limit(number);
        return documents;
    }
}
