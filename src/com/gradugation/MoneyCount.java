package com.gradugation; 
 
import java.util.Random; 
 
import org.andengine.engine.camera.Camera; 
import org.andengine.engine.options.EngineOptions; 
import org.andengine.engine.options.ScreenOrientation; 
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy; 
import org.andengine.entity.scene.Scene; 
import org.andengine.entity.scene.background.Background; 
import org.andengine.entity.sprite.AnimatedSprite; 
import org.andengine.entity.util.FPSLogger; 
import org.andengine.extension.physics.box2d.PhysicsConnector; 
import org.andengine.extension.physics.box2d.PhysicsFactory; 
import org.andengine.extension.physics.box2d.PhysicsWorld; 
import org.andengine.opengl.texture.TextureOptions; 
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas; 
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory; 
import org.andengine.opengl.texture.region.ITiledTextureRegion; 
import org.andengine.ui.activity.SimpleBaseGameActivity; 
import android.hardware.SensorManager; 
 
import com.badlogic.gdx.math.Vector2; 
import com.badlogic.gdx.physics.box2d.Body; 
import com.badlogic.gdx.physics.box2d.FixtureDef; 
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType; 
 
public class MoneyCount extends SimpleBaseGameActivity { 
 
private static final int CAMERA_WIDTH = 720; 
private static final int CAMERA_HEIGHT = 480; 
 
private BitmapTextureAtlas mBitmapTextureAtlas; 
private ITiledTextureRegion mBoxFaceTextureRegion; 
private Scene scene; 
 
protected PhysicsWorld mPhysicsWorld; 
 
@Override 
public EngineOptions onCreateEngineOptions() { 
final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT); 
return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera); 
} 
 
@Override 
protected void onCreateResources() { 
BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/"); 
 
this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR); 
this.mBoxFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "5dollar.png", 0, 0, 2, 1); 
this.mBitmapTextureAtlas.load(); 
} 
 
@Override 
protected Scene onCreateScene() { 
this.mEngine.registerUpdateHandler(new FPSLogger()); 
 
scene = new Scene(); 
scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f)); 
 
this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false); 
this.scene.registerUpdateHandler(this.mPhysicsWorld); 
 
fallingObjects(); 
return scene; 
} 
 
private void fallingObjects(){ 
 
Random rand = new Random(); 
Body body; 
final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f); 
 
for(int i=0; i<21; i++){ 
int xP = rand.nextInt((int)(CAMERA_WIDTH - mBoxFaceTextureRegion.getWidth(1))); 
final AnimatedSprite box = new AnimatedSprite(xP, -13, this.mBoxFaceTextureRegion, this.getVertexBufferObjectManager()); 
body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, box, BodyType.DynamicBody, objectFixtureDef);	 
box.animate(200); 
this.scene.attachChild(box); 
this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(box, body, true, true)); 
} 
} 
}