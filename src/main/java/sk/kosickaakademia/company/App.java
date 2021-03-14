package sk.kosickaakademia.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sk.kosickaakademia.company.database.Database;
import sk.kosickaakademia.company.entity.User;

import java.util.List;


@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
        /*
        Database database=new Database();
        database.insertNewUser(new User("Červená", "Čiapočka", 10,1));
        List<User> list=database.getMales();
        System.out.println("Iba muži: \n"+ list);
        List<User> list1=database.getFemales();
        System.out.println("Ina ženy: \n"+list1);
        List<User> list2=database.getAllUsers();
        System.out.println("Všetci: \n"+list2);
        List<User> list3=database.getUserByAge(5,50);
        System.out.println("Len vybrané vekové rozpätie: \n"+list3);
        List<User> list4=database.getUser("ik");
        System.out.println("Users podľa výberového patternu: \n"+list4);
        User user =database.getUserById(2);
        System.out.println(user);*/

    }
}
