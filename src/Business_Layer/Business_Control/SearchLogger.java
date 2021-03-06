package Business_Layer.Business_Control;

import Business_Layer.Enum.ActionStatus;
import Service_Layer.Spelling;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class SearchLogger {
    protected String dataPath ="./lib/spellingDict.txt";

    /**
     * Search data according word
     * @param keyWord
     * @return
     */
    public ActionStatus findData(String keyWord){
        ActionStatus AC;
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
                line=line.toLowerCase();
                if (line.contains(keyWord) || line.contains(keyword2)){
                    sb.append(line);
                    sb.append("\n");
                }
            }
            fr.close();
            AC = new ActionStatus(true, sb.toString());
            return AC;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        AC = new ActionStatus(false, "");
        return AC;
    }


    /**
     * Show the history of Search
     * @return  String
     */
    public ActionStatus showSearchHistory(){
        StringBuilder sb=new StringBuilder();
        for (String s : DataManagement.getCurrent().getSearch()){
            sb.append(s);
            sb.append("\n");
        }
        return new ActionStatus(true, sb.toString());
    }


    /**
     * System administrator can watch logger
     * @param path
     * @return
     */
    public ActionStatus watchLogger(String path){
        File from = new File("logs");
        try {
            copyDir(from,path);
            return new ActionStatus(true,"The logger copy successfully");
        } catch (IOException e) {
            return new ActionStatus(false,"There was a problem copying files. Please select another folder.");
        }
    }

    /**
     * copy file from logger to ptah in commuter
     * @param src  -
     * @param PtahDest -
     * @throws IOException -
     */
    private void copyDir(File src,String PtahDest) throws IOException {
        int number_file = 1;
        if (src.isDirectory()) {
            File[] content = src.listFiles();
            if (content != null) {
                for (int i = 0; i < content.length; i++) {
                    File dest = new File(PtahDest + "\\" + number_file+".txt");
                    Files.copy(content[i].toPath().toAbsolutePath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    number_file++;
                }
            }
        }
    }
}


