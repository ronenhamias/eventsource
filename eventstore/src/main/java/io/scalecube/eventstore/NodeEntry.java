package io.scalecube.eventstore;

public class NodeEntry {

  private long keyPos;
  private long valuePos;

  public NodeEntry(long keyPos, long valuePos) {
    this.keyPos = keyPos;
    this.valuePos = valuePos;
  }

  public long getKeyPos() {
    return keyPos;
  }

  public long getValuePos() {
    return valuePos;
  }

}
