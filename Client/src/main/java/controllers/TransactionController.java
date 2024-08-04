package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import models.Id;
import models.Message;

import java.io.IOException;
import java.util.List;

public class TransactionController {
    private String rootURL = "http://zipcode.rocks:8085";
    private MessageController msgCtrl;
    private IdController idCtrl;

    public TransactionController(MessageController m, IdController j) {
        msgCtrl = m;
        idCtrl = j;
    }

    public List<Id> getIds() {
        return idCtrl.getIds();
    }

    public Id getId(String id) {
        return idCtrl.getId(id);
    }
    public String putId(Id id) {
        Id updatedID = idCtrl.putId(id);

        if(updatedID != null){
            return "id updated successfully" ;
        }
        return "Failed to update id";
    }

    public String deleteId(String id) {

        return idCtrl.deleteID(id);
    }

    public String postId(Id id) {
//         Id tid = new Id(idtoRegister, githubName);
//         tid = idCtrl.postId(tid);
//         return ("Id registered.");
        Id postedID = idCtrl.postId(id);
        if(postedID != null){
            return "Id registeted";
        }

        return "Failed to register id.";
    }

    public List<Message> getMessages() {
        return msgCtrl.getMessages();
    }
    public List<Message> getMessagesForID(String id){
        if(msgCtrl.getMessagesForId(id) == null){
            System.out.println("No messages available");
            return null;
        }
        return msgCtrl.getMessagesForId(id);
    }

    public String postMessage(String myId, String toId, String msg) throws IOException {
//         Message message = new Message(msg,myId,toId);
         msgCtrl.postMessage(myId,toId,msg);

         return "Message posted";
    }

    public Message getMessageFromSeq(String seq){

        return msgCtrl.getMessageForSequence(seq);
    }




}
