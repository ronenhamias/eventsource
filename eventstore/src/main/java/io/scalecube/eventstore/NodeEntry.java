/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.scalecube.eventstore;

/**
 *
 * @author nuwansa
 */
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
