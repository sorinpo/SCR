// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package sorinpo.scr.edu.model;

import sorinpo.scr.edu.model.ActivityData;
import sorinpo.scr.edu.model.Participation;

privileged aspect Participation_Roo_JavaBean {
    
    public Long Participation.getPupilId() {
        return this.pupilId;
    }
    
    public void Participation.setPupilId(Long pupilId) {
        this.pupilId = pupilId;
    }
    
    public int Participation.getYear() {
        return this.year;
    }
    
    public void Participation.setYear(int year) {
        this.year = year;
    }
    
    public ActivityData Participation.getSchool() {
        return this.school;
    }
    
    public void Participation.setSchool(ActivityData school) {
        this.school = school;
    }
    
    public ActivityData Participation.getFreeTime() {
        return this.freeTime;
    }
    
    public void Participation.setFreeTime(ActivityData freeTime) {
        this.freeTime = freeTime;
    }
    
    public ActivityData Participation.getExtraSchool() {
        return this.extraSchool;
    }
    
    public void Participation.setExtraSchool(ActivityData extraSchool) {
        this.extraSchool = extraSchool;
    }
    
    public ActivityData Participation.getGroupCounseling() {
        return this.groupCounseling;
    }
    
    public void Participation.setGroupCounseling(ActivityData groupCounseling) {
        this.groupCounseling = groupCounseling;
    }
    
    public ActivityData Participation.getIndividualCounseling() {
        return this.individualCounseling;
    }
    
    public void Participation.setIndividualCounseling(ActivityData individualCounseling) {
        this.individualCounseling = individualCounseling;
    }
    
    public ActivityData Participation.getParentalCommunication() {
        return this.parentalCommunication;
    }
    
    public void Participation.setParentalCommunication(ActivityData parentalCommunication) {
        this.parentalCommunication = parentalCommunication;
    }
    
    public ActivityData Participation.getLocalMeetings() {
        return this.localMeetings;
    }
    
    public void Participation.setLocalMeetings(ActivityData localMeetings) {
        this.localMeetings = localMeetings;
    }
    
}
