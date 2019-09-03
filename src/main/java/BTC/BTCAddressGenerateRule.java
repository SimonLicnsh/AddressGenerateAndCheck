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
    public static String getAddress(String pubkey){

        byte[] publicKey = new BigInteger(pubkey, 16).toByteArray();
        byte[] sha256Bytes = DigestUtils.sha256(publicKey);
        System.out.println("sha256加密=" + Utils.bytesToHexString(sha256Bytes));
        RIPEMD160Digest digest = new RIPEMD160Digest();
        digest.update(sha256Bytes, 0, sha256Bytes.length);
        byte[] ripemd160Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(ripemd160Bytes, 0);

        System.out.println("ripemd160加密=" + Utils.bytesToHexString(ripemd160Bytes));

        byte[] networkID = new BigInteger("00", 16).toByteArray();
        byte[] extendedRipemd160Bytes = Utils.add(networkID, ripemd160Bytes);

        System.out.println("添加NetworkID=" + Utils.bytesToHexString(extendedRipemd160Bytes));

        byte[] twiceSha256Bytes = DigestUtils.sha256(DigestUtils.sha256(extendedRipemd160Bytes));

        System.out.println("两次sha256加密=" + Utils.bytesToHexString(twiceSha256Bytes));

        byte[] checksum = new byte[4];

        System.arraycopy(twiceSha256Bytes, 0, checksum, 0, 4);
        System.out.println(Utils.bytesToHexString(checksum));
        System.out.println("checksum=" + Utils.bytesToHexString(checksum));

        byte[] binaryBitcoinAddressBytes = Utils.add(extendedRipemd160Bytes, checksum);

        System.out.println("添加checksum之后=" + Utils.bytesToHexString(binaryBitcoinAddressBytes));

        String bitcoinAddress = Base58.encode(binaryBitcoinAddressBytes);
        System.out.println("bitcoinAddress=" + bitcoinAddress);
        return bitcoinAddress;
    }

}