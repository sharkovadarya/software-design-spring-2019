package ru.sbb.hse.karvozavr.cli.builtins

import ru.sbb.hse.karvozavr.cli.Command
import ru.sbb.hse.karvozavr.cli.streams.InStream
import ru.sbb.hse.karvozavr.cli.streams.OutStream

class EchoCommand(inputStream: InStream, args: List<String>, outStream: OutStream, errStream: OutStream) :
    Command(inputStream, args, outStream, errStream) {
    override fun execute(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}