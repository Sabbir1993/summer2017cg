/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.ac.seu.lwjgldemo;

import org.lwjgl.Version;

/**
 *
 * @author kmhasan
 */
public class Renderer {

    public Renderer() {
    }
    
    public void run() {
        init();
        loop();
    }
    
    private void init() {
        System.out.println("LWJGL version " + Version.getVersion());
    }
    
    private void loop() {
        
    }
}
