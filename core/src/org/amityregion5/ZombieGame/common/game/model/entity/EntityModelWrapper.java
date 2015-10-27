package org.amityregion5.ZombieGame.common.game.model.entity;

import org.amityregion5.ZombieGame.client.game.IDrawingLayer;
import org.amityregion5.ZombieGame.common.entity.IEntity;
import org.amityregion5.ZombieGame.common.game.model.IEntityModel;

public class EntityModelWrapper<T extends IEntity> implements IEntityModel<T> {

	private T entity;
	
	public EntityModelWrapper(T entity) {
		this.entity = entity;
	}
	
	@Override
	public T getEntity() {
		return entity;
	}

	@Override
	public void tick(float timeStep) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public float damage(float damage, IEntityModel<?> source, String damageType) {
		return 0;
	}

	@Override
	public IDrawingLayer[] getDrawingLayers() {
		return new IDrawingLayer[]{};
	}

	@Override
	public float getHealth() {
		return 0;
	}

	@Override
	public float getMaxHealth() {
		return 0;
	}

	@Override
	public boolean isHostile() {
		return false;
	}
}