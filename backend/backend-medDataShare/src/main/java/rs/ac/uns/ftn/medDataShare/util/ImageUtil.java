package rs.ac.uns.ftn.medDataShare.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImageUtil {

    private static final String IMAGE_FOLDER = "resources/image/";


    public static boolean uploadImage(byte[] image, String fileName, String fileType) {
        try {
            String fileUrl = IMAGE_FOLDER + fileName;

            File outputFile = new File(fileUrl);

            ByteArrayInputStream bis = new ByteArrayInputStream(image);
            BufferedImage bImage = ImageIO.read(bis);
            return ImageIO.write(bImage, fileType, outputFile);
        } catch (IOException e) {
            System.out.println("Exception occured :" + e.getMessage());
            return false;
        }

    }

    public static byte[] getBytes() {
        String fileName = "grupe_it.png";
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
