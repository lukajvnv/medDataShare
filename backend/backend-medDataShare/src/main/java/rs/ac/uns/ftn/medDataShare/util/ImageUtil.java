package rs.ac.uns.ftn.medDataShare.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImageUtil {

    private static final String IMAGE_FOLDER = "resources/image/";

    public static byte[] getBytes(String fileName) {
        String fileUrl = IMAGE_FOLDER + fileName;
        File outputFile = new File(fileUrl);
        try {
            byte[] ui = Files.readAllBytes(outputFile.toPath());
            return ui;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
