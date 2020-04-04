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

public class encriptarDES {

    String mensaje;

    public encriptarDES(String mensaje) {
        this.mensaje = mensaje;
    }

    public static void metodoEncriptar(String llave, int modo, File entrada, File salida) throws Exception {
        FileInputStream finput = new FileInputStream(entrada);
        FileOutputStream fsalida = new FileOutputStream(salida);

        DESKeySpec dks = new DESKeySpec(llave.getBytes());

        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey sk = skf.generateSecret(dks);

        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        byte[] ivBytes = new byte[8];
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        cipher.init(Cipher.ENCRYPT_MODE, sk, iv, SecureRandom.getInstance("SHA1PRNG"));
        CipherInputStream cis = new CipherInputStream(finput, cipher);
        write(cis, fsalida);

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

    public void encriptar() {
        try ( Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("textoPlano.txt"), "utf-8"))) {
            writer.write(mensaje);
        } catch (Exception ex) {
            Logger.getLogger(encriptarDES.class.getName()).log(Level.SEVERE, null, ex);
        }

        File plain = new File("textoPlano.txt");
        File encriptado = new File("encriptado.txt");

        try {
            metodoEncriptar("12345678", Cipher.ENCRYPT_MODE, plain, encriptado);
        } catch (Exception ex) {
            Logger.getLogger(encriptarDES.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
