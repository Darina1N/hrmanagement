package sk.kosickaakademia.company;

import sk.kosickaakademia.company.database.Database;
import sk.kosickaakademia.company.entity.User;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );
        Database database=new Database();
        database.insertNewUser(new User("Martinko", "Klingáč", 50,0));
        List<User> list=database.getMales();
        System.out.println(list);
    }
}
