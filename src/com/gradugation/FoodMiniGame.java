package com.gradugation;

import static org.andengine.extension.physics.box2d.util.constants.PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.EmptyTexture;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.align.HorizontalAlign;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.widget.Toast;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class FoodMiniGame extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	private static final int POINTS_NEEDED = 5;
	private static final int CREDITS_EARNED = 3;
	private int score = 0;

	protected Engine engine;
	private Camera camera;

	private HUD mHUD;
	private Scene scene = new Scene();
	private PhysicsWorld mPhysicsWorld;

	private ITexture frenchFriesTexture, applesTexture, continueTexture;
	private ITextureRegion frenchFriesTextureRegion, applesTextureRegion, continueTextureRegion;
	private Font mFont;

	@Override
	public EngineOptions onCreateEngineOptions() {
		Toast.makeText(
				this,
				"Get a score of 5 in 15 seconds! Gain points with apples, lose points with fries!",
				Toast.LENGTH_LONG).show();

		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback) {
		try {
			this.frenchFriesTexture = new AssetBitmapTexture(
					this.getTextureManager(), this.getAssets(),
					"gfx/food_game/fries.png");
			this.applesTexture = new AssetBitmapTexture(
					this.getTextureManager(), this.getAssets(),
					"gfx/food_game/apples.png");
			this.continueTexture = new AssetBitmapTexture(
					this.getTextureManager(), this.getAssets(),
					"gfx/whack_aflyer_img/continue_button.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.frenchFriesTextureRegion = TextureRegionFactory
				.extractFromTexture(this.frenchFriesTexture);
		this.frenchFriesTexture.load();

		this.applesTextureRegion = TextureRegionFactory
				.extractFromTexture(this.applesTexture);
		this.applesTexture.load();
		
		this.continueTextureRegion = TextureRegionFactory
				.extractFromTexture(this.continueTexture);
		this.continueTexture.load();


		final ITexture strokeFontTexture = new EmptyTexture(
				this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.mFont = new StrokeFont(this.getFontManager(), strokeFontTexture,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, true,
				Color.WHITE, 1, Color.BLACK);
		this.mFont.load();

		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	private void createSprite(float pX, float pY, final Text text) {
		List<ITextureRegion> foodList = new ArrayList<ITextureRegion>();
		foodList.add(applesTextureRegion);
		foodList.add(frenchFriesTextureRegion);

		Random randomGenerator = new Random();
		final int index = randomGenerator.nextInt(foodList.size());
		final ITextureRegion randomTextureRegion = foodList.get(index);

		final Sprite sprite = new Sprite(pX, pY, randomTextureRegion,
				this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					if (index == 0) {
						score++;
						text.setText("Score: " + score);
					} else {
						score--;
						text.setText("Score: " + score);
					}
					this.detachSelf();
					scene.unregisterTouchArea(this);
					break;
				case TouchEvent.ACTION_UP:
					break;
				case TouchEvent.ACTION_MOVE:
					break;
				}
				return true;
			}

		};

		// This method generates the timer that detaches the sprite if they have
		// not been touched

		TimerHandler noTouchTimerHandler = new TimerHandler(3, true,
				new ITimerCallback() {
					public void onTimePassed(TimerHandler pTimerHandler) {
						scene.detachChild(sprite);
						scene.unregisterTouchArea(sprite);
					}
				});

		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0,
				SensorManager.GRAVITY_EARTH), false);
		final FixtureDef spriteDef = PhysicsFactory.createFixtureDef(1f, 0.5f,
				0.5f);
		final Body body = FoodMiniGame.createBody(this.mPhysicsWorld, sprite,
				BodyType.KinematicBody, spriteDef);
		body.setLinearVelocity(0, -6);

		scene.attachChild(sprite);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite,
				body, true, false));
		scene.registerUpdateHandler(noTouchTimerHandler);
		scene.registerUpdateHandler(mPhysicsWorld);
		scene.registerTouchArea(sprite);
		scene.setTouchAreaBindingOnActionDownEnabled(true);

	}

	// This method generates new sprites. 
	private void createSpriteSpawnTimeHandler(final Text text) {
		TimerHandler spriteTimerHandler;
		final float cameraHeight = CAMERA_HEIGHT / 2 + (200);

		createSprite(CAMERA_WIDTH / 2, cameraHeight, text);
		createSprite(CAMERA_WIDTH / 5, cameraHeight, text);
		createSprite(CAMERA_WIDTH - (CAMERA_WIDTH / 5), cameraHeight, text);

		// randomize the 4 seconds to choose between 4~6 sec
		spriteTimerHandler = new TimerHandler(3, true, new ITimerCallback() {
			public void onTimePassed(TimerHandler pTimerHandler) {
				createSprite(CAMERA_WIDTH / 2, cameraHeight, text);
				createSprite(CAMERA_WIDTH / 5, cameraHeight, text);
				createSprite(CAMERA_WIDTH - (CAMERA_WIDTH / 5), cameraHeight,
						text);
			}
		});
		scene.registerUpdateHandler(spriteTimerHandler);
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateScreenCallback) {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		scene.getBackground().setColor(1f, 1f, 1f);

		mHUD = new HUD();
		mHUD.attachChild(new Entity());
		camera.setHUD(mHUD);

		final Text scoreText = new Text(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2,
				this.mFont, "Score: " + score,
				this.getVertexBufferObjectManager());
		scoreText.setPosition(CAMERA_WIDTH - (CAMERA_WIDTH / 10), CAMERA_HEIGHT
				- (CAMERA_HEIGHT / 10));

		final Text timerText = new Text(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2,
				this.mFont, "Timer: " + 15, this.getVertexBufferObjectManager());
		timerText.setPosition(CAMERA_WIDTH / 4, CAMERA_HEIGHT
				- (CAMERA_HEIGHT / 10));

		final Text gameOver = new Text(0, 0, this.mFont,
				"                                 ", new TextOptions(
						HorizontalAlign.CENTER),
				this.getVertexBufferObjectManager());
		gameOver.setPosition(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2);

		createSpriteSpawnTimeHandler(scoreText);

		mHUD.attachChild(gameOver);
		mHUD.attachChild(scoreText);
		mHUD.attachChild(timerText);

		TimerHandler gameTimerHandler = new TimerHandler(1, true,
				new ITimerCallback() {
					int countdown = 15;

					public void onTimePassed(TimerHandler pTimerHandler) {
						countdown--;
						timerText.setText("Timer: " + countdown);
						if (countdown == 0) {
							Intent output = new Intent();
							if (score >= POINTS_NEEDED) {
								gameOver.setText("Game Over! You won!");
								output.putExtra(Event.FOOD_REQUEST_CODE+"", CREDITS_EARNED);
							} else {
								gameOver.setText("Game Over! You lost!");
								output.putExtra(Event.FOOD_REQUEST_CODE+"", 0);
							}
							setResult(RESULT_OK, output);
							scene.setIgnoreUpdate(true);
							scene.clearTouchAreas();
							createContinueButton();
						}
					}
				});

		scene.registerUpdateHandler(gameTimerHandler);
		pOnCreateScreenCallback.onCreateSceneFinished(scene);
	}
	
	private void createContinueButton() {
		final Sprite continueButton = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2-(CAMERA_HEIGHT/5), continueTextureRegion,
				this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					finish();
					break;
				case TouchEvent.ACTION_UP:
					break;
				case TouchEvent.ACTION_MOVE:
					break;
				}
				return true;
			}

		};
		mHUD.attachChild(continueButton);
		mHUD.registerTouchArea(continueButton);
	}

	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) {
		// populate the scene here
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	private static Body createBody(final PhysicsWorld pPhysicsWorld,
			final IEntity pEntity, final BodyType pBodyType,
			final FixtureDef pFixtureDef) {
		/*
		 * Remember that the vertices are relative to the center-coordinates of
		 * the Shape.
		 */
		final float halfWidth = pEntity.getWidth() * 0.5f
				/ PIXEL_TO_METER_RATIO_DEFAULT;
		final float halfHeight = pEntity.getHeight() * 0.5f
				/ PIXEL_TO_METER_RATIO_DEFAULT;

		final float top = -halfHeight;
		final float bottom = halfHeight;
		final float left = halfWidth;
		final float centerX = 0;
		final float right = -halfWidth;

		final Vector2[] vertices = { new Vector2(centerX, top),
				new Vector2(left, bottom), new Vector2(right, bottom),
				new Vector2(centerX, bottom), };

		return PhysicsFactory.createPolygonBody(pPhysicsWorld, pEntity,
				vertices, pBodyType, pFixtureDef);
	}

}