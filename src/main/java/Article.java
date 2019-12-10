import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Article{
    private String title;
    private String author;
    private Date date;
    private int id;
    private ArrayList<Datum> contents;
    private ArrayList<Article> comments;

    public Article(String title){
        this.title = title;
        contents = new ArrayList<Datum>();
        comments = new ArrayList<Article>();
    }


    public Article setTitle(String title){
        this.title = title;
        return this;
    }
    public Article setId(int id){
        this.id = id;
        return this;
    }
    public Article setAuthor(String author){
        this.author = author;
        return this;
    }
    public Article setDate(Date date){
        this.date = date;
        return this;
    }
    public Article addContents(String content, File image){
        Datum datum = new Datum(content, image);
        contents.add(datum);
        return this;
    }
    public Article addComments(Article article){
        comments.add(article);
        return this;
    }

    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public Date getDate(){
        return date;
    }
    public int getId(){
        return id;
    }
    public int contentsSize(){ return contents.size();}
    public int commentsSize(){ return comments.size();}
    public Datum getContents(int index) {
        if(contents.size() <= index) return null;
        return contents.get(index);
    }
    public Article getComments(int index){
        if(comments.size() <= index) return null;
        return comments.get(index);
    }


}