package sk.kosickaakademia.company.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.company.log.Log;
import sk.kosickaakademia.company.util.Util;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SecretController {
    private final String PASSWORD="Kosice2021!";
    Map<String,String> map=new HashMap<>();
    Log log = new Log();

    @GetMapping("/secret")
    public ResponseEntity<String> secret(@RequestHeader("token") String header){
        System.out.println(header);
        String token= header.substring(7);//token ako taký je az od 7 znaku
        for(Map.Entry<String, String> entry: map.entrySet()){
            if(entry.getValue().equals(token)){
                return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body("secret");
            }
        }
        return ResponseEntity.status(404).body("");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String authorization){
        JSONObject jsonObject=null;
        try{
            jsonObject=(JSONObject) new JSONParser().parse(authorization);
            String login= (String) jsonObject.get("login");
            String password=(String) jsonObject.get("password");
            if(login==null || password==null){
                log.error("Login or password is incorect");
                return ResponseEntity.status(400).body("");
            }
            if(password.equals(PASSWORD)){
                String token=new Util().generateToken();
                map.put(login,token);
                log.print("User is login");
                JSONObject object= new JSONObject();
                object.put("login",login);
                object.put("token","Bearer"+token);
                return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(object.toJSONString());
            }else {
                log.error("Passoword is wrong");
                return ResponseEntity.status(401).body("");
            }
        }catch(ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("token") String header){
        String token=header.substring(7);
        String login=null;
        for (Map.Entry<String, String> entry : map.entrySet()){
            if(entry.getValue().equalsIgnoreCase(token)){
                login=entry.getKey();
                break;
            }
        }
        int status;
        if(login!=null){
            map.remove(login);
            log.info("Logout user: "+login);
            status=204;
        }else {
            log.error("Logout failed. User "+login+"does not exist.");
            status=404;
        }
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body("");
    }
}
