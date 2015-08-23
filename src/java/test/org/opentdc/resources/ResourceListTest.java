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
package test.org.opentdc.resources;

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
import org.opentdc.resources.ResourceModel;
import org.opentdc.resources.ResourcesService;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.ContactTest;

/**
 * Testing lists of resources.
 * @author Bruno Kaiser
 *
 */
public class ResourceListTest extends AbstractTestClient {
	public static final String CN = "ResourceListTest";
	private static WebClient wc = null;
	private static ArrayList<ResourceModel> testObjects = null;
	private static WebClient addressbookWC = null;
	private static AddressbookModel adb = null;
	private static ContactModel contact = null;
	private static ContactModel contact1 = null;
	private static ContactModel contact2 = null;
	private static int limit;

	@BeforeClass
	public static void initializeTests() {
		wc = createWebClient(ServiceUtil.RESOURCES_API_URL, ResourcesService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		adb = AddressbookTest.post(addressbookWC, new AddressbookModel(CN), Status.OK);
		contact = ContactTest.post(addressbookWC, adb.getId(), new ContactModel(CN + 1, CN + 2), Status.OK);
		contact1 = ContactTest.post(addressbookWC, adb.getId(), new ContactModel("John", "Doe"), Status.OK);
		contact2 = ContactTest.post(addressbookWC, adb.getId(), new ContactModel("Steve", "Doe"), Status.OK);
		System.out.println("***** " + CN);
		limit = 2 * GenericService.DEF_SIZE + 5; // if DEF_SIZE == 25 -> _limit2 = 55
		System.out.println("\tlimit:\t" + limit);
		testObjects = new ArrayList<ResourceModel>();
		ResourceModel _model = null;
		for (int i = 0; i < limit; i++) { 
			switch(i) {
			case 0: _model = new ResourceModel("RESNAME0", contact1.getId());
					break;
			case 1: _model = new ResourceModel("RESNAME1", contact2.getId());
					break;
			default: _model = new ResourceModel(CN + i, contact.getId());
					break;
			}
			testObjects.add(ResourceTest.post(wc, _model, Status.OK));
		}
		System.out.println("created " + testObjects.size() + " test objects");
		printModelList("testObjects", testObjects);
	}
	
	@AfterClass
	public static void cleanupTest() {
		for (ResourceModel _model : testObjects) {
			ResourceTest.delete(wc, _model.getId(), Status.NO_CONTENT);
		}
		AddressbookTest.delete(addressbookWC, adb.getId(), Status.NO_CONTENT);
		addressbookWC.close();
		wc.close();
	}
	
	/**
	 * Test whether all allocated test objects are listed.
	 */
	@Test
	public void testAllListed() {
		List<ResourceModel> _list = ResourceTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testAllListed", _list);
		ArrayList<String> _ids = new ArrayList<String>();
		for (ResourceModel _model : _list) {
			_ids.add(_model.getId());
		}		
		for (ResourceModel _model : testObjects) {
			assertTrue("Resource <" + _model.getId() + "> should be listed", _ids.contains(_model.getId()));
		}
	}

	/**
	 * Test whether all listed objects are readable.
	 */
	@Test
	public void testAllReadable() {
		for (ResourceModel _model : testObjects) {
			ResourceTest.get(wc, _model.getId(), Status.OK);
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
	
		List<ResourceModel> _batch = null;
		while(true) {
			_numberOfBatches++;
			_batch = ResourceTest.list(wc, null, _position, -1, Status.OK);
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
		List<ResourceModel> _objs = ResourceTest.list(wc, null, 5, 5, Status.OK);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
	
	/**
	 * Get some elements until the end of the list
	 */
	@Test
	public void testLastElements() {
		int _totalMembers = calculateMembers();
		List<ResourceModel> _objs = ResourceTest.list(wc, null, (_totalMembers - 4), 4, Status.OK);
		assertEquals("list() should return correct number of elements", 4, _objs.size());		
	}
	
	/**
	 * Read some elements until after the list end
	 */
	@Test 
	public void testOverEndOfList() {
		int _totalMembers = calculateMembers();
		List<ResourceModel> _objs = ResourceTest.list(wc, null, (_totalMembers - 5), 10, Status.OK);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
		
	// test some queries	
	@Test
	public void testQueryResourcesByNameLikeResource()
	{
		executeQueryTest("testQueryResourcesByNameLikeResource", "name().isLike(RESNAME)", 2);
	}
	
	@Test
	public void testQueryResourcesByNameLikeCN()
	{
		executeQueryTest("testQueryResourcesByNameLikeCN", "name().isLike(" + CN + ")", limit -2);
	}
	
	@Test
	public void testQueryResourcesByEqualName()
	{
		executeQueryTest("testQueryResourcesByEqualName", "name().equalTo(RESNAME1)", 1);
	}
	
	@Test
	public void testQueryResourcesByFirstName()
	{
		executeQueryTest("testQueryResourcesByFirstName", "firstName().equalTo(John)", 1);
	}
	
	@Test
	public void testQueryResourcesByLastName()
	{
		executeQueryTest("testQueryResourcesByLastName", "lastName().equalTo(Doe)", 2);
	}
	
	@Test
	public void testQueryResourcesByLastNameNotEqual()
	{
		executeQueryTest("testQueryResourcesByLastNameNotEqual", "lastName().notEqualTo(Doe)", limit - 2);
	}
	
	@Test
	public void testQueryResourcesByContactId()
	{
		executeQueryTest("testQueryResourcesByContactId", "contactId().equalTo(" + contact.getId() + ")", limit - 2);
	}
	
	@Test
	public void testQueryResourcesByContactId2()
	{
		executeQueryTest("testQueryResourcesByContactId2", "contactId().equalTo(" + contact2.getId() + ")", 1);
	}
	
	@Test
	public void testQueryResourcesByContactIdNotEqual()
	{
		executeQueryTest("testQueryResourcesByContactIdNotEqual", "contactId().notEqualTo(" + contact2.getId() + ")", limit - 1);
	}
	
	@Test
	public void testQueryResourcesByCombination()
	{
		String _query = "contactId().equalTo(" + contact.getId() + ");name().equalTo(" + CN + "5)";
		executeQueryTest("testQueryResourcesByCombination", _query, 1);
	}
	
	
	private void executeQueryTest(
			String testcaseName,
			String query,
			int expectedResult) {
		List<ResourceModel> _objs = ResourceTest.list(wc, query, 0, Integer.MAX_VALUE, Status.OK);
		printModelList(testcaseName + " / " + query, _objs);
		assertEquals("list(" + query + ") should return " + expectedResult + " objects", expectedResult, _objs.size());		
	}
	
	/**
	 * Print the result of the list() operation onto stdout.
	 * @param title  the title of the log section
	 * @param list a list of ResourceModel objects
	 */
	public static void printModelList(String title, List<ResourceModel> list) {
		System.out.println("***** " + title);
		System.out.println("\ttextId\t\t\t\tname");
		for (ResourceModel _model : list) { 
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
		return ResourceTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK).size();
	}
}
