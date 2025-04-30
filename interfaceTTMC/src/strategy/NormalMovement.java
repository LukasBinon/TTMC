package strategy;

import models.PlayerConfig;

public class NormalMovement implements MovementStrategy {

	@Override
	public void move(PlayerConfig player, int steps) {
		if (steps > 0) {
            player.setPosition(player.getPosition() + steps);
        }
	}

}
