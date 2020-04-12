package com.maty.android.bookshelves

import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

/**
 * Utility method to run blocks on a dedicated background thread, used for io/database work.
 */
fun ioThread(f : () -> Unit) {
    val future = IO_EXECUTOR.submit(f)
    try {
        future.get()
    } catch (e : ExecutionException) {
        throw e.cause ?: e
    }
}