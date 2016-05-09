package com.purestudios.mariobros.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.purestudios.mariobros.MarioBros;

public class PlayScreen implements Screen {
  
  private MarioBros game;
  private Texture texture;
  private OrthographicCamera gameCam;
  private Viewport gamePort;
  
  public PlayScreen(MarioBros game) {
    this.game = game;
    this.texture = new Texture("badlogic.jpg");
    gameCam = new OrthographicCamera();
    gamePort = new StretchViewport(800, 480, gameCam);
  }
  
  @Override
  public void show() {
    // TODO Auto-generated method stub
  }
  
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(1, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
    game.batch.setProjectionMatrix(gameCam.combined);
    
    game.batch.begin();
    game.batch.draw(texture, 0, 0);
    game.batch.end();
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
