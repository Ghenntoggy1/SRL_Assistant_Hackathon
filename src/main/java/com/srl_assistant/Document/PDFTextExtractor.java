package com.srl_assistant.Document;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class PDFTextExtractor {

    public String extractTextFromUrl(String fileUrl) throws Exception {
        System.out.println("Extracting text from PDF file: " + fileUrl);
        PDDocument document = null;

        try {
            byte[] pdfBytes = downloadFileAsBytes(fileUrl);

            document = Loader.loadPDF(pdfBytes);

            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);

        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    private byte[] downloadFileAsBytes(String fileUrl) throws Exception {
        HttpURLConnection connection = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            URL url = new URL(fileUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() != 200) {
                throw new Exception("Failed to fetch file from URL: " + fileUrl);
            }

            try (InputStream inputStream = connection.getInputStream()) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            return outputStream.toByteArray();

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
