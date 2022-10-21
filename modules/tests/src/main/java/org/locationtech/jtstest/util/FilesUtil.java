/*
 * Copyright (c) 2017 Martin Davis.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v20.html
 * and the Eclipse Distribution License is available at
 *
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */
package org.locationtech.jtstest.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FilesUtil {

  public static List<String> expand(Collection<?> fileAndDirectoryNames) {
    List<String> filenames = new ArrayList<String>();
    for (Object fileAndDirectoryName : fileAndDirectoryNames) {
      String name = (String) fileAndDirectoryName;
      File file = new File(name);
      if (file.isDirectory()) {
        filenames.addAll(expand(file));
      } else if (file.isFile()) {
        filenames.add(name);
      }
    }
    return filenames;
  }

  public static List<String> expand(Collection<?> fileAndDirectoryNames, String fileExtension) {
    List<String> filenames = new ArrayList<String>();
    for (Object fileAndDirectoryName : fileAndDirectoryNames) {
      String name = (String) fileAndDirectoryName;
      File file = new File(name);
      if (file.isDirectory()) {
        filenames.addAll(expand(file, fileExtension));
      } else if (file.isFile()) {
        filenames.add(name);
      }
    }
    return filenames;
  }

  public static List<String> expand(File fileOrDir) {
    List<String> filenames = new ArrayList<String>();
    if (fileOrDir.isDirectory()) {
      File[] files = fileOrDir.listFiles();
      for (File file : files) {
        if (file.isFile()) {
          filenames.add(file.getPath());
        }
      }
    }
    else {
      filenames.add(fileOrDir.getPath());
    }
    return filenames;
  }
  
  public static List<String> expand(File fileOrDir, String fileExtension) {
    List<String> filenames = new ArrayList<String>();
    if (fileOrDir.isDirectory()) {
      File[] files = fileOrDir.listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
          return name.endsWith("." + fileExtension);
        }
      });
      for (File file : files) {
        if (file.isFile()) {
          filenames.add(file.getPath());
        }
      }
    } else {
      filenames.add(fileOrDir.getPath());
    }
    return filenames;
  }

	public static Collection<Object> filenamesDeep(File directory) {
		Collection<Object> filenames = new ArrayList<Object>();
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				filenames.add(file.getPath());
			} else if (file.isDirectory()) {
				filenames.add(filenamesDeep(file));
			}
		}
		return filenames;
	}

	public static List<File> toFile(List<String> filenames) {
		List<File> files = new ArrayList<>();
		for (String filename : filenames) {
			files.add(new File(filename));
		}
		return files;
	}

}
