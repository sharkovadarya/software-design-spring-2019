package ru.spb.hse.karvozavr.cli.commands.builtins

import org.junit.Assert
import org.junit.Test
import ru.spb.hse.karvozavr.cli.shell.CliShell
import ru.spb.hse.karvozavr.cli.streams.EmptyStream
import ru.spb.hse.karvozavr.cli.streams.ReadWriteStream
import ru.spb.hse.karvozavr.cli.util.ExitCode
import java.io.File
import java.nio.file.Paths

class CdCommandTest {
    @Test
    fun testSrc() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()
        val shell = CliShell.emptyShell()
        val directory = shell.environment().currentDir()

        val cmd = CdCommand(
            listOf("src"),
            EmptyStream,
            outStream,
            errStream,
            shell
        )

        Assert.assertEquals(ExitCode.SUCCESS, cmd.execute())
        Assert.assertEquals(false, outStream.isNotEmpty())
        val newDirectory = shell.environment().currentDir()
        Assert.assertEquals(Paths.get(directory.toString() + File.separator + "src"), newDirectory)
        Assert.assertEquals(true, outStream.isEmpty())
        Assert.assertEquals(true, errStream.isEmpty())
    }

    @Test
    fun testHomeDirectory() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()
        val shell = CliShell.emptyShell()

        val cmd = CdCommand(
            emptyList(),
            EmptyStream,
            outStream,
            errStream,
            shell
        )

        Assert.assertEquals(ExitCode.SUCCESS, cmd.execute())
        Assert.assertEquals(false, outStream.isNotEmpty())
        val newDirectory = shell.environment().currentDir()
        Assert.assertEquals(Paths.get(System.getProperty("user.home")), newDirectory)
        Assert.assertEquals(true, outStream.isEmpty())
        Assert.assertEquals(true, errStream.isEmpty())
    }

    @Test
    fun testPreviousDirectory() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()
        val shell = CliShell.emptyShell()
        val directory = shell.environment().currentDir()

        CdCommand(
            listOf("src/main"),
            EmptyStream,
            outStream,
            errStream,
            shell
        ).execute()

        val cmd = CdCommand(
            listOf(".."),
            EmptyStream,
            outStream,
            errStream,
            shell
        )

        Assert.assertEquals(ExitCode.SUCCESS, cmd.execute())
        Assert.assertEquals(false, outStream.isNotEmpty())
        val newDirectory = shell.environment().currentDir()
        Assert.assertEquals(Paths.get(directory.toString() + File.separator + "src"), newDirectory)
        Assert.assertEquals(true, outStream.isEmpty())
        Assert.assertEquals(true, errStream.isEmpty())
    }

    @Test
    fun testCurrentDirectory() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()
        val shell = CliShell.emptyShell()
        val directory = shell.environment().currentDir()

        val cmd = CdCommand(
            listOf("."),
            EmptyStream,
            outStream,
            errStream,
            shell
        )

        Assert.assertEquals(ExitCode.SUCCESS, cmd.execute())
        Assert.assertEquals(false, outStream.isNotEmpty())
        val newDirectory = shell.environment().currentDir()
        Assert.assertEquals(directory, newDirectory)
        Assert.assertEquals(true, outStream.isEmpty())
        Assert.assertEquals(true, errStream.isEmpty())
    }

    @Test
    fun testNonexistentDirectory() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()
        val shell = CliShell.emptyShell()

        val cmd = CdCommand(
            listOf("src/main/test"),
            EmptyStream,
            outStream,
            errStream,
            shell
        )

        Assert.assertEquals(ExitCode.RESOURCE_NOT_FOUND, cmd.execute())
        Assert.assertEquals(false, outStream.isNotEmpty())
        Assert.assertEquals(true, outStream.isEmpty())
        Assert.assertEquals(false, errStream.isEmpty())
        Assert.assertEquals("cd: src/main/test: No such file or directory", errStream.scanLine())
    }

    @Test
    fun testFile() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()
        val shell = CliShell.emptyShell()

        val cmd = CdCommand(
            listOf("build.gradle"),
            EmptyStream,
            outStream,
            errStream,
            shell
        )

        Assert.assertEquals(ExitCode.INVALID_ARGUMENTS, cmd.execute())
        Assert.assertEquals(false, outStream.isNotEmpty())
        Assert.assertEquals(true, outStream.isEmpty())
        Assert.assertEquals(false, errStream.isEmpty())
        Assert.assertEquals("cd: build.gradle: Not a directory", errStream.scanLine())
    }
}