/**
 * Copyright 2013 Lunifera GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lunifera.example.vaadin.lunifera.standalone;

import org.lunifera.runtime.web.vaadin.osgi.common.OSGiUI;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class StandaloneUI extends OSGiUI {

	@Override
	protected void init(VaadinRequest request) {
		setContent(new Label(
				"Yep, thats the Standalone UI provided by lunifera OSGi bridge."));
	}

}
