/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.ac.seu.lwjgldemo;

import java.nio.FloatBuffer;
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
    private int vaoId;
    private int vboId;
    private int vertexCount;

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
        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            switch (key) {
                case GLFW.GLFW_KEY_ESCAPE:
                    GLFW.glfwSetWindowShouldClose(window, true);
                    break;
            }
        });


        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        setupModel();
        GLFW.glfwShowWindow(window);
    }

    private void setupModel() {
        float vertices[] = {
            -0.50f, -0.50f, 0,
            +0.50f, -0.50f, 0,
            +0.25f, +0.50f, 0,
            -0.25f, +0.50f, 0
        };

        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();

        vertexCount = vertices.length / 3;

        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    private void drawSomething() {
        GL11.glClearColor(0, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        GL11.glColor3f(1, 1, 0);

        GL11.glPushMatrix();
        GL11.glRotatef(angle, 0, 0, 1);
//        GL11.glBegin(GL11.GL_QUADS);
//        GL11.glVertex3f(-0.5f, -0.5f, 0);
//        GL11.glVertex3f(+0.5f, -0.5f, 0);
//        GL11.glVertex3f(+0.25f, +0.5f, 0);
//        GL11.glVertex3f(-0.25f, +0.5f, 0);
//        GL11.glEnd();
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);

        // Draw the vertices
        GL11.glDrawArrays(GL11.GL_LINE_LOOP, 0, vertexCount);

        // Put everything back to default (deselect)
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);

        GL11.glPopMatrix();
        angle++;
    }

    private void loop() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            drawSomething();

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }
}
