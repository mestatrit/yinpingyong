package com.mt.common.xml;

/**
 * This interface defines the methods that can be called
 * after the beginning of a start tag has been output, but
 * before it has been terminated with an end tag or the shorthand way (/>).
 */
public interface ElementWAX extends CommonWAX {

    /**
     * @see WAX#blankLine()
     */
    ElementWAX blankLine();

    /**
     * @see WAX#cdata(String)
     */
    ElementWAX cdata(String text);

    /**
     * @see WAX#child(String, String)
     */
    ElementWAX child(String name, String text);

    /**
     * @see WAX#child(String, String, String)
     */
    ElementWAX child(String prefix, String name, String text);

    /**
     * @see WAX#close()
     */
    void close();

    /**
     * @see WAX#end()
     */
    ElementWAX end();

    /**
     * @see WAX#nlText(String)
     */
    ElementWAX nlText(String text);

    /**
     * @see WAX#text(String)
     */
    ElementWAX text(String text);

    /**
     * @see WAX#text(String, boolean, boolean)
     */
    ElementWAX text(String text, boolean newLine, boolean escape);
}