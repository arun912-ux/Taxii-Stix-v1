package com.example.taxiistix;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    ServiceHelper conHelp;


    @GetMapping("/")
    String homePage(){
        return "index";
    }


    @GetMapping(value = "ips", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public String ips() {
        return conHelp.getIps();
    }

    @GetMapping(value = "domains", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public String domains() {
        return conHelp.getDomains();
    }

    @GetMapping(value = "urls", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public String urls() {
        return conHelp.getUrls();
    }


    @PostMapping(value = "/form", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public String form(@ModelAttribute("select") Object type,
                       @ModelAttribute("input") String input){
        System.out.println(type + " : " + input);
        return conHelp.search(type.toString(), input);
    }


}
