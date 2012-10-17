package com.mt.common.xml;

/**
 * This interface defines the methods that can be called
 * after the beginning of a start tag has been output, but it
 * (the start tag itself, not a terminating end tag) has been closed.
 */
public interface StartTagWAX extends ElementWAX {

    /**
     * @see com.mt.common.xml.WAX#attr(String, Object)
     */
    StartTagWAX attr(String name, Object value);

    /**
     * @see com.mt.common.xml.WAX#attr(String, String, Object)
     */
    StartTagWAX attr(String prefix, String name, Object value);

    /**
     * @see com.mt.common.xml.WAX#attr(boolean, String, String, Object)
     */
    StartTagWAX attr(
            boolean newLine, String prefix, String name, Object value);

    /**
     * @see com.mt.common.xml.WAX#namespace(String)
     */
    StartTagWAX namespace(String uri);

    /**
     * @see com.mt.common.xml.WAX#namespace(String, String)
     */
    StartTagWAX namespace(String prefix, String uri);

    /**
     * @see com.mt.common.xml.WAX#namespace(String, String, String)
     */
    StartTagWAX namespace(String prefix, String uri, String schemaPath);
}