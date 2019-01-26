package com.geekbrains.netty.example.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileMessage extends AbstractMessage {
    private String filename;
    private List<String> fileMessage;
    private byte[] data;

    public String getFilename() {
        return filename;
    }
    public List<String> getFileMessage() {
        return fileMessage;
    }

    public byte[] getData() {
        return data;
    }

    public FileMessage(Path path) throws IOException {
        filename = path.getFileName().toString();
        fileMessage = Files.readAllLines(path);
        data = Files.readAllBytes(path);
    }
}
