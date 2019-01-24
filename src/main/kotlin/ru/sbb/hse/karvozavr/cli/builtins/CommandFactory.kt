package ru.sbb.hse.karvozavr.cli.builtins

import ru.sbb.hse.karvozavr.cli.Command
import ru.sbb.hse.karvozavr.cli.streams.InStream
import ru.sbb.hse.karvozavr.cli.streams.OutStream

object CommandFactory {
    fun createCommand(
        commandName: String,
        args: List<String>,
        inStream: InStream,
        outStream: OutStream,
        errStream: OutStream
    ): Command = when (commandName) {
        "cat" -> CatCommand(inStream, args, outStream, errStream)
        else -> TODO()
    }
}