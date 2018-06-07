package model;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
public class JarFileLoader 
{
	public JarFileLoader ()
	{
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  boolean load (String args )	{
		URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class clazz= URLClassLoader.class;
		// Use reflection
		try {
			Method method= clazz.getDeclaredMethod("addURL", new Class[] { URL.class });
			method.setAccessible(true);
			String  jarPath = args;   	
			File f = new File(jarPath);
			if(f.exists() == false){
				//throw new Exception("File [" + jarPath + "] doesn't exist!");
				return false;
			}
			Thread.currentThread().setContextClassLoader(classLoader);

			method.invoke(classLoader, new Object[] { f.toURI().toURL() });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;

	}
}