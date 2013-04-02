// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package sorinpo.scr.edu.model;

import sorinpo.scr.edu.model.Participation;
import sorinpo.scr.edu.model.Pupil;

privileged aspect Pupil_Roo_JavaBean {
    
    public String Pupil.getName() {
        return this.name;
    }
    
    public void Pupil.setName(String name) {
        this.name = name;
    }
    
    public ParentState Pupil.getParentState() {
        return this.parentState;
    }
    
    public void Pupil.setParentState(ParentState parentState) {
        this.parentState = parentState;
    }
    
    public String Pupil.getLeftToCountry() {
        return this.leftToCountry;
    }
    
    public void Pupil.setLeftToCountry(String leftToCountry) {
        this.leftToCountry = leftToCountry;
    }
    
    public String Pupil.getComment() {
        return this.comment;
    }
    
    public void Pupil.setComment(String comment) {
        this.comment = comment;
    }
    
    public String Pupil.getCounty() {
        return this.county;
    }
    
    public void Pupil.setCounty(String county) {
        this.county = county;
    }
    
    public Participation Pupil.getSchool() {
        return this.school;
    }
    
    public void Pupil.setSchool(Participation school) {
        this.school = school;
    }
    
    public Participation Pupil.getExtra() {
        return this.extra;
    }
    
    public void Pupil.setExtra(Participation extra) {
        this.extra = extra;
    }
    
    public Participation Pupil.getGroup() {
        return this.group;
    }
    
    public void Pupil.setGroup(Participation group) {
        this.group = group;
    }
    
    public Participation Pupil.getIndividual() {
        return this.individual;
    }
    
    public void Pupil.setIndividual(Participation individual) {
        this.individual = individual;
    }
    
    public Participation Pupil.getOnline() {
        return this.online;
    }
    
    public void Pupil.setOnline(Participation online) {
        this.online = online;
    }
    
    public Participation Pupil.getDiscussion() {
        return this.discussion;
    }
    
    public void Pupil.setDiscussion(Participation discussion) {
        this.discussion = discussion;
    }
    
    public boolean Pupil.isDeleteRequested() {
        return this.deleteRequested;
    }
    
    public void Pupil.setDeleteRequested(boolean deleteRequested) {
        this.deleteRequested = deleteRequested;
    }
    
    public boolean Pupil.isUnlockRequested() {
        return this.unlockRequested;
    }
    
    public void Pupil.setUnlockRequested(boolean unlockRequested) {
        this.unlockRequested = unlockRequested;
    }
    
    public boolean Pupil.isLocked() {
        return this.locked;
    }
    
    public void Pupil.setLocked(boolean locked) {
        this.locked = locked;
    }
    
}
