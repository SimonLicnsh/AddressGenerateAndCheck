package BTC;


import org.apache.commons.codec.digest.DigestUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

public class BTCAddressCheckRule {

    private static final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private static final BigInteger ALPHABET_SIZE = BigInteger.valueOf(ALPHABET.length());

    /**
     * 将 Base58Check 字符串反转为 byte 数组
     *
     * @param s base58format address
     * @return decode address
     */
    static byte[] base58ToRawBytes(String s) {
        // Parse base-58 string
        BigInteger num = BigInteger.ZERO;
        for (int i = 0; i < s.length(); i++) {
            num = num.multiply(ALPHABET_SIZE);
//            System.out.println(num);
            int digit = ALPHABET.indexOf(s.charAt(i));
//            System.out.println(digit);
            if (digit == -1) {
                throw new IllegalArgumentException("Invalid character for Base58Check");
            }
            num = num.add(BigInteger.valueOf(digit));
        }
        // 删除可能的前导0
        byte[] b = num.toByteArray();
        if (b[0] == 0) {
            b = Arrays.copyOfRange(b, 1, b.length);
        }
        try {
            // 前导1转为前导0
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            for (int i = 0; i < s.length() && s.charAt(i) == ALPHABET.charAt(0); i++) {
                buf.write(0);
            }
            buf.write(b);
            byte[] bb=buf.toByteArray();
            return buf.toByteArray();
        } catch (IOException e) {
            throw new AssertionError(e);
        }

    }

    /**
     * 两次Hash
     * @param data 解码之后去掉校验码的部分
     * @return
     */
    public static byte[] doubleHash(byte[] data) {
        return DigestUtils.sha256(DigestUtils.sha256(data));
    }

    /**
    转字节数组并判断校验码
     */

    public static boolean checkAddress(String s) {
        byte[] concat = base58ToRawBytes(s);
        byte[] data = Arrays.copyOf(concat, concat.length - 4);
        byte[] checknum = Arrays.copyOfRange(concat, concat.length - 4, concat.length);
        byte[] rechecknum = Arrays.copyOf(doubleHash(data), 4);
        System.out.println(Utils.bytesToHexString(checknum));
        System.out.println(Utils.bytesToHexString(rechecknum));
        if (!Arrays.equals(rechecknum, checknum)) {

            throw new IllegalArgumentException("mischeck");
        }
        return true;
    }


}
