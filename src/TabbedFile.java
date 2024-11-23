import java.io.*;
import java.util.ArrayList;

public class TabbedFile {
    public static ArrayList<TabbedFile> tabbedFiles = new ArrayList<>();
    public File file;
    public String fileName;

    public TabbedFile() {
    }

    public static boolean addFile(File file) {
        TabbedFile newFile = new TabbedFile();
        newFile.file = file;
        newFile.fileName = file.getName();
        if(!isOpened(file)) {
            tabbedFiles.add(newFile);
            return true;
        }
        return false;
    }

    public String getFileContent(int index) {
        String fileContent = "";
        try (BufferedReader fileReader = new BufferedReader(new FileReader(tabbedFiles.get(index).file))) {
            String line ="";
            while ((line = fileReader.readLine()) != null) {
                fileContent += line + "\n";
            }
        } catch (IOException e) {

        }
        return fileContent;
    }

    private static boolean isOpened(File file) {
        for (TabbedFile tabbedFile : tabbedFiles) {
            if(tabbedFile.file.getAbsolutePath().equals(file.getAbsolutePath())) {
                return true;
            }
        }
        return false;
    }

    public static void saveFile(int index, String fileContent) {
        TabbedFile savingFile = tabbedFiles.get(index);
        try(BufferedWriter fileWriter = new BufferedWriter(new FileWriter(savingFile.file))) {
            fileWriter.write(fileContent);
        } catch (IOException e) {
            System.out.println("Error while saving file");
        }
    }

    public static boolean saveNewFile(File file, String fileContent) {
        if(file.exists()) {
            return false;
        } else {
            try {
                file.createNewFile();
                File newFile = new File(file.getAbsolutePath());
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(newFile));
                fileWriter.write(fileContent);
                fileWriter.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }

}
