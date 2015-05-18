package me.chris.SimpleChat.PartyHandler;

import java.util.ArrayList;

import me.chris.SimpleChat.Variables;

import org.bukkit.entity.Player;

public class Party
{
	public String				partyName;
	public String				owner;
	public String				password;
	public ArrayList<String>	invisibleMembers;
	public ArrayList<String>	members;
	public ArrayList<String>	bannedplayers;
	public ArrayList<Player>	onlineMembers;
	
	public Party(String partyName, String owner, String password, ArrayList<String> members,
			ArrayList<String> bannedplayers)
	{
		this.partyName = partyName;
		this.owner = owner;
		this.password = password;
		this.members = members;
		this.bannedplayers = bannedplayers;
		this.invisibleMembers = new ArrayList<String>();
		this.onlineMembers = new ArrayList<Player>();
		
		if (!members.contains(owner))
		{
			members.add(owner);
		}
	}
	
	public boolean hasPassword()
	{
		if (password.equalsIgnoreCase(""))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean isOwner(String p)
	{
		if (p.equalsIgnoreCase(owner))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean hasMembers()
	{
		if (members.isEmpty())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean hasBannedPlayers()
	{
		if (bannedplayers.isEmpty())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean isMember(String member)
	{
		for (String mem : members)
		{
			if (mem.equalsIgnoreCase(member))
			{
				member = mem;
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isBanned(String bannedP)
	{
		for (String mem : bannedplayers)
		{
			if (bannedP.equalsIgnoreCase(mem))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	
	public void addMember(String member)
	{
		if (!isMember(member))
		{
			members.add(member);
			Player partyMember = Variables.plugin.getServer().getPlayer(member);
			if (partyMember != null && partyMember.isOnline())
			{
				onlineMembers.add(partyMember);
			}
		}
	}
	
	public void banMember(String member)
	{
		if(isMember(member))
		{
			removeMember(member);
		}
		
		bannedplayers.add(member);
		for (Player onlineP : onlineMembers)
		{
			if (onlineP.getName().equalsIgnoreCase(member))
			{
				onlineMembers.remove(onlineP);
			}
		}
		
		
	}
	
	public boolean unbanMember(String member)
	{
		boolean contains = false;
		for (String mem : bannedplayers)
		{
			if (mem.equalsIgnoreCase(member))
			{
				member = mem;
				contains = true;
				break;
			}
		}
		
		if (contains == true)
		{
			members.remove(member);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean removeMember(String member)
	{
		boolean contains = false;
		for (String mem : members)
		{
			if (mem.equalsIgnoreCase(member))
			{
				member = mem;
				contains = true;
				break;
			}
		}
		
		for (Player onlineP : onlineMembers)
		{
			if (onlineP.getName().equalsIgnoreCase(member))
			{
				onlineMembers.remove(onlineP);
			}
		}
		
		if (contains == true)
		{
			members.remove(member);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void sendMessage(String msg)
	{
		for (String member : members)
		{
			Player partyMember = Variables.plugin.getServer().getPlayer(member);
			if (partyMember != null && partyMember.isOnline())
			{
				partyMember.sendMessage(msg);
			}
		}
		
		for (String member : invisibleMembers)
		{
			Player partyMember = Variables.plugin.getServer().getPlayer(member);
			if (partyMember.isOnline())
			{
				
				partyMember.sendMessage(msg);
			}
		}
	}
	
	public void addInvisibleMember(String name)
	{
		invisibleMembers.add(name);
		
	}
	
	public void addOnlineMember(Player p)
	{
		onlineMembers.add(p);
	}
	
	public void removeOnlineMember(Player p)
	{
		onlineMembers.remove(p);
		
	}
}
