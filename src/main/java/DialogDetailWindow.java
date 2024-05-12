import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DialogDetailWindow extends JFrame {
    private Dialog dialog;
    private JTextArea conversationArea;
    private MainWindow mainWindow;
    private JButton addButton;

    public DialogDetailWindow(MainWindow mainWindow, Dialog dialog) {
        super("Dialog Details");
        this.mainWindow = mainWindow;
        this.dialog = dialog;
        setupUI();
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void setupUI() {
        conversationArea = new JTextArea(20, 50);
        conversationArea.setText(formatConversations());
        conversationArea.setEditable(false);

        addButton = new JButton("+ Add Conversation");
        addButton.addActionListener(this::addConversation);

        add(new JScrollPane(conversationArea), BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);
    }

    private String formatConversations() {
        StringBuilder sb = new StringBuilder();
        for (Conversation conv : dialog.getConversations()) {
            sb.append("Q: ").append(conv.getQuestion()).append("\n");
            sb.append("A: ").append(conv.getAnswer()).append("\n\n");
        }
        return sb.toString();
    }

    private void addConversation(ActionEvent e) {
        String question = JOptionPane.showInputDialog(this, "Enter your question:");
        String answer = JOptionPane.showInputDialog(this, "Enter AI's answer:");
        if (question != null && answer != null && !question.isEmpty() && !answer.isEmpty()) {
            Conversation newConv = new Conversation(question, answer);
            dialog.addConversation(newConv);
            FileManager.appendConversation(dialog.getTitle(), newConv);
            conversationArea.setText(formatConversations());
            conversationArea.invalidate();
            conversationArea.revalidate();
        }
    }
}

