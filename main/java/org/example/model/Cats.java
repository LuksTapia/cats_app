package org.example.model;

import io.github.cdimascio.dotenv.Dotenv;

public class Cats {

    Dotenv dotenv = Dotenv.load();

    int id;
    String url;
    String apikey = dotenv.get("API_KEY");
    String image;

    public int getId() {
        return id;
    }

    private void setId(int id) {
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
