package models;

import java.util.List;

public class Game {
	public final int MAX_POSITION = 35;
	private boolean finished;
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
		for(PlayerConfig p : players) {
			System.out.println(p.getPosition());
			if(p.getPosition() == MAX_POSITION - 1)
				p.setFinished();
			if(p.isFinished())
				nbPlayerFinished++;
		}
		finished = (nbPlayerFinished == (players.size() - 1))?true:false;
	}
	
	
}