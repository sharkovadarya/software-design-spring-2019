package ru.sbb.hse.karvozavr.cli.pipeline

import ru.sbb.hse.karvozavr.cli.Command

interface Pipeline {
    fun execute(): Int
}

object EmptyPipeline : Pipeline {
    override fun execute(): Int {
        return 0
    }
}

class SingleCommandPipeline(
    private val command: Command
) : Pipeline {
    override fun execute(): Int =
        command.execute()
}

class MultiCommandPipeline(
    private val pipeline: List<Command>
) : Pipeline {
    override fun execute(): Int {
        var err = 0
        pipeline.takeWhile {
            err = it.execute()
            err == 0
        }
        return err
    }
}
