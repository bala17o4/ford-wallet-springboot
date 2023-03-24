package com.application;

import com.application.wallet.WalletDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WalletControllerTest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void init(){
        this.restTemplate.postForObject("http://localhost:" + port + "/wallet",new WalletDto(1,"Bala123",25000.0,"bala@abc.com","Bala17us@1"),WalletDto.class);
    }
    @Test
    public void greetingTest() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Welcome to wallet application");
    }

    @Test
    public void getWalletByIdTest() throws Exception {
        WalletDto found =this.restTemplate.getForObject("http://localhost:" + port + "/wallet/1", WalletDto.class);
        assertEquals("Bala123",found.getName());
    }
    @Test
    public void getWalletExceptionTest() throws Exception{
        String exceptionMessage = this.restTemplate.getForObject("http://localhost:" + port + "/wallet/2", String.class);
        assertEquals("Wallet id not found",exceptionMessage);
    }
}
