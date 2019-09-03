package ETH;

import java.util.regex.Pattern;


public class ETHAddressCheckRule {
    static String address="0x001d3f1ef827552ae1114027bd3ecf1f086ba0f9";
    //ETH address: 0x+40位16进制数 不区分大小写
    static String pattern="^[0-9a-fA-F]{40}$";

    public static boolean isETHValidAddress(String address) {
        if (address.length()== 42 && address.substring(0,2).toUpperCase().equals("0X")) {
            address = address.substring(2);
        }
        Boolean isMatch= Pattern.matches(pattern,address);
        return  isMatch;
    }
















    public static void main(String[] args) {
        System.out.println(isETHValidAddress(address));
    }
}
