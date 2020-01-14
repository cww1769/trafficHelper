package com.vivian.trafficHelper.data;

public class UserInfo {
    private String phone = "";
    private long lastLoginTime = 0;
    private String username = "";
    private String passwd = "";
    private String realname = "";
    private String community = "";
    private String address = "";
    private int gender = 0;  //1 for unknown; 1 for male, 2 for female
    private long age = 0;
    private int role = 0; //0 for 业主，1 for 管理员，2 for 物业工作人员

    public UserInfo(int role, String phone, long lastLoginTime, String username, String passwd, String realname, String community, String address, int gender, long age) {
        this.role = role;
        this.phone = phone;
        this.lastLoginTime = lastLoginTime;
        this.username = username;
        this.passwd = passwd;
        this.realname = realname;
        this.community = community;
        this.address = address;
        this.gender = gender;
        this.age = age;
    }

    public UserInfo() {

    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
