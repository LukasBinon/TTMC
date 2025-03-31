package models;

public class PlayerConfig {
	
        private String playerName;
        private int shapeIndex;
        private int colorIndex;
        
        public PlayerConfig(String name, int shape, int color) {
            this.playerName = name;
            this.shapeIndex = shape;
            this.colorIndex = color;
            
            
        
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
}
