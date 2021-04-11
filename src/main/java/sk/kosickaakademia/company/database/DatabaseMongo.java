package sk.kosickaakademia.company.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import sk.kosickaakademia.company.entity.User;

public class DatabaseMongo {

    public static void main(String[] args) {
        User user=new User("Robin","Hood",31,0);
    }

    MongoClient mongoClient = new MongoClient("localhost", 27017);
    MongoDatabase database = mongoClient.getDatabase("myMongoDb");

    public void insertUserToMongo(User user){
        Document document=new Document();
        document.append("name",user.getFname());
        document.append("lname",user.getLname());
        document.append("age",user.getAge());
        document.append("gender",user.getGender().getValue());
        database.getCollection("users").insertOne(document);
        System.out.println("Document was insert");
    }
}
