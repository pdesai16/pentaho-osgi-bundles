/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2017 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
package org.pentaho.osgi.platform.plugin.deployer.impl;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pdesai on 4/4/2017.
 */

public class PlatformDeployerBundleListener implements BundleListener {
  private static Logger log = LoggerFactory.getLogger( PlatformDeployerBundleListener.class );
  private BundleContext bundleContext;
  private BundleStateManager bundleStateManager;


  public void setBundleContext( BundleContext bundleContext ) {
    this.bundleContext = bundleContext;
  }

  public void setBundleStateManager( BundleStateManager bundleStateManager ) {
    this.bundleStateManager = bundleStateManager;
  }

  // For unit test only
  static void setLog( Logger log ) {
    PlatformDeployerBundleListener.log = log;
  }

  // For unit test only
  static Logger getLog() {
    return log;
  }

  @Override public void bundleChanged( BundleEvent event ) {
    switch ( event.getType() ) {
      case BundleEvent.INSTALLED:
        // Checking for "common-ui" for now
        if ( event.getBundle().getSymbolicName().startsWith( "common-ui" ) ) {
          String bundleStr = event.getBundle().getHeaders().get( "Bundle-Name" ) +  event.getBundle().getHeaders().get( "Bundle-Version" );
          log.info( " LISTENER PlatformDeployerBundleListener Headers = " + event.getBundle().getHeaders().get( "Bundle-Version" )
            + event.getBundle().getHeaders().get( "Bundle-Name" ) );
          bundleStateManager.setState( bundleStr, event.getBundle().getState() );
        }
        break;
    }
  }

  public void init() throws Exception {
    bundleContext.addBundleListener( this );
  }
}
