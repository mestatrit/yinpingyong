package com.mt.common.xml;

/**
 * This interface defines the methods that can be called
 * while writing the prologue section of an XML document.
 */
public interface PrologWAX extends CommonWAX {

    /**
     * @see com.mt.common.xml.WAX#dtd(String)
     */
    PrologWAX dtd(String filePath);

    /**
     * @see com.mt.common.xml.WAX#entityDef(String, String)
     */
    PrologWAX entityDef(String name, String value);

    /**
     * @see com.mt.common.xml.WAX#externalEntityDef(String, String)
     */
    PrologWAX externalEntityDef(String name, String filePath);

    PrologWAX xslt(String filePath);
}
