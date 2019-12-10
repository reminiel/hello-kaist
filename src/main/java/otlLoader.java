import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;



public class otlLoader {

    private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36";

    public otlLoader(){
        fetch(search("CS350"));
        return;
    }

    private String Translate(String code){
        return code;
    }

    private String search(String code){
        String url, value;
        Connection connection;
        Document doc = null;
        url = "https://otl.kaist.ac.kr/review/result/?q=" + code;
        try{
            connection = Jsoup.connect(url);
            doc = connection.get();
            if(doc.select("div.col-xs-24.col-sm-12.col-md-11.col-lg-10").size() != 1) return null;
            value =  doc.select("div.col-xs-24.col-sm-12.col-md-11.col-lg-10").select("input").attr("value");
            return value;
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Container fetch(String code){
        Container container = new Container(1);
        String url, target;
        int flag=0;
        url = "https://otl.kaist.ac.kr/review/result/course/"+ code +"/-1";
        Document doc = null;
        Connection connection;
        try{
            connection = Jsoup.connect(url + "/json/1")
                    .header("origin", url)
                    .header("referer", url).ignoreContentType(true);
            doc = connection.get();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node;
            try{
                target = doc.select("body").text();
                target = target.substring(1, target.length()-1);
                node = mapper.readTree(target.replaceAll("(\\\\\\\\)", "\\\\").replaceAll("(\\\\\")", "\""));
                for(JsonNode n:node.get("results")){
                    if(flag == 0) {
                        flag = 1;
                        container.addTags("courseCode", n.get("course_code").asText());
                        container.addTags("courseTitle", n.get("lecture_title").asText());
                    }
                    Article article = new Article(null);
                    article.setAuthor(n.get("writer").asText());
                    for(String s:n.get("comment").asText().split("\\\\r\\\\n")){
                        article.addContents(Translate("s"),null);
                    }
                }
                return container;
            }
            catch (IOException e){
                System.out.println(e.getMessage());
                return null;
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
