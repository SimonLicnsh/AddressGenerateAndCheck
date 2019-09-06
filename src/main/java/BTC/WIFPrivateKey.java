package BTC;

import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;

public class WIFPrivateKey {
    public void WIFPrivKey(String privkey){

        byte[] privkeybyte = new BigInteger(privkey,16).toByteArray();
        System.out.println("WIF中的pribkeybyte::"+Utils.bytesToHexString(privkeybyte));
        byte[] extendprivkey = new byte[65];
        byte[] netid = new byte[1];
         byte[] networkID = new BigInteger("80", 16).toByteArray();
        System.arraycopy(networkID, 1, netid, 0, 1);
        if (privkeybyte.length != 32) {
            byte[] privkeybytereal = new byte[32];
            System.arraycopy(privkeybyte, 1, privkeybytereal, 0, 32);
            System.out.println("privatekeyreal::"+Utils.bytesToHexString(privkeybytereal));
            extendprivkey = Utils.add(netid, privkeybytereal);
        } else {
            System.out.println("privkeybyte=" + Utils.bytesToHexString(privkeybyte));
            extendprivkey = Utils.add(netid, privkeybyte);
        }
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
    }
}
