package org.amityregion5.ZombieGame.client.window;

import java.text.NumberFormat;

import org.amityregion5.ZombieGame.ZombieGame;
import org.amityregion5.ZombieGame.client.asset.TextureRegistry;
import org.amityregion5.ZombieGame.client.screen.InGameScreen;
import org.amityregion5.ZombieGame.common.game.model.entity.PlayerModel;
import org.amityregion5.ZombieGame.common.weapon.WeaponStack;
import org.amityregion5.ZombieGame.common.weapon.types.NullWeapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

/**
 * The screen overlay
 * @author sergeys
 *
 */
public class HUDOverlay implements Screen {
	private ShapeRenderer	shapeRender	= new ShapeRenderer(); //The shape renderer
	private InGameScreen	screen; //The screen
	private PlayerModel		player; //The player
	private GlyphLayout		glyph		= new GlyphLayout(); //The glyph layout 
	private SpriteBatch		batch		= new SpriteBatch(); //The sprite batch
	private int				eachBoxSize	= 64; //The size of each hotbar box
	private Sprite[] sprites;
	private String[] names;
	private Vector2 oldSize = new Vector2(0,0);

	public HUDOverlay(InGameScreen screen, PlayerModel player) {
		this.screen = screen; //Set values
		this.player = player;
		sprites = new Sprite[player.getHotbar().length];
		names = new String[player.getHotbar().length];
	}

	@Override
	public void drawScreen(float delta, Camera camera) {
		//Prepare the screen
		drawPrepare(delta);

		//Draw the hotbar
		drawHotbar(delta);

		//Draw the area to the right
		drawRightHUD(delta);

		//Disable blending
		Gdx.gl.glDisable(GL20.GL_BLEND);
		//Line size = 1
		Gdx.gl.glLineWidth(1);
	}

	public void drawPrepare(float delta) {
		//Update projection matricies
		shapeRender.setProjectionMatrix(screen.getScreenProjectionMatrix());
		batch.setProjectionMatrix(screen.getScreenProjectionMatrix());

		//Enable blending
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendColor(1, 1, 1, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void drawHotbar(float delta) {
		//Calculate start X
		float startX = (screen.getWidth() - player.getHotbar().length * eachBoxSize*ZombieGame.getXScalar()) / 2;
		for (int i = 0; i < player.getHotbar().length; i++) {

			//Draw the inner filling
			shapeRender.begin(ShapeType.Filled);
			shapeRender.setColor(Color.GRAY);
			shapeRender.rect(startX + eachBoxSize * i*ZombieGame.getAScalar(), 0, eachBoxSize*ZombieGame.getAScalar(), eachBoxSize*ZombieGame.getAScalar());
			shapeRender.end();

			Gdx.gl.glLineWidth(2);

			//If it is not a null weapon draw an icon
			if (!(player.getHotbar()[i].getWeapon() instanceof NullWeapon)) {
				WeaponStack weapon = player.getHotbar()[i];
				if (sprites[i] == null || !names[i].equals(TextureRegistry.getTextureNamesFor(weapon.getGameTextureName()).get(0)) || !oldSize.equals(new Vector2(screen.getWidth(), screen.getHeight()))) {
					names[i] = TextureRegistry.getTextureNamesFor(weapon.getGameTextureName()).get(0);
					sprites[i] = TextureRegistry.getAtlas().createSprite(names[i]);
					sprites[i].setBounds(startX + eachBoxSize * i*ZombieGame.getAScalar(), 0, eachBoxSize*ZombieGame.getAScalar(), eachBoxSize*ZombieGame.getAScalar());
				}

				batch.begin();

				sprites[i].draw(batch);

				batch.end();
			}

			//Draw the border
			shapeRender.begin(ShapeType.Line);
			shapeRender.setColor(Color.DARK_GRAY);
			shapeRender.rect(startX + eachBoxSize * i*ZombieGame.getAScalar(), 0, eachBoxSize*ZombieGame.getAScalar(), eachBoxSize*ZombieGame.getAScalar());
			shapeRender.end();
		}
		if (!oldSize.equals(new Vector2(screen.getWidth(), screen.getHeight()))) {
			oldSize = new Vector2(screen.getWidth(), screen.getHeight());
		}

		//Draw a box around the current selected one
		shapeRender.begin(ShapeType.Line);
		shapeRender.setColor(Color.LIGHT_GRAY);
		shapeRender.rect(startX + eachBoxSize * player.getCurrWeapIndex()*ZombieGame.getAScalar(), 0, eachBoxSize*ZombieGame.getAScalar(), eachBoxSize*ZombieGame.getAScalar());
		shapeRender.end();
	}

	private void drawRightHUD(float delta) {
		//Update projection matrix
		shapeRender.setProjectionMatrix(batch.getProjectionMatrix());

		shapeRender.begin(ShapeType.Filled);

		//Draw the box
		shapeRender.setColor(75 / 255f, 75 / 255f, 75 / 255f, 75 / 255f);
		shapeRender.rect(screen.getWidth() - 400*ZombieGame.getXScalar(), 0, 400*ZombieGame.getXScalar(), 200*ZombieGame.getYScalar());
		shapeRender.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);

		//Draw the weapon name
		batch.begin();
		screen.getSmallOutlineFont().draw(batch, player.getCurrentWeapon().getWeapon().getName(), screen.getWidth() - 390*ZombieGame.getXScalar(), 190*ZombieGame.getYScalar());

		//Draw the weapon ammo
		if (player.getCurrentWeapon().getTotalAmmo() == 0 ^ player.getCurrentWeapon().getAmmo() == 0) {
			screen.getSmallOutlineFont().setColor(Color.YELLOW);
		}
		if (player.getCurrentWeapon().getTotalAmmo() == 0 && player.getCurrentWeapon().getAmmo() == 0) {
			screen.getSmallOutlineFont().setColor(Color.RED);
		}
		screen.getSmallOutlineFont().draw(batch, player.getCurrentWeapon().getAmmoString(), screen.getWidth() - 390*ZombieGame.getXScalar(), 170*ZombieGame.getYScalar());
		screen.getSmallOutlineFont().setColor(new Color(1, 1, 1, 1));

		//Draw the money
		screen.getSmallOutlineFont().draw(batch, "$" + NumberFormat.getInstance().format(player.getMoney()), screen.getWidth() - 390*ZombieGame.getXScalar(), 150*ZombieGame.getYScalar());
		batch.end();

		shapeRender.begin(ShapeType.Filled);

		//Draw health bar red
		shapeRender.setColor(1f, 0f, 0f, 1f);
		shapeRender.rect(screen.getWidth() - 395*ZombieGame.getXScalar(), 110*ZombieGame.getYScalar(), 90*ZombieGame.getXScalar(), 20*ZombieGame.getYScalar());

		//Draw health bar green
		shapeRender.setColor(0f, 1f, 0f, 1f);
		shapeRender.rect(screen.getWidth() - 395*ZombieGame.getXScalar(), 110*ZombieGame.getYScalar(), 90 * player.getHealth() / player.getMaxHealth()*ZombieGame.getXScalar(), 20*ZombieGame.getYScalar());

		shapeRender.end();

		//Draw health
		batch.begin();
		glyph.setText(screen.getSmallOutlineFont(), ((int) (player.getHealth() / player.getMaxHealth() * 100 * 10 + 0.5)) / 10f + "%", new Color(1, 1, 1, 1), 90*ZombieGame.getXScalar(),
				Align.center, false);
		screen.getSmallOutlineFont().draw(batch, glyph, screen.getWidth() - 395*ZombieGame.getXScalar(), 113*ZombieGame.getYScalar() + glyph.height);

		if (!(player.getCurrentWeapon().getWeapon() instanceof NullWeapon)) {
			//Draw the gun status
			screen.getSmallOutlineFont().draw(batch, "Weapon Status: " + player.getCurrentWeapon().getStatus(), screen.getWidth() - 390*ZombieGame.getXScalar(), 90*ZombieGame.getYScalar());
		}

		//Draw the score
		screen.getSmallOutlineFont().draw(batch, "Score: " + (int)(player.getScore()*100)/100d, screen.getWidth() - 390*ZombieGame.getXScalar(), 70*ZombieGame.getYScalar());
		batch.end();
	}

	@Override
	public void dispose() {
		//Dispose things
		shapeRender.dispose();
		batch.dispose();
	}
}
