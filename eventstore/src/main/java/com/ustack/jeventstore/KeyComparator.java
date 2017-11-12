/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ustack.jeventstore;

/**
 *
 * @author nuwansa
 */
public interface KeyComparator {
    int compare(long left,byte[] key);
}
