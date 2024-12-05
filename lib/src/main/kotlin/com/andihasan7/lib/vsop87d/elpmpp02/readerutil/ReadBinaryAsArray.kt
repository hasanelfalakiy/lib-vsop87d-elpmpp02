package com.andihasan7.lib.vsop87d.elpmpp02.readerutil
/*
import com.esotericsoftware.kryo.kryo5.Kryo
import com.esotericsoftware.kryo.kryo5.io.Input

object ReadBinaryAsArray {

    /**
     * function to read binary as array
     *
     * @param resourcePath path of binary file
     *
     * @return Array<DoubleArray>
     */
    fun readBinaryAsArray(resourcePath: String): Array<DoubleArray> {

        val inputStream = this::class.java.classLoader.getResourceAsStream(resourcePath) ?: throw IllegalArgumentException("Resource $resourcePath not found")

        val kryo = Kryo()
        kryo.register(DoubleArray::class.java)
        kryo.register(Array<DoubleArray>::class.java)

        Input(inputStream).use { input ->
            return kryo.readObject(input, Array<DoubleArray>::class.java)
        }
    }
}
*/