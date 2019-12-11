import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

/*
Need translate key set to environment variable
add env GOOGLE_APPLICATION_CREDENTIALS to HelloatKaist-e0403dc05d64.json
 */

public class Translator {
    public static String googleTranslate(String base){
        Translation t = null;
        try {
            Translate translate = TranslateOptions.getDefaultInstance().getService();
            t = translate.translate(base, TranslateOption.sourceLanguage("ko"), TranslateOption.targetLanguage("en"));
        }
        catch(Exception e){
            System.out.println(e);
        }
        if(t == null) return base;
        return t.getTranslatedText();
    }
}
