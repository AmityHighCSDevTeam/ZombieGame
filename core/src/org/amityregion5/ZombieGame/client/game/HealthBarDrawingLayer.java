package org.amityregion5.ZombieGame.client.game;

import org.amityregion5.ZombieGame.common.entity.IEntity;
import org.amityregion5.ZombieGame.common.game.model.IEntityModel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class HealthBarDrawingLayer implements IDrawingLayer {
	
	public static final HealthBarDrawingLayer instance = new HealthBarDrawingLayer();

	@Override
	public void draw(IEntityModel<?> eM, SpriteBatch batch, ShapeRenderer shapeRenderer) {
		
		IEntity e = eM.getEntity();
		
		shapeRenderer.begin(ShapeType.Filled);
		
		float height = e.getShape().getRadius()/6;
		
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(
				(float)(e.getBody().getWorldCenter().x - e.getShape().getRadius()*0.9), 
				(float)(e.getBody().getWorldCenter().y - height/2),
				(float)(e.getShape().getRadius() * 1.8), 
				(float)(height));
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.rect(
				(float)(e.getBody().getWorldCenter().x - e.getShape().getRadius()*0.9), 
				(float)(e.getBody().getWorldCenter().y - height/2),
				(float)(e.getShape().getRadius() * 1.8 * eM.getHealth()/eM.getMaxHealth()), 
				(float)(height));
		shapeRenderer.end();
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(
				(float)(e.getBody().getWorldCenter().x - e.getShape().getRadius()*0.9), 
				(float)(e.getBody().getWorldCenter().y - height/2),
				(float)(e.getShape().getRadius() * 1.8), 
				(float)(height));
		shapeRenderer.end();
	}
}