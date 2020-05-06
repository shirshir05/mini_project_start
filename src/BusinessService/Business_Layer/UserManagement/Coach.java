package BusinessService.Business_Layer.UserManagement;

import BusinessService.Business_Layer.Trace.CoachPersonalPage;

public class Coach /*extends Subscription*/ {

    protected CoachPersonalPage PersonalPage;
    protected String qualification;
    protected String roleInTeam;

    public Coach(String arg_user_name/*, String arg_password,String email*/) {
      //  super(arg_user_name, arg_password,email);
        PersonalPage = new CoachPersonalPage(arg_user_name);
      //  permissions.add_default_player_or_coach_permission();
    }

    //**********************************************get & set ************************************************************//
    /**
     * Get of Coach Qualification
     * @return
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * Placement of Coach Qualification
     * @param qualification
     */
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getRoleInTeam() {
        return roleInTeam;
    }

    /**
     * Placement of a role in the group
     * @param roleInTeam
     */
    public void setRoleInTeam(String roleInTeam) {
        this.roleInTeam = roleInTeam;
    }

    public CoachPersonalPage getPersonalPage() {
        return PersonalPage;
    }

    public void setPersonalPage(CoachPersonalPage personalPage) {
        PersonalPage = personalPage;
    }



    //**********************************************to string ************************************************************//


    /**
     * @return
     */
    @Override
    public String toString() {

        return "Coach: " + "\n" +
             //   "name: " + name + "\n" +
              //  "email: " + email + "\n" +
                "qualification: " + qualification + "\n" +
                "roleInTeam: " + roleInTeam;
    }
}