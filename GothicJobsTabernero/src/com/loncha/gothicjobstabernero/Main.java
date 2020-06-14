 package com.loncha.gothicjobstabernero;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.loncha.gothicjobstabernero.objetos.*;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {
	CrafteosCaldero cCaldero;
	CrafteosMortero cMortero;
	CrafteosAsador cAsador;
	
	List<String> ingredientesMortero = new ArrayList<String>();
	List<String> ingredientesCaldero = new ArrayList<String>();
	List<String> ingredientesAsador = new ArrayList<String>();
	
	ArrayList<RecetaMortero> recetasMortero = new ArrayList<RecetaMortero>();
	ArrayList<RecetaCaldero> recetasCaldero = new ArrayList<RecetaCaldero>();
	ArrayList<RecetaAsador> recetasAsador = new ArrayList<RecetaAsador>();
	
	ArrayList<ItemStack> itemsCustomTabernero = new ArrayList<ItemStack>();
	
	FileConfiguration configFile;
	
	@Override
	public void onEnable() {
		cCaldero = new CrafteosCaldero(this);
		cMortero = new CrafteosMortero(this);
		cAsador = new CrafteosAsador(this);
		
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(this.cCaldero, this);
		getServer().getPluginManager().registerEvents(this.cMortero, this);
		getServer().getPluginManager().registerEvents(this.cAsador, this);
		
		getCommand("reloadtabernero").setExecutor(new Reload(this));
		
		cargarRecetasMortero();
		cargarRecetasCaldero();
		cargarRecetasAsador();
		cargarItemsCustom();

	}
	
	public void cargarRecetasMortero() {
		File config = new File("plugins/GothicJobsTabernero/mortero.yml");
		
		configFile = new YamlConfiguration();
		
		try {
			recetasMortero = new ArrayList<RecetaMortero>();
			
			configFile.load(config);
			
			if (getCustomConfig().getConfigurationSection("recetas").getKeys(true) != null) {
				for (String s : getCustomConfig().getConfigurationSection("recetas").getKeys(false)) {
					RecetaMortero rm = new RecetaMortero();
					
					String[] nombreytipo = getCustomConfig().getString("recetas."+s+".ingrediente").split("/");
					String[] resultadoytipo = getCustomConfig().getString("recetas."+s+".resultado").split("/");
					
					rm.setNombreIngrediente(nombreytipo[0]);
					rm.setTipoIngrediente(Material.getMaterial(nombreytipo[1]));
					
					rm.setNombreResultado(resultadoytipo[0]);
					rm.setTipoResultado(Material.getMaterial(resultadoytipo[1]));
					
					rm.setUsosMortero(getCustomConfig().getInt("recetas."+s+".usos"));
					
					recetasMortero.add(rm);
				}
			}

			ingredientesMortero = getCustomConfig().getStringList("ingredientes");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cargarRecetasAsador() {
		File config = new File("plugins/GothicJobsTabernero/asador.yml");
		
		configFile = new YamlConfiguration();
		
		try {
			recetasAsador = new ArrayList<RecetaAsador>();
			
			configFile.load(config);
			
			if (getCustomConfig().getConfigurationSection("recetas").getKeys(true) != null) {
				for (String s : getCustomConfig().getConfigurationSection("recetas").getKeys(false)) {
					RecetaAsador ra = new RecetaAsador();
					
					String[] nombreytipo = getCustomConfig().getString("recetas."+s+".ingrediente").split("/");
					String[] resultadoytipo = getCustomConfig().getString("recetas."+s+".resultado").split("/");
					
					ra.setNombreIngrediente(nombreytipo[0]);
					ra.setTipoIngrediente(Material.getMaterial(nombreytipo[1]));
					
					ra.setNombreResultado(resultadoytipo[0]);
					ra.setTipoResultado(Material.getMaterial(resultadoytipo[1]));
					
					ra.setTiempo(getCustomConfig().getInt("recetas."+s+".tiempo"));
					ra.setNivel(getCustomConfig().getInt("recetas."+s+".nivel"));
					ra.setPiezas(getCustomConfig().getInt("recetas."+s+".piezas"));
					
					recetasAsador.add(ra);
				}
			}

			ingredientesAsador = getCustomConfig().getStringList("ingredientes");		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cargarRecetasCaldero() {
		File config = new File("plugins/GothicJobsTabernero/caldero.yml");
		
		configFile = new YamlConfiguration();
		
		try {
			recetasCaldero = new ArrayList<RecetaCaldero>();
			
			configFile.load(config);
			
			if (getCustomConfig().getConfigurationSection("recetas").getKeys(true) != null) {
				for (String s : getCustomConfig().getConfigurationSection("recetas").getKeys(false)) {
					RecetaCaldero rc = new RecetaCaldero();
					
					String[] resultado = getCustomConfig().getString("recetas."+s+".resultado").split("/");
					rc.setNombreResultado(resultado[0]);
					rc.setDataResultado(Integer.parseInt(resultado[1]));
					rc.setCantidadResultado(Integer.parseInt(resultado[2]));
					
					List<String> ingredientes = getCustomConfig().getStringList("recetas."+s+".ingredientes");
					
					for (String s2 : ingredientes) {
						String[] desgloseIngrediente = s2.split("/");
						
						rc.nombreIngredientes.add(desgloseIngrediente[0]);
						rc.dataIngredientes.add(Integer.parseInt(desgloseIngrediente[1]));
						rc.cantidadIngredientes.add(Integer.parseInt(desgloseIngrediente[2]));
					}
					
					rc.setHerramienta(getCustomConfig().getString("recetas."+s+".herramienta"));
					rc.setCalor(getCustomConfig().getBoolean("recetas."+s+".calor"));
					rc.setTiempo(getCustomConfig().getInt("recetas."+s+".tiempo"));
					rc.setNivel(getCustomConfig().getInt("recetas."+s+".nivel"));
					rc.setProfesion(getCustomConfig().getString("recetas."+s+".profesion"));
					
					recetasCaldero.add(rc);
				}
			}
			
			ingredientesCaldero = getCustomConfig().getStringList("ingredientes");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cargarItemsCustom(){
		File config = new File("plugins/GothicJobsTabernero/itemscustom.yml");
		
		configFile = new YamlConfiguration();
		
		try {
			//Resetear arrays
			itemsCustomTabernero = new ArrayList<ItemStack>();
			
			configFile.load(config);
		
			if (getCustomConfig().getConfigurationSection("items").getKeys(true) != null) {
				for (String s : getCustomConfig().getConfigurationSection("items").getKeys(false)) {
					String nombre, material;
					int cantidad, data;
					List<String> lore = new ArrayList<String>();
					
					nombre = getCustomConfig().getString("items."+s+".nombre");
					material = getCustomConfig().getString("items."+s+".material");
					data = getCustomConfig().getInt("items."+s+".data");
					cantidad = getCustomConfig().getInt("items."+s+".cantidad");
					lore = getCustomConfig().getStringList("items."+s+".lore");
					
					ItemStack is = new ItemStack(Material.getMaterial(material), cantidad, (short)data);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName(nombre);
					im.setLore(lore);
					is.setItemMeta(im);
					
					itemsCustomTabernero.add(is);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getCustomConfig() {
		return this.configFile;
	}
}
