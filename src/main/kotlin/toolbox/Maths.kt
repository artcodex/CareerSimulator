package com.bn_gaming.career_simulator.toolbox

import org.joml.Matrix4f
import org.joml.Vector3f

class Maths {
    companion object {
        fun createTransformationMatrix(translation: Vector3f, rx: Double, ry: Double, rz: Double, scale: Float): Matrix4f {
            var matrix = Matrix4f()
            matrix = matrix.identity()
            matrix = matrix.translate(translation)
            matrix = matrix.rotate(Math.toRadians(rx).toFloat(), Vector3f(1.0f,0.0f,0.0f))
            matrix = matrix.rotate(Math.toRadians(ry).toFloat(), Vector3f(0.0f,1.0f,0.0f))
            matrix = matrix.rotate(Math.toRadians(rz).toFloat(), Vector3f(0.0f,0.0f,1.0f))
            matrix = matrix.scale(scale)

            return matrix
        }
    }
}