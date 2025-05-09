package models;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Piece {
	private Circle token; 
    private int position; 

    public Piece(Color color) {
        this.token = new Circle(15, color); 
        this.token.setVisible(false);      
        this.position = 0;
    }

    public Circle getToken() {
        return token;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setVisible(boolean isVisible) {
        this.token.setVisible(isVisible); 
    }
    
}

