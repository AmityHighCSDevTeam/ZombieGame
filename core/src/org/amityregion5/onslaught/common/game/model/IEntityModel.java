package org.amityregion5.onslaught.common.game.model;

import org.amityregion5.onslaught.client.game.IDrawingLayer;
import org.amityregion5.onslaught.common.entity.IEntity;
import org.amityregion5.onslaught.common.game.Game;

import com.google.gson.JsonObject;

/**
 * The interface for entities
 * 
 * @author sergeys
 * @param <T> the type of entity
 */
public interface IEntityModel<T extends IEntity> {
	/**
	 * Get the entity that this models
	 * 
	 * @return the entity
	 */
	T getEntity();

	/**
	 * Tick this model
	 * 
	 * @param timeStep time since last tick
	 */
	void tick(float timeStep);

	/**
	 * Clear memory used by this model
	 */
	void dispose();

	/**
	 * Get drawing layers
	 * 
	 * @return the drawing layers
	 */
	IDrawingLayer[] getDrawingLayers();

	/**
	 * Damage this entity
	 * 
	 * @param damage the amount of damage
	 * @param source the source of the damage
	 * @param damageType the type of damage
	 * @return damage accepted
	 */
	float damage(float damage, IEntityModel<?> source, String damageType);

	/**
	 * Get the health of this entity
	 * @return the health
	 */
	float getHealth();

	/**
	 * Get the maximum health of this entity
	 * @return the maximum health
	 */
	float getMaxHealth();

	/**
	 * Is this entity a hostile
	 * 
	 * @return is it a hostile
	 */
	boolean isHostile();

	void read(JsonObject obj);
	void write(JsonObject obj);

	void doPostDeserialize(Game game);

	boolean shouldBeDeleted();
}
