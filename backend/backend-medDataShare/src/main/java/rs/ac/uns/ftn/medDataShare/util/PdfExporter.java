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

    public static byte[] clinicalTrialExportPdf(ClinicalTrialDto clinicalTrialDto, Binary binary){
        String fileName = "resources/pdf/temp.pdf";
        try {
            export(fileName, clinicalTrialDto, binary);
            byte[] bytes = getBytes(fileName);
            deleteFile(fileName);
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] getBytes(String fileName) {
        File outputFile = new File(fileName);
        try {
            byte[] ui = Files.readAllBytes(outputFile.toPath());
            return ui;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void export(String fileName, ClinicalTrialDto clinicalTrialDto, Binary binary) throws IOException, DocumentException, URISyntaxException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
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
}
