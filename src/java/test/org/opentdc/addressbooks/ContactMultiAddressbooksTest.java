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
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/**
 * Test the creation and deletion of contacts within several different addressbooks.
 * @author Bruno Kaiser
 *
 */
public class ContactMultiAddressbooksTest extends AbstractTestClient {
	private static final String CN = "ContactMultiAddressbooksTest";
	private static AddressbookModel a1 = null;
	private static AddressbookModel a2 = null;
	private static WebClient wc = null;
	private static int before = 0;

	/**
	 * Initialize the test with several contacts
	 */
	@BeforeClass
	public static void initializeTest() {
		wc = initializeTest(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		before = ContactTest.list(wc, null, null, 0, Integer.MAX_VALUE, Status.OK, true).size();
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
	public void testContactsInMultipleAddressbooks() {
		// step 1: add contact c1 in addressbook a1			-> a1: 1, a2: 0, all: 1
		ContactModel _c1 = ContactTest.post(wc, a1.getId(), new ContactModel("a1", "c1"), Status.OK);
		checkListSizes("01", 1, 0, 1 + before);		
		
		// step 2: add contact c2 in addressbook a2			-> 1, 1, 2
		ContactModel _c2 = ContactTest.post(wc, a2.getId(), new ContactModel("a2", "c2"), Status.OK);
		checkListSizes("02", 1, 1, 2 + before);		
		
		// step 3: add contact c3 in addressbooks a1 		-> 2, 1, 3
		ContactModel _c3 = ContactTest.post(wc, a1.getId(), new ContactModel("a1a2", "c3"), Status.OK);
		checkListSizes("03", 2, 1, 3 + before);		
		
		// step 4: add contact c3 in addressbooks a2 		-> 2, 2, 3
		ContactTest.post(wc, a2.getId(), _c3, Status.OK);
		checkListSizes("04", 2, 2, 3 + before);		
		
		// step 5: remove contact c3 from addressbook a2 	-> 2, 1, 3
		ContactTest.delete(wc, a2.getId(), _c3.getId(), Status.NO_CONTENT);
		checkListSizes("05", 2, 1, 3 + before);		
		
		// step 6: remove contact c3 from addressbook a1	-> 1, 1, 3
		ContactTest.delete(wc, a1.getId(), _c3.getId(), Status.NO_CONTENT);
		checkListSizes("06", 1, 1, 3 + before);		
		
		// step 7: remove contact c2 from addressbook a2	-> 1, 0, 3
		ContactTest.delete(wc, a2.getId(), _c2.getId(), Status.NO_CONTENT);
		checkListSizes("07", 1, 0, 3 + before);		

		// step 8: remove contact c1 from addressbook a1	-> 0, 0, 3
		ContactTest.delete(wc, a1.getId(), _c1.getId(), Status.NO_CONTENT);
		checkListSizes("08", 0, 0, 3 + before);		

		// step 9: add contact c1 in addressbook a1			-> 1, 0, 3
		ContactTest.post(wc, a1.getId(), _c1, Status.OK);
		checkListSizes("09", 1, 0, 3 + before);		

		// step 10: add contact c4 in addressbook a1		-> 2, 0, 4
		ContactModel _c4 = ContactTest.post(wc, a1.getId(), new ContactModel("a1a2", "c4"), Status.OK);
		checkListSizes("10", 2, 0, 4 + before);		
		
		// step 11: add contact c4 in addressbook a2		-> 2, 1, 4
		ContactTest.post(wc, a2.getId(), _c4, Status.OK);
		checkListSizes("11", 2, 1, 4 + before);		
		
		// step 12: add contact c5 in addressbook a2		-> 2, 2, 5
		ContactModel _c5 = ContactTest.post(wc, a2.getId(), new ContactModel("a2", "c5"), Status.OK);
		checkListSizes("12", 2, 2, 5 + before);		
		
		// step 13: remove contact c1 from addressbook all	-> 1, 2, 4
		String _allAbookId = getIdOfAddressbookAll();
		ContactTest.delete(wc, _allAbookId, _c1.getId(), Status.NO_CONTENT);
		checkListSizes("13", 1, 2, 4 + before);		
		
		// step 14: remove contact c2 from addressbook all	-> 1, 2, 3
		ContactTest.delete(wc, _allAbookId, _c2.getId(), Status.NO_CONTENT);
		checkListSizes("14", 1, 2, 3 + before);		

		// step 15: remove contact c3 from addressbook all	-> 1, 2, 2
		ContactTest.delete(wc, _allAbookId, _c3.getId(), Status.NO_CONTENT);
		checkListSizes("15", 1, 2, 2 + before);		

		// step 16: remove contact c4 from addressbook all	-> 0, 1, 1
		ContactTest.delete(wc, _allAbookId, _c4.getId(), Status.NO_CONTENT);
		checkListSizes("16", 0, 1, 1 + before);		

		// step 17: remove contact c5 from addressbook all	-> 0, 0, 0
		ContactTest.delete(wc, _allAbookId, _c5.getId(), Status.NO_CONTENT);
		checkListSizes("17", 0, 0, 0 + before);		

		// step 18: add contact c6 in addressbook all	-> 0, 0, 1
		ContactModel _c6 = ContactTest.post(wc, _allAbookId, new ContactModel("all", "c6"), Status.OK);
		checkListSizes("18", 0, 0, 1 + before);	
		
		// step 19: read contact c6 from addressbook all 
		ContactModel _cm1 = ContactTest.get(wc, _allAbookId, _c6.getId(), Status.OK);
		System.out.println("19:\tget:\t" + _cm1.getId());
		
		// step 20: update contact c6 on addressbook all
		_cm1.setJobTitle("NEW_JOBTITLE");
		ContactModel _cm2 = ContactTest.put(wc, _allAbookId, _cm1, Status.OK);
		System.out.println("20:\tput:\t" + _cm2.getJobTitle());
		assertEquals("job title should have changed", "NEW_JOBTITLE", _cm2.getJobTitle());
		
		// step 21: remove contact c6 from addressbook all	-> 0, 0, 0
		ContactTest.delete(wc, _allAbookId, _c6.getId(), Status.NO_CONTENT);
		checkListSizes("21", 0, 0, 0 + before);				
	}
		
	private static int checkListSizes(String title, int a1s, int a2s, int alls) {
		int _a1 = ContactTest.list(wc, a1.getId(), null, 0, Integer.MAX_VALUE, Status.OK, false).size();
		int _a2 = ContactTest.list(wc, a2.getId(), null, 0, Integer.MAX_VALUE, Status.OK, false).size();
		int _alls = ContactTest.list(wc, null, null, 0, Integer.MAX_VALUE, Status.OK, true).size();
		System.out.println(title + "\ta1:\t" + _a1 + "\ta2:\t" + _a2 + "\tall:\t" + _alls);	
		assertEquals("size of addressbook a1 should be as expected", a1s, _a1);
		assertEquals("size of addressbook a2 should be as expected", a2s, _a2);
		assertEquals("size of allContacts should be as expected", alls, _alls);
		return _alls;
	}
	
	private static String getIdOfAddressbookAll() {
		for (AddressbookModel _model : AddressbookTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK)) {
			if (_model.getName().equalsIgnoreCase("AAA")) {
				return _model.getId();
			}
		}
		return null;
	}
			
	/* (non-Javadoc)
	 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
	 */
	protected int calculateMembers() {
		return ContactTest.list(wc, null, null, 0, Integer.MAX_VALUE, Status.OK, true).size();
	}
}