package com.justinmichael.game.command;

import com.justinmichael.game.Game;
import com.justinmichael.game.entity.Player;

public class MoveCommand extends AbstractCommand
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6771687844925956636L;
	private Player player;
	private float x, y;

	public MoveCommand(Player player, float x, float y)
	{
		super();
		this.player = player;
		this.x = x;
		this.y = y;
	}

	@Override
	public void execute()
	{
		try
		{
			Game.getPlayerByID(player.playerID).setPosition(x, y);
		} catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Unable to execute MoveCommand to player " + player.playerID + ", player returned null.");
		}
	}

}
