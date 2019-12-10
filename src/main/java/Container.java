import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        tags = new HashMap<String, String>();
    }

    public Container addTags(String key, String value){
//        System.out.println("Key " + key + " set to " + value);
        tags.put(key, value);
        return this;
    }
    public Container setTags(){
        throw new NotImplementedException();
//        return this;
    }
    public Container addArticle(Article article){
        articles.add(article);
        return this;
    }
    public Container setArticles(){
        throw new NotImplementedException();
//        return this;
    }

    public HashMap<String, String> getTags() {
        return tags;
    }
    public ArrayList<Article> getArticles(){
        return articles;
    }



}
