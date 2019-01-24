package ru.spb.hse.karvozavr.cli.shell.env

import java.nio.file.Path

class Directory(val directory: Path) {

    override fun toString(): String {
        return directory.toString()
    }
}