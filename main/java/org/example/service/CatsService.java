package org.example.service;

import com.google.gson.Gson;
import com.squareup.okhttp.*;

import org.example.interfaces.CatsInterface;
import org.example.model.Cats;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class CatsService implements CatsInterface {

    private final String BASE_URL = "https://api.thecatapi.com/v1/images/search";

    private final String FAV_URL = "https://api.thecatapi.com/v1/favourites";
    private final int MAX_WIDTH = 600;


    @Override
    public void showCats() throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(BASE_URL).get().build();
        Response response = client.newCall(request).execute();
        String resp = response.body().string();

        //cut {} using substring in order to get only the info inside this JSON

        resp = resp.substring(1,resp.length());
        resp = resp.substring(0,resp.length()-1);

        //convert API response into cat object
        Gson gson = new Gson();
        Cats cats = gson.fromJson(resp, Cats.class);

        //resizing image
        Image image;
        try{
            URL url = new URL(cats.getUrl()); //url obtained from JSON cats line 38
            image = ImageIO.read(url);

            ImageIcon catFont = new ImageIcon(image);

            if(catFont.getIconWidth() > MAX_WIDTH) {

                Image background = catFont.getImage();
                Image modified = background.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
                catFont = new ImageIcon(modified);
            }

            //create menu

            String menu = "Options: \n"
                    + "1. See another picture\n"
                    + "2. Favourite\n" +
                    "3. Return\n";

            String [] buttons = {"See another picture", "Favourite", "Return"};
            String idCat = cats.getId(); // from object cats, I return the id from te cat
            String option = (String) JOptionPane.showInputDialog(null, menu,
                    idCat, JOptionPane.INFORMATION_MESSAGE, catFont, buttons, buttons[0]);
            
            int selected = -1;

            for (int i = 0; i < buttons.length; i++) {
                if(option.equals(buttons[i])) {
                    selected = i;
                }
            }

            showSelection(selected, cats);


        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void showSelection(int selection, Cats cats) throws IOException {

        switch(selection) {
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
    public void addFavourite(Cats cat){

        try{
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n\t\"image_id\":\""+cat.getId()+"\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", cat.getApikey())
                    .build();
            Response response = client.newCall(request).execute();

            if(!response.isSuccessful()) {
                response.body().close();
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }





    }
}
