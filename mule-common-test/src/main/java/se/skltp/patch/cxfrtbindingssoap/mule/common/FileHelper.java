package se.skltp.patch.cxfrtbindingssoap.mule.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.common.classloader.ClassLoaderUtils;

public class FileHelper {

	public static String getFileFromClasspath(String filename) {
		try {
			InputStream is = ClassLoaderUtils.getResourceAsStream(filename,
					FileHelper.class);
			StringWriter sw = new StringWriter();
			IOUtils.copy(is, sw, "UTF-8");
			return sw.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
