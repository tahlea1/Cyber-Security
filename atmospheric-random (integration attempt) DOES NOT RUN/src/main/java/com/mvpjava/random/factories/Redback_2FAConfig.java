//submitted by Wing Hung Wu
//reference: https://medium.com/@ihorsokolyk/two-factor-authentication-with-java-and-google-authenticator-9d7ea15ffee6

package com.mvpjava.random.factories;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;

public class Redback_2FAConfig extends RandomJavaConfig {
    
    
    //generate a secret key
    AtmosphericRandom atmosphericRandom() {
        return new AtmosphericRandom(closeableHttpClient(), httpPostFactory(), randomMapper());
	}
    
  
    //generate a TOTP code based on the secret key
    // public static String getTOTPCode(String secretKey) {
    //     Base32 base32 = new Base32();
    //     byte[] bytes = base32.decode(secretKey);
    //     String hexKey = Hex.encodeHexString(bytes);
    //     return TOTP.getOTP(hexKey);
    // }
    
    //generate a Google Authenticator Bar Code
    public static String getGoogleAuthenticatorBarCode(AtmosphericRandom atmosphericRandom, String account, String issuer) {
        try {
            return "otpauth://totp/"
                    + URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(atmosphericRandom, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }
    //create the QR code based on the bar code
    public static void createQRCode(String barCodeData, String filePath, int height, int width)
            throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE,
                width, height);
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            MatrixToImageWriter.writeToStream(matrix, "png", out);
        }
    }

    public static void infinityGeneratingCodes(AtmosphericRandom atmosphericRandom) {
        String lastCode = null;
        while (true) {
            String code = getTOTPCode(atmosphericRandom);
            if (!code.equals(lastCode)) {
                System.out.println(code);
            }
            lastCode = code;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {};
        }
    }



}
