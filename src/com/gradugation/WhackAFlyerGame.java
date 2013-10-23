package com.gradugation;

import java.io.IOException;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
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
import org.andengine.util.math.MathUtils;

import android.os.Handler;
import android.view.Menu;

public class WhackAFlyerGame extends SimpleBaseGameActivity implements IOnSceneTouchListener {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	private static final int MAX_NUMBER_OF_FLYERS = 4;
	private static final int MAX_TIME_DELAY_FOR_FLYER = 5;
	private static final int MIN_TIME_DELAY_FOR_FLYER = 2;

	private BitmapTextureAtlas characterTextureAtlas;
    public ITextureRegion character;
    private BitmapTextureAtlas bgTextureAtlas;
    private ITextureRegion bgRegion;
    private Sprite sprImage;
    private float currentX;
    private float currentY;
    private float mEffectSpawnDelay;
    final Scene scene = new Scene();
    private int[][] flyerPositions = new int[MAX_NUMBER_OF_FLYERS][2];
    private ITextureRegion[] flyers = new ITextureRegion[MAX_NUMBER_OF_FLYERS];
    private BitmapTextureAtlas[] flyerAtlas = new BitmapTextureAtlas[MAX_NUMBER_OF_FLYERS];
    
 // Need handler for callbacks to the UI thread
    final Handler mHandler = new Handler();

    // Create runnable for posting
    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            updateResultsInUi();
        }
    };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.whack_aflyer, menu);
		return true;
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera mCamera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,// LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	@Override
	protected void onCreateResources() throws IOException {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.characterTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 512,512,TextureOptions.BILINEAR);
		this.character = BitmapTextureAtlasTextureRegionFactory.createFromAsset(characterTextureAtlas, this, "splash2.png", 0, 0);;
		this.characterTextureAtlas.load();
		
		this.bgTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 951,720,TextureOptions.NEAREST_PREMULTIPLYALPHA);
		this.bgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bgTextureAtlas, this, "bricks.png", 0, 0);;
		this.bgTextureAtlas.load();
		
		for (int i = 0, pos = 0; i < MAX_NUMBER_OF_FLYERS; i++, pos += CAMERA_WIDTH / MAX_NUMBER_OF_FLYERS) {
			//X position
			flyerPositions[i][0] = 0;
			//Y position
			flyerPositions[i][1] = pos;
		}
		
		for (int i = 0; i < MAX_NUMBER_OF_FLYERS; i++) {
			String imgName = "flyer_" + (i+1) + ".png";
			this.flyerAtlas[i] = new BitmapTextureAtlas(this.getTextureManager(), 1024,1024,TextureOptions.BILINEAR);
			this.flyers[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(flyerAtlas[i], this, imgName, 0, 0);;
			this.flyerAtlas[i].load(); 
		}
	}
 

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		scene.setOnSceneTouchListener(this);
		
		final float centerX = 239;
		final float centerY = 0;
		currentX = centerX;
		currentY = centerY;

		
		
		scene.setBackground(new Background(Color.WHITE));
		   
		final Sprite mySprite = new Sprite(0, 0, bgRegion, this.getVertexBufferObjectManager());
		
		  
		sprImage = new Sprite(currentX,currentY, character, this.getVertexBufferObjectManager());
		sprImage.setScale((float) .1);
		scene.attachChild(mySprite);
		scene.attachChild(sprImage);
		
		mHandler.post(mUpdateResults);
		
		mEffectSpawnDelay = 5;
		createFlyerSpawnTimeHandler();

        return scene;
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.isActionDown()) {
			
		}
		return false;
	}
	
	public void updateResultsInUi() {
		scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() { }

			@Override
			public void onUpdate(final float pSecondsElapsed) {		
				if (currentY <= CAMERA_HEIGHT) {
					sprImage.registerEntityModifier(new MoveModifier(0.8f,
							currentX,currentY, currentX, currentY + 8) {
						@Override
				    	protected void onModifierStarted(IEntity pItem) {
				        	super.onModifierStarted(pItem);
						}

				        @Override
				        protected void onModifierFinished(IEntity pItem) {
				        	currentX=sprImage.getX();
				        	currentY=sprImage.getY();
				            super.onModifierFinished(pItem);
				        }
					});
				} else {
					
				}
			}
		});
	}
	
	private void createSprite(float pX, float pY, ITextureRegion spriteTexture) {
        final Sprite sprite = new Sprite(pX, pY, spriteTexture, this.getVertexBufferObjectManager());
        sprite.setScale((float) .1);
        this.mEngine.getScene().attachChild(sprite);
        
        TimerHandler spriteTimerHandler;
        final int timeToKeepFlyer = MathUtils.random(MIN_TIME_DELAY_FOR_FLYER, MAX_TIME_DELAY_FOR_FLYER);
        this.getEngine().registerUpdateHandler(spriteTimerHandler = new TimerHandler(timeToKeepFlyer, new ITimerCallback()
        {
        	
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				removeSprite(sprite);
				
			} }));
	}
	
	private void removeSprite (Sprite sprite) {
		this.mEngine.getScene().detachChild(sprite);
	}
	
	private void createFlyerSpawnTimeHandler()
	{
	        TimerHandler spriteTimerHandler;
	       
	        this.getEngine().registerUpdateHandler(spriteTimerHandler = new TimerHandler(mEffectSpawnDelay, new ITimerCallback()
	        {                      
	            @Override
	            public void onTimePassed(final TimerHandler pTimerHandler)
	            {
	                float xPos, yPos = MathUtils.random(0, CAMERA_HEIGHT-64.0f);
	                final int imgPos = MathUtils.random(MAX_NUMBER_OF_FLYERS);
	                
	                if (imgPos < MAX_NUMBER_OF_FLYERS / 2) {
	                	// image is for the right side of the board
	                	xPos = CAMERA_WIDTH - 64.0f;
	                } else {
	                	// image is for the left side of the board
	                	xPos = 0;
	                }
	                       
	                createSprite(xPos, yPos, flyers[imgPos]);
	            }
	        }));
	}
}