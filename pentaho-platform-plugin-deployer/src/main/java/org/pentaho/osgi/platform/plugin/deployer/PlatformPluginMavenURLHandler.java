package org.pentaho.osgi.platform.plugin.deployer;

import org.osgi.service.url.AbstractURLStreamHandlerService;
import org.pentaho.osgi.platform.plugin.deployer.api.PluginFileHandler;
import org.pentaho.osgi.platform.plugin.deployer.impl.BundleStateManager;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by bryan on 8/29/14.
 */
public class PlatformPluginMavenURLHandler extends AbstractURLStreamHandlerService {
  private List<PluginFileHandler> pluginFileHandlers;
  private BundleStateManager bundleStateManager;

  public void setPluginFileHandlers( List<PluginFileHandler> pluginFileHandlers ) {
    this.pluginFileHandlers = pluginFileHandlers;
  }
  public void setBundleStateManager( BundleStateManager bundleStateManager) {
    this.bundleStateManager = bundleStateManager;
  }

  @Override public URLConnection openConnection( URL u ) throws IOException {
    URL mvnUrl = new URL( "mvn", null, u.getPath() );
    return new PlatformPluginBundlingURLConnection( mvnUrl, pluginFileHandlers, bundleStateManager );
  }
}
