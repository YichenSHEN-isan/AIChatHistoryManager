import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MainWindow extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JButton clearButton;
    private JButton newDialogButton;
    private JPanel dialogListPanel;

    public MainWindow() {
        super("AI Bot Dialog Tracker");
        setupUI();
        refreshDialogList();
    }

    private void setupUI() {
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");
        newDialogButton = new JButton("New Dialog");
        
        dialogListPanel = new JPanel();
        dialogListPanel.setLayout(new BoxLayout(dialogListPanel, BoxLayout.Y_AXIS));
        //dialogListPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        searchButton.addActionListener(this::searchDialogs);
        clearButton.addActionListener(e -> clearSearchField());
        newDialogButton.addActionListener(e -> openNewDialogWindow());

        JPanel topPanel = new JPanel();
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(clearButton);
        topPanel.add(newDialogButton);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(dialogListPanel), BorderLayout.CENTER);
    }

    private void searchDialogs(ActionEvent e) {
        String keyword = searchField.getText();
        if (keyword.equals("")){
            refreshDialogList();
        }
        
        else{
            dialogListPanel.removeAll();
            dialogListPanel.add(Box.createVerticalStrut(20));
            JLabel searchLabel = new JLabel("Dialogs that match your query:");
            searchLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            dialogListPanel.add(searchLabel);
            dialogListPanel.add(Box.createVerticalStrut(20));
            List<Dialog> dialogs = FileManager.searchDialogs(keyword);
            for (Dialog dialog : dialogs) {
                JButton button = new JButton(dialog.getTitle());
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                //button.setLayout(new FlowLayout(FlowLayout.CENTER));
                button.addActionListener(ev -> openDialogDetailWindow(dialog));
                dialogListPanel.add(button);
                dialogListPanel.add(Box.createVerticalStrut(10));
            }
        dialogListPanel.revalidate();
        dialogListPanel.repaint();
        }
    }

    private void clearSearchField() {
        searchField.setText("");
        refreshDialogList();
    }

    public void refreshDialogList() {
        dialogListPanel.removeAll();
        dialogListPanel.add(Box.createVerticalStrut(20));
        JLabel startLabel = new JLabel("All your dialogs:");
        startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialogListPanel.add(startLabel);
        dialogListPanel.add(Box.createVerticalStrut(20));
        List<String> titles = FileManager.getAllDialogTitles();
        for (String title : titles) {
            JButton button = new JButton(title);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(e -> openDialogDetailWindow(title));
            dialogListPanel.add(button);
            dialogListPanel.add(Box.createVerticalStrut(10));
        }
        dialogListPanel.revalidate();
        dialogListPanel.repaint();
    }


    private void openNewDialogWindow() {
        NewDialogWindow newDialogWindow = new NewDialogWindow(this);
        newDialogWindow.setVisible(true);
    }

    private void openDialogDetailWindow(Dialog dialog) {
        DialogDetailWindow dialogDetailWindow = new DialogDetailWindow(this, dialog);
        dialogDetailWindow.setVisible(true);
    }

    // Overload
    private void openDialogDetailWindow(String title) {
        Dialog dialog = FileManager.readDialog(title);
        if (dialog != null) {
            DialogDetailWindow dialogDetailWindow = new DialogDetailWindow(this, dialog);
            dialogDetailWindow.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Dialog data could not be loaded.");
        }
    }

}

