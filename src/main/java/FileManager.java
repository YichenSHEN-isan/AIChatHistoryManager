import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
//import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;

public class FileManager {
    private static final String DIRECTORY = "dialogs";

    static {
        new File(DIRECTORY).mkdirs();
    }

    public static Dialog readDialog(String title) {
        File file = new File(DIRECTORY, title + ".json");
        if (file.exists()) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                JSONObject jsonObject = new JSONObject(content);
                Dialog dialog = new Dialog(jsonObject.getString("title"));
                JSONArray conversations = jsonObject.getJSONArray("conversations");
                for (int i = 0; i < conversations.length(); i++) {
                    JSONObject convObj = conversations.getJSONObject(i);
                    dialog.addConversation(new Conversation(convObj.getString("question"), convObj.getString("answer")));
                }
                return dialog;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static List<Dialog> loadDialogs() {
        List<Dialog> dialogs = new ArrayList<>();
        File folder = new File(DIRECTORY);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    try {
                        String content = new String(Files.readAllBytes(file.toPath()));
                        JSONObject jsonObject = new JSONObject(content);
                        Dialog dialog = new Dialog(jsonObject.getString("title"));
                        JSONArray conversations = jsonObject.getJSONArray("conversations");
                        for (int i = 0; i < conversations.length(); i++) {
                            JSONObject convObj = conversations.getJSONObject(i);
                            dialog.addConversation(new Conversation(convObj.getString("question"), convObj.getString("answer")));
                        }
                        dialogs.add(dialog);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return dialogs;
    }

    public static void saveDialog(Dialog dialog) {
        try (FileWriter file = new FileWriter(DIRECTORY + "/" + dialog.getTitle() + ".json")) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", dialog.getTitle());
            JSONArray conversationsArray = new JSONArray();
            for (Conversation conv : dialog.getConversations()) {
                JSONObject convObject = new JSONObject();
                convObject.put("question", conv.getQuestion());
                convObject.put("answer", conv.getAnswer());
                conversationsArray.put(convObject);
            }
            jsonObject.put("conversations", conversationsArray);
            file.write(jsonObject.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getAllDialogTitles() {
        File folder = new File(DIRECTORY);
        File[] listOfFiles = folder.listFiles();
        List<String> titles = new ArrayList<>();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".json")) {
                    String title = file.getName().replace(".json", "");
                    titles.add(title);
                }
            }
        }
        return titles;
    }

    public static void appendConversation(String dialogTitle, Conversation newConversation) {
        File file = new File(DIRECTORY, dialogTitle + ".json");
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            JSONObject existingData = new JSONObject(content);
            JSONArray conversations = existingData.optJSONArray("conversations");
            if (conversations == null) {
                conversations = new JSONArray();
                existingData.put("conversations", conversations);
            }
            JSONObject newConv = new JSONObject();
            newConv.put("question", newConversation.getQuestion());
            newConv.put("answer", newConversation.getAnswer());
            conversations.put(newConv);
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(existingData.toString(4));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    public static List<Dialog> searchDialogs(String keyword) {
        // return a list of dialogs which contains subword match of keyword in both title and conversation.

    }
    */

    public static List<Dialog> searchDialogs(String keyword) {
        String[] keywords = keyword.toLowerCase().split("\\s+");
        List<Dialog> allDialogs = loadDialogs();
        Set<Dialog> matchingDialogsSet = new HashSet<>();

        for (Dialog dialog : allDialogs) {
            boolean matchFound = false;

            for (String kw : keywords) {
                if (dialog.getTitle().toLowerCase().contains(kw)) {
                    matchFound = true;
                    break;
                }
            }

            if (!matchFound) {
                for (Conversation conv : dialog.getConversations()) {
                    for (String kw : keywords) {
                        if (conv.getQuestion().toLowerCase().contains(kw) || conv.getAnswer().toLowerCase().contains(kw)) {
                            matchFound = true;
                            break;
                        }
                    }
                    if (matchFound) {
                        break;
                    }
                }
            }

            if (matchFound && !matchingDialogsSet.contains(dialog)) {
                matchingDialogsSet.add(dialog);
            }
        }

        return new ArrayList<>(matchingDialogsSet);
    }

}
