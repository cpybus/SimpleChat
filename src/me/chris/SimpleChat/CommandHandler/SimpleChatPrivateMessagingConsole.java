package me.chris.SimpleChat.CommandHandler;

import java.util.List;
import me.chris.SimpleChat.SimpleChatAPI;
import me.chris.SimpleChat.SimpleChatHelperMethods;
import me.chris.SimpleChat.Variables;
import org.bukkit.entity.Player;

public class SimpleChatPrivateMessagingConsole
{
	public static void msgFromConsole(String otherP, String msg)
	{
		List<Player> matchedPlayers = Variables.plugin.getServer().matchPlayer(otherP);

		if ((matchedPlayers == null) || (matchedPlayers.size() < 1))
		{
			Variables.plugin.getServer().getConsoleSender().sendMessage("[SimpleChat] The name " + otherP + " did not match any players.");
			return;
		}
		if (matchedPlayers.size() > 1)
		{
			Variables.plugin.getServer().getConsoleSender().sendMessage("[SimpleChat] The name " + otherP + " matched more than one player. Please enter a different/more-specific name.");
			return;
		}

		Player matchedPlayer = (Player) matchedPlayers.get(0);

		String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "+otherpname", "+otherdname", "+otherpre", "+othersuf", "+othergro", "+msg", "&" };
		String[] replace = { "Console", "Console", "", "", "", matchedPlayer.getName(), matchedPlayer.getDisplayName(), SimpleChatAPI.getPrefix(matchedPlayer), SimpleChatAPI.getSuffix(matchedPlayer), SimpleChatAPI.getGroup(matchedPlayer), msg, "§" };

		String[] replace2 = { matchedPlayer.getName(), matchedPlayer.getDisplayName(), SimpleChatAPI.getPrefix(matchedPlayer), SimpleChatAPI.getSuffix(matchedPlayer), SimpleChatAPI.getGroup(matchedPlayer), "Console", "Console", "", "", "", msg, "§" };

		Variables.plugin.getServer().getConsoleSender().sendMessage(SimpleChatHelperMethods.replaceVars(Variables.SendingMessage, vars, replace));
		matchedPlayer.sendMessage(SimpleChatHelperMethods.replaceVars(Variables.ReceivingMessage, vars, replace2));

		for (Player onlinePlayer : Variables.plugin.getServer().getOnlinePlayers())
		{
			if (onlinePlayer == matchedPlayer)
			{
				continue;
			}

			if ((!Variables.perms.has(onlinePlayer, "simplechat.socialspy")) || (Variables.socialSpyOffPM.contains(onlinePlayer)))
				continue;
			onlinePlayer.sendMessage("§8§l[§r§7Console §e-> §7" + matchedPlayer.getName() + "§8§l] §r§6§o" + msg);
		}
	}

	public static void msgToConsole(Player p, String msg)
	{
		String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "+otherpname", "+otherdname", "+otherpre", "+othersuf", "+othergro", "+msg", "&" };
		String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p), SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), "Console", "Console", "", "", "", msg, "§" };

		String[] replace2 = { "Console", "Console", "", "", "", p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p), SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), msg, "§" };

		p.sendMessage(SimpleChatHelperMethods.replaceVars(Variables.SendingMessage, vars, replace));
		Variables.plugin.getServer().getConsoleSender().sendMessage(SimpleChatHelperMethods.replaceVars(Variables.ReceivingMessage, vars, replace2));

		for (Player onlinePlayer : Variables.plugin.getServer().getOnlinePlayers())
		{
			if (onlinePlayer == p)
			{
				continue;
			}

			if ((!Variables.perms.has(onlinePlayer, "simplechat.socialspy")) || (Variables.socialSpyOffPM.contains(onlinePlayer)))
				continue;
			onlinePlayer.sendMessage("§8§l[§r§7" + p.getName() + " §e-> §7" + "Console" + "§8§l] §r§6§o" + msg);
		}
	}
}