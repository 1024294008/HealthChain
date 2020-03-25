package cn.edu.seu.model;

public class User_HealthData {
    private int id;
    private int userid;
    private String uploadTime;
    private String dataAddr;
    private String permitVisit;

    public User_HealthData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getDataAddr() {
        return dataAddr;
    }

    public void setDataAddr(String dataAddr) {
        this.dataAddr = dataAddr;
    }

    public String getPermitVisit() {
        return permitVisit;
    }

    public void setPermitVisit(String permitVisit) {
        this.permitVisit = permitVisit;
    }
}
