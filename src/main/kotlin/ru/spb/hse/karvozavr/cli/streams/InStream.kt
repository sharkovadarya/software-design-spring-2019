package ru.spb.hse.karvozavr.cli.streams

interface InStream {
    fun readLine(): String
    fun isEmpty(): Boolean
    fun isNotEmpty() = !isEmpty()
}

object EmptyStream : InStream {
    override fun isEmpty(): Boolean = true

    override fun readLine(): String = ""
}
