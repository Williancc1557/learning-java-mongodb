import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Updates.*;

import static com.mongodb.client.model.Filters.eq;

public class PersonManagerRepository {
    private DatabaseHelper dbHelper;
    private MongoCollection personCollection;

    public PersonManagerRepository() {
        this.dbHelper = DatabaseHelper.getInstance();
        personCollection = dbHelper.client.getDatabase("test").getCollection("person");
    }

    public void removeByName(String name) {
        Bson filter = eq("name", name);
        personCollection.deleteOne(filter);
    }

    public Document getByName(String name) {
        Bson filter = eq("name", name);
        return (Document) personCollection.find(filter).first();
    }


    public FindIterable<Document> getAllPersons() {
        return (FindIterable<Document>) personCollection.find();
    }



    public void insertPerson(String name, int age) {
        Document document = new Document();
        document.append("name", name);
        document.append("age", age);

        personCollection.insertOne(document);
   }

    public void updatePerson(String name, int age) {
        Bson filter = eq("name", name);
        Bson document = set("age", age);

        personCollection.updateOne(filter, document);
    }
}
