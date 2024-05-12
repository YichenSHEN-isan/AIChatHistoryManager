import java.util.ArrayList;
import java.util.List;

public class Dialog {
    private String title;
    private List<Conversation> conversations;

    public Dialog() {
        this.title = "default title";
        this.conversations = new ArrayList<>();
    }

    public Dialog(String title) {
        this.title = title;
        this.conversations = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public List<Conversation> getConversations() {
        return new ArrayList<>(conversations);
    }

    public void addConversation(Conversation conversation) {
        this.conversations.add(conversation);
    }
}

