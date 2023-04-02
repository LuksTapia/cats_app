package org.example.model;

public class ImageX {

    private String id;
    private String url;

    public ImageX(String id, String url) {
        setId(id);
        setUrl(url);
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    private void setUrl(String url) {
        this.url = url;
    }
}
