package ru.spb.hse.karvozavr.cli.pipeline

object EmptyPipeline : Pipeline {
    override fun execute(): Int {
        return 0
    }
}