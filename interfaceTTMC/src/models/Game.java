package models;

import java.util.List;

public class Game {
	public final int MAX_POSITION = 35;
	private boolean finished;
	private static EDifficulty difficulty = EDifficulty.NORMAL;
	private List<PlayerConfig> players;
	
	public Game(List<PlayerConfig> players) {
		this.players = players;
		this.finished = false;
	}
	
	public List<PlayerConfig> getPlayers() {
		return players;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public void verifyEndGame() {
		int nbPlayerFinished = 0;
		if(players.size() != 1) {
			for(PlayerConfig p : players) {
				if(p.getPosition() == MAX_POSITION - 1)
					p.setFinished();
				if(p.isFinished())
					nbPlayerFinished++;
			}
			finished = (nbPlayerFinished == (players.size() - 1))?true:false;
		} else {
			PlayerConfig player = players.get(0);
			if(player.getPosition() == MAX_POSITION - 1)
				player.setFinished();
			finished = player.isFinished();
		}
	}
	
	public static void setDifficulty(EDifficulty diff) {
		difficulty = diff;
	}
	
	public static EDifficulty getDifficulty() {
		return difficulty;
	}
	
}