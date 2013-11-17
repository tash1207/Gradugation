package com.gradugation;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.graphics.Color;
import android.graphics.Typeface;

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

	private StrokeFont mCongratsFont, mScoreFont;

	// ===========================================================
	// Constructors
	// ===========================================================

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

		this.mScoreFont = new StrokeFont(this.getFontManager(),
				strokeFontTexture, Typeface.create(Typeface.DEFAULT,
						Typeface.BOLD), FONT_SIZE, true, Color.BLACK, 1,
				Color.WHITE);
		this.mScoreFont.load();

	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();
		scene.setBackground(new Background(0.9f, 0.9f, 0.9f));

		final VertexBufferObjectManager vertexBufferObjectManager = this
				.getVertexBufferObjectManager();
		final Text congratsText = new Text(CAMERA_WIDTH / 2, CAMERA_HEIGHT
				- (CAMERA_HEIGHT / 6), this.mCongratsFont,
				"Congratulations for Gradugating!", vertexBufferObjectManager);
		final Text winnerText = new Text(CAMERA_WIDTH / 2, CAMERA_HEIGHT
				- (CAMERA_HEIGHT / 4), this.mCongratsFont,
				"Our winner and esteemed valedictorian is [Player1]",
				vertexBufferObjectManager);

		final Text playerOneText = new Text(CAMERA_WIDTH / 2, CAMERA_HEIGHT
				- (CAMERA_HEIGHT / 2), this.mCongratsFont,
				"[Player1] : [Credits1]\n[Player2] : [Credits2]\n[Player3] : [Credits3]\n[Player4] : [Credits4]",
				vertexBufferObjectManager);

		scene.attachChild(congratsText);
		scene.attachChild(winnerText);
		scene.attachChild(playerOneText);

		return scene;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
