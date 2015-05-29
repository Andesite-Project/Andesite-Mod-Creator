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
package info.varden.andesite.crypto;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * Benchmarks the current system for cryptography time calculations.
 * @author Marius
 */
public final class CryptoBenchmark {
    /**
     * Determines the speed of RSA keypair generation.
     * @param keysize The size of the RSA keys to generate
     * @param iterations The number of RSA keys to generate
     * @return A set of benchmark results, index 0 being the fastest and index 1 being the slowest
     * @throws NoSuchAlgorithmException The RSA algorithm is not present on this machine
     */
    public static BenchmarkResults getRSAKeyGenSpeed(int keysize, int iterations) throws NoSuchAlgorithmException {
        long[] results = new long[iterations];
        for (int i = 0; i < iterations; i++) {
            long sTime = System.currentTimeMillis();
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(keysize);
            KeyPair kp = kpg.genKeyPair();
            long eTime = System.currentTimeMillis() - sTime;
            results[i] = eTime;
        }
        return new BenchmarkResults(results);
    }
    
    /**
     * Prepares for benchmarking.
     * @throws NoSuchAlgorithmException The RSA algorithm is not present on this machine
     */
    public static void initializeEncrypter() throws NoSuchAlgorithmException {
        getRSAKeyGenSpeed(512, 10);
    }
    
    /**
     * Converts the keygen results from one key size to another.
     * @param result The keygen result
     * @param bchKeysize The benchmarked key size
     * @param targetKeysize The target key size
     * @return Estimated keygen result for the target key size
     * @throws InvalidKeyException Key size must be a tuple of two between 512 and 8192
     */
    public static double getRSACalcConvKeysizeSeconds(double result, int bchKeysize, int targetKeysize) throws InvalidKeyException {
        double baseFactor;
        switch (bchKeysize) {
            case 512:
                baseFactor = result / 10.6;
                break;
            case 1024:
                baseFactor = result / 45.4;
                break;
            case 2048:
                baseFactor = result / 264.6;
                break;
            case 4096:
                baseFactor = result / 4592.8;
                break;
            case 8192:
                baseFactor = result / 61020.35;
                break;
            default:
                throw new InvalidKeyException("Key size invalid; bchKeysize must be tuple of two 512 <= bchKeysize <= 8192");
        }
        switch (targetKeysize) {
            case 512:
                return baseFactor * 10.6;
            case 1024:
                return baseFactor * 45.4;
            case 2048:
                return baseFactor * 264.6;
            case 4096:
                return baseFactor * 4592.8;
            case 8192:
                return baseFactor * 61020.35;
            default:
                throw new InvalidKeyException("Key size invalid; targetKeysize must be tuple of two 512 <= targetKeysize <= 8192");
        }
    }
    
    /**
     * Returns the time scaling factor for a given key size. Deprecated; please use getRSACalcConvKeysizeSeconds instead.
     * @param x The key size
     * @return The time scaling factor for this key size
     */
    @Deprecated
    private static double calcRSAMsecForXOnDev(double x) {
        return (0.000000000000003D * Math.pow(x, 5D)) + (0.000000000005376D * Math.pow(x, 4D)) - (0.000000140149253D * Math.pow(x, 3D)) + (0.000169057576429D * Math.pow(x, 2D)) + (0.036116245814768D * x) - 5.000000000000882D;
    }
}
