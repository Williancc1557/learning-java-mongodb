import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class DatabaseHelper {
    private static DatabaseHelper instance;
    public MongoClient client;

    private DatabaseHelper() {
        MongoClient mongoClient = MongoClients.create("mongodb+srv://williancacoelho:123@cluster0.njrkckj.mongodb.net");
        client = mongoClient;
    }

    public static DatabaseHelper getInstance() {
        if (instance == null) instance = new DatabaseHelper();
        return instance;
    }
}
