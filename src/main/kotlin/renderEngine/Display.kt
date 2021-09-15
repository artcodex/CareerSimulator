package com.bn_gaming.career_simulator.renderEngine

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback

class Display {
    companion object {
        fun createDisplay() {
            GLFWErrorCallback.createPrint(System.err).set()

            if (!glfwInit()) {
                throw IllegalStateException("Unable to initialize GLFW");
            }

            glfwDefaultWindowHints()
            glfwWindowHint(GLFW_VISIBLE)
        }
    }
}