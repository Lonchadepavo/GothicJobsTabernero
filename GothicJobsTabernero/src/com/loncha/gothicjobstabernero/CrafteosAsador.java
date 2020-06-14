package com.loncha.gothicjobstabernero;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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

import com.loncha.gothicjobstabernero.objetos.RecetaAsador;

import net.md_5.bungee.api.ChatColor;

public class CrafteosAsador implements Listener {
	Main m;
	ArrayList<String> tipoConstrucciones = new ArrayList<String>(Arrays.asList("asador"));
	
	public CrafteosAsador(Main m) {
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
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			
			if (checkMesa(b) == "asador") {
				for (String s : m.ingredientesAsador) {
					if (nombreItemInHand.equals(s)) {
						if (itemInHand.getType() == Material.DIAMOND_HOE) {
							//Cambiar el bloque por el cadaver
							b.setType(Material.WOOL);
							b.setMetadata(nombreItemInHand, new FixedMetadataValue(m, "true"));
							b.setMetadata("estado", new FixedMetadataValue(m, "crudo"));
							b.setData((byte)6);
							
							p.sendMessage(ChatColor.YELLOW+"La carne empieza a cocinarse.");
							reproducirSonido(p,Sound.ITEM_ARMOR_EQUIP_LEATHER, 5);
							
							//Te quita el cadaver del inventario
							restarObjeto(itemInHand, p);
							
							//Lanza un scheduler para cocinar el cadaver
							for (RecetaAsador ra : m.recetasAsador) {
								if (ra.getNombreIngrediente().equals(nombreItemInHand)) {
									
						            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
						            scheduler.scheduleSyncDelayedTask(m, new Runnable() {
						                @Override
						                public void run() {
						            		Location l5 = new Location(b.getWorld(), b.getLocation().getX(), b.getLocation().getY()-1, b.getLocation().getZ());
						            		
						            		if (l5.getBlock().getType() == Material.FIRE) {
						            			if (p.hasPermission("gjobs.tabernero"+ra.getNivel())) {
						            				b.setMetadata("left", new FixedMetadataValue(m,"true"));
						    						b.setMetadata("estado", new FixedMetadataValue(m, "cocinado"));
													b.setMetadata("piezas", new FixedMetadataValue(m, ra.getPiezas()));
													b.setMetadata(ra.getNombreResultado(), new FixedMetadataValue(m, "true"));
													
													b.removeMetadata(ra.getNombreIngrediente(), m);
													
													b.setData((byte)12);
													
													p.sendMessage(ChatColor.GREEN+"Parece que la carne está lista.");
													
						            			} else {
						            				//LA RECETA SALE MAL
						            				p.sendMessage(ChatColor.DARK_RED+"No sabes lo que haces y quemas la carne.");
						            				reproducirSonido(p,Sound.BLOCK_FIRE_EXTINGUISH, 5);
						            				b.removeMetadata("estado", m);
						            				b.removeMetadata("pizas", m);
						            				b.removeMetadata("left", m);
						            				
						            				for (String s2 : m.ingredientesAsador) {
						            					b.removeMetadata(s2, m);
						            				}
						            				
						            				b.setType(Material.IRON_FENCE);
						            				
						            			}
						            		} else {
						            			//LA RECETA SALE MAL
						            			p.sendMessage(ChatColor.DARK_RED+"La carne no se cocina lo suficiente y se echa a perder.");
					            				reproducirSonido(p,Sound.BLOCK_FIRE_EXTINGUISH, 5);
					            				b.removeMetadata("estado", m);
					            				b.removeMetadata("pizas", m);
					            				b.removeMetadata("left", m);
					            				
					            				for (String s2 : m.ingredientesAsador) {
					            					b.removeMetadata(s2, m);
					            				}
					            				
					            				b.setType(Material.IRON_FENCE);
						            			
						            		}
						                }
						            }, ra.getTiempo()*20);
								}
							}	
						}
					}
				}
			}
		} else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			
			if (b.hasMetadata("piezas")) {
				if (b.getMetadata("piezas").get(0).asInt() > 0) {
					if (nombreItemInHand.equals("§fCuchillo de cazador")) {
						for (ItemStack item : m.itemsCustomTabernero) {
							String nombreItem = "";
							
							if (item.hasItemMeta()) {
								nombreItem = item.getItemMeta().getDisplayName();
							} else {
								nombreItem = item.getType().toString();
							}
							
							if (b.hasMetadata(nombreItem)) {
								b.setMetadata("piezas", new FixedMetadataValue(m, b.getMetadata("piezas").get(0).asInt()-1));
								b.getLocation().getWorld().dropItem(b.getLocation(), item);
								
								p.sendMessage(ChatColor.YELLOW+"Cortas un filete con el cuchillo.");
								reproducirSonido(p,Sound.ITEM_ARMOR_EQUIP_LEATHER, 5);
							}
						}
					}
				} else {
        			p.sendMessage(ChatColor.DARK_RED+"Ya no queda más carne.");
    				b.removeMetadata("estado", m);
    				b.removeMetadata("pizas", m);
    				b.removeMetadata("left", m);
    				
    				for (String s2 : m.ingredientesAsador) {
    					b.removeMetadata(s2, m);
    				}
    				
    				for (ItemStack item : m.itemsCustomTabernero) {
						String nombreItem = "";
						
						if (item.hasItemMeta()) {
							nombreItem = item.getItemMeta().getDisplayName();
						} else {
							nombreItem = item.getType().toString();
						}
						
						b.removeMetadata(nombreItem, m);
    				}
    				
    				b.setType(Material.IRON_FENCE);
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
	
	public String checkMesa(Block b) {	
		//Tiene que comprobar los bloques a los lados y el bloque que tiene debajo
		Location l1 = new Location(b.getWorld(), b.getLocation().getX()+1, b.getLocation().getY(), b.getLocation().getZ());
		Location l2 = new Location(b.getWorld(), b.getLocation().getX()-1, b.getLocation().getY(), b.getLocation().getZ());
		Location l3 = new Location(b.getWorld(), b.getLocation().getX(), b.getLocation().getY(), b.getLocation().getZ()+1);
		Location l4 = new Location(b.getWorld(), b.getLocation().getX(), b.getLocation().getY(), b.getLocation().getZ()-1);
		
		Location l5 = new Location(b.getWorld(), b.getLocation().getX(), b.getLocation().getY()-1, b.getLocation().getZ());
		
		if (b.getType() == Material.IRON_FENCE) {
			if (l1.getBlock().getType() == Material.IRON_FENCE && l2.getBlock().getType() == Material.IRON_FENCE) {
				if (l5.getBlock().getType() == Material.FIRE) {
					return tipoConstrucciones.get(0);
				}
			} else if (l3.getBlock().getType() == Material.IRON_FENCE && l4.getBlock().getType() == Material.IRON_FENCE) {
				if (l5.getBlock().getType() == Material.FIRE) {
					return tipoConstrucciones.get(0);
				}
			}
		}
		
		return "nada";
	}

}
