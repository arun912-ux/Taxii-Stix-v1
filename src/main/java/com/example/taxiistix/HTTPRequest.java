package com.example.taxiistix;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class HTTPRequest {


    static Object httpUri() throws IOException {

        // ! Discovery Service
//        URL url = new URL("http://hailataxii.com/taxii-discovery-service");
        // ! Collection Information, POLL
        URL url = new URL("http://hailataxii.com/taxii-data");


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
        String data = "\n" +
                "<taxii_11:Poll_Request \n" +
                "    xmlns:taxii_11=\"http://taxii.mitre.org/messages/taxii_xml_binding-1.1\"\n" +
                "    message_id=\"123456\"\n" +
                "    collection_name=\"guest.Abuse_ch\">\n" +
                "    <taxii_11:Exclusive_Begin_Timestamp>2018-05-20T15:00:00Z \n" +
                "    </taxii_11:Exclusive_Begin_Timestamp>\n" +
                "    <taxii_11:Inclusive_End_Timestamp>2018-05-25T15:18:00Z\n" +
                "    </taxii_11:Inclusive_End_Timestamp>\n" +
                "    <taxii_11:Poll_Parameters allow_asynch=\"false\">\n" +
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

//        return sb.toString();
//        return xmlToJson(sb.toString());
        return XML.toJSONObject(sb.toString());

//        return sb.toString();


    }


    static void savrToDB(String xmlString) throws IOException {

        JSONObject xmlJSONObj = XML.toJSONObject(xmlString);
        String jsonString = xmlJSONObj.toString(4);

//        DB db2 = DBMaker.memoryDB().make();
//
//        HTreeMap<Integer, String> memoryDB = db2.hashMap("memoryDB", Serializer.INTEGER, Serializer.STRING).createOrOpen();
//
//        db2.commit();
//        db2.close();

    }

}