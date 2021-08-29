package com.example.kakaoapplication

import android.content.Context
import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
private const val FAKE_STRING = "KaKao Application"

@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

    @Mock
    private lateinit var context: Context

    @Test
    fun readStringFromContext() {
        `when`(context.getString(R.string.app_name)).thenReturn(FAKE_STRING)
        val result = context.getString(R.string.app_name)

        assertThat(result).isEqualTo(FAKE_STRING)

        `when`(context.packageName).thenReturn("com.example.kakaoapplication")
        val packageName = context.packageName
        assertThat(packageName).isEqualTo("com.example.kakaoapplication")
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}