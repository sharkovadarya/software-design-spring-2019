package ru.spb.hse.karvozavr.cli.shell.env

import ru.spb.hse.karvozavr.cli.util.ExitCode

interface Environment {
    fun variables(): MutableMap<String, String>
    fun currentDir(): Directory
    fun changeDirectory(newDir: Directory)
    fun shellPrompt(): String
}

