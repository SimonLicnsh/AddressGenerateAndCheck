package ETH;


import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECPoint;


public  class ETHKeyPairs {
    public String createKey() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
//        第一种产生方法
//        BigInteger privKey = Keys.createEcKeyPair().getPrivateKey();
////      System.out.println(privKey);
//        BigInteger pubKey = Sign.publicKeyFromPrivate(privKey);
//        ECKeyPair keyPair = new ECKeyPair(privKey, pubKey);
//        System.out.println("Private key: " + privKey.toString(16));
//        System.out.println("Public key: " + pubKey.toString(16));
//        return pubKey.toString(16);
        //第二种生成方法
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
        keyGen.initialize(ecSpec);
        KeyPair kp = keyGen.generateKeyPair();
        PublicKey pub = kp.getPublic();
        PrivateKey pvt = kp.getPrivate();
        ECPrivateKey epvt = (ECPrivateKey) pvt;
        String sepvt = adjustTo64(epvt.getS().toString(16)).toUpperCase();
        System.out.println("s[" + sepvt.length() + "]: " + sepvt);
        ECPublicKey epub = (ECPublicKey)pub;
        ECPoint pt = epub.getW();
        String sx = adjustTo64(pt.getAffineX().toString(16)).toUpperCase();
        String sy = adjustTo64(pt.getAffineY().toString(16)).toUpperCase();
        String bcPub = "04" + sx + sy;
        String bcpubkey=sx+sy;
        System.out.println("bcPub: " + bcPub);
        return bcpubkey;
    }
    static private String adjustTo64(String s) {
        switch(s.length()) {
            case 62: return "00" + s;
            case 63: return "0" + s;
            case 64: return s;
            default:
                throw new IllegalArgumentException("not a valid key: " + s);
        }
    }

}
