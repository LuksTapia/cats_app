package org.example.interfaces;

import org.example.model.Cats;

import java.io.IOException;

public interface CatsInterface {

    public abstract void showCats() throws IOException;

    public abstract void showSelection(int selected, Cats cats) throws IOException ;

    public abstract void addFavourite(Cats cats);
}
