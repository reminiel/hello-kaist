package deliveryAssistantService;

import java.sql.Statement;

public class Container {


    public static class restaurant {
        private int resID;
        private String resName;
        private String resAddress;
        private String resDescription;
        byte resImg;


        public restaurant() {

        }

        public int getResID() {
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

        public restaurant setResID(int rid) {
            this.resID = rid;
            return this;
        }

        public restaurant setResName(String rname) {
            this.resName = rname;
            return this;
        }

        public restaurant setResAddress(String radd) {
            this.resAddress = radd;
            return this;
        }

        public restaurant setResDescription(String rdes) {
            this.resDescription = rdes;
            return this;
        }
    }
}