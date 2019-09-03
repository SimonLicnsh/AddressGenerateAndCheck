package ETH;

import org.web3j.crypto.Hash;
public class ETHAddressGenerateRule {

    public String getAddress(String pubkey){

        String data= Hash.sha3(pubkey);
        final  int start=data.length()-40;
        final int end=data.length();
        String address=data.substring(start,end);
        String finaladdress="0x"+address;
        return finaladdress;
    }








}
