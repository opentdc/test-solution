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

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.OrgType;
import org.opentdc.rates.RateModel;
import org.opentdc.rates.RatesService;
import org.opentdc.resources.ResourceModel;
import org.opentdc.resources.ResourcesService;
import org.opentdc.service.LocalizedTextModel;
import org.opentdc.service.ServiceUtil;
import org.opentdc.tags.TagModel;
import org.opentdc.tags.TagsService;
import org.opentdc.util.LanguageCode;
import org.opentdc.workrecords.WorkRecordModel;
import org.opentdc.workrecords.WorkRecordsService;
import org.opentdc.wtt.CompanyModel;
import org.opentdc.wtt.ProjectModel;
import org.opentdc.wtt.WttService;

import test.org.opentdc.AbstractTestClient;
import test.org.opentdc.addressbooks.AddressbookTest;
import test.org.opentdc.addressbooks.ContactTest;
import test.org.opentdc.addressbooks.OrgTest;
import test.org.opentdc.rates.RateTest;
import test.org.opentdc.resources.ResourceTest;
import test.org.opentdc.tags.LocalizedTextTest;
import test.org.opentdc.tags.TagTest;
import test.org.opentdc.wtt.CompanyTest;
import test.org.opentdc.wtt.ProjectTest;

/**
 * Testing references to tags.
 * @author Bruno Kaiser
 *
 */
public class TagQueryTest extends AbstractTestClient {
	private static final String CN = "TagQueryTest";
	private static WebClient wc = null;
	private static WebClient tagWC = null;
	private static WebClient wttWC = null;
	private static WebClient addressbookWC = null;
	private static WebClient resourceWC = null;
	private static WebClient rateWC = null;
	
	private static AddressbookModel addressbook = null;
	private static OrgModel org = null;
	private static CompanyModel company = null;
	private static ProjectModel project = null;
	private static ContactModel contact = null;
	private static ResourceModel resource = null;
	private static RateModel rate = null;
	private static WorkRecordModel wr1 = null;
	private static WorkRecordModel wr2 = null;
	private static WorkRecordModel wr3 = null;
	private static WorkRecordModel wr4 = null;
	private static TagModel tag1 = null;
	private static TagModel tag2 = null;
	private static TagModel tag3 = null;
	private static TagModel tag4 = null;
	private static TagModel tag5 = null;
	private static Date date; 

	@BeforeClass
	public static void initializeTests() {
		wc = createWebClient(ServiceUtil.WORKRECORDS_API_URL, WorkRecordsService.class);
		tagWC = createWebClient(ServiceUtil.TAGS_API_URL, TagsService.class);
		wttWC = createWebClient(ServiceUtil.WTT_API_URL, WttService.class);
		addressbookWC = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		resourceWC = createWebClient(ServiceUtil.RESOURCES_API_URL, ResourcesService.class);
		rateWC = createWebClient(ServiceUtil.RATES_API_URL, RatesService.class);
		
		date = new Date();
		addressbook = AddressbookTest.post(addressbookWC, new AddressbookModel(CN), Status.OK);
		contact = ContactTest.post(addressbookWC, addressbook.getId(), 
				new ContactModel(CN + "1", CN + "2"), Status.OK);
		org = OrgTest.post(addressbookWC, addressbook.getId(), 
				new OrgModel(CN, OrgType.COOP), Status.OK);
		
		company = CompanyTest.post(wttWC, 
				new CompanyModel(CN, "MY_DESC", org.getId()), Status.OK);
		project = ProjectTest.post(wttWC, company.getId(), 
				new ProjectModel(CN + "1", CN + "2"), Status.OK);
		resource = ResourceTest.post(resourceWC, 
				new ResourceModel(CN, contact.getId()), Status.OK);
		rate = RateTest.post(rateWC, 
				new RateModel(CN, 100, "MY_DESC"), Status.OK);
		tag1 = postTag(1);
		tag2 = postTag(2);
		tag3 = postTag(3);
		tag4 = postTag(4);
		tag5 = postTag(5);

		wr1 = postWR(1);
		wr2 = postWR(2);
		wr3 = postWR(3);
		wr4 = postWR(4);
		
	}
	
	private static WorkRecordModel postWR(int index) {
		WorkRecordModel _wr = WorkRecordTest.create(company, project, resource, date, 1, 30, true, true, true, CN + index);
		switch(index) {
		case 1:		_wr.setTagIdList(tag1.getId() + "," + tag3.getId() + "," + tag5.getId()); 
					break;
		case 2:		_wr.setTagIdList(tag2.getId() + "," + tag4.getId() + "," + tag5.getId()); 
					break;
		case 3:		_wr.setTagIdList(tag3.getId() + "," + tag5.getId()); 
					break;
		case 4:		_wr.setTagIdList(tag4.getId() + "," + tag5.getId()); 
					break;
		}
		WorkRecordModel _wr1 = WorkRecordTest.post(wc, _wr, Status.OK);
		System.out.println("workrecord" + index + ": " + _wr1.getId() + "\t" + _wr.getTagIdList());
		return _wr1;
	}
	
	private static TagModel postTag(int index) {
		TagModel _tag = TagTest.create(tagWC, Status.OK);
		LocalizedTextTest.post(tagWC, _tag, new LocalizedTextModel(LanguageCode.getDefaultLanguageCode(), CN + index), Status.OK);
		System.out.println("tag" + index + ": " + _tag.getId());
		return _tag;
	}
	
	@AfterClass
	public static void cleanupTest() {
		AddressbookTest.delete(addressbookWC, addressbook.getId(), Status.NO_CONTENT);
		addressbookWC.close();
		
		ResourceTest.delete(resourceWC, resource.getId(), Status.NO_CONTENT);
		resourceWC.close();
		
		CompanyTest.delete(wttWC, company.getId(), Status.NO_CONTENT);
		wttWC.close();
		
		RateTest.delete(rateWC, rate.getId(), Status.NO_CONTENT);
		rateWC.close();
		
		TagTest.delete(tagWC, tag1.getId(), Status.NO_CONTENT);
		TagTest.delete(tagWC, tag2.getId(), Status.NO_CONTENT);
		TagTest.delete(tagWC, tag3.getId(), Status.NO_CONTENT);
		TagTest.delete(tagWC, tag4.getId(), Status.NO_CONTENT);
		TagTest.delete(tagWC, tag5.getId(), Status.NO_CONTENT);
		tagWC.close();
		
		WorkRecordTest.delete(wc, wr1.getId(), Status.NO_CONTENT);
		WorkRecordTest.delete(wc, wr2.getId(), Status.NO_CONTENT);
		WorkRecordTest.delete(wc, wr3.getId(), Status.NO_CONTENT);
		WorkRecordTest.delete(wc, wr4.getId(), Status.NO_CONTENT);
		wc.close();
	}
	
	/********************************* test queries by tag *********************************/	
	@Test
	public void testTagQueryEqual1() {
		System.out.println("testTagQueryEqual1");
		List<WorkRecordModel> _wrs = WorkRecordTest.list(wc, "tagId().equalTo(" + tag1.getId() + ")", 0, Integer.MAX_VALUE, Status.OK);
		assertEquals("number of elements returned should be correct", 1, _wrs.size());
	}
	
	@Test
	public void testTagQueryEqual2() {
		System.out.println("testTagQueryEqual2");
		List<WorkRecordModel> _wrs = WorkRecordTest.list(wc, "tagId().equalTo(" + tag2.getId() + ")", 0, Integer.MAX_VALUE, Status.OK);
		assertEquals("number of elements returned should be correct", 1, _wrs.size());
	}
	
	@Test
	public void testTagQueryEqual3() {
		System.out.println("testTagQueryEqual3");
		List<WorkRecordModel> _wrs = WorkRecordTest.list(wc, "tagId().equalTo(" + tag3.getId() + ")", 0, Integer.MAX_VALUE, Status.OK);
		assertEquals("number of elements returned should be correct", 2, _wrs.size());
	}
	
	@Test
	public void testTagQueryEqual4() {
		System.out.println("testTagQueryEqual4");
		List<WorkRecordModel> _wrs = WorkRecordTest.list(wc, "tagId().equalTo(" + tag4.getId() + ")", 0, Integer.MAX_VALUE, Status.OK);
		assertEquals("number of elements returned should be correct", 2, _wrs.size());
	}
	
	@Test
	public void testTagQueryEqual5() {
		System.out.println("testTagQueryEqual5");
		List<WorkRecordModel> _wrs = WorkRecordTest.list(wc, "tagId().equalTo(" + tag5.getId() + ")", 0, Integer.MAX_VALUE, Status.OK);
		assertEquals("number of elements returned should be correct", 4, _wrs.size());
	}
	
	@Test
	public void testTagQueryNotEqual1() {
		System.out.println("testTagQueryNotEqual1");
		List<WorkRecordModel> _wrs = WorkRecordTest.list(wc, "tagId().notEqualTo(" + tag1.getId() + ")", 0, Integer.MAX_VALUE, Status.OK);
		assertEquals("number of elements returned should be correct", 3, _wrs.size());
	}

	@Test
	public void testTagQueryNotEqual2() {
		System.out.println("testTagQueryNotEqual2");
		List<WorkRecordModel> _wrs = WorkRecordTest.list(wc, "tagId().notEqualTo(" + tag2.getId() + ")", 0, Integer.MAX_VALUE, Status.OK);
		assertEquals("number of elements returned should be correct", 3, _wrs.size());
	}
	
	@Test
	public void testTagQueryNotEqual3() {
		System.out.println("testTagQueryNotEqual3");
		List<WorkRecordModel> _wrs = WorkRecordTest.list(wc, "tagId().notEqualTo(" + tag3.getId() + ")", 0, Integer.MAX_VALUE, Status.OK);
		assertEquals("number of elements returned should be correct", 2, _wrs.size());
	}
	
	@Test
	public void testTagQueryNotEqual4() {
		System.out.println("testTagQueryNotEqual4");
		List<WorkRecordModel> _wrs = WorkRecordTest.list(wc, "tagId().notEqualTo(" + tag4.getId() + ")", 0, Integer.MAX_VALUE, Status.OK);
		assertEquals("number of elements returned should be correct", 2, _wrs.size());
	}
	
	@Test
	public void testTagQueryNotEqual5() {
		System.out.println("testTagQueryNotEqual5");
		List<WorkRecordModel> _wrs = WorkRecordTest.list(wc, "tagId().notEqualTo(" + tag5.getId() + ")", 0, Integer.MAX_VALUE, Status.OK);
		assertEquals("number of elements returned should be correct", 0, _wrs.size());
	}

	@Test
	public void testTagQueryEqual3and5() {
		System.out.println("testTagQueryEqual3and5");
		List<WorkRecordModel> _wrs = WorkRecordTest.list(wc, 
				"tagId().equalTo(" + tag3.getId() + ");tagId().equalTo(" + tag5.getId() + ")", 
				0, Integer.MAX_VALUE, Status.OK);
		assertEquals("number of elements returned should be correct", 2, _wrs.size());
	}
	
	@Test
	public void testTagQueryEqual3and4() {
		System.out.println("testTagQueryEqual3and4");
		List<WorkRecordModel> _wrs = WorkRecordTest.list(wc, 
				"tagId().equalTo(" + tag3.getId() + ");tagId().equalTo(" + tag4.getId() + ")", 
				0, Integer.MAX_VALUE, Status.OK);
		assertEquals("number of elements returned should be correct", 0, _wrs.size());
	}
	
	@Test
	public void testTagQueryEqual1and3() {
		System.out.println("testTagQueryEqual1and3");
		List<WorkRecordModel> _wrs = WorkRecordTest.list(wc, 
				"tagId().equalTo(" + tag1.getId() + ");tagId().equalTo(" + tag3.getId() + ")", 
				0, Integer.MAX_VALUE, Status.OK);
		assertEquals("number of elements returned should be correct", 1, _wrs.size());
	}
	
	@Test
	public void testTagQueryEqual1and2() {
		System.out.println("testTagQueryEqual1and2");
		List<WorkRecordModel> _wrs = WorkRecordTest.list(wc, 
				"tagId().equalTo(" + tag1.getId() + ");tagId().equalTo(" + tag2.getId() + ")", 
				0, Integer.MAX_VALUE, Status.OK);
		assertEquals("number of elements returned should be correct", 0, _wrs.size());
	}
	
	@Test
	public void testTagQueryEqual5andNotEqual2() {
		System.out.println("testTagQueryEqual5andNotEqual2");
		List<WorkRecordModel> _wrs = WorkRecordTest.list(wc, 
				"tagId().equalTo(" + tag5.getId() + ");tagId().notEqualTo(" + tag2.getId() + ")", 
				0, Integer.MAX_VALUE, Status.OK);
		assertEquals("number of elements returned should be correct", 3, _wrs.size());
	}
	
	protected int calculateMembers() {
		return 1;
	}
}