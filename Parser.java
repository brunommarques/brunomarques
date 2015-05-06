import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  
  public synchronized void setFile(File f) {
	file = f;
  }
  
  public synchronized File getFile() {
  	return file;
  }
  
  public String getContent() throws IOException {
	return getContentInternal(false);
  }
  
  
  public String getContentWithoutUnicode() throws IOException {
	return getContentInternal(true);
  }
  
  
  public String getContentInternal(boolean withoutUnicode) throws IOException {
	StringBuilder output = new StringBuilder();
	BufferedReader reader = null;
	
	try {
		reader = new BufferedReader(new FileReader(file));	  
		int size;
		char [] buf = new char[1000];
	    	while ((size = reader.read((buf))) > 0) {
	      		for (int i = 0; i < size; i++) {
	    	  	if (buf[i] < 0x80 || !withoutUnicode)
	    		  output.append(buf[i]);
	      }
	    }
	} finally {
	  	if (reader != null)
	  		reader.close();
	}
    	
	return output.toString();
  }
  
  
  public void saveContent(String content) throws IOException {
	  BufferedWriter writer = null;
	  
	  try {
		  writer = new BufferedWriter(new FileWriter(file));	  
		  writer.write(content);
	  } finally {
		  if (writer != null)
			  writer.close();
	  }
  }
}
