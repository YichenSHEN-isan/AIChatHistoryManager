import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/*
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 /This is a Chat History Tracker for AI chatbot, it aims to provide an experimental solution for chat history management./
 /The basic unit of chat history is "Dialog", which has a title and contains multiple conversations (Q&A).               /
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  Usage:

  You could input (simply by copying) your chat history with any AI chatbot into this system for future search.
  Chat histories are store locally as JSON files in "dialogs" directive, so you will not lose them after restarting.

  Create new dialog:
      First, you can click "New Dialog" to create a new dialog. Input your title and first round of conversation.
      Now you can see your new dialog appear on the main page.
  Add new conversation to certain dialog:
      You can click it to view all conversations in this dialog. You can also add new conversation by clicking the add
      button below.
  Search:
      Back to the main page, you can search the chat history. The search is performed on everything (both title and
      contents). You can input a keyword and click "search", then the dialogs containing this keyword will be filtered
      and presented below. Any languages are supported.

  There are 4 JSON files of multiple languages in "dialogs" directive for test.
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
*/

public class MainApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainWindow.setSize(800, 600);
            mainWindow.setVisible(true);
        });
    }
}

