package me.chris.SimpleChat.PartyHandler;

import java.util.Map.Entry;

import me.chris.SimpleChat.Variables;

import org.bukkit.entity.Player;

public class PartyJoinLeaveCommand
{
	public static void join(Player p, String party)
	{
		if (Variables.perms.has(p, "simplechat.party.joinparty") || Variables.perms.has(p, "simplechat.party.*")
				|| Variables.perms.has(p, "simplechat.admin") || Variables.perms.has(p, "simplechat.*"))
		{
			if (PartyAPI.isPlayerInAParty(p))
			{
				p.sendMessage("§a[SimpleChat] §4You are already in a party.");
			}
			else
			{
				for (Entry<String, Party> entry : Variables.Parties.entrySet())
				{
					if (entry.getKey().equalsIgnoreCase(party))
					{
						// Has the player been banned from that party?
						if (entry.getValue().hasBannedPlayers())
						{
							for (String player : entry.getValue().bannedplayers)
							{
								if (player.equalsIgnoreCase(p.getName()))
								{
									p.sendMessage("§a[SimpleChat] §4You have been previously banned from that party.");
									return;
								}
							}
						}
						
						// Does the party have a password?
						if (entry.getValue().hasPassword())
						{
							p.sendMessage("§a[SimpleChat] §4That party requires a password.");
							return;
						}
						entry.getValue().sendMessage("§a[SimpleChat] §c" + p.getName() + " §3has joined your party.");
						entry.getValue().addMember(p.getName());
						p.sendMessage("§a[SimpleChat] §3You have been added to the party.");
						Variables.plugin.saveYamls();
						return;
						
					}
				}
				
				p.sendMessage("§a[SimpleChat] §4No party found by that name.");
			}
			
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4You do not have permission to join a party.");
			p.sendMessage("§a[SimpleChat] §4To do this you need: simplechat.party.joinparty");
		}
	}
	
	public static void joinPassword(Player p, String party, String password)
	{
		if (Variables.perms.has(p, "simplechat.party.joinparty") || Variables.perms.has(p, "simplechat.party.*")
				|| Variables.perms.has(p, "simplechat.admin") || Variables.perms.has(p, "simplechat.*"))
		{
			if (PartyAPI.isPlayerInAParty(p))
			{
				p.sendMessage("§a[SimpleChat] §4You are already in a party.");
			}
			else
			{
				for (Entry<String, Party> entry : Variables.Parties.entrySet())
				{
					if (entry.getKey().equalsIgnoreCase(party))
					{
						// Has the player been banned from that party?
						if (entry.getValue().hasBannedPlayers())
						{
							for (String player : entry.getValue().bannedplayers)
							{
								if (player.equalsIgnoreCase(p.getName()))
								{
									p.sendMessage("§a[SimpleChat] §4You have been previously banned from that party.");
									return;
								}
							}
						}
						
						// Does the party have a password?
						if (entry.getValue().hasPassword())
						{
							if (password.equalsIgnoreCase(entry.getValue().password))
							{
								entry.getValue().sendMessage(
										"§a[SimpleChat] §c" + p.getName() + " §3has joined your party.");
								entry.getValue().addMember(p.getName());
								p.sendMessage("§a[SimpleChat] §3You have been added to the party.");
								Variables.plugin.saveYamls();
								return;
							}
							else
							{
								p.sendMessage("§a[SimpleChat] §4That password is incorrect.");
								return;
							}
							
						}
						else
						{
							p.sendMessage("§a[SimpleChat] §4That party does not require a password.");
							return;
						}
						
					}
				}
				
				p.sendMessage("§a[SimpleChat] §4No party found by that name.");
			}
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4You do not have permission to join a party.");
			p.sendMessage("§a[SimpleChat] §4To do this you need: simplechat.party.joinparty");
		}
	}
	
	public static void joinInvisible(Player p, String party)
	{
		if (Variables.perms.has(p, "simplechat.party.joininvisible") || Variables.perms.has(p, "simplechat.party.*")
				|| Variables.perms.has(p, "simplechat.admin") || Variables.perms.has(p, "simplechat.*"))
		{
			for (Entry<String, Party> entry : Variables.Parties.entrySet())
			{
				if (entry.getKey().equalsIgnoreCase(party))
				{
					entry.getValue().addInvisibleMember(p.getName());
					p.sendMessage("§a[SimpleChat] §3You have been discretely added to the party.");
				}
			}
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4You do not have permission to use this command.");
			p.sendMessage("§a[SimpleChat] §4To do this you need: simplechat.party.joininvisible");
		}
	}
	
	public static void leave(Player p)
	{
		if (Variables.perms.has(p, "simplechat.party.leaveparty") || Variables.perms.has(p, "simplechat.party.*")
				|| Variables.perms.has(p, "simplechat.admin") || Variables.perms.has(p, "simplechat.*"))
		{
			if (PartyAPI.isPlayerInAParty(p))
			{
				Party pty = PartyAPI.findPartyofPlayer(p);
				if (!pty.owner.equalsIgnoreCase(p.getName()))
				{
					pty.removeMember(p.getName());
					pty.sendMessage("§a[SimpleChat] §c" + p.getName() + " §3has left your party.");
					
					p.sendMessage("§a[SimpleChat] §3You have been removed from the party.");
					Variables.plugin.saveYamls();
				}
				else
				{
					p.sendMessage("§a[SimpleChat] §4You cannot leave a party you are the owner of.");
					p.sendMessage("§a[SimpleChat] §4Pass on ownership using .");
				}
			}
			else
			{
				p.sendMessage("§a[SimpleChat] §4You are not in a party.");
			}
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4You do not have permission to leave parties.");
			p.sendMessage("§a[SimpleChat] §4To do this you need: simplechat.party.leaveparty");
		}
	}
}
