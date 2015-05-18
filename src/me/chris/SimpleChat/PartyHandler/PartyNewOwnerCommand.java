package me.chris.SimpleChat.PartyHandler;

import me.chris.SimpleChat.Variables;

import org.bukkit.entity.Player;

public class PartyNewOwnerCommand
{
	public static void newOwner(Player p, String otherP)
	{
		if (PartyAPI.isPlayerInAParty(p))
		{
			Party pty = PartyAPI.findPartyofPlayer(p);
			if (pty.owner.equalsIgnoreCase(p.getName()))
			{
				
				for (String mem : pty.members)
				{
					if (mem.equalsIgnoreCase(otherP))
					{
						Player partyMember = Variables.plugin.getServer().getPlayer(otherP);
						
						if (partyMember != null && partyMember.isOnline())
						{
							partyMember.sendMessage("§a[SimpleChat] §6You have been appointed owner of the "
									+ "party and the previous owner has stepped down.");
							
							
						}
						
						p.sendMessage("§a[SimpleChat] §6You are no longer owner of this party.");
						
						pty.owner = mem;
						Variables.plugin.saveYamls();
						return;
					}
				}
				
				p.sendMessage("§a[SimpleChat] §4Could not find §c" + otherP + " §4in the party.");
				
			}
			
			else
			{
				p.sendMessage("§a[SimpleChat] §4You are not owner of this party.");
			}
			
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4You are not in a party.");
		}
	}
}
