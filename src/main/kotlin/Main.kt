package com.bn_gaming.career_simulator

import com.bn_gaming.career_simulator.models.TexturedModel
import com.bn_gaming.career_simulator.renderEngine.Display
import com.bn_gaming.career_simulator.renderEngine.Loader
import com.bn_gaming.career_simulator.renderEngine.Renderer
import com.bn_gaming.career_simulator.shaders.StaticShader
import com.bn_gaming.career_simulator.textures.ModelTexture


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

            val textureCoords = floatArrayOf(
                0.0f, 0.0f, //V0
                0.0f, 1.0f, //V1
                1.0f, 1.0f, //V2
                1.0f, 0.0f  //V3
            )

            val model = loader.loadToVAO(vertices, textureCoords, indices)
            val texture = ModelTexture(loader.loadTexture("texture"))
            val texturedModel = TexturedModel(model, texture)
            shader.setup()
            while (!Display.shouldWindowClose()) {
                Display.updateDisplay {
                    render.prepare()
                    shader.start()
                    render.render(texturedModel)
                    shader.stop()
                }
            }

            shader.cleanUp()
            loader.cleanUp()
            Display.closeDisplay();
        }
    }
}