package sk.kosickaakademia.company.database;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.json.simple.JSONObject;
import sk.kosickaakademia.company.entity.User;
import sk.kosickaakademia.company.log.Log;


import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {
    Log log=new Log();
    private final String InsertQuery ="INSERT INTO user (fname, lname, age, gender) "+
            "VALUES (?, ?, ?, ?)";

    MongoClient mongoClient = new MongoClient("localhost", 27017);
    MongoDatabase database = mongoClient.getDatabase("myMongoDb");


    public Connection getConnection(){
        try {
            Properties properties=new Properties();
            InputStream loader=getClass().getClassLoader().getResourceAsStream("database.properties");
            properties.load(loader);
            String url=properties.getProperty("url");
            String username=properties.getProperty("username");
            String password=properties.getProperty("password");
            Connection connection= DriverManager.getConnection(url,username,password);
            log.print("Connection is OK.");
            return connection;
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }

    public void closeConnection(Connection connection){
        if(connection!=null)
        {
            try {
                connection.close();
                log.print("Connection closed.");
            }catch (SQLException e){
                log.error(e.toString());
            }
        }
    }

    public boolean insertNewUser(User user){
      Connection connection=getConnection();
      if(connection!=null){
          try{
              PreparedStatement ps=connection.prepareStatement(InsertQuery);
              ps.setString(1, user.getFname());
              ps.setString(2, user.getLname());
              ps.setInt(3,user.getAge());
              ps.setInt(4,user.getGender().getValue());
              int result=ps.executeUpdate();
              closeConnection(connection);
              log.print("New user is added");
              return result==1;
          }catch (SQLException e){
              log.error(e.toString());
          }
      }
      return false;
    }

    private List<User> executeSelect(PreparedStatement ps){
        List<User> list = new ArrayList<>();
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                int age = rs.getInt("age");
                int gender = rs.getInt("gender");
                User user=new User(id,fname,lname,age,gender);
                list.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public List<User> getMales(){
        String sqlM="SELECT * FROM user WHERE gender=0";
        try{
            PreparedStatement ps=getConnection().prepareStatement(sqlM);
            return executeSelect(ps);
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }

    public List<User> getFemales(){
        String sqlF="SELECT * FROM user WHERE gender=1";
        try{
            PreparedStatement ps=getConnection().prepareStatement(sqlF);
            return executeSelect(ps);
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }

    public List<User> getUserByAge(int from, int to){
        if(from>to)
            return null;
        String sqlAge="SELECT * FROM user WHERE age>=? AND age<=?";
        try{
            PreparedStatement ps=getConnection().prepareStatement(sqlAge);
            ps.setInt(1,from);
            ps.setInt(2,to);
            return executeSelect(ps);
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }

    public User getUserById(int id){
        if(id <= 0)
            return null;
        String sglId="SELECT * FROM user WHERE id=?";
        try{
            PreparedStatement ps=getConnection().prepareStatement(sglId);
            ps.setInt(1,id);
            List<User> list= executeSelect(ps);
            if(list.isEmpty())
                return null;
            else
                return list.get(0);
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }

    public List<User> getAllUsers(){
        String sqlAll="SELECT * FROM user";
        try{
            PreparedStatement ps=getConnection().prepareStatement(sqlAll);
            return executeSelect(ps);
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }

    public boolean changeAge(int id, int newAge){
        if(newAge<=0 || newAge>99)
            return false;
        String sqlNewAge="UPDATE user SET age=? WHERE id=?";
        try{
            PreparedStatement ps=getConnection().prepareStatement(sqlNewAge);
            ps.setInt(1,newAge);
            ps.setInt(2,id);
            int result=ps.executeUpdate();
            if(result==1)
                return true;
        }catch (Exception e){
            log.error(e.toString());
        }
        return false;
    }

    public List<User> getUser(String pattern){//metoda ktorá príjíma pattern, čiže podreťazec a potom prehľadáva komplet celé meno
        // "mi" -> Miro, Mila, Jarmila, Kominar
        // select    ....where fname like '%?%' OR lname like '%?%'*/
        List<User> list=new ArrayList<>();
        String sqlUser="SELECT * FROM user WHERE fname LIKE ? OR lname LIKE ?";
        try{
            PreparedStatement ps=getConnection().prepareStatement(sqlUser);
            ps.setString(1,"%"+pattern+"%");
            ps.setString(2,"%"+pattern+"%");
            return executeSelect(ps);
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }

}
