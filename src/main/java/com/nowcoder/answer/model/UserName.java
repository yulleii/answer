package com.nowcoder.answer.model;

public class UserName {
    private String fname;
    private String lname;
    public UserName(){

    }
    public UserName(String fname,String lname){
        this.fname=fname;
        this.lname=lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    @Override
    public String toString() {
        return "UserName{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                '}';
    }
}
