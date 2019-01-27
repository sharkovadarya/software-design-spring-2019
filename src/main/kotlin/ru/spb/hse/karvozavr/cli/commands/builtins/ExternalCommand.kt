package ru.spb.hse.karvozavr.cli.commands.builtins

import ru.spb.hse.karvozavr.cli.commands.Command
import ru.spb.hse.karvozavr.cli.shell.Shell
import ru.spb.hse.karvozavr.cli.streams.InStream
import ru.spb.hse.karvozavr.cli.streams.OutStream
import ru.spb.hse.karvozavr.cli.util.ExitCode
import java.io.File
import java.nio.file.Files

class ExternalCommand(
    args: List<String>,
    inputStream: InStream,
    outStream: OutStream,
    errStream: OutStream,
    shell: Shell,
    val command: String
) : Command(args, inputStream, outStream, errStream, shell) {

    private val inFile = Files.createTempFile("pipetempin", ".tmp")
    private val outFile = Files.createTempFile("pipetempout", ".tmp")

    override fun execute(): ExitCode {
        return try {
            val commandList = listOf(command) + args
            val exitCode = ProcessBuilder()
                .command(commandList)
                .redirectInput(inputStreamToFile())
                .redirectOutput(outFile.toFile())
                .start()
                .waitFor()

            outputFileToStream()

            if (exitCode == 0) {
                ExitCode.SUCCESS
            } else {
                ExitCode.UNKNOWN_CODE
            }
        } catch (e: Exception) {
            ExitCode.UNKNOWN_COMMAND
        }
    }

    private fun inputStreamToFile(): File {
        Files.newBufferedWriter(inFile).use {
            while (inputStream.isNotEmpty()) {
                it.write(inputStream.readLine())
            }
        }
        return inFile.toFile()
    }

    private fun outputFileToStream() {
        Files.lines(outFile).forEach { writeLine(it) }
    }
}