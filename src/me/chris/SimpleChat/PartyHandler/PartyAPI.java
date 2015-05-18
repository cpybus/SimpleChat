package me.chris.SimpleChat.PartyHandler;

import java.util.ArrayList;
import java.util.Map.Entry;

import me.chris.SimpleChat.Variables;

import org.bukkit.entity.Player;

public class PartyAPI
{
	//Finds out if player is in a party
	public static boolean isPlayerInAParty(Player p)
	{
		for(Entry<String, Party> entry : Variables.Parties.entrySet())
		{
			ArrayList<String> members = entry.getValue().members;
			for(String member : members)
			{
				if(p.getName().equalsIgnoreCase(member))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	//Finds the party that the person is in.
	public static Party findPartyofPlayer(Player p)
	{
		for(Entry<String, Party> entry : Variables.Parties.entrySet())
		{
			ArrayList<String> members = entry.getValue().members;
			for(String member : members)
			{
				if(p.getName().equalsIgnoreCase(member))
				{
					return entry.getValue();
				}
			}
		}
		
		return null;
	}
}
