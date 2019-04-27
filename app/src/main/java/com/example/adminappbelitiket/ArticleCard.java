package com.example.adminappbelitiket;

public class ArticleCard {
    String nama_artikel,url_photo_artikel,desc_artikel;

    public ArticleCard() {
    }

    public ArticleCard(String nama_artikel, String url_photo_artikel, String desc_artikel) {
        this.nama_artikel = nama_artikel;
        this.url_photo_artikel = url_photo_artikel;
        this.desc_artikel = desc_artikel;
    }

    public String getNama_artikel() {
        return nama_artikel;
    }

    public void setNama_artikel(String nama_artikel) {
        this.nama_artikel = nama_artikel;
    }

    public String getUrl_photo_artikel() {
        return url_photo_artikel;
    }

    public void setUrl_photo_artikel(String url_photo_artikel) {
        this.url_photo_artikel = url_photo_artikel;
    }

    public String getDesc_artikel() {
        return desc_artikel;
    }

    public void setDesc_artikel(String desc_artikel) {
        this.desc_artikel = desc_artikel;
    }
}
