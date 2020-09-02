package com.abc.wcapp;

public class Notic {
        String noticid;
        String noticM;
        String noticg;

        public Notic(){

        }

        public Notic(String noticid, String noticM, String noticg) {
            this.noticid = noticid;
            this.noticM = noticM;
            this.noticg = noticg;
        }

        public String getNoticid() {
            return noticid;
        }

        public String getNoticM() {
            return noticM;
        }

        public String getNoticg() {
            return noticg;
        }
    }

