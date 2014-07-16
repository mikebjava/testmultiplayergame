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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.justinmichael.game.command.MoveCommand;
import com.justinmichael.game.entity.Cuboid;
import com.justinmichael.game.entity.Entity;
import com.justinmichael.game.entity.Player;
import com.justinmichael.game.network.SocketListener;

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

	public static Socket socket;
	private boolean isConnected;
	public static Player player;
	public static ArrayList<Player> otherPlayers = new ArrayList<Player>();
	public static ArrayList<Entity> otherEntities = new ArrayList<Entity>();
	public Random random = new Random();
	public static SocketListener socketListener;

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
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			player.setX(player.getX() + player.getSpeed());
			try
			{
				socketListener.getObjectOutputStream().writeObject(new MoveCommand(player, player.getX(), player.getY()));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			player.setX(player.getX() - player.getSpeed());
			try
			{
				socketListener.getObjectOutputStream().writeObject(new MoveCommand(player, player.getX(), player.getY()));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			player.setY(player.getY() - player.getSpeed());
			try
			{
				socketListener.getObjectOutputStream().writeObject(new MoveCommand(player, player.getX(), player.getY()));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			player.setY(player.getY() + player.getSpeed());
			try
			{
				socketListener.getObjectOutputStream().writeObject(new MoveCommand(player, player.getX(), player.getY()));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		player.render();

		for (int i = 0; i < otherPlayers.size(); i++)
		{
			otherPlayers.get(i).render();
		}

		for (int i = 0; i < otherEntities.size(); i++)
		{
			if (otherEntities.get(i) instanceof Cuboid)
			{
				((Cuboid) otherEntities.get(i)).moveToClosestPlayer();
				((Cuboid) otherEntities.get(i)).render();
			}
		}
	}

	private void preInit()
	{

	}

	public void connect()
	{
		while (!isConnected)
		{
			try
			{
				socket = new Socket("10.0.0.15", 25565);
				isConnected = true;
				socketListener = new SocketListener(socket);
				new Thread(socketListener).start();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
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
		player = new Player(WIDTH / 2, HEIGHT / 2, 10, 10, 1f);
	}

	private void postInit()
	{
		connect();
	}

	private void stop()
	{
		Display.destroy();
		System.exit(0);
	}

	public static void main(String[] args)
	{
		new Game();
	}

	public static Player getPlayerByID(int id)
	{
		for (int i = 0; i < otherPlayers.size(); i++)
		{
			if (id == otherPlayers.get(i).playerID)
			{
				return otherPlayers.get(i);
			}
		}
		System.out.println("Unable to get player by id " + id + ".");
		return null;
	}

	public static int getPlayerIndexByID(int id)
	{
		for (int i = 0; i < otherPlayers.size(); i++)
		{
			if (id == otherPlayers.get(i).playerID)
			{
				return i;
			}
		}
		System.out.println("Unable to get player by id " + id + ".");
		return 0;
	}
}
