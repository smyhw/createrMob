package online.smyhw.createrMob;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;

public class Helper 
{
	public static void breakBlock(Location location)
	{
		location.getWorld().playSound(location, Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD , 1, 1);
		location.getWorld().playEffect(location, Effect.SMOKE, 50);
		location.getBlock().setType(Material.AIR);
	}
	
	/**
	 * 破坏了方块，返回true
	 * @param location
	 * @return 没有破坏，返回false
	 */
	public static boolean breakBlock_if_notAir(Location location)
	{
		if(location.getBlock().getType()!=Material.AIR  && location.getBlock().getType()!=Material.STATIONARY_WATER && location.getBlock().getType()!=Material.WATER)
		{
			breakBlock(location);
			return true;
		}
		else
		{return false;}
	}

	public static void makeBlock(Location location)
	{
    	Random tmp1 = new Random();
    	int rand = tmp1.nextInt(mc.configer.getStringList("make_blocks").size());
		location.getBlock().setType(
				Material.matchMaterial(
						mc.configer.getStringList("make_blocks").get(rand)
				)
		);
	}

}
