package ru.spb.hse.karvozavr.cli.streams

class StdoutStream : OutStream {
    override fun writeLine(line: String) {
        println(line)
    }
}