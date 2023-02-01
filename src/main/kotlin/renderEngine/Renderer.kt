package com.bn_gaming.career_simulator.renderEngine

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30.*
class Renderer {
    fun prepare() {
        GL11.glClearColor(1.0f, 0.0f, 0.0f, 1.0f)
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
    }

    fun render(model: RawModel) {
        glBindVertexArray(model.vaoID)
        glEnableVertexAttribArray(0)
        glDrawElements(GL_TRIANGLES, model.vertexCount, GL11.GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glBindVertexArray(0)
    }
}