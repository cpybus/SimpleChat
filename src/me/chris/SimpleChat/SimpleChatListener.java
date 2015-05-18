package me.chris.SimpleChat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;

import me.chris.SimpleChat.CommandHandler.SimpleChatAdminChat;
import me.chris.SimpleChat.CommandHandler.SimpleChatAdminChatConsole;
import me.chris.SimpleChat.CommandHandler.SimpleChatCommandHandler;
import me.chris.SimpleChat.CommandHandler.SimpleChatOtherCommands;
import me.chris.SimpleChat.CommandHandler.SimpleChatOtherCommandsConsole;
import me.chris.SimpleChat.CommandHandler.SimpleChatPrivateMessaging;
import me.chris.SimpleChat.CommandHandler.SimpleChatPrivateMessagingConsole;
import me.chris.SimpleChat.CommandHandler.SimpleChatSocialSpy;
import me.chris.SimpleChat.PartyHandler.Party;
import me.chris.SimpleChat.PartyHandler.PartyAPI;
import me.chris.SimpleChat.PartyHandler.PartyChat;
import me.chris.SimpleChat.PartyHandler.PartyCommands;
import mkremins.fanciful.FancyMessage;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class SimpleChatListener implements Listener
{
	// +------------------------------------------------------+ //
	// | Player Join | //
	// | | //
	// | For when a player joins. Ton of stuff going on in | //
	// | This listener, look through code carefully. | //
	// +------------------------------------------------------+ //
	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerJoin(PlayerJoinEvent event)
	{
		Player p = event.getPlayer();
		
		if (PartyAPI.isPlayerInAParty(p))
		{
			Party pty = PartyAPI.findPartyofPlayer(p);
			pty.addOnlineMember(p);
		}
		
		if (Variables.perms.has(p, "SimpleChat.socialspy") || Variables.perms.has(p, "SimpleChat.*"))
		{
			Variables.onlineSocialSpy.add(p);
		}
		
		if (Variables.perms.has(p, "SimpleChat.adminchat") || Variables.perms.has(p, "SimpleChat.*"))
		{
			Variables.onlineAdminChat.add(p);
		}
		
		if (p.isOp())
		{
			String lastestVersion = "";
			try
			{
				lastestVersion = SimpleChatHelperMethods.updateCheck();
			}
			catch (Throwable t)
			{
				
			}
			
			if (!lastestVersion.equalsIgnoreCase(Variables.version))
			{
				p.sendMessage("§5=====================================================");
				p.sendMessage("§4 Warning!§f This isnt the lastest version of SimpleChat!");
				p.sendMessage("§c " + lastestVersion + "§f Is out! (This is §c" + Variables.version + "§f)");
				p.sendMessage("§5=====================================================");
			}
		}
		
		if (Variables.UseSimpleChatOtherMessages == true)
		{
			String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "&" };
			String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p),
					SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), "§" };
			String joinMsg = SimpleChatHelperMethods.replaceVars(Variables.OtherMessagesJoin, vars, replace);
			event.setJoinMessage(joinMsg);
		}
		
		if (Variables.UseSimpleChatJoinMsg == true)
		{
			ArrayList<String> joinMsgs = Variables.JoinMsgToPlayer;
			
			for (int counter = 0; counter < joinMsgs.size(); counter++)
			{
				String msg = joinMsgs.get(counter);
				String originalMsg = msg;
				
				ArrayList<Integer> angleBrackets = new ArrayList<Integer>();
				
				for (int index = 0; index < msg.length(); index++)
				{
					if (msg.charAt(index) == '<')
					{
						angleBrackets.add(index);
						angleBrackets.add(msg.indexOf((char) 62, index));
					}
				}
				
				if (angleBrackets.isEmpty())
				{
					String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "&" };
					String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p),
							SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), "§" };
					msg = SimpleChatHelperMethods.replaceVars(msg, vars, replace);
					
					p.sendMessage(msg);
					continue;
				}
				
				if (angleBrackets.size() % 2 != 0 || angleBrackets.contains(-1))
				{
					Variables.log.log(Level.WARNING, "The onlineplayers variable on Line " + (counter + 1)
							+ " in the JoinMsgToPlayer section of extra.yml is NOT in the proper format. Please fix.");
					String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "&" };
					String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p),
							SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), "§" };
					msg = SimpleChatHelperMethods.replaceVars(msg, vars, replace);
					
					p.sendMessage(msg);
					continue;
				}
				
				for (int index = 0; index < angleBrackets.size(); index += 2)
				{
					String substring = originalMsg.substring((angleBrackets.get(index) + 1),
							(angleBrackets.get(index + 1)));
					String nameColor = "&f";
					String commaColor = "&f";
					String group = "all";
					if (substring.length() >= 13 && substring.substring(0, 13).equalsIgnoreCase("onlineplayers"))
					{
						if (substring.length() == 13 && !substring.contains(":"))
						{
							msg = msg.replaceAll("<" + substring + ">",
									SimpleChatHelperMethods.onlinePlayers(nameColor, commaColor, group));
						}
						else if (substring.contains(":"))
						{
							String[] split1 = substring.split(":");
							if (split1[1].contains(","))
							{
								String[] variables = split1[1].split(",");
								if (variables.length == 1)
								{
									msg = msg.replaceAll("<" + substring + ">",
											SimpleChatHelperMethods.onlinePlayers(variables[0], commaColor, group));
								}
								else if (variables.length == 2)
								{
									msg = msg.replaceAll("<" + substring + ">",
											SimpleChatHelperMethods.onlinePlayers(variables[0], variables[1], group));
								}
								else if (variables.length == 3)
								{
									msg = msg.replaceAll("<" + substring + ">", SimpleChatHelperMethods.onlinePlayers(
											variables[0], variables[1], variables[2]));
								}
							}
							else
							{
								msg = msg.replaceAll("<" + substring + ">",
										SimpleChatHelperMethods.onlinePlayers(split1[1], commaColor, group));
							}
						}
					}
					else
					{
						Variables.log
								.log(Level.WARNING,
										"The onlineplayers variable on Line "
												+ (counter + 1)
												+ " in the JoinMsgToPlayer section of extra.yml is NOT in the proper format. Please fix.");
					}
				}
				
				String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "&" };
				String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p),
						SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), "§" };
				msg = SimpleChatHelperMethods.replaceVars(msg, vars, replace);
				
				p.sendMessage(msg);
			}
		}
	}
	
	// +------------------------------------------------------+ //
	// | Player Chat | //
	// | | //
	// | For when a player chats. Ton of stuff going on in | //
	// | This listener, look through code carefully. | //
	// +------------------------------------------------------+ //
	
	@EventHandler(priority = EventPriority.LOW)
	public void playerChat(AsyncPlayerChatEvent event)
	{
		Player p = event.getPlayer();
		String msg = event.getMessage();
		
		if (Variables.adminChat.contains(p))
		{
			event.setCancelled(true);
			SimpleChatAdminChat.adminChat(p, msg);
			return;
		}
		
		if (Variables.lockedPM.containsKey(p))
		{
			event.setCancelled(true);
			SimpleChatPrivateMessaging.lockedPMChat(p, msg);
			return;
		}
		
		if (Variables.lockedPartyChat.containsKey(p))
		{
			event.setCancelled(true);
			PartyChat.partyChat(p, msg);
			return;
		}
		
		msg = msg.replace("%", "%%");
		
		if (SimpleChatChatState.getChatState().equalsIgnoreCase("off")
				&& !Variables.perms.has(p, "simplechat.chatoffbypass"))
		{
			event.setCancelled(true);
			p.sendMessage("§a[SimpleChat]§4 An admin has turned off chat.");
			return;
		}
		
		if (Variables.matching == false)
		{
			return;
		}
		
		// Replaces variables, but only in the descriptor part. (Not the message
		// itself)
		String playerChat = SimpleChatHelperMethods.replaceVars(Variables.MessageFormat, new String[] { "+pre", "+suf",
				"+pname", "+dname", "+gro", "&" },
				new String[] { SimpleChatAPI.getPrefix(p), SimpleChatAPI.getSuffix(p), event.getPlayer().getName(),
						event.getPlayer().getDisplayName(), SimpleChatAPI.getGroup(p), "§" });
		
		// All of the following formats and censors the message itself
		// Checks to see if sever uses the censor.
		if (Variables.UseSimpleChatCensor == true)
		{
			if (!Variables.perms.has(p, "simplechat.cancurse"))
			{
				msg = SimpleChatHelperMethods.censorMessage(msg, '*', Variables.CurseWords);
			}
		}
		
		// Checks to see if the server uses caps preventer
		if (Variables.UseSimpleChatCapsPreventer == true)
		{
			if (!Variables.perms.has(p, "simplechat.canusecaps"))
			{
				msg = SimpleChatHelperMethods.noCaps(msg, p);
				if (msg.equals("_KICKED_"))
				{
					event.setCancelled(true);
					return;
				}
			}
		}
		
		// Checks for perms for each color "&"
		if (msg.contains("&"))
		{
			char[] msgChar = msg.toCharArray();
			boolean permsStatus = true;
			HashMap<Integer, Character> indexes = new HashMap<Integer, Character>();
			int counter = 0;
			for (Character c : msgChar)
			{
				if (c.equals('&'))
				{
					if (counter < msgChar.length - 1)
					{
						indexes.put(counter, msgChar[counter + 1]);
					}
					else
					{
						indexes.put(counter, ' ');
					}
				}
				counter++;
			}
			
			for (Entry<Integer, Character> entry : indexes.entrySet())
			{
				if (entry.getValue() == ' ')
				{
					continue;
				}
				
				if (Variables.perms.has(p, "simplechat.color") || Variables.perms.has(p, "simplechat.color.*")
						|| Variables.perms.has(p, ("simplechat.color." + entry.getValue())))
				{
					msgChar[entry.getKey()] = '§';
				}
				else
				{
					permsStatus = false;
				}
			}
			
			if (permsStatus == false)
			{
				p.sendMessage("§a[SimpleChat] §4You dont have perms for one (or all) of the color values you tried to use!");
			}
			
			msg = "";
			for (Character c : msgChar)
			{
				msg = msg + c;
			}
		}
		
		playerChat = playerChat.replace("+msg", msg);
		event.setFormat(playerChat);
	}
	
	// +------------------------------------------------------+ //
	// | Player Quit and Player Kick | //
	// | | //
	// | Here are the two listeners for when a player quits | //
	// | And when a player is kicked. Enjoy. | //
	// +------------------------------------------------------+ //
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player p = event.getPlayer();
		
		if (Variables.UseSimpleChatOtherMessages == true)
		{
			String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "&" };
			String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p),
					SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), "§" };
			
			String leaveMsg = SimpleChatHelperMethods.replaceVars(Variables.OtherMessagesLeave, vars, replace);
			
			event.setQuitMessage(leaveMsg);
		}
		
		if (PartyAPI.isPlayerInAParty(p))
		{
			Party pty = PartyAPI.findPartyofPlayer(p);
			pty.removeOnlineMember(p);
		}
		
		if (Variables.perms.has(p, "SimpleChat.socialspy") || Variables.perms.has(p, "SimpleChat.*"))
		{
			Variables.onlineSocialSpy.remove(p);
		}
		
		if (Variables.perms.has(p, "SimpleChat.adminchat") || Variables.perms.has(p, "SimpleChat.*"))
		{
			Variables.onlineAdminChat.remove(p);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerKick(PlayerKickEvent event)
	{
		Player p = event.getPlayer();
		String reason = event.getReason();
		if (Variables.UseSimpleChatOtherMessages == true)
		{
			String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "+reason", "&" };
			String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p),
					SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), reason, "§" };
			
			String kickMsg = SimpleChatHelperMethods.replaceVars(Variables.OtherMessagesKick, vars, replace);
			event.setLeaveMessage(kickMsg);
		}
		
		if (PartyAPI.isPlayerInAParty(p))
		{
			Party pty = PartyAPI.findPartyofPlayer(p);
			pty.removeOnlineMember(p);
		}
		
		if (Variables.perms.has(p, "SimpleChat.socialspy") || Variables.perms.has(p, "SimpleChat.*"))
		{
			Variables.onlineSocialSpy.remove(p);
		}
		
		if (Variables.perms.has(p, "SimpleChat.adminchat") || Variables.perms.has(p, "SimpleChat.*"))
		{
			Variables.onlineAdminChat.remove(p);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		if (Variables.UseSimpleChatDieMessages == true)
		{
			Player player = event.getEntity();
			
			String pName = player.getName();
			String dName = player.getDisplayName();
			String damager = "";
			
			EntityDamageEvent dEvent = player.getLastDamageCause();
			
			System.out.println(dEvent.getEntity());
			
			if (dEvent instanceof EntityDamageByEntityEvent)
			{
				EntityDamageByEntityEvent dEEvent = (EntityDamageByEntityEvent) dEvent;
				if (dEEvent.getDamager() instanceof Player)
					damager = ((Player) dEEvent.getDamager()).getName();
				else
				{
					damager = "a" + SimpleChatHelperMethods.parseEntityName(dEEvent.getDamager());
				}
			}
			
			String dMsg = event.getDeathMessage();
			
			String finalDeathMsg = "";
			
			if (dMsg.contains("went up in flames"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathInFire");
			}
			else if (dMsg.contains("burned to death"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathOnFire");
			}
			else if (dMsg.contains("tried to swim in lava"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathLava");
			}
			else if (dMsg.contains("suffocated in a wall"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathInWall");
			}
			else if (dMsg.contains("drowned"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathDrowned");
			}
			else if (dMsg.contains("starved to death"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathStarve");
			}
			else if (dMsg.contains("was pricked to death"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathCactus");
			}
			else if (dMsg.contains("hit the ground too hard"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathFall");
			}
			else if (dMsg.contains("fell out of the world"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathOutOfWorld");
			}
			else if (dMsg.contains("died"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathGeneric");
			}
			else if (dMsg.contains("blew up"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathExplosion");
			}
			else if (dMsg.contains("was killed by magic"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathMagic");
			}
			else if (dMsg.contains("was slain by"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathSlainBy");
			}
			else if (dMsg.contains("was shot by"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathArrow");
			}
			else if (dMsg.contains("was fireballed by"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathFireball");
			}
			else if (dMsg.contains("was pummeled by"))
			{
				finalDeathMsg = Variables.DieMessages.get("deathThrown");
			}
			String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "+other", "&" };
			String[] replace = { pName, dName, SimpleChatAPI.getPrefix(player), SimpleChatAPI.getSuffix(player),
					SimpleChatAPI.getGroup(player), damager, "§" };
			
			event.setDeathMessage(SimpleChatHelperMethods.replaceVars(finalDeathMsg, vars, replace));
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event)
	{
		String message = event.getMessage();
		String[] tempArgs = message.split(" ");
		String cmd = tempArgs[0].replace("/", "");
		String[] args = new String[tempArgs.length - 1];
		
		if (args.length > 0)
		{
			for (int index = 1; index < tempArgs.length; index++)
			{
				args[(index - 1)] = tempArgs[index];
			}
		}
		
		Player p = event.getPlayer();
		
		if (((cmd.equalsIgnoreCase("message")) || (cmd.equalsIgnoreCase("msg")) || (cmd.equalsIgnoreCase("m"))
				|| (cmd.equalsIgnoreCase("tell")) || (cmd.equalsIgnoreCase("whisper")) || (cmd.equalsIgnoreCase("pm")))
				&& (Variables.UseSimpleChatMsgAndReplyFormatting))
		{
			if ((Variables.perms.has(p, "simplechat.msg")) || (Variables.perms.has(p, "simplechat.*")))
			{
				if (args.length > 1)
				{
					if (args[0].equalsIgnoreCase("Console"))
					{
						SimpleChatPrivateMessagingConsole
								.msgToConsole(p, SimpleChatCommandHandler.combineArgs(1, args));
					}
					else
					{
						SimpleChatPrivateMessaging.msg(p, args[0], SimpleChatCommandHandler.combineArgs(1, args));
					}
				}
				else if (args.length == 1)
				{
					SimpleChatPrivateMessaging.lockPM(p, args[0]);
				}
				else if ((args.length == 0) && (Variables.lockedPM.containsKey(p)))
				{
					SimpleChatPrivateMessaging.unlockPM(p);
				}
				else
				{
					SimpleChatCommandHandler.invalidCommand(p, cmd, args);
				}
			}
			else
			{
				SimpleChatCommandHandler.noPerms(p, cmd, args);
			}
			
			event.setCancelled(true);
		}
		else if (((cmd.equalsIgnoreCase("reply")) || (cmd.equalsIgnoreCase("r")))
				&& (Variables.UseSimpleChatMsgAndReplyFormatting))
		{
			if ((Variables.perms.has(p, "simplechat.reply")) || (Variables.perms.has(p, "simplechat.*")))
			{
				if (args.length >= 1)
				{
					SimpleChatPrivateMessaging.reply(p, SimpleChatCommandHandler.combineArgs(0, args));
				}
				else
				{
					SimpleChatCommandHandler.invalidCommand(p, cmd, args);
				}
			}
			else
			{
				SimpleChatCommandHandler.noPerms(p, cmd, args);
			}
			event.setCancelled(true);
		}
		else if ((cmd.equalsIgnoreCase("me")) && (Variables.UseSimpleChatGeneralFormatting))
		{
			if ((Variables.perms.has(p, "simplechat.me")) || (Variables.perms.has(p, "simplechat.*")))
			{
				if (args.length >= 1)
				{
					SimpleChatOtherCommands.me(p, args);
				}
				else
				{
					SimpleChatCommandHandler.invalidCommand(p, cmd, args);
				}
			}
			else
			{
				SimpleChatCommandHandler.noPerms(p, cmd, args);
			}
			
			event.setCancelled(true);
		}
		else if ((cmd.equalsIgnoreCase("say")) && (Variables.UseSimpleChatGeneralFormatting))
		{
			if ((Variables.perms.has(p, "simplechat.say")) || (Variables.perms.has(p, "simplechat.*")))
			{
				if (args.length >= 1)
				{
					SimpleChatOtherCommands.say(p, args);
				}
				else
				{
					SimpleChatCommandHandler.invalidCommand(p, cmd, args);
				}
			}
			else
			{
				SimpleChatCommandHandler.noPerms(p, cmd, args);
			}
			
			event.setCancelled(true);
		}
		else if (((cmd.equalsIgnoreCase("broadcast")) || (cmd.equalsIgnoreCase("bcast")))
				&& (Variables.UseSimpleChatGeneralFormatting))
		{
			if ((Variables.perms.has(p, "simplechat.broadcast")) || (Variables.perms.has(p, "simplechat.*")))
			{
				if (args.length >= 1)
				{
					SimpleChatOtherCommands.broadcast(p, args);
				}
				else
				{
					SimpleChatCommandHandler.invalidCommand(p, cmd, args);
				}
			}
			else
			{
				SimpleChatCommandHandler.noPerms(p, cmd, args);
			}
			
			event.setCancelled(true);
		}
		else if (((cmd.equalsIgnoreCase("a")) || (cmd.equalsIgnoreCase("admin")) || (cmd.equalsIgnoreCase("achat")) || (cmd
				.equalsIgnoreCase("adminchat"))) && (Variables.UseSimpleChatAdminChat))
		{
			if ((Variables.perms.has(p, "simplechat.adminchat")) || (Variables.perms.has(p, "simplechat.*")))
			{
				if (args.length == 0)
				{
					SimpleChatAdminChat.toggleAdminChat(p);
				}
				else if (args.length >= 1)
				{
					String msg = "";
					for (String word : args)
					{
						msg = msg + " " + word;
					}
					msg = msg.trim();
					SimpleChatAdminChat.adminChat(p, msg);
				}
				else
				{
					SimpleChatCommandHandler.invalidCommand(p, cmd, args);
				}
			}
			else
			{
				SimpleChatCommandHandler.noPerms(p, cmd, args);
			}
			
			event.setCancelled(true);
		}
		else if (((cmd.equalsIgnoreCase("sspm")) || (cmd.equalsIgnoreCase("socialspypm")))
				&& (Variables.UseSimpleChatMsgAndReplyFormatting))
		{
			if ((Variables.perms.has(p, "simplechat.socialspy")) || (Variables.perms.has(p, "simplechat.*")))
			{
				if (args.length == 0)
				{
					SimpleChatSocialSpy.toggleSocialSpyPM(p);
				}
				else
				{
					SimpleChatCommandHandler.invalidCommand(p, cmd, args);
				}
			}
			else
			{
				SimpleChatCommandHandler.noPerms(p, cmd, args);
			}
			
			event.setCancelled(true);
		}
		else if (((cmd.equalsIgnoreCase("ssparty")) || (cmd.equalsIgnoreCase("socialspyparty")))
				&& (Variables.UseSimpleChatPartyChat))
		{
			if ((Variables.perms.has(p, "simplechat.socialspy")) || (Variables.perms.has(p, "simplechat.*")))
			{
				if (args.length == 0)
				{
					SimpleChatSocialSpy.toggleSocialSpyParty(p);
				}
				else
				{
					SimpleChatCommandHandler.invalidCommand(p, cmd, args);
				}
			}
			else
			{
				SimpleChatCommandHandler.noPerms(p, cmd, args);
			}
			
			event.setCancelled(true);
		}
		else if ((cmd.equalsIgnoreCase("p")) && (Variables.UseSimpleChatPartyChat))
		{
			if ((Variables.perms.has(p, "simplechat.party.talk"))
					|| (Variables.perms.has(p, "simplechat.*") || (Variables.perms.has(p, "simplechat.party.*") || (Variables.perms
							.has(p, "simplechat.party.admin")))))
			{
				if (args.length == 0)
				{
					PartyChat.togglePartyChat(p);
				}
				else if (args.length >= 1)
				{
					String msg = "";
					for (String word : args)
					{
						msg = msg + " " + word;
					}
					msg = msg.trim();
					PartyChat.partyChat(p, msg);
				}
				else
				{
					SimpleChatCommandHandler.invalidCommand(p, cmd, args);
				}
			}
			else
			{
				SimpleChatCommandHandler.noPerms(p, cmd, args);
			}
			
			event.setCancelled(true);
		}
		else if ((cmd.equalsIgnoreCase("party")) && (Variables.UseSimpleChatPartyChat))
		{
			
			new PartyCommands(p, cmd, args);
			
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onServerCommandEvent(ServerCommandEvent event)
	{
		String commandLine = event.getCommand();
		String[] tempArgs = commandLine.split(" ");
		String cmd = tempArgs[0];
		String[] args = new String[tempArgs.length - 1];
		
		if (args.length > 0)
		{
			for (int index = 1; index < tempArgs.length; index++)
			{
				args[(index - 1)] = tempArgs[index];
			}
		}
		
		if (((cmd.equalsIgnoreCase("message")) || (cmd.equalsIgnoreCase("msg")) || (cmd.equalsIgnoreCase("m"))
				|| (cmd.equalsIgnoreCase("tell")) || (cmd.equalsIgnoreCase("whisper")) || (cmd.equalsIgnoreCase("pm")))
				&& (Variables.UseSimpleChatMsgAndReplyFormatting))
		{
			if (args.length > 1)
			{
				SimpleChatPrivateMessagingConsole
						.msgFromConsole(args[0], SimpleChatCommandHandler.combineArgs(1, args));
			}
			else
			{
				SimpleChatCommandHandler.invalidCommand(cmd, args);
			}
			event.setCommand("cmdrdrct");
		}
		else if (((cmd.equalsIgnoreCase("reply")) || (cmd.equalsIgnoreCase("r")))
				&& (Variables.UseSimpleChatMsgAndReplyFormatting))
		{
			Variables.plugin.getServer().getConsoleSender()
					.sendMessage("[SimpleChat] The /reply command is not programmed to work with console.");
			event.setCommand("");
		}
		else if ((cmd.equalsIgnoreCase("me")) && (Variables.UseSimpleChatGeneralFormatting))
		{
			if (args.length >= 1)
			{
				SimpleChatOtherCommandsConsole.me(args);
			}
			else
			{
				SimpleChatCommandHandler.invalidCommand(cmd, args);
			}
			event.setCommand("cmdrdrct");
		}
		else if ((cmd.equalsIgnoreCase("say")) && (Variables.UseSimpleChatGeneralFormatting))
		{
			if (args.length >= 1)
			{
				SimpleChatOtherCommandsConsole.say(args);
			}
			else
			{
				SimpleChatCommandHandler.invalidCommand(cmd, args);
			}
			
			event.setCommand("cmdrdrct");
		}
		else if (((cmd.equalsIgnoreCase("broadcast")) || (cmd.equalsIgnoreCase("bcast")))
				&& (Variables.UseSimpleChatGeneralFormatting))
		{
			if (args.length >= 1)
			{
				SimpleChatOtherCommandsConsole.broadcast(args);
			}
			else
			{
				SimpleChatCommandHandler.invalidCommand(cmd, args);
			}
			
			event.setCommand("cmdrdrct");
		}
		else if (((cmd.equalsIgnoreCase("a")) || (cmd.equalsIgnoreCase("admin")) || (cmd.equalsIgnoreCase("achat")) || (cmd
				.equalsIgnoreCase("adminchat"))) && (Variables.UseSimpleChatAdminChat))
		{
			if (args.length >= 1)
			{
				String msg = "";
				for (String word : args)
				{
					msg = msg + " " + word;
				}
				msg = msg.trim();
				SimpleChatAdminChatConsole.adminChat(msg);
			}
			else
			{
				SimpleChatCommandHandler.invalidCommand(cmd, args);
			}
			
			event.setCommand("cmdrdrct");
		}
	}
}
