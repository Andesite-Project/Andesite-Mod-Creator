/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.varden.andesite.creator.gui.components;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Limits the input length of a JTextField.
 * @author Marius
 */
public class FieldCharLimiter extends PlainDocument {
    /**
     * The character limit.
     */
    private final int limit;
    
    /**
     * Constructs a limiter with the specified limit.
     * @param limit The limit of characters
     */
    public FieldCharLimiter(int limit) {
        super();
        this.limit = limit;
    }
    
    /**
     * Inserts a string to the Document.
     * @param offset Offset to insert the string into
     * @param str The string to insert
     * @param attr Attributes
     * @throws BadLocationException String cannot be inserted in this location
     */
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) return;
        if (getLength() + str.length() <= limit) {
            super.insertString(offset, str, attr);
        } else {
            super.insertString(offset, str.substring(0, limit - getLength()), attr);
        }
    }
}
