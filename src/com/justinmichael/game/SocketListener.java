package com.justinmichael.game;

import java.net.Socket;

import com.justinmichael.game.entity.Player;

public class SocketListener implements Runnable
{
	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				Object obj = Game.inputStream.readObject();
				if (obj != null)
				{
					if (obj instanceof Player)
					{
						Game.otherPlayers.add((Player) obj);
					}

					if (obj instanceof String)
					{
						String str = (String) obj;
						if (str.startsWith("playerLeave:"))
						{
							int playerIDLeft = Integer.parseInt(str.replaceAll("playerLeave:", ""));
							for (int i = 0; i < Game.otherPlayers.size(); i++)
							{
								if (playerIDLeft == Game.otherPlayers.get(i).playerID)
								{
									Game.otherPlayers.remove(i);
									break;
								}
							}
						}

						if (str.startsWith("playerX:"))
						{
							String[] args = str.split(":");
							int playerID = Integer.parseInt(args[1]);
							int newX = Integer.parseInt(args[2]);

							Game.getPlayerByID(playerID).setX(newX);

						}

						if (str.startsWith("playerY:"))
						{
							String[] args = str.split(":");
							int playerID = Integer.parseInt(args[1]);
							int newY = Integer.parseInt(args[2]);

							Game.getPlayerByID(playerID).setY(newY);
						}
					}

				}
			} catch (Exception e)
			{
				System.out.println("Unable to read data: " + e.getLocalizedMessage());
			}
		}
	}
}
