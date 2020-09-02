package com.abc.wcapp.Model;

public class notic {
    String noticM;
    String noticg;
    String noticid;


    public notic(){

    }

    public notic(String noticM, String noticg,String noticid) {
        this.noticM = noticM;
        this.noticg = noticg;
        this.noticid=noticid;
    }

    public String getNoticM() {
        return noticM;
    }

    public void setNoticM(String noticM) {
        this.noticM = noticM;
    }

    public String getNoticg() {
        return noticg;
    }

    public void setNoticid(String notiid) {
        this.noticid = noticid;
    }

    public String getNoticid() {
        return noticid;
    }

    public void setNoticg(String noticg) {
        this.noticg = noticg;
    }
}
