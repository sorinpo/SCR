// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package sorinpo.scr.edu.model;

import sorinpo.scr.edu.model.ActivityData;
import sorinpo.scr.edu.model.Config;

privileged aspect Config_Roo_JavaBean {
    
    public int Config.getActiveYear() {
        return this.activeYear;
    }
    
    public void Config.setActiveYear(int activeYear) {
        this.activeYear = activeYear;
    }
    
    public ActivityData Config.getActiveMonths() {
        return this.activeMonths;
    }
    
    public void Config.setActiveMonths(ActivityData activeMonths) {
        this.activeMonths = activeMonths;
    }
    
}
