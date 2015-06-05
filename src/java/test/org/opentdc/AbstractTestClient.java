/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Arbalo AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package test.org.opentdc;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.opentdc.service.GenericService;

public abstract class AbstractTestClient<T> {
	private static final String DEFAULT_URL = "http://localhost:8080/opentdc-services-test/";
	protected static int LIMIT;			// when testing lists, this defined the number of elements in a list

	protected WebClient webclient = null;
	protected int status;
	protected String apiUrl = "";

	protected String createUrl(
		String api) {
		String _serviceUrl = DEFAULT_URL;
		if(System.getProperty("service.url") != null && 
			System.getProperty("service.url").startsWith("http://")) {
			_serviceUrl = System.getProperty("service.url");
		}
		if (!_serviceUrl.endsWith("/")) {
			_serviceUrl = _serviceUrl + "/";
		}
		return _serviceUrl + api;
	}
	
	protected WebClient createWebClient(
		Class<T> serviceClass) {
		JAXRSClientFactoryBean _sf = new JAXRSClientFactoryBean();
		_sf.setResourceClass(serviceClass);
		_sf.setAddress(apiUrl);
		BindingFactoryManager _manager = _sf.getBus().getExtension(BindingFactoryManager.class);
		JAXRSBindingFactory _factory = new JAXRSBindingFactory();
		_factory.setBus(_sf.getBus());
		_manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, _factory);
		WebClient webclient = _sf.createWebClient();
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		return webclient;
	}
	
	protected void initializeTest(
		String api,
		Class<T> serviceClass
	) {
		apiUrl = createUrl(api);
		webclient = createWebClient(serviceClass);
		
		// ensure that LIMIT < GenericeService.DEF_SIZE (for testing purposes)
		if (GenericService.DEF_SIZE > 10) {
			LIMIT = 10;
		}
		/*  dead code, if DEF_SIZE is set > 10
		else {
			throw new Exception("AbstractTestClient.LIMIT needs to be set smaller than GenericService.DEF_SIZE");
		}
		*/
	}
	
	@After
	public void reset() {
		// webclient.reset();
		webclient.close();
	}
}
