package com.example.sungsoyeon.couplediary;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by sungsoyeon on 2018-06-10.
 */

public class UserData extends RealmObject {
    @Required
    private String name;
    private String id;
    private String pw;
    private String checkpw;
    private String couplename;
    private boolean gender1;
    private boolean gender2;
    private boolean couplegender1;
    private boolean couplegender2;

    private String storytitle;
    private String storycontent;
    private boolean everyshare;
    private String couplestorytitle;
    private String couplestorycontent;
    private boolean coupleshare;
    private String personalstorytitle;
    private String personalstorycontent;
    private boolean personal;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPw() {
        return pw;
    }
    public void setPw(String pw) {
        this.pw = pw;
    }
    public String getCheckpw() {
        return checkpw;
    }
    public void setCheckpw(String checkpw) {
        this.checkpw = checkpw;
    }
    public String getCouplename() {
        return couplename;
    }
    public void setCouplename(String couplename) {
        this.couplename = couplename;
    }
    public boolean isGender1() {
        return gender1;
    }
    public void setGender1(boolean gender1) {
        this.gender1 = gender1;
    }
    public boolean isGender2() {
        return gender2;
    }
    public void setGender2(boolean gender2) {
        this.gender2 = gender2;
    }
    public boolean isCouplegender1() {
        return couplegender1;
    }
    public void setCouplegender1(boolean couplegender1) {
        this.couplegender1 = couplegender1;
    }
    public boolean isCouplegender2() {
        return couplegender2;
    }
    public void setCouplegender2(boolean couplegender2) {
        this.couplegender2 = couplegender2;
    }


    public String getStorytitle() {
        return storytitle;
    }
    public void setStorytitle(String storytitle) {
        this.storytitle = storytitle;
    }

    public String getStorycontent() {
        return storycontent;
    }
    public void setStorycontent(String storycontent) {
        this.storycontent = storycontent;
    }

    public boolean isEveryshare() {
        return everyshare;
    }
    public void setEveryshare(boolean everyshare) {
        this.everyshare = everyshare;
    }


    public String getCouplestorytitle() {
        return couplestorytitle;
    }
    public void setCouplestorytitle(String couplestorytitle) {
        this.couplestorytitle = couplestorytitle;
    }
    public String getCouplestorycontent() {
        return couplestorycontent;
    }
    public void setCouplestorycontent(String couplestorycontent) {
        this.couplestorycontent = couplestorycontent;
    }
    public boolean isCoupleshare() {
        return coupleshare;
    }
    public void setCoupleshare(boolean coupleshare) {
        this.coupleshare = coupleshare;
    }


    public String getPersonalstorytitle() {
        return personalstorytitle;
    }
    public void setPersonalstorytitle(String personalstorytitle) {
        this.personalstorytitle = personalstorytitle;
    }
    public String getPersonalstorycontent() {
        return personalstorycontent;
    }
    public void setPersonalstorycontent(String personalstorycontent) {
        this.personalstorycontent = personalstorycontent;
    }
    public boolean isPersonal() {
        return personal;
    }
    public void setPersonal(boolean personal) {
        this.personal = personal;
    }
}
