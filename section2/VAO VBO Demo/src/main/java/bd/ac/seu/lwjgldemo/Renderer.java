/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.ac.seu.lwjgldemo;

import java.time.LocalTime;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author kmhasan
 */
public class Renderer {

    private long window;
    private float angle = 0;
    private int frameCounter = 0;
    private long lastTime = 0;
    private double vertices[][];
    
    public Renderer() {
    }
    
    public void run() {
        init();
        loop();
    }
    
    private void init() {
        System.out.println("LWJGL version " + Version.getVersion());
        
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Could not initialize GLFW");
        
        window = GLFW.glfwCreateWindow(800, 800, "VAO VBO Demo", 0, 0);
        GLFW.glfwMakeContextCurrent(window);
        
        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
           if (key == GLFW.GLFW_KEY_ESCAPE)
               GLFW.glfwSetWindowShouldClose(window, true);
        });

        vertices = new double[10000][3];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i][0] = Math.random();
            vertices[i][1] = Math.random();
            vertices[i][2] = 0;
        }
        
        GLFW.glfwShowWindow(window);
    }
    
    private void drawSomething() {
        GL11.glClearColor(0, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        GL11.glColor3f(1, 1, 0);
        
        GL11.glPushMatrix();
        GL11.glRotatef(angle, 0, 0, 1);
        GL11.glBegin(GL11.GL_QUADS);
            for (int i = 0; i < vertices.length; i++)
                GL11.glVertex3d(vertices[i][0], vertices[i][1], vertices[i][2]);
        GL11.glEnd();
        GL11.glPopMatrix();
        
        angle = angle + .10f ;
        frameCounter++;
        long currentTime = System.nanoTime();
        long timeDifference = currentTime - lastTime;
        double fps = 1000000000.0 / timeDifference;
        System.out.printf("FPS: %.3f\n", fps);
        lastTime = currentTime;
    }
    
    private void loop() {
        GL.createCapabilities();
        
        while (!GLFW.glfwWindowShouldClose(window)) {
            drawSomething();
            
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }
}
