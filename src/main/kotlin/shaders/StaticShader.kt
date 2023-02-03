package com.bn_gaming.career_simulator.shaders

import org.joml.Matrix4f

class StaticShader : ShaderProgram(VERTEX_SHADER, FRAGMENT_SHADER) {
    private var location_transformationMatrix = 0
    companion object {
        const val VERTEX_SHADER = "src\\main\\kotlin\\shaders\\vertexShader.glsl"
        const val FRAGMENT_SHADER = "src\\main\\kotlin\\shaders\\fragmentShader.glsl"
    }

    override fun bindAttributes() {
        super.bindAttribute(0, "position")
        super.bindAttribute(1, "textureCoords")
    }

    override fun getAllUniformLocations() {
        location_transformationMatrix = getUniformLocation("transformationMatrix")
    }

    fun loadTransformationMatrix(matrix: Matrix4f) {
        loadMatrix(location_transformationMatrix, matrix)
    }
}