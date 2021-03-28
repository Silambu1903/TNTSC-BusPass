package com.tnstc.buspass.Model;

import com.tnstc.buspass.Database.Entity.PassEntity;

public class PassEntry {
    PassEntity passEntryList;
    boolean isSelected;

    public PassEntry(PassEntity passEntryList) {
        this.passEntryList = passEntryList;
    }

    public PassEntity getPassEntryList() {
        return passEntryList;
    }


    public boolean isSelected() {
        return isSelected;
    }

}
