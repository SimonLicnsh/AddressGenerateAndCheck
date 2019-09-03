package BTC;


import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


public  class KeyPairs {
    /*
    产生公钥私钥对
     */
    public byte[] createKey() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {

        byte[] J=new BigInteger("03",16).toByteArray();
        byte[] O=new BigInteger("02",16).toByteArray();
        BigInteger privKey = Keys.createEcKeyPair().getPrivateKey();

        BigInteger pubKey = Sign.publicKeyFromPrivate(privKey);
        ECKeyPair keyPair = new ECKeyPair(privKey, pubKey);

        System.out.println("privkey="+privKey.toString(16));
        System.out.println("pubkey="+pubKey.toString(16));
        String privkey16 = privKey.toString(16);
        String pubkey16=pubKey.toString(16);


        byte[] pubkeybyteX = new byte[32];
        byte[] privkeybyte = new BigInteger(privkey16,16).toByteArray();
        byte[] pubkeybyte =new BigInteger(pubkey16,16).toByteArray();
        String s  =pubKey.toString(2);
        if (pubkeybyte.length != 64) {

            System.arraycopy(pubkeybyte, 1, pubkeybyteX, 0, 32);
            System.out.println("pub::"+Utils.bytesToHexString(pubkeybyteX));
            byte[] pubkeybyteY=new byte[32];
            System.arraycopy(pubkeybyte,33,pubkeybyteY,0,32);
            System.out.println("YYY="+Utils.bytesToHexString(pubkeybyteY));
            System.out.println(s.substring(s.length()-1,s.length()));
            if (s.substring(s.length()-1,s.length()).equals("1")){
                pubkeybyteX=Utils.add(J,pubkeybyteX);
                System.out.println(Utils.bytesToHexString(pubkeybyteX));
            }else {
                pubkeybyteX=Utils.add(O,pubkeybyteX);
            }
//            extendprivkey = Utils.add(netid, privkeybytereal);
        } else {
            System.arraycopy(pubkeybyte, 0, pubkeybyteX, 0, 32);
            System.out.println("pubkeybyte=" + Utils.bytesToHexString(pubkeybyteX));
            byte[] pubkeybyteY=new byte[32];
            System.arraycopy(pubkeybyte,32,pubkeybyteY,0,32);
            System.out.println("YYY="+Utils.bytesToHexString(pubkeybyteY));
            if (s.substring(s.length()-1,s.length()).equals("1")){
                pubkeybyteX=Utils.add(J,pubkeybyteX);
                System.out.println(Utils.bytesToHexString(pubkeybyteX));
            }else {
                pubkeybyteX=Utils.add(O,pubkeybyteX);
            }
        }
                String pubkeyX=pubkeybyteX.toString();
//调用生成WIF格式私钥：这个地方出错，生成地WIF格式与导入之后生成的不同
//        WIFPrivateKey wifPrivateKey=new WIFPrivateKey();
//        wifPrivateKey.WIFPrivKey(privkey16);
//
        return pubkeybyteX;
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
