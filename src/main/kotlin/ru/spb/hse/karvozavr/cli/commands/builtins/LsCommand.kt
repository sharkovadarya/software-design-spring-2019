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

    override fun execute(): ExitCode {
        val currentDirectory = shell().environment().currentDir()
        val lsDirectory = if (args.isEmpty()) currentDirectory.toFile() else {
            Paths.get(currentDirectory.toString() + File.separator + args.first()).toFile()
        }

        if (!lsDirectory.exists()) {
            writeError("ls: cannot access ${lsDirectory.name}: No such file or directory")
            return ExitCode.INVALID_ARGUMENTS
        } else if (lsDirectory.isFile) {
            writeLine(lsDirectory.name)
            return ExitCode.SUCCESS
        }

        for (file in lsDirectory.listFiles()) {
            writeLine(file.name)
        }

        return ExitCode.SUCCESS
    }
}