package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        showMenu();

    }

    private static void showMenu() {

        int menuOption = -1;
        String [] buttons = {
                "1. See cats" , "2. Log out"};

        do{

            String option = (String) JOptionPane.showInputDialog(null, "Java Cats", "Main Menu", JOptionPane.INFORMATION_MESSAGE,
                    null, buttons,buttons[0]);

            for(int i=0;i<buttons.length;i++){
                if(option.equals(buttons[i])){
                    menuOption = i;
                }
            }

            chosenOption(menuOption);

        } while(menuOption != 2);
    }

    private static void chosenOption(int option) {

        switch (option) {
            case 0:
                break;
            default:
                break;
        }

    }
}