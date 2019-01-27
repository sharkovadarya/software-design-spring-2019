package ru.spb.hse.karvozavr.cli.streams

class StdoutStream : OutStream {
    override fun write(line: String) {
        println(line)
    }
}