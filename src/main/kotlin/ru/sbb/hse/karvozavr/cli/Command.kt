package ru.sbb.hse.karvozavr.cli

import ru.sbb.hse.karvozavr.cli.streams.InStream
import ru.sbb.hse.karvozavr.cli.streams.OutStream

abstract class Command(
    protected val inputStream: InStream,
    protected val args: List<String>,
    private val outStream: OutStream,
    private val errStream: OutStream
) {
    protected fun writeError(err: String) {
        errStream.writeLine(err)
    }

    protected fun writeLine(line: String) {
        outStream.writeLine(line)
    }

    abstract fun execute(): Int
}
