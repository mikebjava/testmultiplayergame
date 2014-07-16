package com.justinmichael.game.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.justinmichael.game.Game;
import com.justinmichael.game.command.ICommand;
import com.justinmichael.game.entity.Player;

public class SocketListener implements Runnable, ISocketActionHandler
{

	private Socket socket;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private boolean listen;

	public SocketListener(Socket socket)
	{
		super();
		try
		{
			System.out.println("Initializing socket listener...");
			this.socket = socket;
			System.out.println("Getting inputstream...");
			this.objectInputStream = new ObjectInputStream(socket.getInputStream());
			System.out.println("Getting outputstream...");
			this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("Sending player data...");
			objectOutputStream.writeObject(Game.player);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Socket getSocket()
	{
		return socket;
	}

	@Override
	public void run()
	{
		this.setListening(true);
		while (isListening())
		{
			try
			{
				Object obj = null;
				while ((obj = objectInputStream.readObject()) != null)
				{
					this.onInput(socket, obj);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public boolean isListening()
	{
		return listen;
	}

	public void setListening(boolean listen)
	{
		this.listen = listen;
	}

	public ObjectInputStream getObjectInputStream()
	{
		return objectInputStream;
	}

	public ObjectOutputStream getObjectOutputStream()
	{
		return objectOutputStream;
	}

	@Override
	public void onInput(Socket source, Object input)
	{

		if (input instanceof Player)
		{
			System.out.println("Data Received, type Player.");
			Game.otherPlayers.add((Player) input);
		}

		if (input instanceof ICommand)
		{
			((ICommand) input).execute();
		}

	}

	@Override
	public void onOutput(Socket source, Object output)
	{

	}

}
