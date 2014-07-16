package com.justinmichael.game.command;

import com.justinmichael.game.Game;
import com.justinmichael.game.entity.Cuboid;
import com.justinmichael.game.entity.Entity;
import com.justinmichael.game.entity.Player;

public class MoveCommand extends AbstractCommand
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6771687844925956636L;
	private Entity entity;
	private float x, y;

	public MoveCommand(Entity player, float x, float y)
	{
		super();
		this.entity = player;
		this.x = x;
		this.y = y;
	}

	@Override
	public void execute()
	{
		if (entity instanceof Player)
		{
			Player player = (Player) entity;
			try
			{
				Game.getPlayerByID(player.playerID).setPosition(x, y);
			} catch (Exception e)
			{
				e.printStackTrace();
				System.err.println("Unable to execute MoveCommand to player " + player.playerID + ", player returned null.");
			}
		} else if (entity instanceof Cuboid)
		{
			Cuboid c = (Cuboid) entity;
			c.setPosition(x, y);
		}

	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

}
