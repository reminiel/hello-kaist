/*Author: hmnhn
* CS350 Team 2*/

package deliveryAssistantService;
import java.sql.*;
import java.util.*;

public class Restaurant {
    private static int resID;
    private String resName;
    private String resAddress;
    private String resDescription;
    private String resCon;
    private byte resImg;
    private static ArrayList<Restaurant> resList = new ArrayList<Restaurant>();

    public Restaurant(int resId, String resName, String resAdd, String resD, String resC) {
        this.resID = resId;
        this.resName = resName;
        this.resAddress = resAdd;
        this.resDescription = resD;
        this.resCon = resC;
    }

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

    public static ArrayList<Restaurant> loadRes() {
        resList.add(new Restaurant(1, "Nene Chicken", "393-3 Gung-dong, Yuseong-gu, Daejeon", "Selling variety of korean fried chicken", "042-543-4479"));
        resList.add(new Restaurant(2, "Pizza Maru", "108-5 Eoeun-dong, Yuseong-gu, Daejeon", "Selling delicious pizza", "042-822-1085"));
        resList.add(new Restaurant(3, "Gulsesang", "113-16 Eoeun-dong, Yuseong-gu, Daejeon", "Korean restaurant", "042-863-8282"));

        System.out.println(resList);
        return resList;
    }

    public String toString(){
        return ("\nRestaurant Name:"+this.getResName()+
                "\nDescription:"+this.getResDescription()+
                "\nContact:"+this.getResCon());
    }

    private static void displayRestaurant(String restaurant) {

    }

    public static void main (String [] args) throws SQLException {

        loadRes();
    }
}