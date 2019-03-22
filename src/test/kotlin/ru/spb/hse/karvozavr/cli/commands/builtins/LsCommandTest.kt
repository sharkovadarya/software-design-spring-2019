package ru.spb.hse.karvozavr.cli.commands.builtins

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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

        assertEquals(ExitCode.SUCCESS, cmd.execute())
        assertEquals(true, outStream.isNotEmpty())
        val filesList = listOf("main", "test")
        val output = generateSequence { if (outStream.isNotEmpty()) outStream.scanLine() else null }.toList()
        assertEquals(filesList.size, output.size)
        for (filename in filesList) {
            assertTrue(output.contains(filename))
        }
        assertEquals(true, outStream.isEmpty())
        assertEquals(true, errStream.isEmpty())
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

        assertEquals(ExitCode.SUCCESS, cmd.execute())
        assertEquals(true, outStream.isNotEmpty())
        val filesList = listOf("main", "test")
        val output = generateSequence { if (outStream.isNotEmpty()) outStream.scanLine() else null }.toList()
        assertEquals(filesList.size, output.size)
        for (filename in filesList) {
            assertTrue(output.contains(filename))
        }
        assertEquals(true, outStream.isEmpty())
        assertEquals(true, errStream.isEmpty())
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

        assertEquals(ExitCode.SUCCESS, cmd.execute())
        assertEquals(true, outStream.isNotEmpty())
        val filesList = listOf("main", "test")
        val output = generateSequence { if (outStream.isNotEmpty()) outStream.scanLine() else null }.toList()
        assertEquals(filesList.size, output.size)
        for (filename in filesList) {
            assertTrue(output.contains(filename))
        }
        assertEquals(true, outStream.isEmpty())
        assertEquals(true, errStream.isEmpty())
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

        assertEquals(ExitCode.SUCCESS, cmd.execute())
        assertEquals(true, outStream.isNotEmpty())
        val filesList = listOf("main", "test")
        val output = generateSequence { if (outStream.isNotEmpty()) outStream.scanLine() else null }.toList()
        assertEquals(filesList.size, output.size)
        for (filename in filesList) {
            assertTrue(output.contains(filename))
        }
        assertEquals(true, outStream.isEmpty())
        assertEquals(true, errStream.isEmpty())
    }
    
    @Test
    fun testNonexistentDirectory() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()
        val shell = CliShell.emptyShell()

        val cmd = LsCommand(
            listOf("nonexistent"),
            EmptyStream,
            outStream,
            errStream,
            shell
        )

        assertEquals(ExitCode.RESOURCE_NOT_FOUND, cmd.execute())
        assertEquals("ls: cannot access nonexistent: No such file or directory",
            errStream.scanLine())
    }

    @Test
    fun testFile() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()
        val shell = CliShell.emptyShell()

        val cmd = LsCommand(
            listOf("build.gradle"),
            EmptyStream,
            outStream,
            errStream,
            shell
        )

        assertEquals(ExitCode.SUCCESS, cmd.execute())
        assertEquals("build.gradle", outStream.scanLine())
        assertEquals(true, outStream.isEmpty())
        assertEquals(true, errStream.isEmpty())
    }

    @Test
    fun testMultipleArguments() {
        val errStream = ReadWriteStream()
        val outStream = ReadWriteStream()
        val shell = CliShell.emptyShell()

        val cmd = LsCommand(
            listOf("src/main", "src/test"),
            EmptyStream,
            outStream,
            errStream,
            shell
        )
        assertEquals(ExitCode.SUCCESS, cmd.execute())
        assertEquals(true, outStream.isNotEmpty())
        val filesList = listOf("-src/main", "java", "kotlin", "resources",
            "-src/test","java", "kotlin", "resources")
        val output = generateSequence { if (outStream.isNotEmpty()) outStream.scanLine() else null }.toList()
        assertEquals(filesList.sorted(), output.sorted())
        assertEquals(true, outStream.isEmpty())
        assertEquals(true, errStream.isEmpty())
    }
}