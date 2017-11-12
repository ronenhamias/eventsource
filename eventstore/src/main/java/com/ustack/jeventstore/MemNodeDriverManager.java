/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ustack.jeventstore;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author nuwansa
 */
public class MemNodeDriverManager extends NodeDriverManager{

    private Map<Integer,NodeDriver> mapDrivers  = new HashMap<>();
    private int nodeCount = 0;
    public MemNodeDriverManager(int nodeDriverSizeInBytes, int nodeSizeInBytes, Function<byte[], Long> keyProvider, Function<byte[], Long> valueProvider, KeyComparator comparator) {
        super(nodeDriverSizeInBytes, nodeSizeInBytes, keyProvider, valueProvider, comparator);
    }

    @Override
    public NodeDriver getNodeDriver(long pos) {
        return mapDrivers.computeIfAbsent((int)(pos/getNodeDriverSizeInBytes()),this::create);
    }
    
    
    private NodeDriver create(int index)
    {
        return new NodeDriver(this, index, ByteBuffer.allocateDirect(getNodeDriverSizeInBytes()));
    }

    @Override
    public Node createNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
