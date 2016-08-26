package org.lunifera.example.vaadin.osgi.bootstrap.push;

import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;

@SuppressWarnings("serial")
public class ServletService extends VaadinServletService {
	 
    public ServletService(VaadinServlet servlet,
            DeploymentConfiguration deploymentConfiguration)
            throws ServiceException {
        super(servlet, deploymentConfiguration);
    }
 
    @Override
    public ClassLoader getClassLoader() {
        // return the bundle classloader
        // see http://dev.vaadin.com/ticket/15516
        return ServletService.class.getClassLoader();
    }
}
