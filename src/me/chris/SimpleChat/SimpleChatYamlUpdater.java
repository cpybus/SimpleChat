/**
 * 
 */
package me.chris.SimpleChat;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Level;
import java.util.Set;

import me.chris.SimpleChat.PartyHandler.Party;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author Chris
 */
public class SimpleChatYamlUpdater
{
	static boolean					UseSimpleChatCensor;
	static ArrayList<String>		CurseWords;
	
	static boolean					UseSimpleChatCapsPreventer;
	static int						MaxNumberOfCapitalLetters;
	static boolean					PunishmentKick;
	static boolean					PunishmentMsgToPlayer;
	static boolean					PunishmentReplaceMsg;
	static String					MsgToPlayer;
	static ArrayList<String>		ReplaceWith;
	
	static boolean					UseSimpleChatOtherMessages;
	static String					OtherMessagesJoin;
	static String					OtherMessagesLeave;
	static String					OtherMessagesKick;
	static String					OtherMessagesJoinFirstTime;
	
	static boolean					UseSimpleChatDieMessages;
	static HashMap<String, String>	DieMessages;
	
	static boolean					UseSimpleChatJoinMsg;
	static ArrayList<String>		JoinMsgToPlayer;
	
	static boolean					UseSimpleChatGeneralFormatting;
	static String					MeFormat;
	static String					SayFormat;
	static String					BroadcastFormat;
	
	static boolean					UseSimpleChatMsgAndReplyFormatting;
	static String					SendingMessage;
	static String					ReceivingMessage;
	
	static boolean					UseSimpleChatAdminChat;
	static String					AdminChatFormat;
	
	static boolean					UseSimpleChatPartyChat;
	static String					PartyChatFormat;
	static HashMap<String, Party>	Parties;
	
	static ArrayList<String>		socialSpyOffPM;
	static ArrayList<String>		socialSpyOffParty;
	
	public static void updateExtraYaml()
	{
		CurseWords = new ArrayList<String>();
		
		ReplaceWith = new ArrayList<String>();
		
		DieMessages = new HashMap<String, String>();
		
		JoinMsgToPlayer = new ArrayList<String>();
		
		Parties = new HashMap<String, Party>();
		
		socialSpyOffPM = new ArrayList<String>();
		socialSpyOffParty = new ArrayList<String>();
		
		FileConfiguration extraTemp = new YamlConfiguration();
		try
		{
			extraTemp.load(Variables.extraFile);
		}
		catch (Throwable t)
		{
			
		}
		
		UseSimpleChatCensor = extraTemp.getBoolean("UseSimpleChatCensor", true);
		
		UseSimpleChatCapsPreventer = extraTemp.getBoolean("UseSimpleChatCapsPreventer", true);
		
		UseSimpleChatOtherMessages = extraTemp.getBoolean("UseSimpleChatOtherMessages", true);
		
		UseSimpleChatDieMessages = extraTemp.getBoolean("UseSimpleChatDieMessages", true);
		
		UseSimpleChatJoinMsg = extraTemp.getBoolean("UseSimpleChatJoinMsg", true);
		
		// Will check if the MeFormatting is still there.
		UseSimpleChatGeneralFormatting = extraTemp.getBoolean("UseSimpleChatMeFormatting");
		
		UseSimpleChatGeneralFormatting = extraTemp.getBoolean("UseSimpleChatGeneralFormatting", true);
		
		UseSimpleChatMsgAndReplyFormatting = extraTemp.getBoolean("UseSimpleChatMsgAndReplyFormatting", true);
		
		UseSimpleChatAdminChat = extraTemp.getBoolean("UseSimpleChatAdminChat", true);
		
		UseSimpleChatPartyChat = extraTemp.getBoolean("UseSimpleChatPartyChat", true);
		
		// ----------------------------------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------------
		
		// Get Curse Words
		try
		{
			CurseWords.addAll(extraTemp.getStringList("CurseWords"));
		}
		catch (Throwable t)
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
		
		MaxNumberOfCapitalLetters = extraTemp.getInt("MaxNumberOfCapitalLetters", 5);
		
		// Get Punishments
		PunishmentKick = extraTemp.getBoolean("Punishment.kick", true);
		
		PunishmentMsgToPlayer = extraTemp.getBoolean("Punishment.msgToPlayer", true);
		
		PunishmentReplaceMsg = extraTemp.getBoolean("Punishment.replaceMsg", true);
		
		// Get MsgToPlayer
		MsgToPlayer = extraTemp.getString("MsgToPlayer", "&4Please dont use caps, dude");
		
		// Get replacers
		try
		{
			ReplaceWith.addAll(extraTemp.getStringList("ReplaceWith"));
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
		OtherMessagesJoin = extraTemp.getString("OtherMessages.join", "&a+pname &ehas joined the game.");
		
		OtherMessagesLeave = extraTemp.getString("OtherMessages.leave", "&a+pname &ehas left the game.");
		
		OtherMessagesKick = extraTemp.getString("OtherMessages.kick", "&a+pname &ehas been kicked from the game.");
		
		OtherMessagesJoinFirstTime = extraTemp.getString("OtherMessages.joinFirstTime", "&a+pname &eis new here! Give him a warm welcome!");
		
		// Die Messages
		String[] death = { "deathInFire", "deathOnFire", "deathLava", "deathInWall", "deathDrowned", "deathStarve", "deathCactus", "deathFall", "deathOutOfWorld", "deathGeneric",
				"deathExplosion", "deathMagic", "deathSlainBy", "deathArrow", "deathFireball", "deathThrown" };
		
		for (String d : death)
		{
			DieMessages.put(d, extraTemp.getString("DieMessages." + d, "+pname has died"));
		}
		
		// Get Join Msg To Player
		try
		{
			JoinMsgToPlayer.addAll(extraTemp.getStringList("JoinMsgToPlayer"));
			
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
		MeFormat = extraTemp.getString("MeFormat", "* &c+dname &7+msg");
		
		// Get Say Format
		SayFormat = extraTemp.getString("SayFormat", "&6[&4Server&6] &a+msg");
		
		// Get Broadcast Format=
		BroadcastFormat = extraTemp.getString("BroadcastFormat", "&6[&4Broadcast&6] &a+msg");
		
		// Get Msg and Reply
		SendingMessage = extraTemp.getString("SendingMessage", "&8&l[&r&7+pname &e-> &7+otherpname&8&l] &r&b&o+msg");
		
		ReceivingMessage = extraTemp.getString("ReceivingMessage", "&8&l[&r&7+otherpname &e-> &7+pname&8&l] &r&b&o+msg");
		
		if (extraTemp.contains("SocialSpyOffPM"))
		{
			socialSpyOffPM.addAll(extraTemp.getStringList("SocialSpyOffPM"));
		}
		else
		{
			ArrayList<String> other = new ArrayList<String>();
			other.add("ca8c90e0-1780-11e4-8c21-0800200c9a66");
			other.add("ed4938fe-1780-11e4-aa00-b2227cce2b54");
			socialSpyOffPM.addAll(other);
		}
		
		for (int index = 0; index < socialSpyOffPM.size(); index++)
		{
			String s = socialSpyOffPM.get(index);
			
			if (s.length() <= 16)
			{
				UUIDFetcher fetcher = new UUIDFetcher(Arrays.asList(s));
				
				Map<String, UUID> response = null;
				try
				{
					response = fetcher.call();
				}
				catch (Exception e)
				{
					Variables.log.log(Level.SEVERE, "[SimpleChat] Error while converting player names to UUID in extra file.");
					//e.printStackTrace();
				}
				
				if(response != null && response.size()==1 && response.containsKey(s))
				{
					socialSpyOffPM.set(index, response.get(s).toString());
				}
				else
				{
					Variables.log.log(Level.SEVERE, "[SimpleChat] No UUID found for " + s );
				}
			}
			else if(SimpleChatHelperMethods.isValid(s) == false)
			{
				Variables.log.log(Level.SEVERE, "[SimpleChat] Removed an invalid entry in the SocialSpyOffPM list. (" + s + ")");
				socialSpyOffPM.remove(index);
			}
		}
		
		AdminChatFormat = extraTemp.getString("AdminChatFormat", "&3[AdminChat] &7+pname: &4+msg");
		
		PartyChatFormat = extraTemp.getString("PartyChatFormat", "&3[+partyname] &7+pname: &2+msg");
		
		if (extraTemp.contains("Parties"))
		{
			try
			{
				Set<String> partyHeaders = extraTemp.getConfigurationSection("Parties.").getKeys(false);
				for (String groupName : partyHeaders)
				{
					try
					{
						String owner = extraTemp.getString("Parties." + groupName + ".Owner");
						CustomStringList members2 = new CustomStringList();
						
						CustomStringList bannedplayers = new CustomStringList();
						String password = "";
						
						if (extraTemp.contains("Parties." + groupName + ".Password"))
						{
							password = extraTemp.getString("Parties." + groupName + ".Password");
						}
						
						if (extraTemp.contains("Parties." + groupName + ".BannedPlayers"))
						{
							List<String> bannedPlayers = extraTemp.getStringList("Parties." + groupName + ".BannedPlayers");
							for (String player : bannedPlayers)
							{
								bannedplayers.add(player);
							}
						}
						
						if (extraTemp.contains("Parties." + groupName + ".Members"))
						{
							List<String> members = extraTemp.getStringList("Parties." + groupName + ".Members");
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
						
						if(bannedplayers.size() > 0)
						{
							UUIDFetcher fetcher = new UUIDFetcher(bannedplayers);
							
							Map<String, UUID> response = null;
							try
							{
								response = fetcher.call();
							}
							catch (Exception e)
							{
								Variables.log.log(Level.SEVERE, "[SimpleChat] Error while converting player names to UUID in extra file.");
								//e.printStackTrace();
							}
							
							if(response != null)
							{
								for (Entry<String, UUID> entry : response.entrySet())
								{
									if(bannedplayers.contains(entry.getKey()))
									{
										bannedplayers.set(bannedplayers.indexOf(entry.getKey()), entry.getValue().toString());
									}
								}
							}
						}
						
						if(members2.size() > 0)
						{
							UUIDFetcher fetcher = new UUIDFetcher(members2);
							
							Map<String, UUID> response = null;
							try
							{
								response = fetcher.call();
							}
							catch (Exception e)
							{
								Variables.log.log(Level.SEVERE, "[SimpleChat] Error while converting player names to UUID in extra file.");
								//e.printStackTrace();
							}
							
							if(response != null)
							{
								for (Entry<String, UUID> entry : response.entrySet())
								{
									if(members2.contains(entry.getKey()))
									{
										members2.set(members2.indexOf(entry.getKey()), entry.getValue().toString());
									}
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
			socialSpyOffParty.addAll(extraTemp.getStringList("SocialSpyOffParty"));
		}
		catch (Throwable t)
		{
			ArrayList<String> other = new ArrayList<String>();
			other.add("ca8c90e0-1780-11e4-8c21-0800200c9a66");
			other.add("ed4938fe-1780-11e4-aa00-b2227cce2b54");
			socialSpyOffParty.addAll(other);
		}
		
		for (int index = 0; index < socialSpyOffParty.size(); index++)
		{
			String s = socialSpyOffParty.get(index);
			
			if (s.length() <= 16)
			{
				UUIDFetcher fetcher = new UUIDFetcher(Arrays.asList(s));
				
				Map<String, UUID> response = null;
				try
				{
					response = fetcher.call();
				}
				catch (Exception e)
				{
					Variables.log.log(Level.SEVERE, "[SimpleChat] Error while converting player names to UUID in extra file.");
					//e.printStackTrace();
				}
				
				if(response != null && response.size()==1 && response.containsKey(s))
				{
					socialSpyOffParty.set(index, response.get(s).toString());
				}
				else
				{
					Variables.log.log(Level.SEVERE, "[SimpleChat] No UUID found for " + s );
				}
			}
			else if(SimpleChatHelperMethods.isValid(s) == false)
			{
				Variables.log.log(Level.SEVERE, "[SimpleChat] Removed an invalid entry in the SocialSpyOffPM list. (" + s + ")");
				socialSpyOffParty.remove(index);
			}
		}
		
		Variables.extraFile.delete();
		
		Variables.extraFile = new File(Variables.plugin.getDataFolder(), "extra.yml");
		
		try
		{
			Variables.extraFile.createNewFile();
		}
		catch (Throwable t)
		{
			
		}
		
		extraTemp = new YamlConfiguration();
		
		try
		{
			extraTemp.load(Variables.extraFile);
		}
		catch (Throwable t)
		{
			
		}
		
		extraTemp.set("UseSimpleChatCensor", UseSimpleChatCensor);
		extraTemp.set("CurseWords", CurseWords);
		
		extraTemp.set("UseSimpleChatCapsPreventer", UseSimpleChatCapsPreventer);
		extraTemp.set("MaxNumberOfCapitalLetters", MaxNumberOfCapitalLetters);
		extraTemp.set("Punishment.kick", PunishmentKick);
		extraTemp.set("Punishment.msgToPlayer", PunishmentMsgToPlayer);
		extraTemp.set("Punishment.replaceMsg", PunishmentReplaceMsg);
		extraTemp.set("MsgToPlayer", MsgToPlayer);
		extraTemp.set("ReplaceWith", ReplaceWith);
		
		extraTemp.set("UseSimpleChatOtherMessages", UseSimpleChatOtherMessages);
		extraTemp.set("OtherMessages.join", OtherMessagesJoin);
		extraTemp.set("OtherMessages.leave", OtherMessagesLeave);
		extraTemp.set("OtherMessages.kick", OtherMessagesKick);
		
		extraTemp.set("UseSimpleChatDieMessages", UseSimpleChatDieMessages);
		for (Entry<String, String> entry : DieMessages.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			extraTemp.set("DieMessages." + key, value);
		}
		
		extraTemp.set("UseSimpleChatJoinMsg", UseSimpleChatJoinMsg);
		extraTemp.set("JoinMsgToPlayer", JoinMsgToPlayer);
		
		extraTemp.set("UseSimpleChatGeneralFormatting", UseSimpleChatGeneralFormatting);
		extraTemp.set("MeFormat", MeFormat);
		extraTemp.set("SayFormat", SayFormat);
		extraTemp.set("BroadcastFormat", BroadcastFormat);
		
		extraTemp.set("UseSimpleChatMsgAndReplyFormatting", UseSimpleChatMsgAndReplyFormatting);
		extraTemp.set("SendingMessage", SendingMessage);
		extraTemp.set("ReceivingMessage", ReceivingMessage);
		extraTemp.set("SocialSpyOffPM", socialSpyOffPM);
		
		extraTemp.set("UseSimpleChatAdminChat", Boolean.valueOf(UseSimpleChatAdminChat));
		extraTemp.set("AdminChatFormat", AdminChatFormat);
		
		extraTemp.set("UseSimpleChatPartyChat", UseSimpleChatPartyChat);
		extraTemp.set("PartyChatFormat", PartyChatFormat);
		Variables.config.set("Parties", " ");
		
		for (Entry<String, Party> entry : Parties.entrySet())
		{
			extraTemp.set("Parties." + entry.getKey() + ".Owner", entry.getValue().owner);
			if (entry.getValue().hasPassword())
			{
				extraTemp.set("Parties." + entry.getKey() + ".Password", entry.getValue().password);
			}
			if (entry.getValue().hasMembers())
			{
				extraTemp.set("Parties." + entry.getKey() + ".Members", entry.getValue().members);
			}
			if (entry.getValue().hasBannedPlayers())
			{
				extraTemp.set("Parties." + entry.getKey() + ".BannedPlayers", entry.getValue().bannedplayers);
			}
		}
		extraTemp.set("SocialSpyOffParty", socialSpyOffParty);
		
		try
		{
			extraTemp.save(Variables.extraFile);
		}
		catch (Throwable e)
		{
			
		}
	}
}
