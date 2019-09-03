package BTC;


import org.apache.commons.codec.digest.DigestUtils;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Sign;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECPoint;


public  class KeyPairs {
    /*
    产生公钥私钥对
     */
    public String createKey() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
        //使用ECDSA算法生成四要对
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
        keyGen.initialize(ecSpec);
        KeyPair kp = keyGen.generateKeyPair();
        PublicKey pub = kp.getPublic();
        PrivateKey pvt = kp.getPrivate();
        ECPrivateKey epvt = (ECPrivateKey) pvt;
        String sepvt = adjustTo64(epvt.getS().toString(16)).toUpperCase();
        System.out.println("s[" + sepvt.length() + "]: " + sepvt);
        ECPublicKey epub = (ECPublicKey) pub;
        ECPoint pt = epub.getW();
        String sx = adjustTo64(pt.getAffineX().toString(16)).toUpperCase();
        String sy = adjustTo64(pt.getAffineY().toString(16)).toUpperCase();
        String bcPub = "04" + sx + sy;
        System.out.println("bcPub: " + bcPub);
        //转BigInteger类型
        BigInteger privKey = new BigInteger(sepvt, 16);
        BigInteger pubKey = Sign.publicKeyFromPrivate(privKey);
        String privkey16 = privKey.toString(16);
        System.out.println("Private key: " + privKey.toString(16));
        System.out.println("Public key: " + pubKey.toString(16));
        byte[] privkeybyte = new BigInteger(privkey16, 16).toByteArray();
        System.out.println(Utils.bytesToHexString(privkeybyte));
        byte[] extendprivkey = new byte[65];
        byte[] netid = new byte[1];
        byte[] networkID = new BigInteger("80", 16).toByteArray();
        System.arraycopy(networkID, 1, netid, 0, 1);
        if (privkeybyte.length != 32) {
            byte[] privkeybytereal = new byte[32];
            System.arraycopy(privkeybyte, 1, privkeybytereal, 0, 32);
            System.out.println(Utils.bytesToHexString(privkeybytereal));
            extendprivkey = Utils.add(netid, privkeybytereal);
        } else {
            System.out.println("privkeybyte=" + Utils.bytesToHexString(privkeybyte));
            extendprivkey = Utils.add(netid, privkeybyte);
        }
        //转WIF钱包导入格式私钥
        System.out.println("networkID=" + Utils.bytesToHexString(netid));
        System.out.println("extendprivkey=" + Utils.bytesToHexString(extendprivkey));
        byte[] hash = DigestUtils.sha256(DigestUtils.sha256(extendprivkey));
        System.out.println("hash=" + Utils.bytesToHexString(hash));
        byte[] checksum = new byte[4];
        System.arraycopy(hash, 0, checksum, 0, 4);
        System.out.println("checksum=" + Utils.bytesToHexString(checksum));
        byte[] binaryprivkey = Utils.add(extendprivkey, checksum);
        System.out.println("binaryprivkey=" + Utils.bytesToHexString(binaryprivkey));
        String WIFprivkey = Base58.encode(binaryprivkey);
        System.out.println(WIFprivkey);
        return String.valueOf(pubKey);
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
