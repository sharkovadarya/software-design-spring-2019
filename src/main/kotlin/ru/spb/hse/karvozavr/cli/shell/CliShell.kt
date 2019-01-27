package ru.spb.hse.karvozavr.cli.shell

import ru.spb.hse.karvozavr.cli.shell.env.CliEnvironment
import ru.spb.hse.karvozavr.cli.shell.env.Directory
import ru.spb.hse.karvozavr.cli.shell.env.Environment
import java.nio.file.Paths

/**
 * Basic shell implementation.
 */
class CliShell(val environment: Environment) : Shell {

    companion object {
        fun emptyShell(): CliShell = CliShell(CliEnvironment(Directory(Paths.get(System.getProperty("user.dir")))))
    }

    private var terminated: Boolean = false;

    override fun environment(): Environment =
        environment

    override fun isTerminated(): Boolean =
        terminated

    override fun terminate() {
        terminated = true
    }
}