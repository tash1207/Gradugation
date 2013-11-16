package com.gradugation;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

public class WaitInLineMinigame extends SimpleBaseGameActivity implements IOnSceneTouchListener {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	
	private static int DEVICE_WIDTH;
	private static int DEVICE_HEIGHT;
	
	// The number of credits earned by completing this minigame
	static int CREDITS_EARNED = 3;
	
    private BitmapTextureAtlas bgTextureAtlas;
    private ITextureRegion bgRegion;

	private BitmapTextureAtlas characterTextureAtlas;
    public ITextureRegion character;
    
    private BitmapTextureAtlas athleteTextureAtlas;
    public ITextureRegion athleteTextureRegion;
    
    private BitmapTextureAtlas engineerTextureAtlas;
    public ITextureRegion engineerTextureRegion;
    
    private BitmapTextureAtlas busTextureAtlas;
    public ITextureRegion busTextureRegion;
    
    private Sprite sprChar;
    private Sprite sprBg;
    private Sprite spr1;
    private Sprite spr2;
    private Sprite sprBus;
    
    // Coordinates of sprites
    private float currentX = 275;
    private float currentY = 15;
    
    private float spr1X = 65;
    private float spr1Y = 205;
    
    private float spr2X = 400;
    private float spr2Y = 120;
    
    private float sprBusX = 75;
    private float sprBusY = 0;
    
    final Scene scene = new Scene();
    
    CountDownTimer timer;
    
    private boolean gameStarted = false;
    private boolean motionDown = false;
    boolean collisionOccurred = false;
    boolean timeUp = false;
    boolean finalDialogShown = false;
    boolean gameOver = false;
    
    // Need handler for callbacks to the UI thread
    final Handler mHandler = new Handler();

    // Create runnable for posting
    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            updateResultsInUi();
        }
    };
    
    final Runnable finishGame = new Runnable() {
        public void run() {
            finishGame();
            gameOver = true;
        }
    };

	@Override
	public EngineOptions onCreateEngineOptions() {
		DisplayMetrics dm = new DisplayMetrics();
	    this.getWindowManager().getDefaultDisplay().getMetrics(dm);
	    DEVICE_WIDTH = dm.widthPixels;
	    DEVICE_HEIGHT = dm.heightPixels;
	      
		final Camera mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(DEVICE_WIDTH, DEVICE_HEIGHT), mCamera);
	}

	@Override
	protected void onCreateResources() throws IOException {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		// Background
		this.bgTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 960, 720, 
				TextureOptions.NEAREST_PREMULTIPLYALPHA);
		this.bgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bgTextureAtlas, this, 
				"hub.png", 0, 0);
		this.bgTextureAtlas.load();
		
		// Main character
		this.characterTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, 
				TextureOptions.BILINEAR);
		this.character = BitmapTextureAtlasTextureRegionFactory.createFromAsset(characterTextureAtlas, this, 
				"splash2.png", 0, 0);
		this.characterTextureAtlas.load();
		
		// Athletes
		this.athleteTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 530, 530, 
				TextureOptions.BILINEAR);
		this.athleteTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(athleteTextureAtlas, this, 
				"athlete.png", 0, 0);
		this.athleteTextureAtlas.load();
		
		// Engineers
		this.engineerTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 530, 530, 
				TextureOptions.BILINEAR);
		this.engineerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(engineerTextureAtlas, this, 
				"engineer.png", 0, 0);
		this.engineerTextureAtlas.load();
		
		// Bus
		this.busTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 214, 324, 
				TextureOptions.BILINEAR);
		this.busTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(busTextureAtlas, this, 
				"bus.png", 0, 0);
		this.busTextureAtlas.load();
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		scene.setOnSceneTouchListener(this);
		
		scene.setBackground(new Background(Color.WHITE));
		sprBg = new Sprite(CAMERA_WIDTH - 80, CAMERA_HEIGHT - 10, bgRegion, this.getVertexBufferObjectManager());

		sprChar = new Sprite(currentX, currentY, character, this.getVertexBufferObjectManager());
		sprChar.setScale(0.125f);
		
		spr1 = new Sprite(spr1X, spr1Y, athleteTextureRegion, this.getVertexBufferObjectManager());
		spr1.setScale(0.115f);
		spr1.setVisible(false);
		
		spr2 = new Sprite(spr2X, spr2Y, engineerTextureRegion, this.getVertexBufferObjectManager());
		spr2.setScale(0.115f);
		spr2.setVisible(false);
		
		sprBus = new Sprite(sprBusX, sprBusY, busTextureRegion, this.getVertexBufferObjectManager());
		sprBus.setScale(0.8f);
		
		scene.attachChild(sprBg);
		scene.attachChild(spr1);
		scene.attachChild(spr2);
		scene.attachChild(sprChar);
		scene.attachChild(sprBus);
		
		mHandler.post(mUpdateResults);

        return scene;
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN)
			motionDown = true;
	    if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_UP)
	    	motionDown = false;
		return false;
	}
	
	public void updateResultsInUi() {
		// Show instructions before game begins
		if (!gameStarted) {
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setMessage(getString(R.string.wait_in_line_instructions));
	        builder.setCancelable(false);
	        builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
	
	                @Override
	                public void onClick(DialogInterface dialog, int arg1) {
	                        dialog.dismiss();
	                        gameStarted = true;
	                		timer = new CountDownTimer(15800, 1000) {
	                			public void onTick(long millisUntilFinished) {
	                			}
	                			
	                			public void onFinish() {
	                				if (!gameOver) {
	                					timeUp = true;
	                					mHandler.post(finishGame);
	                				}
	                			}
	                		};
	                		timer.start();
	                }
	        });
	        AlertDialog dialog = builder.create();
	        dialog.show();
		}
        
		scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() { }

			@Override
			public void onUpdate(final float pSecondsElapsed) {
				if (gameStarted && !gameOver) {
					// Check for collisions
					if ((Math.abs(spr1.getX() - sprChar.getX()) < 35 && Math.abs(spr1.getY() - sprChar.getY()) < 51) ||
						(Math.abs(spr2.getX() - sprChar.getX()) < 35 && Math.abs(spr2.getY() - sprChar.getY()) < 51)) {
						collisionOccurred = true;
						mHandler.post(finishGame);
						finalDialogShown = true;
					}
					
					if (sprBusY < 180) {
						// Motion for bus
						sprBus.registerEntityModifier(new MoveModifier(0.03f,
								sprBusX, sprBusY, sprBusX, sprBusY + 3) {
							@Override
					    	protected void onModifierStarted(IEntity pItem) {
					        	super.onModifierStarted(pItem);
							}
		
					        @Override
					        protected void onModifierFinished(IEntity pItem) {
					        	sprBusX = sprBus.getX();
					        	sprBusY = sprBus.getY();
					            super.onModifierFinished(pItem);
					        }
						});
					}
					else {
						spr1.setVisible(true);
						// When sprite 1 is done exiting the bus off the screen, have another "sprite 1"
						// exit the bus
						if (spr1X > CAMERA_WIDTH && currentY < CAMERA_HEIGHT)  {
							spr1X = 65;
							spr1Y = 220;
						}
						// Motion for sprite 1
						spr1.registerEntityModifier(new MoveModifier(0.04f,
								spr1X, spr1Y, spr1X + 2, spr1Y) {
							@Override
					    	protected void onModifierStarted(IEntity pItem) {
					        	super.onModifierStarted(pItem);
							}
		
					        @Override
					        protected void onModifierFinished(IEntity pItem) {
					        	spr1X = spr1.getX();
					        	spr1Y = spr1.getY();
					            super.onModifierFinished(pItem);
					        }
						});
					}
					spr2.setVisible(true);
					// When sprite 2 is done walking to the bus, have another "sprite 2"
					// walk from the top right of the screen
					if (spr2X < 88 && currentY < CAMERA_HEIGHT)  {
						spr2X = CAMERA_WIDTH + 100;
						spr2Y = 285;
					}
					// Motion for sprite 2
					spr2.registerEntityModifier(new MoveModifier(0.03f,
							spr2X, spr2Y, spr2X - 2, spr2Y) {
						@Override
				    	protected void onModifierStarted(IEntity pItem) {
				        	super.onModifierStarted(pItem);
						}
	
				        @Override
				        protected void onModifierFinished(IEntity pItem) {
				        	spr2X = spr2.getX();
				        	spr2Y = spr2.getY();
				            super.onModifierFinished(pItem);
				        }
					});
					// Motion for current character
					if (currentY <= CAMERA_HEIGHT + 32 && !motionDown) {
						sprChar.registerEntityModifier(new MoveModifier(0.028f,
								currentX, currentY, currentX, currentY + 1) {
							@Override
					    	protected void onModifierStarted(IEntity pItem) {
					        	super.onModifierStarted(pItem);
							}
	
					        @Override
					        protected void onModifierFinished(IEntity pItem) {
					        	currentX = sprChar.getX();
					        	currentY = sprChar.getY();
					            super.onModifierFinished(pItem);
					        }
						});
					}
					if (currentY > CAMERA_HEIGHT + 30 && !finalDialogShown) {
						Log.d("finished", "game");
						mHandler.post(finishGame);
						finalDialogShown = true;
					}
				}
			}
		});
	}
	
	public void finishGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Intent output = new Intent();
        if (collisionOccurred) {
        	builder.setMessage(getString(R.string.wait_in_line_failure_hit));
        	output.putExtra(Event.WAIT_IN_LINE_REQUEST_CODE+"", CREDITS_EARNED);
        }
        else if (timeUp) {
        	builder.setMessage(getString(R.string.wait_in_line_failure_time));
        	output.putExtra(Event.WAIT_IN_LINE_REQUEST_CODE+"", CREDITS_EARNED);
        }
        else {
        	builder.setMessage(getString(R.string.wait_in_line_success, CREDITS_EARNED));
        	// Code to give character CREDITS_EARNED credits
        	output.putExtra(Event.WAIT_IN_LINE_REQUEST_CODE+"", CREDITS_EARNED);
        }
        setResult(RESULT_OK, output);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int arg1) {
                        dialog.dismiss();
                        finish();
                }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
	}
}
