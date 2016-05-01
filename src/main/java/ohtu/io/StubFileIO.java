package ohtu.io;

import static ohtu.util.CharReplacer.*;

/**
 * Class for testing purposes. 
 * 
 */
public class StubFileIO implements FileIOInterface{
    
    private String filePath;
    private String content;
    
    
    public StubFileIO(){        
        this.filePath = "";
        this.content = "";
    }

    @Override
    public String readFile(String fileName) {
        if(fileName.equals(this.filePath)){
            return replaceBibtexFormatChars(content);
        }        
        return "";
    }

    @Override
    public void write(String fileName, String text) {
        this.filePath = fileName;
        this.content = replaceSpecialChars(text);
    }
    
}
