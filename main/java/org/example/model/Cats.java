package org.example.model;

import io.github.cdimascio.dotenv.Dotenv;

public class Cats {

    private Dotenv dotenv = Dotenv.load();
    private String id;
    private String url;
    private String apikey = dotenv.get("API_KEY");
    private String image;


    public Cats() {
    }

    public Cats(String id, String url, String apikey, String image) {
        setId(id);
        setUrl(url);
        setApikey(apikey);
        setImage(image);
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    public String getApikey() {
        return apikey;
    }

    private void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getImage() {
        return image;
    }

    private void setImage(String image) {
        this.image = image;
    }
}
