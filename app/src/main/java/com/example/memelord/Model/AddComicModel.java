package com.example.memelord.Model;

public class AddComicModel {

    private String comicid;
    private String comicmemelord;
    private String comickeyword;
    private String comicphoto;
    private String comictime;

    public AddComicModel() {
    }

    public AddComicModel(String comicid, String comicmemelord, String comickeyword, String comicphoto, String comictime) {
        this.comicid = comicid;
        this.comicmemelord = comicmemelord;
        this.comickeyword = comickeyword;
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

    public String getComickeyword() {
        return comickeyword;
    }

    public void setComickeyword(String comickeyword) {
        this.comickeyword = comickeyword;
    }

    public String getComicphoto() {
        return comicphoto;
    }

    public void setComicphoto(String comicphoto) {
        this.comicphoto = comicphoto;
    }
}
