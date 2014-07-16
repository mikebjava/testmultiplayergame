package com.justinmichael.game.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketListener implements Runnable
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

}
