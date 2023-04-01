package org.example;

import org.example.service.CatsService;

import javax.swing.*;
import java.io.IOException;


public class Main {

    public static void main(String[] args) {

        showMenu();

    }

    public static void showMenu() {


        int menuOption = -1;

        String [] buttons = {
                "1. See cats" ,"2. See favourites" ,"3. Log out"};

        do{

            String option = (String) JOptionPane.showInputDialog(null, "Java Cats", "Main Menu", JOptionPane.INFORMATION_MESSAGE,
                    null, buttons,buttons[0]);

            for(int i=0;i<buttons.length;i++){
                if(option.equals(buttons[i])){
                    menuOption = i;
                }
            }

            try{
                chosenOption(menuOption);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }


        } while(menuOption != 2);
    }

    private static void chosenOption(int option) throws IOException {

        CatsService s = new CatsService();

        switch (option) {
            case 0:
                s.showCats();
                break;
            default:
                break;
        }

    }
}