package ru.spb.hse.karvozavr.cli.commands.builtins

import ru.spb.hse.karvozavr.cli.commands.Command
import ru.spb.hse.karvozavr.cli.shell.Shell
import ru.spb.hse.karvozavr.cli.streams.InStream
import ru.spb.hse.karvozavr.cli.streams.OutStream

/**
 * Echo command.
 *
 * Similar to bash echo command.
 *
 *
 */
class EchoCommand(
    args: List<String>,
    inputStream: InStream,
    outStream: OutStream,
    errStream: OutStream,
    shell: Shell
) :
    Command(args, inputStream, outStream, errStream, shell) {

    override fun execute(): Int {
        writeLine(args.joinToString(separator = " "))
        return 0
    }
}
