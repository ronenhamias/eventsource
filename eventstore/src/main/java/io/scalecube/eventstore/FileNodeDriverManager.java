/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.scalecube.eventstore;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nuwansa
 */
public class FileNodeDriverManager extends NodeDriverManager{

    private Map<Integer,NodeDriver> mapDrivers  = new HashMap<>();
    private int nodeCount = 0;
    
    public FileNodeDriverManager(int nodeDriverSizeInBytes, int nodeSizeInBytes, Function<byte[], Long> keyProvider, Function<byte[], Long> valueProvider, KeyComparator comparator) {
        super(nodeDriverSizeInBytes, nodeSizeInBytes, keyProvider, valueProvider, comparator);
    }

    @Override
    public NodeDriver getNodeDriver(long pos) {
       return mapDrivers.computeIfAbsent((int)(pos/getNodeDriverSizeInBytes()),this::create);
    }

    private NodeDriver create(int index)
    {
        try {
            RandomAccessFile file = new RandomAccessFile("E:/btree/"+index, "rw");
            file.setLength(getNodeDriverSizeInBytes());
            MappedByteBuffer buffer = file.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, getNodeDriverSizeInBytes());
            return new NodeDriver(this, index, buffer);
        } catch (Exception ex) {
            Logger.getLogger(FileNodeDriverManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public Node createNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
