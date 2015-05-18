package me.chris.SimpleChat.PartyHandler;

import me.chris.SimpleChat.Variables;

import org.bukkit.entity.Player;

public class PartyKickBanCommand
{
	public static void kick(Player p, String otherP)
	{
		if (PartyAPI.isPlayerInAParty(p))
		{
			Party pty = PartyAPI.findPartyofPlayer(p);
			if (pty.owner.equalsIgnoreCase(p.getName()))
			{
				if (otherP.equalsIgnoreCase(p.getName()))
				{
					p.sendMessage("§a[SimpleChat] §4You cannoy kick yourself from the party");
				}
				else
				{
					if (pty.removeMember(otherP))
					{
						Player partyMember = Variables.plugin.getServer().getPlayer(otherP);
						if (partyMember != null && partyMember.isOnline())
						{
							partyMember.sendMessage("§a[SimpleChat] §6You have been kicked from the party.");
							
						}
						
						pty.sendMessage("§a[SimpleChat] §4" + otherP + " §6has been kicked from the party.");
						
					}
					else
					{
						p.sendMessage("§a[SimpleChat] §4Could not find §c" + otherP + " §4in the party.");
					}
				}
				
			}
			else
			{
				if (Variables.perms.has(p, "simplechat.party.kickplayer")
						|| Variables.perms.has(p, "simplechat.party.admin")
						|| Variables.perms.has(p, "simplechat.party.*") || Variables.perms.has(p, "simplechat.*"))
				{
					if (pty.isOwner(otherP))
					{
						p.sendMessage("§a[SimpleChat] §4You cannot kick the owner of a party.");
					}
					else
					{
						if (pty.removeMember(otherP))
						{
							Player partyMember = Variables.plugin.getServer().getPlayer(otherP);
							if (partyMember != null && partyMember.isOnline())
							{
								partyMember.sendMessage("§a[SimpleChat] §6You have been kicked from the party.");
								
							}
							
							pty.sendMessage("§a[SimpleChat] §4" + otherP + " §6has been kicked from the party.");
							
						}
						else
						{
							p.sendMessage("§a[SimpleChat] §4Could not find §c" + otherP + " §4in the party.");
						}
					}
					
				}
				else
				{
					p.sendMessage("§a[SimpleChat] §4You are not owner of this party.");
				}
			}
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4You are not in a party.");
		}
	}
	
	public static void ban(Player p, String otherP)
	{
		if (PartyAPI.isPlayerInAParty(p))
		{
			Party pty = PartyAPI.findPartyofPlayer(p);
			if (pty.owner.equalsIgnoreCase(p.getName()))
			{
				if (otherP.equalsIgnoreCase(p.getName()))
				{
					p.sendMessage("§a[SimpleChat] §4You cannoy ban yourself from the party");
				}
				else
				{
					if (pty.removeMember(otherP))
					{
						pty.banMember(otherP);
						
						Player partyMember = Variables.plugin.getServer().getPlayer(otherP);
						if (partyMember != null && partyMember.isOnline())
						{
							partyMember.sendMessage("§a[SimpleChat] §6You have been banned from the party.");
							
						}
						
						pty.sendMessage("§a[SimpleChat] §4" + otherP + " §6has been banned from the party.");
						Variables.plugin.saveYamls();
						
					}
					else
					{
						p.sendMessage("§a[SimpleChat] §4You do not have permission to ban people from your own party. Ask an admin.");
						p.sendMessage("§a[SimpleChat] §4To do this you need: simplechat.party.banplayer.own");
					}
				}
			}
			else
			{
				if (Variables.perms.has(p, "simplechat.party.banplayer")
						|| Variables.perms.has(p, "simplechat.party.admin")
						|| Variables.perms.has(p, "simplechat.party.*") || Variables.perms.has(p, "simplechat.*"))
				{
					if (pty.isOwner(otherP))
					{
						p.sendMessage("§a[SimpleChat] §4You cannot ban the owner of a party.");
					}
					else
					{
						if (pty.removeMember(otherP))
						{
							pty.banMember(otherP);
							
							Player partyMember = Variables.plugin.getServer().getPlayer(otherP);
							if (partyMember != null && partyMember.isOnline())
							{
								partyMember.sendMessage("§a[SimpleChat] §6You have been banned from the party.");
								
							}
							
							pty.sendMessage("§a[SimpleChat] §4" + otherP + " §6has been banned from the party.");
							
							Variables.plugin.saveYamls();
							
						}
						else
						{
							p.sendMessage("§a[SimpleChat] §4Could not find §c" + otherP + " §4in the party.");
						}
					}
					
				}
				else
				{
					p.sendMessage("§a[SimpleChat] §4You are not owner of this party.");
				}
			}
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4You are not in a party.");
		}
	}
	
	public static void unban(Player p, String otherP)
	{
		if (PartyAPI.isPlayerInAParty(p))
		{
			Party pty = PartyAPI.findPartyofPlayer(p);
			if (pty.owner.equalsIgnoreCase(p.getName()))
			{
				if (otherP.equalsIgnoreCase(p.getName()))
				{
					p.sendMessage("§a[SimpleChat] §4You cannot unban yourself from the party");
				}
				else
				{
					if (pty.removeMember(otherP))
					{
						pty.banMember(otherP);
						
						Player partyMember = Variables.plugin.getServer().getPlayer(otherP);
						if (partyMember != null && partyMember.isOnline())
						{
							partyMember.sendMessage("§a[SimpleChat] §6You have been banned from the party.");
							
						}
						
						pty.sendMessage("§a[SimpleChat] §4" + otherP + " §6has been banned from the party.");
						Variables.plugin.saveYamls();
						
					}
					else
					{
						p.sendMessage("§a[SimpleChat] §4You do not have permission to ban people from your own party. Ask an admin.");
						p.sendMessage("§a[SimpleChat] §4To do this you need: simplechat.party.banplayer.own");
					}
				}
			}
			else
			{
				if (Variables.perms.has(p, "simplechat.party.unbanplayer")
						|| Variables.perms.has(p, "simplechat.party.admin")
						|| Variables.perms.has(p, "simplechat.party.*") || Variables.perms.has(p, "simplechat.*"))
				{
					if (pty.isOwner(otherP))
					{
						p.sendMessage("§a[SimpleChat] §4You cannot ban the owner of a party.");
					}
					else
					{
						if (pty.removeMember(otherP))
						{
							pty.banMember(otherP);
							
							Player partyMember = Variables.plugin.getServer().getPlayer(otherP);
							if (partyMember != null && partyMember.isOnline())
							{
								partyMember.sendMessage("§a[SimpleChat] §6You have been banned from the party.");
								
							}
							
							pty.sendMessage("§a[SimpleChat] §4" + otherP + " §6has been banned from the party.");
							
							Variables.plugin.saveYamls();
							
						}
						else
						{
							p.sendMessage("§a[SimpleChat] §4Could not find §c" + otherP + " §4in the party.");
						}
					}
					
				}
				else
				{
					p.sendMessage("§a[SimpleChat] §4You are not owner of this party.");
				}
			}
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4You are not in a party.");
		}
	}
}
