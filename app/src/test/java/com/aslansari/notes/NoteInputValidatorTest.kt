package com.aslansari.notes

import com.aslansari.notes.util.NoteInputValidator
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NoteInputValidatorTest {

    @Test
    fun `should not validate url with spaces`() {
        val url = "http:// via.placeholder.com/300x200"
        assert(!NoteInputValidator.validateURL(url))
    }

    @Test
    fun `should validate complete url`() {
        val url = "https://www.via.placeholder.com/300x200"
        assert(NoteInputValidator.validateURL(url))
    }

    @Test
    fun `should validate url without www`() {
        val url = "https://via.placeholder.com/300x200"
        assert(NoteInputValidator.validateURL(url))
    }

    @Test
    fun `should not validate url with missing tld`() {
        val url = "https://placeholder/300x200"
        assert(!NoteInputValidator.validateURL(url))
    }

    @Test
    fun `should validate url without path`() {
        val url = "http://via.placeholder.com"
        assert(NoteInputValidator.validateURL(url))
    }

    @Test
    fun `should not validate url with protocol other than http or https`() {
        val url = "tcp://via.placeholder.com"
        assert(!NoteInputValidator.validateURL(url))
    }

    @Test
    fun `should not validate url with numbers in tld`() {
        val url = "tcp://via.placeholder.co3"
        assert(!NoteInputValidator.validateURL(url))
    }
}
