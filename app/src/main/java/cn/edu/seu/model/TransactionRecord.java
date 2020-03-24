package cn.edu.seu.model;

public class TransactionRecord {
    private int id;
    private String sendAddress;
    private String recieveAddress;
    private double transactEth;
    private String transactTime;
    private String transactAddr;
    private String transactRemarks;

    public TransactionRecord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getRecieveAddress() {
        return recieveAddress;
    }

    public void setRecieveAddress(String recieveAddress) {
        this.recieveAddress = recieveAddress;
    }

    public double getTransactEth() {
        return transactEth;
    }

    public void setTransactEth(double transactEth) {
        this.transactEth = transactEth;
    }

    public String getTransactTime() {
        return transactTime;
    }

    public void setTransactTime(String transactTime) {
        this.transactTime = transactTime;
    }

    public String getTransactAddr() {
        return transactAddr;
    }

    public void setTransactAddr(String transactAddr) {
        this.transactAddr = transactAddr;
    }

    public String getTransactRemarks() {
        return transactRemarks;
    }

    public void setTransactRemarks(String transactRemarks) {
        this.transactRemarks = transactRemarks;
    }
}
