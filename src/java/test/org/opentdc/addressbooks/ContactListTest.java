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
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/**
 * Testing lists of contacts.
 * @author Bruno Kaiser
 *
 */
public class ContactListTest extends AbstractTestClient {
	private static final String CN = "ContactListTest";
	private static AddressbookModel adb = null;
	private static WebClient wc = null;
	private static ArrayList<ContactModel> testObjects = null;
	private static int limit;

	/**
	 * Initialize test with several contacts.
	 */
	@BeforeClass
	public static void initializeTests() {
		wc = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		System.out.println("***** " + CN);
		limit = 2 * GenericService.DEF_SIZE + 5; // if DEF_SIZE == 25 -> _limit2 = 55
		System.out.println("\tlimit:\t" + limit);
		adb = AddressbookTest.post(wc, new AddressbookModel(CN), Status.OK);
		testObjects = new ArrayList<ContactModel>();
		ContactModel _model = null;
		for (int i = 0; i < limit; i++) { 
			switch(i) {
			case 0: _model = new ContactModel("Hans", "Muster");
					_model.setBirthday(ServiceUtil.parseDate("19110101", "yyyyMMdd"));
					break;
			case 1: _model = new ContactModel("John", "Doe");
					_model.setBirthday(ServiceUtil.parseDate("19220202", "yyyyMMdd"));
					break;
			case 2: _model = new ContactModel("Fritz", "Muster");
					_model.setBirthday(ServiceUtil.parseDate("19330303", "yyyyMMdd"));
					break;
			default: _model = new ContactModel(CN + i, "Test" + i);
					break;
			}
			testObjects.add(ContactTest.post(wc, adb.getId(), _model, Status.OK));
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
		List<ContactModel> _list = ContactTest.list(wc, adb.getId(), null, 0, Integer.MAX_VALUE, Status.OK, false);
		printModelList("testAllListed", _list);
		ArrayList<String> _ids = new ArrayList<String>();
		for (ContactModel _model : _list) {
			_ids.add(_model.getId());
		}		
		for (ContactModel _model : testObjects) {
			assertTrue("Contact <" + _model.getId() + "> should be listed", _ids.contains(_model.getId()));
		}
	}

	/**
	 * Test whether all listed objects are readable.
	 */
	@Test
	public void testAllReadable() {
		for (ContactModel _model : testObjects) {
			ContactTest.get(wc, adb.getId(), _model.getId(), Status.OK);
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
	
		List<ContactModel> _batch = null;
		while(true) {
			_numberOfBatches++;
			_batch = ContactTest.list(wc, adb.getId(), null, _position, -1, Status.OK, false);
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
		List<ContactModel> _objs = ContactTest.list(wc, adb.getId(), null, 5, 5, Status.OK, false);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
	
	/**
	 * Get some elements until the end of the list
	 */
	@Test
	public void testLastElements() {
		int _totalMembers = calculateMembers();
		List<ContactModel> _objs = ContactTest.list(wc, adb.getId(), null, (_totalMembers - 4), 4, Status.OK, false);
		assertEquals("list() should return correct number of elements", 4, _objs.size());		
	}
	
	/**
	 * Read some elements until after the list end
	 */
	@Test 
	public void testOverEndOfList() {
		int _totalMembers = calculateMembers();
		List<ContactModel> _objs = ContactTest.list(wc, adb.getId(), null, (_totalMembers - 5), 10, Status.OK, false);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}

	// test some queries	
	@Test
	public void testQueryAllContactsByName()
	{
		executeQueryTest("testQueryAllContactsByName", "firstName().isLike(" + CN + ")", limit -3);
	}
	
	@Test
	public void testQuerySingleContactByName()
	{
		executeQueryTest("testQuerySingleContactByName", "firstName().equalTo(" + CN + "4)", 1);
	}
	
	@Test
	public void testQueryByFirstName()
	{
		executeQueryTest("testQueryByFirstName", "firstName().equalTo(John)", 1);
	}
	
	@Test
	public void testQueryByLastName()
	{
		executeQueryTest("testQueryByLastName", "lastName().equalTo(Muster)", 2);
	}
	
	@Test
	public void testQueryByFullName()
	{
		executeQueryTest("testQueryByFullName", "fn().equalTo(John Doe)", 1);
	}
	
	@Test
	public void testQueryByNames()
	{
		executeQueryTest("testQueryByNames", "lastName().equalTo(Muster);firstName().equalTo(Hans)", 1);
	}
	
	@Test
	public void testQueryByBirthday()
	{
		executeQueryTest("testQueryByBirthday", "birthday().equalTo(19220202)", 1);
	}
	
	private void executeQueryTest(
			String testcaseName,
			String query,
			int expectedResult) {
		List<ContactModel> _objs = ContactTest.list(wc, adb.getId(), query, 0, Integer.MAX_VALUE, Status.OK, false);
		printModelList(testcaseName + " / " + query, _objs);
		assertEquals("list(" + query + ") should return " + expectedResult + " objects", expectedResult, _objs.size());		
	}
	
	/**
	 * Print the result of the list() operation onto stdout.
	 * @param title  the title of the log section
	 * @param list a list of ContactModel objects
	 */
	public static void printModelList(String title, List<ContactModel> list) {
		System.out.println("***** " + title);
		System.out.println("\ttextId\tname");
		for (ContactModel _model : list) { 
			System.out.println(
					"\t" + _model.getId() + 
					"\t" + _model.getFn());
		}
		System.out.println("\ttotal:\t" + list.size() + " elements");
	}
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
	 */
	protected int calculateMembers() {
		return ContactTest.list(wc, adb.getId(), null, 0, Integer.MAX_VALUE, Status.OK, false).size();
	}
}