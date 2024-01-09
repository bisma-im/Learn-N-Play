package com.example.learnnplay;

import java.util.List;

public class FlashCardDTO {
    private String letter;
    private List<String> objectNames;
    private List<String> imageIdentifiers;
    private List<String> audioIdentifiers;

    public FlashCardDTO() {
    }

    public FlashCardDTO(String letter, List<String> objectNames, List<String> imageIdentifiers, List<String> audioIdentifiers) {
        this.letter = letter;
        this.objectNames = objectNames;
        this.imageIdentifiers = imageIdentifiers;
        this.audioIdentifiers = audioIdentifiers;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public List<String> getObjectNames() {
        return objectNames;
    }

    public void setObjectNames(List<String> objectNames) {
        this.objectNames = objectNames;
    }

    public List<String> getImageIdentifiers() {
        return imageIdentifiers;
    }

    public void setImageIdentifiers(List<String> imageIdentifiers) {
        this.imageIdentifiers = imageIdentifiers;
    }

    public List<String> getAudioIdentifiers() {
        return audioIdentifiers;
    }

    public void setAudioIdentifiers(List<String> audioIdentifiers) {
        this.audioIdentifiers = audioIdentifiers;
    }
}
