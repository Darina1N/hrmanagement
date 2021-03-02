package sk.kosickaakademia.company.database;

import sk.kosickaakademia.company.log.Log;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    Log log=new Log();
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
}
