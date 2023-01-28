package com.bn_gaming.career_simulator

import com.bn_gaming.career_simulator.renderEngine.Display
import com.bn_gaming.career_simulator.renderEngine.Loader
import com.bn_gaming.career_simulator.renderEngine.Renderer


class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            print("Ready to go")
            val loader = Loader()
            val render = Renderer()
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
                Display.updateDisplay {
                    render.prepare()
                    render.render(model)
                }
            }

            Display.closeDisplay()
        }
    }
}