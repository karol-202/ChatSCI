package pl.karol202.chatsci.util

import kotlinx.coroutines.io.ByteWriteChannel
import kotlinx.coroutines.io.writeStringUtf8

suspend fun ByteWriteChannel.writeUTF8Line(line: String) = writeStringUtf8("$line\n")
