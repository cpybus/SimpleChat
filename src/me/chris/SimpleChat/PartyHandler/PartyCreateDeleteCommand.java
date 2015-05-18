package me.chris.SimpleChat.PartyHandler;

import java.util.ArrayList;
import java.util.Map.Entry;

import me.chris.SimpleChat.Variables;

import org.bukkit.entity.Player;

public class PartyCreateDeleteCommand
{
	// String partyName, String owner, String password, ArrayList<String>
	// members, ArrayList<String> bannedplayers
	public static void createParty(Player creator, String name)
	{
		if (PartyAPI.isPlayerInAParty(creator))
		{
			creator.sendMessage("§a[SimpleChat] §4You are already in a party.");
		}
		else
		{
			if (Variables.perms.has(creator, "simplechat.party.createparty.normal")
					|| Variables.perms.has(creator, "simplechat.party.createparty.*")
					|| Variables.perms.has(creator, "simplechat.party.admin")
					|| Variables.perms.has(creator, "simplechat.party.*")
					|| Variables.perms.has(creator, "simplechat.*"))
			{
				ArrayList<String> members = new ArrayList<String>();
				ArrayList<String> bannedplayers = new ArrayList<String>();
				for (Entry<String, Party> entry : Variables.Parties.entrySet())
				{
					if (entry.getKey().equalsIgnoreCase(name))
					{
						creator.sendMessage("§a[SimpleChat] §4That party name already exists.");
						return;
					}
					
				}
				Party pty = new Party(name, creator.getName(), "", members, bannedplayers);
				Variables.Parties.put(name, pty);
				creator.sendMessage("§a[SimpleChat] §3Created the §c" + name + "§3 party");
				Variables.plugin.saveYamls();
			}
			else
			{
				creator.sendMessage("§a[SimpleChat] §4You do not have permission to create a party.");
				creator.sendMessage("§a[SimpleChat] §4To do this you need: simplechat.party.createparty.normal");
			}
		}
		
	}
	
	public static void createParty(Player creator, String name, String password)
	{
		if (PartyAPI.isPlayerInAParty(creator))
		{
			creator.sendMessage("§a[SimpleChat] §4You are already in a party.");
		}
		else
		{
			if (Variables.perms.has(creator, "simplechat.party.createparty.pass")
					|| Variables.perms.has(creator, "simplechat.party.createparty.*")
					|| Variables.perms.has(creator, "simplechat.party.admin")
					|| Variables.perms.has(creator, "simplechat.party.*")
					|| Variables.perms.has(creator, "simplechat.*"))
			{
				ArrayList<String> members = new ArrayList<String>();
				ArrayList<String> bannedplayers = new ArrayList<String>();
				for (Entry<String, Party> entry : Variables.Parties.entrySet())
				{
					if (entry.getKey().equalsIgnoreCase(name))
					{
						creator.sendMessage("§a[SimpleChat] §4That party name already exists.");
						return;
					}
					
				}
				Party pty = new Party(name, creator.getName(), password, members, bannedplayers);
				Variables.Parties.put(name, pty);
				creator.sendMessage("§a[SimpleChat] §3Created the §c" + name + "§3 party");
				Variables.plugin.saveYamls();
			}
			else
			{
				creator.sendMessage("§a[SimpleChat] §4You do not have permission to create a password-protected party.");
				creator.sendMessage("§a[SimpleChat] §4To do this you need: simplechat.party.createparty.pass");
			}
		}
	}
	
	public static void deleteParty(Player p)
	{
		if (PartyAPI.isPlayerInAParty(p))
		{
			Party pty = PartyAPI.findPartyofPlayer(p);
			if (pty.owner == p.getName())
			{
				Variables.Parties.remove(pty.partyName);
				p.sendMessage("§a[SimpleChat] §3Deleted the §c" + pty.partyName + " §3party successfully");
				Variables.plugin.saveYamls();
				
			}
			else
			{
				if (Variables.perms.has(p, "simplechat.party.deleteparty")
						|| Variables.perms.has(p, "simplechat.party.admin")
						|| Variables.perms.has(p, "simplechat.party.*") || Variables.perms.has(p, "simplechat.*"))
				{
					Variables.Parties.remove(pty.partyName);
					p.sendMessage("§a[SimpleChat] §3Deleted the §c" + pty.partyName + " §3party successfully");
					Variables.plugin.saveYamls();
				}
				else
				{
					p.sendMessage("§a[SimpleChat] §4You do not have permission to delete this party.");
					p.sendMessage("§a[SimpleChat] §4To do this you need: simplechat.party.deleteparty.any");
				}
			}
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4You are not in a party.");
		}
	}
	
	public static void deleteParty(Player p, String party)
	{
		for (Entry<String, Party> entry : Variables.Parties.entrySet())
		{
			if (entry.getKey().equalsIgnoreCase(party))
			{
				Party pty = entry.getValue();
				
				if (Variables.perms.has(p, "simplechat.party.deleteparty")
						|| Variables.perms.has(p, "simplechat.party.admin")
						|| Variables.perms.has(p, "simplechat.party.*") || Variables.perms.has(p, "simplechat.*"))
				{
					Variables.Parties.remove(pty.partyName);
					Variables.plugin.saveYamls();
					p.sendMessage("§a[SimpleChat] §3Deleted the §c" + pty.partyName + " §3party successfully");
					
				}
				else
				{
					p.sendMessage("§a[SimpleChat] §4You do not have permission to delete any party.");
					p.sendMessage("§a[SimpleChat] §4To do this you need: simplechat.party.deleteparty.any");
				}
				
				return;
			}
		}
		
		p.sendMessage("§a[SimpleChat] §4The §c" + party + " §4party was not found.");
		
	}
}
