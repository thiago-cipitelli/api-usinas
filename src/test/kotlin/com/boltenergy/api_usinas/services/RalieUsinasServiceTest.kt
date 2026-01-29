import com.boltenergy.api_usinas.services.RalieUsinas
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class RalieUsinasServiceTest {

    @MockK
    lateinit var webClient: WebClient

    @MockK
    lateinit var requestHeadersUriSpec: WebClient.RequestHeadersUriSpec<*>

    @MockK
    lateinit var requestHeadersSpec: WebClient.RequestHeadersSpec<*>

    @MockK
    lateinit var responseSpec: WebClient.ResponseSpec

    lateinit var service: RalieUsinas

    @BeforeEach
    fun setup() {
        service = RalieUsinas(webClient)

        every { webClient.get() } returns requestHeadersUriSpec
        every { requestHeadersUriSpec.uri(any<String>()) } returns requestHeadersSpec
        every { requestHeadersSpec.retrieve() } returns responseSpec
    }

    @Test
    fun `deve baixar e salvar o arquivo`() {
        val conteudo = "a,b\n1,2".toByteArray()

        every {
            responseSpec.bodyToMono(ByteArray::class.java)
        } returns Mono.just(conteudo)

        val path = Path.of("tmp/ralie-usinas.csv")

        Files.deleteIfExists(path)

        service.downloadFile()

        assertTrue(Files.exists(path))
        assertEquals(conteudo.toList(), Files.readAllBytes(path).toList())
    }
}