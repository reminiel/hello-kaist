import java.io.File;

public class Datum{
    public String content;
    public File image;

    public Datum(String content){
        this.content = content;
        this.image = null;
    }

    public Datum(String content, File image){
        this.content = content;
        this.image = image;
    }
}