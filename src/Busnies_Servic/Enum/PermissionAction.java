package Busnies_Servic.Enum;

public enum PermissionAction {
    /*player / coach permissions*/
    personal_page, //edited

    /*fan permissions*/
    write_complaint, Search_History,

    /*team_owner permissions - can be team_manager to */
    Edit_team, // allows edit all team assets, represent team asset by Team Name.
    Appointment_of_team_owner, Remove_Appointment_of_team_owner,
    Appointment_of_team_manager, Remove_Appointment_of_team_manager,
    Appointment_of_player, Remove_Appointment_of_player,
    Team_financial,//edited
    Close_team,

    /*system admin permissions*/
    Close_team_perpetually,
    Removing_Subscriptions,
    Respond_to_complaints, watch_log,
    Recommendation_system,

    /*union permissions*/
    define_league, define_season,appointment_referee, remove_referee,
    setting_referee_in_league, calculation_policy, setting_games,
    setting_games_policy, change_budget_regulations,

    /*referee permissions*/
    update_event

}


//update others personal page: add in users class list of permitted users (not including team owner/manager)
//add to team owner :appointee ID, who gave his permissions, if union set global unionID