package sk.kosickaakademia.company.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Login {
    private Map<String, Date> blocked; //zoznam blokovaných userov
    private Map<String,Integer> attemp; //zoznam userov s počtom ich pokusov na pripojenie

    public Login(){
        blocked=new HashMap<>();
        attemp=new HashMap<>();
    }

    public String loginUser(String username){

        return null;
    }
}
