package io.scalecube.eventstore;

import java.util.Iterator;

/**
 * represent leafNode of the BPlusTree. which contains all the key,value in sorted order.
 *
 * @author nuwansa
 */
public class LeafNode extends Node {

  public LeafNode(NodeDriver driver, int pos) {
    super(driver, pos);
  }

  @Override
  public Iterator<NodeEntry> findGte(byte[] key) {
    int keyCount = getKeyCount();
    if (keyCount == 0) {
      return new NodeIterator(null, 0);
    }
    int index = driver.binarySearch(pos, keyCount, key);
    if (index == -1) {
      index = 0;
    } else if (index < 0) {
      index = adjustIndex(index);
      index = Math.abs(index);
      index++;
    }
    return new NodeIterator(this, index);
  }

  @Override
  public Status put(byte[] key, byte[] value) {
    if (isFull()) {
      return Status.NODE_FULL;
    }
    int index;
    int keyCount = getKeyCount();
    if (isEmpty()) {
      index = 0;
    } else {
      index = driver.binarySearch(pos, keyCount, key);
      if (index >= 0) {
        return Status.KEY_EXIST;
      } else if (index == -1) {
        index = 0;
      } else {
        index = adjustIndex(index);
        index = Math.abs(index);
        index++;
      }
    }
    moveKeyIndex(index, keyCount);
    moveValueIndex(index, keyCount);
    putKey(index, driver.getKeyProvider().apply(key));
    putValue(index, driver.getValueProvider().apply(value));
    updateKeyCount(keyCount + 1);
    return Status.DONE;
  }

  @Override
  public Node split(byte[] key) {
    int index = driver.getMaxKeyCount() / 2;
    long pos = getKeyPos(index);
    int val = driver.getComparator().compare(pos, key);
    if (val >= 0) {
      index++;
    }
    return null;
  }

  /**
   * *
   * print leaf node internal structure.
   */
  public void print() {
    int index = 0;
    while (index < getKeyCount()) {
      System.out.print(getKeyPos(index) + ",");
      index++;
    }
    System.err.println("");
    index = 0;
    while (index < getKeyCount()) {
      System.out.print(getValuePos(index) + ",");
      index++;
    }
    System.err.println("");
  }
}
