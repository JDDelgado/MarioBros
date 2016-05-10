package com.purestudios.mariobros;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.purestudios.mariobros.screens.PlayScreen;

public class MarioBros extends Game {
  public SpriteBatch batch;
  public static final int V_WIDTH = 400;
  public static final int V_HEIGHT = 208;
  public static final float PPM = 100;
  
  @Override
  public void create() {
    batch = new SpriteBatch();
    setScreen(new PlayScreen(this));
  }
  
  @Override
  public void render() {
    super.render(); //Delegate Render to the PlayScreen (or current screen)
  }
}
