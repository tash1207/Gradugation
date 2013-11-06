package com.gradugation;

import java.io.IOException;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.badlogic.gdx.math.Vector2;


import android.hardware.SensorManager;
import android.view.Menu;

public class BalanceMinigame extends SimpleBaseGameActivity {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;

	private Scene scene;
	protected PhysicsWorld mPhysicsWorld;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.balance_minigame, menu);
		return true;
	}

	@Override
	public EngineOptions onCreateEngineOptions() {

		final Camera mCamera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
				mCamera);
	}

	@Override
	protected void onCreateResources() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	protected Scene onCreateScene() {
		scene = new Scene();
		scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f)); 
		this.mEngine.registerUpdateHandler(new FPSLogger()); 
		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false); 
		this.scene.registerUpdateHandler(this.mPhysicsWorld); 
		
		return scene;
	}
}
