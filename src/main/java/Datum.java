import java.io.File;

public class Datum{
    public String content;
    public Image image;

    public Datum(String content){
        this.content = content;
        this.image = null;
    }

    public Datum(String content, Image image){
        this.content = content;
        this.image = image;
    }
}