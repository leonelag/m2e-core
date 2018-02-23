/*******************************************************************************
 * Copyright (c) 2008-2010 Sonatype, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Sonatype, Inc. - initial API and implementation
 *******************************************************************************/

package org.eclipse.m2e.jdt.internal;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;


/**
 * Maven classpath container
 */
public class MavenClasspathContainer implements IClasspathContainer, Serializable {
  private static final long serialVersionUID = -5976726121300869771L;

  private final IClasspathEntry[] entries;

  private final IPath path;

  public MavenClasspathContainer(IPath path, IClasspathEntry[] entries) {
    this.path = path;
    this.entries = sorted(entries);
  }

  public String getDescription() {
    return Messages.MavenClasspathContainer_description;
  }

  public int getKind() {
    return IClasspathContainer.K_APPLICATION;
  }

  public synchronized IClasspathEntry[] getClasspathEntries() {
    return entries;
  }

  public IPath getPath() {
    return path;
  }

  /**
   * Returns copy of the input array, sorted by last segment of the entry's path.
   */
  private static IClasspathEntry[] sorted(IClasspathEntry[] entries) {
    if(entries == null) {
      return null;
    }

    IClasspathEntry[] sorted = new IClasspathEntry[entries.length];
    System.arraycopy(entries, 0, sorted, 0, entries.length);
    Arrays.sort(sorted, byLastSegment());
    return sorted;
  }

  private static Comparator<IClasspathEntry> byLastSegment() {
    return (e1, e2) -> String.CASE_INSENSITIVE_ORDER.compare(e1.getPath().lastSegment(), e2.getPath().lastSegment());
  }
}
