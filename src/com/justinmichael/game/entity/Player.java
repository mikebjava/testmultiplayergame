package com.justinmichael.game.entity;

import java.io.Serializable;
import java.util.Random;

import org.lwjgl.opengl.GL11;

public class Player extends Entity
{
	/**
	 * 
	 */
	Random random = new Random();
	public int playerID = random.nextInt();

	public Player(float x, float y, float width, float height, float speed)
	{
		super(x, y, width, height, speed);
	}

	public void render()
	{
		// Corners start upper left, rotating clockwise
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3d(random.nextFloat(), random.nextFloat(), random.nextFloat());
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + width, y);
		GL11.glVertex2f(x + width, y + height);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();
	}
}
