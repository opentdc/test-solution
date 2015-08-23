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
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/**
 * Testing list() of Addressbooks.
 * @author Bruno Kaiser
 *
 */
public class AddressbookListTest extends AbstractTestClient {
	public static final String CN = "AddressbookListTest";
	private static WebClient wc = null;
	private static ArrayList<AddressbookModel> testObjects = null;
	private static int limit;

	/**
	 * Initialize the test with several Addressbooks
	 */
	@BeforeClass
	public static void initializeTests() {
		wc = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		System.out.println("***** " + CN);
		limit = 2 * GenericService.DEF_SIZE + 5; // if DEF_SIZE == 25 -> _limit2 = 55
		System.out.println("\tlimit:\t" + limit);
		testObjects = new ArrayList<AddressbookModel>();
		for (int i = 0; i < limit; i++) { 
			testObjects.add(AddressbookTest.post(wc, new AddressbookModel(CN + i), Status.OK));
		}
		System.out.println("created " + testObjects.size() + " test objects");
		printModelList("testObjects", testObjects);
	}

	/**
	 * Remove all allocated test resources.
	 */
	@AfterClass
	public static void cleanupTest() {
		for (AddressbookModel _model : testObjects) {
			AddressbookTest.delete(wc, _model.getId(), Status.NO_CONTENT);
		}
		wc.close();
	}
	
	/**
	 * Test whether all allocated test objects are listed.
	 */
	@Test
	public void testAllListed() {
		List<AddressbookModel> _list = AddressbookTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testAllListed", _list);
		ArrayList<String> _ids = new ArrayList<String>();
		for (AddressbookModel _model : _list) {
			_ids.add(_model.getId());
		}		
		for (AddressbookModel _model : testObjects) {
			assertTrue("Addressbook <" + _model.getId() + "> should be listed", _ids.contains(_model.getId()));
		}
	}

	/**
	 * Test whether all listed objects are readable.
	 */
	@Test
	public void testAllReadable() {
		for (AddressbookModel _model : testObjects) {
			AddressbookTest.get(wc, _model.getId(), Status.OK);
		}			
	}

	/**
	 * Test batch-wise access to the test data (list with default position and size).
	 */
	@Test
	public void testBatchedList() 
	{
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
	
		List<AddressbookModel> _batch = null;
		while(true) {
			_numberOfBatches++;
			_batch = AddressbookTest.list(wc, null, _position, -1, Status.OK);
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
	public void testNextElements() 
	{
		List<AddressbookModel> _objs = AddressbookTest.list(wc, null, 5, 5, Status.OK);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
	
	/**
	 * Get some elements until the end of the list
	 */
	@Test
	public void testLastElements() 
	{
		int _totalMembers = calculateMembers();
		List<AddressbookModel> _objs = AddressbookTest.list(wc, null, (_totalMembers - 4), 4, Status.OK);
		assertEquals("list() should return correct number of elements", 4, _objs.size());		
	}
	
	/**
	 * Read some elements until after the list end
	 */
	@Test 
	public void testOverEndOfList() 
	{
		int _totalMembers = calculateMembers();
		List<AddressbookModel> _objs = AddressbookTest.list(wc, null, (_totalMembers - 5), 10, Status.OK);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
	
	// test some queries
	@Test
	public void testQueryAllAddressbookByName()
	{
		executeQueryTest("testQueryAllAddressbookByName", "name().equalTo(AAA)", 1);
	}
	
	@Test
	public void testQueryAllTestAddressbooksByName()
	{
		executeQueryTest("testQueryAllTestAddressbooksByName", "name().isLike(" + CN + ")", limit);
	}
	
	@Test
	public void testQuerySingleTestAddressbookByName()
	{
		executeQueryTest("testQuerySingleTestAddressbookByName", "name().equalTo(" + CN + "1)", 1);
	}
	
	private void executeQueryTest(
			String testcaseName,
			String query,
			int expectedResult) {
		List<AddressbookModel> _objs = AddressbookTest.list(wc, query, 0, Integer.MAX_VALUE, Status.OK);
		printModelList(testcaseName + " / " + query, _objs);
		assertEquals("list(" + query + ") should return " + expectedResult + " objects", expectedResult, _objs.size());		
	}

	/**
	 * Print the result of the list() operation onto stdout.
	 * @param title  the title of the log section
	 * @param list a list of AddressbookModel objects
	 */
	public static void printModelList(
			String title, 
			List<AddressbookModel> list) 
	{
		System.out.println("***** " + title);
		System.out.println("\tID\tname");
		for (AddressbookModel _model : list) { 
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
		return AddressbookTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK).size();
	}
}
