package BusinessService.Service_Layer;

import Presentation_Layer.Spelling;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class SearchLogger {
    protected String dataPath ="./lib/spellingDict.txt";

    /**
     * Search data according word
     * @param keyWord
     * @return
     */
    public String findData(String keyWord){
        DataManagement.getCurrent().addSearch(keyWord);
        String keyword2 = Spelling.getCorrectWord(keyWord);
        try
        {
            String dataPath = "./lib/spellingDict.txt";
            File file=new File(dataPath);
            FileReader fr=new FileReader(file);
            BufferedReader br=new BufferedReader(fr);
            StringBuilder sb=new StringBuilder();
            String line;
            while((line=br.readLine())!=null)
            {
                if (line.contains(keyWord) || line.contains(keyword2)){
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
     * @return  String
     */
    public String showSearchHistory(){
        StringBuilder sb=new StringBuilder();
        for (String s : DataManagement.getCurrent().getSearch()){
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }




}