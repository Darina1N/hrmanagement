package sk.kosickaakademia.company.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.kosickaakademia.company.database.Database;
import sk.kosickaakademia.company.entity.User;
import sk.kosickaakademia.company.enumerator.Gender;
import sk.kosickaakademia.company.log.Log;
import sk.kosickaakademia.company.util.Util;

import java.util.List;

@RestController
public class Controller {
    Log log=new Log();

    @PostMapping("/user/new")
    public ResponseEntity<String> insertNewUser(@RequestBody String data){
        try{
            JSONObject jsonObject= (JSONObject) new JSONParser().parse(data);
            String fname = (String) jsonObject.get("fname");
            String lname = (String) jsonObject.get("lname");
            String gender = (String) jsonObject.get("gender");
            int age= Integer.parseInt(String.valueOf(jsonObject.get("age")));
            System.out.println(fname+" "+lname+" "+gender+" "+age);
            if(fname==null || fname.trim().length()==0 || lname==null || lname.trim().length()==0 || age<1){
                log.error("Missing fname or lname or incorect value of age in the request");
                JSONObject object=new JSONObject();
                object.put("ERROR","Missing fname or lname or incorect value of age in the request");
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(object.toJSONString());
            }
            Gender g;
            if(gender.equalsIgnoreCase("male")){
                g=Gender.Male;
            }else if(gender.equalsIgnoreCase("female")){
                g=Gender.Female;
            }else
                g=Gender.Other;
            User user=new User(fname,lname,age,g.getValue());
            new Database().insertNewUser(user);
        }catch (Exception e) {
            log.error("Nemožno vložiť data do /user/new controller");
            JSONObject obj=new JSONObject();
            obj.put("ERROR", "Nemožno vložiť data do /user/new controller");
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(obj.toJSONString());
        }
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(null);
    }

    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers(){
        List<User>list=new Database().getAllUsers();
        String json=new Util().getJSON(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
        //kedze mi vracia json tak mi vrati aj prazdny a teda nepotrebujem robit 400
    }
}
