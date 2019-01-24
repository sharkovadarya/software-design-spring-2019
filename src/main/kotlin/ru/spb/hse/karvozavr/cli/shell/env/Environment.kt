package ru.spb.hse.karvozavr.cli.shell.env

interface Environment {
    fun variables(): MutableMap<String, String>
    fun currentDir(): Directory
    fun changeDirectory(newDir: Directory)
}

