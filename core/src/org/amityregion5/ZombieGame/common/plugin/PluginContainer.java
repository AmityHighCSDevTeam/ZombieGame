package org.amityregion5.ZombieGame.common.plugin;

import java.util.ArrayList;

import org.amityregion5.ZombieGame.common.weapon.types.IWeapon;

public class PluginContainer {
	private ArrayList<IWeapon>					weapons			= new ArrayList<IWeapon>();
	private ArrayList<Class<? extends IWeapon>>	weaponClasses	= new ArrayList<Class<? extends IWeapon>>();
	
	private String name;
	private String desc;
	private String jarLoc;
	private String pluginFolderLoc;
	
	public PluginContainer() {
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the jarLoc
	 */
	public String getJarLoc() {
		return jarLoc;
	}

	/**
	 * @param jarLoc the jarLoc to set
	 */
	public void setJarLoc(String jarLoc) {
		this.jarLoc = jarLoc;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	public void addWeapon(IWeapon weapon) {
		weapons.add(weapon);
	}

	public void addWeaponClass(Class<? extends IWeapon> weaponClass) {
		weaponClasses.add(weaponClass);
	}
	
	public boolean isActive() {
		return true;
	}
	
	public ArrayList<IWeapon> getWeapons() {
		return weapons;
	}
	
	public ArrayList<Class<? extends IWeapon>> getWeaponClasses() {
		return weaponClasses;
	}
	
	public String getPluginFolderLoc() {
		return pluginFolderLoc;
	}
	
	public void setPluginFolderLoc(String pluginFolderLoc) {
		this.pluginFolderLoc = pluginFolderLoc;
	}
}
