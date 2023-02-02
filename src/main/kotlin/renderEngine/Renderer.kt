package com.bn_gaming.career_simulator.renderEngine

import com.bn_gaming.career_simulator.models.RawModel
import com.bn_gaming.career_simulator.models.TexturedModel
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30.*
class Renderer {
    fun prepare() {
        GL11.glClearColor(1.0f, 0.0f, 0.0f, 1.0f)
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
    }

    fun render(texturedModel: TexturedModel) {
        val model = texturedModel.rawModel
        glBindVertexArray(model.vaoID)
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, texturedModel.texture.textureID)
        glDrawElements(GL_TRIANGLES, model.vertexCount, GL11.GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glBindVertexArray(0)
    }
}