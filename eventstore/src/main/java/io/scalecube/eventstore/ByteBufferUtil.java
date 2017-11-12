package io.scalecube.eventstore;

import java.nio.ByteBuffer;

public class ByteBufferUtil {

    /**
     * binary search on byte array.
     * @param buffer bytebuffer that hold sorted data
     * @param pos   starting position of the buffer
     * @param fromIndex starting index
     * @param toIndex   end index
     * @param key   search key
     * @param comparator comparator
     * @return possible place that key should be
     */
  public static int binarySearch(ByteBuffer buffer, int pos, int fromIndex, int toIndex, byte[] key,
      KeyComparator comparator) {

    int low = fromIndex;
    int high = toIndex - 1;

    while (low <= high) {
      int mid = (low + high) >>> 1;
      long val = getLong(buffer, pos, mid);
      int cmp = comparator.compare(val, key);
      if (cmp < 0) {
        low = mid + 1;
      } else if (cmp > 0) {
        high = mid - 1;
      } else {
        return mid; // key found
      }
    }
    return -(low + 1); // key not found.
  }

  private static long getLong(ByteBuffer buffer, int pos, int index) {
    return buffer.getLong(pos + (index * 8));
  }

}
