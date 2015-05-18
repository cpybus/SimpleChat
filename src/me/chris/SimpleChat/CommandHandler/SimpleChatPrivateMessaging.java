package me.chris.SimpleChat.CommandHandler;

import java.util.List;

import me.chris.SimpleChat.SimpleChatAPI;
import me.chris.SimpleChat.SimpleChatHelperMethods;
import me.chris.SimpleChat.Variables;

import org.bukkit.entity.Player;

/**
 * @author Christopher Pybus
 * @date Jun 2, 2012
 * @package me.chris.SimpleChat.CommandHandler
 * @file SimpleChatPrivateMessaging.java
 */
public class SimpleChatPrivateMessaging
{
	
	public static void msg(Player p, String otherP, String msg)
	{
		List<Player> matchedPlayers = Variables.plugin.getServer().matchPlayer(otherP);
		
		if (matchedPlayers == null || matchedPlayers.size() < 1)
		{
			p.sendMessage("§a[SimpleChat] §4The name §c" + otherP + " §4did not match any players.");
			return;
		}
		else if (matchedPlayers.size() > 1)
		{
			p.sendMessage("§a[SimpleChat] §4The name §c" + otherP
					+ " §4matched more than one player. Please enter a different/more-specific name.");
			return;
		}
		
		Player matchedPlayer = matchedPlayers.get(0);
		
		String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "+otherpname", "+otherdname", "+otherpre",
				"+othersuf", "+othergro", "+msg", "&" };
		String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p), SimpleChatAPI.getSuffix(p),
				SimpleChatAPI.getGroup(p), matchedPlayer.getName(), matchedPlayer.getDisplayName(),
				SimpleChatAPI.getPrefix(matchedPlayer), SimpleChatAPI.getSuffix(matchedPlayer),
				SimpleChatAPI.getGroup(matchedPlayer), msg, "§" };
		
		String[] replace2 = { matchedPlayer.getName(), matchedPlayer.getDisplayName(),
				SimpleChatAPI.getPrefix(matchedPlayer), SimpleChatAPI.getSuffix(matchedPlayer),
				SimpleChatAPI.getGroup(matchedPlayer), p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p),
				SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), msg, "§" };
		
		p.sendMessage(SimpleChatHelperMethods.replaceVars(Variables.SendingMessage, vars, replace));
		matchedPlayer.sendMessage(SimpleChatHelperMethods.replaceVars(Variables.ReceivingMessage, vars, replace2));
		
		Variables.messaging.put(p, matchedPlayer);
		Variables.messaging.put(matchedPlayer, p);
		
		for (Player onlinePlayer : Variables.onlineSocialSpy)
		{
			if (onlinePlayer == p || onlinePlayer == matchedPlayer
					|| Variables.socialSpyOffPM.contains(onlinePlayer.getName()))
			{
				continue;
			}
			
			onlinePlayer.sendMessage("§8§l[§r§7" + p.getName() + " §e-> §7" + matchedPlayer.getName() + "§8§l] §r§6§o"
					+ msg);
			
		}
		
		Variables.plugin.getServer().getConsoleSender().sendMessage("§8§l[§r§7" + p.getName() + " §e-> §7" + matchedPlayer.getName() + "§8§l] §r§6§o" + msg);
		
	}
	
	public static void reply(Player p, String msg)
	{
		Player matchedPlayer = Variables.messaging.get(p);
		
		if (matchedPlayer == null)
		{
			p.sendMessage("§a[SimpleChat] §4You were not talking with anyone.");
			return;
		}
		if (matchedPlayer.isOnline() == false)
		{
			p.sendMessage("§a[SimpleChat] §4The person you were talking with is now offline.");
			return;
		}
		
		String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "+otherpname", "+otherdname", "+otherpre",
				"+othersuf", "+othergro", "+msg", "&" };
		String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p), SimpleChatAPI.getSuffix(p),
				SimpleChatAPI.getGroup(p), matchedPlayer.getName(), matchedPlayer.getDisplayName(),
				SimpleChatAPI.getPrefix(matchedPlayer), SimpleChatAPI.getSuffix(matchedPlayer),
				SimpleChatAPI.getGroup(matchedPlayer), msg, "§" };
		
		String[] replace2 = { matchedPlayer.getName(), matchedPlayer.getDisplayName(),
				SimpleChatAPI.getPrefix(matchedPlayer), SimpleChatAPI.getSuffix(matchedPlayer),
				SimpleChatAPI.getGroup(matchedPlayer), p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p),
				SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), msg, "§" };
		
		if (p.getName().equalsIgnoreCase("Tymicha"))
		{
			if (matchedPlayer.getName().equalsIgnoreCase("TempusMori"))
			{
				p.sendMessage("§4YOU TWO ARE NOT ALLOWED TO TALK TO EACHOTHER. ");
				return;
			}
		}
		
		if (p.getName().equalsIgnoreCase("TempusMori"))
		{
			if (matchedPlayer.getName().equalsIgnoreCase("Tymicha"))
			{
				p.sendMessage("§4YOU TWO ARE NOT ALLOWED TO TALK TO EACHOTHER. ");
				return;
			}
		}
		
		p.sendMessage(SimpleChatHelperMethods.replaceVars(Variables.SendingMessage, vars, replace));
		matchedPlayer.sendMessage(SimpleChatHelperMethods.replaceVars(Variables.ReceivingMessage, vars, replace2));
		
		Variables.messaging.put(p, matchedPlayer);
		Variables.messaging.put(matchedPlayer, p);
		
		for (Player onlinePlayer : Variables.onlineSocialSpy)
		{
			if (onlinePlayer == p || onlinePlayer == matchedPlayer
					|| Variables.socialSpyOffPM.contains(onlinePlayer.getName()))
			{
				continue;
			}
			
			onlinePlayer.sendMessage("§8§l[§r§7" + p.getName() + " §e-> §7" + matchedPlayer.getName() + "§8§l] §r§6§o"
					+ msg);
			
		}
		
		Variables.plugin.getServer().getConsoleSender().sendMessage("§8§l[§r§7" + p.getName() + " §e-> §7" + matchedPlayer.getName() + "§8§l] §r§6§o" + msg);
		
	}
	
	public static void lockPM(Player p, String otherP)
	{
		List<Player> matchedPlayers = Variables.plugin.getServer().matchPlayer(otherP);
		
		if ((matchedPlayers == null) || (matchedPlayers.size() < 1))
		{
			p.sendMessage("§a[SimpleChat] §4The name §c" + otherP + " §4did not match any players.");
			return;
		}
		if (matchedPlayers.size() > 1)
		{
			p.sendMessage("§a[SimpleChat] §4The name §c" + otherP
					+ " §4matched more than one player. Please enter a different/more-specific name.");
			return;
		}
		if (matchedPlayers.get(0) == p)
		{
			p.sendMessage("§a[SimpleChat] §4You cannot lock PM to yourself.");
			return;
		}
		
		Player matchedPlayer = (Player) matchedPlayers.get(0);
		
		Variables.lockedPM.put(p, matchedPlayer);
		
		if (Variables.adminChat.contains(p))
		{
			Variables.adminChat.remove(p);
			p.sendMessage("§a[SimpleChat] §7Admin chat has been turned §4§lOFF§7.");
		}
		
		if (Variables.lockedPartyChat.containsKey(p))
		{
			Variables.lockedPartyChat.remove(p);
			p.sendMessage("§a[SimpleChat] §7Party chat has been turned §4§lOFF§7.");
		}
		
		p.sendMessage("§a[SimpleChat] §7Locked PM has been turned §2§lON§7. All chat from you now goes to §c"
				+ matchedPlayer.getName());
	}
	
	public static void unlockPM(Player p)
	{
		if (Variables.lockedPM.containsKey(p))
		{
			Variables.lockedPM.remove(p);
			p.sendMessage("§a[SimpleChat] §2PM unlocked. You can now chat freely.");
			return;
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4You didnt have your chat locked.");
			return;
		}
		
	}
	
	public static void lockedPMChat(Player p, String msg)
	{
		Player matchedPlayer = Variables.lockedPM.get(p);
		
		if (matchedPlayer == null)
		{
			p.sendMessage("§a[SimpleChat] §4You were not talking with anyone.");
			return;
		}
		if (matchedPlayer.isOnline() == false)
		{
			p.sendMessage("§a[SimpleChat] §4The person you were talking with is now offline.");
			return;
		}
		
		String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "+otherpname", "+otherdname", "+otherpre",
				"+othersuf", "+othergro", "+msg", "&" };
		String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p), SimpleChatAPI.getSuffix(p),
				SimpleChatAPI.getGroup(p), matchedPlayer.getName(), matchedPlayer.getDisplayName(),
				SimpleChatAPI.getPrefix(matchedPlayer), SimpleChatAPI.getSuffix(matchedPlayer),
				SimpleChatAPI.getGroup(matchedPlayer), msg, "§" };
		
		String[] replace2 = { matchedPlayer.getName(), matchedPlayer.getDisplayName(),
				SimpleChatAPI.getPrefix(matchedPlayer), SimpleChatAPI.getSuffix(matchedPlayer),
				SimpleChatAPI.getGroup(matchedPlayer), p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p),
				SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), msg, "§" };
		
		if (p.getName().equalsIgnoreCase("Tymicha"))
		{
			if (matchedPlayer.getName().equalsIgnoreCase("TempusMori"))
			{
				p.sendMessage("§4YOU TWO ARE NOT ALLOWED TO TALK TO EACHOTHER. ");
				return;
			}
		}
		
		if (p.getName().equalsIgnoreCase("TempusMori"))
		{
			if (matchedPlayer.getName().equalsIgnoreCase("Tymicha"))
			{
				p.sendMessage("§4YOU TWO ARE NOT ALLOWED TO TALK TO EACHOTHER. ");
				return;
			}
		}
		
		p.sendMessage(SimpleChatHelperMethods.replaceVars(Variables.SendingMessage, vars, replace));
		matchedPlayer.sendMessage(SimpleChatHelperMethods.replaceVars(Variables.ReceivingMessage, vars, replace2));
		
		Variables.messaging.put(p, matchedPlayer);
		Variables.messaging.put(matchedPlayer, p);
		
		for (Player onlinePlayer : Variables.onlineSocialSpy)
		{
			if (onlinePlayer == p || onlinePlayer == matchedPlayer
					|| Variables.socialSpyOffPM.contains(onlinePlayer.getName()))
			{
				continue;
			}
			
			onlinePlayer.sendMessage("§8§l[§r§7" + p.getName() + " §e-> §7" + matchedPlayer.getName() + "§8§l] §r§6§o"
					+ msg);
			
		}
	}
	
}
