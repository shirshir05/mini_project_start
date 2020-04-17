package BusniesServic.Business_Layer.Trace;

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
        addPageOwner(subject_name);
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

    public boolean removePermissionToEdit(String user){
        boolean ans = false;
        if(!user.equals(pageOwner)) {
            permissionToEdit.remove(user);
            ans = true;
        }
        return ans;
    }

    //**********************************************get & set ************************************************************//

    public boolean addToPageData(String dataHeadline , Object data){
        boolean ans = false;
        if(!pageData.containsKey(dataHeadline)){
            pageData.put(dataHeadline,data);
            ans = true;
        }
        return ans;
    }

    public Object getPageData(String dataHeadline){
        if(pageData.containsKey(dataHeadline)){
            return pageData.get(dataHeadline);
        }
        return null;
    }

    public Object editPageData(String dataHeadline,Object newData){
        boolean ans = false;
        if(pageData.containsKey(dataHeadline)){
            pageData.replace(dataHeadline,newData);
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
