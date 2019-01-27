package ru.spb.hse.karvozavr.cli.streams

interface OutStream {
    fun write(line: String)
}