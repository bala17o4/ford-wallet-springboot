//package com.application;
//import com.application.wallet.WalletException;
//import com.application.wallet.WalletJpaRepository;
//import com.application.wallet.WalletService;
//import com.application.wallet.WalletDto;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import static org.mockito.BDDMockito.given;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//public class WalletmockTest {
//    @Autowired
//    private WalletService walletService;
//
//    @MockBean
//    private WalletJpaRepository walletRepository;
//
//    @Test
//    public void testServiceWithOutActualRepository() throws  WalletException {
//        given(this.walletRepository.getWalletById(100))
//                .willReturn(new WalletDto(100,"Ford", 2500.0,"ford@ford.com"));
//        assertEquals("Ford",walletService.getWalletById(100).getName());
//    }
//
//    @Test
//    public void testGetWalletThrowsExceptionTest() throws WalletException{
//
//        given(this.walletRepository.getWalletById(200))
//                .willReturn(null);
//        assertThrows(WalletException.class,()->walletService.getWalletById(200));
//    }
//
//    @Test
//    public void addFundsToWalletTest() throws WalletException{
//
//        given(this.walletRepository.getWalletById(200))
//                .willReturn(new WalletDto(200,"Ford 1",2000.0, "ford1@ford.com"));
//        given(this.walletRepository.getWalletById(400))
//                .willReturn(new WalletDto(400,"Ford 2",4000.0, "ford2@ford.com"));
//
//        Double newBalance=this.walletService.addFundsToWalletById(200,250.0);
//        assertEquals(2250.0,newBalance);
//    }
//
//    @Test
//    public void addFundsToWalletException() throws WalletException{
//        given(this.walletRepository.getWalletById(200))
//                .willReturn(new WalletDto(200,"Ford 1",2000.0, "ford1@ford.com"));
//        given(this.walletRepository.getWalletById(400))
//                .willReturn(new WalletDto(400,"Ford 2",4000.0, "ford2@ford.com"));
//        assertThrows(WalletException.class,()->this.walletService.addFundsToWalletById(300,2500.0));
//    }
//
//    @Test
//    public void withdrawFundsFromWalletTest() throws WalletException{
//
//        given(this.walletRepository.getWalletById(200))
//                .willReturn(new WalletDto(200,"Ford 1",2000.0,"ford1ford.com"));
//        given(this.walletRepository.getWalletById(400))
//                .willReturn(new WalletDto(400,"Ford 2",4000.0,"ford2@ford.com"));
//
//        Double newBalance=this.walletService.withdrawFundsFromWalletById(200,250.0);
//        assertEquals(1750.0,newBalance);
//    }
//    @Test
//    public void withdrawFundsFromWalletInsufficientFundExceptionTest() throws WalletException{
//
//        given(this.walletRepository.getWalletById(200))
//                .willReturn(new WalletDto(200,"Ford 1",2000.0,"ford1@ford.com"));
//        given(this.walletRepository.getWalletById(400))
//                .willReturn(new WalletDto(400,"Ford 2",4000.0,"ford2@ford.com"));
//
//
//        assertThrows(WalletException.class,()->this.walletService.withdrawFundsFromWalletById(200,2500.0));
//    }
//    @Test
//    public void withdrawFundsFromWalletInsufficientFundExceptionMessageTest() throws WalletException{
//
//        given(this.walletRepository.getWalletById(200))
//                .willReturn(new WalletDto(200,"Ford 1",2020.0,"ford1@ford.com"));
//        given(this.walletRepository.getWalletById(400))
//                .willReturn(new WalletDto(400,"Ford 2",4000.0,"ford2@ford.com"));
//        String eMessage="";
//        try{
//            this.walletService.withdrawFundsFromWalletById(200,2500.0);
//        }
//        catch (WalletException e){
//            eMessage=e.getMessage();
//        }
//        assertEquals("Insufficient balance. Current Balance: 2020.0",eMessage);
//    }
//
//    @Test
//    public void addFundsExceptionMessageTest() throws WalletException{
//        given(this.walletRepository.getWalletById(200))
//                .willReturn(new WalletDto(200,"Ford 1",2020.0,"ford1@ford.com"));
//        given(this.walletRepository.getWalletById(400))
//                .willReturn(new WalletDto(400,"Ford 2",4000.0,"ford2@ford.com"));
//        String eMessage="";
//        try{
//            this.walletService.addFundsToWalletById(200,0.0);
//        }
//        catch (WalletException e){
//            eMessage=e.getMessage();
//        }
//        assertEquals("The amount to be added should be greater than 0",eMessage);
//    }
//}
