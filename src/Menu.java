import javax.swing.*;
import java.util.ArrayList;

public class Menu extends JMenuBar {
    Listener eventHandler;

    public Menu(Listener eventHandler) {
        this.eventHandler = eventHandler;

        JMenu fileMenu = new JMenu("File");
        JMenu runMenu = new JMenu("Run");

        ArrayList<JMenuItem> fileMenuItems = new ArrayList<>();
        ArrayList<JMenuItem> runMenuItems = new ArrayList<>();

        fileMenuItems.add(new JMenuItem("Open"));
        fileMenuItems.add(new JMenuItem("Close"));
        fileMenuItems.add(new JMenuItem("Save"));
        fileMenuItems.add(new JMenuItem("Save as"));
        fileMenuItems.add(new JMenuItem("Quit"));

        runMenuItems.add(new JMenuItem("Compile"));

        for(JMenuItem menuItem : fileMenuItems) {
            fileMenu.add(menuItem);
            menuItem.setActionCommand(menuItem.getText());
            menuItem.addActionListener(eventHandler);
        }

        for(JMenuItem menuItem : runMenuItems) {
            runMenu.add(menuItem);
            menuItem.setActionCommand(menuItem.getText());
            menuItem.addActionListener(eventHandler);
        }

        add(fileMenu);
        add(runMenu);
    }


}
