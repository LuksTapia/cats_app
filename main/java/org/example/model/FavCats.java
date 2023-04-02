package org.example.model;

import io.github.cdimascio.dotenv.Dotenv;

public class FavCats {

    Dotenv dotenv = Dotenv.load();

    private String id;
    private String imageId;
    private String apiKey = dotenv.get("API_KEY");
    private ImageX image;

    public FavCats(){

    }

    public FavCats(String id, String idImage, String apiKey, ImageX imageX) {
        setId(id);
        setIdImage(idImage);
        setApiKey(apiKey);
        setImageX(imageX);
    }

    public String imageURL() {
        return this.image.getUrl();
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getIdImage() {
        return imageId;
    }

    private void setIdImage(String idImage) {
        this.imageId = idImage;
    }

    public String getApiKey() {
        return apiKey;
    }

    private void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ImageX getImageX() {
        return image;
    }

    private void setImageX(ImageX imageX) {
        this.image = imageX;
    }
}
