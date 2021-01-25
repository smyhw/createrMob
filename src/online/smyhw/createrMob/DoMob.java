package online.smyhw.createrMob;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DoMob extends BukkitRunnable
{
	private final JavaPlugin plugin;
	
	Creature entity;//本实例操作的实体
	Location lasteLoc;//上一次运行时，实体的位置
	
	public DoMob(JavaPlugin plugin,Creature entity)
	{
		this.plugin = plugin;
		this.entity = entity;
		lasteLoc = entity.getLocation();
		this.runTaskTimer(this.plugin, 0,mc.configer.getInt("do_ai_ticks", 10));
	}

    @Override
    public void run() 
    {
    	if(!this.entity.isValid())
    	{//本实例操作的实体已经死亡，本实例生命周期结束
    		mc.mobList.remove(this);
    		return;
    	}
    	if(!(//如果实体的位置在变化，则不搭建或破坏方块
    			this.lasteLoc.getBlockX() == this.entity.getLocation().getBlockX() &&
    			this.lasteLoc.getBlockY() == this.entity.getLocation().getBlockY() &&
    			this.lasteLoc.getBlockZ() == this.entity.getLocation().getBlockZ()
    	))
    	{
    		this.lasteLoc = this.entity.getLocation();
    		return;	
    	}
    	//如果实体没有目标，则不执行
    	if(this.entity.getTarget() == null) {return;}
    	
    	//到这里决定执行操作
    	Random rand = new Random();
    	int randNum = rand.nextInt(100);
    	LivingEntity target = this.entity.getTarget();
    	Location targetLoc = target.getLocation();
    	
    	if(targetLoc.getBlockY() != this.entity.getLocation().getBlockY() && randNum<33)
    	{
    		if(targetLoc.getBlockY() > this.entity.getLocation().getBlockY())
    		{
	    		//如果头上不是空气方块，将其置为空气方块
	    		Location tmp1 = this.entity.getLocation();
	    		tmp1.setY(tmp1.getY()+2);
	    		if(Helper.breakBlock_if_notAir(tmp1)){return;}
	    		//方块垫脚
	    		this.entity.getLocation().getBlock().setType(Material.DIRT);
	    		tmp1 = this.entity.getLocation();
	    		tmp1.setY(this.entity.getLocation().getY()+1);
	    		this.entity.teleport(tmp1);
	    		return;
    		}
    		else
    		{//向下挖
    			Location tmp1 = this.entity.getLocation();
    			tmp1.setY(tmp1.getY()-1);
    			Helper.breakBlock(tmp1);
    		}
    	}
    	if(targetLoc.getBlockX() != this.entity.getLocation().getBlockX() && randNum<66)
    	{
    		//确定需要操作的坐标(没有确定Y轴)
    		Location tmp1 = this.entity.getLocation();
    		if(targetLoc.getBlockX() > this.entity.getLocation().getBlockX())
    		{tmp1.setX(tmp1.getX()+1);}
    		else{tmp1.setX(tmp1.getX()-1);}
    		//破坏挡路的方块
    		tmp1.setY(tmp1.getY()+1);
    		if(Helper.breakBlock_if_notAir(tmp1)){return;}
    		tmp1.setY(tmp1.getY()-1);
    		if(Helper.breakBlock_if_notAir(tmp1)){return;}
    		//如果脚下是空气，搭路
			tmp1.setY(tmp1.getY()-2);
			Helper.makeBlock(tmp1);
			return;
    	}
    	if(targetLoc.getBlockZ() != this.entity.getLocation().getBlockZ())
    	{
    		Location tmp1 = this.entity.getLocation();
    		if(targetLoc.getBlockZ() > this.entity.getLocation().getBlockZ())
    		{tmp1.setZ(tmp1.getZ()+1);}
    		else{tmp1.setZ(tmp1.getZ()-1);}
    		
    		tmp1.setY(tmp1.getY()+1);
    		if(Helper.breakBlock_if_notAir(tmp1)){return;}
    		tmp1.setY(tmp1.getY()-1);
    		if(Helper.breakBlock_if_notAir(tmp1)){return;}
    		
			tmp1.setY(tmp1.getY()-2);
    		Helper.makeBlock(tmp1);
			return;
    	}
    	
    }
}
