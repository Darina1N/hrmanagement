package sk.kosickaakademia.company.util;

import sk.kosickaakademia.company.entity.User;
import org.json.simple.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Util {
    public String getJSON(List<User> list){//na vstupe bude zoznam userov a vráti mi json, ale tak že user bude ako pole
        if(list.isEmpty())
            return "{}";
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("formatDateTime",getCurrentDateTime());
        jsonObject.put("size",list.size());
        JSONArray jsonArray=new JSONArray();
        for(User user: list){
            JSONObject userJson=new JSONObject();
            userJson.put("id",user.getId());
            userJson.put("fname",user.getFname());
            userJson.put("lname",user.getLname());
            userJson.put("age",user.getAge());
            userJson.put("gender",user.getGender().toString());
            jsonArray.add(userJson);
        }
        jsonObject.put("users",jsonArray);
        return jsonObject.toJSONString();
    }

    public String getJSON(User user){//na svtupe bude len 1 user a vráti mi json, ale aj keď je 1 tak user bude pole
        if(user==null)
            return "{}";
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("formatDateTime",getCurrentDateTime());
        jsonObject.put("size",1);
        JSONArray jsonArray=new JSONArray();
        JSONObject userJson=new JSONObject();
        userJson.put("id",user.getId());
        userJson.put("fname",user.getFname());
        userJson.put("lname",user.getLname());
        userJson.put("age",user.getAge());
        userJson.put("gender",user.getGender().toString());
        jsonArray.add(userJson);
        jsonObject.put("users",jsonArray);
        return jsonObject.toJSONString();
    }

    public String getCurrentDateTime () {//metoda ktorá mi vráti dátum a čas kedy niekto požiadal o request
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formatDateTime = now.format(format);
            return formatDateTime;
        }


    public String upravaName(String name){//metoda ktora mi upravi meno aby bolo jednotne zapisane v db MILAN->Milan
        if(name.isEmpty())
            return null;
        name=name.trim();
        String newName="";
        newName=""+name.toUpperCase().charAt(0);
        String substring=name.substring(1,name.length()).toLowerCase();
        return (newName+substring);
    }
}
