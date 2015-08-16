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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.WttService;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.OrgType;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.OrgTest;

/**
 * Testing lists of companies in time tracking service WTT.
 * @author Bruno Kaiser
 *
 */
public class CompanyListTest extends AbstractTestClient {
	private static final String CN = "CompanyListTest";
	private static WebClient wc = null;
	private static AddressbookModel adb = null;
	private static OrgModel org = null;
	private static WebClient addressbookWC = null;
	private static ArrayList<CompanyModel> testObjects = null;
	
	/**
	 * Initialize the test with several companies
	 */
	@BeforeClass
	public static void initializeTests() {
		wc = initializeTest(ServiceUtil.WTT_API_URL, WttService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		System.out.println("***** " + CN);
		
		adb = AddressbookTest.post(addressbookWC, 
				new AddressbookModel(CN), Status.OK);
		org = OrgTest.post(addressbookWC, adb.getId(), 
				new OrgModel(CN, OrgType.CLUB), Status.OK);
		testObjects = new ArrayList<CompanyModel>();
		for (int i = 0; i < (2 * GenericService.DEF_SIZE + 5); i++) { // if DEF_SIZE == 25 -> _limit2 = 55
			testObjects.add(CompanyTest.post(wc, new CompanyModel(CN + i, "MY_DESC", org.getId()), Status.OK));
		}
		System.out.println("created " + testObjects.size() + " test objects");
		printModelList("testObjects", testObjects);
	}

	/**
	 * Remove all allocated test resources.
	 */
	@AfterClass
	public static void cleanupTest() {
		AddressbookTest.delete(addressbookWC, adb.getId(), Status.NO_CONTENT);
		addressbookWC.close();
		
		for (CompanyModel _model : testObjects) {
			CompanyTest.delete(wc, _model.getId(), Status.NO_CONTENT);
		}
		wc.close();
	}

	/**
	 * Test whether all allocated test objects are listed.
	 */
	@Test
	public void testAllListed() {
		List<CompanyModel> _list = CompanyTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testAllListed", _list);
		ArrayList<String> _ids = new ArrayList<String>();
		for (CompanyModel _model : _list) {
			_ids.add(_model.getId());
		}		
		for (CompanyModel _model : testObjects) {
			assertTrue("Company <" + _model.getId() + "> should be listed", _ids.contains(_model.getId()));
		}
	}

	/**
	 * Test whether all listed objects are readable.
	 */
	@Test
	public void testAllReadable() {
		for (CompanyModel _model : testObjects) {
			CompanyTest.get(wc, _model.getId(), Status.OK);
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
	
		List<CompanyModel> _batch = null;
		while(true) {
			_numberOfBatches++;
			_batch = CompanyTest.list(wc, null, _position, -1, Status.OK);
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
		List<CompanyModel> _objs = CompanyTest.list(wc, null, 5, 5, Status.OK);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
	
	/**
	 * Get some elements until the end of the list
	 */
	@Test
	public void testLastElements() 
	{
		int _totalMembers = calculateMembers();
		List<CompanyModel> _objs = CompanyTest.list(wc, null, (_totalMembers - 4), 4, Status.OK);
		assertEquals("list() should return correct number of elements", 4, _objs.size());		
	}
	
	/**
	 * Read some elements until after the list end
	 */
	@Test 
	public void testOverEndOfList() 
	{
		int _totalMembers = calculateMembers();
		List<CompanyModel> _objs = CompanyTest.list(wc, null, (_totalMembers - 5), 10, Status.OK);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
		
	/**
	 * Print the result of the list() operation onto stdout.
	 * @param title  the title of the log section
	 * @param list a list of CompanyModel objects
	 */
	public static void printModelList(
			String title, 
			List<CompanyModel> list) 
	{
		System.out.println("***** " + title);
		System.out.println("\tID\ttitle");
		for (CompanyModel _model : list) { 
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
		return CompanyTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK).size();
	}
}