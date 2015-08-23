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
package test.org.opentdc.workrecords;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opentdc.workrecords.WorkRecordModel;
import org.opentdc.workrecords.WorkRecordsService;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.wtt.WttService;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.OrgType;
import org.opentdc.resources.ResourceModel;
import org.opentdc.resources.ResourcesService;
import org.opentdc.service.GenericService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.ContactTest;
import test.org.opentdc.addressbooks.OrgTest;
import test.org.opentdc.resources.ResourceTest;
import test.org.opentdc.wtt.CompanyTest;
import test.org.opentdc.wtt.ProjectTest;

/**
 * Testing lists of WorkRecords.
 * @author Bruno Kaiser
 *
 */
public class WorkRecordListTest extends AbstractTestClient {
	public static final String CN = "WorkRecordListTest";
	private static WebClient wc = null;
	private static WebClient wttWC = null;
	private static WebClient addressbookWC = null;
	private static WebClient resourceWC = null;
	private static CompanyModel company = null;
	private static ProjectModel project = null;
	private static AddressbookModel addressbook = null;
	private static ResourceModel resource = null;
	private static ContactModel contact = null;
	private static OrgModel org = null;
	private static ArrayList<WorkRecordModel> testObjects = null;

	/**
	 * Initialize the test with several WorkRecords
	 */
	@BeforeClass
	public static void initializeTests() {
		wc = createWebClient(ServiceUtil.WORKRECORDS_API_URL, WorkRecordsService.class);
		wttWC = createWebClient(ServiceUtil.WTT_API_URL, WttService.class);
		resourceWC = createWebClient(ServiceUtil.RESOURCES_API_URL, ResourcesService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);

		addressbook = AddressbookTest.post(addressbookWC, 
				new AddressbookModel(CN), Status.OK);
		contact = ContactTest.post(addressbookWC, addressbook.getId(), 
				new ContactModel(CN + "1", CN + "2"), Status.OK);
		org = OrgTest.post(addressbookWC, addressbook.getId(), 
				new OrgModel(CN, OrgType.SOLE), Status.OK);
		company = CompanyTest.post(wttWC, 
				new CompanyModel(CN, "MY_DESC", org.getId()), Status.OK);
		project = ProjectTest.post(wttWC, company.getId(), 
				new ProjectModel(CN, "MY_DESC"), Status.OK);
		resource = ResourceTest.post(resourceWC, 
				new ResourceModel(CN, contact.getId()), Status.OK);
		System.out.println("***** " + CN);
		testObjects = new ArrayList<WorkRecordModel>();
		for (int i = 1; i < (2 * GenericService.DEF_SIZE + 5); i++) { // if DEF_SIZE == 25 -> _limit2 = 54 (55 -1)
			testObjects.add(WorkRecordTest.post(wc, 
					WorkRecordTest.create(company, project, resource, 
							genDate(2015, (i < 30 ? 7 : 8), (i < 30 ? i : i - 29)), 
							i, i, true, true, true, CN),
					Status.OK));
		}
		System.out.println("created " + testObjects.size() + " test objects");
		printModelList("testObjects", testObjects);
	}
	
	public static Date genDate(int year, int month, int day) {
		String _dateStr = String.format("%04d%02d%02d", year, month, day);
		Date _date = ServiceUtil.parseDate(_dateStr, "yyyyMMdd");
		System.out.println("genDate(" + year + ", " + month + ", " + day + ") -> " + _date);
		return _date;
	}
	
	/**
	 * Remove all test resources used
	 */
	@AfterClass
	public static void cleanupTest() {
		AddressbookTest.delete(addressbookWC, addressbook.getId(), Status.NO_CONTENT);
		addressbookWC.close();
		
		ResourceTest.delete(resourceWC, resource.getId(), Status.NO_CONTENT);
		resourceWC.close();
		
		CompanyTest.delete(wttWC, company.getId(), Status.NO_CONTENT);
		wttWC.close();
		
		for (WorkRecordModel _model : testObjects) {
			WorkRecordTest.delete(wc, _model.getId(), Status.NO_CONTENT);
		}		
		wc.close();
	}

	/**
	 * Test whether all allocated test objects are listed.
	 */
	@Test
	public void testAllListed() {
		List<WorkRecordModel> _list = WorkRecordTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testAllListed", _list);
		ArrayList<String> _ids = new ArrayList<String>();
		for (WorkRecordModel _model : _list) {
			_ids.add(_model.getId());
		}		
		for (WorkRecordModel _model : testObjects) {
			assertTrue("WorkRecord <" + _model.getId() + "> should be listed", _ids.contains(_model.getId()));
		}
	}

	/**
	 * Test whether all listed objects are readable.
	 */
	@Test
	public void testAllReadable() {
		for (WorkRecordModel _model : testObjects) {
			WorkRecordTest.get(wc, _model.getId(), Status.OK);
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
	
		List<WorkRecordModel> _batch = null;
		while(true) {
			_numberOfBatches++;
			_batch = WorkRecordTest.list(wc, null, _position, -1, Status.OK);
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
		List<WorkRecordModel> _objs = WorkRecordTest.list(wc, null, 5, 5, Status.OK);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
	
	/**
	 * Get some elements until the end of the list
	 */
	@Test
	public void testLastElements() 
	{
		int _totalMembers = calculateMembers();
		List<WorkRecordModel> _objs = WorkRecordTest.list(wc, null, (_totalMembers - 4), 4, Status.OK);
		assertEquals("list() should return correct number of elements", 4, _objs.size());		
	}
	
	/**
	 * Read some elements until after the list end
	 */
	@Test 
	public void testOverEndOfList() 
	{
		int _totalMembers = calculateMembers();
		List<WorkRecordModel> _objs = WorkRecordTest.list(wc, null, (_totalMembers - 5), 10, Status.OK);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
	
	// test some queries
	// precondition: because of asserts on size(), these tests assume that the storage is empty.
	// this is not a realistic assumption
	// but we leave it here until WorkRecords will be composites of Activities.
	@Test
	public void testDateQueryGreaterThan()
	{
		List<WorkRecordModel> _objs = WorkRecordTest.list(wc, "startAt().greaterThan(20150801)", 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testDateQueryGreaterThan / startAt().greaterThan(20150801)", _objs);
		assertEquals("list(startAt().greaterThan(20150801)) should return 24 objects", 24, _objs.size());
	}
	
	@Test
	public void testDateQueryLessThan()
	{
		List<WorkRecordModel> _objs = WorkRecordTest.list(wc, "startAt().lessThan(20150801)", 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testDateQueryLessThan / startAt().lessThan(20150801)", _objs);
		assertEquals("list(startAt().lessThan(20150801)) should return 29 objects", 29, _objs.size());
	}

	@Test
	public void testDateQueryEquals()
	{
		List<WorkRecordModel> _objs = WorkRecordTest.list(wc, "startAt().equalTo(20150801)", 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testDateQueryEquals / startAt().equals(20150801)", _objs);
		assertEquals("list(startAt().equals(20150801)) should return 1 object", 1, _objs.size());
	}
	
	@Test
	public void testDateInterval()
	{
		List<WorkRecordModel> _objs = WorkRecordTest.list(wc, "startAt().greaterThan(20150801);startAt().lessThanOrEqualTo(20150810)", 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testDateInterval / startAt().greaterThan(20150801);startAt().lessThanOrEqualTo(20150810)", _objs);
		assertEquals("list(startAt().greaterThan(20150801);startAt().lessThanOrEqualTo(20150810)) should return 9 objects", 9, _objs.size());
	}
	
	@Test
	public void testBooleanTrue()
	{
		List<WorkRecordModel> _objs = WorkRecordTest.list(wc, "isRunning().equalTo(true)", 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testBooleanTrue / isRunning().equalTo(true)", _objs);
		assertEquals("list(isRunning().equalTo(true)) should return 54 objects", 54, _objs.size());
	}
	
	@Test
	public void testBooleanFalse()
	{
		List<WorkRecordModel> _objs = WorkRecordTest.list(wc, "isRunning().equalTo(false)", 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testBooleanFalse / isRunning().equalTo(false)", _objs);
		assertEquals("list(isRunning().equalTo(false)) should return 0 objects", 0, _objs.size());
	}
	
	@Test
	public void testBooleanNotEqualTo()
	{
		List<WorkRecordModel> _objs = WorkRecordTest.list(wc, "isRunning().notEqualTo(false)", 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testBooleanFalse / isRunning().notEqualTo(false)", _objs);
		assertEquals("list(isRunning().notEqualTo(false)) should return 54 objects", 54, _objs.size());
	}
	
	@Test
	public void testIntLessThan()
	{
		List<WorkRecordModel> _objs = WorkRecordTest.list(wc, "durationHours().lessThan(5)", 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testIntLessThan / durationHours().lessThan(5)", _objs);
		assertEquals("list(durationHours().lessThan(5)) should return 4 objects", 4, _objs.size());
	}
	
	@Test
	public void testIntLessThanOrEqualTo()
	{
		List<WorkRecordModel> _objs = WorkRecordTest.list(wc, "durationHours().lessThanOrEqualTo(5)", 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testIntLessThan / durationHours().lessThanOrEqualTo(5)", _objs);
		assertEquals("list(durationHours().lessThanOrEqualTo(5)) should return 5 objects", 5, _objs.size());
	}
	
	@Test
	public void testIntEqualTo()
	{
		List<WorkRecordModel> _objs = WorkRecordTest.list(wc, "durationHours().equalTo(5)", 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testIntEqualTo / durationHours().equalTo(5)", _objs);
		assertEquals("list(durationHours().equalTo(5)) should return 1 objects", 1, _objs.size());
	}
	
	@Test
	public void testIntNotEqualTo()
	{
		List<WorkRecordModel> _objs = WorkRecordTest.list(wc, "durationHours().notEqualTo(5)", 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testIntEqualTo / durationHours().notEqualTo(5)", _objs);
		assertEquals("list(durationHours().notEqualTo(5)) should return 53 objects", 53, _objs.size());
	}
	
	@Test
	public void testIntGreaterThan()
	{
		List<WorkRecordModel> _objs = WorkRecordTest.list(wc, "durationHours().greaterThan(50)", 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testIntGreaterThan / durationHours().greaterThan(50)", _objs);
		assertEquals("list(durationHours().greaterThan(50)) should return 4 objects", 4, _objs.size());
	}
	
	@Test
	public void testIntGreaterThanOrEqualTo()
	{
		List<WorkRecordModel> _objs = WorkRecordTest.list(wc, "durationHours().greaterThanOrEqualTo(50)", 0, Integer.MAX_VALUE, Status.OK);
		printModelList("testIntGreaterThanOrEqualTo / durationHours().greaterThanOrEqualTo(50)", _objs);
		assertEquals("list(durationHours().greaterThanOrEqualTo(50)) should return 5 objects", 5, _objs.size());
	}
	
	@Test
	public void testIntIsLike()
	{
		WorkRecordTest.list(wc, "durationHours().isLike(50)", 0, Integer.MAX_VALUE, Status.BAD_REQUEST);
	}
		
	/**
	 * Print the result of the list() operation onto stdout.
	 * @param title  the title of the log section
	 * @param list a list of AddressModel objects
	 */
	public static void printModelList(
			String title, 
			List<WorkRecordModel> list) 
	{
		System.out.println("***** " + title);
		System.out.println("\tID\tcompanyID\tprojectID\tresourceID");
		for (WorkRecordModel _model : list) { 
			System.out.println(
					"\t" + _model.getId() + 
					"\t" + _model.getCompanyId() + 
					"\t" + _model.getProjectId() + 
					"\t" + _model.getResourceId());
		}
		System.out.println("\ttotal:\t" + list.size() + " elements");
	}
	
	/* (non-Javadoc)
	 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
	 */
	protected int calculateMembers() {
		return WorkRecordTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK).size();
	}
}