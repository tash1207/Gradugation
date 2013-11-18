package com.gradugation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.array.ArrayUtils;

import com.coordinates.SpriteCoordinate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

public class GameOverScreen extends SimpleBaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;

	private static final int FONT_SIZE = 32;

	// ===========================================================
	// Fields
	// ===========================================================

	private static StrokeFont mCongratsFont;
	private StrokeFont mScoreFont;
	private AssetBitmapTexture continueTexture;
	private ITextureRegion continueTextureRegion;
	private static String winner;
	private static int credits;
	private static String[] playerNames = { "Abby", "Bob", "Charlie", "Donna" };
	private static int[] playerCredits = { 3, 7, 2, 5 };
	
	// ===========================================================
	// Constructors
	// ===========================================================

	private Scene scene;

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	@Override
	public void onCreateResources() {
		final ITexture strokeFontTexture = new BitmapTextureAtlas(
				this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);

		this.mCongratsFont = new StrokeFont(this.getFontManager(),
				strokeFontTexture, Typeface.create(Typeface.DEFAULT,
						Typeface.BOLD), FONT_SIZE, true, Color.BLACK, 1,
				Color.WHITE);
		this.mCongratsFont.load();

		try {
			this.continueTexture = new AssetBitmapTexture(
					this.getTextureManager(), this.getAssets(),
					"gfx/whack_aflyer_img/continue_button.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.continueTextureRegion = TextureRegionFactory
				.extractFromTexture(this.continueTexture);

		this.continueTexture.load();

	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		scene = new Scene();
		scene.setBackground(new Background(0.9f, 0.9f, 0.9f));

		final VertexBufferObjectManager vertexBufferObjectManager = this
				.getVertexBufferObjectManager();

		// Output
		final Text congratsText = new Text(CAMERA_WIDTH / 2, CAMERA_HEIGHT
				- (CAMERA_HEIGHT / 6), this.mCongratsFont,
				"Congratulations for Gradugating!", vertexBufferObjectManager);
		scene.attachChild(congratsText);
		displayWinner();
		
		//bubbleSort(playerNames, playerCredits);
		
		if (playerNames.length >= 1) {
			final Text playerOneText = new Text(CAMERA_WIDTH / 2, CAMERA_HEIGHT
					- (CAMERA_HEIGHT / 2), this.mCongratsFont,
					playerNames[0] + " : " + playerCredits[0], vertexBufferObjectManager);
			scene.attachChild(playerOneText);
		} 
		
		if (playerNames.length >= 2) {
			final Text playerTwoText = new Text(CAMERA_WIDTH / 2, CAMERA_HEIGHT
				- (CAMERA_HEIGHT / 2), this.mCongratsFont,
				"\n\n" + playerNames[1] + " : " + playerCredits[1], vertexBufferObjectManager);
			scene.attachChild(playerTwoText);
		} 

		if (playerNames.length >= 3) {
			final Text playerThreeText = new Text(CAMERA_WIDTH / 2, CAMERA_HEIGHT
				- (CAMERA_HEIGHT / 2), this.mCongratsFont,
				"\n\n\n\n" + playerNames[2] + " : " + playerCredits[2], vertexBufferObjectManager);
			scene.attachChild(playerThreeText);
		} 
		
		if (playerNames.length >= 4) {
			final Text playerFourText = new Text(CAMERA_WIDTH / 2, CAMERA_HEIGHT
				- (CAMERA_HEIGHT / 2), this.mCongratsFont,
				"\n\n\n\n\n\n" + playerNames[3] + " : " + playerCredits[3], vertexBufferObjectManager);
			scene.attachChild(playerFourText);
		} 

		final Sprite continueButton = new Sprite(CAMERA_WIDTH / 2,
				CAMERA_HEIGHT / 2 - (CAMERA_HEIGHT / 2), continueTextureRegion,
				this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mainMenuClick(mRenderSurfaceView);
					break;
				case TouchEvent.ACTION_UP:
					break;
				case TouchEvent.ACTION_MOVE:
					break;
				}
				return true;
			}

		};
		scene.attachChild(continueButton);
		scene.registerTouchArea(continueButton);

		return scene;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void mainMenuClick(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	public void displayWinner() {
		final VertexBufferObjectManager vertexBufferObjectManager = this
				.getVertexBufferObjectManager();
		final Text winnerText = new Text(CAMERA_WIDTH / 2, CAMERA_HEIGHT
				- (CAMERA_HEIGHT / 4), this.mCongratsFont, "Our winner is "
				+ winner + "  with " + credits + " credits!",
				vertexBufferObjectManager);
		scene.attachChild(winnerText);
	}
	
	public void displayScore() {
		
	}

	public static void setWinner(String s, int c) {
		winner = s;
		credits = c;
	}

	public static void setPlayers(String[] names, int[] credits) {
		playerNames = names;
		playerCredits = credits;
	}
	
	public void bubbleSort(String[] playerNames, int[] playerCredits) {
		int length = playerCredits.length;
		int temp1 = 0;
		String temp2 = null;
		for (int i = 0; i < length; i++) {
			for (int j = (length - 1); j >= (i+1); j--) {
				if (playerCredits[i] < playerCredits[i-1]) {
					temp1 = playerCredits[j];
					playerCredits[j] = playerCredits[j-1];
					playerCredits[j-1] = temp1;
				
				}
			}
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
