package com.justinmichael.game.network;

import java.net.Socket;

public interface ISocketActionHandler
{
	public void onInput(Socket source);

	public void onOutput(Socket source);
}
