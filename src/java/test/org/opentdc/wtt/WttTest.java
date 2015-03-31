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
package test.org.opentdc.wtt;

import javax.ws.rs.core.MediaType;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.opentdc.wtt.WttService;

@RunWith(Suite.class)
@SuiteClasses({ CompanyTest.class, ProjectTest.class, ResourceTest.class })
public class WttTest {

	public static WebClient initWebClient(String uri) {
		JAXRSClientFactoryBean _sf = new JAXRSClientFactoryBean();
		_sf.setResourceClass(WttService.class);
		_sf.setAddress(uri);
		BindingFactoryManager _manager = _sf.getBus().getExtension(
				BindingFactoryManager.class);
		JAXRSBindingFactory _factory = new JAXRSBindingFactory();
		_factory.setBus(_sf.getBus());
		_manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID,
				_factory);
		WebClient _wc = _sf.createWebClient();
		_wc.type(MediaType.APPLICATION_JSON).accept(
				MediaType.APPLICATION_JSON);
		return _wc;
	}

	/********************************** main *********************************/
	/**
	 * Main method to run the JUnit tests outside Eclipse via standard Java code
	 * e.g. with ant.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Testing Company...");
		printResult(JUnitCore.runClasses(CompanyTest.class));
		System.out.println("Testing Project...");
		printResult(JUnitCore.runClasses(ProjectTest.class));
		System.out.println("Tests completed.");
	}
	
	private static void printResult(Result result) {
		for (Failure _failure : result.getFailures()) {
			System.out.println(_failure.toString());
		}
	}
}
