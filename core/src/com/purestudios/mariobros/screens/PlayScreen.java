package com.purestudios.mariobros.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
  
  public PlayScreen(MarioBros game) {
    this.game = game;
    gameCam = new OrthographicCamera();
    
    //Create a FitViewport to maintain virtual aspect ratio despite screen resolution.
    gamePort = new FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, gameCam);
    
    //Create game HUD for scores/timers/level info.
    hud = new Hud(game.batch);
    
    //Load Map and Setup our map renderer.
    mapLoader = new TmxMapLoader();
    map = mapLoader.load("level1.tmx");
    renderer = new OrthogonalTiledMapRenderer(map);
    
    gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
    
    //World Stuff
    world = new World(new Vector2(0,0), true);
    b2dr = new Box2DDebugRenderer();
    
    BodyDef bdef = new BodyDef();
    PolygonShape shape = new PolygonShape();
    FixtureDef fdef = new FixtureDef();
    Body body;
    
    //Create Ground Bodies/Fixtures
    for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
      Rectangle rect = ((RectangleMapObject) object).getRectangle();
      
      bdef.type = BodyDef.BodyType.StaticBody;
      bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
      
      body = world.createBody(bdef);
      
      shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
      fdef.shape = shape;
      body.createFixture(fdef);
    }
    
    //Create Pipe Bodies/Fixtures
    for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
      Rectangle rect = ((RectangleMapObject) object).getRectangle();
      
      bdef.type = BodyDef.BodyType.StaticBody;
      bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
      
      body = world.createBody(bdef);
      
      shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
      fdef.shape = shape;
      body.createFixture(fdef);
    }
    
    //Create Bricks Bodies/Fixtures
    for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
      Rectangle rect = ((RectangleMapObject) object).getRectangle();
      
      bdef.type = BodyDef.BodyType.StaticBody;
      bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
      
      body = world.createBody(bdef);
      
      shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
      fdef.shape = shape;
      body.createFixture(fdef);
    }
    
    //Create Coins Bodies/Fixtures
    for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
      Rectangle rect = ((RectangleMapObject) object).getRectangle();
      
      bdef.type = BodyDef.BodyType.StaticBody;
      bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
      
      body = world.createBody(bdef);
      
      shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
      fdef.shape = shape;
      body.createFixture(fdef);
    }
  }
  
  public void update(float dt) {
    handleInput(dt);
    
    gameCam.update();
    renderer.setView(gameCam);
  }
  
  public void handleInput(float dt) {
    if (Gdx.input.isTouched()) {
      gameCam.position.x += 100 * dt;
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
    // TODO Auto-generated method stub
  }
}
