package com.bn_gaming.career_simulator.renderEngine

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30.*
import java.nio.FloatBuffer

class Loader {
    private val vaos: MutableList<Int> = mutableListOf()
    private val vbos: MutableList<Int> = mutableListOf()

    fun loadToVAO(positions: FloatArray): RawModel {
        val vaoID = createVAO()

        storeDataInAttributeList(0, positions)
        unbindVAO()

        return RawModel(vaoID = vaoID, vertexCount = positions.size / 3)
    }

    fun cleanup() {
        vaos.forEach {
            glDeleteVertexArrays(it)
        }

        vbos.forEach {
            glDeleteBuffers(it)
        }
    }

    private fun createVAO(): Int {
        val vaoID = glGenVertexArrays()
        vaos.add(vaoID)

        glBindVertexArray(vaoID)

        return vaoID
    }

    private fun storeDataInAttributeList(attributeNumber: Int, data: FloatArray) {
        val vboID = glGenBuffers()
        vbos.add(vboID)

        glBindBuffer(GL_ARRAY_BUFFER, vboID)

        val buffer = storeDataInFloatBuffer(data)
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
        glVertexAttribPointer(attributeNumber, 3, GL_FLOAT, false, 0, 0)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    private fun storeDataInFloatBuffer(data: FloatArray): FloatBuffer {
        val buffer = BufferUtils.createFloatBuffer(data.size)
        buffer.put(data)
        buffer.flip()

        return buffer
    }

    private fun unbindVAO() {
        glBindVertexArray(0)
    }
}