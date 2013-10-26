package com.gradugation;

import java.io.IOException;
import java.util.Random;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.EmptyTexture;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.Constants;
import org.andengine.util.debug.Debug;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class MainGameScreen extends SimpleBaseGameActivity implements
		IOnSceneTouchListener {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;

	// ===========================================================
	// Fields
	// ===========================================================

	private BoundCamera mCamera;

	private ITexture mPlayerTexture;
	private TiledTextureRegion mPlayerTextureRegion;

	private TMXTiledMap mTMXTiledMap;
	protected int mCactusCount;

	private BitmapTextureAtlas characterTextureAtlas, diceTextureAtlas;
	public ITextureRegion character;

	private ITexture mFaceTexture;
	private ITextureRegion mFaceTextureRegion;
	private ITexture mPausedTexture, mResumeTexture, mMainMenuTexture, mSaveGameTexture;
	private ITextureRegion mPausedTextureRegion, mResumeTextureRegion,
			mMainMenuTextureRegion, mSaveGameTextureRegion;
	
	private TextureRegion diceTextureRegion;

	private CameraScene mPauseScene;
	private Scene scene;

	private HUD mHUD;

	private Font mFont;
	private StrokeFont mStrokeFont, mStrokeFontLarge;

	private float currentX;
	private float currentY;
	private float currentX2;
	private float currentY2;

	private boolean turnDone;
	private boolean moving;
	public int turnNum;
	public int currentCharacter;
	public int ranNumb;
	private int numCharacters;

	private boolean gameDone = false;

	float initX;
	float initY;
	float finalX;
	float finalY;

	boolean swipeDone = false;
	private BitmapTextureAtlas characterTextureAtlas2;
	private TextureRegion character2;

	private Random random;
	private int diceRoll = 0;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public EngineOptions onCreateEngineOptions() {
		// Toast.makeText(this,
		// "The tile the player is walking on will be highlighted.",
		// Toast.LENGTH_LONG).show();

		final float maxVelocityX = 150;
		final float maxVelocityY = 150;
		final float maxZoomFactorChange = 5;
		this.mCamera = new SmoothCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT,
				maxVelocityX, maxVelocityY, maxZoomFactorChange);
		this.mCamera.setBoundsEnabled(false);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
				this.mCamera);
	}

	@Override
	public void onCreateResources() throws IOException {
		// this.mPlayerTexture = new
		// AssetBitmapTexture(this.getTextureManager(), this.getAssets(),
		// "gfx/player.png", TextureOptions.DEFAULT);
		// this.mPlayerTextureRegion =
		// TextureRegionFactory.extractTiledFromTexture(this.mPlayerTexture, 3,
		// 4);
		// this.mPlayerTexture.load();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.characterTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		this.character = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(characterTextureAtlas, this, "splash2.png", 0,
						0);
		;
		this.characterTextureAtlas.load();
		this.characterTextureAtlas2 = new BitmapTextureAtlas(
				this.getTextureManager(), 1000, 1000, TextureOptions.BILINEAR);
		this.character2 = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(characterTextureAtlas2, this, "engineer.png",
						0, 0);
		;
		this.characterTextureAtlas2.load();
		
		this.diceTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 170, 90, TextureOptions.BILINEAR);
		this.diceTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(diceTextureAtlas, this, "dice.png",
						0, 0);
		;
		this.diceTextureAtlas.load();
		
		// Pause Assets
		this.mFaceTexture = new AssetBitmapTexture(this.getTextureManager(),
				this.getAssets(), "gfx/menu.png", TextureOptions.BILINEAR);
		this.mFaceTextureRegion = TextureRegionFactory
				.extractFromTexture(this.mFaceTexture);
		this.mFaceTexture.load();

		this.mPausedTexture = new AssetBitmapTexture(this.getTextureManager(),
				this.getAssets(), "gfx/paused.png", TextureOptions.BILINEAR);
		this.mPausedTextureRegion = TextureRegionFactory
				.extractFromTexture(this.mPausedTexture);
		this.mPausedTexture.load();

		this.mResumeTexture = new AssetBitmapTexture(this.getTextureManager(),
				this.getAssets(), "gfx/resume.png", TextureOptions.BILINEAR);
		this.mResumeTextureRegion = TextureRegionFactory
				.extractFromTexture(this.mResumeTexture);
		this.mResumeTexture.load();

		this.mMainMenuTexture = new AssetBitmapTexture(
				this.getTextureManager(), this.getAssets(), "gfx/mainmenu.png",
				TextureOptions.BILINEAR);
		this.mMainMenuTextureRegion = TextureRegionFactory
				.extractFromTexture(this.mMainMenuTexture);
		this.mMainMenuTexture.load();

		this.mSaveGameTexture = new AssetBitmapTexture(this.getTextureManager(),
				this.getAssets(), "gfx/savegame.png", TextureOptions.BILINEAR);
		this.mSaveGameTextureRegion = TextureRegionFactory
				.extractFromTexture(this.mSaveGameTexture);
		this.mSaveGameTexture.load();

		// UI Fonts
		final ITexture fontTexture = new EmptyTexture(this.getTextureManager(),
				256, 256, TextureOptions.BILINEAR);
		final ITexture strokeFontTexture = new EmptyTexture(
				this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);

		this.mFont = new Font(this.getFontManager(), fontTexture,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 18, true,
				Color.BLACK);
		this.mFont.load();

		this.mStrokeFont = new StrokeFont(this.getFontManager(),
				strokeFontTexture, Typeface.create(Typeface.DEFAULT,
						Typeface.BOLD), 18, true, Color.WHITE, 1, Color.BLACK);
		this.mStrokeFont.load();

		this.mStrokeFontLarge = new StrokeFont(this.getFontManager(),
				strokeFontTexture, Typeface.create(Typeface.DEFAULT,
						Typeface.BOLD), 32, true, Color.WHITE, 1, Color.BLACK);
		this.mStrokeFontLarge.load();

	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();

		/*
		 * User Interface HUD At each corner of the screen, will display
		 * player's information Include number of credits There will be a button
		 * to roll dice and turn number will be displayed.
		 */
		final VertexBufferObjectManager vertexBufferObjectManager = this
				.getVertexBufferObjectManager();

		final Text textStroke1 = new Text(100, 300, this.mStrokeFont,
				"[Player 1 Name]\nCredits: ", vertexBufferObjectManager);
		final Text textStroke2 = new Text(400, 300, this.mStrokeFont,
				"[Player 2 Name]\nCredits: ", vertexBufferObjectManager);
		final Text textStroke3 = new Text(100, 50, this.mStrokeFont,
				"[Player 3 Name]\nCredits: ", vertexBufferObjectManager);
		final Text textStroke4 = new Text(400, 50, this.mStrokeFont,
				"[Player 4 Name]\nCredits: ", vertexBufferObjectManager);
		final Text textStroke5 = new Text(400, 100, this.mStrokeFont,
				"You rolled " + diceRoll, vertexBufferObjectManager);

		/*
		 * To update text, use [text].setText("blah blah"); In which "blah blah"
		 * is whatever you want to change the text to. You can use variables.
		 */

		mHUD = new HUD();
		mHUD.attachChild(scene);

		/*
		 * Where the button should go A fancier button should go here, but to
		 * test the randomizer, I believe this should suffice.
		 */

		final Sprite diceButton = new Sprite(250, CAMERA_HEIGHT/10, this.diceTextureRegion,
				this.getVertexBufferObjectManager()) {

			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
				/*
				 * Here, you can update the randomizer when the user presses the
				 * button. Disregard the effect, just lets me know that the
				 * button is being pressed.
				 */
				//generate random number [1,6]
				random = new Random();
				diceRoll = random.nextInt(6) + 1;
				
				if (touchEvent.isActionUp()) {
					this.setColor(Color.GRAY);
					textStroke5.setText("You rolled: " + diceRoll);
				}
				if (touchEvent.isActionDown()) {
					this.setColor(Color.WHITE);
				}
				return true;
			};
		};
		diceButton.setScale((float) .7);
		
		// Load the Pause Scene
		this.mPauseScene = new CameraScene(this.mCamera);
		final float cX = (CAMERA_WIDTH - this.mPausedTextureRegion.getWidth())
				/ 2 + (this.mPausedTextureRegion.getWidth() / 3);
		final float cY = (CAMERA_HEIGHT - this.mPausedTextureRegion.getHeight()) / 5;

		// Resume Button

		final Sprite resumeButton = new Sprite(cX + (CAMERA_WIDTH / 10), cY
				+ (CAMERA_HEIGHT / 2), this.mResumeTextureRegion,
				this.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
				switch (touchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mHUD.clearChildScene();
					scene.setIgnoreUpdate(false);
					break;
				case TouchEvent.ACTION_MOVE:
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			};
		};
		this.mPauseScene.registerTouchArea(resumeButton);
		this.mPauseScene.attachChild(resumeButton);

		// Return to main menu button

		final Sprite mainMenuButton = new Sprite(cX + (CAMERA_WIDTH / 10), cY
				+ (CAMERA_HEIGHT / 3), this.mMainMenuTextureRegion,
				this.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
				switch (touchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					scene.detachChildren();
					scene.clearEntityModifiers();
					scene.clearTouchAreas();
					scene.clearUpdateHandlers();
					mHUD.detachChildren();
					mPauseScene.detachChildren();
					this.detachSelf();
					onClick(mRenderSurfaceView);
					finish();
					break;
				case TouchEvent.ACTION_MOVE:
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			};
		};
		
		//save game button
		final Sprite saveGameButton = new Sprite(cX+(CAMERA_WIDTH/10), cY + (CAMERA_HEIGHT/4),
				this.mSaveGameTextureRegion, this.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
				switch (touchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					break;
				case TouchEvent.ACTION_MOVE:
					break;
				case TouchEvent.ACTION_UP:
					break;
				}
				return true;
			};
		};
		this.mPauseScene.registerTouchArea(mainMenuButton);
		this.mPauseScene.attachChild(mainMenuButton);

		/* Makes the paused Game look through. */
		this.mPauseScene.setBackgroundEnabled(false);

		// Main Menu Button on HUD
		final Sprite pauseSprite = new Sprite(mCamera.getWidth()
				- (mCamera.getWidth() / 2), 300, this.mFaceTextureRegion,
				this.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
				switch (touchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					scene.setIgnoreUpdate(true);
					mHUD.setChildScene(mPauseScene, false, true, true);
					break;
				case TouchEvent.ACTION_MOVE:

					break;
				case TouchEvent.ACTION_UP:

					break;
				}
				return true;
			};
		};

		mHUD.attachChild(textStroke1);
		mHUD.attachChild(textStroke2);
		mHUD.attachChild(textStroke3);
		mHUD.attachChild(textStroke4);
		mHUD.attachChild(textStroke5);

		mHUD.registerTouchArea(diceButton);
		mHUD.attachChild(diceButton);

		mHUD.registerTouchArea(pauseSprite);
		mHUD.attachChild(pauseSprite);

		mCamera.setHUD(mHUD);

		/* Main Map Scene */

		scene.setOnSceneTouchListener(this);

		try {
			final TMXLoader tmxLoader = new TMXLoader(this.getAssets(),
					this.mEngine.getTextureManager(),
					TextureOptions.BILINEAR_PREMULTIPLYALPHA,
					this.getVertexBufferObjectManager(),
					new ITMXTilePropertiesListener() {
						@Override
						public void onTMXTileWithPropertiesCreated(
								final TMXTiledMap pTMXTiledMap,
								final TMXLayer pTMXLayer,
								final TMXTile pTMXTile,
								final TMXProperties<TMXTileProperty> pTMXTileProperties) {
							/*
							 * We are going to count the tiles that have the
							 * property "cactus=true" set.
							 */
							// if(pTMXTileProperties.containsTMXProperty("cactus",
							// "true")) {
							// MainGameScreen.this.mCactusCount++;
							// }
						}
					});
			this.mTMXTiledMap = tmxLoader
					.loadFromAsset("tmx/New_Multi_Gator_map.tmx");
			this.mTMXTiledMap.setOffsetCenter(0, 0);

			// this.toastOnUiThread("Cactus count in this TMXTiledMap: " +
			// MainGameScreen.this.mCactusCount, Toast.LENGTH_LONG);
		} catch (final TMXLoadException e) {
			Debug.e(e);
		}
		moving = false;
		turnDone = false;
		turnNum = 1;
		ranNumb = 1 + (int) (Math.random() * ((6 - 1) + 1));
		numCharacters = 2;

		scene.attachChild(this.mTMXTiledMap);

		/* Make the camera not exceed the bounds of the TMXEntity. */
		this.mCamera.setBoundsEnabled(false);
		this.mCamera.setBounds(0, 0, this.mTMXTiledMap.getWidth(),
				this.mTMXTiledMap.getHeight());
		this.mCamera.setBoundsEnabled(true);

		final float centerX = 8 * (32) - 16; // minus 16 for image alignment
		final float centerY = 13 * (32) - 10; // minus 10 for image alignment
		currentX = centerX;
		currentY = centerY;
		currentX2 = centerX + 32;
		currentY2 = centerY;
		// positionX[0] = centerX;
		// positionY[0] = centerY;

		final Sprite mySprite = new Sprite(currentX, currentY, character,
				this.getVertexBufferObjectManager());
		mySprite.setScale((float) .1);
		// final Sprite mySprite2 = new Sprite(currentX,currentY, character,
		// this.getVertexBufferObjectManager());
		// mySprite2.setScale((float) .1);
		// scene.attachChild(mySprite);

		final Sprite mySprite2 = new Sprite(currentX2, currentY2, character2,
				this.getVertexBufferObjectManager());
		mySprite2.setScale((float) .1);

		final Sprite[] SpriteList = new Sprite[numCharacters];

		if (numCharacters >= 1)
			SpriteList[0] = mySprite;
		if (numCharacters >= 2)
			SpriteList[1] = mySprite2;

		/* Create the sprite and add it to the scene. */
		// final AnimatedSprite player = new AnimatedSprite(centerX, centerY,
		// this.character, this.getVertexBufferObjectManager());
		this.mCamera.setChaseEntity(mySprite);
		currentCharacter = 0;

		// final Path path = new Path(5).to(50, 740).to(50, 1000).to(820,
		// 1000).to(820, 740).to(0);

		// player.registerEntityModifier(new LoopEntityModifier(new
		// PathModifier(30, path, null, new IPathModifierListener() {
		// @Override
		// public void onPathStarted(final PathModifier pPathModifier, final
		// IEntity pEntity) {
		//
		// }
		//
		// @Override
		// public void onPathWaypointStarted(final PathModifier pPathModifier,
		// final IEntity pEntity, final int pWaypointIndex) {
		// switch(pWaypointIndex) {
		// case 0:
		// player.animate(new long[] { 200, 200, 200 }, 0, 2, true);
		// break;
		// case 1:
		// player.animate(new long[] { 200, 200, 200 }, 3, 5, true);
		// break;
		// case 2:
		// player.animate(new long[] { 200, 200, 200 }, 6, 8, true);
		// break;
		// case 3:
		// player.animate(new long[] { 200, 200, 200 }, 9, 11, true);
		// break;
		// }
		// }
		//
		// @Override
		// public void onPathWaypointFinished(final PathModifier pPathModifier,
		// final IEntity pEntity, final int pWaypointIndex) {
		//
		// }
		//
		// @Override
		// public void onPathFinished(final PathModifier pPathModifier, final
		// IEntity pEntity) {
		//
		// }
		// })));

		/*
		 * Now we are going to create a rectangle that will always highlight the
		 * tile below the feet of the pEntity.
		 */
		final Rectangle currentTileRectangle = new Rectangle(0, 0,
				this.mTMXTiledMap.getTileWidth(),
				this.mTMXTiledMap.getTileHeight(),
				this.getVertexBufferObjectManager());
		/* Set the OffsetCenter to 0/0, so that it aligns with the TMXTiles. */
		currentTileRectangle.setOffsetCenter(0, 0);
		currentTileRectangle.setColor(1, 0, 0, 0.25f);
		scene.attachChild(currentTileRectangle);

		/* The layer for the player to walk on. */
		final TMXLayer tmxLayer = this.mTMXTiledMap.getTMXLayers().get(0);

		scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {
			}

			@Override
			public void onUpdate(final float pSecondsElapsed) {
				// /* Get the scene-coordinates of the players feet. */
				float[] localCoord = new float[2];
				localCoord[0] = SpriteList[currentCharacter].getWidth() * .5f;
				localCoord[1] = SpriteList[currentCharacter].getHeight() * .5f;
				final float[] playerFootCordinates = SpriteList[currentCharacter]
						.convertLocalCoordinatesToSceneCoordinates(localCoord);
				//
				/* Get the tile the feet of the player are currently waking on. */
				final TMXTile tmxTile = tmxLayer.getTMXTileAt(
						playerFootCordinates[Constants.VERTEX_INDEX_X],
						playerFootCordinates[Constants.VERTEX_INDEX_Y]);
				if (tmxTile != null) {
					// tmxTile.setTextureRegion(null); <-- Eraser-style removing
					// of tiles =D
					currentTileRectangle.setPosition(
							tmxLayer.getTileX(tmxTile.getTileColumn()),
							tmxLayer.getTileY(tmxTile.getTileRow()));
				}

				if (move) {
					movementFunction(SpriteList[currentCharacter]);
					moving = true;
					MainGameScreen.this.mCamera.updateChaseEntity();

				}

				if (moving == true && turnDone == true && swipeDone == false) {
					moving = false;
					turnDone = false;
					currentCharacter = (currentCharacter + 1) % (numCharacters);
					MainGameScreen.this.mCamera
							.setChaseEntity(SpriteList[currentCharacter]);
					// if(currentCharacter==0){
					// SpriteList[currentCharacter].registerEntityModifier(new
					// MoveModifier(0.5f,currentX,currentY, currentX,
					// currentY+1));
					// SpriteList[currentCharacter].registerEntityModifier(new
					// MoveModifier(0.5f,currentX,currentY, currentX,
					// currentY-1));
					// }else if(currentCharacter ==1){
					// SpriteList[currentCharacter].registerEntityModifier(new
					// MoveModifier(0.5f,currentX2,currentY2, currentX2,
					// currentY2+1 ));
					// SpriteList[currentCharacter].registerEntityModifier(new
					// MoveModifier(0.5f,currentX2,currentY2, currentX2,
					// currentY2-1 ));
					// }
				}

				if (swipeDone == false) {
					ranNumb = (1 + (int) (Math.random() * ((3 - 1) + 1))) * 32;
				}

			}
		});
		scene.attachChild(SpriteList[0]);
		scene.attachChild(SpriteList[1]);

		return scene;
	}

	boolean move = false;

	protected void movementFunction(final Sprite mySprite) {
		// Swipe up
		if (swipeDone == true && (finalY - initY) > 40) {
			if (currentCharacter == 0) {
				mySprite.registerEntityModifier(new MoveModifier(0.5f,
						currentX, currentY, currentX, currentY + (ranNumb)) {
					@Override
					protected void onModifierStarted(IEntity pItem) {
						super.onModifierStarted(pItem);
						move = false;
						gameDone = false;
					}

					@Override
					protected void onModifierFinished(IEntity pItem) {
						currentX = mySprite.getX();
						currentY = mySprite.getY();
						super.onModifierFinished(pItem);
						checkMiniGameHotSpots();
						swipeDone = false;
						turnDone = true;

					}
				});
			} else if (currentCharacter == 1) {
				mySprite.registerEntityModifier(new MoveModifier(0.5f,
						currentX2, currentY2, currentX2, currentY2 + (ranNumb)) {
					@Override
					protected void onModifierStarted(IEntity pItem) {
						super.onModifierStarted(pItem);
						move = false;
						gameDone = false;
					}

					@Override
					protected void onModifierFinished(IEntity pItem) {
						currentX2 = mySprite.getX();
						currentY2 = mySprite.getY();
						super.onModifierFinished(pItem);
						checkMiniGameHotSpots();
						swipeDone = false;
						turnDone = true;

					}
				});
			}
		}
		// Swipe down
		if (swipeDone == true && (finalY - initY) < -40) {
			if (currentCharacter == 0) {
				mySprite.registerEntityModifier(new MoveModifier(0.8f,
						currentX, currentY, currentX, currentY - (ranNumb)) {
					@Override
					protected void onModifierStarted(IEntity pItem) {
						super.onModifierStarted(pItem);
						move = false;
						gameDone = false;
					}

					@Override
					protected void onModifierFinished(IEntity pItem) {
						currentX = mySprite.getX();
						currentY = mySprite.getY();
						super.onModifierFinished(pItem);
						checkMiniGameHotSpots();
						swipeDone = false;
						turnDone = true;

					}
				});
			} else if (currentCharacter == 1) {
				mySprite.registerEntityModifier(new MoveModifier(0.5f,
						currentX2, currentY2, currentX2, currentY2 - (ranNumb)) {
					@Override
					protected void onModifierStarted(IEntity pItem) {
						super.onModifierStarted(pItem);
						move = false;
						gameDone = false;
					}

					@Override
					protected void onModifierFinished(IEntity pItem) {
						currentX2 = mySprite.getX();
						currentY2 = mySprite.getY();
						super.onModifierFinished(pItem);
						checkMiniGameHotSpots();
						swipeDone = false;
						turnDone = true;

					}
				});
			}
		}

		// Swipe left
		if (swipeDone == true && (finalX - initX) > 40) {
			if (currentCharacter == 0) {
				mySprite.registerEntityModifier(new MoveModifier(0.8f,
						currentX, currentY, currentX + (ranNumb), currentY) {
					@Override
					protected void onModifierStarted(IEntity pItem) {
						super.onModifierStarted(pItem);
						move = false;
						gameDone = false;
					}

					@Override
					protected void onModifierFinished(IEntity pItem) {
						currentX = mySprite.getX();
						currentY = mySprite.getY();
						super.onModifierFinished(pItem);
						checkMiniGameHotSpots();
						swipeDone = false;
						turnDone = true;

					}
				});
			} else if (currentCharacter == 1) {
				mySprite.registerEntityModifier(new MoveModifier(0.5f,
						currentX2, currentY2, currentX2 + (ranNumb), currentY2) {
					@Override
					protected void onModifierStarted(IEntity pItem) {
						super.onModifierStarted(pItem);
						move = false;
						gameDone = false;
					}

					@Override
					protected void onModifierFinished(IEntity pItem) {
						currentX2 = mySprite.getX();
						currentY2 = mySprite.getY();
						super.onModifierFinished(pItem);
						checkMiniGameHotSpots();
						swipeDone = false;
						turnDone = true;

					}
				});
			}
		}

		// Swipe right
		if (swipeDone == true && (finalX - initX) < -40) {
			if (currentCharacter == 0) {
				mySprite.registerEntityModifier(new MoveModifier(0.8f,
						currentX, currentY, currentX - (ranNumb), currentY) {
					@Override
					protected void onModifierStarted(IEntity pItem) {
						super.onModifierStarted(pItem);
						move = false;
						gameDone = false;
					}

					@Override
					protected void onModifierFinished(IEntity pItem) {
						currentX = mySprite.getX();
						currentY = mySprite.getY();
						super.onModifierFinished(pItem);
						checkMiniGameHotSpots();
						swipeDone = false;
						turnDone = true;
					}
				});
			} else if (currentCharacter == 1) {
				mySprite.registerEntityModifier(new MoveModifier(0.5f,
						currentX2, currentY2, currentX2 - (ranNumb), currentY2) {
					@Override
					protected void onModifierStarted(IEntity pItem) {
						super.onModifierStarted(pItem);
						move = false;
						gameDone = false;
					}

					@Override
					protected void onModifierFinished(IEntity pItem) {
						currentX2 = mySprite.getX();
						currentY2 = mySprite.getY();
						super.onModifierFinished(pItem);
						checkMiniGameHotSpots();
						swipeDone = false;
						turnDone = true;

					}
				});
			}
		}
	}

	// Checks the hot spots for the minigames
	protected void checkMiniGameHotSpots() {

		if (currentY >= 470 && currentY < 502 && currentX >= 224
				&& currentX < 256 && move == false && gameDone == false) {
			Intent intent = new Intent(MainGameScreen.this, WiresMiniGame.class);
			startActivity(intent);
			gameDone = true;
		} else if (currentY >= 342 && currentY < 374 && currentX >= 224
				&& currentX < 256 && move == false && gameDone == false) {
			Intent intent = new Intent(MainGameScreen.this,
					BenchPressMinigame.class);
			startActivity(intent);
			gameDone = true;
		}

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_UP)
		// {
		// move = true;
		//
		// }
		if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {
			initX = pSceneTouchEvent.getX();
			initY = pSceneTouchEvent.getY();
			move = false;
		}
		if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_MOVE) {
			move = true;
			finalX = pSceneTouchEvent.getX();
			finalY = pSceneTouchEvent.getY();
			swipeDone = true;

		}
		return false;
	}

	public void onClick(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	public synchronized void onResumeGame() {
		if (this.mEngine != null)
			super.onResumeGame();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
