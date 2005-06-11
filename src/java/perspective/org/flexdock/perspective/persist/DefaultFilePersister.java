/*
 * Created on May 30, 2005
 */
package org.flexdock.perspective.persist;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @author Christopher Butler
 */
public class DefaultFilePersister implements Persister {
	
	public PerspectiveModel load(InputStream in) throws IOException {
		if(in==null)
			return null;

		ObjectInputStream ois = null;
		try {
			ois = in instanceof ObjectInputStream? (ObjectInputStream)in:
					new ObjectInputStream(in);
			return (PerspectiveModel)ois.readObject();
		} catch(ClassNotFoundException e) {
			IOException ex = new IOException("Unable to unmarshall stored data.");
			ex.initCause(e);
			throw ex;
		}
		finally {
			if(ois!=null)
				ois.close();
		}
	}
	
	public boolean store(OutputStream out, PerspectiveModel info) throws IOException {
		if(info==null || out==null)
			return false;

		ObjectOutputStream oos = null;
		try {
			oos = out instanceof ObjectOutputStream? (ObjectOutputStream)out: 
						new ObjectOutputStream(out);
			oos.writeObject(info);
			return true;
		}
		finally {
			if(oos!=null)
				oos.close();
		}
	}

}
