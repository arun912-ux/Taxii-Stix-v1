package com.example.taxiistix;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    HTTPRequest conHelp;


    @GetMapping("/")
    String homePage(){
        return "index";
    }


    @GetMapping(value = "json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public String json() throws IOException {
//        JSONObject data1 = (JSONObject) HTTPRequest.getJson(null);
//        return data1.toString(4);
        return HTTPRequest.getJson(null);
    }


    @GetMapping(value = "xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody()
    public String xml() throws IOException {
        return HTTPRequest.getXml(null);
    }




}
