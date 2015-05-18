package me.chris.SimpleChat.PartyHandler;

import org.bukkit.entity.Player;

public class PartyCommands
{
	public PartyCommands(Player p, String cmd, String[] args)
	{
		int length = args.length;
		if (length == 0)
		{
			PartyInformationCommand.info(p);
		}
		else if (length == 1)
		{
			String arg1 = args[0];
			if (arg1.equalsIgnoreCase("leaveparty") || arg1.equalsIgnoreCase("leave"))
			{
				PartyJoinLeaveCommand.leave(p);
			}
			else if (arg1.equalsIgnoreCase("deleteparty") || arg1.equalsIgnoreCase("delete"))
			{
				PartyCreateDeleteCommand.deleteParty(p);
			}
			else if (arg1.equalsIgnoreCase("accept"))
			{
				PartyInviteAcceptCommand.accept(p);
			}
			else if (arg1.equalsIgnoreCase("list"))
			{
				PartyListCommand.list(p);
			}
			else if (arg1.equalsIgnoreCase("help"))
			{
				PartyHelpCommand.help(p);
			}
			else
			{
				invalidCommand(p, cmd, args);
			}

		}
		else if (length == 2)
		{
			String arg1 = args[0];
			String arg2 = args[1];
			if (arg1.equalsIgnoreCase("joinparty") || arg1.equalsIgnoreCase("join"))
			{
				PartyJoinLeaveCommand.join(p, arg2);
			}
			else if (arg1.equalsIgnoreCase("joinInvisible"))
			{
				PartyJoinLeaveCommand.joinInvisible(p, arg2);
			}
			else if (arg1.equalsIgnoreCase("createparty") || arg1.equalsIgnoreCase("create"))
			{
				PartyCreateDeleteCommand.createParty(p, arg2);
			}
			else if (arg1.equalsIgnoreCase("deleteparty") || arg1.equalsIgnoreCase("delete"))
			{
				PartyCreateDeleteCommand.deleteParty(p, arg2);
			}
			else if (arg1.equalsIgnoreCase("kickplayer") || arg1.equalsIgnoreCase("kick"))
			{
				PartyKickBanCommand.kick(p, arg2);
			}
			else if (arg1.equalsIgnoreCase("banplayer") || arg1.equalsIgnoreCase("ban"))
			{
				PartyKickBanCommand.ban(p, arg2);
			}
			else if (arg1.equalsIgnoreCase("unbanplayer") || arg1.equalsIgnoreCase("unban"))
			{
				PartyKickBanCommand.unban(p, arg2);
			}
			else if (arg1.equalsIgnoreCase("invite"))
			{
				PartyInviteAcceptCommand.invite(p, arg2);
			}
			else if (arg1.equalsIgnoreCase("info"))
			{
				PartyInformationCommand.specificInfo(p, arg2);
			}
			else if (arg1.equalsIgnoreCase("newowner"))
			{
				PartyNewOwnerCommand.newOwner(p, arg2);
			}
			else
			{
				invalidCommand(p, cmd, args);
			}
		}
		else if (length == 3)
		{
			String arg1 = args[0];
			String arg2 = args[1];
			String arg3 = args[2];
			if (arg1.equalsIgnoreCase("joinparty") || arg1.equalsIgnoreCase("join"))
			{
				PartyJoinLeaveCommand.joinPassword(p, arg2, arg3);
			}
			else if (arg1.equalsIgnoreCase("createparty") || arg1.equalsIgnoreCase("create"))
			{
				PartyCreateDeleteCommand.createParty(p, arg2, arg3);
			}
			else
			{
				invalidCommand(p, cmd, args);
			}
		}
		else
		{
			invalidCommand(p, cmd, args);

		}

	}

	public static void invalidCommand(Player p, String cmd, String[] args)
	{
		String command = cmd;
		for (String arg : args)
		{
			command = command + " " + arg;
		}
		command = command.trim();
		p.sendMessage("§a[SimpleChat] §4The following command is invalid:");
		p.sendMessage("§a[SimpleChat] §o§c/" + command);
	}
}
