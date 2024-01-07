package com.example.learnnplay;

public class FlashCard {
    String letter;
    String[] objectNames;
    int[] images;
    int[] audios;

    public FlashCard(String letter, String[] objectNames, int[] images, int[] audios) {
        this.letter = letter;
        this.objectNames = objectNames;
        this.images = images;
        this.audios = audios;
    }
}
