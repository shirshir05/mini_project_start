package Busnies_Servic.Service_Layer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SearchLogger {
    protected String dataPath ="./lib/spellingDict.txt";

    /**
     * Search data according word
     * @param keyWord
     * @return
     */
    public String findData(String keyWord){
        DataManagement.getCurrent().addSearch(keyWord);
        //TODO - use spell correction instead of reading the dictionary file
        try
        {
            File file=new File(dataPath);
            FileReader fr=new FileReader(file);
            BufferedReader br=new BufferedReader(fr);
            StringBuffer sb=new StringBuffer();
            String line;
            while((line=br.readLine())!=null)
            {
                if (line.contains(keyWord)){
                    sb.append(line);
                    sb.append("\n");
                }
            }
            fr.close();
            return sb.toString();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Show the history of Search
     * @return
     */
    public String showSearchHistory(){
        StringBuffer sb=new StringBuffer();
        for (String s : DataManagement.getCurrent().getSearch()){
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }




}
