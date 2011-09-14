/*******************************************************************************
 * Copyright (c) 2010 Sonatype, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Sonatype, Inc. - initial API and implementation
 *******************************************************************************/

package org.eclipse.m2e.core.project.configurator;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;


/**
 * Build participant aware of the notion of pre-configuration build.
 * <p>
 * Pre-configuration build runs as part of project import before invocation of
 * {@link AbstractProjectConfigurator#configure(ProjectConfigurationRequest, IProgressMonitor)} (hence
 * "pre-configuration"). The main usecase for pre-configuration build is to allow changes to MavenProject mutable state,
 * i.e. sources roots, resources and properties.
 * <p>
 * Participants of pre-configure build are not expected to make any changes to workspace resources or filesystem. To
 * allow direct execution of maven plugins compatible with workspace incremental build, special "no changes" build
 * context is used during pre-configuration build.
 * 
 * @since 1.1
 */
public abstract class AbstractBuildParticipant2 extends AbstractBuildParticipant {

  /**
   * Build kind constant indicating a pre-configuration build request.
   */
  public static final int PRECONFIGURE_BUILD = 1 << 16;

  /**
   * @param kind the kind of build being requested, {@link #FULL_BUILD}, {@link #AUTO_BUILD}, {@link #INCREMENTAL_BUILD}
   *          or {@link #PRECONFIGURE_BUILD}.
   */
  @Override
  public abstract Set<IProject> build(int kind, IProgressMonitor monitor) throws Exception;

}