package io.scalecube.eventstore;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class NodeDriverManager {

  private int maxKeyCount;
  private final Function<byte[], Long> keyProvider;
  private final Function<byte[], Long> valueProvider;
  private final int mainSegmentLen;
  private final int keySegmentLen;
  private final int nodeTypeOffset;
  private final KeyComparator comparator;
  private final int nodeSizeInBytes;
  private final int nodeDriverSizeInBytes;
  protected final Map<Integer, NodeDriver> mapDrivers = new HashMap<>();

  /**
   * abstract layer for ByteBuffer manager.
   * @param nodeDriverSizeInBytes memory map max segment size
   * @param nodeSizeInBytes buffer max size of the node
   * @param keyProvider    keyProvider
   * @param valueProvider  valueProvider
   * @param comparator     comparator
   */
  public NodeDriverManager(int nodeDriverSizeInBytes, int nodeSizeInBytes, Function<byte[], Long> keyProvider,
      Function<byte[], Long> valueProvider, KeyComparator comparator) {
    this.nodeDriverSizeInBytes = nodeDriverSizeInBytes;
    this.nodeSizeInBytes = nodeSizeInBytes;
    validateSize(nodeSizeInBytes);
    this.maxKeyCount = calculateMaxKeyCount(nodeSizeInBytes);
    this.keyProvider = keyProvider;
    this.valueProvider = valueProvider;
    this.keySegmentLen = maxKeyCount * 8;
    this.mainSegmentLen = keySegmentLen + 8;
    this.nodeTypeOffset = keySegmentLen + 2;
    this.comparator = comparator;
  }

  public abstract NodeDriver getNodeDriver(long pos);

  public abstract Node createNode();

  public int getMaxKeyCount() {
    return maxKeyCount;
  }

  public Function<byte[], Long> getKeyProvider() {
    return keyProvider;
  }

  public Function<byte[], Long> getValueProvider() {
    return valueProvider;
  }

  public int getMainSegmentLen() {
    return mainSegmentLen;
  }

  public int getKeySegmentLen() {
    return keySegmentLen;
  }

  public int getNodeTypeOffset() {
    return nodeTypeOffset;
  }

  public KeyComparator getComparator() {
    return comparator;
  }

  public int getNodeSizeInBytes() {
    return nodeSizeInBytes;
  }

  private void validateSize(int nodeSizeInBytes) {
    if (nodeSizeInBytes % 8 != 0) {
      throw new NodeException("invalid node size :  " + nodeSizeInBytes);
    }
  }

  private int calculateMaxKeyCount(int nodeSizeInBytes) {
    int slots = nodeSizeInBytes / 8;
    if (slots < 12) {
      throw new NodeException("node size is less than miniumum size");
    }
    int slots2 = slots - 5;
    if (slots2 % 2 != 1) {
      throw new NodeException("invalid node size");
    }

    return slots2 / 2;
  }

  public int getNodeDriverSizeInBytes() {
    return nodeDriverSizeInBytes;
  }


}
