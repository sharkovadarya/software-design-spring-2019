package ru.spb.hse.karvozavr.cli.pipeline

import ru.spb.hse.karvozavr.cli.commands.Command
import ru.spb.hse.karvozavr.cli.util.ExitCode

class MultiCommandPipeline(
    private val pipeline: List<Command>
) : Pipeline {
    override fun execute(): ExitCode {
        var err = ExitCode.SUCCESS
        pipeline.takeWhile {
            err = it.execute()
            err == ExitCode.SUCCESS
        }
        return err
    }
}