package br.com.nexus.main.core.models;

public class PlayerVipModel {

    private String ID;
    private Long timeExpire;

    public PlayerVipModel(String ID, Long timeExpire) {
        this.ID = ID;
        this.timeExpire = timeExpire;
    }

    public void getVip() {

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Long getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(Long timeExpire) {
        this.timeExpire = timeExpire;
    }
}
