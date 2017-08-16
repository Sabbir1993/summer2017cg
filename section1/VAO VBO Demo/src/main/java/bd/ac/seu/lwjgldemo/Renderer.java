/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.ac.seu.lwjgldemo;

import java.nio.DoubleBuffer;
import java.time.LocalTime;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author kmhasan
 */
public class Renderer {

    private long window;
    private float angle;
    private float currentMilliSeconds;
    private float previousMilliSeconds;
    private static final int NUM_VERTICES = 1000000;
    private double vertices[];
    private int vaoId;
    private int vboId;

    public Renderer() {
        angle = 0;
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

        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            switch (key) {
                case GLFW.GLFW_KEY_ESCAPE:
                    GLFW.glfwSetWindowShouldClose(window, true);
                    break;
            }
        });

        GLFW.glfwMakeContextCurrent(window);

        setupModel();
    }

    private void setupModel() {
        GL.createCapabilities();

        vertices = new double[NUM_VERTICES * 3];
        for (int row = 0; row < NUM_VERTICES; row++) {
            vertices[row * 3 + 0] = Math.random(); // [0, 1)
            vertices[row * 3 + 1] = Math.random();
            vertices[row * 3 + 2] = 0;
        }

        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(NUM_VERTICES * 3);
        buffer.put(vertices);
        buffer.flip();

        // VAO - Vertex Array Object
        // VBO - Vertex Buffer Object
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_DOUBLE, false, 0, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    private void loop() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            currentMilliSeconds = System.nanoTime() / 1000000.0f;
            float difference = currentMilliSeconds - previousMilliSeconds;
            double fps = 1000.0 / difference;
            System.out.printf("FPS: %.3f\n", fps);

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            drawSomething();
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
            previousMilliSeconds = currentMilliSeconds;
        }
    }

    private void drawSomething() {
        GL11.glPushMatrix();
        GL11.glRotatef(angle, 0, 0, 1);
        
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);

        // Draw the vertices
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, NUM_VERTICES);

        // Put everything back to default (deselect)
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        
//        GL11.glBegin(GL11.GL_TRIANGLES);
//        for (int i = 0; i < NUM_VERTICES * 3; i = i + 3) {
//            double x = vertices[i + 0];
//            double y = vertices[i + 1];
//            double z = vertices[i + 2];
//            GL11.glVertex3d(x, y, z);
//        }
//        GL11.glEnd();
        GL11.glPopMatrix();
        angle = angle + 1f;
    }
}
