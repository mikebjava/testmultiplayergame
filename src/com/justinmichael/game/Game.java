package com.justinmichael.game;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Game
{

	/** The width of the Display */
	public static final int WIDTH = 800;

	/** The height of the Display */
	public static final int HEIGHT = 600;

	/** The height of the Display */
	public static final String windowTitle = "Game Name";

	/** The version of the Game */
	public static final String version = "v1.00";

	public Game()
	{
		initDisplay();
		initOpenGL();
		preInit();
		initEntities();
		postInit();

		while (!Display.isCloseRequested())
		{
			render();
			Display.update();
			Display.sync(60);
		}

		stop();

	}

	private void initDisplay()
	{
		try
		{

			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle(windowTitle + " " + version);
			Display.create();

		} catch (LWJGLException e)
		{
			System.err.println("Display could not be created: " + e.getStackTrace());
		}

	}

	private void render()
	{

	}

	private void preInit()
	{

	}

	private void initOpenGL()
	{
		glMatrixMode(GL_PROJECTION);
		glMatrixMode(GL_MODELVIEW);
		glOrtho(0, WIDTH, HEIGHT, 0, -1, 1);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	private void initEntities()
	{

	}

	private void postInit()
	{

	}

	private void stop()
	{
		Display.destroy();

	}

	public static void main(String[] args)
	{
		new Game();
	}

}
