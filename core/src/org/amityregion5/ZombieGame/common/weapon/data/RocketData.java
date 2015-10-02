package org.amityregion5.ZombieGame.common.weapon.data;

import org.json.simple.JSONObject;

public class RocketData extends GrenadeData{
	private double acceleration;

	public RocketData(JSONObject o) {
		super(o);
		if (o.containsKey("accel")) {
			acceleration = ((Number) o.get("accel")).doubleValue();
		}
	}

	/**
	 * @return the acceleration
	 */
	public double getAcceleration() {
		return acceleration;
	}

	/**
	 * @param acceleration the acceleration to set
	 */
	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}
}
