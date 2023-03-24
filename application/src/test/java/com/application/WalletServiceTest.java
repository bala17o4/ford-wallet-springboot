package com.application;

import com.application.wallet.WalletDto;
import com.application.wallet.WalletException;
import com.application.wallet.WalletJpaRepository;
import com.application.wallet.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class WalletServiceTest {

    @Autowired
    private WalletService walletService;



    @Test
    void registerWalletTest(){
        assertEquals("Bala",walletService.registerWallet(new WalletDto(4,"Bala",15000.0,"bala@123.com","Bal@123ac" )).getName());
    }

    @Test
    void getWalletIdException(){
        assertThrows(NoSuchElementException.class, ()->this.walletService.getWalletById(45));
    }
    @Test
    void getWalletByIdTest() throws WalletException{
        assertEquals(15000, this.walletService.getWalletById(4).getBalance());
    }

    @Test
    void addFundsTest() throws WalletException{
        assertEquals(15500.0, this.walletService.addFundsToWalletById(4,500.0));
    }

    @Test
    void withdrawFundTest() throws WalletException{
        assertEquals(15000.0, this.walletService.withdrawFundsFromWalletById(4,500.0));
    }

    @Test
    void transferFundTest() throws WalletException{
        assertEquals(true, this.walletService.fundTransfer(1,0,1000.0));
    }

}
