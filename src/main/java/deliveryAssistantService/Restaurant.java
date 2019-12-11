/*Author: hmnhn
* CS350 Team 2*/

package deliveryAssistantService;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.FileReader;
import java.sql.*;
import java.util.*;
import java.util.List;

public class Restaurant {
    private static int resID;
    private String resName;
    private String resAddress;
    private String resDescription;
    private String resCon;
    private byte resImg;
    private static Statement stmt;
    private static ResultSet rs;
    private static ArrayList<String> resList = new ArrayList<String>();

    public Restaurant() {    }

    public static int getResID() {
        return resID;
    }

    public String getResName() {
        return resName;
    }

    public String getResAddress() {
        return resAddress;
    }

    public String getResDescription() {
        return resDescription;
    }

    public String getResCon() {
        return resCon;
    }

    public Restaurant setResID(int rid) {
        this.resID = rid;
        return this;
    }

    public Restaurant setResName(String rname) {
        this.resName = rname;
        return this;
    }

    public Restaurant setResAddress(String radd) {
        this.resAddress = radd;
        return this;
    }

    public Restaurant setResDescription(String rdes) {
        this.resDescription = rdes;
        return this;
    }
    public Restaurant setResCon(String rcon) {
        this.resCon = rcon;
        return this;
    }

    public byte[] setResImg(Byte rimg){
        this.resImg = rimg;
        return new byte[0];
    }

    public static ArrayList<String> loadRes() throws SQLException {
        /*JSONParser parser = new JSONParser();


        try {
            //show list of restaurant using json
            /*Object obj  = parser.parse(new FileReader("C:\\Users\\User\\IdeaProjects\\hello-kaist\\src\\restaurant.json"));
            JsonObject jsonObject = (JsonObject) obj;
            String name = (String) jsonObject.get("resName");
            System.out.println("Restaurant list: "+ name);


        } catch (Exception e) {
            System.err.println("exception");
            System.err.println(e.getMessage());
        }*/
        return resList;
    }

    private static void getRes() {
        Restaurant rest = new Restaurant();
        List<String> restaurants;

        try {
            restaurants = rest.loadRes();
            for (String restaurant : restaurants) {
                System.out.println("display res");
                displayRestaurant(restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayRestaurant(String restaurant) {
        /*System.out.println("Restaurant Name:" + restaurant.getResName());
        System.out.println("Address:" + restaurant.getResAddress());
        System.out.println("Contact number:" + restaurant.getResCon());*/
        //MENU BUTTON?
        //pass resID here
    }

    public static void main (String [] args) throws SQLException {

        loadRes();
    }
}