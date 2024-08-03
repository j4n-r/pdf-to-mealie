package com.j4nr;

public class App {
    public static void main(String[] args) {
        PDFExtractor pdfExtractor = new PDFExtractor("cookBook.pdf", "");
        pdfExtractor.getImage(200);
        System.out.println(pdfExtractor.getText(200));
    }
}
