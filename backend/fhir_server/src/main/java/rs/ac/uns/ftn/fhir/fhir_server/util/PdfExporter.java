package rs.ac.uns.ftn.fhir.fhir_server.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.stream.Stream;

public class PdfExporter {

    private static final String FILE_PATH = "resources/pdf/iTextHelloWorld.pdf";
    private static final String IMAGE_PATH = "resources/image/CT_small.jpg";

    public static void export() throws IOException, DocumentException, URISyntaxException {
        Document document = new Document();

        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(FILE_PATH));

        document.open();

        document.addAuthor("Luka");
        document.addCreationDate();
        document.addTitle("New pdf");
        document.addHeader("header", "header desc");

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setPaddingTop(50f);
        document.add(paragraph);
        document.add(paragraph);

        Image img = Image.getInstance(IMAGE_PATH);

        PdfPTable table = new PdfPTable(3);
        table.setSpacingBefore(20f);
        table.setSpacingAfter(20f);
        addTableHeader(table);
        addRows(table);
//        addCustomRows(table);

        document.add(table);

        document.add(img);
        document.close();


    }

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

    public static void deleteFile() {
        File outputFile = new File(FILE_PATH);
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
        Stream.of("column header 1", "column header 2", "column header 3")
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
