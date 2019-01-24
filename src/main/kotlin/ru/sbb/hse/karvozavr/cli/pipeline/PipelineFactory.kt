package ru.sbb.hse.karvozavr.cli.pipeline

import ru.sbb.hse.karvozavr.cli.Command
import ru.sbb.hse.karvozavr.cli.CommandNode
import ru.sbb.hse.karvozavr.cli.builtins.CommandFactory
import ru.sbb.hse.karvozavr.cli.streams.InStream
import ru.sbb.hse.karvozavr.cli.streams.OutStream
import ru.sbb.hse.karvozavr.cli.streams.ReadWriteStream

object PipelineFactory {

    fun createPipeline(
        commands: List<CommandNode>,
        inStream: InStream,
        outStream: OutStream,
        errStream: OutStream
    ): Pipeline = when (commands.size) {
        0 -> EmptyPipeline
        1 -> SingleCommandPipeline(
            CommandFactory.createCommand(
                commands.first().name,
                commands.first().args,
                inStream,
                outStream,
                errStream
            )
        )
        else -> createMultiCommandPipeline(
            commands,
            inStream,
            outStream,
            errStream
        )
    }

    private fun createMultiCommandPipeline(
        commands: List<CommandNode>,
        inStream: InStream,
        outStream: OutStream,
        errStream: OutStream
    ): MultiCommandPipeline {
        val pipeline = mutableListOf<Command>()
        var bufferStream = ReadWriteStream()
        var cmd = CommandFactory.createCommand(
            commands.first().name,
            commands.first().args,
            inStream,
            bufferStream,
            errStream
        )
        pipeline.add(cmd)

        for (i in 1 until (commands.size - 1)) {
            val outBufferStream = ReadWriteStream()
            cmd = CommandFactory.createCommand(
                commands[i].name,
                commands[i].args,
                bufferStream,
                outBufferStream,
                errStream
            )
            pipeline.add(cmd)
            bufferStream = outBufferStream
        }

        cmd = CommandFactory.createCommand(
            commands.last().name,
            commands.last().args,
            bufferStream,
            outStream,
            errStream
        )
        pipeline.add(cmd)

        return MultiCommandPipeline(pipeline)
    }
}