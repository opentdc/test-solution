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
import org.opentdc.addressbooks.AddressModel;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.AttributeType;
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/**
 * Testing lists of Addresses.
 * @author Bruno Kaiser
 *
 */
public class AddressListTest extends AbstractTestClient {
	private static final String CN = "AddressListTest";
	private static AddressbookModel adb = null;
	private static ContactModel contact = null;
	private static WebClient wc = null;
	private static ArrayList<AddressModel> testObjects = null;
		
	/**
	 * Initialize the test with several Addresses.
	 */
	@BeforeClass
	public static void initializeTests() {
		wc = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		adb = AddressbookTest.post(wc, new AddressbookModel(CN), Status.OK);
		contact = ContactTest.post(wc, adb.getId(), new ContactModel(CN + "1", CN + "2"), Status.OK);
		System.out.println("***** AddressListTest:");
		testObjects = new ArrayList<AddressModel>();
		for (int i = 0; i < (2 * GenericService.DEF_SIZE + 5); i++) { // if DEF_SIZE == 25 -> _limit2 = 55
			AddressModel _model = ContactAddressTest.post(wc, adb.getId(), contact.getId(), 
					ContactAddressTest.createUrlAddress(
							AttributeType.HOME, "AddressListTest" + i), Status.OK);
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
		AddressbookTest.delete(wc, adb.getId(), Status.NO_CONTENT);
		System.out.println("deleted 1 addressbook with 1 contact and " + testObjects.size() + " test objects");
		wc.close();
	}

	@Test
	public void testAllListed() {
		List<AddressModel> _list = ContactAddressTest.list(wc, adb.getId(), contact.getId(), null, 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testAllListed", _list);
		ArrayList<String> _ids = new ArrayList<String>();
		for (AddressModel _model : _list) {
			_ids.add(_model.getId());
		}		
		for (AddressModel _model : testObjects) {
			assertTrue("Address <" + _model.getId() + "> should be listed", _ids.contains(_model.getId()));
		}
	}

	@Test
	public void testAllReadable() {
		for (AddressModel _model : testObjects) {
			ContactAddressTest.get(wc, adb.getId(), contact.getId(), _model.getId(), Status.OK);
		}			
	}
	
	@Test
	public void testBatchedList() {
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
	
		List<AddressModel> _batch = null;
		while(true) {
			_numberOfBatches++;
			_batch = ContactAddressTest.list(wc, adb.getId(), contact.getId(), null, _position, -1, Status.OK);
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
	
	@Test
	public void testNextElements() {
		List<AddressModel> _objs = ContactAddressTest.list(wc, adb.getId(), contact.getId(), null, 5, 5, Status.OK);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
	
	@Test
	public void testLastElements() {
		int _totalMembers = calculateMembers();
		List<AddressModel> _objs = ContactAddressTest.list(wc, adb.getId(), contact.getId(), null, (_totalMembers - 4), 4, Status.OK);
		assertEquals("list() should return correct number of elements", 4, _objs.size());		
	}
	
	@Test 
	public void testOverEndOfList() {
		int _totalMembers = calculateMembers();
		List<AddressModel> _objs = ContactAddressTest.list(wc, adb.getId(), contact.getId(), null, (_totalMembers - 5), 10, Status.OK);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
	
	/**
	 * Print the result of the list() operation onto stdout.
	 * @param title  the title of the log section
	 * @param list a list of AddressModel objects
	 */
	public static void printModelList(String title, List<AddressModel> list) {
		System.out.println("***** " + title);
		System.out.println("\ttextId\taddressType");
		for (AddressModel _model : list) { 
			System.out.println(
					"\t" + _model.getId() + 
					"\t" + _model.getAddressType().toString());
		}
		System.out.println("\ttotal:\t" + list.size() + " elements");
	}
	
	protected int calculateMembers() {
		return ContactAddressTest.list(wc, adb.getId(), contact.getId(), null, 0, Integer.MAX_VALUE, Status.OK).size();
	}
}
