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

import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

public class WaitInLineMinigame extends SimpleBaseGameActivity implements IOnSceneTouchListener {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	
	private static int DEVICE_WIDTH;
	private static int DEVICE_HEIGHT;

	private BitmapTextureAtlas characterTextureAtlas;
    public ITextureRegion character;
    
    private BitmapTextureAtlas spr1TextureAtlas;
    public ITextureRegion spr1TextureRegion;
    
    private BitmapTextureAtlas bgTextureAtlas;
    private ITextureRegion bgRegion;
    
    private Sprite sprChar;
    private Sprite sprBg;
    private Sprite spr1;
    
    // Coordinates of sprites
    private float currentX = 250;
    private float currentY = 20;
    
    private float spr1X = 40;
    private float spr1Y = 200;
    
    final Scene scene = new Scene();
    
    private boolean motionDown = false;
    
    // Need handler for callbacks to the UI thread
    final Handler mHandler = new Handler();

    // Create runnable for posting
    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            updateResultsInUi();
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
		this.characterTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, 
				TextureOptions.BILINEAR);
		this.character = BitmapTextureAtlasTextureRegionFactory.createFromAsset(characterTextureAtlas, this, 
				"splash2.png", 0, 0);
		this.characterTextureAtlas.load();
		
		this.spr1TextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, 
				TextureOptions.BILINEAR);
		this.spr1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(spr1TextureAtlas, this, 
				"splash2.png", 0, 0);
		this.spr1TextureAtlas.load();
		
		this.bgTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 960, 720, 
				TextureOptions.NEAREST_PREMULTIPLYALPHA);
		this.bgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bgTextureAtlas, this, 
				"hub.png", 0, 0);
		this.bgTextureAtlas.load();
		
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		scene.setOnSceneTouchListener(this);
		
		scene.setBackground(new Background(Color.WHITE));
		sprBg = new Sprite(CAMERA_WIDTH - 80, CAMERA_HEIGHT - 10, bgRegion, this.getVertexBufferObjectManager());

		sprChar = new Sprite(currentX, currentY, character, this.getVertexBufferObjectManager());
		sprChar.setScale(0.125f);
		
		spr1 = new Sprite(spr1X, spr1Y, spr1TextureRegion, this.getVertexBufferObjectManager());
		spr1.setScale(0.1f);
		
		scene.attachChild(sprBg);
		scene.attachChild(sprChar);
		scene.attachChild(spr1);
		
//        scene.setBackground(new SpriteBackground(new Sprite(0, 0, DEVICE_WIDTH, DEVICE_HEIGHT, 
//        		this.bgRegion, getVertexBufferObjectManager())));
//
//        scene.setPosition(0, 0);
		
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
		scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() { }

			@Override
			public void onUpdate(final float pSecondsElapsed) {
				// Motion for sprite 1
				spr1.registerEntityModifier(new MoveModifier(0.05f,
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
				// Motion for current character
				if (currentY <= CAMERA_HEIGHT + 32 && !motionDown) {
					sprChar.registerEntityModifier(new MoveModifier(0.04f,
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
			}
		});
	}
}
