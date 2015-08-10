package runningfun.restfulservice;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by Herschbach.Stefan on 10.08.2015.
 */
public class ReadValuesFromMongoDB {

    public FindIterable<Document> getValues() {

        MongoClient mongoClient = new MongoClient();

        MongoDatabase db = mongoClient.getDatabase("local");
        FindIterable<Document> iterable = db.getCollection("Gas").find();


        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        return iterable;
    }
}
