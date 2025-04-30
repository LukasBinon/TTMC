package strategy;

import models.PlayerConfig;

public interface MovementStrategy {
	public void move(PlayerConfig player, int steps);
}
