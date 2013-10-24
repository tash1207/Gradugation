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
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;
import org.andengine.util.math.MathUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class WhackAFlyerGame extends SimpleBaseGameActivity implements IOnSceneTouchListener {

    private static final int CAMERA_WIDTH = 480;
    private static final int CAMERA_HEIGHT = 320;
    private static final int MAX_NUMBER_OF_FLYERS = 4;
    private static final int MAX_TIME_DELAY_FOR_FLYER = 5;
    private static final int MIN_TIME_DELAY_FOR_FLYER = 2;
    private static final float MAX_SPAWN_DELAY = 4;
    private static final float MIN_SPAWN_DELAY = .5f;
    private static final int POINTS_REQUIRED = 8;
    private static final int CREDITS_EARNED = 3;
    
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
            gameFinished();
        }
    };
    
    final Runnable spawnerStart = new Runnable() {
    	public void run() {
    		createrSpawnerTimeHandler();
    	}
    };
    
    private BitmapTextureAtlas characterTextureAtlas;
    private ITextureRegion character;
    private BitmapTextureAtlas bgTextureAtlas;
    private ITextureRegion bgRegion;
    private Sprite sprImage;
    private float currentX;
    private float currentY;
    private float mEffectSpawnDelay = 2;
    private ITextureRegion[] flyers = new ITextureRegion[MAX_NUMBER_OF_FLYERS];
    private BitmapTextureAtlas[] flyerAtlas = new BitmapTextureAtlas[MAX_NUMBER_OF_FLYERS];
    private int points;
    private boolean finished = false;
    private boolean gameStarted = false;
    private Text pointsLabel;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.whack_aflyer, menu);
        return true;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera mCamera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
    
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
    }

    @Override
    protected void onCreateResources() throws IOException {
        this.points = 0;

        this.characterTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),
                512,512,TextureOptions.BILINEAR);
        this.character = BitmapTextureAtlasTextureRegionFactory.createFromAsset
                (characterTextureAtlas, this, "gfx/splash2.png", 0, 0);;
        this.characterTextureAtlas.load();
        
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/whack_aflyer_img/");
        this.bgTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),
                951,720,TextureOptions.NEAREST_PREMULTIPLYALPHA);
        this.bgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bgTextureAtlas,
                this, "bricks.png", 0, 0);
        this.bgTextureAtlas.load();
    
        for (int i = 0; i < MAX_NUMBER_OF_FLYERS; i++) {
            String imgName = "flyer_" + (i+1) + ".png";
            this.flyerAtlas[i] = new BitmapTextureAtlas(this.getTextureManager(),
                    1024,1024,TextureOptions.BILINEAR);
            this.flyers[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset
                    (flyerAtlas[i], this, imgName, 0, 0);
            this.flyerAtlas[i].load(); 
        }
    }
 

    @Override
    protected Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());
    
        Scene scene = new Scene();
        scene.setOnSceneTouchListener(this);
        
        final float centerX = 239;
        final float centerY = 0;
        currentX = centerX;
        currentY = centerY;
    
        scene.setTouchAreaBindingOnActionDownEnabled(true);
        scene.setTouchAreaBindingOnActionMoveEnabled(true);
        scene.setBackground(new Background(Color.WHITE));
       
        final Sprite mySprite = new Sprite(0, 0, bgRegion,
                this.getVertexBufferObjectManager());
        
        sprImage = new Sprite(currentX,currentY, character,
                this.getVertexBufferObjectManager());
        sprImage.setScale((float) .1);
        
        //Font mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
        //mFont.load();

        //this.pointsLabel = new Text(100, 40, mFont, "Points: ", new TextOptions(), this.getVertexBufferObjectManager());
        //new Text(100, 40, mFont, &quot;Hello AndEngine!nYou can even have multilined text!&quot;, new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());

        
        scene.attachChild(mySprite);
        scene.attachChild(sprImage);
        //scene.attachChild(pointsLabel);
    
        mHandler.post(mUpdateResults);
        mHandler.post(spawnerStart);
    
        
        
        return scene;
    }
    
    private void flyerWhacked(Sprite spriteWhacked) {
        this.mEngine.getScene().detachChild(spriteWhacked);
        this.points += 1;
        Log.d(DISPLAY_SERVICE, "IN HERE" + this.points);
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (pSceneTouchEvent.isActionDown()) {

        }
        return false;
    }
    
    public void updateResultsInUi() {
    	this.mEngine.getScene().registerUpdateHandler(new IUpdateHandler () {
            @Override
            public void reset() { }
    
            @Override
            public void onUpdate(final float pSecondsElapsed) {  
                    
                    if (!finished && gameStarted) {
                        if (currentY <= CAMERA_HEIGHT+32f) {
                            sprImage.registerEntityModifier(new MoveModifier(0.05f,
                                    currentX,currentY, currentX, currentY + 1) {
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
                            mHandler.post(finishGame);
                        }
                        //pointsLabel.setText("Points: " + points);
                    }
            }
        });
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.whack_aflyer_instructions, POINTS_REQUIRED));
        builder.setCancelable(false);
        builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
				gameStarted = true;
				
			}
        	
        });
        
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    
    private Sprite createSprite(float pX, float pY, ITextureRegion spriteTexture) {
        final Sprite sprite = new Sprite(pX, pY, spriteTexture,
                this.getVertexBufferObjectManager()) {
        	private boolean touched = false;
            @Override
            public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
                if (touchEvent.isActionUp() && !this.touched) {
                	this.touched = true;
                    flyerWhacked(this);
                }
                return false;
            }
            
        };
        this.mEngine.getScene().registerTouchArea(sprite);

        sprite.setScale((float) .1);
        this.mEngine.getScene().attachChild(sprite);
        
        return sprite;
    }
    
    private void removeSprite (Sprite sprite) {
        this.mEngine.getScene().detachChild(sprite);
    }
    
    private void createrSpawnerTimeHandler() {
    	this.mEngine.registerUpdateHandler(new TimerHandler(mEffectSpawnDelay, true,
    			new ITimerCallback() {
    		@Override
    		public void onTimePassed(TimerHandler pTimerHandler) {
    			mEffectSpawnDelay = MathUtils.random(MIN_SPAWN_DELAY, MAX_SPAWN_DELAY);
    			createFlyerSpawnTimeHandler();
    			pTimerHandler.setTimerSeconds(mEffectSpawnDelay);
    			pTimerHandler.setAutoReset(!finished);
    			
    		}
    	}));
    }
    private void createFlyerDestroyTimeHandler(Sprite sprite) {
        final Sprite spriteToRemove = sprite;
        final int timeToKeepFlyer = MathUtils.random(MIN_TIME_DELAY_FOR_FLYER,
                                                     MAX_TIME_DELAY_FOR_FLYER);
        this.getEngine().registerUpdateHandler(new TimerHandler(timeToKeepFlyer,
                new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                removeSprite(spriteToRemove);
                
            } 
        }));
    }

    private void createFlyerSpawnTimeHandler() {
        float xPos, yPos = MathUtils.random(0, CAMERA_HEIGHT-32.0f);
        yPos = 32.0f * (int)(yPos / 32);
        final int imgPos = MathUtils.random(MAX_NUMBER_OF_FLYERS);
            
        if (imgPos < MAX_NUMBER_OF_FLYERS / 2) {
            // image is for the right side of the board
            xPos = CAMERA_WIDTH - 32.0f;
        } else {
            // image is for the left side of the board
            xPos = 20;
        }
                   
        if (!finished && gameStarted) {
        	Sprite spriteCreated = createSprite(xPos, yPos, flyers[imgPos]);
        	createFlyerDestroyTimeHandler(spriteCreated);
        }
    }
    
    private void gameFinished() {
        finished = true;
        if (this.points >= POINTS_REQUIRED) {
            Toast.makeText(WhackAFlyerGame.this, getString(R.string.whack_aflyer_success, this.points, 
                    CREDITS_EARNED), Toast.LENGTH_LONG).show();
            // Code to add CREDITS_EARNED number of credits to the character
        }
        else {
            Toast.makeText(WhackAFlyerGame.this, getString(R.string.whack_aflyer_failure, this.points), 
                    Toast.LENGTH_LONG).show();
        }
        
        BitmapTextureAtlas continueButtonAtlas = new BitmapTextureAtlas(this.getTextureManager(),
                951,720,TextureOptions.NEAREST_PREMULTIPLYALPHA);
        ITextureRegion continueButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(continueButtonAtlas,
                this, "continue_button.png", 0, 0);
        continueButtonAtlas.load();
        
        final Sprite continueSprite = new Sprite(CAMERA_WIDTH/2, 32f, continueButton,
                this.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
                if (touchEvent.isActionDown()) {
                   finish();
                }
                return false;
            }
            
        };
        continueSprite.setScale((float) .25);
        this.mEngine.getScene().registerTouchArea(continueSprite);
        this.mEngine.getScene().attachChild(continueSprite);
    }
    
    @Override
    public void finish() {
    	super.finish();
    }
}

