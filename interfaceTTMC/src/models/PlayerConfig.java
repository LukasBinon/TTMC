package models;

import javafx.scene.shape.Circle;
import strategy.HardMovement;
import strategy.MovementStrategy;
import strategy.NormalMovement;

public class PlayerConfig {
	
        private String playerName;
        private int shapeIndex;
        private int colorIndex;
        private int position;
        private boolean finished;
        private MovementStrategy movementStrategy;
        private int rank;
        
        public PlayerConfig(String name, int shape, int color, EDifficulty difficulty) {
            this.playerName = name;
            this.shapeIndex = shape;
            this.colorIndex = color;
            this.position = 0;
            this.finished = false;
            this.movementStrategy = switch(difficulty) {
	            case NORMAL -> new NormalMovement();
	            case HARD -> new HardMovement();
            };
            this.rank = 4;
        }
        
        public void move(int steps) {
        	movementStrategy.move(this, steps);
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
	    
	    public int getRank() {
	    	return rank;
	    }
	    
	    public void setRank(int rank) {
	    	if(rank > 0 && rank < 5)
	    		this.rank = rank;
	    }

}
