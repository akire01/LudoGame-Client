/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.erika.javafx.chat;

import java.util.Hashtable;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;

/**
 *
 * @author ER
 */
public class InitialDirContextCloseable extends InitialDirContext implements AutoCloseable{
     public InitialDirContextCloseable(Hashtable<?, ?> environment) throws NamingException {
        super(environment);
    }
}
