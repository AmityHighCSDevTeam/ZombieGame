package org.amityregion5.ZombieGame.common.shop;

import java.util.Map;
import java.util.Optional;

import org.amityregion5.ZombieGame.common.game.model.entity.PlayerModel;
import org.amityregion5.ZombieGame.common.weapon.WeaponStack;
import org.amityregion5.ZombieGame.common.weapon.types.IWeapon;
import org.amityregion5.ZombieGame.common.weapon.types.NullWeapon;

public class GunPurchaseable implements IPurchaseable {
	
	private IWeapon gun;
	
	public GunPurchaseable(IWeapon gun) {
		this.gun = gun;
	}
	
	@Override
	public String getName() {
		return gun.getName();
	}

	@Override
	public String getDescription() {
		return gun.getDescription();
	}

	@Override
	public Map<String, String> getCurrentDescriptors(PlayerModel player) {
		int level = 0;

		Optional<WeaponStack> ws = player.getWeapons().parallelStream().filter((wS)->wS.getWeapon()==gun).findAny();

		if (ws.isPresent()) {
			level = ws.get().getLevel();
		}

		Map<String, String> currLev = gun.getWeaponDataDescriptors(level);
		return currLev;
	}

	@Override
	public Map<String, String> getNextDescriptors(PlayerModel player) {
		int level = 0;

		Optional<WeaponStack> ws = player.getWeapons().parallelStream().filter((wS)->wS.getWeapon()==gun).findAny();

		if (ws.isPresent()) {
			level = ws.get().getLevel();
		}
		
		level += 1;
		
		if (!hasNextLevel(player)) {
			return null;
		}

		Map<String, String> nextLev = gun.getWeaponDataDescriptors(level);
		return nextLev;
	}

	@Override
	public boolean hasNextLevel(PlayerModel player) {
		Optional<WeaponStack> ws = player.getWeapons().parallelStream().filter((wS)->wS.getWeapon()==gun).findAny();

		if (ws.isPresent() && ws.get().getLevel() + 1 < ws.get().getWeapon().getNumLevels()) {
			return true;
		}
		return !ws.isPresent();
	}

	@Override
	public int getCurrentLevel(PlayerModel player) {
		int level = 0;
		
		Optional<WeaponStack> ws = player.getWeapons().parallelStream().filter((wS)->wS.getWeapon()==gun).findAny();

		if (ws.isPresent()) {
			level = ws.get().getLevel();
		} else {
			return -1;
		}
		
		return level;
	}

	@Override
	public int getNumLevels() {
		return gun.getNumLevels();
	}

	@Override
	public double getPrice(PlayerModel player) {
		return gun.getWeaponData(Math.min(getCurrentLevel(player)+1,getNumLevels())).getPrice();
	}

	@Override
	public boolean canPurchase(PlayerModel player) {
		if (!hasNextLevel(player)) {
			return false;
		}
		return true;
	}

	@Override
	public void onPurchase(PlayerModel player) {
		Optional<WeaponStack> ws = player.getWeapons().parallelStream().filter((wS)->wS.getWeapon()==gun).findAny();
		
		if (ws.isPresent()) {
			ws.get().setLevel(ws.get().getLevel()+1);
		} else {
			WeaponStack newWeap = new WeaponStack(gun);
			player.getWeapons().add(newWeap);
			if (player.getCurrentWeapon().getWeapon() instanceof NullWeapon) {
				player.getHotbar()[player.getCurrWeapIndex()] = newWeap;
			}
		}
	}

	@Override
	public boolean hasIcon() {
		return true;
	}

	@Override
	public String getIconName(PlayerModel player) {
		return gun.getWeaponData(Math.min(getCurrentLevel(player)+1,getNumLevels())).getIconTextureString();
	}
}
