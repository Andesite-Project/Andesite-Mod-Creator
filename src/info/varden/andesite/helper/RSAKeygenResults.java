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
package info.varden.andesite.helper;

import info.varden.andesite.creator.CipheredKeyPair;

/**
 * Results for RSA key generation.
 * @author Marius
 */
public class RSAKeygenResults {
    /**
     * The encrypted RSA key pair.
     */
    private CipheredKeyPair pair;
    /**
     * An exception that occurred during key generation, if any.
     */
    private Exception ex;
    /**
     * The message associated with the exception.
     */
    private String message;
    
    /**
     * Initializes RSA keygen results after a successful key generation.
     * @param pair The generated key pair
     */
    public RSAKeygenResults(CipheredKeyPair pair) {
        this.pair = pair;
    }
    
    /**
     * Initializes RSA keygen results after a failed key generation.
     * @param ex The occurring exception
     * @param message A message describing the problem
     */
    public RSAKeygenResults(Exception ex, String message) {
        this.ex = ex;
        this.message = message;
    }
    
    /**
     * Returns whether or not the generation failed.
     * @return True if failed; false otherwise
     */
    public boolean isError() {
        return this.pair == null;
    }
    
    /**
     * Gets the generated key pair.
     * @return The key pair
     */
    public CipheredKeyPair getKeyPair() {
        return this.pair;
    }
    
    /**
     * Gets the exception in case of an error.
     * @return The exception
     */
    public Exception getException() {
        return this.ex;
    }
    
    /**
     * Gets the error message in case of an error.
     * @return The error message
     */
    public String getMessage() {
        return this.message;
    }
}
