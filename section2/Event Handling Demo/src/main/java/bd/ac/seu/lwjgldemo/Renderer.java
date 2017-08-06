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
    private float speed = 1f;

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

        window = GLFW.glfwCreateWindow(800, 800, "Event Handling Demo", 0, 0);

        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) {
                GLFW.glfwSetWindowShouldClose(window, true);
            } else if (key == GLFW.GLFW_KEY_UP && action == GLFW.GLFW_PRESS) {
                speed = speed + 0.1f;
            } else if (key == GLFW.GLFW_KEY_DOWN && action == GLFW.GLFW_PRESS) {
                speed = speed - 0.1f;
            }
        });

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwShowWindow(window);
    }

    private void drawCircle(float radius, int sides) {
        double theta = (2 * Math.PI) / sides;
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        for (int i = 0; i < sides; i++) {
            float x = (float) (radius * Math.cos(theta * i));
            float y = (float) (radius * Math.sin(theta * i));
            float z = 0;
            GL11.glVertex3f(0, 0, 0);
            GL11.glVertex3f(x, y, z);
            x = (float) (radius * Math.cos(theta * (i + 1)));
            y = (float) (radius * Math.sin(theta * (i + 1)));
            GL11.glVertex3f(x, y, z);
        }
        GL11.glEnd();
    }

    private void drawOrbit(float radius, int sides) {
        double theta = (2 * Math.PI) / sides;
        GL11.glBegin(GL11.GL_LINES);
        for (int i = 0; i < sides; i = i + 2) {
            float x = (float) (radius * Math.cos(theta * i));
            float y = (float) (radius * Math.sin(theta * i));
            float z = 0;
            GL11.glVertex3f(x, y, z);
            x = (float) (radius * Math.cos(theta * (i + 1)));
            y = (float) (radius * Math.sin(theta * (i + 1)));
            GL11.glVertex3f(x, y, z);
        }
        GL11.glEnd();
    }

    private void drawSolarSystem() {
        GL11.glClearColor(0, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        GL11.glPushMatrix();

        // draw orbits
        GL11.glPushMatrix();
        GL11.glColor3f(1, 1, 1);
        GL11.glScalef(0.8f, 0.8f, 0.8f);
        drawOrbit(1, 100);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawOrbit(1, 50);
        GL11.glPopMatrix();

        // sun
        GL11.glPushMatrix();
        GL11.glScalef(0.25f, 0.25f, 0.25f);
        GL11.glColor3f(1, 1, 0);
        drawCircle(1, 20);
        GL11.glPopMatrix();

        // mars
        GL11.glPushMatrix();
        GL11.glRotatef(angle / 2, 0, 0, 1);
        GL11.glTranslatef(0.4f, 0, 0);
        GL11.glScalef(0.10f, 0.10f, 0.10f);
        GL11.glRotatef(angle, 0, 0, 1);
        GL11.glColor3f(1, 0, 0);
        drawCircle(1, 20);
        GL11.glPopMatrix();

        // earth
        GL11.glPushMatrix();
        GL11.glRotatef(angle, 0, 0, 1);
        GL11.glTranslatef(0.8f, 0, 0);
        GL11.glScalef(0.10f, 0.10f, 0.10f);
        GL11.glRotatef(angle, 0, 0, 1);
        GL11.glColor3f(0, 0, 1);
        drawCircle(1, 20);
        // moon
        GL11.glRotatef(angle * 3, 0, 0, 1);
        GL11.glTranslatef(2, 0, 0);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor3f(0.8f, 0.8f, 0.8f);
        drawCircle(1, 20);
        GL11.glPopMatrix();

        GL11.glEnd();
        GL11.glPopMatrix();

        angle = angle + speed;
    }

    private void loop() {
        GL.createCapabilities();

        while (!GLFW.glfwWindowShouldClose(window)) {
            drawSolarSystem();

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }
}
