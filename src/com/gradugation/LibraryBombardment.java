package com.gradugation;

import java.io.IOException;
import java.util.Date;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.TextUtils;
import org.andengine.util.adt.color.Color;
import org.andengine.util.math.MathUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.widget.EditText;

public class LibraryBombardment extends SimpleBaseGameActivity {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	static int CREDITS_EARNED = 3;

	private float mEffectSpawnDelay = 1;
	Scene scene;
	TimerHandler spriteTimerHandler;

	private int scoreCoffee = 0;
	private int scoreFriend = 0;
	private int scoreBook = 0;

	private int spritesCount = 0;

	private ITextureRegion[] dollars = new ITextureRegion[10];
	private BitmapTextureAtlas[] dollarsAtlas = new BitmapTextureAtlas[10];

	private ITextureRegion bgR;
	private BitmapTextureAtlas bgA;
	private Sprite bgS;

	private Sprite[] sprites = new Sprite[10];

	private AlertDialog.Builder alertDialogBuilder;
	private AlertDialog alertDialog;

    private String characterType;
	
	private boolean gameStarted = false;
	private boolean gameOver = false;

	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera mCamera = new BoundCamera(0, 0, CAMERA_WIDTH,
				CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	final Handler mHandler = new Handler();

	// Create runnable for posting
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			updateResultsInUi();
		}
	};

	private void updateResultsInUi() {
		alertDialogBuilder = new AlertDialog.Builder(this);

		// set title and message
		alertDialogBuilder
				.setTitle("Keep track (separately) of how many books, friends, coffee mugs appear on the screen!");
		alertDialogBuilder.setMessage("Press Continue to play.");
		alertDialogBuilder.setCancelable(false);

		// create continue button
		alertDialogBuilder.setNeutralButton("Continue",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, close
						// dialog box
						gameStarted = true;
						mHandler.post(spawnerStart);
						dialog.cancel();
						dialog.dismiss();
					}
				});

		// create alert dialog
		alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	final Runnable spawnerStart = new Runnable() {
		public void run() {
			createSpriteSpawnTimeHandler();
		}
	};

	final Runnable finishGame = new Runnable() {
		public void run() {
			finishGame();
			gameOver = true;
		}
	};

	private void finishGame() {
		gameOver = true;
		
		String whichScore;
		final int rand = 1 + (int) (Math.random() * 3);
		
		if(rand == 1)
			whichScore = "Books";
		else if(rand == 2)
			whichScore = "Coffees";
		else
			whichScore = "Friends";
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("How many "+ whichScore +" appeared on the screen?");
		alert.setMessage("Input amount below.");

		alert.setCancelable(false);

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Editable value = input.getText();
				// Do something with value!
				// if(value.toString().equals("")){
				// lose();
				// }
				try {
					int x = Integer.parseInt(value.toString());
					if (((rand == 1)&&(x == scoreBook))||((rand == 2)&&(x == scoreCoffee))||((rand == 3)&&(x == scoreFriend))) {
						win();
					} else {
						lose();
					}
				} catch (NumberFormatException e) {
					lose();
				}

			}
		});

		alert.show();
	}

	private void lose() {
		Intent output = new Intent();
		output.putExtra(Event.MCOUNT_REQUEST_CODE+"", 0);
		setResult(RESULT_OK, output);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Sorry!");
		alert.setMessage("Come back to try again.");

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Do something with value!
				finish();
			}
		});

		alert.show();
	}

	private void win() {
		// update the player's credits
		Intent output = new Intent();
		output.putExtra(Event.LIB_WEST_REQUEST_CODE + "", CREDITS_EARNED);
		setResult(RESULT_OK, output);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Correct!");
		alert.setMessage("You've earned some credits!");

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Do something with value!
				finish();
			}
		});

		alert.show();

	}

	@Override
	protected void onCreateResources() throws IOException {
		
		characterType = getIntent().getStringExtra("character_type");
		if (characterType == null) characterType = "Gradugator";
		
		int rand = -1;
		int prevRand = -1;
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("gfx/lib_west_images/");

		for (int i = 0; i < 10; i++) {
			while(rand == prevRand){
				rand = 1 + (int) (Math.random() * 3);
			}
			if (rand == 1) {
				scoreBook += 1;
				dollarsAtlas[i] = new BitmapTextureAtlas(
						this.getTextureManager(), 1024, 1024,
						TextureOptions.BILINEAR);
				dollars[i] = BitmapTextureAtlasTextureRegionFactory
						.createFromAsset(dollarsAtlas[i], this,
								"Book.png", 0, 0);
				dollarsAtlas[i].load();
				prevRand = rand;
			} else if (rand == 2) {
				scoreCoffee += 1;
				dollarsAtlas[i] = new BitmapTextureAtlas(
						this.getTextureManager(), 1024, 1024,
						TextureOptions.BILINEAR);
				dollars[i] = BitmapTextureAtlasTextureRegionFactory
						.createFromAsset(dollarsAtlas[i], this,
								"Coffee.png", 0, 0);
				dollarsAtlas[i].load();
				prevRand = rand;
			} else if (rand == 3) {
				scoreFriend += 1;
				dollarsAtlas[i] = new BitmapTextureAtlas(
						this.getTextureManager(), 1024, 1024,
						TextureOptions.BILINEAR);
				dollars[i] = BitmapTextureAtlasTextureRegionFactory
						.createFromAsset(dollarsAtlas[i], this,
								"Friend.png", 0, 0);
				dollarsAtlas[i].load();
				prevRand = rand;
			}
		}
		bgA = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024,
				TextureOptions.BILINEAR);
		bgR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bgA, this,
				"libWestBG.jpg", 0, 0);
		bgS = new Sprite(240, 160, bgR, this.getVertexBufferObjectManager());
		this.bgA.load();
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		scene = new Scene();
		scene.setBackground(new Background(Color.WHITE));

		// bgS.setScale((float) .5);
		scene.attachChild(bgS);

		mHandler.post(mUpdateResults);

		return scene;
	}

	private void createSprite(float pX, float pY, int spriteNumber) {
		sprites[spriteNumber] = new Sprite(pX, pY, dollars[spriteNumber],
				this.getVertexBufferObjectManager());
		sprites[spriteNumber].setScale((float) .2);
		scene.attachChild(sprites[spriteNumber]);
	}

	private void detachSprite(int whatSpriteToDelete) {
		if (whatSpriteToDelete >= 0)
			scene.detachChild(sprites[whatSpriteToDelete]);
	}

	private void createSpriteSpawnTimeHandler() {
		this.mEngine
				.registerUpdateHandler(spriteTimerHandler = new TimerHandler(
						mEffectSpawnDelay, new ITimerCallback() {
							@Override
							public void onTimePassed(
									final TimerHandler pTimerHandler) {
								spriteTimerHandler.reset();

								final float xPos = 240;
								final float yPos = 160;

								createSprite(xPos, yPos, spritesCount);
								detachSprite(spritesCount - 1);
								spritesCount++;
								if (spritesCount >= 10) {
									// endGame();
									mEngine.unregisterUpdateHandler(spriteTimerHandler);
									gameOver = true;
									mHandler.post(finishGame);
								}
							}
						}));
	}

}
