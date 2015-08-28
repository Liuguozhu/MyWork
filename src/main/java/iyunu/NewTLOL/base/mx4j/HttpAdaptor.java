package iyunu.NewTLOL.base.mx4j;

import java.net.MalformedURLException;

import javax.management.Attribute;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import mx4j.tools.adaptor.http.XSLTProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpAdaptor {

	private Logger logger = LoggerFactory.getLogger(HttpAdaptor.class);
	private int port = 0;
	private String host = null;

	NewTLOL tianLongDriverMBean = null;

	public HttpAdaptor(final NewTLOL tianLongDriver, final String host, final int port) {
		this.host = host;
		this.port = port;
		this.tianLongDriverMBean = tianLongDriver;
	}

	public final void start() throws JMException, MalformedURLException {
		MBeanServer server = MBeanServerFactory.createMBeanServer("NewTLOL");
		ObjectName serverName = new ObjectName("Http:name=HttpAdaptor");
		mx4j.tools.adaptor.http.HttpAdaptor adaptor = new mx4j.tools.adaptor.http.HttpAdaptor();
		server.registerMBean(adaptor, serverName);

		if (port > 0) {
			server.setAttribute(serverName, new Attribute("Port", port));
		} else {
			logger.error("Incorrect port value " + port);
		}
		if (host != null) {
			server.setAttribute(serverName, new Attribute("Host", host));
		} else {
			logger.error("Incorrect null hostname");
		}

		ObjectName processorName = new ObjectName("Http:name=XSLTProcessor");
		XSLTProcessor xsltProcessor = new XSLTProcessor();
		server.registerMBean(xsltProcessor, processorName);

		server.setAttribute(processorName, new Attribute("UseCache", Boolean.valueOf(false)));

		server.setAttribute(serverName, new Attribute("ProcessorName", processorName));

		server.registerMBean(tianLongDriverMBean, new ObjectName("NewTLOL:name=NewTLOL MANAGER"));

		server.invoke(serverName, "start", null, null);
	}
}
