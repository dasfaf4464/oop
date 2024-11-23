import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Compiler {
    public String compile(File file) {
        int exitCode;
        String message;

        String[] compileCommand = { //컴파일커맨드 구성(한 개만 컴파일)
                System.getProperty("java.home")+"//bin//javac.exe", //자바 컴파일러 절대경로
                "-d",
                file.getParentFile().getAbsolutePath(), //저장할 폴더 절대경로
                file.getAbsolutePath()//컴파일할 소스코드 절대경로
        };

        ProcessBuilder compileBuilder = new ProcessBuilder(compileCommand);
        compileBuilder.redirectErrorStream(true);
        Process compileProcess;

        try {
            compileProcess = compileBuilder.start();
            exitCode = compileProcess.waitFor();
        }
        catch (InterruptedException | IOException e) {
            message = "Failed to start compiler process: " + e.getMessage();
            return message;
        }

        if(exitCode != 0) {

            try {
                InputStream errorStream = compileProcess.getInputStream();
                message = new String(errorStream.readAllBytes());
                return message;
            }
            catch (IOException e) {
                message = "Failed to read error output: " + e.getMessage();
                return message;
            }

        } else {
            message = "Compilation completed successfully";
            return message;
        }
    }
}
