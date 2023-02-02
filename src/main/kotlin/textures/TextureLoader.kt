package com.bn_gaming.career_simulator.textures
import de.matthiasmann.twl.utils.PNGDecoder
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL30.glGenerateMipmap
import java.io.InputStream
import java.nio.ByteBuffer

class TextureLoader {
    companion object {
        fun loadTexture(textureStream: InputStream): Int{
            //load png file
            //load png file
            val decoder = PNGDecoder(textureStream)

            //create a byte buffer big enough to store RGBA values

            //create a byte buffer big enough to store RGBA values
            val buffer: ByteBuffer = ByteBuffer.allocateDirect(4 * decoder.width * decoder.height)

            //decode

            //decode
            decoder.decode(buffer, decoder.width * 4, PNGDecoder.Format.RGBA)

            //flip the buffer so its ready to read

            //flip the buffer so its ready to read
            buffer.flip()

            //create a texture

            //create a texture
            val id: Int = glGenTextures()

            //bind the texture

            //bind the texture
            glBindTexture(GL_TEXTURE_2D, id)

            //tell opengl how to unpack bytes

            //tell opengl how to unpack bytes
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1)

            //set the texture parameters, can be GL_LINEAR or GL_NEAREST

            //set the texture parameters, can be GL_LINEAR or GL_NEAREST
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR.toFloat())
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR.toFloat())

            //upload texture

            //upload texture
            glTexImage2D(
                GL_TEXTURE_2D,
                0,
                GL_RGBA,
                decoder.width,
                decoder.height,
                0,
                GL_RGBA,
                GL_UNSIGNED_BYTE,
                buffer
            )

            // Generate Mip Map

            // Generate Mip Map
            glGenerateMipmap(GL_TEXTURE_2D)

            return id
        }
    }
}