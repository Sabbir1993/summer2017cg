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
    private float secondsAngle;
    private float minutesAngle;
    private float hoursAngle;
    private float speed;

    public Renderer() {
        secondsAngle = 0;
        minutesAngle = 0;
        hoursAngle = 0;
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

    private float getSeconds() {
        LocalTime currentTime = LocalTime.now();
        float secondsFraction = currentTime.getNano() / 1000000000.0f;
        return currentTime.getSecond() + secondsFraction;
    }

    private float getMinutes() {
        LocalTime currentTime = LocalTime.now();
        return currentTime.getMinute() + getSeconds() / 60.0f;
    }

    private float getHours() {
        LocalTime currentTime = LocalTime.now();
        return currentTime.getHour() + getMinutes() / 60.f;
    }

    private void drawSecondsHand() {
        secondsAngle = 90 - getSeconds() * 6;
        GL11.glPushMatrix();
        GL11.glColor3f(1, 1, 1);
        GL11.glRotatef(secondsAngle, 0, 0, 1);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3f(-0.0f, 0, 0);
        GL11.glVertex3f(+0.4f, 0, 0);

        GL11.glVertex3f(0.4f, -0.05f, 0);
        GL11.glVertex3f(0.4f, +0.05f, 0);

        GL11.glVertex3f(0.4f, -0.05f, 0);
        GL11.glVertex3f(+0.5f, 0, 0);

        GL11.glVertex3f(0.4f, +0.05f, 0);
        GL11.glVertex3f(+0.5f, 0, 0);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    private void drawMinutesHand() {
        minutesAngle = 90 - getMinutes() * 6;
        GL11.glPushMatrix();
        GL11.glColor3f(1, 1, 1);
        GL11.glRotatef(minutesAngle, 0, 0, 1);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3f(-0.0f, 0, 0);
        GL11.glVertex3f(+0.3f, 0, 0);

        GL11.glVertex3f(0.3f, -0.05f, 0);
        GL11.glVertex3f(0.3f, +0.05f, 0);

        GL11.glVertex3f(0.3f, -0.05f, 0);
        GL11.glVertex3f(+0.4f, 0, 0);

        GL11.glVertex3f(0.3f, +0.05f, 0);
        GL11.glVertex3f(+0.4f, 0, 0);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    private void drawHoursHand() {
        hoursAngle = 90 - getHours() * (360 / 12.0f);
        GL11.glPushMatrix();
        GL11.glColor3f(1, 1, 1);
        GL11.glRotatef(hoursAngle, 0, 0, 1);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3f(-0.0f, 0, 0);
        GL11.glVertex3f(+0.2f, 0, 0);

        GL11.glVertex3f(0.2f, -0.05f, 0);
        GL11.glVertex3f(0.2f, +0.05f, 0);

        GL11.glVertex3f(0.2f, -0.05f, 0);
        GL11.glVertex3f(+0.3f, 0, 0);

        GL11.glVertex3f(0.2f, +0.05f, 0);
        GL11.glVertex3f(+0.3f, 0, 0);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    private void drawClock() {
        drawDial(0.5, 120);
        drawDial(0.6, 120);
        drawDial(0.1, 120);
        drawMinuteMarks();
        drawHourMarks();
        drawSecondsHand();
        drawMinutesHand();
        drawHoursHand();

        // Lab tasks:
        // Draw the minute and hour hands and sync them with the 
        // clock of your computer
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
        double z = +0.1;
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
