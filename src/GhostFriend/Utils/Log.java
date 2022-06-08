package GhostFriend.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

public class Log {
    private static final String dir = "Log";
    private static final String fileName = "Server.log";
    private static File file;

    private static synchronized void createDir() {
        try {
            Path dirPath = Paths.get(dir);
            Files.createDirectories(dirPath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static synchronized void createFile() {
        if (file == null) {
            file = new File(dir + "/" + fileName);
        }

        if (file.exists()) {
            return;
        }

        try {
            if (!file.createNewFile()) {
                throw new IOException("File not created");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void printText(String text) throws IOException {
        createDir();
        createFile();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(simpleDateFormat.format(System.currentTimeMillis()));
            fileWriter.write(" (" + Thread.currentThread().getId() + ")");
            fileWriter.write(" ---- ");
            fileWriter.write(text);
            fileWriter.write("\n");
            fileWriter.flush();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(text);
    }
}
