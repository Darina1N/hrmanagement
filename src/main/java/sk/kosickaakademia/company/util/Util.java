package sk.kosickaakademia.company.util;

import sk.kosickaakademia.company.entity.User;
import org.json.simple.*;
import sk.kosickaakademia.company.enumerator.Gender;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

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

    public String getOverview(List<User> list) {
        int count= list.size();
        int countMale=0;
        int countFemale=0;
        int sumAge=0;
        double averageAge=0.0;
        int minAge= count>0? list.get(0).getAge():0; //ternary operator - ak počet je>0 tak daj mi vek na danej pozicii ak nie tak 0
        int maxAge= count>0? list.get(0).getAge():0;
        for(User user : list){
            if(user.getGender()== Gender.Male)
                countMale++;
            if(user.getGender()==Gender.Female)
                countFemale++;
            sumAge+=user.getAge();
            if(minAge>user.getAge())
                minAge=user.getAge();
            if(maxAge<user.getAge())
                maxAge=user.getAge();
            averageAge=(double)sumAge/count;
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("count",count);
        jsonObject.put("countMale",countMale);
        jsonObject.put("countFemale",countFemale);
        jsonObject.put("minAge",minAge);
        jsonObject.put("maxAge",maxAge);
        jsonObject.put("averageAge",averageAge);
        return jsonObject.toJSONString();
    }

    public String generateToken(){//metoda na generovanie tokenu - 40 znakov-velke a male pismena a cisla
        String token="";
        Random random=new Random();
        for(int i=0;i<40;i++){
            int znak=random.nextInt(3);
            switch (znak){
                case 0: token=token+(char)(random.nextInt(26)+65); break;//veľké pismena
                case 1: token=token+(char)(random.nextInt(26)+97); break;//malé písmená
                case 2: token=token+(char)(random.nextInt(10)+48); break;//čísla
            }
        }
        return token;
    }
}
