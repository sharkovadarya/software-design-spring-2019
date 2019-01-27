package ru.spb.hse.karvozavr.cli.pipeline

import ru.spb.hse.karvozavr.cli.util.ExitCode

object EmptyPipeline : Pipeline {
    override fun execute(): ExitCode =
        ExitCode.SUCCESS
}