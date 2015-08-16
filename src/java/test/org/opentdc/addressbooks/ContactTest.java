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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.ContactModel;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/**
 * Testing contacts.
 * @author Bruno Kaiser
 *
 */
public class ContactTest extends AbstractTestClient {
	private static final String CN = "ContactTest";
		private static AddressbookModel adb = null;
		private WebClient wc = null;
				
		@Before
		public void initializeTests() {
			wc = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
			adb = AddressbookTest.post(wc, new AddressbookModel(CN), Status.OK);
		}
		
		@After
		public void cleanupTest() {
			AddressbookTest.delete(wc, adb.getId(), Status.NO_CONTENT);
			wc.close();
		}

		/********************************** contact attribute tests *********************************/	
		@Test
		public void testEmptyConstructor() {
			ContactModel _model = new ContactModel();
			assertNull("id should not be set by empty constructor", _model.getId());
			assertNull("birthday should not be set by empty constructor", _model.getBirthday());
			assertNull("company should not be set by empty constructor", _model.getCompany());
			assertNull("department should not be set by empty constructor", _model.getDepartment());
			assertNull("firstname should not be set by empty constructor", _model.getFirstName());
			assertNull("fn should not be set by empty constructor", _model.getFn());
			assertNull("jobtitle should not be set by empty constructor", _model.getJobTitle());
			assertNull("lastname should not be set by empty constructor", _model.getLastName());
			assertNull("maidenname should not be set by empty constructor", _model.getMaidenName());
			assertNull("middlename should not be set by empty constructor", _model.getMiddleName());
			assertNull("nickname should not be set by empty constructor", _model.getNickName());
			assertNull("note should not be set by empty constructor", _model.getNote());
			assertNull("photourl should not be set by empty constructor", _model.getPhotoUrl());
			assertNull("prefix should not be set by empty constructor", _model.getPrefix());
			assertNull("suffix should not be set by empty constructor", _model.getSuffix());
		}
				
		@Test
		public void testId() {
			ContactModel _model = new ContactModel();
			assertNull("id should not be set by constructor", _model.getId());
			_model.setId("testId");
			assertEquals("id should have changed:", "testId", _model.getId());
		}

		@Test
		public void testBirthday() {
			ContactModel _model = new ContactModel();
			assertNull("birthday should not be set by empty constructor", _model.getBirthday());
			Date _bdate = new Date();
			_model.setBirthday(_bdate);
			assertEquals("birthday should have changed:", _bdate, _model.getBirthday());
		}
		
		@Test
		public void testCompany() {
			ContactModel _model = new ContactModel();
			assertNull("company should not be set by empty constructor", _model.getCompany());
			_model.setCompany("testCompany");
			assertEquals("company should have changed:", "testCompany", _model.getCompany());
		}
		
		@Test
		public void testDepartment() {
			ContactModel _model = new ContactModel();
			assertNull("department should not be set by empty constructor", _model.getDepartment());
			_model.setDepartment("testDepartment");
			assertEquals("department should have changed:", "testDepartment", _model.getDepartment());
		}
		
		@Test
		public void testFirstName() {
			ContactModel _model = new ContactModel();
			assertNull("firstName should not be set by empty constructor", _model.getFirstName());
			_model.setFirstName("testFirstName");
			assertEquals("firstName should have changed:", "testFirstName", _model.getFirstName());
		}
		
		@Test
		public void testJobTitle() {
			ContactModel _model = new ContactModel();
			assertNull("jobTitle should not be set by empty constructor", _model.getJobTitle());
			_model.setJobTitle("testJobTitle");
			assertEquals("jobTitle should have changed:", "testJobTitle", _model.getJobTitle());
		}
		
		@Test
		public void testLastName() {
			ContactModel _model = new ContactModel();
			assertNull("LastName should not be set by empty constructor", _model.getLastName());
			_model.setLastName("testLastName");
			assertEquals("LastName should have changed:", "testLastName", _model.getLastName());
		}

		@Test
		public void testMaidenName() {
			ContactModel _model = new ContactModel();
			assertNull("MaidenName should not be set by empty constructor", _model.getMaidenName());
			_model.setMaidenName("testMaidenName");
			assertEquals("MaidenName should have changed:", "testMaidenName", _model.getMaidenName());
		}

		@Test
		public void testMiddleName() {
			ContactModel _model = new ContactModel();
			assertNull("MiddleName should not be set by empty constructor", _model.getMiddleName());
			_model.setMiddleName("testMiddleName");
			assertEquals("MiddleName should have changed:", "testMiddleName", _model.getMiddleName());
		}

		@Test
		public void testNickName() {
			ContactModel _model = new ContactModel();
			assertNull("NickName should not be set by empty constructor", _model.getNickName());
			_model.setNickName("testNickName");
			assertEquals("NickName should have changed:", "testNickName", _model.getNickName());
		}

		@Test
		public void testNote() {
			ContactModel _model = new ContactModel();
			assertNull("Note should not be set by empty constructor", _model.getNote());
			_model.setNote("testNote");
			assertEquals("Note should have changed:", "testNote", _model.getNote());
		}

		@Test
		public void testPhotoUrl() {
			ContactModel _model = new ContactModel();
			assertNull("PhotoUrl should not be set by empty constructor", _model.getPhotoUrl());
			_model.setPhotoUrl("testPhotoUrl");
			assertEquals("PhotoUrl should have changed:", "testPhotoUrl", _model.getPhotoUrl());
		}

		@Test
		public void testPrefix() {
			ContactModel _model = new ContactModel();
			assertNull("Prefix should not be set by empty constructor", _model.getPrefix());
			_model.setPrefix("testPrefix");
			assertEquals("Prefix should have changed:", "testPrefix", _model.getPrefix());
		}

		@Test
		public void testSuffix() {
			ContactModel _model = new ContactModel();
			assertNull("Suffix should not be set by empty constructor", _model.getSuffix());
			_model.setSuffix("testSuffix");
			assertEquals("Suffix should have changed:", "testSuffix", _model.getSuffix());
		}

		@Test
		public void testCreatedBy() {
			ContactModel _model = new ContactModel();
			assertNull("createdBy should not be set by empty constructor", _model.getCreatedBy());
			_model.setCreatedBy("testCreatedBy");
			assertEquals("createdBy should have changed", "testCreatedBy", _model.getCreatedBy());	
		}
		
		@Test
		public void testCreatedAt() {
			ContactModel _model = new ContactModel();
			assertNull("createdAt should not be set by empty constructor", _model.getCreatedAt());
			_model.setCreatedAt(new Date());
			assertNotNull("createdAt should have changed", _model.getCreatedAt());
		}
			
		@Test
		public void testModifiedBy() {
			ContactModel _model = new ContactModel();
			assertNull("modifiedBy should not be set by empty constructor", _model.getModifiedBy());
			_model.setModifiedBy("testModifiedBy");
			assertEquals("modifiedBy should have changed", "testModifiedBy", _model.getModifiedBy());	
		}
		
		@Test
		public void testModifiedAt() {
			ContactModel _model = new ContactModel();
			assertNull("modifiedAt should not be set by empty constructor", _model.getModifiedAt());
			_model.setModifiedAt(new Date());
			assertNotNull("modifiedAt should have changed", _model.getModifiedAt());
		}

		/********************************* REST service tests *********************************/	
				
		@Test
		public void testCreateReadDeleteWithEmptyConstructor() {
			ContactModel _model1 = new ContactModel();
			assertNull("id should not be set by empty constructor", _model1.getId());
			assertNull("birthday should not be set by empty constructor", _model1.getBirthday());
			assertNull("company should not be set by empty constructor", _model1.getCompany());
			assertNull("department should not be set by empty constructor", _model1.getDepartment());
			assertNull("firstname should not be set by empty constructor", _model1.getFirstName());
			assertNull("fn should not be set by empty constructor", _model1.getFn());
			assertNull("jobtitle should not be set by empty constructor", _model1.getJobTitle());
			assertNull("lastname should not be set by empty constructor", _model1.getLastName());
			assertNull("maidenname should not be set by empty constructor", _model1.getMaidenName());
			assertNull("middlename should not be set by empty constructor", _model1.getMiddleName());
			assertNull("nickname should not be set by empty constructor", _model1.getNickName());
			assertNull("note should not be set by empty constructor", _model1.getNote());
			assertNull("photourl should not be set by empty constructor", _model1.getPhotoUrl());
			assertNull("prefix should not be set by empty constructor", _model1.getPrefix());
			assertNull("suffix should not be set by empty constructor", _model1.getSuffix());
			
			post(_model1, Status.BAD_REQUEST);
			_model1.setFirstName("testCreateReadDeleteWithEmptyConstructor");
			_model1.setLastName("Test");
			ContactModel _model2 = post(_model1, Status.OK);
			
			assertNull("create() should not change the id of the local object", _model1.getId());
			assertNull("create() should not change the birthday of the local object", _model1.getBirthday());
			assertNull("create() should not change the company of the local object", _model1.getCompany());
			assertNull("create() should not change the department of the local object", _model1.getDepartment());
			assertNotNull("create() should not change the firstName of the local object", _model1.getFirstName());
			assertNotNull("create() should not change the lastName of the local object", _model1.getLastName());
			assertNull("create() should not change the fn of the local object", _model1.getFn());
			assertNull("create() should not change the jobTitle of the local object", _model1.getJobTitle());
			assertNull("create() should not change the maidenName of the local object", _model1.getMaidenName());
			assertNull("create() should not change the middleName of the local object", _model1.getMiddleName());
			assertNull("create() should not change the nickName of the local object", _model1.getNickName());
			assertNull("create() should not change the note of the local object", _model1.getNote());
			assertNull("create() should not change the photoUrl of the local object", _model1.getPhotoUrl());
			assertNull("create() should not change the prefix of the local object", _model1.getPrefix());
			assertNull("create() should not change the suffix of the local object", _model1.getSuffix());
			
			assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
			assertNull("birthday of returned object should be null after remote create", _model2.getBirthday());   // what is the correct value of a date?
			assertNull("company of returned object should still be null after remote create", _model2.getCompany());
			assertNull("department of returned object should still be null after remote create", _model2.getDepartment());
			assertNotNull("firstName of returned object should still be null after remote create", _model2.getFirstName());
			assertNotNull("lastName of returned object should still be null after remote create", _model2.getLastName());
			assertNotNull("fn of returned object should be unchanged", _model2.getFn());
			assertNull("jobTitle of returned object should still be null after remote create", _model2.getJobTitle());
			assertNull("maidenName of returned object should still be null after remote create", _model2.getMaidenName());
			assertNull("middleName of returned object should still be null after remote create", _model2.getMiddleName());
			assertNull("nickName of returned object should still be null after remote create", _model2.getNickName());
			assertNull("note of returned object should still be null after remote create", _model2.getNote());
			assertNull("photoUrl of returned object should still be null after remote create", _model2.getPhotoUrl());
			assertNull("prefix of returned object should still be null after remote create", _model2.getPrefix());
			assertNull("suffix of returned object should still be null after remote create", _model2.getSuffix());

			ContactModel _model3 = get(_model2.getId(), Status.OK);
			assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
			assertEquals("birthday of returned object should be unchanged after remote create", _model2.getBirthday(), _model3.getBirthday());
			assertEquals("company of returned object should be unchanged after remote create", _model2.getCompany(), _model3.getCompany());
			assertEquals("department of returned object should be unchanged after remote create", _model2.getDepartment(), _model3.getDepartment());
			assertEquals("firstName of returned object should be unchanged after remote create", _model2.getFirstName(), _model3.getFirstName());
			assertEquals("fn of returned object should be unchanged after remote create", _model2.getFn(), _model3.getFn());
			assertEquals("jobTitle of returned object should be unchanged after remote create", _model2.getJobTitle(), _model3.getJobTitle());
			assertEquals("lastName of returned object should be unchanged after remote create", _model2.getLastName(), _model3.getLastName());
			assertEquals("maidenName of returned object should be unchanged after remote create", _model2.getMaidenName(), _model3.getMaidenName());
			assertEquals("middleName of returned object should be unchanged after remote create", _model2.getMiddleName(), _model3.getMiddleName());
			assertEquals("nickName of returned object should be unchanged after remote create", _model2.getNickName(), _model3.getNickName());
			assertEquals("note of returned object should be unchanged after remote create", _model2.getNote(), _model3.getNote());
			assertEquals("photoUrl of returned object should be unchanged after remote create", _model2.getPhotoUrl(), _model3.getPhotoUrl());
			assertEquals("prefix of returned object should be unchanged after remote create", _model2.getPrefix(), _model3.getPrefix());
			assertEquals("suffix of returned object should be unchanged after remote create", _model2.getSuffix(), _model3.getSuffix());

			delete(_model3.getId(), Status.NO_CONTENT);
		}
		
		@Test
		public void testCreateReadDelete() {
			Date _bdate = new Date();
			ContactModel _model1 = setDefaultValues(new ContactModel(), _bdate, 1);
			assertNull("id should not be set by constructor", _model1.getId());
			assertEquals("birthday should be set correctly", _bdate, _model1.getBirthday());
			assertEquals("company should be set correctly", "MY_COMPANY1", _model1.getCompany());
			assertEquals("department should be set correctly", "MY_DEPT1", _model1.getDepartment());
			assertEquals("firstName should be set correctly", "MY_FNAME1", _model1.getFirstName());
			assertEquals("jobTitle should be set correctly", "MY_JOBTITLE1", _model1.getJobTitle());
			assertEquals("lastName should be set correctly", "MY_LNAME1", _model1.getLastName());
			assertEquals("maidenName should be set correctly", "MY_MNAME1", _model1.getMaidenName());
			assertEquals("middleName should be set correctly", "MY_MNAME1", _model1.getMiddleName());
			assertEquals("nickName should be set correctly", "MY_NNAME1", _model1.getNickName());
			assertEquals("note should be set correctly", "MY_NOTE1", _model1.getNote());
			assertEquals("photoUrl should be set correctly", "MY_PHOTO_URL1", _model1.getPhotoUrl());
			assertEquals("prefix should be set correctly", "MY_PREFIX1", _model1.getPrefix());
			assertEquals("suffix should be set correctly", "MY_SUFFIX1", _model1.getSuffix());
			
			ContactModel _model2 = post(_model1, Status.OK);
			
			assertNull("id should still be null", _model1.getId());
			assertEquals("birthday should be unchanged", _bdate, _model1.getBirthday());
			assertEquals("company should be unchanged", "MY_COMPANY1", _model1.getCompany());
			assertEquals("department should be unchanged", "MY_DEPT1", _model1.getDepartment());
			assertEquals("firstName should be unchanged", "MY_FNAME1", _model1.getFirstName());
			assertNull("fn should be empty", _model1.getFn());
			assertEquals("jobTitle should be unchanged", "MY_JOBTITLE1", _model1.getJobTitle());
			assertEquals("lastName should be unchanged", "MY_LNAME1", _model1.getLastName());
			assertEquals("maidenName should be unchanged", "MY_MNAME1", _model1.getMaidenName());
			assertEquals("middleName should be unchanged", "MY_MNAME1", _model1.getMiddleName());
			assertEquals("nickName should be unchanged", "MY_NNAME1", _model1.getNickName());
			assertEquals("note should be unchanged", "MY_NOTE1", _model1.getNote());
			assertEquals("photoUrl should be unchanged", "MY_PHOTO_URL1", _model1.getPhotoUrl());
			assertEquals("prefix should be unchanged", "MY_PREFIX1", _model1.getPrefix());
			assertEquals("suffix should be unchanged", "MY_SUFFIX1", _model1.getSuffix());
			
			assertNotNull("id of returned object should be set", _model2.getId());
			assertEquals("birthday should be unchanged", _bdate, _model2.getBirthday());
			assertEquals("company should be unchanged", "MY_COMPANY1", _model2.getCompany());
			assertEquals("department should be unchanged", "MY_DEPT1", _model2.getDepartment());
			assertEquals("firstName should be unchanged", "MY_FNAME1", _model2.getFirstName());
			assertNotNull("fn should be set", _model2.getFn());
			assertEquals("jobTitle should be unchanged", "MY_JOBTITLE1", _model2.getJobTitle());
			assertEquals("lastName should be unchanged", "MY_LNAME1", _model2.getLastName());
			assertEquals("maidenName should be unchanged", "MY_MNAME1", _model2.getMaidenName());
			assertEquals("middleName should be unchanged", "MY_MNAME1", _model2.getMiddleName());
			assertEquals("nickName should be unchanged", "MY_NNAME1", _model2.getNickName());
			assertEquals("note should be unchanged", "MY_NOTE1", _model2.getNote());
			assertEquals("photoUrl should be unchanged", "MY_PHOTO_URL1", _model2.getPhotoUrl());
			assertEquals("prefix should be unchanged", "MY_PREFIX1", _model2.getPrefix());
			assertEquals("suffix should be unchanged", "MY_SUFFIX1", _model2.getSuffix());
			
			ContactModel _model3 = get(_model2.getId(), Status.OK);
			assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
			assertEquals("birthday should be the same", _model2.getBirthday(), _model3.getBirthday());
			assertEquals("company should be the same", _model2.getCompany(), _model3.getCompany());
			assertEquals("department should be the same", _model2.getDepartment(), _model3.getDepartment());
			assertEquals("firstName should be the same", _model2.getFirstName(), _model3.getFirstName());
			assertEquals("fn should be the same", _model2.getFn(), _model3.getFn());
			assertEquals("jobTitle should be the same", _model2.getJobTitle(), _model3.getJobTitle());
			assertEquals("lastName should be the same", _model2.getLastName(), _model3.getLastName());
			assertEquals("maidenName should be the same", _model2.getMaidenName(), _model3.getMaidenName());
			assertEquals("middleName should be the same", _model2.getMiddleName(), _model3.getMiddleName());
			assertEquals("nickName should be the same", _model2.getNickName(), _model3.getNickName());
			assertEquals("note should be the same", _model2.getNote(), _model3.getNote());
			assertEquals("photoUrl should be the same", _model2.getPhotoUrl(), _model3.getPhotoUrl());
			assertEquals("prefix should be the same", _model2.getPrefix(), _model3.getPrefix());
			assertEquals("suffix should be the same", _model2.getSuffix(), _model3.getSuffix());

			delete(_model3.getId(), Status.NO_CONTENT);
		}
		
		@Test
		public void testClientSideId() {
			ContactModel _model1 = new ContactModel();
			_model1.setFirstName("testClientSideId");
			_model1.setLastName("Test");
			_model1.setId("LOCAL_ID");
			assertEquals("id should have changed", "LOCAL_ID", _model1.getId());
			post(_model1, Status.BAD_REQUEST);
		}
		
		@Test
		public void testDuplicateId() {
			ContactModel _model = new ContactModel();
			_model.setFirstName("testDuplicateId");
			_model.setLastName("Test");
			ContactModel _model1 = post(_model, Status.OK);
			
			ContactModel _model2 = new ContactModel();
			_model2.setFirstName("testDuplicateId");
			_model2.setLastName("Test");
			_model2.setId(_model1.getId());		// wrongly create a 2nd ContactModel object with the same ID
			post(_model2, Status.CONFLICT);
			
			delete(_model1.getId(), Status.NO_CONTENT);
		}
		
		@Test
		public void testList() {
			List<ContactModel> _listBefore = list(null, Status.OK);
			ArrayList<ContactModel> _localList = new ArrayList<ContactModel>();	
			for (int i = 0; i < LIMIT; i++) {
				_localList.add(post(new ContactModel("testList" + i, "Test"), Status.OK));
			}
			assertEquals("correct number of contacts should be created", LIMIT, _localList.size());
			
			List<ContactModel> _listAfter = list(null, Status.OK);		
			assertEquals("list() should return the correct number of contacts", _listBefore.size() + LIMIT, _listAfter.size());

			ArrayList<String> _remoteListIds = new ArrayList<String>();
			for (ContactModel _model : _listAfter) {
				_remoteListIds.add(_model.getId());
			}
			for (ContactModel _model : _localList) {
				assertTrue("contact <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
			}
			for (ContactModel _model : _localList) {
				get(_model.getId(), Status.OK);
			}
			for (ContactModel _model : _localList) {
				delete(_model.getId(), Status.NO_CONTENT);
			}
		}
		
		@Test
		public void testCreate() {	
			Date _bdate1 = new Date(1000);
			ContactModel _model = setDefaultValues(new ContactModel(), _bdate1, 1);
			_model.setFirstName("MY_FNAME1");
			_model.setLastName("MY_LNAME1");
			ContactModel _model1 = post(_model, Status.OK);

			Date _bdate2 = new Date(2000);
			_model = setDefaultValues(new ContactModel(), _bdate2, 2);
			_model.setFirstName("MY_FNAME2");
			_model.setLastName("MY_LNAME2");
			ContactModel _model2 = post(_model, Status.OK);
			
			assertNotNull("ID should be set", _model1.getId());
			assertNotNull("ID should be set", _model2.getId());
			assertThat(_model2.getId(), not(equalTo(_model1.getId())));
			
			assertEquals("birthday should be unchanged", _bdate1, _model1.getBirthday());
			assertEquals("company should be unchanged", "MY_COMPANY1", _model1.getCompany());
			assertEquals("department should be unchanged", "MY_DEPT1", _model1.getDepartment());
			assertEquals("firstName should be unchanged", "MY_FNAME1", _model1.getFirstName());
			assertNotNull("fn should be set", _model1.getFn());
			assertEquals("jobTitle should be unchanged", "MY_JOBTITLE1", _model1.getJobTitle());
			assertEquals("lastName should be unchanged", "MY_LNAME1", _model1.getLastName());
			assertEquals("maidenName should be unchanged", "MY_MNAME1", _model1.getMaidenName());
			assertEquals("middleName should be unchanged", "MY_MNAME1", _model1.getMiddleName());
			assertEquals("nickName should be unchanged", "MY_NNAME1", _model1.getNickName());
			assertEquals("note should be unchanged", "MY_NOTE1", _model1.getNote());
			assertEquals("photoUrl should be unchanged", "MY_PHOTO_URL1", _model1.getPhotoUrl());
			assertEquals("prefix should be unchanged", "MY_PREFIX1", _model1.getPrefix());
			assertEquals("suffix should be unchanged", "MY_SUFFIX1", _model1.getSuffix());
			
			assertEquals("birthday should be unchanged", _bdate2, _model2.getBirthday());
			assertEquals("company should be unchanged", "MY_COMPANY2", _model2.getCompany());
			assertEquals("department should be unchanged", "MY_DEPT2", _model2.getDepartment());
			assertEquals("firstName should be unchanged", "MY_FNAME2", _model2.getFirstName());
			assertNotNull("fn should be set", _model2.getFn());
			assertEquals("jobTitle should be unchanged", "MY_JOBTITLE2", _model2.getJobTitle());
			assertEquals("lastName should be unchanged", "MY_LNAME2", _model2.getLastName());
			assertEquals("maidenName should be unchanged", "MY_MNAME2", _model2.getMaidenName());
			assertEquals("middleName should be unchanged", "MY_MNAME2", _model2.getMiddleName());
			assertEquals("nickName should be unchanged", "MY_NNAME2", _model2.getNickName());
			assertEquals("note should be unchanged", "MY_NOTE2", _model2.getNote());
			assertEquals("photoUrl should be unchanged", "MY_PHOTO_URL2", _model2.getPhotoUrl());
			assertEquals("prefix should be unchanged", "MY_PREFIX2", _model2.getPrefix());
			assertEquals("suffix should be unchanged", "MY_SUFFIX2", _model2.getSuffix());

			delete(_model1.getId(), Status.NO_CONTENT);
			delete(_model2.getId(), Status.NO_CONTENT);
		}
		
		@Test
		public void testDoubleCreate() {	
			ContactModel _model = post(new ContactModel("testDoubleCreate", "Test"), Status.OK);
			assertNotNull("ID should be set:", _model.getId());		
			post(_model, Status.CONFLICT);
			delete(_model.getId(), Status.NO_CONTENT);
		}
		
		@Test
		public void testRead() {
			ArrayList<ContactModel> _localList = new ArrayList<ContactModel>();
			for (int i = 0; i < LIMIT; i++) {
				_localList.add(post(new ContactModel("testRead" + i, "Test"), Status.OK));
			}
			for (ContactModel _model : _localList) {
				get(_model.getId(), Status.OK);
			}
			List<ContactModel> _remoteList = list(null, Status.OK);
			for (ContactModel _model : _remoteList) {
				assertEquals("ID should be unchanged when reading a contact", _model.getId(), get(_model.getId(), Status.OK).getId());						
			}
			for (ContactModel _model : _localList) {
				delete(_model.getId(), Status.NO_CONTENT);
			}
		}
			
		@Test
		public void testMultiRead() {
			ContactModel _model = setDefaultValues(new ContactModel(), new Date(1000), 1);
			_model.setFirstName("testMultiRead");
			_model.setLastName("Test");
			ContactModel _model1 = post(_model, Status.OK);
			ContactModel _model2 = get(_model1.getId(), Status.OK);
			assertEquals("ID should be unchanged after read", _model1.getId(), _model2.getId());		
			ContactModel _model3 = get(_model1.getId(), Status.OK);		
			
			assertEquals("ID should be the same:", _model1.getId(), _model2.getId());
			assertEquals("birthday should be the same", _model1.getBirthday(), _model2.getBirthday());
			assertEquals("company should be the same", _model1.getCompany(), _model2.getCompany());
			assertEquals("department should be the same", _model1.getDepartment(), _model2.getDepartment());
			assertEquals("firstName should be the same", _model1.getFirstName(), _model2.getFirstName());
			assertEquals("fn should be the same", _model1.getFn(), _model2.getFn());
			assertEquals("jobTitle should be the same", _model1.getJobTitle(), _model2.getJobTitle());
			assertEquals("lastName should be the same", _model1.getLastName(), _model2.getLastName());
			assertEquals("maidenName should be the same", _model1.getMaidenName(), _model2.getMaidenName());
			assertEquals("middleName should be the same", _model1.getMiddleName(), _model2.getMiddleName());
			assertEquals("nickName should be the same", _model1.getNickName(), _model2.getNickName());
			assertEquals("note should be the same", _model1.getNote(), _model2.getNote());
			assertEquals("photoUrl should be the same", _model1.getPhotoUrl(), _model2.getPhotoUrl());
			assertEquals("prefix should be the same", _model1.getPrefix(), _model2.getPrefix());
			assertEquals("suffix should be the same", _model1.getSuffix(), _model2.getSuffix());
			
			
			assertEquals("ID should be the same:", _model2.getId(), _model3.getId());
			assertEquals("birthday should be the same", _model2.getBirthday(), _model3.getBirthday());
			assertEquals("company should be the same", _model2.getCompany(), _model3.getCompany());
			assertEquals("department should be the same", _model2.getDepartment(), _model3.getDepartment());
			assertEquals("firstName should be the same", _model2.getFirstName(), _model3.getFirstName());
			assertEquals("fn should be the same", _model2.getFn(), _model3.getFn());
			assertEquals("jobTitle should be the same", _model2.getJobTitle(), _model3.getJobTitle());
			assertEquals("lastName should be the same", _model2.getLastName(), _model3.getLastName());
			assertEquals("maidenName should be the same", _model2.getMaidenName(), _model3.getMaidenName());
			assertEquals("middleName should be the same", _model2.getMiddleName(), _model3.getMiddleName());
			assertEquals("nickName should be the same", _model2.getNickName(), _model3.getNickName());
			assertEquals("note should be the same", _model2.getNote(), _model3.getNote());
			assertEquals("photoUrl should be the same", _model2.getPhotoUrl(), _model3.getPhotoUrl());
			assertEquals("prefix should be the same", _model2.getPrefix(), _model3.getPrefix());
			assertEquals("suffix should be the same", _model2.getSuffix(), _model3.getSuffix());
			
			delete(_model1.getId(), Status.NO_CONTENT);
		}
		
		@Test
		public void testUpdate() 
		{	
			ContactModel _model1 = post(new ContactModel("testUpdate", "Test"), Status.OK);
			Date _bdate1 = new Date(3000);
			ContactModel _model2 = put(setDefaultValues(_model1, _bdate1, 2), Status.OK);
			
			assertNotNull("ID should be set", _model2.getId());
			assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());	
			assertEquals("birthday should be set correctly", _bdate1, _model2.getBirthday());
			assertEquals("company should be set correctly", "MY_COMPANY2", _model2.getCompany());
			assertEquals("department should be set correctly", "MY_DEPT2", _model2.getDepartment());
			assertEquals("firstName should be set correctly", "MY_FNAME2", _model2.getFirstName());
			assertNotNull("fn should be set", _model2.getFn());
			assertEquals("jobTitle should be set correctly", "MY_JOBTITLE2", _model2.getJobTitle());
			assertEquals("lastName should be set correctly", "MY_LNAME2", _model2.getLastName());
			assertEquals("maidenName should be set correctly", "MY_MNAME2", _model2.getMaidenName());
			assertEquals("middleName should be set correctly", "MY_MNAME2", _model2.getMiddleName());
			assertEquals("nickName should be set correctly", "MY_NNAME2", _model2.getNickName());
			assertEquals("note should be set correctly", "MY_NOTE2", _model2.getNote());
			assertEquals("photoUrl should be set correctly", "MY_PHOTO_URL2", _model2.getPhotoUrl());
			assertEquals("prefix should be set correctly", "MY_PREFIX2", _model2.getPrefix());
			assertEquals("suffix should be set correctly", "MY_SUFFIX2", _model2.getSuffix());

			Date _bdate2 = new Date(4000);
			ContactModel _model3 = put(setDefaultValues(_model1, _bdate2, 3), Status.OK);

			assertNotNull("ID should be set", _model3.getId());
			assertEquals("ID should be unchanged", _model1.getId(), _model3.getId());
			assertEquals("birthday should be set correctly", _bdate2, _model3.getBirthday());
			assertEquals("company should be set correctly", "MY_COMPANY3", _model3.getCompany());
			assertEquals("department should be set correctly", "MY_DEPT3", _model3.getDepartment());
			assertEquals("firstName should be set correctly", "MY_FNAME3", _model3.getFirstName());
			assertNotNull("fn should be set", _model3.getFn());
			assertEquals("jobTitle should be set correctly", "MY_JOBTITLE3", _model3.getJobTitle());
			assertEquals("lastName should be set correctly", "MY_LNAME3", _model3.getLastName());
			assertEquals("maidenName should be set correctly", "MY_MNAME3", _model3.getMaidenName());
			assertEquals("middleName should be set correctly", "MY_MNAME3", _model3.getMiddleName());
			assertEquals("nickName should be set correctly", "MY_NNAME3", _model3.getNickName());
			assertEquals("note should be set correctly", "MY_NOTE3", _model3.getNote());
			assertEquals("photoUrl should be set correctly", "MY_PHOTO_URL3", _model3.getPhotoUrl());
			assertEquals("prefix should be set correctly", "MY_PREFIX3", _model3.getPrefix());
			assertEquals("suffix should be set correctly", "MY_SUFFIX3", _model3.getSuffix());
			
			delete(_model1.getId(), Status.NO_CONTENT);
		}

		@Test
		public void testDelete() 
		{
			ContactModel _model1 = post(new ContactModel("testDelete", "Test"), Status.OK);
			ContactModel _model2 = get(_model1.getId(), Status.OK);
			assertEquals("ID should be unchanged when reading a contact", _model1.getId(), _model2.getId());						
			delete(_model1.getId(), Status.NO_CONTENT);
			get(_model1.getId(), Status.NOT_FOUND);
			get(_model1.getId(), Status.NOT_FOUND);
		}
		
		@Test
		public void testDoubleDelete() 
		{
			ContactModel _model1 = post(new ContactModel("testDoubleDelete", "Test"), Status.OK);
			get(_model1.getId(), Status.OK);
			delete(_model1.getId(), Status.NO_CONTENT);
			get(_model1.getId(), Status.NOT_FOUND);
			delete(_model1.getId(), Status.NOT_FOUND);
			get(_model1.getId(), Status.NOT_FOUND);
		}
					
		@Test
		public void testModifications() 
		{
			ContactModel _model1 = post(new ContactModel("testModifications", "Test"), Status.OK);			
			assertNotNull("create() should set createdAt", _model1.getCreatedAt());
			assertNotNull("create() should set createdBy", _model1.getCreatedBy());
			assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
			assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
			assertEquals("createdAt and modifiedAt should be identical after create()", _model1.getCreatedAt(), _model1.getModifiedAt());
			assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());	
			_model1.setFirstName("MY_NAME2");
			_model1.setLastName("Test");
			ContactModel _model2 = put(_model1, Status.OK);
			assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
			assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
			assertThat(_model2.getModifiedAt(), not(equalTo(_model2.getCreatedAt())));
			// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
			// assertThat(_model2.getModifiedBy(), not(equalTo(_model2.getCreatedBy())));
			_model1.setModifiedBy("MYSELF");
			_model1.setModifiedAt(new Date(1000));
			ContactModel _model3 = put(_model1, Status.OK);
			
			// test, that modifiedBy really ignored the client-side value "MYSELF"
			assertThat(_model1.getModifiedBy(), not(equalTo(_model3.getModifiedBy())));
			// check whether the client-side modifiedAt() is ignored
			assertThat(_model1.getModifiedAt(), not(equalTo(_model3.getModifiedAt())));

			delete(_model1.getId(), Status.NO_CONTENT);
		}
		
		// fn=null, firstName="FirstName", lastName=null -> fn="FirstName"
		@Test
		public void testFnCreate1() {
			ContactModel _model = post(new ContactModel("FirstName", null), Status.OK);
			assertNotNull("create() should set a valid fn", _model.getFn());
			assertEquals("create() should not change the firstName", "FirstName", _model.getFirstName());
			assertNull("create() should not change the lastName", _model.getLastName());
			delete(_model.getId(), Status.NO_CONTENT);
		}
		
		// fn=null, firstName=null, lastName="LastName" -> fn="LastName"
		@Test
		public void testFnCreate2() {
			ContactModel _model = post(new ContactModel(null, "LastName"), Status.OK);
			assertNotNull("create() should set a valid fn", _model.getFn());
			assertNull("create() should not change the firstName", _model.getFirstName());
			assertEquals("create() should not change the lastName", "LastName", _model.getLastName());
			delete(_model.getId(), Status.NO_CONTENT);
		}
		
		// fn=null, firstName="FirstName", lastName="LastName" -> fn="FirstName LastName"
		@Test
		public void testFnCreate3() {
			ContactModel _model = post(new ContactModel("FirstName", "LastName"), Status.OK);
			assertNotNull("create() should set a valid fn", _model.getFn());
			assertEquals("create() should not change the firstName", "FirstName", _model.getFirstName());
			assertEquals("create() should not change the lastName", "LastName", _model.getLastName());
			delete(_model.getId(), Status.NO_CONTENT);
		}
			
		// fn=null, firstName=null, lastName=null -> BAD_REQUEST 
		@Test
		public void testFnCreate4() {		
			post(new ContactModel(null, null), Status.BAD_REQUEST);
		}
						
		// fn="FN", firstName="FirstName", lastName="LastName" -> fn="FN"
		@Test
		public void testFnCreate5() {
			ContactModel _model1 = new ContactModel();
			_model1.setFirstName("firstName");
			_model1.setFn("fn");
			_model1.setLastName("lastName");
			ContactModel _model2 = post(_model1, Status.OK);
			assertNotNull("create() should set a valid fn", _model2.getFn());
			assertThat(_model1.getFn(), not(equalTo(_model2.getFn())));   // fn should be derived
			assertEquals("create() should not change the firstName", "firstName", _model2.getFirstName());
			assertEquals("create() should not change the lastName", "lastName", _model2.getLastName());
			delete(_model2.getId(), Status.NO_CONTENT);
		}
		
		// update  firstName -> should change fn, but not lastName
		@Test
		public void testFnUpdateFirstName() {
			ContactModel _model1 = post(new ContactModel("firstName1", "lastName1"), Status.OK);
			_model1.setFirstName("firstName2");
			ContactModel _model2 = put(_model1, Status.OK);
			assertNotNull("update should set a valid fn", _model2.getFn());
			assertThat(_model1.getFn(), not(equalTo(_model2.getFn())));  // fn should be derived
			assertEquals("create() should change the firstName", "firstName2", _model2.getFirstName());
			assertEquals("create() should not change the lastName", "lastName1", _model2.getLastName());
			delete(_model2.getId(), Status.NO_CONTENT);
		}
		
		// update lastName -> should change fn, but not firstName
		@Test
		public void testFnUpdateLastName() {
			ContactModel _model1 = post(new ContactModel("firstName1", "lastName1"), Status.OK);
			_model1.setLastName("lastName2");
			ContactModel _model2 = put(_model1, Status.OK);
			assertNotNull("update should set a valid fn", _model2.getFn());
			assertThat(_model1.getFn(), not(equalTo(_model2.getFn())));  // fn should be derived
			assertEquals("create() should not change the firstName", "firstName1", _model2.getFirstName());
			assertEquals("create() should change the lastName", "lastName2", _model2.getLastName());
			delete(_model2.getId(), Status.NO_CONTENT);
		}

		/********************************** helper methods *********************************/	
		/**
		 * Retrieve a list of ContactModel from AddressbooksService by executing a HTTP GET request.
		 * This uses neither position nor size queries.
		 * @param query the URL query to use
		 * @param expectedStatus the expected HTTP status to test on
		 * @return a List of ContactModel objects in JSON format
		 */
		public List<ContactModel> list(
				String query, 
				Status expectedStatus) {
			return list(wc, adb.getId(), query, -1, Integer.MAX_VALUE, expectedStatus, false);
		}
		
		/**
		 * Retrieve a list of ContactModel from AddressbooksService by executing a HTTP GET request.
		 * @param webClient the WebClient for the AddressbooksService
		 * @param aid the ID of the addressbook to be listed
		 * @param query the URL query to use
		 * @param position the position to start a batch with
		 * @param size the size of a batch
		 * @param expectedStatus the expected HTTP status to test on
		 * @param listAllContacts if true, allContacts is called, else a normal list GET
		 * @return a List of ContactModel objects in JSON format
		 */
		public static List<ContactModel> list(
				WebClient webClient, 
				String aid,
				String query, 
				int position,
				int size,
				Status expectedStatus,
				boolean listAllContacts) {
			webClient.resetQuery();
			if (listAllContacts == true) {
				webClient.replacePath("/").path("allContacts");
			} else {
				webClient.replacePath("/").path(aid).path(ServiceUtil.CONTACT_PATH_EL);
			}
			Response _response = executeListQuery(webClient, query, position, size);
			List<ContactModel> _list = null;
			if (expectedStatus != null) {
				assertEquals("list() should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
			}
			if (_response.getStatus() == Status.OK.getStatusCode()) {
				_list = new ArrayList<ContactModel>(webClient.getCollection(ContactModel.class));
				// System.out.println("list(webClient, " + query + ", " + position + ", " + size + ", " + expectedStatus.toString() + ", " + listAllContacts + ") ->" + _list.size());
			}
			return _list;
		}
		
		/**
		 * Create a new ContactModel on the server by executing a HTTP POST request.
		 * @param model the ContactModel to post
		 * @param expectedStatus the expected HTTP status to test on; if this is null, it will not be tested
		 * @return the created ContactModel
		 */
		private ContactModel post(
				ContactModel model,
				Status expectedStatus) {
			return post(wc, adb.getId(), model, expectedStatus);
		}

		/**
		 * Create a new ContactModel on the server by executing a HTTP POST request.
		 * @param webClient the WebClient representing the AddressbooksService
		 * @param aid the ID of the addressbook to be listed
		 * @param model the ContactModel data to create on the server
		 * @param exceptedStatus the expected HTTP status to test on
		 * @return the created ContactModel
		 */
		public static ContactModel post(
				WebClient webClient,
				String aid,
				ContactModel model,
				Status expectedStatus) 
		{
			Response _response = webClient.replacePath("/").path(aid).path(ServiceUtil.CONTACT_PATH_EL).post(model);
			if (expectedStatus != null) {
				assertEquals("POST should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
			}
			if (_response.getStatus() == Status.OK.getStatusCode()) {
				return _response.readEntity(ContactModel.class);
			} else {
				return null;
			}
		}		
		
		/**
		 * Fill the attributes of a ContactModel with default values.
		 * @param model the ContactModel to fill with default values.
		 * @param bdate the birth date
		 * @param suffix a numeric suffix to apply (for testing purposes)
		 * @return the ContactModel filled with default values
		 */
		private ContactModel setDefaultValues(
				ContactModel model, 
				Date bdate, 
				int suffix) 
		{
			model.setBirthday(bdate);
			model.setCompany("MY_COMPANY" + suffix);
			model.setDepartment("MY_DEPT" + suffix);
			model.setFirstName("MY_FNAME" + suffix);
			model.setJobTitle("MY_JOBTITLE" + suffix);
			model.setLastName("MY_LNAME" + suffix);
			model.setMaidenName("MY_MNAME" + suffix);
			model.setMiddleName("MY_MNAME" + suffix);
			model.setNickName("MY_NNAME" + suffix);
			model.setNote("MY_NOTE" + suffix);
			model.setPhotoUrl("MY_PHOTO_URL" + suffix);
			model.setPrefix("MY_PREFIX" + suffix);
			model.setSuffix("MY_SUFFIX" + suffix);
			return model;
		}
		
		/**
		 * Read the ContactModel with id from AddressbooksService by executing a HTTP GET method.
		 * @param id the id of the ContactModel to retrieve
		 * @param expectedStatus the expected HTTP status to test on
		 * @return the retrieved ContactModel object in JSON format
		 */
		private ContactModel get(
				String id, 
				Status expectedStatus) {
			return get(wc, adb.getId(), id, expectedStatus);
		}
		
		/**
		 * Read the ContactModel with id from AddressbooksService by executing a HTTP GET method.
		 * @param webClient the web client representing the AddressbooksService
		 * @param aid the ID of the addressbook 
		 * @param id the id of the ContactModel to retrieve
		 * @param expectedStatus  the expected HTTP status to test on
		 * @return the retrieved ContactModel object in JSON format
		 */
		public static ContactModel get(
				WebClient webClient,
				String aid,
				String id,
				Status expectedStatus) {
			Response _response = webClient.replacePath("/").path(aid).path(ServiceUtil.CONTACT_PATH_EL).path(id).get();
			if (expectedStatus != null) {
				assertEquals("GET should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
			}
			if (_response.getStatus() == Status.OK.getStatusCode()) {
				return _response.readEntity(ContactModel.class);
			} else {
				return null;
			}
		}
		
		/**
		 * Update a ContactModel on the AddressbooksService by executing a HTTP PUT method.
		 * @param model the new ContactModel data
		 * @param expectedStatus the expected HTTP status to test on
		 * @return the updated ContactModel object in JSON format
		 */
		public ContactModel put(
				ContactModel model, 
				Status expectedStatus) {
			return put(wc, adb.getId(), model, expectedStatus);
		}
		
		/**
		 * Update a ContactModel on the AddressbooksService by executing a HTTP PUT method.
		 * @param webClient the web client representing the AddressbooksService
		 * @param aid the ID of the addressbook 
		 * @param model the new ContactModel data
		 * @param expectedStatus the expected HTTP status to test on
		 * @return the updated ContactModel object in JSON format
		 */
		public static ContactModel put(
				WebClient webClient,
				String aid,
				ContactModel model,
				Status expectedStatus) {
			webClient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			Response _response = webClient.replacePath("/").path(aid).path(ServiceUtil.CONTACT_PATH_EL).path(model.getId()).put(model);
			if (expectedStatus != null) {
				assertEquals("PUT should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
			}
			if (_response.getStatus() == Status.OK.getStatusCode()) {
				return _response.readEntity(ContactModel.class);
			} else {
				return null;
			}
		}

		/**
		 * Delete the ContactModel with id on the AddressbooksService by executing a HTTP DELETE method.
		 * @param id the id of the ContactModel object to delete
		 * @param expectedStatus the expected HTTP status to test on
		 */
		public void delete(
				String id, 
				Status expectedStatus) {
			delete(wc, adb.getId(), id, expectedStatus);
		}
		
		/**
		 * Delete the ContactModel with id on the AddressbooksService by executing a HTTP DELETE method.
		 * @param webClient the WebClient for the AddressbooksService
		 * @param aid the ID of the addressbook 
		 * @param id the id of the ContactModel object to delete
		 * @param expectedStatus the expected HTTP status to test on
		 */
		public static void delete(
				WebClient webClient,
				String aid,
				String id,
				Status expectedStatus) {
			Response _response = webClient.replacePath("/").path(aid).path(ServiceUtil.CONTACT_PATH_EL).path(id).delete();	
			if (expectedStatus != null) {
				assertEquals("DELETE should return with correct status", expectedStatus.getStatusCode(), _response.getStatus());
			}
		}		
		
		/* (non-Javadoc)
		 * @see test.org.opentdc.AbstractTestClient#calculateMembers()
		 */
		protected int calculateMembers() {
			return AddressbookTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK).size();
		}
}
