package ru.spb.hse.karvozavr.cli.shell

import ru.spb.hse.karvozavr.cli.shell.env.Environment

interface Shell {
    fun environment(): Environment
    fun isTerminated(): Boolean
    fun isNotTerminated() = !isTerminated()
    fun terminate()
}
