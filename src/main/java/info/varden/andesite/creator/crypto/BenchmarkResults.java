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
package info.varden.andesite.creator.crypto;

/**
 * RSA keygen benchmark results.
 * @author Marius
 */
public class BenchmarkResults {
    /**
     * List of results.
     */
    private final long[] results;
    
    /**
     * Initializes the results.
     * @param results The results
     */
    protected BenchmarkResults(long[] results) {
        this.results = results;
    }
    
    /**
     * Returns the fastest recorded keygen time.
     * @return Keygen time in milliseconds
     */
    public long getFastestBenchmarkMillis() {
        long shortest = Long.MAX_VALUE;
        for (long result : results) {
            if (result < shortest) {
                shortest = result;
            }
        }
        return shortest;
    }
    
    /**
     * Returns the slowest recorded keygen time.
     * @return Keygen time in milliseconds
     */
    public long getSlowestBenchmarkMillis() {
        long longest = Long.MIN_VALUE;
        for (long result : results) {
            if (result > longest) {
                longest = result;
            }
        }
        return longest;
    }
    
    /**
     * Returns the average keygen time.
     * @return Keygen time in milliseconds
     */
    public double getAverageBenchmarkMillis() {
        double total = 0D;
        for (long result : results) {
            total += (double) result;
        }
        return total / (double) results.length;
    }
}
