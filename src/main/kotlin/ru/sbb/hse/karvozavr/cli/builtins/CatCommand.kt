package ru.sbb.hse.karvozavr.cli.builtins

import ru.sbb.hse.karvozavr.cli.Command
import ru.sbb.hse.karvozavr.cli.streams.InStream
import ru.sbb.hse.karvozavr.cli.streams.OutStream
import java.io.FileNotFoundException
import java.io.FileReader

class CatCommand(inputStream: InStream, args: List<String>, outStream: OutStream, errStream: OutStream) :
    Command(inputStream, args, outStream, errStream) {

    override fun execute(): Int = when (args.size) {
        0 -> catStdin()
        1 -> catFile(args.first())
        else -> {
            writeError("Invalid arguments: cat command require 1 or 0 arguments.")
            1
        }
    }

    private fun catStdin(): Int {
        while (inputStream.isNotEmpty())
            writeLine(inputStream.readLine())
        return 0
    }

    private fun catFile(file: String): Int {
        try {
            FileReader(file).use {
                it.readLines().forEach { line -> writeLine(line) }
            }
        } catch (e: FileNotFoundException) {
            writeError("File $file doesn't exist in a filesystem.")
            return 2
        }
        return 0
    }
}