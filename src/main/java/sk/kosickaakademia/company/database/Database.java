package sk.kosickaakademia.company.database;

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
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
}
