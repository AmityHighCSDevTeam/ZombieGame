package org.amityregion5.ZombieGame.common.plugin;

import org.amityregion5.ZombieGame.ZombieGame;
import org.amityregion5.ZombieGame.client.asset.SoundRegistry;
import org.amityregion5.ZombieGame.client.asset.TextureRegistry;
import org.amityregion5.ZombieGame.client.settings.InputData;
import org.amityregion5.ZombieGame.common.entity.EntityLantern;
import org.amityregion5.ZombieGame.common.game.model.entity.LanternModel;
import org.amityregion5.ZombieGame.common.weapon.types.Grenade;
import org.amityregion5.ZombieGame.common.weapon.types.Placeable;
import org.amityregion5.ZombieGame.common.weapon.types.Rocket;
import org.amityregion5.ZombieGame.common.weapon.types.BasicGun;
import org.amityregion5.ZombieGame.common.weapon.types.Shotgun;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

import box2dLight.PointLight;

public class CorePlugin implements IPlugin {
	
	private PluginContainer container;

	@Override
	public void init(PluginContainer container) {
		this.container = container;
	}

	@Override
	public void preLoad() {
		container.addWeaponClass(BasicGun.class);
		container.addWeaponClass(Shotgun.class);
		container.addWeaponClass(Placeable.class);
		container.addWeaponClass(Grenade.class);
		container.addWeaponClass(Rocket.class);
	}

	@Override
	public void load() {
		Placeable.registeredObjects.put("Lantern_0", (g, vector)->{
			LanternModel lantern = new LanternModel(new EntityLantern(), g, LanternModel.getLIGHT_COLOR(), "Core/Entity/Lantern/0.png", "Lantern_0");
			lantern.setLight(new PointLight(g.getLighting(), 300,
					lantern.getColor(), 10, vector.x, vector.y));
			lantern.getEntity().setFriction(0.99f);
			lantern.getEntity().setMass(10);
			return lantern;
		});
		Placeable.registeredObjects.put("Lantern_1", (g, vector)->{
			LanternModel lantern = new LanternModel(new EntityLantern(), g, new Color(1,0,0,1), "Core/Entity/Lantern/1.png", "Lantern_1");
			lantern.setLight(new PointLight(g.getLighting(), 300,
					lantern.getColor(), 10, vector.x, vector.y));
			lantern.getEntity().setFriction(0.99f);
			lantern.getEntity().setMass(10);
			return lantern;
		});
	}

	@Override
	public void postLoad() {
		TextureRegistry.tryRegisterAs("Core/explosion.png", "explosion");
		TextureRegistry.tryRegisterAs("Core/backgroundTile2.png", "backgroundTile");
		TextureRegistry.tryRegisterAs("Core/HealthBox.png", "healthPack");
		TextureRegistry.tryRegisterAs("Core/UpgradeArrow.png", "upgradeArrow");
		
		SoundRegistry.tryRegister("Core/Audio/Zombie/ZombieGrowl1.wav");
		SoundRegistry.tryRegister("Core/Audio/Zombie/ZombieGrowl2.wav");
		SoundRegistry.tryRegister("Core/Audio/Zombie/ZombieGrowl3.wav");
		SoundRegistry.tryRegister("Core/Audio/Zombie/ZombieGrowl4.wav");
		SoundRegistry.tryRegister("Core/Audio/Zombie/ZombieGrowl5.wav");
		SoundRegistry.tryRegister("Core/Audio/Zombie/ZombieGrowl6.wav");
		
		SoundRegistry.tryRegister("Core/Audio/explode.wav");
		
		ZombieGame.instance.settings.registerInput("Shoot", new InputData(false, Buttons.LEFT));
		ZombieGame.instance.settings.registerInput("Move_Up", new InputData(true, Keys.W));
		ZombieGame.instance.settings.registerInput("Move_Down", new InputData(true, Keys.S));
		ZombieGame.instance.settings.registerInput("Move_Right", new InputData(true, Keys.D));
		ZombieGame.instance.settings.registerInput("Move_Left", new InputData(true, Keys.A));
		ZombieGame.instance.settings.registerInput("Toggle_Flashlight", new InputData(true, Keys.F));
		ZombieGame.instance.settings.registerInput("Buy_Ammo", new InputData(true, Keys.B));
		ZombieGame.instance.settings.registerInput("Reload", new InputData(true, Keys.R));
		ZombieGame.instance.settings.registerInput("Hotbar_1", new InputData(true, Keys.NUM_1));
		ZombieGame.instance.settings.registerInput("Hotbar_2", new InputData(true, Keys.NUM_2));
		ZombieGame.instance.settings.registerInput("Hotbar_3", new InputData(true, Keys.NUM_3));

		ZombieGame.instance.settings.registerInput("Shop_Window", new InputData(true, Keys.P));
		ZombieGame.instance.settings.registerInput("Inventory_Window", new InputData(true, Keys.I));
		ZombieGame.instance.settings.registerInput("Close_Window", new InputData(true, Keys.ESCAPE));
	}

	@Override
	public void dispose() {
	}
}