package com.gradugation;

import java.io.IOException;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import android.view.Menu;

public class WhackAFlyerGame extends SimpleBaseGameActivity {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	
	private ITexture bgTexture;
	private ITexture mFaceTexture;
	private BitmapTextureAtlas characterTextureAtlas;
    public ITextureRegion character;
    public ITextureRegion bgTextureRegion;
    private BitmapTextureAtlas mParallaxBackAtlas;
    private ITextureRegion mParallaxBackRegion;
    private Sprite sprImage;
    private float currentX;
    private float currentY;
    private RepeatingSpriteBackground mRepeatingBackground;

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
		
		this.mParallaxBackAtlas = new BitmapTextureAtlas(this.getTextureManager(), 951,720,TextureOptions.NEAREST_PREMULTIPLYALPHA);
		this.mParallaxBackRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mParallaxBackAtlas, this, "bricks.png", 0, 0);;
		this.mParallaxBackAtlas.load();
		
	}
	
	/*protected void onLoadResources() throws IOException {
		this.mParallaxBack = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "drawable/bricks.png", TextureOptions.BILINEAR);
		this.mParallaxBackRegion = TextureRegionFactory.extractFromTexture(this.mParallaxBack);
		this.mParallaxBack.load();
	}*/

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		final float centerX = 239;
		final float centerY = 0;
		currentX = centerX;
		currentY = centerY;

		final Scene scene = new Scene();
		
		scene.setBackground(new Background(Color.WHITE));
		   
		  sprImage = new Sprite(0, 0, mParallaxBackRegion, this.getVertexBufferObjectManager());
		  scene.attachChild(sprImage);
		  
		  final Sprite mySprite = new Sprite(currentX,currentY, character, this.getVertexBufferObjectManager());
			mySprite.setScale((float) .1);
			scene.attachChild(mySprite);
			
			scene.registerUpdateHandler(new IUpdateHandler() {
				@Override
				public void reset() { }

				@Override
				public void onUpdate(final float pSecondsElapsed) {
					
					// need boundries here
					mySprite.registerEntityModifier(new MoveModifier(0.8f,currentX,currentY, currentX, currentY + 8)
							{
								@Override
						        protected void onModifierStarted(IEntity pItem)
						        {
						                super.onModifierStarted(pItem);

								}

						        @Override
						        protected void onModifierFinished(IEntity pItem)
						        {
						        		currentX=mySprite.getX();
						        		currentY=mySprite.getY();
						                super.onModifierFinished(pItem);

						        }
							});
						}
						
			});

        return scene;
	}
}
