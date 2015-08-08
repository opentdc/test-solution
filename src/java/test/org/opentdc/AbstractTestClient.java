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

import static org.junit.Assert.assertEquals;

import org.apache.cxf.jaxrs.client.WebClient;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

/**
 * @author Bruno Kaiser
 *
 */
public abstract class AbstractTestClient {
	protected static int LIMIT;			// when testing lists, this defined the number of elements in a list
	protected int status;
	
	/**
	 * Initializes the test environment
	 * @param apiUrl the url used to call the test service
	 * @param serviceClass the tested class
	 * @return the webclient that represents the test service
	 */
	protected static WebClient initializeTest(
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
	
	/**
	 * Create a webclient.
	 * @param apiUrl the url used to call the test service
	 * @param serviceClass the tested class
	 * @return the webclient that represents the test service
	 */
	protected static WebClient createWebClient(String apiUrl, Class<?> serviceClass) {
		return ServiceUtil.createWebClient(apiUrl, serviceClass);
	}
	
	/**
	 * Retrieve the number of members of the testobject list
	 * @return
	 */
	abstract protected int calculateMembers();
	
	/**
	 * @param nrBatches
	 * @param nrObjects
	 * @param lastBatchSize
	 */
	protected void validateBatches(int nrBatches, int nrObjects, int lastBatchSize) {
		int _totalMembers = calculateMembers();
		int _nrFullBatches = (_totalMembers / GenericService.DEF_SIZE) + 1;
		int _lastIncrement = _totalMembers % GenericService.DEF_SIZE;
		System.out.println("Estimated:");
		System.out.println("\tlistSize:\t\t" + _totalMembers);
		System.out.println("\tbatchSize:\t\t" + GenericService.DEF_SIZE);
		System.out.println("\tnrBatches:\t\t" + _nrFullBatches);
		System.out.println("\tlastBatchSize:\t\t" + _lastIncrement);
		System.out.println("Measured:");
		System.out.println("\tlistSize:\t\t" + nrObjects);
		System.out.println("\tbatchSize:\t\t" + GenericService.DEF_SIZE);
		System.out.println("\tnrBatches:\t\t" + nrBatches);
		System.out.println("\tlastBatchSize:\t\t" + lastBatchSize);
		assertEquals("number of batches should be as expected", _nrFullBatches, nrBatches);
		assertEquals("should have returned all objects", _totalMembers, nrObjects);
		assertEquals("last batch size should be as expected", _lastIncrement, lastBatchSize);		
	}
}
