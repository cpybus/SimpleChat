package me.chris.SimpleChat.CommandHandler;

import me.chris.SimpleChat.SimpleChatHelperMethods;
import me.chris.SimpleChat.Variables;

public class SimpleChatOtherCommandsConsole
{
	public static void me(String[] args)
	{
		String msg = "";
		String[] arrayOfString1 = args;
		int j = args.length;
		for (int i = 0; i < j; i++)
		{
			String word = arrayOfString1[i];

			msg = msg + " " + word;
		}
		String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "+msg", "&" };
		String[] replace = { "Console", "Console", "", "", "", msg.trim(), "§" };
		String meMsg = SimpleChatHelperMethods.replaceVars(Variables.MeFormat, vars, replace);
		Variables.plugin.getServer().broadcastMessage(meMsg);
	}

	public static void say(String[] args)
	{
		String msg = "";
		String[] arrayOfString1 = args;
		int j = args.length;
		for (int i = 0; i < j; i++)
		{
			String word = arrayOfString1[i];

			msg = msg + " " + word;
		}
		String[] vars = { "+msg", "&" };
		String[] replace = { msg.trim(), "§" };
		String sayMsg = SimpleChatHelperMethods.replaceVars(Variables.SayFormat, vars, replace);
		Variables.plugin.getServer().broadcastMessage(sayMsg);
	}

	public static void broadcast(String[] args)
	{
		String msg = "";
		String[] arrayOfString1 = args;
		int j = args.length;
		for (int i = 0; i < j; i++)
		{
			String word = arrayOfString1[i];

			msg = msg + " " + word;
		}
		String[] vars = { "+msg", "&" };
		String[] replace = { msg.trim(), "§" };
		String BcastMsg = SimpleChatHelperMethods.replaceVars(Variables.BroadcastFormat, vars, replace);
		Variables.plugin.getServer().broadcastMessage(BcastMsg);
	}
}