package ru.spb.hse.karvozavr.cli.commands.builtins

import org.junit.Assert.assertEquals
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

        assertEquals(ExitCode.SUCCESS, cmd.execute())
        assertEquals(false, outStream.isNotEmpty())
        val newDirectory = shell.environment().currentDir()
        assertEquals(Paths.get(directory.toString() + File.separator + "src"), newDirectory)
        assertEquals(true, outStream.isEmpty())
        assertEquals(true, errStream.isEmpty())
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

        assertEquals(ExitCode.SUCCESS, cmd.execute())
        assertEquals(false, outStream.isNotEmpty())
        val newDirectory = shell.environment().currentDir()
        assertEquals(Paths.get(System.getProperty("user.home")), newDirectory)
        assertEquals(true, outStream.isEmpty())
        assertEquals(true, errStream.isEmpty())
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

        assertEquals(ExitCode.SUCCESS, cmd.execute())
        assertEquals(false, outStream.isNotEmpty())
        val newDirectory = shell.environment().currentDir()
        assertEquals(Paths.get(directory.toString() + File.separator + "src"), newDirectory)
        assertEquals(true, outStream.isEmpty())
        assertEquals(true, errStream.isEmpty())
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

        assertEquals(ExitCode.SUCCESS, cmd.execute())
        assertEquals(false, outStream.isNotEmpty())
        val newDirectory = shell.environment().currentDir()
        assertEquals(directory, newDirectory)
        assertEquals(true, outStream.isEmpty())
        assertEquals(true, errStream.isEmpty())
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

        assertEquals(ExitCode.RESOURCE_NOT_FOUND, cmd.execute())
        assertEquals(false, outStream.isNotEmpty())
        assertEquals(true, outStream.isEmpty())
        assertEquals(false, errStream.isEmpty())
        assertEquals("cd: src/main/test: No such file or directory", errStream.scanLine())
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

        assertEquals(ExitCode.INVALID_ARGUMENTS, cmd.execute())
        assertEquals(true, outStream.isEmpty())
        assertEquals(false, errStream.isEmpty())
        assertEquals("cd: build.gradle: Not a directory", errStream.scanLine())
    }

    @Test
    fun testTooManyArguments() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()
        val shell = CliShell.emptyShell()

        CdCommand(listOf("src/test/resources"),
            EmptyStream,
            outStream,
            errStream,
            shell).execute()

        val cmd = CdCommand(listOf("folder", "name", "with", "spaces"),
            EmptyStream, 
            outStream, 
            errStream, 
            shell)

        assertEquals(ExitCode.INVALID_ARGUMENTS, cmd.execute())
        assertEquals(true, outStream.isEmpty())
        assertEquals("cd: too many arguments", errStream.scanLine())
        assertEquals(true, errStream.isEmpty())
    }

    @Test
    fun testArgumentWithSpaces() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()
        val shell = CliShell.emptyShell()
        val directory = shell.environment().currentDir()

        val cmd = CdCommand(listOf("src/test/resources"),
            EmptyStream,
            outStream,
            errStream,
            shell)
        cmd.execute()

        val cdCmd = CdCommand(listOf("folder name with spaces"),
            EmptyStream,
            outStream,
            errStream,
            shell)

        assertEquals(ExitCode.SUCCESS, cdCmd.execute())
        val newDirectory = shell.environment().currentDir()
        assertEquals(Paths.get(directory.toString() + File.separator +
                "src${File.separator}test${File.separator}resources${File.separator}folder name with spaces"),
            newDirectory)
        assertEquals(true, outStream.isEmpty())
        assertEquals(true, errStream.isEmpty())
    }
}