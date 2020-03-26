package GhostFriend.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Log {
    private static File file;
    private static FileWriter fileWriter;

    public static synchronized void printText(String text) throws IOException {
        if (file == null) {
            file = new File("Log/ServerLog.txt");
        }

        fileWriter = new FileWriter(file, true);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");

        try {
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

        finally {
            fileWriter.close();
        }


        System.out.println(text);
    }
}
