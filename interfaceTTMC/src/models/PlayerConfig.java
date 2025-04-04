package models;

public class PlayerConfig {

    private String playerName;
    private int shapeIndex;
    private int colorIndex;
    private int currentIndex; // Position actuelle du joueur

    // Constructeur
    public PlayerConfig(String name, int shape, int color) {
        this.playerName = name;
        this.shapeIndex = shape;
        this.colorIndex = color;
        this.currentIndex = 0; // Initialisation à la position de départ
    }

    // Getter et Setter pour playerName
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    // Getter et Setter pour shapeIndex
    public int getShapeIndex() {
        return shapeIndex;
    }

    public void setShapeIndex(int shapeIndex) {
        this.shapeIndex = shapeIndex;
    }

    // Getter et Setter pour colorIndex
    public int getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }

    // Getter et Setter pour currentIndex
    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
}

