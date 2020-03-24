package com.github.wpanas.timeout;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import com.github.wpanas.timeout.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Bean;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureWireMock(port = 0, httpsPort = 0, files = "src/test/resources/__files")
class WebClientTest {

    private static Logger log = LoggerFactory.getLogger(WebClientTest.class);

    @Autowired
    WebClient underTest;

    @Autowired
    Stubber stubber;

    @Test
    void shouldRespondForGetJustFine() {
        String actual = underTest.get();

        assertEquals("Everything seems fine!", actual);
    }

    @TestConfiguration
    static class WebClientTestConfiguration {
        @Bean
        Stubber stubber(WireMockServer wireMockServer) {
            return new Stubber(wireMockServer);
        }

//        @Bean
//        Stubber stubberWithoutDeps() {
//            return new Stubber();
//        }
    }

    static class Stubber {
        Stubber() {
            log.info("Setting up GET");
            stubFor(
                    get(urlEqualTo("/resources"))
                            .willReturn(
                                    aResponse().withBody("Everything seems fine!")
                            )
            );
        }

        Stubber(WireMockServer wireMockServer) {
            log.info("Setting up GET");
            wireMockServer.stubFor(
                    get(urlEqualTo("/resources"))
                            .willReturn(
                                    aResponse().withBody("Everything seems fine!")
                            )
            );
        }
    }
}