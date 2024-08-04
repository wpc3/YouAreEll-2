package controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Id;

public class IdController {

        private String rootURL =  "http://zipcode.rocks:8085/ids";

    ServerController sc;
    private HashMap<String, Id> allIds;

    Id myId;

    public IdController(ServerController shared) {
        sc = shared;
        allIds = new HashMap<String, Id>();
    }

    public ArrayList<Id> getIds() {
        String jsonInput = sc.getIds();
        // convert json to array of Ids
        ObjectMapper mapper = new ObjectMapper();
        List<Id> ids;
        try {
            ids = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Id.class));

            ArrayList<Id> idList = new ArrayList<>(ids);
            // return array of Ids
            return idList;
        } catch (JsonMappingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        }
        return null;
    }

    public Id postId(Id id) {
        // create json from id
        // call server, get json result Or error
        // result json to Id obj

//        String jsonInput = sc.postId();
        //Convert ID object to JSON
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInput = sc.postId();
        // convert json to array of Ids
        ObjectMapper mapper = new ObjectMapper();


        try {
//            id = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Id.class));
           //Convert ID to JSON
            String jsonInput = mapper.writeValueAsString(id);

            //Send POST request
            String jsonResponse = sc.sendRequest("/ids", "POST", jsonInput);
            return mapper.readValue(jsonResponse,Id.class);

        }
         catch (JsonProcessingException e) {
            System.out.println("Faied to post ID " + e.getMessage());
        }
        return null;
//
//        try{
//
//            String jsonInput = mapper.writeValueAsString(id);
//            //Send POST request
//            URL url = new URL(rootURL + "/" + id.getGithub());
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json; utf-8");
//            conn.setRequestProperty("Accept", "application/json");
//            conn.setDoOutput(true);
//
//            try (OutputStream os = conn.getOutputStream()) {
//                byte[] input = jsonInput.getBytes("utf-8");
//                os.write(input, 0, input.length);
//            }
//
//            //Read responses
//            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))){
//                StringBuilder sb = new StringBuilder();
//                String line;
//                while ((line = br.readLine()) != null){
//                    sb.append(line);
//                }
//                return mapper.readValue(sb.toString(),Id.class);
//            }
//
//        }catch (Exception e){
//            System.out.println("Error posting Id: " + e.getMessage());
//        }

//        return null;
    }

    public Id putId(Id id) {

        // convert json to array of Ids
        ObjectMapper mapper = new ObjectMapper();


        try {
//            id = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Id.class));
            //Convert ID to JSON
            String jsonInput = mapper.writeValueAsString(id);

            //Send PUT request
            String jsonResponse = sc.sendRequest("/ids", "PUT", jsonInput);
            return mapper.readValue(jsonResponse,Id.class);

        }
        catch (JsonProcessingException e) {
            System.out.println("Faied to put ID " + e.getMessage());
        }
        return null;

//        try{
//            //Convert ID object to JSON
//            ObjectMapper mapper = new ObjectMapper();
//            String jsonInput = mapper.writeValueAsString(id);
//
//            //Send PUT request
//            URL url = new URL(rootURL + "/" + id.getGithub());
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("PUT");
//            conn.setRequestProperty("Content-Type", "application/json; utf-8");
//            conn.setRequestProperty("Accept", "application/json");
//            conn.setDoOutput(true);
//
//            try (OutputStream os = conn.getOutputStream()) {
//                byte[] input = jsonInput.getBytes("utf-8");
//                os.write(input, 0, input.length);
//            }
//
//            //Read responses
//            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))){
//                StringBuilder sb = new StringBuilder();
//                String line;
//                while ((line = br.readLine()) != null){
//                    sb.append(line);
//                }
//                return mapper.readValue(sb.toString(),Id.class);
//            }
//
//        }catch (Exception e){
//            System.out.println("Error putting Id: " + e.getMessage());
//        }
//
//        return null;

    }

    public Id getId (String id){
        // convert json to array of Ids
        ObjectMapper mapper = new ObjectMapper();


        try {
//            id = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Id.class));
            //Convert ID to JSON
//            String jsonInput = gitHubId;

            //Send POST request
//            String jsonResponse = sc.sendRequest("/ids", "GET", jsonInput);
//            System.out.println();
            return getIds().stream().filter(x->x.getUserid().equals(id)).findFirst().orElse(null);

        }
        catch (Exception e) {
            System.out.println("Faied to get ID " + e.getMessage());
        }
        return null;
//        ObjectMapper mapper = new ObjectMapper();
//        try{
//
//            //Send GET request
//            URL url = new URL(rootURL + "/" + gitHubId);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//
//
//            //Read responses
//             BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                StringBuilder sb = new StringBuilder();
//                String line;
//                while ((line = br.readLine()) != null){
//                    sb.append(line);
//                }
//                br.close();
//                return mapper.readValue(sb.toString(),Id.class);
//            }
//
//        catch (Exception e){
//            System.out.println("Error getting Id: " + e.getMessage());
//        }
//
//        return null;


    }

    public String deleteID(String gitHubId){

        try{

            //Send DELETE request
            URL url = new URL(rootURL + "/" + gitHubId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");


            //Check response code
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                return "ID deleted successfully";
            }
            else {
                return "Failed to delete ID: " + responseCode;
            }
        }

        catch (Exception e){
            System.out.println("Error deleting Id: " + e.getMessage());
        }

        return null;



    }
 
}