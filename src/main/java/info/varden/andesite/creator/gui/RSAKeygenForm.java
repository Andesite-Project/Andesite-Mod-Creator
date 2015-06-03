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
package info.varden.andesite.creator.gui;

import info.varden.andesite.crypto.CipheredKeyPair;
import info.varden.andesite.creator.crypto.RSAKeygenResults;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author Marius
 */
public abstract class RSAKeygenForm extends ProgressWindow<RSAKeygenResults> {
    
    private final String password;
    private final int aesKeylen;
    private final int rsaKeylen;

    public RSAKeygenForm(JFrame parent, JDialog parent1, final int rsaKeylen, final int aesKeylen, final String password) {
        super(parent, parent1, "Generating keypair...", new String[] {
            "Generating keypair. This can take several minutes...",
            "Encrypting private key..."
        });
        this.password = password;
        this.aesKeylen = aesKeylen;
        this.rsaKeylen = rsaKeylen;
    }
    
    @Override
    public Task getTask() {
        return new Task() {
            
            @Override
            public RSAKeygenResults run() {
                try {
                    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                    kpg.initialize(rsaKeylen);
                    KeyPair kp = kpg.genKeyPair();
                    nextStep();
                    CipheredKeyPair ckp = new CipheredKeyPair(kp, password, aesKeylen, "");
                    return new RSAKeygenResults(ckp);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(RSAKeygenForm.class.getName()).log(Level.SEVERE, null, ex);
                    return new RSAKeygenResults(ex, "RSA algorithm not installed on this machine! (0x0401)");
                } catch (InvalidKeySpecException ex) {
                    Logger.getLogger(RSAKeygenForm.class.getName()).log(Level.SEVERE, null, ex);
                    return new RSAKeygenResults(ex, "Key specification is invalid! Please try another password or change AES key size. (0x0402)");
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(RSAKeygenForm.class.getName()).log(Level.SEVERE, null, ex);
                    return new RSAKeygenResults(ex, "AES algorithm not installed on this machine! (0x0403)");
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(RSAKeygenForm.class.getName()).log(Level.SEVERE, null, ex);
                    return new RSAKeygenResults(ex, "Key is invalid! Please try another password or change AES key size. (0x0404)");
                } catch (InvalidParameterSpecException ex) {
                    Logger.getLogger(RSAKeygenForm.class.getName()).log(Level.SEVERE, null, ex);
                    return new RSAKeygenResults(ex, "Failed to retrieve initialization vector! Please try another password or change AES key size. (0x0405)");
                } catch (IllegalBlockSizeException ex) {
                    Logger.getLogger(RSAKeygenForm.class.getName()).log(Level.SEVERE, null, ex);
                    return new RSAKeygenResults(ex, "Failed to encrypt private key! Please try another password or change AES key size. (0x0406)");
                } catch (BadPaddingException ex) {
                    Logger.getLogger(RSAKeygenForm.class.getName()).log(Level.SEVERE, null, ex);
                    return new RSAKeygenResults(ex, "Failed to encrypt private key! Please try another password or change AES key size. (0x0407)");
                }
            }
            
        };
    }
}
