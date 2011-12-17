package org.wave.utils.file;

import java.io.File;

import org.wave.utils.enums.ErrorEnum;


public class FileUtil {

	public static boolean delete(File file) {
		if (file == null) {
			throw new IllegalArgumentException(ErrorEnum.NULL_FILE.getMessage());
		}

		if (file.isDirectory()) {
			String[] children = file.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = delete(new File(file, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		return file.delete();
	}

}
