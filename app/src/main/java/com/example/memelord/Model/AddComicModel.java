package com.example.memelord.Model;

import java.util.List;

public class AddComicModel {

    private String comicid;
    private String comicmemelord;
    private List<AddDescriptionModel> comicdescription;
    private String comicphoto;
    private String comictime;

    public AddComicModel() {
    }

    public AddComicModel(String comicid, String comicmemelord, List<AddDescriptionModel> comicdescription, String comicphoto, String comictime) {
        this.comicid = comicid;
        this.comicmemelord = comicmemelord;
        this.comicdescription = comicdescription;
        this.comicphoto = comicphoto;
        this.comictime = comictime;
    }

    public String getComicid() {
        return comicid;
    }

    public void setComicid(String comicid) {
        this.comicid = comicid;
    }

    public String getComictime() {
        return comictime;
    }

    public void setComictime(String comictime) {
        this.comictime = comictime;
    }

    public String getComicmemelord() {
        return comicmemelord;
    }

    public void setComicmemelord(String comicmemelord) {
        this.comicmemelord = comicmemelord;
    }

    public List<AddDescriptionModel> getComicdescription() {
        return comicdescription;
    }

    public void setComicdescription(List<AddDescriptionModel> comicdescription) {
        this.comicdescription = comicdescription;
    }

    public String getComicphoto() {
        return comicphoto;
    }

    public void setComicphoto(String comicphoto) {
        this.comicphoto = comicphoto;
    }
}
