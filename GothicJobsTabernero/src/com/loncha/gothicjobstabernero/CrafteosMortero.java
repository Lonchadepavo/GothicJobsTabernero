package com.loncha.gothicjobstabernero;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitScheduler;

import com.loncha.gothicjobstabernero.objetos.RecetaMortero;

import net.md_5.bungee.api.ChatColor;

public class CrafteosMortero implements Listener {
	Main m;
	
	public CrafteosMortero(Main m) {
		this.m = m;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		ItemStack itemInHand = p.getInventory().getItemInMainHand();
		String nombreItemInHand = "";
		
		if (itemInHand.hasItemMeta()) {
			nombreItemInHand = itemInHand.getItemMeta().getDisplayName();
		} else {
			nombreItemInHand = itemInHand.getType().toString();
		}
		
		if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			
			if (!b.hasMetadata("tiempofundicion")) {
				if (b.getType() == Material.FLOWER_POT) {
					if (itemInHand != null && itemInHand.getType() != Material.AIR) {
						//SI NO TIENES EL MAZO EN LA MANO
						if (!nombreItemInHand.equals("§fMazo de madera")) {

							for (String s : m.ingredientesMortero) {
	
								//INTRODUCIR INGREDIENTE
								if (nombreItemInHand.equals(s)) {
									if (!b.hasMetadata("enuso")) {
										b.setMetadata("left", new FixedMetadataValue(m,"true"));
										b.setMetadata("enuso", new FixedMetadataValue(m,"true"));
										b.setMetadata("usos", new FixedMetadataValue(m,0));
										b.setMetadata(nombreItemInHand, new FixedMetadataValue(m,"true"));
										
										restarObjeto(itemInHand,p);
										p.sendMessage(ChatColor.GREEN+"Introduces un ingrediente en el tarro.");
										reproducirSonido(p,Sound.BLOCK_GRASS_STEP,5);
									
									//SI YA HAY UN INGREDIENTE EN EL TARRO
									} else {
										p.sendMessage(ChatColor.DARK_RED+"Ya hay un ingrediente en el tarro.");
										
									}
								}
							}
							
						//SI TIENES EL MAZO EN LA MANO
						}  else if (nombreItemInHand.equals("§fMazo de madera")) {
							for (RecetaMortero rm : m.recetasMortero) {
								String nombreReceta = rm.getNombreIngrediente();
								
								//Encuentra la receta
								if (b.hasMetadata(nombreReceta)) {
									//Si todavía no has molido lo suficiente
									if (b.hasMetadata("usos")) {
										if (b.getMetadata("usos").get(0).asInt() < rm.getUsosMortero()) {
											b.setMetadata("usos", new FixedMetadataValue(m,b.getMetadata("usos").get(0).asInt()+1)); //Aumenta el número de usos
											p.sendMessage(ChatColor.YELLOW+"Machacas la mezcla con el mazo.");
											reproducirSonido(p, Sound.ENTITY_SHULKER_BULLET_HIT, 5);
											
											break;
											
										//LA RECETA FUNCIONA
										} else {
											b.removeMetadata("usos", m);
											b.removeMetadata(nombreReceta, m);
											
											b.setMetadata(rm.getNombreResultado(), new FixedMetadataValue(m,"true"));
											p.sendMessage(ChatColor.GREEN+"Parece que la receta está lista.");
											break;
											
										}
									} else {
										p.sendMessage(ChatColor.GREEN+"Ya puedes sacar el resultado del tarro.");
										break;
										
									}
								}
							}
						}
						
					//RECOGER EL RESULTADO O EL INGREDIENTE						
					} else {
						for (ItemStack item : m.itemsCustomTabernero) {
							String nombreItem = "";
							
							if (item.hasItemMeta()) {
								nombreItem = item.getItemMeta().getDisplayName();
							} else {
								nombreItem = item.getType().toString();
							}

							if (b.hasMetadata(nombreItem)) {
								b.getLocation().getWorld().dropItem(b.getLocation(), item);
								b.removeMetadata("enuso", m);
					            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
					            scheduler.scheduleSyncDelayedTask(m, new Runnable() {
					                @Override
					                public void run() {
					                	b.removeMetadata("left",m);
					                }
					            }, 20);
								
								
								b.removeMetadata(nombreItem, m);
								reproducirSonido(p,Sound.ITEM_BOTTLE_FILL,5);
								
							}
						}
					}
				}
			}
		}
	}
	
	public void restarObjeto(ItemStack item, Player p) {
		if (item.getAmount()-1 == 0) {
			p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
		} else {
			item.setAmount(item.getAmount()-1);
			p.getInventory().setItemInMainHand(item);
		}
	}
	
	public void reproducirSonido(Player p, Sound sonido, int rango) {
        for (Player players : Bukkit.getOnlinePlayers()) {
        	if (p.getWorld() == players.getWorld()) {
				if (p.getLocation().distanceSquared(players.getLocation()) <= 10) {
					
					players.getWorld().playSound(p.getLocation(), sonido, 1.0F, 0.01F);
				}
        	}
        }
	}
	
	public void enviarMensajeSimple(Player p, ChatColor color, String mensaje, int rango) {
        for (Player players : Bukkit.getOnlinePlayers()) {
        	if (p.getWorld() == players.getWorld()) {
				if (p.getLocation().distanceSquared(players.getLocation()) <= 10) {
					players.sendMessage(color+mensaje);
				}
        	}
        }
	}
	
}
