package se.skltp.patch.cxfrtbindingssoap.mule.common;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import org.apache.cxf.common.classloader.ClassLoaderUtils;

/**
 * Helps to selectively load the patch for testing, with the unpatched class
 * also present on the classpath.
 * 
 * @author hakan
 */
public class PatchClassLoaderHelper {
	private static final String PATCH_JAR_FILE_NAME_WITHOUT_VERSION = "patch-cxf-rt-bindings-soap";
	private static final String PATCH_CLASS_FILE = "org/apache/cxf/binding/soap/interceptor/SoapActionInInterceptor.class";
	private static final String PATCH_CLASS_NAME = "org.apache.cxf.binding.soap.interceptor.SoapActionInInterceptor";

	/**
	 * Must be called before any other actions that might load the
	 * patched/unpatched class without an active selection.
	 */
	public static Class loadPatchedClass(Class callingClazz) {

		List<URL> urls = ClassLoaderUtils.getResources(PATCH_CLASS_FILE,
				callingClazz);
		URL foundUrl = null;
		for (URL url : urls) {
			log("found URL: " + url);
			if (url.toString().contains(PATCH_JAR_FILE_NAME_WITHOUT_VERSION)) {
				foundUrl = url;
				break;
			}
		}

		if (foundUrl == null) {
			throw new RuntimeException("could not load patch");
		}

		log("will load class from: " + foundUrl);
		ClassLoader cl = URLClassLoader.newInstance(new URL[] { foundUrl });
		try {
			Class c = cl.loadClass(PATCH_CLASS_NAME);
			return c;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("failed to load class", e);
		}
	}

	private static void log(String msg) {
		System.out.println("### "
				+ PatchClassLoaderHelper.class.getSimpleName() + ": " + msg);
	}
}
