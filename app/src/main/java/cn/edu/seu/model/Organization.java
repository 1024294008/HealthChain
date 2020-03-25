package cn.edu.seu.model;

public class Organization {
    private int id;
    private String account;
    private String password;
    private String ethAddress;
    private String organizationName;
    private String type;
    private String certificateResult;
    private String certificateFiles;
    private String certificateTime;
    private double balance;

    public Organization() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEthAddress() {
        return ethAddress;
    }

    public void setEthAddress(String ethAddress) {
        this.ethAddress = ethAddress;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCertificateResult() {
        return certificateResult;
    }

    public void setCertificateResult(String certificateResult) {
        this.certificateResult = certificateResult;
    }

    public String getCertificateFiles() {
        return certificateFiles;
    }

    public void setCertificateFiles(String certificateFiles) {
        this.certificateFiles = certificateFiles;
    }

    public String getCertificateTime() {
        return certificateTime;
    }

    public void setCertificateTime(String certificateTime) {
        this.certificateTime = certificateTime;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
