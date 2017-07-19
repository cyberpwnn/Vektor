package org.cyberpwn.vektor;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Vektor extends JavaPlugin implements Listener, Runnable
{
	private Map<Player, Vector> velocity;
	
	@Override
	public void onEnable()
	{
		velocity = new HashMap<Player, Vector>();
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, this, 0, 0);
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	public void run()
	{
		for(Player i : velocity.keySet())
		{
			
		}
	}
	
	public Vector fastRound(Vector v)
	{
		return new Vector(fastRound(v.getX()), fastRound(v.getY()), fastRound(v.getZ()));
	}
	
	public double fastRound(double d)
	{
		return (d * 1000D) / 1000D;
	}
	
	public void join(Player p)
	{
		clearVelocity(p);
	}
	
	public void quit(Player p)
	{
		velocity.remove(p);
	}
	
	public void clearVelocity(Player p)
	{
		setVelocity(p, new Vector(0, 0, 0));
	}
	
	public void setVelocity(Player p, Vector v)
	{
		velocity.put(p, fastRound(v));
	}
	
	public Vector getVelocity(Player p)
	{
		if(!velocity.containsKey(p))
		{
			clearVelocity(p);
		}
		
		return velocity.get(p);
	}
	
	public double getSpeed(Vector v)
	{
		return v.distance(new Vector(0, 0, 0));
	}
	
	public void impulse(Player p, Vector v)
	{
		setVelocity(p, getVelocity(p).clone().add(v));
	}
	
	public void applyVector(Player p)
	{
		p.setVelocity(fastRound(getVelocity(p)));
	}
	
	public void move(Player p, Location a, Location b)
	{
		if(a.getWorld().equals(b.getWorld()))
		{
			setVelocity(p, b.clone().subtract(a).toVector());
		}
	}
	
	@EventHandler
	public void on(PlayerJoinEvent e)
	{
		join(e.getPlayer());
	}
	
	@EventHandler
	public void on(PlayerQuitEvent e)
	{
		quit(e.getPlayer());
	}
	
	@EventHandler
	public void on(PlayerMoveEvent e)
	{
		move(e.getPlayer(), e.getFrom(), e.getTo());
	}
	
	@EventHandler
	public void on(final EntityDamageEvent e)
	{
		if(e instanceof Player)
		{
			final Vector v = getVelocity(((Player) e).getPlayer());
			
			getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
			{
				public void run()
				{
					((Player) e).getPlayer().setVelocity(v);
				}
			}, 1);
			
			getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
			{
				public void run()
				{
					((Player) e).getPlayer().setVelocity(v);
				}
			}, 2);
			
			getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
			{
				public void run()
				{
					((Player) e).getPlayer().setVelocity(v);
				}
			}, 3);
			
			getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
			{
				public void run()
				{
					((Player) e).getPlayer().setVelocity(v);
				}
			}, 4);
			
			((Player) e).getPlayer().setVelocity(v);
		}
	}
	
}
