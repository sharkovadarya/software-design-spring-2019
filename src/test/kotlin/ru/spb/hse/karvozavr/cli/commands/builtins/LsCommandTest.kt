package ru.spb.hse.karvozavr.cli.commands.builtins

import org.junit.Assert
import org.junit.Test
import ru.spb.hse.karvozavr.cli.shell.CliShell
import ru.spb.hse.karvozavr.cli.streams.EmptyStream
import ru.spb.hse.karvozavr.cli.streams.ReadWriteStream
import ru.spb.hse.karvozavr.cli.util.ExitCode

class LsCommandTest {
    @Test
    fun testSrc() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()

        val cmd = LsCommand(
            listOf("src"),
            EmptyStream,
            outStream,
            errStream,
            CliShell.emptyShell()
        )

        Assert.assertEquals(ExitCode.SUCCESS, cmd.execute())
        Assert.assertEquals(true, outStream.isNotEmpty())
        val filesList = listOf("main", "test")
        val output = generateSequence { if (outStream.isNotEmpty()) outStream.scanLine() else null }.toList()
        Assert.assertEquals(filesList.size, output.size)
        for (filename in filesList) {
            Assert.assertTrue(output.contains(filename))
        }
        Assert.assertEquals(true, outStream.isEmpty())
        Assert.assertEquals(true, errStream.isEmpty())
    }

    @Test
    fun testNoArguments() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()
        val shell = CliShell.emptyShell()

        CdCommand(listOf("src"), EmptyStream, outStream, errStream, shell).execute()

        val cmd = LsCommand(
            emptyList(),
            EmptyStream,
            outStream,
            errStream,
            shell
        )

        Assert.assertEquals(ExitCode.SUCCESS, cmd.execute())
        Assert.assertEquals(true, outStream.isNotEmpty())
        val filesList = listOf("main", "test")
        val output = generateSequence { if (outStream.isNotEmpty()) outStream.scanLine() else null }.toList()
        Assert.assertEquals(filesList.size, output.size)
        for (filename in filesList) {
            Assert.assertTrue(output.contains(filename))
        }
        Assert.assertEquals(true, outStream.isEmpty())
        Assert.assertEquals(true, errStream.isEmpty())
    }

    @Test
    fun testPreviousDirectory() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()
        val shell = CliShell.emptyShell()

        CdCommand(listOf("src/main"), EmptyStream, outStream, errStream, shell).execute()

        val cmd = LsCommand(
            listOf(".."),
            EmptyStream,
            outStream,
            errStream,
            shell
        )

        Assert.assertEquals(ExitCode.SUCCESS, cmd.execute())
        Assert.assertEquals(true, outStream.isNotEmpty())
        val filesList = listOf("main", "test")
        val output = generateSequence { if (outStream.isNotEmpty()) outStream.scanLine() else null }.toList()
        Assert.assertEquals(filesList.size, output.size)
        for (filename in filesList) {
            Assert.assertTrue(output.contains(filename))
        }
        Assert.assertEquals(true, outStream.isEmpty())
        Assert.assertEquals(true, errStream.isEmpty())
    }

    @Test
    fun testCurrentDirectory() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()
        val shell = CliShell.emptyShell()

        CdCommand(listOf("src"), EmptyStream, outStream, errStream, shell).execute()

        val cmd = LsCommand(
            listOf("."),
            EmptyStream,
            outStream,
            errStream,
            shell
        )

        Assert.assertEquals(ExitCode.SUCCESS, cmd.execute())
        Assert.assertEquals(true, outStream.isNotEmpty())
        val filesList = listOf("main", "test")
        val output = generateSequence { if (outStream.isNotEmpty()) outStream.scanLine() else null }.toList()
        Assert.assertEquals(filesList.size, output.size)
        for (filename in filesList) {
            Assert.assertTrue(output.contains(filename))
        }
        Assert.assertEquals(true, outStream.isEmpty())
        Assert.assertEquals(true, errStream.isEmpty())
    }
}