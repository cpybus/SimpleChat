package me.chris.SimpleChat.PartyHandler;

import java.util.ArrayList;

import me.chris.SimpleChat.SimpleChatAPI;
import me.chris.SimpleChat.SimpleChatHelperMethods;
import me.chris.SimpleChat.Variables;

import org.bukkit.entity.Player;

public class PartyChat
{
	
	public static void togglePartyChat(Player p)
	{
		if (!Variables.lockedPartyChat.containsKey(p))
		{
			if (PartyAPI.isPlayerInAParty(p))
			{
				if (Variables.lockedPM.containsKey(p))
				{
					Variables.lockedPM.remove(p);
					p.sendMessage("§a[SimpleChat] §7Locked PM has been turned §4§lOFF§7.");
				}
				
				if (Variables.adminChat.contains(p))
				{
					Variables.adminChat.remove(p);
					p.sendMessage("§a[SimpleChat] §7Admin chat has been turned §4§lOFF§7.");
				}
				
				Variables.lockedPartyChat.put(p, PartyAPI.findPartyofPlayer(p));
				p.sendMessage("§a[SimpleChat] §7Party chat has been turned §2§lON§7.");
			}
			else
			{
				p.sendMessage("§a[SimpleChat] §4You are not in a party.");
			}
			
		}
		else
		{
			Variables.lockedPartyChat.remove(p);
			p.sendMessage("§a[SimpleChat] §7Party chat has been turned §4§lOFF§7.");
		}
		
	}
	
	public static void partyChat(Player p, String msg)
	{
		if (PartyAPI.isPlayerInAParty(p))
		{
			Party pty = PartyAPI.findPartyofPlayer(p);
			ArrayList<Player> onlineMembers = pty.onlineMembers;
			
			//Regular members
			for (Player member : onlineMembers)
			{
				if (member == null)
				{
					continue;
				}
				
				if (!member.isOnline())
				{
					pty.removeOnlineMember(member);
					continue;
				}
				
				String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "+msg", "+partyname", "&" };
				String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p),
						SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), msg,
						PartyAPI.findPartyofPlayer(p).partyName, "§" };
				
				String chat = SimpleChatHelperMethods.replaceVars(Variables.PartyChatFormat, vars, replace);
				
				member.sendMessage(chat);
				
			}
			
			// Invisible Members
			ArrayList<String> invisibleMembers = pty.invisibleMembers;
			for (String member : invisibleMembers)
			{
				if (member.equalsIgnoreCase(p.getName()))
				{
					continue;
				}
				
				Player partyMember = Variables.plugin.getServer().getPlayer(member);
				if (partyMember != null && partyMember.isOnline())
				{
					String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "+msg", "+partyname", "&" };
					String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p),
							SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), msg,
							PartyAPI.findPartyofPlayer(p).partyName, "§" };
					
					String chat = SimpleChatHelperMethods.replaceVars(Variables.PartyChatFormat, vars, replace);
					
					partyMember.sendMessage(chat);
				}
			}
			
			for (Player onlineSocialSpy : Variables.onlineSocialSpy)
			{
				if (onlineSocialSpy == p)
				{
					continue;
				}
				
				if(pty.members.contains(onlineSocialSpy.getName()))
				{
					continue;
				}
				
				if(Variables.socialSpyOffParty.contains(onlineSocialSpy.getName()))
				{
					continue;
				}
				
				onlineSocialSpy.sendMessage("§8§l[§7" + pty.partyName + "§8§l] §7" + p.getName() + ": §e§o" + msg);
			}
			
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4You are not in a party.");
		}
	}
	
}
