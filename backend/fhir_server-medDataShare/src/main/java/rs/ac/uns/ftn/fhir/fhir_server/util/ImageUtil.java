package rs.ac.uns.ftn.fhir.fhir_server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.ac.uns.ftn.fhir.fhir_server.provider.ImagingStudyProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImageUtil {

    private static final String IMAGE_FOLDER = "resources/image/";

    private static final Logger log = LoggerFactory.getLogger(ImageUtil.class);

    public static boolean uploadImage(byte[] image, String fileName, String fileType) {
        try {
            String fileUrl = IMAGE_FOLDER + fileName;

            File outputFile = new File(fileUrl);

            ByteArrayInputStream bis = new ByteArrayInputStream(image);
            BufferedImage bImage = ImageIO.read(bis);
            return ImageIO.write(bImage, fileType, outputFile);
        } catch (Exception e) {
            System.out.println("Exception occured :" + e.getMessage());
            log.info("Exception occured :" + e.getMessage());
            return false;
        }
    }

    public static byte[] getFile(String file, String extension) {
        String fileName = file + "." + extension;
        String fileUrl = IMAGE_FOLDER + fileName;
        File outputFile = new File(fileUrl);
        try {
            byte[] ui = Files.readAllBytes(outputFile.toPath());
            return ui;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
