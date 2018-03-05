package containers;

import java.io.BufferedReader;

public class FileData {
    public String fileName;
    public BufferedReader fileDescriptor;

    public FileData(String fileName, BufferedReader fileDescriptor) {
        this.fileName = fileName;
        this.fileDescriptor = fileDescriptor;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public BufferedReader getFileDescriptor() {
        return fileDescriptor;
    }

    public void setFileDescriptor(BufferedReader fileDescriptor) {
        this.fileDescriptor = fileDescriptor;
    }

    @Override
    public String toString() {
        return fileName;
    }

}
