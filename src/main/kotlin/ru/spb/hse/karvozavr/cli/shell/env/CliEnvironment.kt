package ru.spb.hse.karvozavr.cli.shell.env

class CliEnvironment(private var currentDir: Directory, private val variables: MutableMap<String, String> = mutableMapOf()) :
    Environment {

    override fun variables(): MutableMap<String, String> =
        variables


    override fun currentDir(): Directory =
        currentDir

    override fun changeDirectory(newDir: Directory) {
        currentDir = newDir
    }
}