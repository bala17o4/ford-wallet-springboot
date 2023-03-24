package com.application.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletJpaRepository walletJpaRepository;

    @Override
    public WalletDto registerWallet(WalletDto newWallet) {

        return this.walletJpaRepository.save(newWallet);
    }

    @Override
    public WalletDto getWalletById(Integer walletId) throws WalletException{
        Optional<WalletDto> foundWallet = this.walletJpaRepository.findById(walletId);
        if(foundWallet==null){
            throw new WalletException("Wallet id not found");
        }
        return foundWallet.get();
    }

//    @Override
//    public WalletDto getWalletByName(String walletName) throws WalletException{
//        Optional<WalletDto> foundWallet = this.walletJpaRepository.findById(walletName);
//        if(foundWallet==null){
//            throw new WalletException("Wallet name is not found");
//        }
//        return foundWallet.get();
//    }
//
    @Override
    public WalletDto updateWallet(WalletDto walletDto) throws WalletException{
        if(this.walletJpaRepository.findById(walletDto.getId()) == null){
            throw new WalletException("No such Wallet exists");
        }
        return this.walletJpaRepository.save(walletDto);
    }

    @Override
    public WalletDto deleteWalletById(Integer walletId) throws WalletException{
        Optional<WalletDto> foundWallet =this.walletJpaRepository.findById(walletId);
        if(foundWallet == null){
            throw new WalletException("No such Wallet Exists");
        }
        WalletDto wallet = foundWallet.get();
        this.walletJpaRepository.delete(wallet);
        return wallet;
    }

    @Override
    public Double addFundsToWalletById(Integer walletId, Double amountToBeAdded) throws WalletException{
        Optional<WalletDto> fundwallet = this.walletJpaRepository.findById(walletId);
        if(fundwallet == null){
            throw new WalletException("No such Wallet Exists");
        }else if(amountToBeAdded<=0){
            throw new WalletException("The amount to be added should be greater than 0");
        }
        Double newBalance = fundwallet.get().getBalance() + amountToBeAdded;
        fundwallet.get().setBalance(newBalance);
        this.walletJpaRepository.save(fundwallet.get());
        return newBalance;
    }

    @Override
    public Double withdrawFundsFromWalletById(Integer walletId, Double amount) throws WalletException{
        Optional<WalletDto> tempwallet = this.walletJpaRepository.findById(walletId);
        if(tempwallet == null){
            throw new WalletException("No such Wallet Exists");
        }else if(amount >= tempwallet.get().getBalance()){
            throw new WalletException("Insufficient balance. Current Balance: " + tempwallet.get().getBalance() );
        }
        Double withdrawAmount = tempwallet.get().getBalance();
        withdrawAmount -= amount;
        tempwallet.get().setBalance(withdrawAmount);
        this.walletJpaRepository.save(tempwallet.get());
        return withdrawAmount;
    }

    @Override
    public Boolean fundTransfer(Integer fromWalletid, Integer toWalletid, Double amountToTransfer) throws WalletException{
        Optional<WalletDto> fromWallet = this.walletJpaRepository.findById(fromWalletid);
        Optional<WalletDto> toWallet = this.walletJpaRepository.findById(toWalletid);

        if(fromWallet == null){
            throw new WalletException("From Wallet not exist");
        }else if(toWallet == null){
            throw new WalletException("To wallet not exist");
        } else if(amountToTransfer >= fromWallet.get().getBalance()){
            throw new WalletException("Insufficient Balance. Current Balance: " + fromWallet.get().getBalance());
        }

        fromWallet.get().setBalance(fromWallet.get().getBalance()-amountToTransfer);
        toWallet.get().setBalance(toWallet.get().getBalance()+amountToTransfer);
        this.walletJpaRepository.save(fromWallet.get());
        this.walletJpaRepository.save(toWallet.get());
        return true;
    }

    @Override
    public Collection<WalletDto> getAllWallets() {
        return this.walletJpaRepository.findAll();
    }
}
