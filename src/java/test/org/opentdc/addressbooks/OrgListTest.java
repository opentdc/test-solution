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
package test.org.opentdc.addressbooks;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.OrgType;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/**
 * Testing lists of organizations.
 * @author Bruno Kaiser
 *
 */
public class OrgListTest extends AbstractTestClient {
	private static final String CN = "OrgListTest";
	private static AddressbookModel adb = null;
	private static WebClient wc = null;
	private static ArrayList<OrgModel> testObjects = null;

	/**
	 * Initialize test with several contacts.
	 */
	@BeforeClass
	public static void initializeTests() {
		wc = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		System.out.println("***** " + CN);
		adb = AddressbookTest.post(wc, new AddressbookModel(CN), Status.OK);
		testObjects = new ArrayList<OrgModel>();
		for (int i = 0; i < (2 * GenericService.DEF_SIZE + 5); i++) { // if DEF_SIZE == 25 -> _limit2 = 55
			OrgModel _model = OrgTest.create(wc, adb.getId(), CN + i, OrgType.OTHER, Status.OK);
			testObjects.add(_model);
		}
		System.out.println("created " + testObjects.size() + " test objects");
		printModelList("testObjects", testObjects);
	}
	
	/**
	 * Cleanup all test resources
	 */
	@AfterClass
	public static void cleanupTest() {
		AddressbookTest.delete(wc, adb.getId(), Status.NO_CONTENT);
		wc.close();
	}

	/**
	 * Test whether all allocated test objects are listed.
	 */
	@Test
	public void testAllListed() {
		List<OrgModel> _list = OrgTest.list(wc, adb.getId(), null, 0, Integer.MAX_VALUE, Status.OK, false);
		printModelList("testAllListed", _list);
		ArrayList<String> _ids = new ArrayList<String>();
		for (OrgModel _model : _list) {
			_ids.add(_model.getId());
		}		
		for (OrgModel _model : testObjects) {
			assertTrue("Org <" + _model.getId() + "> should be listed", _ids.contains(_model.getId()));
		}
	}

	/**
	 * Test whether all listed objects are readable.
	 */
	@Test
	public void testAllReadable() {
		for (OrgModel _model : testObjects) {
			OrgTest.get(wc, adb.getId(), _model.getId(), Status.OK);
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
	
		List<OrgModel> _batch = null;
		while(true) {
			_numberOfBatches++;
			_batch = OrgTest.list(wc, adb.getId(), null, _position, -1, Status.OK, false);
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
	 * Get some elements from a specific position
	 */
	@Test
	public void testNextElements() {
		List<OrgModel> _objs = OrgTest.list(wc, adb.getId(), null, 5, 5, Status.OK, false);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
	
	/**
	 * Get some elements at the end of the list
	 */
	@Test
	public void testLastElements() {
		int _totalMembers = calculateMembers();
		List<OrgModel> _objs = OrgTest.list(wc, adb.getId(), null, (_totalMembers - 4), 4, Status.OK, false);
		assertEquals("list() should return correct number of elements", 4, _objs.size());		
	}
	
	/**
	 * Read some elements until after the list end
	 */
	@Test 
	public void testOverEndOfList() {
		int _totalMembers = calculateMembers();
		List<OrgModel> _objs = OrgTest.list(wc, adb.getId(), null, (_totalMembers - 5), 10, Status.OK, false);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
		
	/**
	 * Print the result of the list() operation onto stdout.
	 * @param title  the title of the log section
	 * @param list a list of ContactModel objects
	 */
	public static void printModelList(String title, List<OrgModel> list) {
		System.out.println("***** " + title);
		System.out.println("\ttextId\tname");
		for (OrgModel _model : list) { 
			System.out.println(
					"\t" + _model.getId() + 
					"\t" + _model.getName());
		}
		System.out.println("\ttotal:\t" + list.size() + " elements");
	}
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
	 */
	protected int calculateMembers() {
		return OrgTest.list(wc, adb.getId(), null, 0, Integer.MAX_VALUE, Status.OK, false).size();
	}
}