package com.muyagdx.blast.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.muyagdx.blast.Blast;

	public class DesktopLauncher {
		   public static void main(String[] args) {
		      LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		      config.title = "Blast";
		      config.width = 800;
		      config.height = 480;
		      new LwjglApplication(new Blast(), config);
		   }
		}

