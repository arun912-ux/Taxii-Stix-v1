package com.example.taxiistix;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

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
        JSONObject data1 = (JSONObject) HTTPRequest.getJson();
        return data1.toString(4);
    }


    @GetMapping(value = "xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody()
    public String xml() throws IOException {
        return HTTPRequest.getXml();
    }


}
