package ru.spb.hse.karvozavr.cli.commands.builtins

import ru.spb.hse.karvozavr.cli.commands.Command
import ru.spb.hse.karvozavr.cli.shell.Shell
import ru.spb.hse.karvozavr.cli.streams.InStream
import ru.spb.hse.karvozavr.cli.streams.OutStream
import ru.spb.hse.karvozavr.cli.util.ExitCode
import java.io.File
import java.nio.file.Paths

class LsCommand (
    args: List<String>,
    inputStream: InStream,
    outStream: OutStream,
    errStream: OutStream,
    shell: Shell
) : Command(args, inputStream, outStream, errStream, shell) {

    private fun lsDirectory(directoryName: String = ""): ExitCode {
        val currentDirectory = shell().environment().currentDir()
        val lsDirectory = Paths.get(currentDirectory.toString() + File.separator + directoryName).toFile()
        if (!lsDirectory.exists()) {
            writeError("ls: cannot access ${lsDirectory.name}: No such file or directory")
            return ExitCode.RESOURCE_NOT_FOUND
        } else if (lsDirectory.isFile) {
            writeLine(lsDirectory.name)
        } else {
            for (file in lsDirectory.listFiles()) {
                writeLine(file.name)
            }
        }

        return ExitCode.SUCCESS
    }

    override fun execute(): ExitCode {
        var exitCode = ExitCode.SUCCESS

        if (args.isEmpty()) {
            return lsDirectory()
        }

        for (dir in args) {
            if (args.size > 1) {
                writeLine("-$dir")
            }
            val dirExitCode = lsDirectory(dir)
            if (dirExitCode != ExitCode.SUCCESS) {
                exitCode = dirExitCode
            }
        }

        return exitCode
    }
}