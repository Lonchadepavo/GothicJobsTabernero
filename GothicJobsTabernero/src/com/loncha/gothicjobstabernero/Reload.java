package com.loncha.gothicjobstabernero;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Reload implements CommandExecutor {
	Main m;
	public Reload(Main m) {
		this.m = m;
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		if (p.isOp()) {
			if (arg1.getName().equalsIgnoreCase("reloadtabernero")) {
				m.cargarRecetasMortero();
				m.cargarRecetasCaldero();
				m.cargarRecetasAsador();
				m.cargarItemsCustom();
				p.sendMessage(ChatColor.GREEN+"GothicJobsTabernero recargado");
				return true;
			}
		}
		
		return false;
	}

}
