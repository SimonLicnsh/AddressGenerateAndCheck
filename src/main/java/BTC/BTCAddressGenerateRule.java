package BTC;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;

import java.math.BigInteger;



public class BTCAddressGenerateRule {

/*
*
* 根据公钥获取BTC地址
* 1.对BTC进行sha256
* 2.进行ripemd运算
* 3.添加前缀网络id
* 4.进行两次sha256运算得到校验码
* 5.添加校验码
* 6.添加校验码后Base58编码得到地址
*/
    public static String getAddress(String publicKey){
        System.out.println(publicKey);
        byte[] pubckey = new BigInteger(publicKey, 16).toByteArray();
        System.out.println(pubckey);
        byte[] sha256Bytes = DigestUtils.sha256(pubckey);
        RIPEMD160Digest digest = new RIPEMD160Digest();
        digest.update(sha256Bytes, 0, sha256Bytes.length);
        byte[] ripemd160Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(ripemd160Bytes, 0);
        byte[] networkID = new BigInteger("00", 16).toByteArray();
        byte[] extendedRipemd160Bytes = Utils.add(networkID, ripemd160Bytes);
        byte[] twiceSha256Bytes = DigestUtils.sha256(DigestUtils.sha256(extendedRipemd160Bytes));
        byte[] checksum = new byte[4];
        System.arraycopy(twiceSha256Bytes, 0, checksum, 0, 4);
        System.out.println(Utils.bytesToHexString(checksum));
        byte[] binaryBitcoinAddressBytes = Utils.add(extendedRipemd160Bytes, checksum);
        String bitcoinAddress = Base58.encode(binaryBitcoinAddressBytes);
        System.out.println("bitcoinAddress=" + bitcoinAddress);
        return bitcoinAddress;
    }

}