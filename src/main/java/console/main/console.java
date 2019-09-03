package console.main;

import BTC.BTCAddressCheckRule;
import BTC.BTCAddressGenerateRule;
import BTC.KeyPairs;
import ETH.ETHAddressCheckRule;
import ETH.ETHAddressGenerateRule;
import ETH.ETHKeyPairs;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class console {


    public static void main(String[] args) {
        BTCAddressGenerateRule btcAddressGenerateRule = new BTCAddressGenerateRule();
        BTCAddressCheckRule btcAddressCheckRule = new BTCAddressCheckRule();
        KeyPairs btckeyPairs = new KeyPairs();
        String btcpubkey = null;
        try {
            btcpubkey = btckeyPairs.createKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        btcAddressGenerateRule.getAddress(btcpubkey);

        System.out.println("----------------------------------------ETH-------------------------------");
        ETHAddressGenerateRule ethAddressGenerateRule = new ETHAddressGenerateRule();
        ETHAddressCheckRule ethAddressCheckRule = new ETHAddressCheckRule();
        ETHKeyPairs ethkeyPairs = new ETHKeyPairs();
        String ethpubkey = null;
        try {
            ethpubkey = ethkeyPairs.createKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ethAddressGenerateRule.getAddress(ethpubkey));
    }
}