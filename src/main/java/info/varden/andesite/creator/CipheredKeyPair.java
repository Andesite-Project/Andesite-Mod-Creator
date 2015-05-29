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
package info.varden.andesite.creator;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Stores a public-private keypair for use in the Andesite Mod Creator when encrypting mods.
 * @author Marius
 */
public class CipheredKeyPair {
    private final PublicKey publicKey;
    private final byte[] cphPrivExp;
    private final byte[] salt;
    private final byte[] iv;
    private final int keylength;
    private String owner;
    
    /**
     * Ciphers a key pair.
     * @param kp The key pair to encrypt
     * @param password The password to encrypt with
     * @param keylength The key length of the AES encryption
     * @param owner The owner of the key pair
     * @throws NoSuchAlgorithmException The RSA algorithm does not exist on this machine
     * @throws InvalidKeySpecException The RSA key specification does not exist on this machine
     * @throws NoSuchPaddingException The RSA cipher does not exist on this machine
     * @throws InvalidKeyException The package's public key is invalid
     * @throws IllegalBlockSizeException The block size for the public key specification is invalid
     * @throws BadPaddingException The project data is not padded properly
     * @throws InvalidParameterSpecException The AES parameter specifications are not supported on this machine
     */
    public CipheredKeyPair(KeyPair kp, String password, int keylength, String owner) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException {
        this(kp.getPublic(), kp.getPrivate(), password, keylength, owner);
    }
    
    /**
     * Ciphers a key pair.
     * @param publicKey The public key of the key pair
     * @param privateKey The private key of the key pair
     * @param password The password to encrypt with
     * @param keylength The key length of the AES encryption
     * @param owner The owner of the key pair
     * @throws NoSuchAlgorithmException The RSA algorithm does not exist on this machine
     * @throws InvalidKeySpecException The RSA key specification does not exist on this machine
     * @throws NoSuchPaddingException The RSA cipher does not exist on this machine
     * @throws InvalidKeyException The package's public key is invalid
     * @throws IllegalBlockSizeException The block size for the public key specification is invalid
     * @throws BadPaddingException The project data is not padded properly
     * @throws InvalidParameterSpecException The AES parameter specifications are not supported on this machine
     */
    public CipheredKeyPair(PublicKey publicKey, PrivateKey privateKey, String password, int keylength, String owner) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec priv = kf.getKeySpec(privateKey, RSAPrivateKeySpec.class);
        this.salt = new byte[8];
        SecureRandom secRand = new SecureRandom();
        secRand.nextBytes(salt);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec ks = new PBEKeySpec(password.toCharArray(), salt, 65536, keylength);
        SecretKey tmp = skf.generateSecret(ks);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();
        this.iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        this.publicKey = publicKey;
        this.cphPrivExp = cipher.doFinal(priv.getPrivateExponent().toByteArray());
        this.keylength = keylength;
        this.owner = owner;
    }
    
    /**
     * Creates a CipheredKeyPair instance from an already ciphered key pair.
     * @param publicKey The public key of the key pair
     * @param cipheredPrivateExp The encrypted private key of the key pair
     * @param salt The AES key salt
     * @param iv The AES key initialization vectors
     * @param keylength The AES key length
     * @param owner The owner of the key
     */
    public CipheredKeyPair(PublicKey publicKey, byte[] cipheredPrivateExp, byte[] salt, byte[] iv, int keylength, String owner) {
        this.publicKey = publicKey;
        this.cphPrivExp = cipheredPrivateExp;
        this.salt = salt;
        this.iv = iv;
        this.keylength = keylength;
        this.owner = owner;
    }
    
    /**
     * Gets the public key of this key pair.
     * @return The public key
     */
    public PublicKey getPublicKey() {
        return this.publicKey;
    }
    
    /**
     * Decrypts and returns the private key of this key pair.
     * @param password The password to decrypt with
     * @return The private key
     * @throws NoSuchAlgorithmException The RSA algorithm does not exist on this machine
     * @throws InvalidKeySpecException The RSA key specification does not exist on this machine
     * @throws NoSuchPaddingException The RSA cipher does not exist on this machine
     * @throws InvalidKeyException The package's public key is invalid
     * @throws IllegalBlockSizeException The block size for the public key specification is invalid
     * @throws BadPaddingException The project data is not padded properly
     * @throws InvalidAlgorithmParameterException The AES parameter specifications are not supported on this machine
     */
    public PrivateKey getPrivateKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec ks = new PBEKeySpec(password.toCharArray(), salt, 65536, keylength);
        SecretKey tmp = skf.generateSecret(ks);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pubSpec = kf.getKeySpec(publicKey, RSAPublicKeySpec.class);
        RSAPrivateKeySpec privSpec = new RSAPrivateKeySpec(pubSpec.getModulus(), new BigInteger(cipher.doFinal(cphPrivExp)));
        return kf.generatePrivate(privSpec);
    }
    
    /**
     * Returns the encrypted private key.
     * @return The encrypted private key
     */
    public byte[] getCipheredPrivateExponent() {
        return this.cphPrivExp;
    }
    
    /**
     * Returns the AES salt.
     * @return The salt
     */
    public byte[] getSalt() {
        return this.salt;
    }
    
    /**
     * Returns the AES initialization vectors.
     * @return The initialization vectors
     */
    public byte[] getIV() {
        return this.iv;
    }
    
    /**
     * Returns the AES key length.
     * @return The AES key length
     */
    public int getAESKeyLength() {
        return this.keylength;
    }
    
    /**
     * Returns the RSA key length.
     * @return The RSA key length
     * @throws NoSuchAlgorithmException The RSA algorithm does not exist on this machine
     * @throws InvalidKeySpecException The RSA key specification does not exist on this machine
     */
    public int getRSAKeyLength() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pub = kf.getKeySpec(this.publicKey, RSAPublicKeySpec.class);
        return pub.getModulus().bitLength();
    }
    
    /**
     * Returns the owner of the key pair.
     * @return The owner
     */
    public String getOwner() {
        return this.owner;
    }
    
    /**
     * Sets the owner of the key pair.
     * @param owner The owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }
    
}
