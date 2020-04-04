package DES;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class desencriptarDES {
    
    public desencriptarDES(){}

    public static void metodoDesencriptar(String llave, int modo, File entrada, File salida) throws Exception {
        FileInputStream finput = new FileInputStream(entrada);
        FileOutputStream fsalida = new FileOutputStream(salida);

        DESKeySpec dks = new DESKeySpec(llave.getBytes());

        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey sk = skf.generateSecret(dks);

        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        byte[] ivBytes = new byte[8];
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        cipher.init(Cipher.DECRYPT_MODE, sk, iv, SecureRandom.getInstance("SHA1PRNG"));
        CipherOutputStream cos = new CipherOutputStream(fsalida, cipher);
        write(finput, cos);

    }

    private static void write(InputStream input, OutputStream output) throws Exception {
        byte[] buffer = new byte[64];
        int numBytesRead;
        while ((numBytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, numBytesRead);
        }
        output.close();
        input.close();
    }

    public void desencriptar(){

        File encriptado = new File("encriptado.txt");
        File desencriptado = new File("desencriptado.txt");

        try {
            metodoDesencriptar("12345678", Cipher.DECRYPT_MODE, encriptado, desencriptado);
        } catch (Exception ex) {
            Logger.getLogger(desencriptarDES.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
