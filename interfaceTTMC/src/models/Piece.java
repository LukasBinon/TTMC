package models;

public class Piece {
    private String name;
    private int position;

    public Piece(String name) {
        this.name = name;
        this.position = 0;
    }

    public void moveTo(int newPosition) {
        this.position = newPosition;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }
}

