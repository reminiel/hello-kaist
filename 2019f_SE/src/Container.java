import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Container {
    private int dataType;
    private HashMap<String, String> tags;
    private ArrayList<Article> articles;


    public Container(int dataType){
        this.dataType = dataType;
        articles = new ArrayList<Article>();
    }

    public Container addTags(String key, String value){
        tags.put(key, value);
        return this;
    }
    /*
    public Container setTags(){
        return this;
    }
    */
    public Container addArticle(Article article){
        articles.add(article);
        return this;
    }
    /*
    public Container setArticles(){
        throw new NotImplementedException();
//        return this;
    }
    */

    public HashMap<String, String> getTags() {
        return tags;
    }
    public ArrayList<Article> getArticles(){
        return articles;
    }

    public class Article{
        private String title;
        private String author;
        private DateFormat date;
        private ArrayList<Datum> contents;
        private ArrayList<Datum> comments;

        public Article(String title){
            this.title = title;
            contents = new ArrayList<>();
            comments = new ArrayList<>();
        }


        public Article setTitle(String title){
            this.title = title;
            return this;
        }
        public Article setAuthor(String author){
            this.author = author;
            return this;
        }
        public Article setDate(DateFormat date){
            this.date = date;
            return this;
        }
        public Article addContents(String content, File image){
            Datum datum = new Datum(content, image);
            contents.add(datum);
            return this;
        }
        public Article addComments(String comment, File image){
            Datum datum = new Datum(comment, image);
            comments.add(datum);
            return this;
        }

        public String getTitle(){
            return title;
        }
        public String getAuthor(){
            return author;
        }
        public DateFormat getDate(){
            return date;
        }
        public Datum getContents(int index) {
            if(contents.size() <= index) return null;
            return contents.get(index);
        }
        public Datum getComments(int index){
            if(comments.size() <= index) return null;
            return comments.get(index);
        }

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
    }

}
