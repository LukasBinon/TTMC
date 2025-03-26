package models;

public class Case {
    private double positionX;
    private double positionY;

    public Case(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
    
    

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }
}

