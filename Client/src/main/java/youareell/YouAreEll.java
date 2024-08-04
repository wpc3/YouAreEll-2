package youareell;

import java.util.List;

import controllers.*;
import models.Id;

public class YouAreEll {
    private TransactionController tt;

    public YouAreEll (TransactionController t) {
        this.tt = t;
    }

    public static void main(String[] args) {
        // hmm: is this Dependency Injection?
        YouAreEll urlhandler = new YouAreEll(
            new TransactionController(
                new MessageController(ServerController.shared()), 
                new IdController(ServerController.shared())
        ));
    }

    public String get_ids() {
        List<models.Id> allIds = tt.getIds();
        StringBuilder sb = new StringBuilder();
        for (models.Id id : allIds) {
            sb.append(id.toString()+"\n");
        }
        return sb.toString();
    }

    public String get_messages() {
        List<models.Message> latestMessages = tt.getMessages();
        StringBuilder sb = new StringBuilder();
        for (models.Message msg : latestMessages) {
            sb.append(msg.toString()+"\n");
        }
        return sb.toString();
    }

    public Id get_Id(String id){

        return tt.getId(id);
    }

    public String post_Id(Id id){
        String postId = tt.postId(id);

        return postId != null ? postId : "Failed to register ID. \n";
    }


    public String put_Id(Id id){
        String puttId = tt.putId(id);

        return puttId != null ? puttId : "Failed to register ID. \n";
    }

    public String delete_Id(String id){
        String deleteId = tt.deleteId(id);

        return deleteId != null ? deleteId : "Failed to delete ID. \n";
    }


}
