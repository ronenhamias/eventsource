/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ustack.jeventstore;

import java.nio.ByteBuffer;

/**
 *
 * @author nuwansa
 */
public class ByteBufferUtil {
    
    public static int binarySearch(ByteBuffer buffer, int pos, int fromIndex, int toIndex, byte[] key,
            KeyComparator c) {

        int low = fromIndex;
        int high = toIndex - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            long val = getLong(buffer,pos, mid);
            int cmp = c.compare(val, key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid; // key found
            }
        }
        return -(low + 1);  // key not found.
    }
    
    private static long getLong(ByteBuffer buffer, int pos, int index) {
        return buffer.getLong(pos + (index * 8));
    }

}
