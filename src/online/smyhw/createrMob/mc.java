package online.smyhw.createrMob;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.*;


public class mc extends JavaPlugin implements Listener 
{
	public static JavaPlugin thisPlugin;
	public static Logger loger;
	public static FileConfiguration configer;
	public static List<DoMob> mobList = new ArrayList<DoMob>();
	@Override
    public void onEnable() 
	{
		getLogger().info("createrMob加载中...");
		getLogger().info("正在加载环境...");
		loger=getLogger();
		configer = getConfig();
		thisPlugin=this;
		getLogger().info("正在加载配置...");
		saveDefaultConfig();
		getLogger().info("正在注册监听器...");
		Bukkit.getPluginManager().registerEvents(this,this);
		getLogger().info("createrMob加载完成");
	}

	@Override
	public void onDisable() 
	{
		getLogger().info("createrMob卸载");
    }
	
	@EventHandler
	public void CreatureSpawnEvent(org.bukkit.event.entity.CreatureSpawnEvent e)
	{
		//如果不在指定世界中
		if(!configer.getStringList("enable_worlds").contains(e.getEntity().getWorld().getName())){return;}
		//如果不在怪物列表中
		if(!configer.getStringList("enable_mob").contains(e.getEntityType().name())) {return;}
		DoMob tmp1 = new DoMob(thisPlugin,(Creature)e.getEntity());
		mobList.add(tmp1);
	}
}