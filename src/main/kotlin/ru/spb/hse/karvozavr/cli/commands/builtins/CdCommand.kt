package ru.spb.hse.karvozavr.cli.commands.builtins

import ru.spb.hse.karvozavr.cli.commands.Command
import ru.spb.hse.karvozavr.cli.shell.Shell
import ru.spb.hse.karvozavr.cli.streams.InStream
import ru.spb.hse.karvozavr.cli.streams.OutStream
import ru.spb.hse.karvozavr.cli.util.ExitCode
import java.io.File
import java.nio.file.Paths

class CdCommand (
    args: List<String>,
    inputStream: InStream,
    outStream: OutStream,
    errStream: OutStream,
    shell: Shell
) : Command(args, inputStream, outStream, errStream, shell) {

    override fun execute(): ExitCode {
        if (args.size > 1) {
            writeError("cd: too many arguments")
            return ExitCode.INVALID_ARGUMENTS
        }

        val currentDirectory = shell().environment().currentDir()
        val newDirectory = if (args.isEmpty()) "" else args.first()
        val cdDirectory = if (newDirectory.isEmpty()) Paths.get(System.getProperty("user.home")) else {
            currentDirectory.resolve(newDirectory).normalize()
        }

        if (cdDirectory != null) {
            if (!cdDirectory.toFile().exists()) {
                writeError("cd: $newDirectory: No such file or directory")
                return ExitCode.RESOURCE_NOT_FOUND
            }

            if (cdDirectory.toFile().isFile) {
                writeError("cd: $newDirectory: Not a directory")
                return ExitCode.INVALID_ARGUMENTS
            }

            shell().environment().changeDirectory(cdDirectory)
            return ExitCode.SUCCESS
        }

        return ExitCode.RESOURCE_NOT_FOUND
    }
}