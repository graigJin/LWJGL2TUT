package engineTester;

import entities.Camera;
import entities.Entity;
import renderEngine.DisplayManager;
import org.lwjgl.opengl.Display;
import renderEngine.Loader;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {
    
    public static void main(String[] args) {
        
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);
                
        RawModel model = OBJLoader.loadObjModel("stall", loader);
        
        ModelTexture texture = new ModelTexture(loader.loadTexture("stall"));
        
        TexturedModel texturedModel = new TexturedModel(model, texture);
        
        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-10), 0, 0, 0, 1);
        
        Camera camera = new Camera();
            
        while(!Display.isCloseRequested()) {
            entity.increaseRotation(0, 1, 0);
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }
        
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
        
    }

}
