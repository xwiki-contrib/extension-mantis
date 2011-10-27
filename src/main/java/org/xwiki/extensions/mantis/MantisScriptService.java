/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.extensions.mantis;

import java.net.URL;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.script.service.ScriptService;

import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;

/**
 * Expose MantisBt SOAP service to XWiki scripts.
 * 
 * @version $Id$
 */
@Component
@Named("mantis")
@Singleton
public class MantisScriptService implements ScriptService {

	/**
	 * The logger to log.
	 */
	@Inject
	private Logger logger;

	/**
	 * @param mantisURL
	 *            the URL to the remote MantisBt instance to connect to
	 * @return the client to interact with the remote MantisBt instance
	 */
	public MantisConnectPortType getMantisClient(String mantisURL) {
		MantisConnectPortType mantisConnectPortType = null; 
		try {
			mantisConnectPortType = new MantisConnectLocator().getMantisConnectPort(new URL(mantisURL + "/api/soap/mantisconnect.php"));
			String version = mantisConnectPortType.mc_version();
			this.logger.debug("Connected to Mantis({})", version);
		} catch (Exception e) {
			this.logger.warn("Error  Mantis URL [{}]", mantisURL);
			mantisConnectPortType = null;
		} 
		return mantisConnectPortType;
	}
}
