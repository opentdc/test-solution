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
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/**
 * Testing the listAllContacts() method
 * @author Bruno Kaiser
 *
 */
public class ContactAllListTest extends AbstractTestClient {
	private static final String CN = "ContactAllListTest";
	private static ArrayList<AddressbookModel> addressbooks = null;
	private static final int nrAddressbooks = 3;
	private static final int nrContacts = 10;
	private static int totalContacts = nrAddressbooks * nrContacts;
	private static WebClient wc = null;

	/**
	 * Initialize the test with several contacts
	 */
	@BeforeClass
	public static void initializeTest() {
		wc = initializeTest(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		System.out.println("***** " + CN);
		addressbooks = new ArrayList<AddressbookModel>();
		AddressbookModel _abm = null;
		for (int i = 0; i < nrAddressbooks; i++) {
			_abm = AddressbookTest.post(wc, new AddressbookModel(CN + i), Status.OK);
			addressbooks.add(_abm);
			for (int j = 0; j < nrContacts; j++) {
				ContactTest.post(wc, _abm.getId(), new ContactModel("addressbook" + i + "_contact" + j, CN), Status.OK);
			}
		}
		System.out.println(CN + " - Parameters" + 
				":\n\tnrAddressbooks:\t\t" + nrAddressbooks +
				"\n\tnrContacts:\t\t\t" + nrContacts + 
				"\n\ttotalContacts:\t\t" + totalContacts);		
		printCounters("initializeTest");
	}
	
	@AfterClass
	public static void cleanupTest() {
		for (AddressbookModel _model : addressbooks) {
			AddressbookTest.delete(wc, _model.getId(), Status.NO_CONTENT);
		}
		printCounters("cleanupTest");
		wc.close();
	}
	
	@Test
	public void testContactAllList() {
		printCounters("testContactAllList");
		List<ContactModel> _list = ContactTest.list(wc, null, null, 0, totalContacts + 1, Status.OK, true);
		assertTrue("should have returned all objects", _list.size() >= totalContacts);		
		System.out.println("list() returns " + _list.size() + " elements (_remoteList).");
	}
	
	/**
	 * Same as testContactAllList but with batchwise listing.
	 */
	@Test
	public void testContactAllListBatched() {
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
		List<ContactModel> _list = null;
		printCounters("testContactAllListBatched");
		while(true) {
			_numberOfBatches++;
			_list = ContactTest.list(wc, null, null, _position, -1, Status.OK, true);
			_numberOfReturnedObjects += _list.size();
			System.out.println("batch " + _numberOfBatches + ": position=" + _position + ", returnedObjects=" + _numberOfReturnedObjects);
			if (_list.size() < GenericService.DEF_SIZE) {
				break;
			} else {
				_position += GenericService.DEF_SIZE;					
			}
		}
		validateBatches(_numberOfBatches, _numberOfReturnedObjects, _list.size());
	}
	
	// testing some explicit positions and sizes
	// get next _nrElements elements from position _nrElements
	@Test
	public void testNextElements() {
		System.out.println("testNextElements");
		List<ContactModel> _list = ContactTest.list(wc, null, null, 7, 7, Status.OK, true);
		assertEquals("list() should return correct number of elements", 7, _list.size());
	}
		
	// get last _nrElements elements 
	@Test
	public void testLastElements() {
		System.out.println("testLastElements");
		List<ContactModel> _list = ContactTest.list(wc, null, null, totalContacts - 7, 7, Status.OK, true);
		assertEquals("list() should return correct number of elements", 7, _list.size());
	}
		
	// read over end of list
	@Test
	public void testReadOverEndOfList() {
		System.out.println("testReadOverEndOfList");
		List<ContactModel> _list = ContactTest.list(wc, null, null, totalContacts - 7, 2 * 7, Status.OK, true);
		assertTrue("list() should return correct number of elements", _list.size() >= 7);		
	}
	
	/**
	 * @param title
	 */
	private static void printCounters(String title) {
		System.out.println(title + 
				":\n\tnrAddressbooks:\t\t" + AddressbookTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK).size() +
				"\n\tnrContacts:\t\t\t" + ContactTest.list(wc, null, null, 0, Integer.MAX_VALUE, Status.OK, true).size());		
	}
			
	/* (non-Javadoc)
	 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
	 */
	protected int calculateMembers() {
		return ContactTest.list(wc, null, null, 0, Integer.MAX_VALUE, Status.OK, true).size();
	}
}