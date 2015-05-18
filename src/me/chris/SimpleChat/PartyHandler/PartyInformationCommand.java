package me.chris.SimpleChat.PartyHandler;

import java.util.Map.Entry;

import org.bukkit.entity.Player;

import me.chris.SimpleChat.Variables;

public class PartyInformationCommand
{
	public static void info(Player p)
	{
		p.sendMessage("§5=====================================================");
		p.sendMessage("§a Welcome to the Party system provided by §cSimpleChat");
		p.sendMessage("§a Designed and Programmed by §9Hotshot2162");
		if (PartyAPI.isPlayerInAParty(p))
		{
			Party pty = PartyAPI.findPartyofPlayer(p);
			p.sendMessage("§a You are currently in the §c" + pty.partyName + "§a party:");
			if (pty.hasPassword())
			{
				p.sendMessage("§e  Password: §2" + pty.password);
			}
			else
			{
				p.sendMessage("§e  Password: §2N/A");
				
			}
			if (pty.hasMembers())
			{
				p.sendMessage("§e  Members: §2");
				for (String member : pty.members)
				{
					Player partyMember = Variables.plugin.getServer().getPlayer(member);
					if (partyMember != null && partyMember.isOnline())
					{
						p.sendMessage("§2  - " + member + "§a (Online)");
					}
					else
					{
						p.sendMessage("§2  - " + member);
					}
				}
			}
			else
			{
				p.sendMessage("§e  Members: §2N/A");
				
			}
			if (pty.hasBannedPlayers())
			{
				p.sendMessage("§e  Banned Players: §2");
				for (String member : pty.bannedplayers)
				{
					Player partyMember = Variables.plugin.getServer().getPlayer(member);
					if (partyMember != null && partyMember.isOnline())
					{
						p.sendMessage("§2  - " + member + "§a (Online)");
					}
					else
					{
						p.sendMessage("§2  - " + member);
					}
				}
			}
			else
			{
				p.sendMessage("§e  Banned Players: §2None");
				
			}
			
		}
		else
		{
			p.sendMessage("§a You are currently §onot §r§ain a party! Try: §c/party list");
		}
		
		p.sendMessage("§5=====================================================");
	}
	
	public static void specificInfo(Player p, String party)
	{
		for (Entry<String, Party> entry : Variables.Parties.entrySet())
		{
			if (entry.getKey().equalsIgnoreCase(party))
			{
				Party pty = entry.getValue();
				
				if (Variables.perms.has(p, "simplechat.party.*") || Variables.perms.has(p, "simplechat.party.admin")
						|| Variables.perms.has(p, "simplechat.*"))
				{
					p.sendMessage("§5=====================§c [ Party List ] §5====================");
					
					String name = entry.getKey();
					p.sendMessage("§c" + name);
					p.sendMessage("  §eOwner:§2 " + pty.owner);

					if (pty.hasPassword())
					{
						p.sendMessage("§e  Password: §2" + pty.password);
					}
					else
					{
						p.sendMessage("§e  Password: §2N/A");
						
					}
					
					if (pty.hasMembers())
					{
						p.sendMessage("  §eMembers: ");
						for (String member : pty.members)
						{
							Player partyMember = Variables.plugin.getServer().getPlayer(member);
							if (partyMember != null && partyMember.isOnline())
							{
								p.sendMessage("§2  - " + member + "§a (Online)");
							}
							else
							{
								p.sendMessage("§2  - " + member);
							}
						}
					}
					else
					{
						p.sendMessage("  §eMembers: §2None");
					}
					
					if (pty.hasBannedPlayers())
					{
						p.sendMessage("  §eBanned Members: ");
						for (String member : pty.bannedplayers)
						{
							Player partyMember = Variables.plugin.getServer().getPlayer(member);
							if (partyMember != null && partyMember.isOnline())
							{
								p.sendMessage("§2  - " + member + "§a (Online)");
							}
							else
							{
								p.sendMessage("§2  - " + member);
							}
						}
					}
					else
					{
						p.sendMessage("  §eBanned Members: §2None");
					}
					p.sendMessage("§5=====================================================");
				}
				else if (Variables.perms.has(p, "simplechat.party.infoOther"))
				{
					p.sendMessage("§5=====================§c [ Party List ] §5====================");
					String name = entry.getKey();
					p.sendMessage("§c" + name);
					p.sendMessage("  §eOwner:§2 " + pty.owner);
					p.sendMessage("  §ePasswordProtected:§2 " + pty.hasPassword());
					
					if (pty.hasMembers())
					{
						p.sendMessage("  §e# of Members: " + pty.members.size());
					}
					else
					{
						p.sendMessage("  §e# of Members: §20");
					}
					
					p.sendMessage("§5=====================================================");
				}
				
				else
				{
					p.sendMessage("§a[SimpleChat] §4You do not have permission to view specific information about party.");
					p.sendMessage("§a[SimpleChat] §4To do this you need: simplechat.party.infoOther");
				}
			}
			else
			{
				continue;
			}
			return;
		}
		
		p.sendMessage("§a[SimpleChat] §4The §c" + party + " §4party was not found.");
	}
}
