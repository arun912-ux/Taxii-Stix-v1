package com.example.taxiistix;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class HTTPRequest {

    static URL url;
    static HttpURLConnection http;

    static String httpUri(String date) throws IOException {

        // ! Discovery Service
//        url = new URL("http://hailataxii.com/taxii-discovery-service");
        // ! Collection Information, POLL
        url = new URL("http://hailataxii.com/taxii-data");

        // * Headers
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setConnectTimeout(50000);
        http.setReadTimeout(50000);
        http.setRequestProperty("Content-Type", "application/xml");
        http.setRequestProperty("Accept", "*/*");
        http.setRequestProperty("X-TAXII-Content-Type", "urn:taxii.mitre.org:message:xml:1.1");
        http.setRequestProperty("X-TAXII-Accept", "urn:taxii.mitre.org:message:xml:1.1");
        http.setRequestProperty("X-TAXII-Services", "urn:taxii.mitre.org:services:1.1");
        http.setRequestProperty("X-TAXII-Protocol", "urn:taxii.mitre.org:protocol:http:1.0");


        // ! INFO: Discovery Service
//        String data = "<Discovery_Request xmlns=\"http://taxii.mitre.org/messages/taxii_xml_binding-1.1\" message_id=\"1\"/>\n";
        // !  Collection Information
//        String data = "<taxii_11:Collection_Information_Request xmlns:taxii_11=\"" +
//                "http://taxii.mitre.org/messages/taxii_xml_binding-1.1\"message_id=\"123456\"/>\n";


        // ! POLL
        if(date==null){
            date = "2018-05-20";
        }

        String data = "\n" +
                "<taxii_11:Poll_Request \n" +
                "    xmlns:taxii_11=\"http://taxii.mitre.org/messages/taxii_xml_binding-1.1\"\n" +
                "    message_id=\"123456\"\n" +
                "    collection_name=\"guest.Abuse_ch\">\n" +
                "    <taxii_11:Exclusive_Begin_Timestamp>" + date + "T15:00:00Z \n" +
                "    </taxii_11:Exclusive_Begin_Timestamp>\n" +
                "    <taxii_11:Inclusive_End_Timestamp>2018-05-25T15:18:00Z\n" +
                "    </taxii_11:Inclusive_End_Timestamp>\n" +
                "    <taxii_11:Poll_Parameters allow_async=\"false\">\n" +
                "        <taxii_11:Response_Type>FULL</taxii_11:Response_Type>\n" +
                "    </taxii_11:Poll_Parameters>\n" +
                "</taxii_11:Poll_Request>\n" +
                "\n";


        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);
        BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));

        System.out.println();
        System.out.println(http.getResponseCode() + " : " + http.getResponseMessage());
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null){
            sb.append(line);
        }

        http.disconnect();
        stream.close();

        return sb.toString();

    }

    static String getXml(String date) throws IOException {
        return  httpUri(date);
    }



    static String getJson(String date) throws IOException {

        Map<String, String> ipMap = new HashMap<>();
        Map<String, String> uriMap = new HashMap<>();


        String ret = httpUri(date);
        String jsonString = XML.toJSONObject(ret).toString(4);

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject poll_Response = jsonObject.getJSONObject("taxii_11:Poll_Response");
        JSONArray contentBlock = poll_Response.getJSONArray("taxii_11:Content_Block");

        for(int i=0; i< contentBlock.length(); i++){
            try{
                JSONObject onj = (JSONObject) contentBlock.get(i);
                JSONObject observables = onj.getJSONObject("taxii_11:Content")
                        .getJSONObject("stix:STIX_Package")
                        .getJSONObject("stix:Observables")
                        .getJSONObject("cybox:Observable");

                String cyboxTitle = observables.get("cybox:Title").toString();

                String printString = cyboxTitle + " \n" + observables.toString(4) + "\n\n";

                if(cyboxTitle.contains("IP")){
                    ipMap.put(cyboxTitle.substring(4), observables.toString(4));
                }
                if(cyboxTitle.contains("URI")){
                    uriMap.put(cyboxTitle.substring(5), observables.toString(4));
                }

            }catch (Exception e){
                System.out.print("");
            }

        }

        HTreeMap<String, Map<String, String>> savedDB = save(ipMap, uriMap);

        return savedDB.toString();

//        JSONObject jsonObject = new JSONObject(jsonString);
//        JSONObject poll_Response = jsonObject.getJSONObject("taxii_11:Poll_Response");
//        poll_Response.remove("more");
//        poll_Response.remove("xmlns:taxii_11");
//        poll_Response.remove("message_id");
//        poll_Response.remove("taxii_11:Inclusive_End_Timestamp");
//        poll_Response.remove("in_response_to");
//
//        JSONArray contentBlock = poll_Response.getJSONArray("taxii_11:Content_Block");
////        return contentBlock;
//        List<JSONObject> lsJ = new ArrayList<>(0);
//        StringBuilder sb = new StringBuilder();
//        for(int i=0; i< contentBlock.length(); i++){
//            JSONObject jO = (JSONObject) contentBlock.get(i);
//            sb.append(jO.toString(4));
//            if(i!=contentBlock.length()-1)
//                sb.append(", \n\n");
////            lsJ.add((JSONObject) contentBlock.get(i));
//        }
//        return sb.toString();
////        return lsJ;
////        return (JSONObject) contentBlock.get(1);
//
////        return poll_Response;
    }



    static HTreeMap<String, Map<String, String>> save(Map<String, String> ipMap, Map<String, String> uriMap){

        DB mapdb = DBMaker.memoryDB().make();
        HTreeMap<String, Map<String, String>> hashmap = (HTreeMap<String, Map<String, String>>)
                mapdb.hashMap("hashmap").createOrOpen();


        System.out.println(ipMap.size());
        System.out.println(uriMap.size());

        hashmap.put("IP", ipMap);
        hashmap.put("URI", uriMap);

        System.out.println(hashmap.size());

        return hashmap;

    }


}