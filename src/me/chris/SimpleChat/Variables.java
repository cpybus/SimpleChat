package me.chris.SimpleChat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import me.chris.SimpleChat.PartyHandler.Party;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author Christopher Pybus
 * @date Mar 25, 2012
 * @file SimpleChatVariables.java
 * @package me.chris.SimpleChat
 * @purpose
 */

public class Variables
{
	public static FileConfiguration			config;
	public static FileConfiguration			extra;
	public static Permission				perms;
	public static Logger					log;
	public static SimpleChatMain			plugin;
	
	public static File						configFile;
	public static File						extraFile;
	
	public static File						configExampleFile;
	public static File						extraExampleFile;
	
	public static SimpleChatAPI				api		= new SimpleChatAPI();
	public static SimpleChatHelperMethods	schm	= new SimpleChatHelperMethods();
	public static SimpleChatChatState		sccs	= new SimpleChatChatState();
	
	public static HashMap<Player, Player>	messaging;
	public static HashMap<Player, Player>	lockedPM;
	
	public static boolean					matching;
	
	public static final String				version	= "SimpleChat 6.0";
	
	public static ArrayList<Player>			adminChat;
	public static boolean					consoleAdminChat;
	
	public static ArrayList<Player>			onlineSocialSpy;
	public static ArrayList<Player>			onlineAdminChat;
	
	// PARTY VARIABLES
	public static HashMap<Player, Party>	lockedPartyChat;
	public static HashMap<Player, Party>	inviteToParty;
	
	
	
	// Past this point are the yaml variables
	
	public static String					MessageFormat;
	
	public static HashMap<String, String>	groupPrefixes;
	public static HashMap<String, String>	groupSuffixes;
	
	public static HashMap<String, String>	userPrefixes;
	public static HashMap<String, String>	userSuffixes;
	
	public static String					defaultPrefix;
	public static String					defaultSuffix;
	public static String					defaultGroup;
	
	public static boolean					UseSimpleChatCensor;
	public static ArrayList<String>			CurseWords;
	
	public static boolean					UseSimpleChatCapsPreventer;
	public static int						MaxNumberOfCapitalLetters;
	public static boolean					PunishmentKick;
	public static boolean					PunishmentMsgToPlayer;
	public static boolean					PunishmentReplaceMsg;
	public static String					MsgToPlayer;
	public static ArrayList<String>			ReplaceWith;
	
	public static boolean					UseSimpleChatOtherMessages;
	public static String					OtherMessagesJoin;
	public static String					OtherMessagesLeave;
	public static String					OtherMessagesKick;
	public static String					OtherMessagesJoinFirstTime;
	
	public static boolean					UseSimpleChatDieMessages;
	public static HashMap<String, String>	DieMessages;
	
	public static boolean					UseSimpleChatJoinMsg;
	public static ArrayList<String>			JoinMsgToPlayer;
	
	public static boolean					UseSimpleChatGeneralFormatting;
	public static String					MeFormat;
	public static String					SayFormat;
	public static String					BroadcastFormat;
	
	public static boolean					UseSimpleChatMsgAndReplyFormatting;
	public static String					SendingMessage;
	public static String					ReceivingMessage;
	
	public static boolean					UseSimpleChatAdminChat;
	public static String					AdminChatFormat;
	
	public static boolean					UseSimpleChatPartyChat;
	public static String					PartyChatFormat;
	public static HashMap<String, Party>	Parties;
	
	public static ArrayList<String>			socialSpyOffPM;
	public static ArrayList<String>			socialSpyOffParty;
	
	public Variables(SimpleChatMain plugin)
	{
		Variables.plugin = plugin;
		log = Logger.getLogger("Minecraft");
		
		configFile = new File(plugin.getDataFolder(), "config.yml");
		extraFile = new File(plugin.getDataFolder(), "extra.yml");
		configExampleFile = new File(plugin.getDataFolder(), "config-example.yml");
		extraExampleFile = new File(plugin.getDataFolder(), "extra-example.yml");
		
		config = new YamlConfiguration();
		extra = new YamlConfiguration();
		
		messaging = new HashMap<Player, Player>();
		lockedPM = new HashMap<Player, Player>();
		
		groupPrefixes = new HashMap<String, String>();
		groupSuffixes = new HashMap<String, String>();
		
		userPrefixes = new HashMap<String, String>();
		userSuffixes = new HashMap<String, String>();
		
		CurseWords = new ArrayList<String>();
		
		ReplaceWith = new ArrayList<String>();
		
		DieMessages = new HashMap<String, String>();
		
		JoinMsgToPlayer = new ArrayList<String>();
		
		adminChat = new ArrayList<Player>();
		
		socialSpyOffPM = new ArrayList<String>();
		socialSpyOffParty = new ArrayList<String>();
		
		Parties = new HashMap<String, Party>();
		
		lockedPartyChat = new HashMap<Player, Party>();
		inviteToParty = new HashMap<Player, Party>();
		
		onlineSocialSpy = new ArrayList<Player>();
		onlineAdminChat = new ArrayList<Player>();
	}
	
	public static void importVariables()
	{
		// Assign Message Format
		try
		{
			MessageFormat = Variables.config.getString("MessageFormat").toString();
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the MessageFormat variable. (Config.yml)");
			MessageFormat = "+pname +msg";
		}
		
		// Get groups
		Set<String> groups = Variables.config.getConfigurationSection("Groups.").getKeys(false);
		for (String g : groups)
		{
			String pre;
			String suf;
			try
			{
				pre = Variables.config.getString("Groups." + g + ".prefix");
				suf = Variables.config.getString("Groups." + g + ".suffix");
			}
			catch (Throwable t)
			{
				log.log(Level.SEVERE, "[SimpleChat] Error on the " + g + " group. (Config.yml)");
				log.log(Level.SEVERE, "[SimpleChat] No prefix and/or suffix found");
				pre = "[" + g + "]";
				suf = "";
			}
			Variables.groupPrefixes.put(g.toLowerCase(), pre);
			Variables.groupSuffixes.put(g.toLowerCase(), suf);
		}
		groups = null;
		
		// Get Users
		Set<String> users = null;
		try
		{
			users = Variables.config.getConfigurationSection("Users.").getKeys(false);
		}
		catch (Throwable t)
		{
			users = null;
		}
		
		if (users != null)
		{
			for (String u : users)
			{
				String pre;
				String suf;
				try
				{
					pre = Variables.config.getString("Users." + u + ".prefix");
					suf = Variables.config.getString("Users." + u + ".suffix");
				}
				catch (Throwable t)
				{
					log.log(Level.SEVERE, "[SimpleChat] Error on the " + u + " user. (Config.yml)");
					log.log(Level.SEVERE, "[SimpleChat] No prefix and/or suffix found");
					pre = "[" + u + "]";
					suf = "";
				}
				Variables.userPrefixes.put(u.toLowerCase(), pre);
				Variables.userSuffixes.put(u.toLowerCase(), suf);
			}
		}
		users = null;
		
		// Get Defaults
		try
		{
			Variables.defaultPrefix = Variables.config.getString("Defaults.prefix");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the Defaults -> Prefix. (Config.yml)");
			Variables.defaultPrefix = "";
		}
		
		try
		{
			Variables.defaultSuffix = Variables.config.getString("Defaults.suffix");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the Defaults -> Suffix. (Config.yml)");
			Variables.defaultSuffix = "";
		}
		
		try
		{
			Variables.defaultGroup = Variables.config.getString("Defaults.group");
		}
		catch (Throwable t)
		{
			log.log(Level.SEVERE, "[SimpleChat] Error on the Defaults -> Group. (Config.yml)");
			Variables.defaultGroup = "";
		}
		
		// ----------------------------------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------------
		
		UseSimpleChatCensor = extra.getBoolean("UseSimpleChatCensor", true);
		
		UseSimpleChatCapsPreventer = extra.getBoolean("UseSimpleChatCapsPreventer", true);
		
		UseSimpleChatOtherMessages = extra.getBoolean("UseSimpleChatOtherMessages", true);
		
		UseSimpleChatDieMessages = extra.getBoolean("UseSimpleChatDieMessages", true);
		
		UseSimpleChatJoinMsg = extra.getBoolean("UseSimpleChatJoinMsg", true);
		
		// Will check if the MeFormatting is still there.
		UseSimpleChatGeneralFormatting = extra.getBoolean("UseSimpleChatMeFormatting");
		
		UseSimpleChatGeneralFormatting = extra.getBoolean("UseSimpleChatGeneralFormatting", true);
		
		UseSimpleChatMsgAndReplyFormatting = extra.getBoolean("UseSimpleChatMsgAndReplyFormatting", true);
		
		UseSimpleChatAdminChat = extra.getBoolean("UseSimpleChatAdminChat", true);
		
		UseSimpleChatPartyChat = extra.getBoolean("UseSimpleChatPartyChat", true);
		
		// ----------------------------------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------------
		
		// Get Curse Words
		try
		{
			CurseWords.addAll(extra.getStringList("CurseWords"));
		}
		catch(Throwable t)
		{
			ArrayList<String> temp = new ArrayList<String>();
			temp.add("motherfucker");
			temp.add("fuck");
			temp.add("cunt");
			temp.add("penis");
			temp.add("vagina");
			temp.add("bullshit");
			temp.add("dick");
			temp.add("pussy");
			temp.add("cock");
			CurseWords.addAll(temp);
		}
		
		MaxNumberOfCapitalLetters = extra.getInt("MaxNumberOfCapitalLetters", 5);
		
		
		// Get Punishments
		PunishmentKick = extra.getBoolean("Punishment.kick", true);
		
		PunishmentMsgToPlayer = extra.getBoolean("Punishment.msgToPlayer", true);
		
		PunishmentReplaceMsg = extra.getBoolean("Punishment.replaceMsg", true);
		
		
		// Get MsgToPlayer
		MsgToPlayer = extra.getString("MsgToPlayer", "&4Please dont use caps, dude");
		
		// Get replacers
		try
		{
			ReplaceWith.addAll(extra.getStringList("ReplaceWith"));
		}
		catch (Throwable t)
		{
			ArrayList<String> other = new ArrayList<String>();
			other.add("Look at my new dress!!");
			other.add("Has anyone seen my dolly?");
			other.add("This new makeup is amazing!");
			other.add("I have to go! My ballet class is in 15 minutes!");
			other.add("BRB going to go try on my new dress!");
			other.add("New Victorias Secret bra came in today! Im so excited!!");
			other.add("My new favorite color has GOT to be pink!");
			
			ReplaceWith.addAll(other);
		}
		
		// Other messages
		OtherMessagesJoin = extra.getString("OtherMessages.join", "&a+pname &ehas joined the game.");
		
		OtherMessagesLeave = extra.getString("OtherMessages.leave", "&a+pname &ehas left the game.");
		
		OtherMessagesKick = extra.getString("OtherMessages.kick", "&a+pname &ehas been kicked from the game.");
		
		OtherMessagesJoinFirstTime = extra.getString("OtherMessages.joinFirstTime", "&a+pname &eis new here! Give him a warm welcome!");
		//Remember to add in the commands for this!
		
		// Die Messages
		String[] death = { "deathInFire", "deathOnFire", "deathLava", "deathInWall", "deathDrowned", "deathStarve", "deathCactus", "deathFall", "deathOutOfWorld", "deathGeneric",
				"deathExplosion", "deathMagic", "deathSlainBy", "deathArrow", "deathFireball", "deathThrown" };
		
		for (String d : death)
		{
			DieMessages.put(d, extra.getString("DieMessages." + d, "+pname has died"));
		}
		
		// Get Join Msg To Player
		try
		{
			JoinMsgToPlayer.addAll(extra.getStringList("JoinMsgToPlayer"));
			
		}
		catch (Throwable t)
		{
			ArrayList<String> other = new ArrayList<String>();
			other.add("&5=====================================================");
			other.add("&a          Welcome to the server, +pre +pname!");
			other.add("&a Please make sure youve read all the rules and such.");
			other.add("&4 Online Admins: <onlineplayers:&c,&4,admin>   ");
			other.add("&a Online Members: <onlineplayers:&c,&4,member>   ");
			other.add("&a Online Defaults: <onlineplayers:&c,&4,default>   ");
			other.add("&5=====================================================");
			
			ReplaceWith.addAll(other);
			
		}
		
		// Get Me Format
		MeFormat = extra.getString("MeFormat", "* &c+dname &7+msg");
		
		// Get Say Format
		SayFormat = extra.getString("SayFormat",  "&6[&4Server&6] &a+msg");
		
		// Get Broadcast Format=
		BroadcastFormat = extra.getString("BroadcastFormat", "&6[&4Broadcast&6] &a+msg");
		
		// Get Msg and Reply
		SendingMessage = extra.getString("SendingMessage", "&8&l[&r&7+pname &e-> &7+otherpname&8&l] &r&b&o+msg");
		
		ReceivingMessage = extra.getString("ReceivingMessage", "&8&l[&r&7+otherpname &e-> &7+pname&8&l] &r&b&o+msg");
		
		if (extra.contains("SocialSpyOffPM"))
		{
			socialSpyOffPM.addAll(extra.getStringList("SocialSpyOffPM"));
		}
		else
		{
			ArrayList<String> other = new ArrayList<String>();
			other.add("ca8c90e0-1780-11e4-8c21-0800200c9a66");
			other.add("ed4938fe-1780-11e4-aa00-b2227cce2b54");
			socialSpyOffPM.addAll(other);
		}
		
		AdminChatFormat = extra.getString("AdminChatFormat", "&3[AdminChat] &7+pname: &4+msg");
		
		PartyChatFormat = extra.getString("PartyChatFormat", "&3[+partyname] &7+pname: &2+msg");
		
		if (extra.contains("Parties"))
		{
			try
			{
				Set<String> partyHeaders = extra.getConfigurationSection("Parties.").getKeys(false);
				for (String groupName : partyHeaders)
				{
					try
					{
						String owner = extra.getString("Parties." + groupName + ".Owner");
						CustomStringList members2 = new CustomStringList();
						CustomStringList bannedplayers = new CustomStringList();
						String password = "";
						
						if (extra.contains("Parties." + groupName + ".Password"))
						{
							password = extra.getString("Parties." + groupName + ".Password");
						}
						
						if (extra.contains("Parties." + groupName + ".BannedPlayers"))
						{
							List<String> bannedPlayers = extra.getStringList("Parties." + groupName + ".BannedPlayers");
							for (String player : bannedPlayers)
							{
								bannedplayers.add(player);
							}
						}
						
						if (extra.contains("Parties." + groupName + ".Members"))
						{
							List<String> members = extra.getStringList("Parties." + groupName + ".Members");
							for (String member : members)
							{
								if (bannedplayers.contains(member))
								{
									continue;
								}
								
								if (!members2.contains(member))
								{
									members2.add(member);
								}
								
							}
							
						}
						
						Party party = new Party(groupName, owner, password, members2, bannedplayers);
						
						Parties.put(groupName, party);
					}
					catch (Throwable t)
					{
						Variables.log.log(Level.SEVERE, "[SimpleChat] There was a problem with one of your Parties.");
					}
				}
			}
			catch (Throwable t)
			{
				ArrayList<String> members2 = new ArrayList<String>();
				members2.add("SupremeOverlord");
				members2.add("Player1");
				members2.add("Player2");
				ArrayList<String> bannedplayers = new ArrayList<String>();
				Party party = new Party("ExampleGroup", "SupremeOverlord", "password", members2, bannedplayers);
				Parties.put("ExampleGroup", party);
			}
		}
		else
		{
			ArrayList<String> members2 = new ArrayList<String>();
			members2.add("SupremeOverlord");
			members2.add("Player1");
			members2.add("Player2");
			ArrayList<String> bannedplayers = new ArrayList<String>();
			Party party = new Party("ExampleGroup", "SupremeOverlord", "password", members2, bannedplayers);
			Parties.put("ExampleGroup", party);
		}
		
		if (Parties.isEmpty())
		{
			ArrayList<String> members2 = new ArrayList<String>();
			members2.add("SupremeOverlord");
			members2.add("Player1");
			members2.add("Player2");
			ArrayList<String> bannedplayers = new ArrayList<String>();
			Party party = new Party("ExampleGroup", "SupremeOverlord", "password", members2, bannedplayers);
			Parties.put("ExampleGroup", party);
		}
		
		try
		{
			socialSpyOffParty.addAll(extra.getStringList("SocialSpyOffParty"));
		}
		catch(Throwable t)
		{
			ArrayList<String> other = new ArrayList<String>();
			other.add("ca8c90e0-1780-11e4-8c21-0800200c9a66");
			other.add("ed4938fe-1780-11e4-aa00-b2227cce2b54");
			socialSpyOffParty.addAll(other);
		}
	}
	
	public static void exportVariables()
	{
		Variables.config.set("MessageFormat", Variables.MessageFormat);
		Variables.config.set("Groups", " ");
		Variables.config.set("Users", " ");
		
		for (Entry<String, String> entry : Variables.groupPrefixes.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			Variables.config.set("Groups." + key + ".prefix", value);
		}
		
		for (Entry<String, String> entry : Variables.groupSuffixes.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			Variables.config.set("Groups." + key + ".suffix", value);
		}
		
		if (Variables.userPrefixes != null)
		{
			for (Entry<String, String> entry : Variables.userPrefixes.entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				Variables.config.set("Users." + key + ".prefix", value);
			}
		}
		
		if (Variables.userSuffixes != null)
		{
			for (Entry<String, String> entry : Variables.userSuffixes.entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				Variables.config.set("Users." + key + ".suffix", value);
			}
		}
		
		Variables.config.set("Defaults.prefix", Variables.defaultPrefix);
		Variables.config.set("Defaults.suffix", Variables.defaultSuffix);
		Variables.config.set("Defaults.group", Variables.defaultGroup);
		
		Variables.extra.set("UseSimpleChatCensor", Variables.UseSimpleChatCensor);
		Variables.extra.set("UseSimpleChatCapsPreventer", Variables.UseSimpleChatCapsPreventer);
		Variables.extra.set("UseSimpleChatOtherMessages", Variables.UseSimpleChatOtherMessages);
		Variables.extra.set("UseSimpleChatDieMessages", Variables.UseSimpleChatDieMessages);
		Variables.extra.set("UseSimpleChatJoinMsg", Variables.UseSimpleChatJoinMsg);
		Variables.extra.set("UseSimpleChatGeneralFormatting", Variables.UseSimpleChatGeneralFormatting);
		Variables.extra.set("UseSimpleChatMsgAndReplyFormatting", Variables.UseSimpleChatMsgAndReplyFormatting);
		Variables.extra.set("UseSimpleChatAdminChat", Variables.UseSimpleChatAdminChat);
		Variables.extra.set("UseSimpleChatPartyChat", Variables.UseSimpleChatPartyChat);
		
		Variables.extra.set("CurseWords", Variables.CurseWords);
		
		Variables.extra.set("MaxNumberOfCapitalLetters", Variables.MaxNumberOfCapitalLetters);
		
		Variables.extra.set("Punishment.kick", Variables.PunishmentKick);
		Variables.extra.set("Punishment.msgToPlayer", Variables.PunishmentMsgToPlayer);
		Variables.extra.set("Punishment.replaceMsg", Variables.PunishmentReplaceMsg);
		
		Variables.extra.set("MsgToPlayer", Variables.MsgToPlayer);
		
		Variables.extra.set("ReplaceWith", Variables.ReplaceWith);
		
		Variables.extra.set("OtherMessages.join", Variables.OtherMessagesJoin);
		Variables.extra.set("OtherMessages.leave", Variables.OtherMessagesLeave);
		Variables.extra.set("OtherMessages.kick", Variables.OtherMessagesKick);
		
		for (Entry<String, String> entry : Variables.DieMessages.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			Variables.extra.set("DieMessages." + key, value);
		}
		
		Variables.extra.set("JoinMsgToPlayer", Variables.JoinMsgToPlayer);
		
		Variables.extra.set("MeFormat", Variables.MeFormat);
		Variables.extra.set("SayFormat", Variables.SayFormat);
		Variables.extra.set("BroadcastFormat", Variables.BroadcastFormat);
		
		Variables.extra.set("SendingMessage", Variables.SendingMessage);
		Variables.extra.set("ReceivingMessage", Variables.ReceivingMessage);
		Variables.extra.set("SocialSpyOffPM", socialSpyOffPM);
		
		Variables.extra.set("AdminChatFormat", Variables.AdminChatFormat);
		
		Variables.extra.set("PartyChatFormat", Variables.PartyChatFormat);
		
		Variables.extra.set("Parties", "Filler");
		
		for (Entry<String, Party> entry : Variables.Parties.entrySet())
		{
			Variables.extra.set("Parties." + entry.getKey() + ".Owner", entry.getValue().owner);
			if (entry.getValue().hasPassword())
			{
				Variables.extra.set("Parties." + entry.getKey() + ".Password", entry.getValue().password);
			}
			if (entry.getValue().hasMembers())
			{
				Variables.extra.set("Parties." + entry.getKey() + ".Members", entry.getValue().members);
			}
			if (entry.getValue().hasBannedPlayers())
			{
				Variables.extra.set("Parties." + entry.getKey() + ".BannedPlayers", entry.getValue().bannedplayers);
			}
			
		}
		
		Variables.extra.set("SocialSpyOffParty", socialSpyOffParty);
	}
}
