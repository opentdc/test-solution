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

import org.apache.cxf.jaxrs.client.WebClient;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

public abstract class AbstractTestClient {
	protected static int LIMIT;			// when testing lists, this defined the number of elements in a list
	protected int status;
	
	protected WebClient initializeTest(
		String apiUrl,
		Class<?> serviceClass)
	{
		WebClient _webclient = ServiceUtil.createWebClient(apiUrl, serviceClass);
		
		// ensure that LIMIT < GenericeService.DEF_SIZE (for testing purposes)
		if (GenericService.DEF_SIZE > 10) {
			LIMIT = 10;
		}
		/*  dead code, if DEF_SIZE is set > 10
		else {
			throw new Exception("AbstractTestClient.LIMIT needs to be set smaller than GenericService.DEF_SIZE");
		}
		*/
		return _webclient;
	}
	
	protected static WebClient createWebClient(String apiUrl, Class<?> serviceClass) {
		return ServiceUtil.createWebClient(apiUrl, serviceClass);
	}
}
