package com.devonfw.tools.ide.url.folderhandling;

import com.devonfw.tools.ide.url.folderhandling.abstractUrlClasses.AbstractUrlFile;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UrlChecksum extends AbstractUrlFile {

    final HttpClient client = HttpClient.newBuilder().build();

    private String checksum;

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    /**
     * The constructor.
     *
     * @param parent the {@link #getParent() parent folder}.
     * @param name   the {@link #getName() filename}.
     */
    public UrlChecksum(UrlVersion parent, String name) {
        super(parent, name);

    }

    /**
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
     * @param hashAlgorithm the hash algorithm (e.g sha256, sha1, md)
     * @return
     */
    private String doCreateChecksum(InputStream inputStream, String hashAlgorithm)  {

        try {
            MessageDigest md = MessageDigest.getInstance(hashAlgorithm);

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
            inputStream.close();

            byte[] digestBytes = md.digest();
            String checksum = toHexString(digestBytes);
            System.out.println(checksum);
            setChecksum(checksum);
            return checksum;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read input stream " + e);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such hash algorithm " + hashAlgorithm);
            return null;
        }
    }

    /**
     * @param bytes the byte array to convert in hex String
     * @return
     */
    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * @param hashAlgorithm the hash algorithm (e.g sha256, sha1, md)
     * @param downloadUrl the url of the download file
     */
    public void doChecksum(String hashAlgorithm, String downloadUrl) {
        setChecksum(doCreateChecksum(doGetResponseInputStream(downloadUrl), hashAlgorithm));
        doSave();
    }

    @Override
    protected void doLoad() {

        Path path = getPath();
        try {
            String cs = Files.readString(path);
            setChecksum(cs);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load file " + path, e);
        }
    }

    @Override
    protected void doSave() {
        Path path = getPath();
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            bw.write(getChecksum() + "\n");

        } catch (IOException e) {
            throw new IllegalStateException("Failed to save file " + path, e);
        }
    }
}
