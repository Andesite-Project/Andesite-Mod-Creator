/*
 * The MIT License
 *
 * Copyright 2015 Marius.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package info.varden.andesite.io;

import info.varden.andesite.core.Action;
import info.varden.andesite.core.ActionData;
import info.varden.andesite.core.Actions;
import info.varden.andesite.core.AndesiteProject;
import info.varden.andesite.core.ProjectProperties;
import info.varden.andesite.core.Screenshot;
import info.varden.andesite.core.VariableVersionParser;
import info.varden.andesite.creator.CipheredKeyPair;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;

/**
 * Manages input and output functions for Andesite.
 * @author Marius
 */
public class AndesiteIO {
    /**
     * Writes a project to an output stream in the Andesite Unencrypted Project format. Stream is not closed.
     * @param proj The project instance
     * @param out The stream to write to
     * @throws IOException I/O operation fails
     * @throws UnsupportedEncodingException The UTF-8 charset does not exist
     */
    public static void save(AndesiteProject proj, OutputStream out) throws IOException, UnsupportedEncodingException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.write(new byte[] {0x41, 0x6E, 0x4D, 0x7D}, 0, 4);
        byte[] body = generateBody(proj);
        writeByteArray(body, dos);
    }
    
    /**
     * Writes a project to a file in the Andesite Unencrypted Project format.
     * @param proj The project instance
     * @param f The file to write to
     * @throws FileNotFoundException The specified file does not exist
     * @throws IOException I/O operation fails
     * @throws UnsupportedEncodingException The UTF-8 charset does not exist
     */
    public static void save(AndesiteProject proj, File f) throws FileNotFoundException, IOException, UnsupportedEncodingException {
        FileOutputStream fos = new FileOutputStream(f);
        save(proj, fos);
        fos.close();
    }
    
    /**
     * Reads a project from an input stream in the Andesite Unencrypted Project format. Stream is not closed.
     * @param in The stream to read from
     * @return A project instance
     * @throws IllegalArgumentException The stream does not contain a valid project in Andesite Unencrypted Project format or the constructor for an action is invalid
     * @throws IOException I/O operation fails
     * @throws InstantiationException Can not instantiate an action contained in the project stream
     * @throws IllegalAccessException The action class corresponding to an action stored in the project stream can not be accessed
     * @throws ClassCastException One of the actions in the project format stream is not an instance of Action
     * @throws InvocationTargetException The constructor for an action in the project format stream fails to invoke
     * @throws SecurityException An action cannot be read
     * @throws NoSuchMethodException An action does not support instantiation
     */
    public static AndesiteProject readProject(InputStream in) throws IllegalArgumentException, IOException, InstantiationException, IllegalAccessException, ClassCastException, InvocationTargetException, SecurityException, NoSuchMethodException {
        DataInputStream dis = new DataInputStream(in);
        byte[] magic = new byte[4];
        dis.read(magic, 0, 4);
        if (!Arrays.equals(magic, new byte[] {0x41, 0x6E, 0x4D, 0x7D})) {
            dis.close();
            throw new IllegalArgumentException("File is not a valid Andesite project file.");
        }
        byte[] body = readByteArray(dis);
        return readBody(body);
    }
    
    /**
     * Reads a project from a file in the Andesite Unencrypted Project format.
     * @param f The file to read from
     * @return A project instance
     * @throws FileNotFoundException The specified file does not exist
     * @throws IllegalArgumentException The stream does not contain a valid project in Andesite Unencrypted Project format or the constructor for an action is invalid
     * @throws IOException I/O operation fails
     * @throws InstantiationException Can not instantiate an action contained in the project stream
     * @throws IllegalAccessException The action class corresponding to an action stored in the project stream can not be accessed
     * @throws ClassCastException One of the actions in the project format stream is not an instance of Action
     * @throws InvocationTargetException The constructor for an action in the project format stream fails to invoke
     * @throws SecurityException An action cannot be read
     * @throws NoSuchMethodException An action does not support instantiation
     */
    public static AndesiteProject readProject(File f) throws FileNotFoundException, IllegalArgumentException, IOException, InstantiationException, IllegalAccessException, ClassCastException, InvocationTargetException, SecurityException, NoSuchMethodException {
        FileInputStream fis = new FileInputStream(f);
        AndesiteProject proj = readProject(fis);
        fis.close();
        return proj;
    }
    
    /**
     * Writes a project to an output stream in the Andesite Mod Package format. Stream is not closed.
     * @param proj The project instance
     * @param out The stream to write to
     * @param privateKey The private key used to encrypt the package
     * @param publicKey The public key used to decrypt the package
     * @throws IOException I/O operation fails
     * @throws UnsupportedEncodingException The UTF-8 charset does not exist
     * @throws InvalidKeySpecException The RSA key specification does not exist on this machine
     * @throws NoSuchAlgorithmException The RSA or SHA-512 algorithm does not exist on this machine
     * @throws NoSuchPaddingException The RSA cipher does not exist on this machine
     * @throws InvalidKeyException The supplied private key is invalid
     * @throws IllegalBlockSizeException The block size for the private key specification is invalid
     * @throws BadPaddingException The project data is not padded properly
     */
    public static void publish(AndesiteProject proj, OutputStream out, PrivateKey privateKey, PublicKey publicKey) throws IOException, UnsupportedEncodingException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.write(new byte[] {0x41, 0x6E, 0x4D, 0x7E}, 0, 4);
        byte[] body = generateBody(proj);
        
        MessageDigest hashmd = MessageDigest.getInstance("SHA-512");
        byte[] hash = hashmd.digest(body);
        
        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pub = kf.getKeySpec(publicKey, RSAPublicKeySpec.class);
        writeByteArray(pub.getModulus().toByteArray(), dos);
        writeByteArray(pub.getPublicExponent().toByteArray(), dos);
        
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] signedHash = cipher.doFinal(hash);
        
        writeByteArray(signedHash, dos);
        writeByteArray(body, dos);
    }
    
    /**
     * Writes a project to a file in the Andesite Mod Package format.
     * @param proj The project instance
     * @param f The file to write to
     * @param privateKey The private key used to encrypt the package
     * @param publicKey The public key used to decrypt the package
     * @throws FileNotFoundException The specified file does not exist
     * @throws IOException I/O operation fails
     * @throws UnsupportedEncodingException The UTF-8 charset does not exist
     * @throws InvalidKeySpecException The RSA key specification does not exist on this machine
     * @throws NoSuchAlgorithmException The RSA or SHA-512 algorithm does not exist on this machine
     * @throws NoSuchPaddingException The RSA cipher does not exist on this machine
     * @throws InvalidKeyException The supplied private key is invalid
     * @throws IllegalBlockSizeException The block size for the private key specification is invalid
     * @throws BadPaddingException The project data is not padded properly
     */
    public static void publish(AndesiteProject proj, File f, PrivateKey privateKey, PublicKey publicKey) throws FileNotFoundException, IOException, UnsupportedEncodingException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        FileOutputStream fos = new FileOutputStream(f);
        publish(proj, fos, privateKey, publicKey);
        fos.close();
    }
    
    /**
     * Reads a project from an input stream in the Andesite Mod Package format. Stream is not closed.
     * @param in The stream to read from.
     * @return A project instance
     * @throws IOException I/O operation fails
     * @throws IllegalArgumentException The stream does not contain a valid project in Andesite Mod Package format or the constructor for an action is invalid
     * @throws NoSuchAlgorithmException The RSA or SHA-512 algorithm does not exist on this machine
     * @throws InvalidKeySpecException The RSA key specification does not exist on this machine
     * @throws NoSuchPaddingException The RSA cipher does not exist on this machine
     * @throws InvalidKeyException The package's public key is invalid
     * @throws IllegalBlockSizeException The block size for the public key specification is invalid
     * @throws BadPaddingException The project data is not padded properly
     * @throws ClassCastException One of the actions in the project format stream is not an instance of Action
     * @throws InstantiationException Can not instantiate an action contained in the project stream
     * @throws IllegalAccessException The action class corresponding to an action stored in the project stream can not be accessed
     * @throws InvocationTargetException The constructor for an action in the project format stream fails to invoke
     * @throws SecurityException An action cannot be read
     * @throws NoSuchMethodException An action does not support instantiation
     * @throws SignatureException The signed hash provided in the package does not match the calculated body hash
     */
    public static AndesiteProject readSignedPackage(InputStream in) throws IOException, IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, ClassCastException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, SignatureException {
        DataInputStream dis = new DataInputStream(in);
        byte[] magic = new byte[4];
        dis.read(magic, 0, 4);
        if (!Arrays.equals(magic, new byte[] {0x41, 0x6E, 0x4D, 0x7E})) {
            dis.close();
            throw new IllegalArgumentException("File is not a valid Andesite project file.");
        }
        
        BigInteger mod = new BigInteger(readByteArray(dis));
        BigInteger exp = new BigInteger(readByteArray(dis));
        byte[] signedHash = readByteArray(dis);
        
        RSAPublicKeySpec pub = new RSAPublicKeySpec(mod, exp);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(pub);
        
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] hash = cipher.doFinal(signedHash);
        byte[] body = readByteArray(dis);
        
        MessageDigest hashmd = MessageDigest.getInstance("SHA-512");
        byte[] calcHash = hashmd.digest(body);
        
        if (!MessageDigest.isEqual(hash, calcHash)) {
            throw new SignatureException("Package is corrupt or has been tampered with");
        }
        
        return readBody(body);
    }
    
    /**
     * Reads a project from a file in the Andesite Mod Package format.
     * @param f The file to read from
     * @return A project instance
     * @throws FileNotFoundException The specified file does not exist
     * @throws IOException I/O operation fails
     * @throws IllegalArgumentException The stream does not contain a valid project in Andesite Mod Package format or the constructor for an action is invalid
     * @throws NoSuchAlgorithmException The RSA or SHA-512 algorithm does not exist on this machine
     * @throws InvalidKeySpecException The RSA key specification does not exist on this machine
     * @throws NoSuchPaddingException The RSA cipher does not exist on this machine
     * @throws InvalidKeyException The package's public key is invalid
     * @throws IllegalBlockSizeException The block size for the public key specification is invalid
     * @throws BadPaddingException The project data is not padded properly
     * @throws ClassCastException One of the actions in the project format stream is not an instance of Action
     * @throws InstantiationException Can not instantiate an action contained in the project stream
     * @throws IllegalAccessException The action class corresponding to an action stored in the project stream can not be accessed
     * @throws InvocationTargetException The constructor for an action in the project format stream fails to invoke
     * @throws SecurityException An action cannot be read
     * @throws NoSuchMethodException An action does not support instantiation
     * @throws SignatureException The signed hash provided in the package does not match the calculated body hash
     */
    public static AndesiteProject readSignedPackage(File f) throws FileNotFoundException, IOException, IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, ClassCastException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, SignatureException {
        FileInputStream fis = new FileInputStream(f);
        AndesiteProject proj = readSignedPackage(fis);
        fis.close();
        return proj;
    }
    
    /**
     * Generates the body for the binary project formats as a binary data array
     * @param proj The project instance
     * @return Binary data representing the body for the binary project formats
     * @throws UnsupportedEncodingException The UTF-8 charset does not exist
     * @throws IOException I/O operation fails
     */
    private static byte[] generateBody(AndesiteProject proj) throws UnsupportedEncodingException, IOException {
        Action[] actions = proj.getAllActions();
        
        int minAndesiteVersion = 1;
        for (Action action : actions) {
            ActionData data = action.getClass().getAnnotation(ActionData.class);
            if (data.version() > minAndesiteVersion) {
                minAndesiteVersion = data.version();
            }
        }
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        
        // Header data
        writeString(proj.author, dos);
        writeString(proj.properties.name, dos);
        writeString(proj.properties.modid, dos);
        writeString(proj.properties.version, dos);
        writeString(proj.properties.description, dos);
        dos.writeInt(minAndesiteVersion);
        
        if (proj.properties.hasIcon()) {
            ByteArrayOutputStream iconTemp = new ByteArrayOutputStream();
            ImageIO.write(proj.properties.icon, "PNG", iconTemp);
            iconTemp.close();
            writeByteArray(iconTemp.toByteArray(), dos);
            iconTemp.flush();
        } else {
            dos.writeInt(0);
        }
        
        // Screenshot data
        Screenshot[] screenshots = proj.getAllScreenshots();
        dos.writeInt(screenshots.length);
        for (Screenshot s : screenshots) {
            ByteArrayOutputStream scshSectTemp = new ByteArrayOutputStream();
            ByteArrayOutputStream scshTemp = new ByteArrayOutputStream();
            ImageIO.write(s.image, "PNG", scshTemp);
            scshTemp.close();
            scshSectTemp.write(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(scshTemp.size()).array(), 0, 4);
            scshSectTemp.write(scshTemp.toByteArray(), 0, scshTemp.size());
            scshTemp.flush();
            scshSectTemp.write(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(s.title.length()).array(), 0, 4);
            scshSectTemp.write(s.title.getBytes("UTF-8"), 0, s.title.length());
            scshSectTemp.write(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(s.caption.length()).array(), 0, 4);
            scshSectTemp.write(s.caption.getBytes("UTF-8"), 0, s.caption.length());
            scshSectTemp.close();
            writeByteArray(scshSectTemp.toByteArray(), dos);
            scshSectTemp.flush();
        }
        
        // Action data
        dos.writeInt(actions.length);
        for (Action a : actions) {
            Actions.writeToOutput(a, dos);
        }
        
        dos.close();
        byte[] binary = baos.toByteArray();
        
        return binary;
    }
    
    /**
     * Reads the body from a binary data array in one of the binary project formats
     * @param body Binary data representing the body for one of the binary project formats
     * @return A project instance
     * @throws IOException I/O operation fails
     * @throws InstantiationException Can not instantiate an action contained in the project stream
     * @throws IllegalAccessException The action class corresponding to an action stored in the project stream can not be accessed
     * @throws IllegalArgumentException The constructor for an action is invalid
     * @throws InvocationTargetException The constructor for an action in the project format stream fails to invoke
     * @throws ClassCastException One of the actions in the project format stream is not an instance of Action
     * @throws SecurityException An action cannot be read
     * @throws NoSuchMethodException An action does not support instantiation
     */
    private static AndesiteProject readBody(byte[] body) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassCastException, SecurityException, NoSuchMethodException {
        // Header data
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(body));
        String author = readString(dis);
        String modName = readString(dis);
        String modID = readString(dis);
        String modVersion = readString(dis);
        String description = readString(dis);
        dis.skip(4); // Min version number
        int iconSize = dis.readInt();
        BufferedImage icon = null;
        if (iconSize > 0) {
            byte[] iconData = new byte[iconSize];
            dis.read(iconData, 0, iconSize);
            ByteArrayInputStream iconInput = new ByteArrayInputStream(iconData);
            icon = ImageIO.read(iconInput);
            iconInput.close();
        }
        
        AndesiteProject proj = new AndesiteProject(new ProjectProperties(modName, modID, description, modVersion, icon));
        proj.author = author;
        
        // Screenshot data
        int scCount = dis.readInt();
        
        for (int i = 0; i < scCount; i++) {
            // Forward compatibility - new fields in the future?
            byte[] blockData = readByteArray(dis);
            DataInputStream scDis = new DataInputStream(new ByteArrayInputStream(blockData));
            byte[] scData = readByteArray(scDis);
            
            ByteArrayInputStream scInput = new ByteArrayInputStream(scData);
            BufferedImage scImage = ImageIO.read(scInput);
            String title = readString(scDis);
            String desc = readString(scDis);
            proj.addScreenshot(new Screenshot(scImage, title, desc));
        }
        
        // Action data
        int acCount = dis.readInt();
        
        for (int i = 0; i < acCount; i++) {
            Action act = Actions.readFromInput(dis);
            if (act == null) {
                proj.hasSupportIssues = true;
                continue;
            }
            if (act instanceof VariableVersionParser) {
                if (!((VariableVersionParser) act).isSupported()) {
                    proj.hasSupportIssues = true;
                }
            }
            proj.addAction(act);
        }
        
        dis.close();
        return proj;
    }
    
    /**
     * Reads a string from a data input stream containing data in one of the binary project formats
     * @param dis The stream to read from
     * @return The read string
     * @throws IOException Fails to read data from the stream
     */
    public static String readString(DataInputStream dis) throws IOException {
        return new String(readByteArray(dis));
    }
    
    /**
     * Writes a string to a data output stream containing data in one of the binary project formats
     * @param s The string to write
     * @param dos The stream to write to
     * @throws UnsupportedEncodingException The UTF-8 charset does not exist
     * @throws IOException Fails to read data from the stream
     */
    public static void writeString(String s, DataOutputStream dos) throws UnsupportedEncodingException, IOException {
        writeByteArray(s.getBytes("UTF-8"), dos);
    }
    
    /**
     * Reads a byte array from a data input stream containing data in one of the binary project formats
     * @param dis The stream to read from
     * @return The read byte array
     * @throws IOException Fails to read data from the stream
     */
    public static byte[] readByteArray(DataInputStream dis) throws IOException {
        int len = dis.readInt();
        byte[] data = new byte[len];
        dis.read(data, 0, len);
        return data;
    }
    
    /**
     * Writes a byte array to a data output stream containing data in one of the binary project formats
     * @param array The array to write
     * @param dos The stream to write to
     * @throws IOException Fails to read data from the stream
     */
    public static void writeByteArray(byte[] array, DataOutputStream dos) throws IOException {
        dos.writeInt(array.length);
        dos.write(array, 0, array.length);
    }
    
    /**
     * Reads an encrypted private-public RSA keypair database from an input stream. Stream is not closed.
     * @param in The stream to read from
     * @return The keypair database
     * @throws IOException I/O operation fails
     * @throws NoSuchAlgorithmException The RSA algorithm does not exist on this machine
     * @throws InvalidKeySpecException The RSA key specification does not exist on this machine
     */
    public static HashMap<String, CipheredKeyPair> readKeybase(InputStream in) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        DataInputStream dis = new DataInputStream(in);
        HashMap<String, CipheredKeyPair> keys = new HashMap<String, CipheredKeyPair>();
        int keyNum = dis.readInt();
        for (int i = 0; i < keyNum; i++) {
            String keyName = readString(dis);
            String owner = readString(dis);
            int keyLength = dis.readInt();
            BigInteger mod = new BigInteger(readByteArray(dis));
            BigInteger pubExp = new BigInteger(readByteArray(dis));
            byte[] cphPrivExp = readByteArray(dis);
            byte[] salt = readByteArray(dis);
            byte[] iv = readByteArray(dis);
            
            RSAPublicKeySpec pub = new RSAPublicKeySpec(mod, pubExp);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            keys.put(keyName, new CipheredKeyPair(kf.generatePublic(pub), cphPrivExp, salt, iv, keyLength, owner));
        }
        return keys;
    }
    
    /**
     * Reads an encrypted private-public RSA keypair database from a file.
     * @param f The file to read from
     * @return The keypair database
     * @throws FileNotFoundException The specified file does not exist.
     * @throws IOException I/O operation fails
     * @throws NoSuchAlgorithmException The RSA algorithm does not exist on this machine
     * @throws InvalidKeySpecException The RSA key specification does not exist on this machine
     */
    public static HashMap<String, CipheredKeyPair> readKeybase(File f) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        FileInputStream fis = new FileInputStream(f);
        HashMap<String, CipheredKeyPair> keybase = readKeybase(fis);
        fis.close();
        return keybase;
    }
    
    /**
     * Writes an encrypted private-public RSA keypair database to an output stream. Stream is not closed.
     * @param keys The keys to write
     * @param out The output stream to write to
     * @throws IOException I/O operation fails
     * @throws NoSuchAlgorithmException The RSA algorithm does not exist on this machine
     * @throws InvalidKeySpecException The RSA key specification does not exist on this machine
     */
    public static void writeKeybase(HashMap<String, CipheredKeyPair> keys, OutputStream out) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeInt(keys.size());
        Set<Entry<String, CipheredKeyPair>> keyPairs = keys.entrySet();
        for (Entry<String, CipheredKeyPair> keyPair : keyPairs) {
            writeString(keyPair.getKey(), dos);
            CipheredKeyPair pair = keyPair.getValue();
            writeString(pair.getOwner(), dos);
            dos.writeInt(pair.getAESKeyLength());
            
            KeyFactory kf = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec pub = kf.getKeySpec(pair.getPublicKey(), RSAPublicKeySpec.class);
            writeByteArray(pub.getModulus().toByteArray(), dos);
            writeByteArray(pub.getPublicExponent().toByteArray(), dos);
            
            writeByteArray(pair.getCipheredPrivateExponent(), dos);
            writeByteArray(pair.getSalt(), dos);
            writeByteArray(pair.getIV(), dos);
        }
    }
    
    /**
     * Writes an encrypted private-public RSA keypair database to a file.
     * @param keys The keys to write
     * @param f The file to write to
     * @throws FileNotFoundException The specified file does not exist.
     * @throws IOException I/O operation fails
     * @throws NoSuchAlgorithmException The RSA algorithm does not exist on this machine
     * @throws InvalidKeySpecException The RSA key specification does not exist on this machine
     */
    public static void writeKeybase(HashMap<String, CipheredKeyPair> keys, File f) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        FileOutputStream fos = new FileOutputStream(f);
        writeKeybase(keys, fos);
        fos.close();
    }
}
