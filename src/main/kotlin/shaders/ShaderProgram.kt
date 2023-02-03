package com.bn_gaming.career_simulator.shaders

import org.joml.Matrix4f
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.nio.FloatBuffer


abstract class ShaderProgram(vertexFile: String, fragmentFile: String) {
    private var programID = 0
    private var vertexShaderID = 0
    private var fragmentShaderID = 0
    init {
        vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile,  GL20.GL_FRAGMENT_SHADER)
    }

    fun setup() {
        programID = GL20.glCreateProgram()
        GL20.glAttachShader(programID, vertexShaderID)
        GL20.glAttachShader(programID, fragmentShaderID)
        bindAttributes()

        GL20.glLinkProgram(programID)
        GL20.glValidateProgram(programID)
    }

    fun start() {
        GL20.glUseProgram(programID)
    }

    fun stop() {
        GL20.glUseProgram(0)
    }

    fun cleanUp() {
        stop();
        GL20.glDetachShader(programID, vertexShaderID)
        GL20.glDetachShader(programID, fragmentShaderID)
        GL20.glDeleteShader(vertexShaderID)
        GL20.glDeleteShader(fragmentShaderID)
        GL20.glDeleteProgram(programID)
    }

    protected abstract fun getAllUniformLocations()

    protected fun getUniformLocation(uniformName: String): Int {
        return GL20.glGetUniformLocation(programID, uniformName)
    }

    protected fun bindAttribute(attribute: Int, variableName: String) {
        GL20.glBindAttribLocation(programID, attribute, variableName)
    }

    abstract fun bindAttributes();

    protected fun loadFloat(location: Int, value: Float) {
        GL20.glUniform1f(location, value)
    }

    protected fun loadVector(location: Int, vector: Vector3f) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z)
    }

    protected fun loadBoolean(location: Int, value: Boolean) {
        var toLoad = 0.0f
        if (value) {
            toLoad = 1.0f
        }

        GL20.glUniform1f(location, toLoad)
    }

    protected fun loadMatrix(location: Int, matrix: Matrix4f) {
        matrix.get(matrixBuffer)
        matrixBuffer.flip()
        GL20.glUniformMatrix4fv(location, false, matrixBuffer)
    }

    companion object {
        val matrixBuffer: FloatBuffer = BufferUtils.createFloatBuffer(16)

        fun loadShader(file: String, type: Int): Int {
            val shaderSource = StringBuilder()
            try {
                val reader = BufferedReader(FileReader(file))
                reader.useLines {
                    it.forEach { line -> shaderSource.append(line).append("//\n") }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                System.exit(-1)
            }
            val shaderID = GL20.glCreateShader(type)
            GL20.glShaderSource(shaderID, shaderSource)
            GL20.glCompileShader(shaderID)
            if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
                println(GL20.glGetShaderInfoLog(shaderID, 500))
                System.err.println("Could not compile shader!")
                System.exit(-1)
            }
            return shaderID
        }
    }

}