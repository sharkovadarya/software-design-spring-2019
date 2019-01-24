package ru.spb.hse.karvozavr.cli.pipeline

import ru.spb.hse.karvozavr.cli.commands.Command

class SingleCommandPipeline(
    private val command: Command
) : Pipeline {
    override fun execute(): Int =
        command.execute()
}