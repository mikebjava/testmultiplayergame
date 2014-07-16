package com.justinmichael.game.command;

import com.justinmichael.game.Game;
import com.justinmichael.game.entity.Player;

public class DisconnectCommand extends AbstractCommand
{

	private Player player;

	public DisconnectCommand(Player player)
	{
		super();
		this.player = player;
	}

	@Override
	public void execute()
	{
		Game.otherPlayers.remove(player);
	}

}
