package ru.spb.hse.karvozavr.cli.pipeline

import ru.spb.hse.karvozavr.cli.commands.Command
import ru.spb.hse.karvozavr.cli.util.ExitCode

class SingleCommandPipeline(private val command: Command) : Pipeline {
    override fun execute(): ExitCode =
        command.execute()
}