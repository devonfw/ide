package com.devonfw.tools.ide.url.updater;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumGenerator {
    final HttpClient client = HttpClient.newBuilder().build();
    private String checksum;
    final String hashAlgorithm = "SHA-256";

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    /**
     * start the generator to create checksums of .urls files
     * @param files iterate over files to find .urls files
     */
    public void generateChecksums(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                generateChecksums(file.listFiles());
            } else {
                Path filePath = Path.of(file.getAbsolutePath());
                if (file.getName().endsWith(".urls")) {

                    try {
                        String downloadUrl = Files.readString(filePath).replace("\n", "");
                        setChecksum(doCreateChecksumFromInputStream(doGetResponseInputStream(downloadUrl)));
                    } catch (IOException e) {
                        throw new IllegalStateException("Failed to create checksum " + e);
                    }

                    try (BufferedWriter checksumFile = new BufferedWriter(new FileWriter(file + ".sha256"))) {
                        if (getChecksum().isEmpty()) {
                            throw new IOException("There is an error with calculated checksum!");
                        } else {
                            checksumFile.write(getChecksum());
                            System.out.println(hashAlgorithm + "-checksum for " + file.getPath() + " is " + getChecksum());
                        }
                    } catch (IOException e) {
                        throw new IllegalStateException("Failed to save file " + e);
                    }
                }
            }
        }

    }

    /**
     * get input stream of given url
     * @param url the url of the download file
     * @return the input stream of requested url
     */
    private InputStream doGetResponseInputStream(String url) {
        try {
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            return client.send(request1, HttpResponse.BodyHandlers.ofInputStream()).body();
        } catch (IOException | InterruptedException exception) {
            throw new IllegalStateException("Failed to retrieve response body from url: " + url, exception);
        } catch (IllegalArgumentException e) {
            System.out.println("Error while getting response body from url {}\", url, e");
            return null;
        }
    }

    /**
     * @param inputStream the input stream of requested url
     * @return
     */
    private String doCreateChecksumFromInputStream(InputStream inputStream)  {

        try {
            MessageDigest md = MessageDigest.getInstance(hashAlgorithm);

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
            inputStream.close();

            byte[] digestBytes = md.digest();
            return toHexString(digestBytes);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read input stream " + e);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such hash algorithm " + hashAlgorithm);
            return null;
        }
    }

    /**
     * @param bytes the byte array to convert in hex string
     * @return hex string
     */
    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}