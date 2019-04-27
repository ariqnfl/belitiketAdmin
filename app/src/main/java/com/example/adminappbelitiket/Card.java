package com.example.adminappbelitiket;

public class Card {
    String nama_wisata,short_desc,date_wisata,is_festival,is_photo_spot,is_wifi,ketentuan,lokasi,time_wisata,url_thumbnail;
    Long harga_tiket;

    public Card() {
    }

    public Card(String nama_wisata, String short_desc, String date_wisata, String is_festival, String is_photo_spot, String is_wifi, String ketentuan, String lokasi, String time_wisata, String url_thumbnail, Long harga_tiket) {
        this.nama_wisata = nama_wisata;
        this.short_desc = short_desc;
        this.date_wisata = date_wisata;
        this.is_festival = is_festival;
        this.is_photo_spot = is_photo_spot;
        this.is_wifi = is_wifi;
        this.ketentuan = ketentuan;
        this.lokasi = lokasi;
        this.time_wisata = time_wisata;
        this.url_thumbnail = url_thumbnail;
        this.harga_tiket = harga_tiket;
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public void setNama_wisata(String nama_wisata) {
        this.nama_wisata = nama_wisata;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public String getDate_wisata() {
        return date_wisata;
    }

    public void setDate_wisata(String date_wisata) {
        this.date_wisata = date_wisata;
    }

    public String getIs_festival() {
        return is_festival;
    }

    public void setIs_festival(String is_festival) {
        this.is_festival = is_festival;
    }

    public String getIs_photo_spot() {
        return is_photo_spot;
    }

    public void setIs_photo_spot(String is_photo_spot) {
        this.is_photo_spot = is_photo_spot;
    }

    public String getIs_wifi() {
        return is_wifi;
    }

    public void setIs_wifi(String is_wifi) {
        this.is_wifi = is_wifi;
    }

    public String getKetentuan() {
        return ketentuan;
    }

    public void setKetentuan(String ketentuan) {
        this.ketentuan = ketentuan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTime_wisata() {
        return time_wisata;
    }

    public void setTime_wisata(String time_wisata) {
        this.time_wisata = time_wisata;
    }

    public String getUrl_thumbnail() {
        return url_thumbnail;
    }

    public void setUrl_thumbnail(String url_thumbnail) {
        this.url_thumbnail = url_thumbnail;
    }

    public Long getHarga_tiket() {
        return harga_tiket;
    }

    public void setHarga_tiket(Long harga_tiket) {
        this.harga_tiket = harga_tiket;
    }
}
