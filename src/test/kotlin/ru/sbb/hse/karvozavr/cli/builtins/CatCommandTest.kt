package ru.sbb.hse.karvozavr.cli.builtins

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.sbb.hse.karvozavr.cli.streams.EmptyStream
import ru.sbb.hse.karvozavr.cli.streams.ReadWriteStream


class CatCommandTest {

    @Test
    fun testCatFile() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()

        val cmd = CatCommand(EmptyStream, listOf("/etc/hosts"), outStream, errStream)

        assertEquals(cmd.execute(), 0)
        assertEquals(outStream.isNotEmpty(), true)
        assertEquals(errStream.isEmpty(), true)
    }

    @Test
    fun testCatStdin() {
        val inStream = ReadWriteStream()
        val errStream = ReadWriteStream()
        val inputData = listOf("Hello", "World")
        inputData.forEach { inStream.writeLine(it) }
        val outStream = ReadWriteStream()

        val cmd = CatCommand(inStream, emptyList(), outStream, errStream)

        assertEquals(cmd.execute(), 0)
        assertEquals(outStream.isNotEmpty(), true)
        assertEquals(errStream.isEmpty(), true)

        val seq = generateSequence { if (outStream.isNotEmpty()) outStream.readLine() else null }
        assertEquals(seq.toList(), inputData)
        assertEquals(outStream.isEmpty(), true)
    }
}