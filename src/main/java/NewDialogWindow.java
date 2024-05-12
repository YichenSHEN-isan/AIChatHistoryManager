import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NewDialogWindow extends JFrame {
    private MainWindow mainWindow;
    private JTextField titleField;
    private JTextArea questionArea, answerArea;
    private JButton saveButton;

    public NewDialogWindow(MainWindow mainWindow) {
        super("New Dialog");
        this.mainWindow = mainWindow;
        setupUI();
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void setupUI() {
        titleField = new JTextField(20);
        questionArea = new JTextArea(5, 20);
        answerArea = new JTextArea(5, 20);
        saveButton = new JButton("Save");

        saveButton.addActionListener(this::saveDialog);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(new JLabel("Title:"));
        titlePanel.add(titleField);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 1));
        contentPanel.add(new JLabel("Question:"));
        contentPanel.add(new JScrollPane(questionArea));
        contentPanel.add(new JLabel("Answer:"));
        contentPanel.add(new JScrollPane(answerArea));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void saveDialog(ActionEvent e) {
        String title = titleField.getText();
        String question = questionArea.getText();
        String answer = answerArea.getText();
        if (!title.isEmpty() && !question.isEmpty() && !answer.isEmpty()) {
            Dialog dialog = new Dialog(title);
            dialog.addConversation(new Conversation(question, answer));
            FileManager.saveDialog(dialog);
            mainWindow.refreshDialogList();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

