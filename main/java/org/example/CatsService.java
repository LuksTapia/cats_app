package org.example;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.example.interfaces.CatsInterface;
import org.example.model.Cats;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class CatsService implements CatsInterface {

    private String base_url = "https://api.thecatapi.com/v1/";
    private final int maxWidth = 800;


    @Override
    public void showCats() throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(base_url).get().build();
        Response response = client.newCall(request).execute();
        String resp = response.body().string();

        //cut {} using substring in order to get only the info inside this JSON

        resp = resp.substring(1,resp.length());
        resp = resp.substring(0,resp.length()-1);

        //convert API response into cat object
        Gson gson = new Gson();
        Cats cats = gson.fromJson(resp, Cats.class);

        //resizing image
        Image image = null;
        try{
            URL url = new URL(cats.getUrl()); //url obtained from JSON cats
            image = ImageIO.read(url);

            ImageIcon catFont = new ImageIcon(image);

            if(catFont.getIconWidth() > maxWidth) {

                Image background = catFont.getImage();
                Image modified = background.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                catFont = new ImageIcon(modified);
            }


        }catch(IOException e) {
            System.out.println(e.getMessage());
        }


    }
}
