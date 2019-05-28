package com.moustafa.bigburger.module;

public class productCart_module {

    String ref, title, description, thumbnail;
    double price;
    boolean added;
    int count;

    public productCart_module(String ref, String title, String description, String thumbnail, double price, boolean added, int count) {
        this.ref = ref;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.price = price;
        this.added = added;
        this.count = count;
    }

    public productCart_module() {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
