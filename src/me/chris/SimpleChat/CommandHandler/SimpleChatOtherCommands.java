package me.chris.SimpleChat.CommandHandler;

import me.chris.SimpleChat.SimpleChatAPI;
import me.chris.SimpleChat.SimpleChatChatState;
import me.chris.SimpleChat.SimpleChatHelperMethods;
import me.chris.SimpleChat.Variables;
import mkremins.fanciful.FancyMessage;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SimpleChatOtherCommands
{

	SimpleChatOtherCommands()
	{
	}

	public static void welcome(Player p)
	{
		String lastestVersion = "";
		try
		{
			lastestVersion = SimpleChatHelperMethods.updateCheck();
		}
		catch (Throwable t)
		{

		}

		p.sendMessage("§5=====================================================");
		p.sendMessage("§a Welcome to §cSimpleChat §aPlugin §9(" + Variables.version + ")");
		p.sendMessage("§a Designed and Programmed by §9Hotshot2162");
		p.sendMessage("§5=====================================================");
		if (!lastestVersion.equalsIgnoreCase(Variables.version))
		{
			p.sendMessage("§4 Warning!§f This isnt the lastest version of SimpleChat!");
			p.sendMessage("§c " + lastestVersion + "§f Is out! (This is §c" + Variables.version + "§f)");
			p.sendMessage("§5=====================================================");
		}
	}

	public static void help(Player p)
	{
		p.sendMessage("§5==================§c [ SimpleChat Help ] §5==================");
		
		new FancyMessage("§c/simplechat §e- States the general info.")
			.tooltip("This command does nothing but state the \nauthor and the current version")
			.suggest("/simplechat")
			.send(p);
		new FancyMessage("§c/simplechat help §e- Brings up the help menu.")
			.tooltip("This will bring up the help menu")
			.suggest("/simplechat help")
			.send(p);
		new FancyMessage("§c/simplechat generateYamls §e- Generates sample yamls. ")
			.tooltip("Will generate the sample YAML files in the SimpleChat plugin \nfolder. These YAMLs are examples only and serve no \nfunctional purpose.")
			.suggest("/simplechat generateYamls")
			.send(p);
		new FancyMessage("§c/simplechat reload §e- Reloads all the files.")
			.tooltip("Will load any changes you have \nmade in the YAML files. ")
			.suggest("/simplechat reload")
			.send(p);
		new FancyMessage("§c/chat §e- Toggles current chat state.")
			.tooltip("Will toggle server-wide chat. No player without \nsimplechat.chat permission will be able to talk.")
			.suggest("/chat")
			.send(p);
		new FancyMessage("§c/chaton §e- Turns server-wide chat on. ")
			.tooltip("Will turn on server-wide chat. No player without \nsimplechat.chat permission will be able to talk.")
			.suggest("/chaton")
			.send(p);
		new FancyMessage("§c/chatoff §e- Turns server-wide chat off.")
			.tooltip("Will turn off server-wide chat. No player without \nsimplechat.chat permission will be able to talk.")
			.suggest("/chatoff")
			.send(p);
		new FancyMessage("§c/scconfig §e- Edits config.yml values")
			.tooltip("This command is the base command for all \ncommands that edit the Config.yml file.")
			.suggest("/scconfig")
			.send(p);
		new FancyMessage("§c/scextra §e- Edits extra.yml values")
			.tooltip("This command is the base command for all \ncommands that edit the Extra.yml file")
			.suggest("/scextra")
			.send(p);
		new FancyMessage("§c/ssparty §e- Toggles SocialSpy for parties")
			.tooltip("This will toggle your socialspy \nfor Party Chat")
			.suggest("/ssparty")
			.send(p);
		new FancyMessage("§c/sspm §e- Toggles SocialSpy for PMs")
			.tooltip("This will toggle your socialspy \nfor Private Messages (PMs)")
			.suggest("/sspm")
			.send(p);
		new FancyMessage("NOTE: A \"...\" signifies that the respective value can have multiple words/phrases.")
			.color(ChatColor.DARK_AQUA)
			.style(ChatColor.ITALIC)
			.send(p);
		new FancyMessage("§3§oNOTE: \"msg\" and \"message\" are interchangeable throughout the plugin. ")
			//.color(ChatColor.DARK_AQUA)
			//.style(ChatColor.ITALIC)
			.send(p);
	}
	
	public static void genYamls(Player p)
	{
		Variables.plugin.copy(Variables.plugin.getResource("config-example.yml"), Variables.configExampleFile);
		Variables.plugin.copy(Variables.plugin.getResource("extra-example.yml"), Variables.extraExampleFile);
	}

	public static void reload(Player p)
	{
		Variables.plugin.loadYamls();
		SimpleChatHelperMethods.fillOnlinePlayerLists();
		p.sendMessage("§aPlugin Reload Complete. ");
	}

	public static void me(Player p, String[] args)
	{
		String msg = "";
		for (String word : args)
		{
			msg += " " + word;
		}
		String[] vars = { "+pname", "+dname", "+pre", "+suf", "+gro", "+msg", "&" };
		String[] replace = { p.getName(), p.getDisplayName(), SimpleChatAPI.getPrefix(p), SimpleChatAPI.getSuffix(p), SimpleChatAPI.getGroup(p), msg.trim(), "§" };
		String meMsg = SimpleChatHelperMethods.replaceVars(Variables.MeFormat, vars, replace);
		Variables.plugin.getServer().broadcastMessage(meMsg);

	}
	
	public static void say(Player p, String[] args)
	{
		String msg = "";
		for (String word : args)
		{
			msg += " " + word;
		}
		String[] vars = {"+msg", "&" };
		String[] replace = { msg.trim(), "§" };
		String sayMsg = SimpleChatHelperMethods.replaceVars(Variables.SayFormat, vars, replace);
		Variables.plugin.getServer().broadcastMessage(sayMsg);
	}

	public static void broadcast(Player p, String[] args)
	{
		String msg = "";
		for (String word : args)
		{
			msg += " " + word;
		}
		String[] vars = {"+msg", "&" };
		String[] replace = { msg.trim(), "§" };
		String BcastMsg = SimpleChatHelperMethods.replaceVars(Variables.BroadcastFormat, vars, replace);
		Variables.plugin.getServer().broadcastMessage(BcastMsg);
	}

	public static void chat(Player p)
	{
		String currentState = SimpleChatChatState.getChatState();
		if (currentState.equalsIgnoreCase("off"))
		{
			Variables.plugin.getServer().broadcastMessage("§a[SimpleChat]§7 Chat has been turned §2§lON§r§7 by §c" + p.getName());
			SimpleChatChatState.setChatState("on");
		}
		else
		{
			Variables.plugin.getServer().broadcastMessage("§a[SimpleChat]§7 Chat has been turned §4§lOFF§r§7 by §c" + p.getName());
			SimpleChatChatState.setChatState("off");
		}
	}

	public static void chatOn(Player p)
	{
		Variables.plugin.getServer().broadcastMessage("§a[SimpleChat]§7 Chat has been turned §2§lON§r§7 by §c" + p.getName());
		SimpleChatChatState.setChatState("on");
	}

	public static void chatOff(Player p)
	{
		Variables.plugin.getServer().broadcastMessage("§a[SimpleChat]§7 Chat has been turned §4§lOFF§r§7 by §c" + p.getName());
		SimpleChatChatState.setChatState("off");
	}

	

}
