package com.se330.imageeditor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class SimpleAction extends AbstractAction{
    public SimpleAction(String name)
    {
            putValue( Action.NAME, "Action " + name );
            
    }

    public void actionPerformed(ActionEvent e)
    {
        System.out.println( getValue( Action.NAME ) );
    }
}
