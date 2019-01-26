package com.geekbrains.netty.example.common;

public class FileRequest extends AbstractMessage {
    private String filename;
    private String fileMessage;
    public String getFileMessage() {return fileMessage;}
    public FileRequest(String fileMessage) {this.fileMessage = fileMessage;}

    public String getFilename() {
        return filename;
    }

//    public FileRequest(String filename) { this.filename = filename; }
}

