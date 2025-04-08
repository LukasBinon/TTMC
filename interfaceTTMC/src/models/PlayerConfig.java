package models;

import javafx.scene.shape.Circle;

public class PlayerConfig {
	
        private String playerName;
        private int shapeIndex;
        private int colorIndex;
        private int position;
        private boolean finished;
        
        public PlayerConfig(String name, int shape, int color) {
            this.playerName = name;
            this.shapeIndex = shape;
            this.colorIndex = color;
            this.position = 0;
            this.finished = false;
        }

		public String getPlayerName() {
			return playerName;
		}

		public void setPlayerName(String playerName) {
			this.playerName = playerName;
		}

		public int getShapeIndex() {
			return shapeIndex;
		}

		public void setShapeIndex(int shapeIndex) {
			this.shapeIndex = shapeIndex;
		}

		public int getColorIndex() {
			return colorIndex;
		}

		public void setColorIndex(int colorIndex) {
			this.colorIndex = colorIndex;
		}
		
		
	    public int getPosition() {
			return position;
		}

		public void setPosition(int position) {
			this.position = position;
		}

		public boolean isFinished() {
	    	return finished;
	    }
	    
	    public void setFinished() {
	    	this.finished = true;
	    }

}
