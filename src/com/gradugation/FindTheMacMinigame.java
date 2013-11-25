package com.gradugation;

import java.io.IOException;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.math.MathUtils;
import org.andengine.util.adt.color.Color;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.coordinates.SpriteCoordinate;

public class FindTheMacMinigame extends SimpleBaseGameActivity implements IOnSceneTouchListener {
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	private static int DEVICE_WIDTH;
	private static int DEVICE_HEIGHT;
	private static final float PERCENTAGE_REQUIRED = .8f;
	private static final int CREDITS_EARNED = 3;
	private Camera mCamera;
	private float points, total;
	private CountDownTimer timer;
	private boolean gameFinished;
	private AlertDialog.Builder builder;
	private AlertDialog dialog;
	private HUD mHUD;
	private Sprite sprBg;
	private Sprite[] sprPc;
	private Sprite sprMac;
	private float[] xPos;
	private float[] yPos;
	private Font mFont;

	private BitmapTextureAtlas backgroundTextureAtlas, pcTextureAtlas, macTextureAtlas;
    private ITextureRegion backgroundRegion, pcRegion, macRegion;
    
	@Override
	public EngineOptions onCreateEngineOptions() {
	      
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(DEVICE_WIDTH, DEVICE_HEIGHT), mCamera);
	}

	@Override
	protected void onCreateResources() throws IOException {
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		// Background
		this.backgroundTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 960, 720, 
				TextureOptions.NEAREST_PREMULTIPLYALPHA);
		this.backgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas, this, 
				"computer_lab.png", 0, 0);
		this.backgroundTextureAtlas.load();
	
	}
	
	@Override
	protected Scene onCreateScene() {

		Scene scene = new Scene();
        
		scene.setBackground(new Background(Color.WHITE));
		//sprite for background - computerLab
        sprBg = new Sprite(0, 0, backgroundRegion,
                this.getVertexBufferObjectManager());

		scene.attachChild(sprBg);

		return scene;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		return false;
	}
	
	
}