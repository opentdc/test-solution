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
package test.org.opentdc.rates;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opentdc.rates.RateModel;
import org.opentdc.rates.RatesService;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/**
 * Testing list() of Rates.
 * @author Bruno Kaiser
 *
 */
public class RateListTest extends AbstractTestClient {
	private static final String CN = "RateListTest";
	private static WebClient wc = null;
	private static ArrayList<RateModel> testObjects = null;

	/**
	 * Initialize the test with several Addressbooks
	 */
	@BeforeClass
	public static void initializeTests() {
		wc = createWebClient(ServiceUtil.RATES_API_URL, RatesService.class);
		System.out.println("***** " + CN);
		testObjects = new ArrayList<RateModel>();
		for (int i = 0; i < (2 * GenericService.DEF_SIZE + 5); i++) { // if DEF_SIZE == 25 -> _limit2 = 55
			RateModel _model = RateTest.post(wc, new RateModel(CN, 10, CN), Status.OK);
			testObjects.add(_model);
		}
		System.out.println("created " + testObjects.size() + " test objects");
		printModelList("testObjects", testObjects);
	}

	/**
	 * Remove all test resources used
	 */
	@AfterClass
	public static void cleanupTest() {
		for (RateModel _model : testObjects) {
			RateTest.delete(wc, _model.getId(), Status.NO_CONTENT);
		}
		wc.close();
	}
	
	/**
	 * Test whether all allocated test objects are listed.
	 */
	@Test
	public void testAllListed() {
		List<RateModel> _list = RateTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testAllListed", _list);
		ArrayList<String> _ids = new ArrayList<String>();
		for (RateModel _model : _list) {
			_ids.add(_model.getId());
		}		
		for (RateModel _model : testObjects) {
			assertTrue("Rate <" + _model.getId() + "> should be listed", _ids.contains(_model.getId()));
		}
	}

	/**
	 * Test whether all listed objects are readable.
	 */
	@Test
	public void testAllReadable() {
		for (RateModel _model : testObjects) {
			RateTest.get(wc, _model.getId(), Status.OK);
		}			
	}

	/**
	 * Test batch-wise access to the test data (list with default position and size).
	 */
	@Test
	public void testBatchedList() {
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
	
		List<RateModel> _batch = null;
		while(true) {
			_numberOfBatches++;
			_batch = RateTest.list(wc, null, _position, -1, Status.OK);
			_numberOfReturnedObjects += _batch.size();
			System.out.println("batch " + _numberOfBatches + ": position=" + _position + ", returnedObjects=" + _numberOfReturnedObjects);
			if (_batch.size() < GenericService.DEF_SIZE) {
				break;
			} else {
				_position += GenericService.DEF_SIZE;					
			}
		}
		validateBatches(_numberOfBatches, _numberOfReturnedObjects, _batch.size());
	}

	/**
	 * Get some elements starting from a specific position
	 */
	@Test
	public void testNextElements() {
		List<RateModel> _objs = RateTest.list(wc, null, 5, 5, Status.OK);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
	
	/**
	 * Get some elements until the end of the list
	 */
	@Test
	public void testLastElements() {
		int _totalMembers = calculateMembers();
		List<RateModel> _objs = RateTest.list(wc, null, (_totalMembers - 4), 4, Status.OK);
		assertEquals("list() should return correct number of elements", 4, _objs.size());		
	}
	
	/**
	 * Read some elements until after the list end
	 */
	@Test 
	public void testOverEndOfList() {
		int _totalMembers = calculateMembers();
		List<RateModel> _objs = RateTest.list(wc, null, (_totalMembers - 5), 10, Status.OK);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
	
	/**
	 * Print the result of the list() operation onto stdout.
	 * @param title  the title of the log section
	 * @param list a list of ContactModel objects
	 */
	public static void printModelList(String title, List<RateModel> list) {
		System.out.println("***** " + title);
		System.out.println("\ttextId\ttitle" + "");
		for (RateModel _model : list) { 
			System.out.println(
					"\t" + _model.getId() + 
					"\t" + _model.getTitle());
		}
		System.out.println("\ttotal:\t" + list.size() + " elements");
	}
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
	 */
	protected int calculateMembers() {
		return RateTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK).size();
	}
}