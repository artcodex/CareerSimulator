package com.bn_gaming.career_simulator.renderEngine

import com.bn_gaming.career_simulator.models.RawModel
import com.bn_gaming.career_simulator.textures.TextureLoader
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30.*
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.FloatBuffer
import java.nio.IntBuffer

class Loader {
    private val vaos: MutableList<Int> = mutableListOf()
    private val vbos: MutableList<Int> = mutableListOf()
    private val textures: MutableList<Int> = mutableListOf()

    fun loadToVAO(positions: FloatArray, textureCoords: FloatArray, indices: IntArray): RawModel {
        val vaoID = createVAO()

        bindIndicesBuffer(indices)
        storeDataInAttributeList(0, 3, positions)
        storeDataInAttributeList(1, 2, textureCoords)
        unbindVAO()

        return RawModel(vaoID = vaoID, vertexCount = indices.size)
    }

    fun loadTexture(fileName: String): Int {
        val texture = try {
            TextureLoader.loadTexture(FileInputStream("res/$fileName.png"))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }

        val textureID = texture?.run {
            this
        } ?: -1

        textures.add(textureID)
        return textureID
    }

    private fun createVAO(): Int {
        val vaoID = glGenVertexArrays()
        vaos.add(vaoID)

        glBindVertexArray(vaoID)

        return vaoID
    }

    fun cleanUp() {
        vaos.forEach {
            glDeleteVertexArrays(it)
        }

        vbos.forEach {
            glDeleteBuffers(it)
        }

        textures.forEach {
            if (it >= 0) {
                GL11.glDeleteTextures(it)
            }
        }
    }

    private fun storeDataInAttributeList(attributeNumber: Int, coordSize: Int, data: FloatArray) {
        val vboID = glGenBuffers()
        vbos.add(vboID)

        glBindBuffer(GL_ARRAY_BUFFER, vboID)

        val buffer = storeDataInFloatBuffer(data)
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
        glEnableVertexAttribArray(0)
        glVertexAttribPointer(attributeNumber, coordSize, GL_FLOAT, false, 0, 0)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    private fun bindIndicesBuffer(indices: IntArray) {
        val vboID = glGenBuffers()
        vbos.add(vboID)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID)
        val buffer = storeDataInIntBuffer(indices)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
    }

    private fun storeDataInFloatBuffer(data: FloatArray): FloatBuffer {
        val buffer = BufferUtils.createFloatBuffer(data.size)
        buffer.put(data)
        buffer.flip()

        return buffer
    }

    private fun storeDataInIntBuffer(data: IntArray): IntBuffer {
        val buffer = BufferUtils.createIntBuffer(data.size)
        buffer.put(data)
        buffer.flip()

        return buffer
    }

    private fun unbindVAO() {
        glBindVertexArray(0)
    }
}