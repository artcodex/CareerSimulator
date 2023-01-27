package com.bn_gaming.career_simulator.renderEngine

import org.lwjgl.glfw.Callbacks.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.GLFWKeyCallbackI
import org.lwjgl.glfw.GLFWVidMode
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.*
import java.nio.IntBuffer


class Display {
    companion object {
        var window: Long = 0
        const val WIDTH = 1280
        const val HEIGHT = 720
        fun closeDisplay() {
            // Free the window callbacks and destroy the window
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);

            // Terminate GLFW and free the error callback
            glfwTerminate();
            glfwSetErrorCallback(null)?.free();
        }
        fun shouldWindowClose(): Boolean {
            return glfwWindowShouldClose(window)
        }

        fun updateDisplay(renderFunc: () -> Unit) {
            renderFunc()
            glfwSwapBuffers(window) // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents()
        }

        fun createDisplay() {
            GLFWErrorCallback.createPrint(System.err).set()

            if (!glfwInit()) {
                throw IllegalStateException("Unable to initialize GLFW");
            }

            glfwDefaultWindowHints()
            glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE)
            glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

            // Create the window
            window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
            if ( window == NULL )
                throw RuntimeException("Failed to create the GLFW window");

            glfwSetKeyCallback(window, GLFWKeyCallbackI { window, key, _, action, _ ->
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(
                    window,
                    true
                ) // We will detect this in the rendering loop
            })

            val stack: MemoryStack = stackPush()
            try{
                val pWidth: IntBuffer = stack.mallocInt(1) // int*
                val pHeight: IntBuffer = stack.mallocInt(1) // int*

                // Get the window size passed to glfwCreateWindow
                glfwGetWindowSize(window, pWidth, pHeight)

                // Get the resolution of the primary monitor
                val vidmode: GLFWVidMode? = glfwGetVideoMode(glfwGetPrimaryMonitor())

                // Center the window
                glfwSetWindowPos(
                    window,
                    ((vidmode?.width() ?: 0) - pWidth[0]) / 2,
                    ((vidmode?.height() ?: 0) - pHeight[0]) / 2
                )
            }
            finally {
                stack.close()
            }

            // Make the OpenGL context current
            glfwMakeContextCurrent(window);
            // Enable v-sync
            glfwSwapInterval(1);

            // Make the window visible
            glfwShowWindow(window);

            GL.createCapabilities()
        }
    }
}