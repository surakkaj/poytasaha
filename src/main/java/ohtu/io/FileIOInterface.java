package ohtu.io;

public interface FileIOInterface {
    String readFile(String fileName);
    void write(String fileName, String text);    
}
