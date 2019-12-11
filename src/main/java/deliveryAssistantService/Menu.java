/*Author: hmnhn
 * CS350 Team 2*/

package deliveryAssistantService;

import java.io.*;
import java.sql.*;
import java.util.*;
/*import google cloud client library*/
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.*;

public class Menu {

    //private ArrayList<Restaurant> resList = new ArrayList<Restaurant>();
    private int menuID;
    private int menuPrice;
    private String menuName;
    private int qty;
    private String outputAudio;
    private String orderMsg;
    private byte menuImg;
    private int selectedResID;
    private static Statement stmt;
    /*BELOW IS TO PLAY THE MP3 FILE*/
    Long currentFrame; //store current position
    Clip clip;
    String status; //current status of the clip
    AudioInputStream audioInputStream;
    String filepath = "C:\\Users\\User\\IdeaProjects\\hello-kaist\\output.mp3";

    public Menu() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void main(String[] args) throws Exception {
        String text1 = "치킨 하나 주세요";
        synthesizeMsg(text1);
        //PlayAudio();
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

    public String getOutputAudio() {

        return outputAudio;
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
        this.qty = q;
        return this;
    }

    public static void PlayAudio() {
        try
        {
            Menu audioPlayer = new Menu();

            audioPlayer.play();
            Scanner sc = new Scanner(System.in);

            while (true)
            {
                System.out.println("1. pause");
                System.out.println("2. resume");
                System.out.println("3. restart");
                System.out.println("4. stop");
                System.out.println("5. Jump to specific time");
                int c = sc.nextInt();
                audioPlayer.gotoChoice(c);
                if (c == 4)
                    break;
            }
            sc.close();
        }

        catch (Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }

    }

    public Menu setOrderMsg(String msg) {
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

    // Work as the user enters his choice

    private void gotoChoice(int c)
            throws IOException, LineUnavailableException, UnsupportedAudioFileException
    {
        switch (c)
        {
            case 1:
                pause();
                break;
            case 2:
                resumeAudio();
                break;
            case 3:
                restart();
                break;
            case 4:
                stop();
                break;
            case 5:
                System.out.println("Enter time (" + 0 + ", " + clip.getMicrosecondLength() + ")");
                Scanner sc = new Scanner(System.in);
                long c1 = sc.nextLong();
                jump(c1);
                break;

        }

    }

    // Method to play the audio
    public void play()
    {
        //start the clip
        clip.start();
        status = "play";
    }

    // Method to pause the audio
    public void pause()
    {
        if (status.equals("paused"))
        {
            System.out.println("audio is already paused");
            return;
        }
        this.currentFrame =
                this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    // Method to resume the audio
    public void resumeAudio() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        if (status.equals("play"))
        {
            System.out.println("Audio is already "+"being played");
            return;
        }
        clip.close();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

    // Method to restart the audio
    public void restart() throws IOException, LineUnavailableException,
            UnsupportedAudioFileException
    {
        clip.stop();
        clip.close();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

    // Method to stop the audio
    public void stop() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }

    // Method to jump over a specific part
    public void jump(long c) throws UnsupportedAudioFileException, IOException,
            LineUnavailableException
    {
        if (c > 0 && c < clip.getMicrosecondLength())
        {
            clip.stop();
            clip.close();
            currentFrame = c;
            clip.setMicrosecondPosition(c);
            this.play();
        }
    }
}
