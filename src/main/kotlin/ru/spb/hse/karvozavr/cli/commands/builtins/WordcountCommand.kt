package ru.spb.hse.karvozavr.cli.commands.builtins

import ru.spb.hse.karvozavr.cli.commands.Command
import ru.spb.hse.karvozavr.cli.shell.Shell
import ru.spb.hse.karvozavr.cli.streams.InStream
import ru.spb.hse.karvozavr.cli.streams.OutStream
import java.io.FileNotFoundException
import java.io.FileReader

class WordcountCommand(
    args: List<String>,
    inputStream: InStream,
    outStream: OutStream,
    errStream: OutStream,
    shell: Shell
) :
    Command(args, inputStream, outStream, errStream, shell) {

    override fun execute(): Int = when (args.size) {
        0 -> wcStdin()
        1 -> wcFile(args.first())
        else -> {
            writeError("Invalid arguments: cat command require 1 or 0 arguments.")
            1
        }
    }

    private fun outputResult(lines: Int, words: Int, bytes: Int) {
        writeLine("$lines $words $bytes")
    }

    private fun wcStdin(): Int {
        var lines = 0
        var words = 0
        var bytes = 0
        while (inputStream.isNotEmpty()) {
            val line = inputStream.readLine()
            lines++
            words += line.split("\\s+".toRegex()).size
            bytes += line.length + 1
        }
        outputResult(lines, words, bytes)
        return 0
    }

    private fun wcFile(file: String): Int {
        try {
            var lines = 0
            var words = 0
            var bytes = 0
            val fileReader = FileReader(file)
            fileReader.use {
                it.forEachLine {
                    lines++
                    words += it.split("\\s+".toRegex()).size
                    bytes += it.length + 1
                }
            }
            outputResult(lines, words, bytes)
        } catch (e: FileNotFoundException) {
            writeError("File $file doesn't exist in a filesystem.")
            return 2
        }
        return 0
    }
}