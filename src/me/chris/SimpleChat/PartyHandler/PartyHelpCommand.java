package me.chris.SimpleChat.PartyHandler;

import org.bukkit.entity.Player;

public class PartyHelpCommand
{
	public static void help(Player p)
	{
		p.sendMessage("§5==================§c [ PartyChat Help ] §5==================");
		p.sendMessage("§c/party §e- States the general info of the party youre in.");
		p.sendMessage("§c/party joinparty [partyname] <password> help §e- Joins a party. ");
		p.sendMessage("§c/party leaveparty §e- Leaves your current party. ");
		p.sendMessage("§c/party createparty [partyname] <password> §e- Creates a new party. ");
		p.sendMessage("§c/party deleteparty <partyname> §e- Deletes current party (if not specified) or the specified party. ");
		p.sendMessage("§c/party kickplayer §e- Kicks a player from the party. ");
		p.sendMessage("§c/party banplayer §e- Bans a player from the party. ");
		p.sendMessage("§c/party invite [playername] §e- Invites a player to the party. ");
		p.sendMessage("§c/party accept §e- Accepts an invite.");
		p.sendMessage("§c/party list §e- Lists the names of all parties.");
		p.sendMessage("§c/party info [party] §e- Views specific info for parties. (Regulars view less specific info).");
		p.sendMessage("§c/party newowner [player] §e- Appoints a new owner of the party.");
		p.sendMessage("§c/party joininvisible [party] §e- Joins a party invisibly. You will not appear on member list.");
		p.sendMessage("§3§oNOTE: Being a party owner grants you the following commands for your own party:");
		p.sendMessage("§3§o/party kick, /party ban, /party newowner, /party deleteparty");
		p.sendMessage("§5=====================================================");
	}
}
