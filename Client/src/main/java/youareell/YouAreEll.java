package youareell;

import controllers.*;

public class YouAreEll {
    private TransactionController tt;

    public YouAreEll (TransactionController t) {
        this.tt = t;
    }

    public static void main(String[] args) {
        // hmm: is this Dependency Injection?
        YouAreEll urlhandler = new YouAreEll(
            new TransactionController(
                new MessageController(), new IdController()
        ));
    }

    public String get_ids() {
        return "Not Implemented";
    }

    public String get_messages() {
        return "Not Implemented";
    }


}
