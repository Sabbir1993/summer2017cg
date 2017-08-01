/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.ac.seu.lwjgldemo;

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
        
        window = GLFW.glfwCreateWindow(800, 800, "LWJGL Demo", 0, 0);
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwShowWindow(window);
    }
    
    private void drawSomething() {
        GL11.glClearColor(0, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        GL11.glColor3f(1, 1, 0);
        
        GL11.glPushMatrix();
        GL11.glRotatef(angle, 0, 0, 1);
        GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex3f(-0.5f, -0.5f, 0);
            GL11.glVertex3f(+0.5f, -0.5f, 0);
            GL11.glVertex3f(+0.5f, +0.5f, 0);
            GL11.glVertex3f(-0.5f, +0.5f, 0);
        GL11.glEnd();
        GL11.glPopMatrix();
        angle++;
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
