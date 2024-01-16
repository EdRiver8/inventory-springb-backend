package com.company.inventory.response;

import java.util.ArrayList;
import java.util.HashMap;

public class ResponseRest {
//    private String message;
//    private Integer status;
//    private HashMap<String, Object> data;
//
//    public ResponseRest() {
//        this.message = "";
//        this.status = 0;
//        this.data = new HashMap<>();
//    }
//
//    public ResponseRest(String message, Integer status, HashMap<String, Object> data) {
//        this.message = message;
//        this.status = status;
//        this.data = data;
//    }

    private ArrayList<HashMap<String, String>> metadata = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getMetadata() {
        return metadata;
    }

    public void setMetadata(String type, String code, String date) {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("code", code);
        map.put("message", date);

        this.metadata.add(map);
    }
}
