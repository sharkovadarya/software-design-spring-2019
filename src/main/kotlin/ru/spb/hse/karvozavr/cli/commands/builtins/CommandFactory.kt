package ru.spb.hse.karvozavr.cli.commands.builtins

import ru.spb.hse.karvozavr.cli.commands.Command
import ru.spb.hse.karvozavr.cli.shell.Shell
import ru.spb.hse.karvozavr.cli.streams.InStream
import ru.spb.hse.karvozavr.cli.streams.OutStream

object CommandFactory {
    fun createCommand(
        commandName: String,
        args: List<String>,
        inStream: InStream,
        outStream: OutStream,
        errStream: OutStream,
        shell: Shell
    ): Command = when (commandName) {
        "cat" -> CatCommand(args, inStream, outStream, errStream, shell)
        "echo" -> EchoCommand(args, inStream, outStream, errStream, shell)
        "wc" -> WordcountCommand(args, inStream, outStream, errStream, shell)
        else -> TODO()
    }
}