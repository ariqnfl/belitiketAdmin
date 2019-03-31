package com.example.adminappbelitiket;

public class Card {
    String url_photo_menu,nama_makanan,harga;

    public Card(){

    }
    public Card(String url_photo_menu, String nama_makanan, String harga) {
        this.url_photo_menu = url_photo_menu;
        this.nama_makanan = nama_makanan;
        this.harga = harga;
    }

    public String getUrl_photo_menu() {
        return url_photo_menu;
    }

    public void setUrl_photo_menu(String url_photo_menu) {
        this.url_photo_menu = url_photo_menu;
    }

    public String getNama_makanan() {
        return nama_makanan;
    }

    public void setNama_makanan(String nama_makanan) {
        this.nama_makanan = nama_makanan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
