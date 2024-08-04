package controllers;

import models.Id;
import models.Message;

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


}
