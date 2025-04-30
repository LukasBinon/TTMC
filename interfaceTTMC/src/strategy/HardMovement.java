package strategy;

import models.PlayerConfig;

public class HardMovement implements MovementStrategy {

	@Override
	public void move(PlayerConfig player, int steps) {
		player.setPosition(player.getPosition() + steps);
	}

}
