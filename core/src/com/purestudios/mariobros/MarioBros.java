package com.purestudios.mariobros;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.purestudios.mariobros.screens.PlayScreen;

public class MarioBros extends Game {
  public SpriteBatch batch;
  
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
