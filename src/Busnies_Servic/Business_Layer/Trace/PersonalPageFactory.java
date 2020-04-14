package Busnies_Servic.Business_Layer.Trace;

import DB_Layer.logger;

public class PersonalPageFactory{

    public PersonalPage Create(String subject_name, String type, String page_owner_name){
        PersonalPage p = null;
        if (type.equals("PlayerPersonalPage")) {
            p = new PlayerPersonalPage(subject_name);
            p.addPageOwner(page_owner_name);
        }
        else if(type.equals("TeamPersonalPage")) {
            p = new TeamPersonalPage(subject_name);
            p.addPageOwner(page_owner_name);
        }
        else if(type.equals("CoachPersonalPage")) {
            p = new CoachPersonalPage(subject_name);
            p.addPageOwner(page_owner_name);
        }
        logger.log("PersonalPageFactory: new personal page was created: " +(p!=null) + " ,page name: "+ subject_name + " ,type: " + type + " ,owner: "+ page_owner_name);
        return p;
    }
}


