package com.bn_gaming.career_simulator.shaders

class StaticShader : ShaderProgram(VERTEX_SHADER, FRAGMENT_SHADER) {
    companion object {
        val VERTEX_SHADER = "src\\main\\kotlin\\shaders\\vertexShader.glsl"
        val FRAGMENT_SHADER = "src\\main\\kotlin\\shaders\\fragmentShader.glsl"
    }

    override fun bindAttributes() {
        super.bindAttribute(0, "position")
    }
}