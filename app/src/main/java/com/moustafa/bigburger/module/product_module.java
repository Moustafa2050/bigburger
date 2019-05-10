package com.moustafa.bigburger.module;

public class product_module {

    String ref, title, description, thumbnail;
    float price;

    public product_module(String ref, String title, String description, String thumbnail, float price) {
        this.ref = ref;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.price = price;
    }

    public product_module() {
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
