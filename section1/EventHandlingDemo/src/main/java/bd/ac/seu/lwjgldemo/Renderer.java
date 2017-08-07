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
    private float angle;
    private float speed;

    public Renderer() {
        angle = 0;
        speed = 1;
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
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                GLFW.glfwSetWindowShouldClose(window, true);
            } else if (key == GLFW.GLFW_KEY_UP && action != GLFW.GLFW_RELEASE) {
                speed = speed + 0.1f;
            } else if (key == GLFW.GLFW_KEY_DOWN && action != GLFW.GLFW_RELEASE) {
                speed = speed - 0.1f;
            }
        });

        GLFW.glfwMakeContextCurrent(window);
    }

    private void loop() {
        GL.createCapabilities();

        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            drawClock();
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }

    private void drawClockHand() {
        LocalTime currentTime = LocalTime.now();
        angle = currentTime.getSecond() * 6;
        GL11.glPushMatrix();
        GL11.glColor3f(1, 1, 1);
        GL11.glRotatef(angle, 0, 0, 1);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3f(-0.0f, 0, 0);
        GL11.glVertex3f(+0.4f, 0, 0);

        GL11.glVertex3f(0.4f, -0.1f, 0);
        GL11.glVertex3f(0.4f, +0.1f, 0);

        GL11.glVertex3f(0.4f, -0.1f, 0);
        GL11.glVertex3f(+0.5f, 0, 0);

        GL11.glVertex3f(0.4f, +0.1f, 0);
        GL11.glVertex3f(+0.5f, 0, 0);
        GL11.glEnd();
        GL11.glPopMatrix();
        //angle = angle + speed;
    }
    
    private void drawClock() {
        drawDial(0.5, 120);
        drawDial(0.6, 120);
        drawDial(0.1, 120);
        drawHourMarks();
        drawMinuteMarks();
        drawClockHand();
    }

    private void drawDial(double radius, int sides) {
        double theta = 2 * Math.PI / sides;
        double x;
        double y;
        double z = -0.1;
        GL11.glPushMatrix();
        GL11.glColor3f(1, 0, 0);
        GL11.glBegin(GL11.GL_LINES);
        for (int i = 0; i < sides; i++) {
            //GL11.glVertex3d(0, 0, 0);
            x = radius * Math.cos(theta * i);
            y = radius * Math.sin(theta * i);
            GL11.glVertex3d(x, y, z);
            x = radius * Math.cos(theta * (i + 1));
            y = radius * Math.sin(theta * (i + 1));
            GL11.glVertex3d(x, y, z);
        }
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    private void drawHourMarks() {
        int sides = 12;
        double theta = 2 * Math.PI / sides;
        double x;
        double y;
        double z = -0.1;
        GL11.glPushMatrix();
        GL11.glColor3f(1, 1, 0);
        GL11.glBegin(GL11.GL_LINES);
        double innerRadius = 0.5;
        double outerRadius = 0.6;
        for (int i = 0; i < sides; i++) {
            //GL11.glVertex3d(0, 0, 0);
            x = innerRadius * Math.cos(theta * i);
            y = innerRadius * Math.sin(theta * i);
            GL11.glVertex3d(x, y, z);
            x = outerRadius * Math.cos(theta * i);
            y = outerRadius * Math.sin(theta * i);
            GL11.glVertex3d(x, y, z);
        }
        GL11.glEnd();
        GL11.glPopMatrix();        
    }
    
    private void drawMinuteMarks() {
        int sides = 60;
        double theta = 2 * Math.PI / sides;
        double x;
        double y;
        double z = -0.1;
        GL11.glPushMatrix();
        GL11.glColor3f(0.5f, 0.5f, 0);
        GL11.glBegin(GL11.GL_LINES);
        double innerRadius = 0.5;
        double outerRadius = 0.55;
        for (int i = 0; i < sides; i++) {
            //GL11.glVertex3d(0, 0, 0);
            x = innerRadius * Math.cos(theta * i);
            y = innerRadius * Math.sin(theta * i);
            GL11.glVertex3d(x, y, z);
            x = outerRadius * Math.cos(theta * i);
            y = outerRadius * Math.sin(theta * i);
            GL11.glVertex3d(x, y, z);
        }
        GL11.glEnd();
        GL11.glPopMatrix();        
    }

}
