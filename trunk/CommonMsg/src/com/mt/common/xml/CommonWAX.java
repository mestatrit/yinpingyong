package com.mt.common.xml;

/**
 * This interface groups methods that are shared with
 * PrologueWAX and ElementWAX.
 */
public interface CommonWAX {

    /**
     * @see WAX#comment(String)
     */
    PrologOrElementWAX comment(String text);

    /**
     * @see WAX#processingInstruction(String, String)
     */
    PrologOrElementWAX processingInstruction(String target, String data);

    /**
     * @see WAX#start(String)
     */
    StartTagWAX start(String name);

    /**
     * @see WAX#start(String, String)
     */
    StartTagWAX start(String prefix, String name);
}