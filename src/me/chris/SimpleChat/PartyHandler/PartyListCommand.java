package me.chris.SimpleChat.PartyHandler;

import java.util.Map.Entry;

import org.bukkit.entity.Player;

import me.chris.SimpleChat.Variables;

public class PartyListCommand
{
	public static void list(Player p)
	{
		if (Variables.perms.has(p, "simplechat.party.list") || Variables.perms.has(p, "simplechat.party.*")
				|| Variables.perms.has(p, "simplechat.party.admin") || Variables.perms.has(p, "simplechat.*"))
		{
			p.sendMessage("§5=====================§c [ Party List ] §5====================");
			for (Entry<String, Party> entry : Variables.Parties.entrySet())
			{
				String name = entry.getKey();
				p.sendMessage("§c " + name);
				
			}
			p.sendMessage("§b§oGet more information for each party by doing:");
			p.sendMessage("§b§o/party info [partyname]");
			p.sendMessage("§5=====================================================");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4You do not have permission to list all parties.");
			p.sendMessage("§a[SimpleChat] §4To do this you need: simplechat.party.list");
		}
	}
}
