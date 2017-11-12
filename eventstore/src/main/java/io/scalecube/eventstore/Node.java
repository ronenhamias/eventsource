package io.scalecube.eventstore;

import java.util.Iterator;

public abstract class Node {

  protected final NodeDriver driver;
  protected final int pos;

  public Node(NodeDriver driver, int pos) {
    this.driver = driver;
    this.pos = pos;
  }

  public abstract Status put(byte[] key, byte[] value);

  public abstract Iterator<NodeEntry> findGte(byte[] key);

  public abstract Node split(byte[] key);

  protected void putKey(int index, Long key) {
    driver.putKey(pos, index, key);
  }

  protected long getKeyPos(int index) {
    return driver.getKey(pos, index);
  }

  protected long getValuePos(int index) {
    return driver.getValue(pos, index);
  }

  protected void putValue(int index, long value) {
    driver.putValue(pos, index, value);
  }

  protected int getKeyCount() {
    return driver.getNodeKeyCount(pos);
  }

  protected boolean isFull() {
    return driver.isNodeFull(pos);
  }

  protected boolean isEmpty() {
    return driver.isNodeEmpty(pos);
  }

  protected void moveKeyIndex(int index, int count) {
    driver.moveKeyIndex(pos, index, count);
  }

  protected void moveValueIndex(int index, int count) {
    driver.moveValueIndex(pos, index, count);
  }

  protected static int adjustIndex(int givenIndex) {
    if (givenIndex >= 0) {
      return givenIndex;
    }

    int val = Math.abs(givenIndex);
    if (givenIndex != -1) {
      return -(val - 2);
    }
    return -1;
  }

  protected void updateKeyCount(int keyCount) {
    driver.setNodeKeyCount(pos, keyCount);
  }
}
