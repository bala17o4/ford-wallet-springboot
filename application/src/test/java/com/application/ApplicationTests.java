package com.application;

import com.application.wallet.WalletDto;
import com.application.wallet.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private WalletService walletService;
	@Test
	void registerWalletTests() {
		WalletDto wallet = new WalletDto(3,"Bala123",2345.99,"email@email.com","avc@12355");
		assertEquals(2345.99,this.walletService.registerWallet(wallet).getBalance());
	}

}
