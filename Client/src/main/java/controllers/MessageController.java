package controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Id;
import models.Message;

public class MessageController {
    private String rootURL =  "http://zipcode.rocks:8085/messages";
    ServerController sc;

    private HashSet<Message> messagesSeen;
    // why a HashSet??

    public MessageController(ServerController shared) {
        sc = shared;
        messagesSeen = new HashSet<Message>();
    }
    public ArrayList<Message> getMessages() {
       String jsonInput = sc.getMessages();
        // convert json to array of Ids
        ObjectMapper mapper = new ObjectMapper();
        List<Message> msgs;
        try {
            msgs = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Message.class));

            ArrayList<Message> msgList = new ArrayList<>(msgs);
            // return array of Ids
            return msgList;
        } catch (JsonMappingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        }
        return null;
    }
    public ArrayList<Message> getMessagesForId(String Id) {

//        String jsonInput = sc.getId();


        ObjectMapper mapper = new ObjectMapper();

        List<Message> msgs;
        try {// convert json to array of Ids
            String jsonInput = sc.sendRequest("/ids/" + Id + "/messages", "GET", "" );
//            System.out.println("Recieved json input " + jsonInput);
            msgs = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Message.class));

            ArrayList<Message> msgList = new ArrayList<>(msgs);
            // return array of Ids
            return msgList;

        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        }

        return null;
    }
    public Message getMessageForSequence(String seq) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Send a GET request to the server to retrieve the message with the given sequence
            String response = sc.sendRequest("/messages/" + seq, "GET", "");

            // Deserialize the response JSON into a Message object
            return mapper.readValue(response, Message.class);

        } catch (JsonProcessingException e) {


            return null;
        }
    }
    public ArrayList<Message> getMessagesFromFriend(Id myId, Id friendId) {
        return null;
    }

    public Message postMessage(String myId, String toId, String msg) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
////            id = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Id.class));
//            //Convert ID to JSON
//            String jsonInput = mapper.writeValueAsString(msg);
//
//            //Send POST request
//            String jsonResponse = sc.sendRequest("/messages", "POST", jsonInput);
//            return mapper.readValue(jsonResponse,Message.class);
//
//        }
//        catch (JsonProcessingException e) {
//            System.out.println("Failed to post message " + e.getMessage());
//        }
//        return null;
//    }
        Message message = new Message(myId,toId,msg);


        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, message);
        String jsonBody = writer.toString();


//            msg.setFromid(myId);
//            msg.setToid(toId);
//           sc.sendRequest("/ids/" + myId + "/messages", "POST", mapper.writeValueAsString(message));
        sc.sendMessage(myId,toId,jsonBody);

        return message;

    }
 
}