package sk.kosickaakademia.company.util;

import org.json.JSONObject;

public class XML {
           public String FromJsonToXML (String jsonObjekt){
            JSONObject json = new JSONObject(jsonObjekt);
            String xml = org.json.XML.toString(json);
            return xml;
        }
}
