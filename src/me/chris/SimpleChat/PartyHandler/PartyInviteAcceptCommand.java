package me.chris.SimpleChat.PartyHandler;

import java.util.List;
import java.util.Map.Entry;

import me.chris.SimpleChat.Variables;

import org.bukkit.entity.Player;

public class PartyInviteAcceptCommand
{
	public static void invite(Player p, String otherP)
	{
		if (Variables.perms.has(p, "simplechat.party.invite") || Variables.perms.has(p, "simplechat.party.*")
				|| Variables.perms.has(p, "simplechat.party.admin") || Variables.perms.has(p, "simplechat.*"))
			if (PartyAPI.isPlayerInAParty(p))
			{
				Party pty = PartyAPI.findPartyofPlayer(p);
				
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
				
				if (pty.hasBannedPlayers())
				{
					for (String bannedP : pty.bannedplayers)
					{
						if (bannedP.equalsIgnoreCase(matchedPlayer.getName()))
						{
							p.sendMessage("§a[SimpleChat] §4That player has been banned from this party.");
							return;
						}
					}
				}
				
				if (PartyAPI.isPlayerInAParty(matchedPlayer))
				{
					p.sendMessage("§a[SimpleChat] §4That player is already in a party.");
					return;
				}
				
				Variables.inviteToParty.put(matchedPlayer, pty);
				
				matchedPlayer.sendMessage("§a[SimpleChat] §3You have been invited to the §c" + pty.partyName
						+ "§3 by §c" + otherP + "§3. To accept this invitation, type §c/party accept");
				p.sendMessage("§a[SimpleChat] §3Your invite to §c" + otherP + "§3 was sent.");
			}
			else
			{
				p.sendMessage("§a[SimpleChat] §4You are currently §onot §r§ain a party!");
			}
		else
		{
			p.sendMessage("§a[SimpleChat] §4You do not have permission to send an invite.");
			p.sendMessage("§a[SimpleChat] §4To do this you need: simplechat.party.invite");
		}
		
	}
	
	public static void accept(Player p)
	{
		if (Variables.perms.has(p, "simplechat.party.accept") || Variables.perms.has(p, "simplechat.party.*")
				|| Variables.perms.has(p, "simplechat.party.admin") || Variables.perms.has(p, "simplechat.*"))
		{
			for (Entry<Player, Party> entry : Variables.inviteToParty.entrySet())
			{
				if (entry.getKey().equals(p))
				{
					Party newParty = Variables.Parties.get(entry.getValue().partyName);
					entry.getValue().sendMessage("§a[SimpleChat] §c" + p.getName() + " §3has joined your party.");
					newParty.addMember(p.getName());
					Variables.Parties.put(newParty.partyName, newParty);
					p.sendMessage("§a[SimpleChat] §3You have been added to the party. Type /p to chat.");
					Variables.plugin.saveYamls();
					return;
				}
			}
			
			p.sendMessage("§a[SimpleChat] §4You were not invited to any party.");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4You do not have permission to accept an invite.");
			p.sendMessage("§a[SimpleChat] §4To do this you need: simplechat.party.accept");
		}
		
	}
}
