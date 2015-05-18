package me.chris.SimpleChat.CommandHandler;

import me.chris.SimpleChat.Variables;
import org.bukkit.entity.Player;

public class SimpleChatSocialSpy
{
	public static void toggleSocialSpyPM(Player p)
	{
		if (Variables.socialSpyOffPM.contains(p.getName()))
		{
			Variables.socialSpyOffPM.remove(p.getName());
			p.sendMessage("§a[SimpleChat] §7SocialSpy for PMs has been turned §2§lON§7.");
		}
		else
		{
			Variables.socialSpyOffPM.add(p.getName());
			p.sendMessage("§a[SimpleChat] §7SocialSpy for PMs has been turned §4§lOFF§7.");
		}
		Variables.plugin.saveYamls();
	}
	
	public static void toggleSocialSpyParty(Player p)
	{
		if (Variables.socialSpyOffParty.contains(p.getName()))
		{
			Variables.socialSpyOffParty.remove(p.getName());
			p.sendMessage("§a[SimpleChat] §7SocialSpy for parties has been turned §2§lON§7.");
		}
		else
		{
			Variables.socialSpyOffParty.add(p.getName());
			p.sendMessage("§a[SimpleChat] §7SocialSpy for parties has been turned §4§lOFF§7.");
		}
		Variables.plugin.saveYamls();
	}
}