/*Author: hmnhn
 * CS350 Team 2*/

package deliveryAssistantService;

import java.io.*;
import java.util.*;
/*import google cloud client library*/
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import java.io.IOException;


public class Menu {

    //private ArrayList<Restaurant> resList = new ArrayList<Restaurant>();
    private int menuID;
    private int resID;
    private int menuPrice;
    private String menuName;
    private int qty;
    private String outputAudio;
    private String orderMsg;
    private byte menuImg;
    private int selectedResID;
    private static ArrayList<Menu> menuList = new ArrayList<Menu>();

    public Menu(int menuID, int resID, String menuName, int menuPrice) {
        this.menuID = menuID;
        this.resID = resID;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    public static void main(String[] args) throws Exception {
        String text1 = "치킨 하나 주세요";
        synthesizeMsg(text1);
        //PlayAudio();
        loadMenu();
    }

    public int getMenuID() {
        return menuID;
    }

    private int getMenuPrice()  {
        return menuPrice;
    }

    public String  getMenuName() {
        return menuName;
    }

    public int getQty() {
        return qty;
    }

    public String getOrderMsg() {
        return orderMsg;
    }

    public Byte getMenuImg() {
        return menuImg;
    }

    public Menu setMenuID(int mid) {
        this.menuID = mid;
        return this;
    }

    public Menu setMenuPrice(int price) {
        this.menuPrice = price;
        return this;
    }

    public Menu setMenuName(String name) {
        this.menuName = name;
        return this;
    }

    //BUTTON OR CHECKLIST UI ADD QTY
    public Menu setQty(int q) {
        //int newQ = 0;
        this.qty = q;
        return this;
    }

    public static void PlayAudio() {


    }

    public static ArrayList<Menu> loadMenu() {
        menuList.add(new Menu(11, 1, "Original Fried Chicken", 6000));
        menuList.add(new Menu(12, 1, "Honey Fried Chicken", 6000));
        menuList.add(new Menu(13, 1, "Half Half Fried Chicken", 6000));
        menuList.add(new Menu(21, 2, "Bulgogi Pizza", 9000));
        menuList.add(new Menu(22, 2, "Italian Cheese Pizza", 9000));
        menuList.add(new Menu(23, 2, "Peperoni Pizza", 9000));

        System.out.println(menuList);
        return menuList;
    }

    public Menu setOrderMsg(String msg) {
        msg = ("Delivery order for "+this.getMenuName()+
                " "+this.getQty()+" please");
        this.orderMsg = msg;
        return this;
    }

    public void setMenuImg(Byte img) {
        this.menuImg = img;
    }

    //when user select on the menu
    private static void selectMenu(){
        //this is the resID use to get the menu, foreign key in menu db
        //int rid = deliveryAssistantService.Restaurant.getResID();
        //System.out.println(rid);
    }

    public String toString(){
        return ("\nMenu Name:"+this.getMenuName()+
                "\nPrice:"+this.getMenuPrice()+
                "\nQuantity:"+this.getQty());
    }

    //when user clicks on the sound translation button
    public static void synthesizeMsg(String orderMsg) throws IOException {
        String text = orderMsg;
        //TESTING
        //String text = "치킨 하나 주세요";
        //output file
        String outputAudioFilePath = "C:/Users/User/IdeaProjects/hello-kaist/output.mp3";

        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            //set the text input to be synthesized
            SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

            //build the voice request; languageCode = "ko-KR"
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder().setLanguageCode("ko-KR")
                    .setSsmlGender(SsmlVoiceGender.FEMALE)
                    .build();
            //select the type of audio file you want returned
            AudioConfig audioConfig = AudioConfig.newBuilder().setAudioEncoding((AudioEncoding.MP3))
                    .build();

            //perform the tts request
            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            //get the audio contents from the response
            ByteString audioContents = response.getAudioContent();

            //write the response to output file
            try (OutputStream out = new FileOutputStream(outputAudioFilePath)) {
                out.write(audioContents.toByteArray());
                System.out.println("Audio content written to file \"output.mp3\"");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
