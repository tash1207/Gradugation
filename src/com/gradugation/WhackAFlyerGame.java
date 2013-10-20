package com.gradugation;

import java.io.IOException;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import android.view.Menu;

public class WhackAFlyerGame extends SimpleBaseGameActivity {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	
	private ITexture mFaceTexture;
	private BitmapTextureAtlas characterTextureAtlas;
    public ITextureRegion character;
    private AssetBitmapTexture mParallaxBack;
    private TextureRegion mParallaxBackRegion;
    private Sprite sprImage;

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
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.characterTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1024,1024,TextureOptions.BILINEAR);
		this.character = BitmapTextureAtlasTextureRegionFactory.createFromAsset(characterTextureAtlas, this, "bricks.png", 0, 0);;
		this.characterTextureAtlas.load();
	}
	
	/*protected void onLoadResources() throws IOException {
		this.mParallaxBack = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "drawable/bricks.png", TextureOptions.BILINEAR);
		this.mParallaxBackRegion = TextureRegionFactory.extractFromTexture(this.mParallaxBack);
		this.mParallaxBack.load();
	}*/

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene();
		
		scene.setBackground(new Background(Color.WHITE));
		   
		  sprImage = new Sprite(0, 0, character, this.getVertexBufferObjectManager());
		  scene.attachChild(sprImage);
        return scene;
	}

}
