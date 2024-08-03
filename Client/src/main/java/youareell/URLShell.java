package youareell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import controllers.IdController;
import controllers.MessageController;
import controllers.ServerController;
import controllers.TransactionController;
import models.Id;

// URLShell is a Console view for youareell.YouAreEll.
public class URLShell {
    public static void prettyPrint(String output) {
        // yep, make an effort to format things nicely, eh?
        System.out.println(output);
    }


    public static void main(String[] args) throws java.io.IOException {
        new URLShell().run();
    }

    public void run() throws IOException {
        YouAreEll urll = new YouAreEll(new TransactionController(new MessageController(ServerController.shared()), 
        new IdController(ServerController.shared())));
        
        String commandLine;
        BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in));

        ProcessBuilder pb = new ProcessBuilder();
        List<String> history = new ArrayList<String>();
        int index = 0;
        //we break out with <ctrl c>
        while (true) {
            //read what the user enters
            System.out.println("enter a command ");
            commandLine = console.readLine();

            //input parsed into array of strings(command and arguments)
            String[] commands = commandLine.split(" ");
            List<String> list = new ArrayList<String>();

            //if the user entered a return, just loop again
            if (commandLine.equals(""))
                continue;
            if (commandLine.equals("exit")) {
                System.out.println("\n*** Bye!\n");
                break;
            }

            //loop through to see if parsing worked
            for (int i = 0; i < commands.length; i++) {
                //System.out.println(commands[i]); //***check to see if parsing/split worked***
                list.add(commands[i]);

            }
            //System.out.print(list); //***check to see if list was added correctly***
            history.add(commandLine);
            try {
                //display history of shell with index
                if (list.get(list.size() - 1).equals("history")) {
                    for (String s : history)
                        System.out.println((index++) + " " + s);
                    continue;
                }

                // Specific Commands.

                // ids
                if (list.get(0).contains("ids")) {
                    String results = urll.get_ids();
                    URLShell.prettyPrint(results);
                    continue;
                }

                // messages
                if (list.get(0).contains("messages")) {
                    String results = urll.get_messages();
                    URLShell.prettyPrint(results);
                    continue;
                }

                String command = list.get(0);

                switch (command) {
//                    case "ids":
//                        prettyPrint(urll.get_ids());
//                        break;
//                    case "messages":
//                        prettyPrint(urll.get_messages());
//                        break;
                    case "POST":
                        handlePostID(list, urll);
                        break;
                    case "DELETE":
                        handleDeletetID(list, urll);
                        break;
                    case "PUT":
                        handlePutID(list, urll);
                        break;
                }

                // you need to add a bunch more.

                //post ids
//                if (list.get(0).contains("POST") ){
//                    String pid = list.get(1);
//                    String github = list.get(2);
//                    String name = list.get(3);
//                    String results = urll.post_Id(pid);
//                    URLShell.prettyPrint(results);
//                    continue;
//                }

                //!! command returns the last command in history
                if (list.get(list.size() - 1).equals("!!")) {
                    pb.command(history.get(history.size() - 2));

                }//!<integer value i> command
                // there is BUG in this code, can you find it?
                else if (list.get(list.size() - 1).charAt(0) == '!') {
                    int b = Character.getNumericValue(list.get(list.size() - 1).charAt(1));
                    if (b <= history.size())//check if integer entered isn't bigger than history size
                        pb.command(history.get(b));
                } else {
                    pb.command(list);
                }



//                // // wait, wait, what curiousness is this?
//                 Process process = pb.start();
//
//                // //obtain the input stream
//                 InputStream is = process.getInputStream();
//                 InputStreamReader isr = new InputStreamReader(is);
//                 BufferedReader br = new BufferedReader(isr);
//
//                // //read output of the process
//                 String line;
//                 while ((line = br.readLine()) != null)
//                     System.out.println(line);
//                 br.close();


            } finally {
//                 System.out.println("Input Error, Please try again!");
            }

//            catch ioexception, output appropriate message, resume waiting for input
//             catch (IOException e) {
//                 System.out.println("Input Error, Please try again!");
//             }
            // So what, do you suppose, is the meaning of this comment?
            /** The steps are:
             * 1. parse the input to obtain the command and any parameters
             * 2. create a ProcessBuilder object
             * 3. start the process
             * 4. obtain the output stream
             * 5. output the contents returned by the command
             */

        }


    }
    private void handlePostID(List<String> list, YouAreEll urll){
        if(list.size() < 4){
            System.out.println("POST: id, githubName, name");
            return;
        }
        String id = list.get(1);
        String githubName = list.get(2);
        String name = list.get(3);

        Id newID = new Id(id,githubName,name);
        String result = urll.post_Id(newID);
        prettyPrint(result != null ? "ID posted: " + result : "Failed to post ID");
    }
    private void handlePutID(List<String> list, YouAreEll urll){
        if(list.size() < 4){
            System.out.println("PUT: id, githubName, name");
            return;
        }
        String id = list.get(1);
        String githubName = list.get(2);
        String name = list.get(3);

        Id newID = new Id(id,githubName,name);
        String result = urll.put_Id(newID);
        prettyPrint(result != null ? "ID updated: " + result : "Failed to update ID");
    }

    private void handleDeletetID(List<String> list, YouAreEll urll) {
    if(list.size() < 2){
        System.out.println("DELETE id");
        return;
    }

    String id = list.get(1);
    String success =urll.delete_Id(id);
        System.out.println(success != null ? "ID deleted" : "Failed to delete id.");
    }

}