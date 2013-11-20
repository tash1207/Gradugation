package com.gradugation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
	static ArrayList<Character> players;
	
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
		Intent intent = getIntent();
		players = (ArrayList<Character>)intent.getSerializableExtra("thePlayers");
		displayWinner(players.get(0).getName(), players.get(0).getCredits(), players.get(0).getCoins());
		int playerSize = players.size();
		Collections.sort(players, new MyComparator());
		
		
		ArrayList<Text> playerText = new ArrayList<Text>();
		String escapeSequence = "\n";
		
		for (int i = 0; i < playerSize; i++) {
			if (playerSize >= i + 1) {
				escapeSequence += "\n\n\n\n\n\n\n\n\n".substring(0, i*2);
				playerText.add(new Text(CAMERA_WIDTH / 2, CAMERA_HEIGHT
						- (CAMERA_HEIGHT / 3), this.mCongratsFont,
						escapeSequence + players.get(i).getName()+ " : " + players.get(i).getCredits() + " credits and " + players.get(i).getCoins() + " coins", vertexBufferObjectManager));
				scene.attachChild(playerText.get(i));
			
			}
		}
		
		final Sprite continueButton = new Sprite(CAMERA_WIDTH / 2,
				CAMERA_HEIGHT / 2 - (CAMERA_HEIGHT / 3), continueTextureRegion,
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

	public void displayWinner(String name, int credits, int coins) {
		final VertexBufferObjectManager vertexBufferObjectManager = this
				.getVertexBufferObjectManager();
		final Text winnerText = new Text(CAMERA_WIDTH / 2, CAMERA_HEIGHT
				- (CAMERA_HEIGHT / 4), this.mCongratsFont, "Our winner is "
				+ name + " with " + credits + " credits and " + coins + " coins!",
				vertexBufferObjectManager);
		scene.attachChild(winnerText);
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
	class MyComparator implements Comparator<Character> {
		@Override
		public int compare(Character c1, Character c2) {
			if (c1.getCredits() > c2.getCredits()) {
				return -1;
			} else if (c1.getCredits() < c2.getCredits()) {
				return 1;
			} else if (c1.getCredits() == c2.getCredits()) {
				if (c1.getCoins() > c2.getCoins()) {
					return -1;
				} else if (c1.getCoins() < c1.getCoins()) {
					return 1;
				}
			}
			return 0;
		}
	}
}
