package com.bn_gaming.career_simulator

import com.bn_gaming.career_simulator.renderEngine.Display
import com.bn_gaming.career_simulator.renderEngine.Loader
import com.bn_gaming.career_simulator.renderEngine.RawModel
import com.bn_gaming.career_simulator.renderEngine.Renderer
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11

class Main {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val loader = Loader()
            val renderer = Renderer()

            // Creates the glfw resources for display
            Display.createDisplay()

            val vertices = floatArrayOf(
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
            )

            val model = loader.loadToVAO(vertices)
            while (!Display.shouldWindowClose()) {
                // Loop showing the display until esc key pressed
                Display.updateDisplay {
                    renderer.prepare()
                    renderer.render(model)
                }
            }

            // Close resources used for display
            Display.closeDisplay()
        }
    }
}