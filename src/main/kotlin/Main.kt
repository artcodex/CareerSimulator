package com.bn_gaming.career_simulator

import com.bn_gaming.career_simulator.renderEngine.Display
import com.bn_gaming.career_simulator.renderEngine.Loader
import com.bn_gaming.career_simulator.renderEngine.Renderer
import com.bn_gaming.career_simulator.shaders.StaticShader


class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            print("Ready to go")
            Display.createDisplay()

            val loader = Loader()
            val render = Renderer()
            val shader = StaticShader()

            val vertices = floatArrayOf(
                -0.5f, 0.5f, 0f, // V0
                -0.5f, -0.5f, 0f, // V1
                0.5f, -0.5f, 0f, // V2
                0.5f, 0.5f, 0f // V3
            )

            val indices = intArrayOf(
                0,1,3,
                3,1,2
            )

            val model = loader.loadToVAO(vertices, indices)
            while (!Display.shouldWindowClose()) {
                Display.updateDisplay {
                    render.prepare()
                    shader.start()
                    render.render(model)
                    shader.stop()
                }
            }

            shader.cleanUp()
            loader.cleanUp()
            Display.closeDisplay();
        }
    }
}