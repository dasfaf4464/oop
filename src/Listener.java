import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Listener extends KeyAdapter implements ActionListener, FocusListener, MouseListener {
    private MainFrame frame;
    private Set<Integer> pressedKeys;
    private Compiler compiler;
    boolean tabFocused = false;

    public Listener(MainFrame frame) {
        this.frame = frame;
        pressedKeys = new HashSet<>();
        compiler = new Compiler();
    }

    private void openItemClicked() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.showOpenDialog(frame);
        File file = fileChooser.getSelectedFile();
        if(file.exists()) {
            TabbedFile tabbedFile = new TabbedFile();
            if(TabbedFile.addFile(file)) {
                frame.addTab(TabbedFile.tabbedFiles.getLast().fileName, tabbedFile.getFileContent(TabbedFile.tabbedFiles.size()-1));
            } else {
                frame.showSameFileOpen();
            }
        }
    }

    private void closeItemClicked() {
        int index = frame.editor.getSelectedIndex();
        if(index != -1) {
            TabbedFile.tabbedFiles.remove(index);
            frame.editor.remove(index);
        }
    }

    private void saveItemClicked() {
        int index = frame.editor.getSelectedIndex();

        if(index != -1) {
            JDialog dialog = new JDialog();
            Container c = dialog.getContentPane();
            c.setLayout(new GridLayout(3,1));
            c.add(new JLabel("저장?"), BorderLayout.CENTER);
            JButton s = new JButton("yes");
            JButton n = new JButton("no");
            c.add(s, BorderLayout.SOUTH);
            c.add(n, BorderLayout.NORTH);
            dialog.pack();
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
            s.setActionCommand("yes");
            s.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JScrollPane selectedScroll = (JScrollPane) frame.editor.getComponentAt(index);
                            JViewport viewport = selectedScroll.getViewport();
                            JTextArea selectedComponent = (JTextArea) viewport.getView();
                            TabbedFile.saveFile(index, selectedComponent.getText());
                            dialog.setVisible(false);
                            dialog.dispose();
                        }
                    }
            );
            n.setActionCommand("no");
            n.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            dialog.setVisible(false);
                            dialog.dispose();
                        }
                    }
            );

        }


    }

    private void saveAsItemClicked() {
        int index = frame.editor.getSelectedIndex();
        if(index != -1) {
            JScrollPane selectedScroll = (JScrollPane) frame.editor.getComponentAt(index);
            JViewport viewport = selectedScroll.getViewport();
            JTextArea selectedComponent = (JTextArea) viewport.getView();
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
            fileChooser.showSaveDialog(frame);
            File file = fileChooser.getSelectedFile();
            System.out.println(selectedComponent.getText());
            if(!TabbedFile.saveNewFile(file, selectedComponent.getText())){
                frame.showSameFileSave();
            }
        }
    }


    private void quitItemClicked() {
        try{
            JDialog dialog = new JDialog();
            dialog.setModal(true);
            dialog.setLocationRelativeTo(frame);
            Container c = dialog.getContentPane();
            c.setLayout(new FlowLayout(FlowLayout.CENTER));
            c.add(new JLabel("열려있는 모든 파일을 저장하고 종료합니다."));
            dialog.pack();
            dialog.setVisible(true);
            Thread.sleep(2000);
            for(int i = 0; i < frame.editor.getComponentCount(); i++) {
                JScrollPane selectedScroll = (JScrollPane) frame.editor.getComponentAt(i);
                JViewport viewport = selectedScroll.getViewport();
                JTextArea selectedComponent = (JTextArea) viewport.getView();
                TabbedFile.saveFile(i, selectedComponent.getText());
            }
            frame.setVisible(false);
            System.exit(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void compileItemClicked() {
        int index = frame.editor.getSelectedIndex();
        if(index != -1) {
            JScrollPane selectedScroll = (JScrollPane) frame.editor.getComponentAt(index);
            JViewport viewport = selectedScroll.getViewport();
            JTextArea selectedComponent = (JTextArea) viewport.getView();
            TabbedFile.saveFile(index, selectedComponent.getText());
            File compileFile = TabbedFile.tabbedFiles.get(index).file;
            frame.setResults(compiler.compile(compileFile));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem) e.getSource();
        switch (menuItem.getText()) {
            case "Open": openItemClicked(); break;
            case "Close": closeItemClicked(); break;
            case "Save": saveItemClicked(); break;
            case "Save as": saveAsItemClicked(); break;
            case "Quit": quitItemClicked(); break;

            case "Compile": compileItemClicked(); break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
        if(tabFocused && pressedKeys.contains(KeyEvent.VK_CONTROL) && pressedKeys.contains(KeyEvent.VK_R)) {
            compileItemClicked();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override public void focusGained(FocusEvent e) {
        if(e.getSource() instanceof JTextArea || e.getSource() instanceof JScrollPane) {
            System.out.println("focused");
            tabFocused = true;
        }
    }

    @Override public void focusLost(FocusEvent e) {
        if(e.getSource() instanceof JTextArea || e.getSource() instanceof JScrollPane) {
            System.out.println("focus lost");
            tabFocused = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        JComponent component = (JComponent) e.getSource();
        component.requestFocus();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
