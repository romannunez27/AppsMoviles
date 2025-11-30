package com.example.pasteleriamilsaboresapp.viewmodel

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.example.pasteleriamilsaboresapp.data.model.QrResult
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class QrViewModelTest {

    // Executor síncrono para que LiveData no pida Looper ni hilo principal
    private val instantTaskExecutor = object : TaskExecutor() {
        override fun executeOnDiskIO(runnable: Runnable) {
            runnable.run()
        }

        override fun postToMainThread(runnable: Runnable) {
            runnable.run()
        }

        override fun isMainThread(): Boolean = true
    }

    @BeforeEach
    fun setUp() {
        // Reemplazamos el executor global usado por LiveData
        ArchTaskExecutor.getInstance().setDelegate(instantTaskExecutor)
    }

    @AfterEach
    fun tearDown() {
        // Restauramos comportamiento normal después de cada test
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

    @Test
    fun `onQrDetected actualiza qrResult con el contenido procesado`() {
        val viewModel = QrViewModel()

        viewModel.onQrDetected("ABC123")

        val value = viewModel.qrResult.value
        assertNotNull(value)
        assertEquals(QrResult("ABC123"), value)
    }

    @Test
    fun `clearResult deja qrResult en null`() {
        val viewModel = QrViewModel()

        viewModel.onQrDetected("XYZ")
        viewModel.clearResult()

        val value = viewModel.qrResult.value
        assertNull(value)
    }
}
