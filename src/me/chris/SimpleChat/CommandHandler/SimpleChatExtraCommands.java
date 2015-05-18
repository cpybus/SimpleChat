package me.chris.SimpleChat.CommandHandler;

import java.util.Map.Entry;

import me.chris.SimpleChat.Variables;

import org.bukkit.entity.Player;

public class SimpleChatExtraCommands
{
	SimpleChatExtraCommands()
	{
		
	}
	
	public static void help(Player p)
	{
		p.sendMessage("§5===================§c [ SCExtra Help ] §5===================");
		p.sendMessage("§c/scextra§7 ChatCensor §e- Accesses the CurseCensor area of extra.yml.");
		p.sendMessage("§c/scextra§7 CapsPreventor §e- Accesses the CapsPreventor area of extra.yml.");
		p.sendMessage("§c/scextra§7 OtherMessages §e- Accesses the OtherMsgs area of the extra.yml.");
		p.sendMessage("§c/scextra§7 DieMessage §e- Accesses the DieMessage area of extra.yml.");
		p.sendMessage("§c/scextra§7 JoinMessage §e- Accesses the JoinMessage area of extra.yml.");
		p.sendMessage("§c/scextra§7 GeneralFormat §e- Accesses the GeneralFormat area of extra.yml.");
		p.sendMessage("§c/scextra§7 MsgAndReply §e- Accesses the MeFormat area of extra.yml.");
		p.sendMessage("§c/scextra§7 AdminChat §e- Accesses the AdminChat area of extra.yml.");
		p.sendMessage("§c/scextra§7 PartyChat §e- Accesses the AdminChat area of extra.yml.");
		p.sendMessage("§5=====================================================");
		
	}
	
	// +------------------------------------------------------+ //
	// | chatCensor | //
	// | | //
	// | Handles all of the chatCensor commands: | //
	// | | //
	// +------------------------------------------------------+ //
	public static void ccHelp(Player p)
	{
		p.sendMessage("§5==================§c [ ChatCensor Help ] §5=================");
		p.sendMessage("§c/scextra§7 cc useChatCensor [true/false] §e- Sets the use value to true or false.");
		p.sendMessage("§c/scextra§7 cc view §e- Views the setup of the section.");
		p.sendMessage("§c/scextra§7 cc addword [word1] <word2> <word3> ... §e- Adds the words to the list. ");
		p.sendMessage("§c/scextra§7 cc removeword [word1] <word2> <word3> ... §e- Removes words from list. ");
		p.sendMessage("§5=====================================================");
	}
	
	public static void ccUse(Player p, String arg)
	{
		if (arg.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatCensor = true;
			p.sendMessage("§a[SimpleChat] §3ChatCensor set to true.");
		}
		else if (arg.equalsIgnoreCase("false"))
		{
			Variables.UseSimpleChatCensor = false;
			p.sendMessage("§a[SimpleChat] §3ChatCensor set to false.");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4Please enter either true or false.");
		}
		Variables.plugin.saveYamls();
	}
	
	public static void ccView(Player p)
	{
		p.sendMessage("§5====================§c [ ChatCensor ] §5===================");
		p.sendMessage("§2UseSimpleChatChatCensor: §e" + Variables.UseSimpleChatCensor);
		String words = "§7";
		for (String word : Variables.CurseWords)
		{
			words = words + word + "§4,§7 ";
		}
		p.sendMessage("§2CurseWords: " + words);
		p.sendMessage("§5=====================================================");
	}
	
	public static void ccAddWord(Player p, String word)
	{
		Variables.CurseWords.add(word.toLowerCase());
		p.sendMessage("§a[SimpleChat] §c" + word + " §5was added to the curseword list");
		Variables.plugin.saveYamls();
	}
	
	public static void ccRemoveWord(Player p, String word)
	{
		if (Variables.CurseWords.contains(word.toLowerCase()))
		{
			Variables.CurseWords.remove(word);
			p.sendMessage("§a[SimpleChat] §c" + word + " §5was removed from the curseword list");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §c" + word + " §4was not found on the curseword list");
		}
		Variables.plugin.saveYamls();
	}
	
	public static void ccAddWord(Player p, String[] words) // Unspecified Args
	{
		String wordList = "§7";
		for (String word : words)
		{
			wordList = wordList + word + "§4;§7";
			Variables.CurseWords.add(word.toLowerCase());
		}
		p.sendMessage("§a[SimpleChat] §5The following words were added to the curseword list: " + wordList);
		
		Variables.plugin.saveYamls();
	}
	
	public static void ccRemoveWord(Player p, String[] words) // Unspecified
																// Args
	{
		String removed = "§7";
		String notRemoved = "§7";
		for (String word : words)
		{
			if (Variables.CurseWords.contains(word.toLowerCase()))
			{
				Variables.CurseWords.remove(word);
				removed = removed + word + "§4,§7 ";
			}
			else
			{
				notRemoved = notRemoved + word + "§4,§7 ";
			}
		}
		p.sendMessage("§5====================§c [ ChatCensor ] §5===================");
		p.sendMessage("§2Successfully Removed Words: " + removed);
		p.sendMessage("§2Not Removed/Not Found: " + notRemoved);
		p.sendMessage("§5=====================================================");
		Variables.plugin.saveYamls();
	}
	
	// +------------------------------------------------------+ //
	// | capsPreventor | //
	// | | //
	// | Handles all of the capsPreventor commands: | //
	// | | //
	// +------------------------------------------------------+ //
	public static void cpHelp(Player p)
	{
		p.sendMessage("§5================§c [ CapsPreventor Help ] §5================");
		p.sendMessage("§c/scextra§7 cp useCapsPreventor [true/false] §e- Sets the use value to true or false.");
		p.sendMessage("§c/scextra§7 cp view §e- Views the setup of the section.");
		p.sendMessage("§c/scextra§7 cp setMaxCapitalLetters [max] §e- Sets the max capital letters. ");
		p.sendMessage("§c/scextra§7 cp setPunishment  [punishment] [true/false] §e- Sets a punishment to true or false. ");
		p.sendMessage("§c/scextra§7 cp setMsgToPlayer  [msg...] §e- Sets the msg sent to the player. ");
		p.sendMessage("§5=====================================================");
	}
	
	public static void cpUse(Player p, String arg)
	{
		if (arg.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatCapsPreventer = true;
			p.sendMessage("§a[SimpleChat] §3CapsPreventer set to true.");
		}
		else if (arg.equalsIgnoreCase("false"))
		{
			Variables.UseSimpleChatCapsPreventer = false;
			p.sendMessage("§a[SimpleChat] §3CapsPreventer set to false.");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4Please enter either true or false.");
		}
		Variables.plugin.saveYamls();
	}
	
	public static void cpView(Player p)
	{
		p.sendMessage("§5==================§c [ CapsPreventor ] §5==================");
		p.sendMessage("§2UseSimpleChatChatCensor: §e" + Variables.UseSimpleChatCensor);
		p.sendMessage("§2MaxNumberOfCapitalLetters: §e" + Variables.MaxNumberOfCapitalLetters);
		p.sendMessage("§2Punishment - Kick: §e" + Variables.PunishmentKick);
		p.sendMessage("§2Punishment - MsgToPlayer: §e" + Variables.PunishmentMsgToPlayer);
		p.sendMessage("§2Punishment - ReplaceMsg: §e" + Variables.PunishmentReplaceMsg);
		p.sendMessage("§2Replacement Messages: ");
		for (String msg : Variables.ReplaceWith)
		{
			p.sendMessage("  - §e" + msg);
		}
		p.sendMessage("§5=====================================================");
	}
	
	public static void cpSetMaxCapitalLetters(Player p, String max)
	{
		int maxInt = 0;
		try
		{
			maxInt = Integer.parseInt(max);
			p.sendMessage("§a[SimpleChat] §5MaxNumberOfCapitalLetters set to " + max);
			Variables.MaxNumberOfCapitalLetters = maxInt;
		}
		catch (Throwable t)
		{
			p.sendMessage("§a[SimpleChat] §5Error, expected integer value.");
			
		}
		
		Variables.plugin.saveYamls();
	}
	
	public static void cpSetPunishment(Player p, String punishment, String TorF)
	{
		if (punishment.equalsIgnoreCase("kick"))
		{
			if (TorF.equalsIgnoreCase("true"))
			{
				Variables.PunishmentKick = true;
				p.sendMessage("§a[SimpleChat] §3Punishment -> Kick set to true.");
			}
			else if (TorF.equalsIgnoreCase("false"))
			{
				Variables.PunishmentKick = false;
				p.sendMessage("§a[SimpleChat] §3Punishment -> Kick set to false.");
			}
			else
			{
				p.sendMessage("§a[SimpleChat] §4Please enter either true or false.");
			}
		}
		else if (punishment.equalsIgnoreCase("msgtoplayer") || punishment.equalsIgnoreCase("messagetoplayer"))
		{
			if (TorF.equalsIgnoreCase("true"))
			{
				Variables.PunishmentMsgToPlayer = true;
				p.sendMessage("§a[SimpleChat] §3Punishment -> MsgToPlayer set to true.");
			}
			else if (TorF.equalsIgnoreCase("false"))
			{
				Variables.PunishmentMsgToPlayer = false;
				p.sendMessage("§a[SimpleChat] §3Punishment -> MsgToPlayer set to false.");
			}
			else
			{
				p.sendMessage("§a[SimpleChat] §4Please enter either true or false.");
			}
		}
		else if (punishment.equalsIgnoreCase("replaceMsg") || punishment.equalsIgnoreCase("replacemessage"))
		{
			if (TorF.equalsIgnoreCase("true"))
			{
				Variables.PunishmentReplaceMsg = true;
				p.sendMessage("§a[SimpleChat] §3Punishment -> ReplaceMsg set to true.");
			}
			else if (TorF.equalsIgnoreCase("false"))
			{
				Variables.PunishmentReplaceMsg = false;
				p.sendMessage("§a[SimpleChat] §3Punishment -> ReplaceMsg set to false.");
			}
			else
			{
				p.sendMessage("§a[SimpleChat] §4Please enter either true or false.");
			}
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4Not a valid punishment. Punishments are: §7kick§4, §7msgtoplayer§4, §7replacemsg");
		}
		Variables.plugin.saveYamls();
	}
	
	public static void cpSetMsgToPlayer(Player p, String msg) // Unspecified
																// Args
	{
		Variables.MsgToPlayer = msg;
		p.sendMessage("§a[SimpleChat] §2ReplaceMsg set to: §e" + msg);
		Variables.plugin.saveYamls();
	}
	
	// +------------------------------------------------------+ //
	// | otherMessages | //
	// | | //
	// | Handles all of the otherMessahe commands: | //
	// | | //
	// +------------------------------------------------------+ //
	public static void omHelp(Player p)
	{
		p.sendMessage("§5================§c [ OtherMessages Help ] §5================");
		p.sendMessage("§c/scextra§7 om useOtherMessages [true/false] §e- Sets the use value to true or false.");
		p.sendMessage("§c/scextra§7 om view §e- Views the setup of the section.");
		p.sendMessage("§c/scextra§7 om variables §e- Views all the variables that can be used in the section.");
		p.sendMessage("§c/scextra§7 om setJoinMsg [msg...] §e- Sets the join msg.");
		p.sendMessage("§c/scextra§7 om setLeaveMsg  [msg...] §e- Sets the leave msg.");
		p.sendMessage("§c/scextra§7 om setKickMsg  [msg...] §e- Sets the kick msg.");
		p.sendMessage("§5=====================================================");
		
	}
	
	public static void omUse(Player p, String arg)
	{
		if (arg.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatOtherMessages = true;
			p.sendMessage("§a[SimpleChat] §3OtherMessages set to true.");
		}
		else if (arg.equalsIgnoreCase("false"))
		{
			Variables.UseSimpleChatOtherMessages = false;
			p.sendMessage("§a[SimpleChat] §3OtherMessages set to false.");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4Please enter either true or false.");
		}
		Variables.plugin.saveYamls();
	}
	
	public static void omVariables(Player p)
	{
		p.sendMessage("§5==================§c [ OtherMessages ] §5==================");
		p.sendMessage("§2Avaliable Variables: ");
		p.sendMessage("§2  +pname: §ePlayer Name ");
		p.sendMessage("§2  +dname: §eDisplay Name");
		p.sendMessage("§2  +pre: §ePlayer's Prefix ");
		p.sendMessage("§2  +suf: §ePlayer's Prefix");
		p.sendMessage("§2  +gro: §eGroup Name ");
		p.sendMessage("§2  +reason: §eReason for kick/ban (Only for kick/ban)");
		p.sendMessage("§5=====================================================");
	}
	
	public static void omView(Player p)
	{
		p.sendMessage("§5==================§c [ OtherMessages ] §5==================");
		p.sendMessage("§2UseSimpleChatOtherMessages: §e" + Variables.UseSimpleChatOtherMessages);
		p.sendMessage("§2Join Message: §e" + Variables.OtherMessagesJoin);
		p.sendMessage("§2Leave Message: §e" + Variables.OtherMessagesLeave);
		p.sendMessage("§2Kick Message: §e" + Variables.OtherMessagesKick);
		p.sendMessage("§5=====================================================");
	}
	
	public static void omSetJoinMsg(Player p, String msg) // Unspecified Args
	{
		Variables.OtherMessagesJoin = msg;
		p.sendMessage("§a[SimpleChat] §2Join Message set to: §e" + msg);
		Variables.plugin.saveYamls();
	}
	
	public static void omSetLeaveMsg(Player p, String msg) // Unspecified Args
	{
		Variables.OtherMessagesLeave = msg;
		p.sendMessage("§a[SimpleChat] §2Leave Message set to: §e" + msg);
		Variables.plugin.saveYamls();
	}
	
	public static void omSetKickMsg(Player p, String msg) // Unspecified Args
	{
		Variables.OtherMessagesKick = msg;
		p.sendMessage("§a[SimpleChat] §2Kick Message set to: §e" + msg);
		Variables.plugin.saveYamls();
	}
	
	// +------------------------------------------------------+ //
	// | dieMessage | //
	// | | //
	// | Handles all of the dieMessage commands: | //
	// | | //
	// +------------------------------------------------------+ //
	public static void dmHelp(Player p)
	{
		p.sendMessage("§5==================§c [ DieMessage Help ] §5=================");
		p.sendMessage("§c/scextra§7 dm useDieMessage [true/false] §e- Sets the use value to true or false.");
		p.sendMessage("§c/scextra§7 dm view §e- Views the setup of the section.");
		p.sendMessage("§c/scextra§7 dm variables §e- Views all the variables that can be used in the section.");
		p.sendMessage("§c/scextra§7 dm setDieMessage [die-event] [msg...] §e- Sets the die msg in the respective event.");
		p.sendMessage("§5=====================================================");
	}
	
	public static void dmUse(Player p, String arg)
	{
		if (arg.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatDieMessages = true;
			p.sendMessage("§a[SimpleChat] §3DieMessages set to true.");
		}
		else if (arg.equalsIgnoreCase("false"))
		{
			Variables.UseSimpleChatDieMessages = false;
			p.sendMessage("§a[SimpleChat] §3DieMessages set to false.");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4Please enter either true or false.");
		}
		Variables.plugin.saveYamls();
	}
	
	public static void dmVariables(Player p)
	{
		p.sendMessage("§5====================§c [ DieMessage ] §5===================");
		p.sendMessage("§2Avaliable Variables: ");
		p.sendMessage("§2  +pname: §ePlayer Name ");
		p.sendMessage("§2  +dname: §eDisplay Name");
		p.sendMessage("§2  +pre: §ePlayer's Prefix ");
		p.sendMessage("§2  +suf: §ePlayer's Prefix");
		p.sendMessage("§2  +gro: §eGroup Name ");
		p.sendMessage("§2  +other: §eOther entity involved");
		p.sendMessage("§5=====================================================");
	}
	
	public static void dmView(Player p)
	{
		p.sendMessage("§5====================§c [ DieMessage ] §5===================");
		p.sendMessage("§2UseSimpleChatDieMessages: §e" + Variables.UseSimpleChatDieMessages);
		for (Entry<String, String> entry : Variables.DieMessages.entrySet())
		{
			p.sendMessage(" - §2" + entry.getKey() + ": §e" + entry.getValue());
		}
		p.sendMessage("§5=====================================================");
	}
	
	public static void dmSetDieMsg(Player p, String dieEvent, String msg) // Unspecified
																			// Args
	{
		if (dieEvent.equalsIgnoreCase("deathInFire"))
		{
			Variables.DieMessages.put("deathInFire", msg);
			p.sendMessage("§a[SimpleChat] §2deathInFire die event changed to: §e" + msg);
		}
		else if (dieEvent.equalsIgnoreCase("deathOnFire"))
		{
			Variables.DieMessages.put("deathOnFire", msg);
			p.sendMessage("§a[SimpleChat] §2deathOnFire die event changed to: §e" + msg);
		}
		else if (dieEvent.equalsIgnoreCase("deathLava"))
		{
			Variables.DieMessages.put("deathLava", msg);
			p.sendMessage("§a[SimpleChat] §2deathLava die event changed to: §e" + msg);
		}
		else if (dieEvent.equalsIgnoreCase("deathInWall"))
		{
			Variables.DieMessages.put("deathInWall", msg);
			p.sendMessage("§a[SimpleChat] §2deathInWall die event changed to: §e" + msg);
		}
		else if (dieEvent.equalsIgnoreCase("deathDrowned"))
		{
			Variables.DieMessages.put("deathDrowned", msg);
			p.sendMessage("§a[SimpleChat] §2deathDrowned die event changed to: §e" + msg);
		}
		else if (dieEvent.equalsIgnoreCase("deathStarve"))
		{
			Variables.DieMessages.put("deathStarve", msg);
			p.sendMessage("§a[SimpleChat] §2deathStarve die event changed to: §e" + msg);
		}
		else if (dieEvent.equalsIgnoreCase("deathCactus"))
		{
			Variables.DieMessages.put("deathCactus", msg);
			p.sendMessage("§a[SimpleChat] §2deathCactus die event changed to: §e" + msg);
		}
		else if (dieEvent.equalsIgnoreCase("deathFall"))
		{
			Variables.DieMessages.put("deathFall", msg);
			p.sendMessage("§a[SimpleChat] §2deathFall die event changed to: §e" + msg);
		}
		else if (dieEvent.equalsIgnoreCase("deathOutOfWorld"))
		{
			Variables.DieMessages.put("deathOutOfWorld", msg);
			p.sendMessage("§a[SimpleChat] §2deathOutOfWorld die event changed to: §e" + msg);
		}
		else if (dieEvent.equalsIgnoreCase("deathGeneric"))
		{
			Variables.DieMessages.put("deathGeneric", msg);
			p.sendMessage("§a[SimpleChat] §2deathGeneric die event changed to: §e" + msg);
		}
		else if (dieEvent.equalsIgnoreCase("deathExplosion"))
		{
			Variables.DieMessages.put("deathExplosion", msg);
			p.sendMessage("§a[SimpleChat] §2deathExplosion die event changed to: §e" + msg);
		}
		else if (dieEvent.equalsIgnoreCase("deathMagic"))
		{
			Variables.DieMessages.put("deathMagic", msg);
			p.sendMessage("§a[SimpleChat] §2deathMagic die event changed to: §e" + msg);
		}
		else if (dieEvent.equalsIgnoreCase("deathSlainBy"))
		{
			Variables.DieMessages.put("deathSlainBy", msg);
			p.sendMessage("§a[SimpleChat] §2deathSlainBy die event changed to: §e" + msg);
		}
		else if (dieEvent.equalsIgnoreCase("deathArrow"))
		{
			Variables.DieMessages.put("deathArrow", msg);
			p.sendMessage("§a[SimpleChat] §2deathArrow die event changed to: §e" + msg);
		}
		else if (dieEvent.equalsIgnoreCase("deathFireball"))
		{
			Variables.DieMessages.put("deathFireball", msg);
			p.sendMessage("§a[SimpleChat] §2deathFireball die event changed to: §e" + msg);
		}
		else if (dieEvent.equalsIgnoreCase("deathThrown"))
		{
			Variables.DieMessages.put("deathThrown", msg);
			p.sendMessage("§a[SimpleChat] §2deathInFire die event changed to: §e" + msg);
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4Invalid death event.");
		}
		Variables.plugin.saveYamls();
	}
	
	// +------------------------------------------------------+ //
	// | joinMessage | //
	// | | //
	// | Handles all of the joinMessage commands: | //
	// | | //
	// +------------------------------------------------------+ //
	public static void jmHelp(Player p)
	{
		p.sendMessage("§5=================§c [ JoinMessage Help ] §5=================");
		p.sendMessage("§c/scextra§7 jm useJoinMessage [true/false] §e- Sets the use value to true or false.");
		p.sendMessage("§c/scextra§7 jm view §e- Views the setup of the section.");
		p.sendMessage("§c/scextra§7 jm variables §e- Views all the variables that can be used in the section.");
		p.sendMessage("§c/scextra§7 jm setLine [line#] [msg...] §e- Sets the private join msg sent to the player only.");
		p.sendMessage("§c/scextra§7 jm removeLine [line#] §e- Removes a line.");
		p.sendMessage("§5=====================================================");
	}
	
	public static void jmUse(Player p, String arg)
	{
		if (arg.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatJoinMsg = true;
			p.sendMessage("§a[SimpleChat] §3JoinMsg set to true.");
		}
		else if (arg.equalsIgnoreCase("false"))
		{
			Variables.UseSimpleChatJoinMsg = false;
			p.sendMessage("§a[SimpleChat] §3JoinMsg set to false.");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4Please enter either true or false.");
		}
		Variables.plugin.saveYamls();
	}
	
	public static void jmVariables(Player p)
	{
		p.sendMessage("§5===================§c [ JoinMessage ] §5====================");
		p.sendMessage("§2Avaliable Variables: ");
		p.sendMessage("§2  +pname: §ePlayer Name ");
		p.sendMessage("§2  +dname: §eDisplay Name");
		p.sendMessage("§2  +pre: §ePlayer's Prefix ");
		p.sendMessage("§2  +suf: §ePlayer's Prefix");
		p.sendMessage("§2  +gro: §eGroup Name ");
		p.sendMessage("§2  <onlineplayers>: §eInfo below.");
		p.sendMessage("§2Syntax: §e<onlineplayers:playernamecolor,commacolor,group>");
		p.sendMessage("§2Examples of <onlineplayers> being used:");
		p.sendMessage("§c  <onlineplayers:&4,&c,Admin> §e- this will make names dark red (&4), commas light red (&c), and only display the admin group.");
		p.sendMessage("§c  <onlineplayers:&1,&7> §e- this will make names dark blue (&1) and commas gray (&7). It will display everyone because no group was specified.");
		p.sendMessage("§c  <onlineplayers> §e- this is display all players, and it will all be white, because no colors were specified.");
		p.sendMessage("§c  <onlineplayers:&e,&8,default> §e- this is make names yellow (&e), commas dark gray (&8), and only players from the default group.");
		p.sendMessage("§5=====================================================");
	}
	
	public static void jmView(Player p)
	{
		p.sendMessage("§5===================§c [ JoinMessage ] §5====================");
		p.sendMessage("§2UseSimpleChatJoinMsg: §e" + Variables.UseSimpleChatJoinMsg);
		int counter = 1;
		for (String msg : Variables.JoinMsgToPlayer)
		{
			p.sendMessage("§c(" + counter + ") §2" + msg);
			counter++;
		}
		p.sendMessage("§5=====================================================");
	}
	
	public static void jmSetLine(Player p, String lineNum, String msg) // Unspecified
																		// Args
	{
		int line = 0;
		try
		{
			line = Integer.parseInt(lineNum);
		}
		catch (Throwable t)
		{
			p.sendMessage("§a[SimpleChat] §4Error, expected an integer for line number");
			return;
		}
		p.sendMessage("§a[SimpleChat] §2Successfully changed line number " + line + " to §e" + msg);
		if (line < Variables.JoinMsgToPlayer.size())
		{
			Variables.JoinMsgToPlayer.set(line - 1, msg);
		}
		else
		{
			Variables.JoinMsgToPlayer.add(msg);
		}
		
		Variables.plugin.saveYamls();
	}
	
	public static void jmRemoveLine(Player p, String lineNum)
	{
		int line = 0;
		try
		{
			line = Integer.parseInt(lineNum);
		}
		catch (Throwable t)
		{
			p.sendMessage("§a[SimpleChat] §4Error, expected an integer for line number");
			return;
		}
		
		if (line > Variables.JoinMsgToPlayer.size() || line < 0)
		{
			p.sendMessage("§a[SimpleChat] §4Error, the line number you provided was either too big or too small");
			return;
		}
		
		Variables.JoinMsgToPlayer.remove(line - 1);
		p.sendMessage("§a[SimpleChat] §2Successfully removed line number " + line);
		Variables.plugin.saveYamls();
	}
	
	// +------------------------------------------------------+ //
	// | General Format | //
	// | | //
	// | Handles all of the general format commands: | //
	// | | //
	// +------------------------------------------------------+ //
	public static void gfHelp(Player p)
	{
		p.sendMessage("§5==================§c [ GeneralFormat ] §5==================");
		p.sendMessage("§c/scextra§7 gf useGeneralFormat [true/false] §e- Sets the use value to true or false.");
		p.sendMessage("§c/scextra§7 gf view §e- Views the setup of the section.");
		p.sendMessage("§c/scextra§7 gf variables §e- Views all the variables that can be used in the section.");
		p.sendMessage("§c/scextra§7 gf setMeFormat [format...] §e- Sets the me format.");
		p.sendMessage("§c/scextra§7 gf setSayFormat [format...] §e- Sets the say format.");
		p.sendMessage("§c/scextra§7 gf setBroadcastFormat [format...] §e- Sets the broadcast format.");
		p.sendMessage("§5=====================================================");
	}
	
	public static void gfUse(Player p, String arg)
	{
		if (arg.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatGeneralFormatting = true;
			p.sendMessage("§a[SimpleChat] §3GeneralFormatting set to true.");
		}
		else if (arg.equalsIgnoreCase("false"))
		{
			Variables.UseSimpleChatGeneralFormatting = false;
			p.sendMessage("§a[SimpleChat] §3GeneralFormatting set to false.");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4Please enter either true or false.");
		}
		Variables.plugin.saveYamls();
	}
	
	public static void gfVariables(Player p)
	{
		p.sendMessage("§5==================§c [ GeneralFormat ] §5==================");
		p.sendMessage("§2Avaliable Variables for /me: ");
		p.sendMessage("§2  +pname: §ePlayer Name ");
		p.sendMessage("§2  +dname: §eDisplay Name");
		p.sendMessage("§2  +pre: §ePlayer's Prefix ");
		p.sendMessage("§2  +suf: §ePlayer's Prefix");
		p.sendMessage("§2  +gro: §eGroup Name ");
		p.sendMessage("§2Avaliable Variables for all: ");
		p.sendMessage("§2  +msg: §eThe message ");
		p.sendMessage("§5=====================================================");
	}
	
	public static void gfView(Player p)
	{
		p.sendMessage("§5==================§c [ GeneralFormat ] §5==================");
		p.sendMessage("§2UseSimpleChatMeFormatting: §e" + Variables.UseSimpleChatGeneralFormatting);
		p.sendMessage("§2MeFormat: §e" + Variables.MeFormat);
		p.sendMessage("§2SayFormat: §e" + Variables.SayFormat);
		p.sendMessage("§2BroadcastFormat: §e" + Variables.BroadcastFormat);
		p.sendMessage("§5=====================================================");
	}
	
	public static void gfSetMeFormat(Player p, String msg) // Unspecified Args
	{
		Variables.MeFormat = msg;
		p.sendMessage("§a[SimpleChat] §2MeFormat set to §e" + msg);
		Variables.plugin.saveYamls();
	}
	
	public static void gfSetSayFormat(Player p, String msg) // Unspecified Args
	{
		Variables.SayFormat = msg;
		p.sendMessage("§a[SimpleChat] §2SayFormat set to §e" + msg);
		Variables.plugin.saveYamls();
	}
	
	public static void gfSetBroadcastFormat(Player p, String msg) // Unspecified
																	// Args
	{
		Variables.BroadcastFormat = msg;
		p.sendMessage("§a[SimpleChat] §2BroadcastFormat set to §e" + msg);
		Variables.plugin.saveYamls();
	}
	
	// +------------------------------------------------------+ //
	// | Msging Format | //
	// | | //
	// | Handles all of the me commands: | //
	// | | //
	// +------------------------------------------------------+ //
	public static void mrHelp(Player p)
	{
		p.sendMessage("§5=================§c [ Msg And Reply Help ] §5================");
		p.sendMessage("§c/scextra§7 mr useMsgAndReply [true/false] §e- Sets the use value to true or false.");
		p.sendMessage("§c/scextra§7 mr view §e- Views the setup of the section.");
		p.sendMessage("§c/scextra§7 mr variables §e- Views all the possible variables.");
		p.sendMessage("§c/scextra§7 mr setSendingMsg [format...] §e- Sets the sending format.");
		p.sendMessage("§c/scextra§7 mr setReceivingMsg [format...] §e- Sets the receiving format.");
		p.sendMessage("§5=====================================================");
	}
	
	public static void mrUse(Player p, String arg)
	{
		if (arg.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatMsgAndReplyFormatting = true;
			p.sendMessage("§a[SimpleChat] §3MsgAndReplyFormatting set to true.");
		}
		else if (arg.equalsIgnoreCase("false"))
		{
			Variables.UseSimpleChatMsgAndReplyFormatting = false;
			p.sendMessage("§a[SimpleChat] §3MsgAndReplyFormatting set to false.");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4Please enter either true or false.");
		}
		Variables.plugin.saveYamls();
	}
	
	public static void mrView(Player p)
	{
		p.sendMessage("§5===================§c [ Msg And Reply ] §5==================");
		p.sendMessage("§2UseSimpleChatMsgAndReplyFormatting: §e" + Variables.UseSimpleChatMsgAndReplyFormatting);
		p.sendMessage("§2SendingMessage: §e" + Variables.SendingMessage);
		p.sendMessage("§2ReceivingMessage §e" + Variables.ReceivingMessage);
		p.sendMessage("§5=====================================================");
	}
	
	public static void mrVariables(Player p)
	{
		p.sendMessage("§5===================§c [ Msg And Reply ] §5==================");
		p.sendMessage("§2Avaliable Variables: ");
		p.sendMessage("§2  +pname: §ePlayer Name ");
		p.sendMessage("§2  +dname: §eDisplay Name");
		p.sendMessage("§2  +pre: §ePlayer's Prefix ");
		p.sendMessage("§2  +suf: §ePlayer's Prefix");
		p.sendMessage("§2  +gro: §eGroup Name ");
		p.sendMessage("§2  -----------------------");
		p.sendMessage("§2  +otherpname: §ePlayer Name ");
		p.sendMessage("§2  +otherdname: §eDisplay Name");
		p.sendMessage("§2  +otherpre: §ePlayer's Prefix ");
		p.sendMessage("§2  +othersuf: §ePlayer's Prefix");
		p.sendMessage("§2  +othergro: §eGroup Name ");
		p.sendMessage("§2  -----------------------");
		p.sendMessage("§2  +msg: §eThe message ");
		p.sendMessage("§5=====================================================");
	}
	
	public static void mrSetSendingMsg(Player p, String format) // Unspecified
																// Args
	{
		Variables.SendingMessage = format;
		p.sendMessage("§a[SimpleChat] §2SendingMessage format set to §e" + format);
		Variables.plugin.saveYamls();
	}
	
	public static void mrSetReceivingMsg(Player p, String format) // Unspecified
																	// Args
	{
		Variables.ReceivingMessage = format;
		p.sendMessage("§a[SimpleChat] §2Message format set to §e" + format);
		Variables.plugin.saveYamls();
	}
	
	// +------------------------------------------------------+ //
	// | Admin Chat | //
	// | | //
	// | Handles all of the ac commands: | //
	// | | //
	// +------------------------------------------------------+ //
	public static void acHelp(Player p)
	{
		p.sendMessage("§5===================§c [ AdminChat Help ] §5==================");
		p.sendMessage("§c/scextra§7 ac useAdminChat [true/false] §e- Sets the use value to true or false.");
		p.sendMessage("§c/scextra§7 ac view §e- Views the setup of the section.");
		p.sendMessage("§c/scextra§7 ac variables §e- Views all the possible variables.");
		p.sendMessage("§c/scextra§7 ac SetAdminChatFormat [format...] §e- Sets the sending format.");
		p.sendMessage("§5=====================================================");
	}
	
	public static void acUse(Player p, String arg)
	{
		if (arg.equalsIgnoreCase("true"))
		{
			Variables.UseSimpleChatAdminChat = true;
			p.sendMessage("§a[SimpleChat] §3SimpleChatAdminChat set to true.");
		}
		else if (arg.equalsIgnoreCase("false"))
		{
			Variables.UseSimpleChatAdminChat = false;
			p.sendMessage("§a[SimpleChat] §3SimpleChatAdminChat set to false.");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4Please enter either true or false.");
		}
		Variables.plugin.saveYamls();
	}
	
	public static void acView(Player p)
	{
		p.sendMessage("§5=====================§c [ AdminChat ] §5====================");
		p.sendMessage("§2UseSimpleChatAdminChat: §e" + Variables.UseSimpleChatAdminChat);
		p.sendMessage("§2AdminChatFormat: §e" + Variables.AdminChatFormat);
		p.sendMessage("§5=====================================================");
	}
	
	public static void acVariables(Player p)
	{
		p.sendMessage("§5=====================§c [ AdminChat ] §5====================");
		p.sendMessage("§2Avaliable Variables: ");
		p.sendMessage("§2  +pname: §ePlayer Name ");
		p.sendMessage("§2  +dname: §eDisplay Name");
		p.sendMessage("§2  +pre: §ePlayer's Prefix ");
		p.sendMessage("§2  +suf: §ePlayer's Prefix");
		p.sendMessage("§2  +gro: §eGroup Name ");
		p.sendMessage("§2  +msg: §eThe message ");
		p.sendMessage("§5=====================================================");
	}
	
	public static void acSetAdminChatFormat(Player p, String format) // Unspecified
	// Args
	{
		Variables.AdminChatFormat = format;
		p.sendMessage("§a[SimpleChat] §2AdminChatFormat format set to §e" + format);
		Variables.plugin.saveYamls();
	}
	
	
	
	
	// +------------------------------------------------------+ //
		// | Party Chat | //
		// | | //
		// | Handles all of the pc commands: | //
		// | | //
		// +------------------------------------------------------+ //
		public static void pcHelp(Player p)
		{
			p.sendMessage("§5==================§c [ PartyChat Help ] §5==================");
			p.sendMessage("§c/scextra§7 pc usePartyChat [true/false] §e- Sets the use value to true or false.");
			p.sendMessage("§c/scextra§7 pc view §e- Views the setup of the section.");
			p.sendMessage("§c/scextra§7 pc variables §e- Views all the possible variables.");
			p.sendMessage("§c/scextra§7 pc SetPartyChatFormat [format...] §e- Sets the partychat format.");
			p.sendMessage("§5=====================================================");
		}
		
		public static void pcUse(Player p, String arg)
		{
			if (arg.equalsIgnoreCase("true"))
			{
				Variables.UseSimpleChatPartyChat = true;
				p.sendMessage("§a[SimpleChat] §3SimpleChatPartyChat set to true.");
			}
			else if (arg.equalsIgnoreCase("false"))
			{
				Variables.UseSimpleChatPartyChat = false;
				p.sendMessage("§a[SimpleChat] §3SimpleChatPartyChat set to false.");
			}
			else
			{
				p.sendMessage("§a[SimpleChat] §4Please enter either true or false.");
			}
			Variables.plugin.saveYamls();
		}
		
		public static void pcView(Player p)
		{
			p.sendMessage("§5=====================§c [ PartyChat ] §5====================");
			p.sendMessage("§2UseSimpleChatPartyChat: §e" + Variables.UseSimpleChatPartyChat);
			p.sendMessage("§2PartyChatFormat: §e" + Variables.PartyChatFormat);
			p.sendMessage("§2Parties: §ePlease use \"/party list\" to view party information");
			p.sendMessage("§5=====================================================");
		}
		
		public static void pcVariables(Player p)
		{
			p.sendMessage("§5=====================§c [ PartyChat ] §5====================");
			p.sendMessage("§2Avaliable Variables: ");
			p.sendMessage("§2  +pname: §ePlayer Name ");
			p.sendMessage("§2  +dname: §eDisplay Name");
			p.sendMessage("§2  +partyname: §eParty Name");
			p.sendMessage("§2  +pre: §ePlayer's Prefix ");
			p.sendMessage("§2  +suf: §ePlayer's Prefix");
			p.sendMessage("§2  +gro: §eGroup Name ");
			p.sendMessage("§2  +msg: §eThe message ");
			p.sendMessage("§5=====================================================");
		}
		
		public static void pcSetPartyChatFormat(Player p, String format) // Unspecified
		// Args
		{
			Variables.PartyChatFormat = format;
			p.sendMessage("§a[SimpleChat] §2PartyChatFormat format set to §e" + format);
			Variables.plugin.saveYamls();
		}
}
