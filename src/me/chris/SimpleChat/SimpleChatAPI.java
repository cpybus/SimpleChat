package me.chris.SimpleChat;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @author Christopher Pybus
 * @date Feb 29, 2012
 * @file SimpleChatMethods.java
 * @package me.chris.SimpleChat
 * 
 * @purpose
 */

public class SimpleChatAPI
{

	public static String getPrefix(Player player)
	{

		if (Variables.userPrefixes.containsKey(player.getName().toLowerCase()))
		{
			return Variables.userPrefixes.get(player.getName().toLowerCase());
		}
		
		String group;
		try
		{
			group = Variables.perms.getPrimaryGroup(player).toLowerCase();
		}
		catch(Throwable t)
		{
			group = null;
		}
		
		if (group == null)
		{
			return Variables.defaultPrefix;
		}
		else
		{
			return Variables.groupPrefixes.get(group);
		}

	}

	// Returns the suffix for said player
	public static String getSuffix(Player player)
	{
		String group;
		try
		{
			group = Variables.perms.getPrimaryGroup(player).toLowerCase();
		}
		catch(Throwable t)
		{
			group = null;
		}
		
		
		if (Variables.userSuffixes.containsKey(player.getName().toLowerCase()))
		{
			return Variables.userSuffixes.get(player.getName().toLowerCase());
		}

		if (group == null)
		{
			return Variables.defaultSuffix;
		}
		else
		{
			return Variables.groupSuffixes.get(group);
		}
	}

	// returns the group for said player
	public static String getGroup(Player player)
	{
		String group;
		try
		{
			group = Variables.perms.getPrimaryGroup(player).toLowerCase();
		}
		catch(Throwable t)
		{
			group = null;
		}
		
		
		if (group != null)
		{
			return group;
		}
		else
		{
			return Variables.defaultGroup;
		}
	}

	public static ArrayList<String> getUsers()
	{
		if (Variables.userPrefixes != null)
		{
			Set<String> users = Variables.userPrefixes.keySet();
			ArrayList<String> list = new ArrayList<String>();
			for (String u : users)
			{
				list.add(u);
			}
			return list;
		}
		else
		{
			return null;
		}
	}

	public static ArrayList<String> getGroups()
	{
		Set<String> groups = Variables.groupPrefixes.keySet();
		ArrayList<String> list = new ArrayList<String>();
		for (String g : groups)
		{
			list.add(g);
		}
		return list;
	}

}
