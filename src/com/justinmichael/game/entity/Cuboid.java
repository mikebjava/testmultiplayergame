package com.justinmichael.game.entity;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.justinmichael.game.Game;
import com.justinmichael.game.command.MoveCommand;

public class Cuboid extends Entity
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1910559701444117168L;

	public Cuboid(float x, float y, float width, float height, float speed)
	{
		super(x, y, width, height, speed);
	}

	public void render()
	{

		// Corners start upper left, rotating clockwise
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3d(1.0f, 0, 0);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + width, y);
		GL11.glVertex2f(x + width, y + height);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();

	}

	public void moveToClosestPlayer()
	{
		Player target = null;
		float closest = 0;

		if (Game.otherPlayers.size() > 0)
		{
			for (int i = 0; i < Game.otherPlayers.size(); i++)
			{

				float px = Game.otherPlayers.get(i).getX();
				float py = Game.otherPlayers.get(i).getY();
				float d = (float) Math.sqrt(Math.pow((x - px), 2) + Math.pow((y - py), 2));
				if (i == 0)
				{
					closest = d;
					target = Game.otherPlayers.get(i);
				} else
				{
					if (d < closest)
					{
						closest = d;
						target = Game.otherPlayers.get(i);
					}
				}
			}

			float px = Game.player.getX();
			float py = Game.player.getY();
			float d = (float) Math.sqrt(Math.pow((x - px), 2) + Math.pow((y - py), 2));
			if (d < closest)
			{
				closest = d;
				target = Game.player;
			}
		} else
		{
			target = Game.player;
		}

		if (this.x > target.getX())
		{
			this.setX(this.x - this.getSpeed());
		} else if (this.x < target.getX())
		{
			this.setX(this.x + this.getSpeed());
		}

		if (this.y > target.getY())
		{
			this.setY(this.y - this.getSpeed());
		} else if (this.y < target.getY())
		{
			this.setY(this.y + this.getSpeed());
		}

		try
		{
			Game.socketListener.getObjectOutputStream().writeObject(new MoveCommand(this, this.x, this.y));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
