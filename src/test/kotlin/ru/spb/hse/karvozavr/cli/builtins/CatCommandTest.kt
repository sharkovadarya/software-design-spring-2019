package ru.spb.hse.karvozavr.cli.builtins

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.spb.hse.karvozavr.cli.commands.builtins.CatCommand
import ru.spb.hse.karvozavr.cli.shell.CliShell
import ru.spb.hse.karvozavr.cli.streams.EmptyStream
import ru.spb.hse.karvozavr.cli.streams.ReadWriteStream


class CatCommandTest {

    @Test
    fun testCatFile() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()

        val cmd = CatCommand(
            listOf("/etc/hosts"),
            EmptyStream,
            outStream,
            errStream,
            CliShell.emptyShell()
        )

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

        val cmd = CatCommand(emptyList(), inStream, outStream, errStream, CliShell.emptyShell())

        assertEquals(cmd.execute(), 0)
        assertEquals(outStream.isNotEmpty(), true)
        assertEquals(errStream.isEmpty(), true)

        val seq = generateSequence { if (outStream.isNotEmpty()) outStream.readLine() else null }
        assertEquals(seq.toList(), inputData)
        assertEquals(outStream.isEmpty(), true)
    }
}
