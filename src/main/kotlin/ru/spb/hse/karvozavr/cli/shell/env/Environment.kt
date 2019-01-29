package ru.spb.hse.karvozavr.cli.shell.env

import java.nio.file.Path

interface Environment {
    fun variables(): MutableMap<String, String>
    fun currentDir(): Path
    fun changeDirectory(newDir: Path)
    fun shellPrompt(): String
}

