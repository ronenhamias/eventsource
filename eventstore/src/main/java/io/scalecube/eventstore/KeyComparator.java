package io.scalecube.eventstore;

public interface KeyComparator {
  int compare(long left, byte[] key);
}
