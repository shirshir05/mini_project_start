package Business_Layer.Enum;

import DB_Layer.logger;

import java.io.*;
import java.util.Properties;
public  class Configurations {


    public static String getPropValues(String property){
        InputStream inputStream = null;
        String ans = "";
        try {
            Properties prop = new Properties();
            inputStream = new FileInputStream("resources/config");


            if (inputStream.available() != 0) {
                prop.load(inputStream);
                // get the property value and print it out
                ans = prop.getProperty(property);
            } else {
                //An error is thrown if the file does not exist
                logger.log("Not found resources/config");
            }




        } catch (Exception e) {
            //System.out.println("Exception: " + e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return ans;
    }

    public static void setPropValues(String property, int number){
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            inputStream = new FileInputStream("resources/config");



            if (inputStream.available() != 0) {
                prop.load(inputStream);
                // set the property value and print it out
                FileOutputStream out = new FileOutputStream("resources/config");

                prop.setProperty(property, String.valueOf(number));
                prop.setProperty(property, String.valueOf(number));
                if(property.equals("NumberOfComplaint")){
                    prop.setProperty("NumberOfGames", prop.getProperty("NumberOfGames"));
                }else{//NumberOfGames
                    prop.setProperty("NumberOfComplaint", prop.getProperty("NumberOfComplaint"));
                }
                prop.store(out, null);
                out.close();
            } else {
                //An error is thrown if the file does not exist
                logger.log("Not found resources/config");
            }




        } catch (Exception e) {
            //System.out.println("Exception: " + e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
    public static int getNumberOfGames() {
        //default value

        int result = 1;
        try {
            String ans = getPropValues("NumberOfGames");
            result = Integer.parseInt(ans);
        } catch (NumberFormatException e) {
            //do nothing, return default value
        }
        return result;
    }


    public static int getNumberOfComplaint() {
        //default value

        int result = 1;
        try {
            String ans = getPropValues("NumberOfComplaint");
            result = Integer.parseInt(ans);
        } catch (NumberFormatException e) {
            //do nothing, return default value
        }

        return result;
    }


}
