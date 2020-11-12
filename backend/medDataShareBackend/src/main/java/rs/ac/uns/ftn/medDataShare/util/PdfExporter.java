package rs.ac.uns.ftn.medDataShare.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.hl7.fhir.r4.model.Binary;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.ClinicalTrialDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.stream.Stream;

public class PdfExporter {

    private static final String FILE_PATH = "resources/pdf/trial.pdf";
    private static final String IMAGE_PATH = "resources/image/grupe_it.png";

    public static byte[] getBytes() {
        File outputFile = new File(FILE_PATH);
        try {
            byte[] ui = Files.readAllBytes(outputFile.toPath());
            return ui;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getBytes(String fileName) {
        File outputFile = new File(fileName);
        try {
            byte[] ui = Files.readAllBytes(outputFile.toPath());
            return ui;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] clinicalTrialExportPdf(String fileName){
        fileName = "resources/pdf/temp.pdf";
        try {
            export(fileName);
            byte[] bytes = getBytes();
            deleteFile(fileName);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        byte[] bytes = getBytes();
        deleteFile(fileName);
        return bytes;
    }

    public static byte[] clinicalTrialExportPdf(ClinicalTrialDto clinicalTrialDto, Binary binary){
        String fileName = "resources/pdf/temp.pdf";
        try {
            export(fileName, clinicalTrialDto, binary);
            byte[] bytes = getBytes(fileName);
            deleteFile(fileName);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        byte[] bytes = getBytes();
        deleteFile(fileName);
        return bytes;
    }

    private static void export(String fileName, ClinicalTrialDto clinicalTrialDto, Binary binary) throws IOException, DocumentException, URISyntaxException {
        Document document = new Document();

        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(fileName));

        document.open();

        document.addAuthor("MedDataShare");
        document.addCreationDate();
        document.addTitle("Clinical trial view");
        document.addHeader("header", "header desc");

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("ClinicalTrial", font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setPaddingTop(50f);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(2);
        table.setSpacingBefore(20f);
        table.setSpacingAfter(20f);
        addTableHeader(table);
        populateTable(table, clinicalTrialDto);

        document.add(table);

        Chunk chunkImage = new Chunk("Image:", font);
        Paragraph paragraphImage = new Paragraph(chunkImage);
        paragraphImage.setAlignment(Element.ALIGN_CENTER);
        paragraphImage.setPaddingTop(50f);
        document.add(paragraphImage);

        Image img = Image.getInstance(binary.getContent());
        document.add(img);

        document.close();
    }

    private static void export(String fileName) throws IOException, DocumentException, URISyntaxException {
        Document document = new Document();

        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(fileName));

        document.open();

        document.addAuthor("Luka");
        document.addCreationDate();
        document.addTitle("Newer pdf");
        document.addHeader("header", "header desc");

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setPaddingTop(50f);
        document.add(paragraph);
        document.add(paragraph);

        Image img = Image.getInstance(IMAGE_PATH);

        PdfPTable table = new PdfPTable(2);
        table.setSpacingBefore(20f);
        table.setSpacingAfter(20f);
        addTableHeader(table);
        addRows(table);
        addCustomRows(table);

        document.add(table);

        document.add(img);
        document.close();
    }

    private static void encrypt() throws IOException, DocumentException {
        PdfReader pdfReader = new PdfReader("src/main/resources/pdf/iTextHelloWorld.pdf");
        PdfStamper pdfStamper
                = new PdfStamper(pdfReader, new FileOutputStream("src/main/resources/pdf/encryptedPdf.pdf"));

        pdfStamper.setEncryption(
                "userpass".getBytes(),
                "ownerpass".getBytes(),
                0,
                PdfWriter.ENCRYPTION_AES_256
        );

        pdfStamper.close();
    }

    private static void deleteFile(String fileName) {
        File outputFile = new File(fileName);
        if(outputFile.delete())
        {
            System.out.println(outputFile.getName() + " deleted");
        }
        else
        {
            System.out.println("failed");
        }
    }

    private static void addTableHeader(PdfPTable table) {
        Stream.of("Attribute name", "Attribute value")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private static void addRows(PdfPTable table) {
        table.addCell("row 1, col 1");
        table.addCell("row 1, col 2");
        table.addCell("row 1, col 3");
    }

    private static PdfPCell addStyledCell(String text){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.CYAN);
        cell.setPhrase(new Phrase(text));
        return cell;
    }

    private static void populateTable(PdfPTable table, ClinicalTrialDto clinicalTrialDto) {
        table.addCell(addStyledCell("Id"));
        table.addCell(clinicalTrialDto.getId());
        table.addCell(addStyledCell("Time"));
        table.addCell(clinicalTrialDto.getTime().toString());
        table.addCell(addStyledCell("Introduction"));
        table.addCell(clinicalTrialDto.getIntroduction());
        table.addCell(addStyledCell("Trial type"));
        table.addCell(clinicalTrialDto.getClinicalTrialType().toString());
        table.addCell(addStyledCell("Patient"));
        table.addCell(clinicalTrialDto.getPatient());
        table.addCell(addStyledCell("Doctor"));
        table.addCell(clinicalTrialDto.getDoctor());
        table.addCell(addStyledCell("Relevant parameters"));
        table.addCell(clinicalTrialDto.getRelevantParameters());
        table.addCell(addStyledCell("Conclusion"));
        table.addCell(clinicalTrialDto.getConclusion());
    }

    private static void addCustomRows(PdfPTable table)
            throws URISyntaxException, BadElementException, IOException {
        Image img = Image.getInstance(IMAGE_PATH);
        img.scalePercent(10);

        PdfPCell imageCell = new PdfPCell(img);
        table.addCell(imageCell);

        PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(horizontalAlignCell);

        PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(verticalAlignCell);
    }

}
