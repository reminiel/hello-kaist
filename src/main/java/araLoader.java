import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class araLoader {

    private String cookie1, cookie2;
    private boolean loggedIn;
    private String tempDir;

    public araLoader(){
        loggedIn = false;
        tempDir = "C:\\temp\\";
        }
    public void logout(){
        loggedIn = false;
        try {
            Jsoup.connect("https://ara.kaist.ac.kr/account/logout/").cookie("sessionid", cookie1).cookie("arara_checksum", cookie2).method(Connection.Method.GET).execute();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    public boolean login(String id, String pw){
        int trial = 5;
        if(loggedIn == true) return true;
        String url, origin, value;
        Connection connection;
        Connection.Response response;
        Document doc = null;
        origin = "https://ara.kaist.ac.kr";
        url = "https://ara.kaist.ac.kr/account/login/";
        for(int i=0;i<trial;i++){
            try {
                connection = Jsoup.connect(url).data("username", id).data("password", pw)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                        .header("Origin", origin).header("Referer", origin + '/').method(Connection.Method.POST).timeout(10000);
                response = connection.execute();
                cookie1 = response.cookie("sessionid");
                cookie2 = response.cookie("arara_checksum");
                if(cookie1 == null) return false;
                if(cookie2 == null) return false;
                loggedIn = true;
                return true;
            }
            catch(IOException e){
                System.out.println(e + "\nLogin failed");
            }
        }
        return false;
    }

    private Article commentSetter(Element element){
        Article article = new Article(null);
        try{
        article.setDate(new SimpleDateFormat("yyyy/MM/dd h:mm aa", Locale.US).parse(element.select("p.date").text().replaceAll("오전", "AM").replaceAll("오후", "PM")));
        }
        catch (ParseException e){
            System.out.println(e + "Date Parse failed");
        }
        article.setAuthor(element.select("p.author").select("span.eng").text());
        for(String s:element.select("div.replyArticle").first().html().replaceAll("<br>", "").split("\n")){
            article.addContents(Translator.googleTranslate(s), null);
        }
        for(Element e:element.select("div.replyArticle").select("ul.replyBox")){
            article.addComments(commentSetter(e));
        }
        return article;
    }

    public Container fetchArticle(int articleno){
        int trial = 5;
        if(loggedIn == false) return null;
        String url, origin, value;
        Connection connection;
        Connection.Response response;
        Document doc = null;
        origin = "https://ara.kaist.ac.kr";
        url = "https://ara.kaist.ac.kr/board/BuySell/" + articleno;
        for(int i=0;i<trial;i++) {
            try {
                connection = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                        .method(Connection.Method.GET).cookie("sessionid", cookie1).cookie("arara_checksum", cookie2).timeout(10000);
                response = connection.execute();
                doc = response.parse();
                Container container = new Container(3);
                Article article = new Article(Translator.googleTranslate(doc.select("div.articleTitle").select("h3").text()));
                article.setId(articleno);
                article.setAuthor(doc.select("p.author").select("span.eng").text());
                for(Element element:doc.select("table.attached").select("td").select("a:not(.lightbox)")){
                    URL u = new URL(origin + element.attr("href") + "/");
                    OutputStream outputStream = null;
                    InputStream inputStream = null;
                    try {
                        outputStream = new BufferedOutputStream(new FileOutputStream(tempDir + element.text()));
                        inputStream = u.openConnection().getInputStream();
                        byte[] buf = new byte[1024];
                        int byteRead;
                        while ((byteRead = inputStream.read(buf)) != -1) {
                            outputStream.write(buf, 0, byteRead);
                        }
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                    finally {
                        inputStream.close();
                        outputStream.close();
                    }
                    File f = new File(tempDir + element.text());
                    article.addContents(element.attr("title"), f);
                }
                for(Element element:doc.select("ul.replybox")){
                    article.addComments(commentSetter(element));
                }
                try {
                    article.setDate(new SimpleDateFormat("yyyy/MM/dd h:m a", Locale.US).parse(doc.select("p.date").text().replaceAll("오전", "AM").replaceAll("오후", "PM")));
                    for(String s:doc.select("div.article").html().replaceAll("<br>", "").split("\n")){
                        article.addContents(Translator.googleTranslate(s), null);
                    }
                }
                catch(ParseException e){
                    System.out.println(e + "\nParse failed");
                }
                article.addContents("Link : " + url, null);
                container.addArticle(article);
                return container;
            } catch (IOException e) {
                System.out.println(e + "\nFetch failed");
            }
        }
        return null;
    }
    public Container fetchPage(int pageno){
        int trial = 5;
        if(loggedIn == false) return null;
        String url, origin, value;
        Connection connection;
        Connection.Response response;
        Document doc = null;
        url = "https://ara.kaist.ac.kr/board/BuySell/?page_no=" + pageno;
        for(int i=0;i<trial;i++) {
            try {
                connection = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                        .method(Connection.Method.GET).cookie("sessionid", cookie1).cookie("arara_checksum", cookie2).timeout(10000);
                response = connection.execute();
                doc = response.parse();
                Container container = new Container(2);
                for (Element element : doc.select("table.articleList").select("tr")) {
                    if (element.hasAttr("rel")) {
                        Article article = new Article(Translator.googleTranslate(element.select("td.title").text()));
                        article.setAuthor(element.select("span.username").text());
                        try {
                            article.setDate(new SimpleDateFormat("yyyy/MM/dd").parse(element.select("td.date").text()));
                        } catch (ParseException e) {
                            System.out.println(e + "\nDate parse failed");
                        }
                        article.setId(Integer.parseInt(element.select("td.articleid").text()));
                        container.addArticle(article);
                    }
                }
                return container;
            } catch (IOException e) {
                System.out.println(e + "\nFetch failed");
            }
        }
        return null;
    }
}
