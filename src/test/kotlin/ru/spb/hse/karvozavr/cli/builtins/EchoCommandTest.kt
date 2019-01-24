package ru.spb.hse.karvozavr.cli.builtins

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.spb.hse.karvozavr.cli.commands.builtins.EchoCommand
import ru.spb.hse.karvozavr.cli.shell.CliShell
import ru.spb.hse.karvozavr.cli.streams.EmptyStream
import ru.spb.hse.karvozavr.cli.streams.ReadWriteStream

class EchoCommandTest {

    @Test
    fun testEmpty() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()

        val cmd = EchoCommand(
            emptyList(),
            EmptyStream,
            outStream,
            errStream,
            CliShell.emptyShell()
        )

        assertEquals(cmd.execute(), 0)
        assertEquals(outStream.isNotEmpty(), true)
        assertEquals(outStream.readLine(), "")
        assertEquals(outStream.isEmpty(), true)
        assertEquals(errStream.isEmpty(), true)
    }

    @Test
    fun testOne() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()

        val cmd = EchoCommand(
            listOf("arg"),
            EmptyStream,
            outStream,
            errStream,
            CliShell.emptyShell()
        )

        assertEquals(cmd.execute(), 0)
        assertEquals(outStream.isNotEmpty(), true)
        assertEquals(outStream.readLine(), "arg")
        assertEquals(outStream.isEmpty(), true)
        assertEquals(errStream.isEmpty(), true)
    }

    @Test
    fun testMany() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()

        val data = listOf("arg1", "arg2", "arg3", "arg4")
        val cmd = EchoCommand(
            data,
            EmptyStream,
            outStream,
            errStream,
            CliShell.emptyShell()
        )

        assertEquals(cmd.execute(), 0)
        assertEquals(outStream.isNotEmpty(), true)
        assertEquals(outStream.readLine(), data.joinToString(separator = " "))
        assertEquals(outStream.isEmpty(), true)
        assertEquals(errStream.isEmpty(), true)
    }
}
