package com.purestudios.mariobros.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.purestudios.mariobros.MarioBros;
import com.purestudios.mariobros.scenes.Hud;
import com.purestudios.mariobros.sprites.Mario;
import com.purestudios.mariobros.tools.B2WorldCreator;

public class PlayScreen implements Screen {
  
  private MarioBros game;
  private OrthographicCamera gameCam;
  private Viewport gamePort;
  private Hud hud;
  
  //Tiled Map Variables
  private TmxMapLoader mapLoader;
  private TiledMap map;
  private OrthogonalTiledMapRenderer renderer;
  
  //Box2d variables
  private World world;
  private Box2DDebugRenderer b2dr; //Graphical representation of bodies.
  
  //Mario
  private Mario player;
  private TextureAtlas atlas;
  
  public PlayScreen(MarioBros game) {
    this.game = game;
    
    atlas = new TextureAtlas("Mario_and_Enemies.pack");
    gameCam = new OrthographicCamera();
    
    //Create a FitViewport to maintain virtual aspect ratio despite screen resolution.
    gamePort = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM, MarioBros.V_HEIGHT / MarioBros.PPM, gameCam);
    
    //Create game HUD for scores/timers/level info.
    hud = new Hud(game.batch);
    
    //Load Map and Setup our map renderer.
    mapLoader = new TmxMapLoader();
    map = mapLoader.load("level1.tmx");
    renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);
    
    gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
    
    //World Stuff
    world = new World(new Vector2(0,-10), true);
    b2dr = new Box2DDebugRenderer();
    
    new B2WorldCreator(world, map);
    
    player = new Mario(world, this);
  }
  
  public void update(float dt) {
    handleInput(dt);
    
    world.step(1/60f, 6, 2);
     
    player.update(dt);
    
    gameCam.position.x = player.b2body.getPosition().x;
    
    gameCam.update();
    renderer.setView(gameCam);
  }
  
  public TextureAtlas getAtlas() {
    return atlas;
  }
  
  public void handleInput(float dt) {
    if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
      player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
    }
    
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
      player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
    }
    
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
      player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }
  }
  
  @Override
  public void show() {
    // TODO Auto-generated method stub
  }
  
  @Override
  public void render(float delta) {
    update(delta);
    
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
    renderer.render();
    
    b2dr.render(world, gameCam.combined);
    
    game.batch.setProjectionMatrix(gameCam.combined);
    game.batch.begin();
    player.draw(game.batch);
    game.batch.end();
    
    game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
    hud.stage.draw();
  }
  
  @Override
  public void resize(int width, int height) {
    gamePort.update(width, height);
  }
  
  @Override
  public void pause() {
    // TODO Auto-generated method stub
  }
  
  @Override
  public void resume() {
    // TODO Auto-generated method stub
  }
  
  @Override
  public void hide() {
    // TODO Auto-generated method stub
  }
  
  @Override
  public void dispose() {
    map.dispose();
    renderer.dispose();
    world.dispose();
    b2dr.dispose();
    hud.dispose();
  }
}
