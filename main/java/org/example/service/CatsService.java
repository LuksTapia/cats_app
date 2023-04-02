package org.example.service;

import com.google.gson.Gson;
import com.squareup.okhttp.*;

import org.example.interfaces.CatsInterface;
import org.example.model.Cats;
import org.example.model.FavCats;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class CatsService implements CatsInterface {

    private static final String BASE_URL = "https://api.thecatapi.com/v1/images/search";
    private static final String FAV_URL = "https://api.thecatapi.com/v1/favourites";
    private final int MAX_WIDTH = 600;
    private static final String MENU_FAV = "Options: \n"
            + "1. See another picture\n"
            + "2. Delete Favourite\n" +
            "3. Return\n";

    private static final String MENU = "Options: \n"
            + "1. See another picture\n"
            + "2. Favourite\n" +
            "3. Return\n";

    @Override
    public void showCats() throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(BASE_URL).get().build();
        Response response = client.newCall(request).execute();
        String resp = response.body().string();

        //cut {} using substring in order to get only the info inside this JSON
        resp = resp.substring(1, resp.length());
        resp = resp.substring(0, resp.length() - 1);

        //convert API response into cat object
        Gson gson = new Gson();
        Cats cats = gson.fromJson(resp, Cats.class);

        //resizing image
        Image image = null;
        try {
            URL url = new URL(cats.getUrl()); //url obtained from JSON cats line 38

            ImageIcon catFont = imageTreatment(image, url);
            //create menu
            String[] buttons = {"See another picture", "Favourite", "Return"};
            String idCat = cats.getId(); // from object cats, I return the id from te cat
            String option = (String) JOptionPane.showInputDialog(null, MENU,
                    idCat, JOptionPane.INFORMATION_MESSAGE, catFont, buttons, buttons[0]);

            int selected = -1;

            for (int i = 0; i < buttons.length; i++) {
                if (option.equals(buttons[i])) {
                    selected = i;
                }
            }

            showSelection(selected, cats);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void showSelection(int selection, Cats cats) throws IOException {

        switch (selection) {
            case 0:
                showCats();
                break;
            case 1:
                addFavourite(cats);
                break;
            default:
                break;
        }

    }

    @Override
    public void addFavourite(Cats cat) {

        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n\t\"image_id\":\"" + cat.getId() + "\"\r\n}");
            Request request = new Request.Builder()
                    .url(FAV_URL)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", cat.getApikey())
                    .build();
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                response.body().close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    @Override
    public void listFavourites(String apiKey) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(FAV_URL)
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", apiKey)
                .build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();

        if (!response.isSuccessful()) {
            response.body().close();
        }

        Gson gson = new Gson();
        FavCats[] favs = gson.fromJson(json, FavCats[].class);

        if (favs.length > 0) {
            int min = 1;
            int max = favs.length;
            int random = (int) (Math.random() * ((max-min)+1)) + min;;
            int index = random -1;
            FavCats favourite = favs[index];
            Image image = null;

            try {
                URL url = new URL(favourite.imageURL());
                ImageIcon catFont = imageTreatment(image, url);
                String[] buttons = {"See another favourite", "Delete favourite", "Return"};
                String idCat = favourite.getId();
                String option = (String) JOptionPane.showInputDialog(null, MENU_FAV,
                        idCat, JOptionPane.INFORMATION_MESSAGE, catFont, buttons, buttons[0]);

                int selected = -1;
                for (int i = 0; i < buttons.length; i++) {
                    if (option.equals(buttons[i])) {
                        selected = i;
                    }
                }

                switch (selected) {
                    case 0:
                        listFavourites(apiKey);
                        break;
                    case 1:
                        deleteFavourite(favourite);
                        break;
                    default:
                        break;
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    @Override
    public void deleteFavourite(FavCats favourite) {

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/" + favourite.getId())
                    .delete(null)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", favourite.getApiKey())
                    .build();
            Response response = client.newCall(request).execute();

            if(!response.isSuccessful()) {
                response.body().close();
            }

        } catch(IOException e){
            System.out.println(e);
        }


    }

    private ImageIcon imageTreatment(Image image, URL url) throws IOException {
        image = ImageIO.read(url);

        ImageIcon catFont = new ImageIcon(image);

        if (catFont.getIconWidth() > MAX_WIDTH) {

            Image background = catFont.getImage();
            Image modified = background.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
            catFont = new ImageIcon(modified);
        }
        return catFont;
    }
}
