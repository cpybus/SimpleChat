package me.chris.SimpleChat.CommandHandler;

import java.util.ArrayList;
import java.util.HashMap;

import me.chris.SimpleChat.SimpleChatAPI;
import me.chris.SimpleChat.Variables;

import org.bukkit.entity.Player;

public class SimpleChatConfigCommands
{

	SimpleChatConfigCommands()
	{

	}

	public static void help(Player p)
	{
		p.sendMessage("§5===================§c [ SCConfig Help ] §5===================");
		p.sendMessage("§c/scconfig §7msgformat §e- Edits the msgformat area of config.");
		p.sendMessage("§c/scconfig §7groups §e- Edits the group area of config.");
		p.sendMessage("§c/scconfig §7users §e- Edits the users area of config.");
		p.sendMessage("§c/scconfig §7defaults §e- Edits the defaults area of config.");
		p.sendMessage("§5=====================================================");
	}

	// +------------------------------------------------------+ //
	// | MsgFormat | //
	// | | //
	// | Handles all of the msgformat commands: | //
	// | | //
	// +------------------------------------------------------+ //

	public static void msgFormatHelp(Player p)
	{
		p.sendMessage("§5=================§c [ Msg Format Help ] §5=================");
		p.sendMessage("§c/scconfig §7msgformat variables §e- Lists the variables you can use.");
		p.sendMessage("§c/scconfig §7msgformat view §e- Views the current configuration of this section.");
		p.sendMessage("§c/scconfig §7msgformat setformat [format...] §e- Sets the format. Spaces are allowed.");
		p.sendMessage("§5=====================================================");
	}

	public static void msgFormatVariables(Player p)
	{
		p.sendMessage("§5===============§c [ Msg Format Variables ] §5================");
		p.sendMessage("§2 +pname §eIs the player's name");
		p.sendMessage("§2 +dname §eIs the player's display name");
		p.sendMessage("§2 +pre §eIs the player's prefix");
		p.sendMessage("§2 +suf §eIs the player's suffix");
		p.sendMessage("§2 +gro §eIs the player's group");
		p.sendMessage("§2 +msg §eIs the message sent");
		p.sendMessage("§2 Color Codes §e&a &b &c &d &e &f &l &m &n &o &r &1 &2 &3 &4 &5 &6 &7 &8 &9 &0");
		p.sendMessage("§5=====================================================");
	}

	public static void msgFormatView(Player p)
	{
		p.sendMessage("§5=================§c [ Msg Format View ] §5=================");
		p.sendMessage("§2 MessageFormat §e" + Variables.MessageFormat);
		p.sendMessage("§5=====================================================");
	}

	public static void msgFormatSetFormat(Player p, String format) // Unspecified
																	// Args
	{
		Variables.MessageFormat = format;
		p.sendMessage("§a[SimpleChat] §5Message Format sucessfully set to §c" + format);
		Variables.plugin.saveYamls();
	}

	// +------------------------------------------------------+ //
	// | Groups | //
	// | | //
	// | Handles all of the groups commands: | //
	// | | //
	// +------------------------------------------------------+ //

	public static void groupsHelp(Player p)
	{
		p.sendMessage("§5====================§c [ Groups Help ] §5===================");
		p.sendMessage("§c/scconfig §7groups sync §e- Syncs the groups from permissions plugin with SimpleChat.");
		p.sendMessage("§c/scconfig §7groups view §e- Views the current setup of groups section.");
		p.sendMessage("§c/scconfig §7groups addgroup [groupname] §e- Adds a group. ");
		p.sendMessage("§c/scconfig §7groups removegroup [groupname] §e- Removes a group. ");
		p.sendMessage("§c/scconfig §7groups setprefix [groupname] [prefix...] §e- Sets the prefix of specified group.");
		p.sendMessage("§c/scconfig §7groups setsuffix [groupname] [suffix...] §e- Sets the suffix of specified group.");
		p.sendMessage("§5=====================================================");
	}

	public static void groupsSync(Player p)
	{
		String[] groups = Variables.perms.getGroups();
		// ArrayList<String> emptyList = new ArrayList<String>();
		Variables.groupPrefixes = new HashMap<String, String>();
		Variables.groupSuffixes = new HashMap<String, String>();
		for (String g : groups)
		{
			Variables.groupPrefixes.put(g, "[" + g + "]");
			Variables.groupSuffixes.put(g, "");
		}
		p.sendMessage("§a[SimpleChat] §5Sucessfully synced perms groups with simplechat.");
		Variables.plugin.saveYamls();
	}

	public static void groupsView(Player p)
	{
		ArrayList<String> groups = SimpleChatAPI.getGroups();
		p.sendMessage("§5====================§c [ Groups View ] §5===================");
		for (String g : groups)
		{
			String prefix = Variables.groupPrefixes.get(g);
			String suffix = Variables.groupSuffixes.get(g);
			p.sendMessage("§c" + g + " - §2Pre: §e" + prefix + "  §2Suf: §e" + suffix);
		}
		p.sendMessage("§5=====================================================");
	}

	public static void groupsAddGroup(Player p, String group)
	{
		Variables.groupPrefixes.put(group.toLowerCase(), "[" + group + "]");
		Variables.groupSuffixes.put(group.toLowerCase(), "");
		p.sendMessage("§a[SimpleChat] §5Sucessfully added group.");
		Variables.plugin.saveYamls();
	}

	public static void groupsRemoveGroup(Player p, String group)
	{
		if (Variables.groupPrefixes.containsKey(group.toLowerCase()))
		{
			Variables.groupPrefixes.remove(group.toLowerCase());
			Variables.groupSuffixes.remove(group.toLowerCase());
			p.sendMessage("§a[SimpleChat] §5Sucessfully removed group.");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4Invalid group name.");
		}
		Variables.plugin.saveYamls();
	}

	public static void groupsSetPrefix(Player p, String group, String prefix) // Unspecified
																				// Args
	{
		System.out.println(group + "  " + prefix);
		if (Variables.groupPrefixes.containsKey(group.toLowerCase()))
		{
			Variables.groupPrefixes.put(group.toLowerCase(), prefix);
			p.sendMessage("§a[SimpleChat] §5Sucessfully changed prefix.");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4Invalid group name.");
		}
		Variables.plugin.saveYamls();
	}

	public static void groupsSetSuffix(Player p, String group, String suffix) // Unspecified
																				// Args
	{
		if (Variables.groupSuffixes.containsKey(group.toLowerCase()))
		{
			Variables.groupSuffixes.put(group.toLowerCase(), suffix);
			p.sendMessage("§a[SimpleChat] §5Sucessfully changed suffix.");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4Invalid group name.");
		}
		Variables.plugin.saveYamls();
	}

	// +------------------------------------------------------+ //
	// | Users | //
	// | | //
	// | Handles all of the users commands: | //
	// | | //
	// +------------------------------------------------------+ //

	public static void usersHelp(Player p)
	{
		p.sendMessage("§5====================§c [ Users Help ] §5====================");
		p.sendMessage("§c/scconfig §7users view §e- Views all the info about Users section..");
		p.sendMessage("§c/scconfig §7users adduser [playername] §e- Adds a user to file.");
		p.sendMessage("§c/scconfig §7users removeuser [playername] §e- Removes a user. ");
		p.sendMessage("§c/scconfig §7users setprefix [playername] [prefix...] §e- Gives a user a prefix ");
		p.sendMessage("§c/scconfig §7users setsuffix [playername] [suffix...] §e- Gives a user a suffix");
		p.sendMessage("§5=====================================================");

	}

	public static void usersView(Player p)
	{
		ArrayList<String> users = SimpleChatAPI.getUsers();
		if (users.size() >= 1)
		{
			p.sendMessage("§5====================§c [ Users View ] §5===================");
			for (String u : users)
			{
				String prefix = Variables.userPrefixes.get(u);
				String suffix = Variables.userSuffixes.get(u);
				p.sendMessage("§c" + u + " - §2Pre: §e" + prefix + "  §2Suf: §e" + suffix);
			}
			p.sendMessage("§5=====================================================");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4The user section of config.yml is empty.");
		}
	}

	public static void usersAddUser(Player p, String user)
	{
		Variables.userPrefixes.put(user.toLowerCase(), "");
		Variables.userSuffixes.put(user.toLowerCase(), "");
		p.sendMessage("§a[SimpleChat] §5Sucessfully added user.");
		Variables.plugin.saveYamls();
	}

	public static void usersRemoveUser(Player p, String user)
	{
		if (Variables.userPrefixes.containsKey(user.toLowerCase()))
		{
			Variables.userPrefixes.remove(user.toLowerCase());
			Variables.userSuffixes.remove(user.toLowerCase());
			p.sendMessage("§a[SimpleChat] §5Sucessfully removed user.");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4Invalid user name.");
		}
		Variables.plugin.saveYamls();
	}

	public static void usersSetPrefix(Player p, String user, String prefix) // Unspecified
																// Args
	{
		if (Variables.userPrefixes.containsKey(user.toLowerCase()))
		{
			Variables.userPrefixes.put(user.toLowerCase(), prefix);
			p.sendMessage("§a[SimpleChat] §5Sucessfully changed prefix.");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4Invalid user name.");
		}
		Variables.plugin.saveYamls();
	}

	public static void usersSetSuffix(Player p, String user, String suffix) // Unspecified
																// Args
	{
		if (Variables.userSuffixes.containsKey(user.toLowerCase()))
		{
			Variables.userSuffixes.put(user.toLowerCase(), suffix);
			p.sendMessage("§a[SimpleChat] §5Sucessfully changed suffix.");
		}
		else
		{
			p.sendMessage("§a[SimpleChat] §4Invalid user name.");
		}
		Variables.plugin.saveYamls();
	}

	// +------------------------------------------------------+ //
	// | Defaults | //
	// | | //
	// | Handles all of the defaults commands: | //
	// | | //
	// +------------------------------------------------------+ //

	public static void defaultsHelp(Player p)
	{
		p.sendMessage("§5===================§c [ Defaults Help ] §5===================");
		p.sendMessage("§c/scconfig §7defaults view §e- Views all the info about Defaults section.");
		p.sendMessage("§c/scconfig §7defaults setprefix [prefix] §e- Sets the default prefix.");
		p.sendMessage("§c/scconfig §7defaults setsuffix [suffix] §e- Sets the default suffix. ");
		p.sendMessage("§c/scconfig §7defaults setgroup [group] §e- Sets the default group ");
		p.sendMessage("§5=====================================================");

	}

	public static void defaultsView(Player p)
	{
		p.sendMessage("§5===================§c [ Defaults View ] §5===================");
		p.sendMessage("§2 Default Prefix §e" + Variables.defaultPrefix);
		p.sendMessage("§2 Default Suffix §e" + Variables.defaultSuffix);
		p.sendMessage("§2 Default Group §e" + Variables.defaultGroup);
		p.sendMessage("§5=====================================================");
	}

	public static void defaultsSetPrefix(Player p, String prefix) // Unspecified
																	// Args
	{
		Variables.defaultPrefix = prefix;
		p.sendMessage("§a[SimpleChat] §5Sucessfully changed prefix.");
		Variables.plugin.saveYamls();
	}

	public static void defaultsSetSuffix(Player p, String suffix) // Unspecified
																	// Args
	{
		Variables.defaultSuffix = suffix;
		p.sendMessage("§a[SimpleChat] §5Sucessfully changed suffix.");
		Variables.plugin.saveYamls();
	}

	public static void defaultsSetGroup(Player p, String group) // Unspecified
																// Args
	{
		Variables.defaultGroup = group;
		p.sendMessage("§a[SimpleChat] §5Sucessfully changed group.");
		Variables.plugin.saveYamls();
	}
}
