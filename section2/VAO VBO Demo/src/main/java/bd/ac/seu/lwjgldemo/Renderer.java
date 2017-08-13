/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.ac.seu.lwjgldemo;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.time.LocalTime;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author kmhasan
 */
public class Renderer {

    private long window;
    private float angle = 0;
    private int frameCounter = 0;
    private long lastTime = 0;
    private double vertices[];
    private final static int NUM_VERTICES = 1000000;

    private int vaoId;
    private int vboId;
    
    public Renderer() {
    }

    public void run() {
        init();
        loop();
    }

    private void init() {
        System.out.println("LWJGL version " + Version.getVersion());

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Could not initialize GLFW");
        }

        window = GLFW.glfwCreateWindow(800, 800, "VAO VBO Demo", 0, 0);
        GLFW.glfwMakeContextCurrent(window);

        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE) {
                GLFW.glfwSetWindowShouldClose(window, true);
            }
        });

        GL.createCapabilities();
        
        setupModel();
        
        GLFW.glfwShowWindow(window);
    }

    private void setupModel() {
        int COLUMNS_PER_ROW = 3;
        vertices = new double[NUM_VERTICES * COLUMNS_PER_ROW];
        for (int row = 0; row < vertices.length / COLUMNS_PER_ROW; row++) {
            vertices[row * COLUMNS_PER_ROW + 0] = Math.random();
            vertices[row * COLUMNS_PER_ROW + 1] = Math.random();
            vertices[row * COLUMNS_PER_ROW + 2] = 0;
        }
        
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(vertices.length);
        buffer.put(vertices);
        buffer.flip();
        
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);
        
        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_DOUBLE, false, 0, 0);
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    private void drawSomething() {
        GL11.glClearColor(0, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        GL11.glColor3f(1, 1, 0);

        GL11.glPushMatrix();
        GL11.glRotatef(angle, 0, 0, 1);
        /*
        GL11.glBegin(GL11.GL_QUADS);
        for (int row = 0; row < vertices.length; row = row + 3) {
            double x = vertices[row];
            double y = vertices[row + 1];
            double z = vertices[row + 2];
            GL11.glVertex3d(x, y, z);
        }
        GL11.glEnd();
        */
        
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);

        // Draw the vertices
        GL11.glDrawArrays(GL11.GL_QUADS, 0, vertices.length / 3);

        // Put everything back to default (deselect)
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        
        GL11.glPopMatrix();

        angle = angle + .10f;
        frameCounter++;
        long currentTime = System.nanoTime();
        long timeDifference = currentTime - lastTime;
        double fps = 1000000000.0 / timeDifference;
        System.out.printf("FPS: %.3f\n", fps);
        lastTime = currentTime;
    }

    private void loop() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            drawSomething();

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }
}
