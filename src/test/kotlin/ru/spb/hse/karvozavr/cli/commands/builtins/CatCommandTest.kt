package ru.spb.hse.karvozavr.cli.commands.builtins

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.spb.hse.karvozavr.cli.shell.CliShell
import ru.spb.hse.karvozavr.cli.streams.EmptyStream
import ru.spb.hse.karvozavr.cli.streams.ReadWriteStream
import ru.spb.hse.karvozavr.cli.util.ExitCode


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

        assertEquals(ExitCode.SUCCESS, cmd.execute())
        assertEquals(true, outStream.isNotEmpty())
        assertEquals(true, errStream.isEmpty())
    }

    @Test
    fun testCatStdin() {
        val inStream = ReadWriteStream()
        val errStream = ReadWriteStream()
        val inputData = listOf("Hello", "World")
        inputData.forEach { inStream.write(it) }
        val outStream = ReadWriteStream()

        val cmd = CatCommand(emptyList(), inStream, outStream, errStream, CliShell.emptyShell())

        assertEquals(ExitCode.SUCCESS, cmd.execute())
        assertEquals(true, outStream.isNotEmpty())
        assertEquals(true, errStream.isEmpty())

        val seq = generateSequence { if (outStream.isNotEmpty()) outStream.readLine() else null }
        assertEquals(inputData, seq.toList())
        assertEquals(true, outStream.isEmpty())
    }
}
