package com.cdkj.coin.eth;


/**
 * Created by tianlei on 2017/十二月/27.
 */
public class ETHAddressFactory {

    // public static void main(String[] args) {
    //
    // try {
    //
    // // String addr = productAddress("password");
    // System.out.println(Version.getVersion());
    //
    // } catch (Exception e) {
    //
    // }
    //
    // }
    //
    // public static String productAddress(String password) throws Exception {
    //
    // ECKey key = new ECKey();
    //
    // byte[] addr = key.getAddress();
    // byte[] priv = key.getPrivKeyBytes();
    // byte[] pub = key.getPubKey();
    //
    // String addrBase16 = Hex.toHexString(addr);
    // String privBase16 = Hex.toHexString(priv);
    // String pubBase16 = Hex.toHexString(pub);
    //
    // // try {
    //
    // ECKeyPair ecKeyPair = ECKeyPair.create(key.getPrivKey());
    // WalletFile walletFile = Wallet.createStandard(password, ecKeyPair);
    //
    // ObjectMapper objectMapper = new ObjectMapper();
    // String jsonStr = objectMapper.writeValueAsString(walletFile);
    // String filename = getFileName(addrBase16);
    // String filePath = "/Users/haiqingzheng/Desktop/keystore" + "/"
    // + filename;
    //
    // //
    // saveFile(jsonStr, filePath);
    //
    // return "0x" + addrBase16;
    //
    // // } catch (Exception e) {
    // //
    // // System.out.print("创建WalletFile失败");
    // // return null;
    // //
    // // }
    //
    // }
    //
    // public static String getFileName(String address) {
    //
    // DateTimeFormatter format = DateTimeFormatter
    // .ofPattern("'UTC--'yyyy-MM-dd'T'HH-mm-ss.nVV'--'");
    // ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
    // return now.format(format) + address;
    // }
    //
    // public static void saveFile(String content, final String filePath) {
    // //
    // try {
    // File file = new File(filePath);
    // PrintStream printStream = new PrintStream(
    // new FileOutputStream(file));
    // printStream.print(content);
    //
    // } catch (Exception e) {
    //
    // throw new RuntimeException("失败");
    // }
    // }

}
