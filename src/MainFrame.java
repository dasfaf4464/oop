import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    JTabbedPane editor;
    JTextArea results;
    Menu menuBar;
    Listener eventHandler;

    public MainFrame() {
        setTitle("ide");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        eventHandler = new Listener(this);

        menuBar = new Menu(eventHandler);
        setJMenuBar(menuBar);

        Container container = getContentPane();
        container.setLayout(new GridLayout(2,1));

        editor = new JTabbedPane();
        editor.setFocusable(true);
        editor.addFocusListener(eventHandler);

        results = new JTextArea();

        container.add(editor);
        container.add(results);

        setVisible(true);
    }

    public void addTab(String title, String content) {
        JScrollPane pane = new JScrollPane(new JTextArea(content));
        editor.add(title, pane);
    }

    public void showSameFileOpen() {
        JDialog warning = new JDialog(this);
        warning.setTitle("Warning");
        warning.setModal(true);
        warning.setLocationRelativeTo(null);
        Container c = warning.getContentPane();
        c.setLayout(new GridLayout(2,1));
        c.add(new JLabel("Warning"));
        c.add(new JLabel("File has already been opened"));
        warning.pack();
        warning.setVisible(true);
    }

    public void showSameFileSave() {
        JDialog warning = new JDialog(this);
        warning.setTitle("Warning");
        warning.setModal(true);
        warning.setLocationRelativeTo(null);
        Container c = warning.getContentPane();
        c.setLayout(new GridLayout(2,1));
        c.add(new JLabel("Warning"));
        c.add(new JLabel("File has already exist"));
        warning.pack();
        warning.setVisible(true);
    }

    public void setResults(String text) {
        results.setText(text);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
