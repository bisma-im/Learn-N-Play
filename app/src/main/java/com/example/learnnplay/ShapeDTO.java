package com.example.learnnplay;

public class ShapeDTO {
    private String shapeName;
    private String imageUrl;
    private String audioUrl; // URL to the audio file in Firebase Storage

    // Default constructor for Firebase
    public ShapeDTO() {}

    public ShapeDTO(String shapeName, String imageUrl, String audioUrl) {
        this.shapeName = shapeName;
        this.imageUrl = imageUrl;
        this.audioUrl = audioUrl;
    }

    public String getShapeName() {
        return shapeName;
    }

    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}



