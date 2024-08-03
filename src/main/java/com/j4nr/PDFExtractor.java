package com.j4nr;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFExtractor {
    private String outputDir;
    private PDDocument document;
    private PDFTextStripper reader;

    public PDFExtractor(String pdfFilePath, String outputDir) {
        this.outputDir = outputDir;
        try {
            document = PDDocument.load(new File(pdfFilePath));
            reader = new PDFTextStripper();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getImage(int pageNumber) {
        try {
            // Extract embedded images from the specific page
            PDPage page = document.getPage(pageNumber); // Page numbers are zero-based
            PDResources pdResources = page.getResources();
            int imageCounter = 1;
            for (COSName cosName : pdResources.getXObjectNames()) {
                PDXObject xObject = pdResources.getXObject(cosName);
                if (xObject instanceof PDImageXObject) {
                    PDImageXObject image = (PDImageXObject) xObject;
                    String fileName = outputDir + "recipeImage" + imageCounter + ".png";
                    ImageIO.write(image.getImage(), "png", new File(fileName));
                    System.out.println("Embedded image " + imageCounter + " saved as " + fileName);
                    imageCounter++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getText(int page) {
        try {
            reader.setStartPage(100);
            reader.setEndPage(100);
            String text = reader.getText(document);
            System.out.println(text);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }
}
