package com.application.wallet;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;

@RestController
@CrossOrigin(value = "http://localhost:4200/")
public class WalletControler {

    @Autowired
    private WalletService walletService;
    @Autowired
    private JwtUtil jwtUtil;


    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String greet(){
        return "Welcome to wallet application";
    }

    @PostMapping("/wallet")
    @ResponseStatus(value = HttpStatus.CREATED)
    public WalletDto addWallet(@Valid @RequestBody WalletDto wallet) throws WalletException{
        return this.walletService.registerWallet(wallet);
    }

    @GetMapping("/wallet/{id}")
    public WalletDto getWalletById(@PathVariable Integer id) throws WalletException{
        return this.walletService.getWalletById(id);
    }
//
//    @GetMapping("/wallet/{name}")
//    public WalletDto getByName(@PathVariable String name) throws WalletException{
//        return this.walletService.getWalletByName(name);
//    }

    @PutMapping("/updatewallet")
    public WalletDto updateWallet(@RequestBody WalletDto wallet) throws WalletException{
        return this.walletService.updateWallet(wallet);
    }

    @DeleteMapping("/deletewallet/{walletid}")
    public WalletDto deleteWallet(@PathVariable Integer walletid) throws WalletException{
        return this.walletService.deleteWalletById(walletid);
    }

    @PutMapping("/addfund/{id}/{amount}")
    public Double addFunds(@PathVariable Integer id,@PathVariable Double amount) throws WalletException{
        return this.walletService.addFundsToWalletById(id,amount);
    }
    @Autowired
    private WalletJpaRepository walletJpaRepository;
    @PostMapping("/login")
    public WalletDto loginAccount(@RequestBody LoginDto loginDto, HttpServletResponse response) throws Exception{
        WalletDto wallet = this.walletJpaRepository.findByName(loginDto.getName());
        if(wallet == null) throw new Exception("No such User");
        if(! wallet.getPassword().equals(loginDto.getPassword())){
            throw new Exception("User password does not match");
        }
        String issuer = loginDto.getName();
        Date expiry = new Date(System.currentTimeMillis() + (1000 * 60 * 60));
        String jwt = Jwts.builder().setIssuer(issuer).setExpiration(expiry).signWith(SignatureAlgorithm.HS256,"secretKey").compact();
        Cookie cookie = new Cookie("jwt",jwt);
        wallet.setJwt(jwt);
        response.addCookie(cookie);
        return wallet;
    }

    @PostMapping("logout")
    public String logout(HttpServletResponse response){
        Cookie cookie = new Cookie("jwt","");
        response.addCookie(cookie);
        return "Logout Success !";
    }

    @GetMapping("/userinfo")
    public WalletDto getUserInfo(@RequestHeader("Authorization") String bearerToken ) throws Exception {
        String jwt = bearerToken.substring(7);
        String name = jwtUtil.validateJwtAndGetUserName(jwt);
        return this.walletJpaRepository.findByName(name);

    }

    @PutMapping("/withdraw/{id}/{amount}")
    public Double withdrawFunds(@PathVariable Integer id, @PathVariable Double amount) throws WalletException{
        return this.walletService.withdrawFundsFromWalletById(id,amount);
    }

    @PatchMapping("/transfer/{id}/{toId}/{amount}")
    public Boolean transferFunds(@PathVariable Integer id,@PathVariable Integer toId,@PathVariable Double amount) throws WalletException{
        return walletService.fundTransfer(id, toId, amount);
    }
//
    @GetMapping("/wallets")
    public Collection<WalletDto> getAllWallets(){
        return this.walletService.getAllWallets();
    }
}
