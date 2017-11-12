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
public class NodeIterator implements Iterator<NodeEntry> {

  private LeafNode current;
  private int index;

  public NodeIterator(LeafNode node, int index) {
    this.current = node;
    this.index = index;
  }

  @Override
  public boolean hasNext() {
    if (current == null)
      return false;
    if (index < current.getKeyCount())
      return true;
    else
      return false;
  }

  @Override
  public NodeEntry next() {
    NodeEntry entry = new NodeEntry(current.getKeyPos(index), current.getValuePos(index));
    index++;
    return entry;
  }

}
