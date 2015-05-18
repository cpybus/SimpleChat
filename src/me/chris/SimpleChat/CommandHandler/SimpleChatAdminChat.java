package me.chris.SimpleChat.CommandHandler;

import me.chris.SimpleChat.SimpleChatAPI;
import me.chris.SimpleChat.SimpleChatHelperMethods;
import me.chris.SimpleChat.Variables;
import org.bukkit.entity.Player;

public class SimpleChatAdminChat
{
	public static void toggleAdminChat(Player p)
	{
		
		if (!Variables.adminChat.contains(p))
		{
			if (Variables.lockedPM.containsKey(p))
			{
				Variables.lockedPM.remove(p);
				p.sendMessage("§a[SimpleChat] §7Locked PM has been turned §4§lOFF§7.");
			}
			
			if(Variables.lockedPartyChat.containsKey(p))
			{
				Variables.lockedPartyChat.remove(p);
				p.sendMessage("§a[SimpleChat] §7Party chat has been turned §4§lOFF§7.");
			}

			Variables.adminChat.add(p);
			p.sendMessage("§a[SimpleChat] §7Admin chat has been turned §2§lON§7.");
		}
		else
		{
			Variables.adminChat.remove(p);
			p.sendMessage("§a[SimpleChat] §7Admin chat has been turned §4§lOFF§7.");
		}
	}

	public static void adminChat(Player p, String msg)
	{
		String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "+msg", "&" };
		String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p), SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), msg, "§" };

		String chat = SimpleChatHelperMethods.replaceVars(Variables.AdminChatFormat, vars, replace);
		
		for (Player player : Variables.onlineAdminChat)
		{
			if(player != null && player.isOnline())
			{
				player.sendMessage(chat);
			}
			else
			{
				Variables.onlineAdminChat.remove(player);
			}
		}
		
		Variables.plugin.getServer().getConsoleSender().sendMessage(chat);
	}
}