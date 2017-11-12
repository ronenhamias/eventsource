/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose
 * Tools | Templates and open the template in the editor.
 */
package io.scalecube.eventstore;

import java.util.Iterator;

/**
 *
 * @author nuwansa
 */
public class IndexNode extends Node {

  public IndexNode(NodeDriver driver, int pos) {
    super(driver, pos);
  }

  public Node getLeftChild(int index) {
    long p = driver.getValue(pos, index);
    return driver.loadNode(pos);
  }

  public Node getRightChild(int index) {
    long p = driver.getValue(pos, index + 1);
    return driver.loadNode(p);
  }

  public void putLeftChildPos(int index, long val) {
    putValue(index, val);
  }

  public void putRightChildPos(int index, long val) {
    putValue(index + 1, val);
  }

  @Override
  public Status put(byte[] key, byte[] value) {
    int index = driver.binarySearch(pos, getKeyCount(), key);
    Node node;
    if (index == -1) {
      index = 0;
      node = getLeftChild(0);
    } else {
      index = Math.abs(adjustIndex(index));
      node = getRightChild(index);
    }
    return node.put(key, value);
  }

  @Override
  public Iterator<NodeEntry> findGTE(byte[] key) {
    int index = driver.binarySearch(pos, getKeyCount(), key);
    Node node;
    if (index == -1) {
      index = 0;
      node = getLeftChild(0);
    } else {
      index = Math.abs(adjustIndex(index));
      node = getRightChild(index);
    }
    return node.findGTE(key);
  }

  public Status put(byte[] key, long leftChildPos, long rightChildPos) {
    int keyCount = getKeyCount();
    if (isFull()) {
      return Status.NODE_FULL;
    }

    int index;
    if (keyCount == 0) {
      index = 0;
    } else {
      index = driver.binarySearch(pos, keyCount, key);
      if (index >= 0) {
        // TODO
      } else if (index == -1) {
        index = 0;
      } else {
        index = adjustIndex(index);
        index = Math.abs(index);
        index++;
      }
    }
    moveKeyIndex(index, keyCount);
    moveValueIndex(index, keyCount + 1);
    putKey(index, driver.getKeyProvider().apply(key));
    putLeftChildPos(index, leftChildPos);
    putRightChildPos(index, rightChildPos);
    updateKeyCount(keyCount + 1);
    return Status.DONE;
  }

  @Override
  public Node split(byte[] key) {
    throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools
                                                                   // | Templates.
  }

}
