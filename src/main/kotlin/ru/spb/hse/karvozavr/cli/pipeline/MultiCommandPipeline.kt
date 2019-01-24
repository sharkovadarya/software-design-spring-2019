package ru.spb.hse.karvozavr.cli.pipeline

import ru.spb.hse.karvozavr.cli.commands.Command

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