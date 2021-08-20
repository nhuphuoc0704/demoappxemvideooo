package model;

public class User {
    private int id;
    private String tdn;
    private String mk;
    private int vip;

    public User(String tdn, String mk) {
        this.tdn = tdn;
        this.mk = mk;
        this.vip= 0;

    }

    public String getTdn() {
        return tdn;
    }

    public void setTdn(String tdn) {
        this.tdn = tdn;
    }

    public String getMk() {
        return mk;
    }

    public void setMk(String mk) {
        this.mk = mk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }
}
