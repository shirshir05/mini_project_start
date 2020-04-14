package Busnies_Servic.Business_Layer.Trace;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Observable;


public class PersonalPage extends Observable {

    protected String name;
    protected HashSet<String> permissionToEdit;
    protected String pageOwner;
    protected HashMap<String,Object> pageData;


    /**
     * constructor
     * @param subject_name
     */
    public PersonalPage(String subject_name){
        name=subject_name;
        permissionToEdit = new HashSet<>();
        pageData = new HashMap<>();
    }


    //**********************************************permission To Edit ************************************************************//
    public void addPermissionToEdit(String user){
        permissionToEdit.add(user);
    }

    public void addPageOwner(String user){
        pageOwner = user;
        permissionToEdit.add(user);
    }

    public boolean chackperrmissiontoedit(String user){
        return permissionToEdit.contains(user);
    }

    public boolean removePerrmissionToEdit(String user){
        boolean ans = false;
        if(!user.equals(pageOwner)) {
            permissionToEdit.remove(user);
            ans = true;
        }
        return ans;
    }

    //**********************************************get & set ************************************************************//

    public boolean addToPageData(String dataHedline , Object data){
        boolean ans = false;
        if(!pageData.containsKey(dataHedline)){
            pageData.put(dataHedline,data);
            ans = true;
        }
        return ans;
    }

    public Object getPageData(String dataHedline){
        if(pageData.containsKey(dataHedline)){
            return pageData.get(dataHedline);
        }
        return null;
    }

    public Object editPageData(String dataHedline,Object newData){
        boolean ans = false;
        if(pageData.containsKey(dataHedline)){
            pageData.replace(dataHedline,newData);
            ans = true;
        }
        return ans;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        if(name != null && name.length() != 0){

            this.name = name;
        }
    }


}
