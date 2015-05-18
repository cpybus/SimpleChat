package me.chris.SimpleChat.CommandHandler;

import me.chris.SimpleChat.SimpleChatHelperMethods;
import me.chris.SimpleChat.Variables;
import org.bukkit.entity.Player;

public class SimpleChatAdminChatConsole
{
	public static void adminChat(String msg)
	{
		String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "+msg", "&" };
		String[] replace = { "Console", "Console", "", "", "", msg, "§" };

		String chat = SimpleChatHelperMethods.replaceVars(Variables.AdminChatFormat, vars, replace);

		for (Player players : Variables.plugin.getServer().getOnlinePlayers())
		{
			if (!Variables.perms.has(players, "simplechat.adminchat"))
				continue;
			players.sendMessage(chat);
		}
		
		Variables.plugin.getServer().getConsoleSender().sendMessage(chat);
	}
}
