package com.justinmichael.game.network;

import java.net.Socket;

public interface ISocketActionHandler
{
	public void onInput(Socket source, Object input);

	public void onOutput(Socket source, Object output);
}
