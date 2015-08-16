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

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.OrgType;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/**
 * Test the creation and deletion of orgs in several addressbooks.
 * @author Bruno Kaiser
 *
 */
public class OrgMultiAddressbooksTest extends AbstractTestClient {
	private static final String CN = "OrgMultiAddressbooksTest";
	private static AddressbookModel a1 = null;
	private static AddressbookModel a2 = null;
	private static WebClient wc = null;
	private static int before = 0;

	/**
	 * Initialize the test with several orgs
	 */
	@BeforeClass
	public static void initializeTest() {
		wc = initializeTest(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		before = OrgTest.list(wc, null, null, 0, Integer.MAX_VALUE, Status.OK, true).size();
		System.out.println("***** " + CN);
		System.out.println("before: " + before);
		a1 = AddressbookTest.post(wc, new AddressbookModel(CN + "1"), Status.OK);
		a2 = AddressbookTest.post(wc, new AddressbookModel(CN + "2"), Status.OK);
		checkListSizes("initializeTest\t\t", 0, 0, 0 + before);		
	}
	
	@AfterClass
	public static void cleanupTest() {
		AddressbookTest.delete(wc, a1.getId(), Status.NO_CONTENT);
		AddressbookTest.delete(wc, a2.getId(), Status.NO_CONTENT);
		wc.close();
	}

	@Test
	public void testOrgsInMultipleAddressbooks() {
		// step 1: add org c1 in addressbook a1			-> a1: 1, a2: 0, all: 1
		OrgModel _c1 = OrgTest.post(wc, a1.getId(), new OrgModel("a1 c1", OrgType.ASSOC), Status.OK);
		checkListSizes("01", 1, 0, 1 + before);		
		
		// step 2: add org c2 in addressbook a2			-> 1, 1, 2
		OrgModel _c2 = OrgTest.post(wc, a2.getId(), new OrgModel("a2 c2", OrgType.CLUB), Status.OK);
		checkListSizes("02", 1, 1, 2 + before);		
		
		// step 3: add org c3 in addressbooks a1 		-> 2, 1, 3
		OrgModel _c3 = OrgTest.post(wc, a1.getId(), new OrgModel("a1a2 c3", OrgType.COMP), Status.OK);
		checkListSizes("03", 2, 1, 3 + before);		
		
		// step 4: add org c3 in addressbooks a2 		-> 2, 2, 3
		OrgTest.post(wc, a2.getId(), _c3, Status.OK);
		checkListSizes("04", 2, 2, 3 + before);		
		
		// step 5: remove org c3 from addressbook a2 	-> 2, 1, 3
		OrgTest.delete(wc, a2.getId(), _c3.getId(), Status.NO_CONTENT);
		checkListSizes("05", 2, 1, 3 + before);		
		
		// step 6: remove org c3 from addressbook a1	-> 1, 1, 3
		OrgTest.delete(wc, a1.getId(), _c3.getId(), Status.NO_CONTENT);
		checkListSizes("06", 1, 1, 3 + before);		
		
		// step 7: remove org c2 from addressbook a2	-> 1, 0, 3
		OrgTest.delete(wc, a2.getId(), _c2.getId(), Status.NO_CONTENT);
		checkListSizes("07", 1, 0, 3 + before);		

		// step 8: remove org c1 from addressbook a1	-> 0, 0, 3
		OrgTest.delete(wc, a1.getId(), _c1.getId(), Status.NO_CONTENT);
		checkListSizes("08", 0, 0, 3 + before);		

		// step 9: add org c1 in addressbook a1			-> 1, 0, 3
		OrgTest.post(wc, a1.getId(), _c1, Status.OK);
		checkListSizes("09", 1, 0, 3 + before);		

		// step 10: add org c4 in addressbook a1		-> 2, 0, 4
		OrgModel _c4 = OrgTest.post(wc, a1.getId(), new OrgModel("a1a2 c4", OrgType.LTD), Status.OK);
		checkListSizes("10", 2, 0, 4 + before);		
		
		// step 11: add org c4 in addressbook a2		-> 2, 1, 4
		OrgTest.post(wc, a2.getId(), _c4, Status.OK);
		checkListSizes("11", 2, 1, 4 + before);		
		
		// step 12: add org c5 in addressbook a2		-> 2, 2, 5
		OrgModel _c5 = OrgTest.post(wc, a2.getId(), new OrgModel("a2 c5", OrgType.ORGUNIT), Status.OK);
		checkListSizes("12", 2, 2, 5 + before);		
		
		// step 13: remove org c1 from addressbook all	-> 1, 2, 4
		String _allAbookId = getIdOfAddressbookAll();
		OrgTest.delete(wc, _allAbookId, _c1.getId(), Status.NO_CONTENT);
		checkListSizes("13", 1, 2, 4 + before);		
		
		// step 14: remove org c2 from addressbook all	-> 1, 2, 3
		OrgTest.delete(wc, _allAbookId, _c2.getId(), Status.NO_CONTENT);
		checkListSizes("14", 1, 2, 3 + before);		

		// step 15: remove org c3 from addressbook all	-> 1, 2, 2
		OrgTest.delete(wc, _allAbookId, _c3.getId(), Status.NO_CONTENT);
		checkListSizes("15", 1, 2, 2 + before);		

		// step 16: remove org c4 from addressbook all	-> 0, 1, 1
		OrgTest.delete(wc, _allAbookId, _c4.getId(), Status.NO_CONTENT);
		checkListSizes("16", 0, 1, 1 + before);		

		// step 17: remove org c5 from addressbook all	-> 0, 0, 0
		OrgTest.delete(wc, _allAbookId, _c5.getId(), Status.NO_CONTENT);
		checkListSizes("17", 0, 0, 0 + before);		

		// step 18: add org c6 in addressbook all	-> 0, 0, 1
		OrgModel _c6 = OrgTest.post(wc, _allAbookId, new OrgModel("all c6", OrgType.OTHER), Status.OK);
		checkListSizes("18", 0, 0, 1 + before);	
		
		// step 19: read org c6 from addressbook all 
		OrgModel _cm1 = OrgTest.get(wc, _allAbookId, _c6.getId(), Status.OK);
		System.out.println("19:\tget:\t" + _cm1.getId());
		
		// step 20: update org c6 on addressbook all
		_cm1.setName("NEW_NAME");
		OrgModel _cm2 = OrgTest.put(wc, _allAbookId, _cm1, Status.OK);
		System.out.println("20:\tput:\t" + _cm2.getName());
		assertEquals("job title should have changed", "NEW_NAME", _cm2.getName());
		
		// step 21: remove org c6 from addressbook all	-> 0, 0, 0
		OrgTest.delete(wc, _allAbookId, _c6.getId(), Status.NO_CONTENT);
		checkListSizes("21", 0, 0, 0 + before);				
	}
		
	private static int checkListSizes(String title, int a1s, int a2s, int alls) {
		int _a1 = OrgTest.list(wc, a1.getId(), null, 0, Integer.MAX_VALUE, Status.OK, false).size();
		int _a2 = OrgTest.list(wc, a2.getId(), null, 0, Integer.MAX_VALUE, Status.OK, false).size();
		int _alls = OrgTest.list(wc, null, null, 0, Integer.MAX_VALUE, Status.OK, true).size();
		System.out.println(title + "\ta1:\t" + _a1 + "\ta2:\t" + _a2 + "\tall:\t" + _alls);	
		assertEquals("size of addressbook a1 should be as expected", a1s, _a1);
		assertEquals("size of addressbook a2 should be as expected", a2s, _a2);
		assertEquals("size of allOrgs should be as expected", alls, _alls);
		return _alls;
	}
	
	private static String getIdOfAddressbookAll() {
		for (AddressbookModel _model : AddressbookTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK)) {
			if (_model.getName().equalsIgnoreCase("all")) {
				return _model.getId();
			}
		}
		return null;
	}
			
	/* (non-Javadoc)
	 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
	 */
	protected int calculateMembers() {
		return OrgTest.list(wc, null, null, 0, Integer.MAX_VALUE, Status.OK, true).size();
	}
}