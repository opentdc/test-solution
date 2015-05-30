package test.org.opentdc.addressbooks;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.ContactModel;

import test.org.opentdc.AbstractTestClient;

public class ContactTest extends AbstractTestClient<AddressbooksService> {
		
		private static final String API = "api/addressbooks/";
		public static final String PATH_EL_CONTACT = "contact";
		private static AddressbookModel adb = null;

		@Before
		public void initializeTest(
		) {
			initializeTest(API, AddressbooksService.class);
			Response _response = webclient.replacePath("/").post(new AddressbookModel());
			adb = _response.readEntity(AddressbookModel.class);
		}
		
		@After
		public void cleanupTest() {
			webclient.replacePath(adb.getId()).delete();
		}
		
		/********************************** contact tests *********************************/			
		@Test
		public void testContactModelEmptyConstructor() {
			// new() -> _p
			ContactModel _p = new ContactModel();
			assertNull("id should not be set by empty constructor", _p.getId());
			assertNull("birthday should not be set by empty constructor", _p.getBirthday());
			assertNull("company should not be set by empty constructor", _p.getCompany());
			assertNull("department should not be set by empty constructor", _p.getDepartment());
			assertNull("firstname should not be set by empty constructor", _p.getFirstName());
			assertNull("fn should not be set by empty constructor", _p.getFn());
			assertNull("jobtitle should not be set by empty constructor", _p.getJobTitle());
			assertNull("lastname should not be set by empty constructor", _p.getLastName());
			assertNull("maidenname should not be set by empty constructor", _p.getMaidenName());
			assertNull("middlename should not be set by empty constructor", _p.getMiddleName());
			assertNull("nickname should not be set by empty constructor", _p.getNickName());
			assertNull("note should not be set by empty constructor", _p.getNote());
			assertNull("photourl should not be set by empty constructor", _p.getPhotoUrl());
			assertNull("prefix should not be set by empty constructor", _p.getPrefix());
			assertNull("suffix should not be set by empty constructor", _p.getSuffix());
		}
				
		@Test
		public void testContactIdAttributeChange() {
			// new() -> _p -> _p.setId()
			ContactModel _p = new ContactModel();
			assertNull("id should not be set by constructor", _p.getId());
			_p.setId("MY_ID");
			assertEquals("id should have changed:", "MY_ID", _p.getId());
		}

		// Birthday
		@Test
		public void testContactBirthdayAttributeChange() {
			// new() -> _p -> _p.setBirthday()
			ContactModel _p = new ContactModel();
			assertNull("birthday should not be set by empty constructor", _p.getBirthday());
			Date _bdate = new Date();
			_p.setBirthday(_bdate);
			assertEquals("birthday should have changed:", _bdate, _p.getBirthday());
		}
		
		// Company
		@Test
		public void testContactCompanyAttributeChange() {
			// new() -> _p -> _p.setCompany()
			ContactModel _p = new ContactModel();
			assertNull("company should not be set by empty constructor", _p.getCompany());
			_p.setCompany("MY_COMPANY");
			assertEquals("company should have changed:", "MY_COMPANY", _p.getCompany());
		}
		
		// Department
		@Test
		public void testContactDepartmentAttributeChange() {
			// new() -> _p -> _p.setDepartment()
			ContactModel _p = new ContactModel();
			assertNull("department should not be set by empty constructor", _p.getDepartment());
			_p.setDepartment("MY_DEPT");
			assertEquals("department should have changed:", "MY_DEPT", _p.getDepartment());
		}
		
		// FirstName
		@Test
		public void testContactFirstNameAttributeChange() {
			// new() -> _p -> _p.setFirstName()
			ContactModel _p = new ContactModel();
			assertNull("firstName should not be set by empty constructor", _p.getFirstName());
			_p.setFirstName("MY_FNAME");
			assertEquals("firstName should have changed:", "MY_FNAME", _p.getFirstName());
		}
		
		// Fn
		@Test
		public void testContactFnAttributeChange() {
			// new() -> _p -> _p.setFn()
			ContactModel _p = new ContactModel();
			assertNull("fn should not be set by empty constructor", _p.getFn());
			_p.setFn("MY_FN");
			assertEquals("fn should have changed:", "MY_FN", _p.getFn());
		}
		
		// JobTitle
		@Test
		public void testContactJobTitleAttributeChange() {
			// new() -> _p -> _p.setJobTitle()
			ContactModel _p = new ContactModel();
			assertNull("jobTitle should not be set by empty constructor", _p.getJobTitle());
			_p.setJobTitle("MY_JOBTITLE");
			assertEquals("jobTitle should have changed:", "MY_JOBTITLE", _p.getJobTitle());
		}
		
		// LastName
		@Test
		public void testContactLastNameAttributeChange() {
			// new() -> _p -> _p.setLastName()
			ContactModel _p = new ContactModel();
			assertNull("LastName should not be set by empty constructor", _p.getLastName());
			_p.setLastName("MY_LNAME");
			assertEquals("LastName should have changed:", "MY_LNAME", _p.getLastName());
		}

		// MaidenName
		@Test
		public void testContactMaidenNameAttributeChange() {
			// new() -> _p -> _p.setMaidenName()
			ContactModel _p = new ContactModel();
			assertNull("MaidenName should not be set by empty constructor", _p.getMaidenName());
			_p.setMaidenName("MY_MNAME");
			assertEquals("MaidenName should have changed:", "MY_MNAME", _p.getMaidenName());
		}

		// MiddleName
		@Test
		public void testContactMiddleNameAttributeChange() {
			// new() -> _p -> _p.setMiddleName()
			ContactModel _p = new ContactModel();
			assertNull("MiddleName should not be set by empty constructor", _p.getMiddleName());
			_p.setMiddleName("MY_MNAME");
			assertEquals("MiddleName should have changed:", "MY_MNAME", _p.getMiddleName());
		}

		// NickName
		@Test
		public void testContactNickNameAttributeChange() {
			// new() -> _p -> _p.setNickName()
			ContactModel _p = new ContactModel();
			assertNull("NickName should not be set by empty constructor", _p.getNickName());
			_p.setNickName("MY_NNAME");
			assertEquals("NickName should have changed:", "MY_NNAME", _p.getNickName());
		}

		// Note
		@Test
		public void testContactNoteAttributeChange() {
			// new() -> _p -> _p.setNote()
			ContactModel _p = new ContactModel();
			assertNull("Note should not be set by empty constructor", _p.getNote());
			_p.setNote("MY_NOTE");
			assertEquals("Note should have changed:", "MY_NOTE", _p.getNote());
		}

		// PhotoUrl
		@Test
		public void testContactPhotoUrlAttributeChange() {
			// new() -> _p -> _p.setPhotoUrl()
			ContactModel _p = new ContactModel();
			assertNull("PhotoUrl should not be set by empty constructor", _p.getPhotoUrl());
			_p.setPhotoUrl("MY_PHOTO_URL");
			assertEquals("PhotoUrl should have changed:", "MY_PHOTO_URL", _p.getPhotoUrl());
		}

		// Prefix
		@Test
		public void testContactPrefixAttributeChange() {
			// new() -> _p -> _p.setPrefix()
			ContactModel _p = new ContactModel();
			assertNull("Prefix should not be set by empty constructor", _p.getPrefix());
			_p.setPrefix("MY_PREFIX");
			assertEquals("Prefix should have changed:", "MY_PREFIX", _p.getPrefix());
		}

		// Suffix
		@Test
		public void testContactSuffixAttributeChange() {
			// new() -> _p -> _p.setSuffix()
			ContactModel _p = new ContactModel();
			assertNull("Suffix should not be set by empty constructor", _p.getSuffix());
			_p.setSuffix("MY_SUFFIX");
			assertEquals("Suffix should have changed:", "MY_SUFFIX", _p.getSuffix());
		}

				
		@Test
		public void testContactCreateReadDeleteWithEmptyConstructor() {
			// new() -> _p1
			ContactModel _p1 = new ContactModel();
			assertNull("id should not be set by empty constructor", _p1.getId());
			assertNull("birthday should not be set by empty constructor", _p1.getBirthday());
			assertNull("company should not be set by empty constructor", _p1.getCompany());
			assertNull("department should not be set by empty constructor", _p1.getDepartment());
			assertNull("firstname should not be set by empty constructor", _p1.getFirstName());
			assertNull("fn should not be set by empty constructor", _p1.getFn());
			assertNull("jobtitle should not be set by empty constructor", _p1.getJobTitle());
			assertNull("lastname should not be set by empty constructor", _p1.getLastName());
			assertNull("maidenname should not be set by empty constructor", _p1.getMaidenName());
			assertNull("middlename should not be set by empty constructor", _p1.getMiddleName());
			assertNull("nickname should not be set by empty constructor", _p1.getNickName());
			assertNull("note should not be set by empty constructor", _p1.getNote());
			assertNull("photourl should not be set by empty constructor", _p1.getPhotoUrl());
			assertNull("prefix should not be set by empty constructor", _p1.getPrefix());
			assertNull("suffix should not be set by empty constructor", _p1.getSuffix());
			// create(_p1) -> _p2
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_p1);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _p2 = _response.readEntity(ContactModel.class);
			assertNull("create() should not change the id of the local object", _p1.getId());
			assertNull("create() should not change the birthday of the local object", _p1.getBirthday());
			assertNull("create() should not change the company of the local object", _p1.getCompany());
			assertNull("create() should not change the department of the local object", _p1.getDepartment());
			assertNull("create() should not change the firstName of the local object", _p1.getFirstName());
			assertNull("create() should not change the fn of the local object", _p1.getFn());
			assertNull("create() should not change the jobTitle of the local object", _p1.getJobTitle());
			assertNull("create() should not change the lastName of the local object", _p1.getLastName());
			assertNull("create() should not change the maidenName of the local object", _p1.getMaidenName());
			assertNull("create() should not change the middleName of the local object", _p1.getMiddleName());
			assertNull("create() should not change the nickName of the local object", _p1.getNickName());
			assertNull("create() should not change the note of the local object", _p1.getNote());
			assertNull("create() should not change the photoUrl of the local object", _p1.getPhotoUrl());
			assertNull("create() should not change the prefix of the local object", _p1.getPrefix());
			assertNull("create() should not change the suffix of the local object", _p1.getSuffix());
			assertNotNull("create() should set a valid id on the remote object returned", _p2.getId());
			assertNull("birthday of returned object should be null after remote create", _p2.getBirthday());   // what is the correct value of a date?
			assertNull("company of returned object should still be null after remote create", _p2.getCompany());
			assertNull("department of returned object should still be null after remote create", _p2.getDepartment());
			assertNull("firstName of returned object should still be null after remote create", _p2.getFirstName());
			assertNull("fn of returned object should still be null after remote create", _p2.getFn());
			assertNull("jobTitle of returned object should still be null after remote create", _p2.getJobTitle());
			assertNull("lastName of returned object should still be null after remote create", _p2.getLastName());
			assertNull("maidenName of returned object should still be null after remote create", _p2.getMaidenName());
			assertNull("middleName of returned object should still be null after remote create", _p2.getMiddleName());
			assertNull("nickName of returned object should still be null after remote create", _p2.getNickName());
			assertNull("note of returned object should still be null after remote create", _p2.getNote());
			assertNull("photoUrl of returned object should still be null after remote create", _p2.getPhotoUrl());
			assertNull("prefix of returned object should still be null after remote create", _p2.getPrefix());
			assertNull("suffix of returned object should still be null after remote create", _p2.getSuffix());
			// read(_p2) -> _p3
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p2.getId()).get();
			assertEquals("read(" + _p2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _p3 = _response.readEntity(ContactModel.class);
			assertEquals("id of returned object should be the same", _p2.getId(), _p3.getId());
			assertEquals("birthday of returned object should be unchanged after remote create", _p2.getBirthday(), _p3.getBirthday());
			assertEquals("company of returned object should be unchanged after remote create", _p2.getCompany(), _p3.getCompany());
			assertEquals("department of returned object should be unchanged after remote create", _p2.getDepartment(), _p3.getDepartment());
			assertEquals("firstName of returned object should be unchanged after remote create", _p2.getFirstName(), _p3.getFirstName());
			assertEquals("fn of returned object should be unchanged after remote create", _p2.getFn(), _p3.getFn());
			assertEquals("jobTitle of returned object should be unchanged after remote create", _p2.getJobTitle(), _p3.getJobTitle());
			assertEquals("lastName of returned object should be unchanged after remote create", _p2.getLastName(), _p3.getLastName());
			assertEquals("maidenName of returned object should be unchanged after remote create", _p2.getMaidenName(), _p3.getMaidenName());
			assertEquals("middleName of returned object should be unchanged after remote create", _p2.getMiddleName(), _p3.getMiddleName());
			assertEquals("nickName of returned object should be unchanged after remote create", _p2.getNickName(), _p3.getNickName());
			assertEquals("note of returned object should be unchanged after remote create", _p2.getNote(), _p3.getNote());
			assertEquals("photoUrl of returned object should be unchanged after remote create", _p2.getPhotoUrl(), _p3.getPhotoUrl());
			assertEquals("prefix of returned object should be unchanged after remote create", _p2.getPrefix(), _p3.getPrefix());
			assertEquals("suffix of returned object should be unchanged after remote create", _p2.getSuffix(), _p3.getSuffix());
			// delete(_p3)
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p3.getId()).delete();
			assertEquals("delete(" + _p3.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactCreateReadDelete() {
			// new(with custom attributes) -> _p1
			Date _bdate = new Date();
			ContactModel _p1 = setDefaultValues(new ContactModel(), _bdate, "");
			assertNull("id should not be set by constructor", _p1.getId());
			assertEquals("birthday should be set correctly", _bdate, _p1.getBirthday());
			assertEquals("company should be set correctly", "MY_COMPANY", _p1.getCompany());
			assertEquals("department should be set correctly", "MY_DEPT", _p1.getDepartment());
			assertEquals("firstName should be set correctly", "MY_FNAME", _p1.getFirstName());
			assertEquals("fn should be set correctly", "MY_FN", _p1.getFn());
			assertEquals("jobTitle should be set correctly", "MY_JOBTITLE", _p1.getJobTitle());
			assertEquals("lastName should be set correctly", "MY_LNAME", _p1.getLastName());
			assertEquals("maidenName should be set correctly", "MY_MNAME", _p1.getMaidenName());
			assertEquals("middleName should be set correctly", "MY_MNAME", _p1.getMiddleName());
			assertEquals("nickName should be set correctly", "MY_NNAME", _p1.getNickName());
			assertEquals("note should be set correctly", "MY_NOTE", _p1.getNote());
			assertEquals("photoUrl should be set correctly", "MY_PHOTO_URL", _p1.getPhotoUrl());
			assertEquals("prefix should be set correctly", "MY_PREFIX", _p1.getPrefix());
			assertEquals("suffix should be set correctly", "MY_SUFFIX", _p1.getSuffix());
			// create(_p1) -> _p2
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_p1);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _p2 = _response.readEntity(ContactModel.class);
			
			// validate the local object after the remote create()
			assertNull("id should still be null", _p1.getId());
			assertEquals("birthday should be unchanged", _bdate, _p1.getBirthday());
			assertEquals("company should be unchanged", "MY_COMPANY", _p1.getCompany());
			assertEquals("department should be unchanged", "MY_DEPT", _p1.getDepartment());
			assertEquals("firstName should be unchanged", "MY_FNAME", _p1.getFirstName());
			assertEquals("fn should be unchanged", "MY_FN", _p1.getFn());
			assertEquals("jobTitle should be unchanged", "MY_JOBTITLE", _p1.getJobTitle());
			assertEquals("lastName should be unchanged", "MY_LNAME", _p1.getLastName());
			assertEquals("maidenName should be unchanged", "MY_MNAME", _p1.getMaidenName());
			assertEquals("middleName should be unchanged", "MY_MNAME", _p1.getMiddleName());
			assertEquals("nickName should be unchanged", "MY_NNAME", _p1.getNickName());
			assertEquals("note should be unchanged", "MY_NOTE", _p1.getNote());
			assertEquals("photoUrl should be unchanged", "MY_PHOTO_URL", _p1.getPhotoUrl());
			assertEquals("prefix should be unchanged", "MY_PREFIX", _p1.getPrefix());
			assertEquals("suffix should be unchanged", "MY_SUFFIX", _p1.getSuffix());
			
			// validate the returned remote object
			assertNotNull("id of returned object should be set", _p2.getId());
			assertEquals("birthday should be unchanged", _bdate, _p2.getBirthday());
			assertEquals("company should be unchanged", "MY_COMPANY", _p2.getCompany());
			assertEquals("department should be unchanged", "MY_DEPT", _p2.getDepartment());
			assertEquals("firstName should be unchanged", "MY_FNAME", _p2.getFirstName());
			assertEquals("fn should be unchanged", "MY_FN", _p2.getFn());
			assertEquals("jobTitle should be unchanged", "MY_JOBTITLE", _p2.getJobTitle());
			assertEquals("lastName should be unchanged", "MY_LNAME", _p2.getLastName());
			assertEquals("maidenName should be unchanged", "MY_MNAME", _p2.getMaidenName());
			assertEquals("middleName should be unchanged", "MY_MNAME", _p2.getMiddleName());
			assertEquals("nickName should be unchanged", "MY_NNAME", _p2.getNickName());
			assertEquals("note should be unchanged", "MY_NOTE", _p2.getNote());
			assertEquals("photoUrl should be unchanged", "MY_PHOTO_URL", _p2.getPhotoUrl());
			assertEquals("prefix should be unchanged", "MY_PREFIX", _p2.getPrefix());
			assertEquals("suffix should be unchanged", "MY_SUFFIX", _p2.getSuffix());
			
			// read(_p2)  -> _p3
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p2.getId()).get();
			assertEquals("read(" + _p2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _p3 = _response.readEntity(ContactModel.class);
			assertEquals("id of returned object should be the same", _p2.getId(), _p3.getId());
			assertEquals("birthday should be the same", _p2.getBirthday(), _p3.getBirthday());
			assertEquals("company should be the same", _p2.getCompany(), _p3.getCompany());
			assertEquals("department should be the same", _p2.getDepartment(), _p3.getDepartment());
			assertEquals("firstName should be the same", _p2.getFirstName(), _p3.getFirstName());
			assertEquals("fn should be the same", _p2.getFn(), _p3.getFn());
			assertEquals("jobTitle should be the same", _p2.getJobTitle(), _p3.getJobTitle());
			assertEquals("lastName should be the same", _p2.getLastName(), _p3.getLastName());
			assertEquals("maidenName should be the same", _p2.getMaidenName(), _p3.getMaidenName());
			assertEquals("middleName should be the same", _p2.getMiddleName(), _p3.getMiddleName());
			assertEquals("nickName should be the same", _p2.getNickName(), _p3.getNickName());
			assertEquals("note should be the same", _p2.getNote(), _p3.getNote());
			assertEquals("photoUrl should be the same", _p2.getPhotoUrl(), _p3.getPhotoUrl());
			assertEquals("prefix should be the same", _p2.getPrefix(), _p3.getPrefix());
			assertEquals("suffix should be the same", _p2.getSuffix(), _p3.getSuffix());
			// delete(_p3)
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p3.getId()).delete();
			assertEquals("delete(" + _p3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactProjectWithClientSideId() {
			// new() -> _p1 -> _p1.setId()
			ContactModel _p1 = new ContactModel();
			_p1.setId("LOCAL_ID");
			assertEquals("id should have changed", "LOCAL_ID", _p1.getId());
			// create(_p1) -> BAD_REQUEST
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_p1);
			assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactProjectWithDuplicateId() {
			// create(new()) -> _p2
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(new ContactModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _p2 = _response.readEntity(ContactModel.class);

			// new() -> _p3 -> _p3.setId(_p2.getId())
			ContactModel _p3 = new ContactModel();
			_p3.setId(_p2.getId());		// wrongly create a 2nd ContactModel object with the same ID
			
			// create(_p3) -> CONFLICT
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_p3);
			assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactList() {
			ArrayList<ContactModel> _localList = new ArrayList<ContactModel>();		
			Response _response = null;
			webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT);
			for (int i = 0; i < LIMIT; i++) {
				// create(new()) -> _localList
				_response = webclient.post(new ContactModel());
				assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_localList.add(_response.readEntity(ContactModel.class));
			}
			
			// list(/) -> _remoteList
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).get();
			List<ContactModel> _remoteList = new ArrayList<ContactModel>(webclient.getCollection(ContactModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

			ArrayList<String> _remoteListIds = new ArrayList<String>();
			for (ContactModel _p : _remoteList) {
				_remoteListIds.add(_p.getId());
			}
			
			for (ContactModel _p : _localList) {
				assertTrue("project <" + _p.getId() + "> should be listed", _remoteListIds.contains(_p.getId()));
			}
			
			for (ContactModel _p : _localList) {
				_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p.getId()).get();
				assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_response.readEntity(ContactModel.class);
			}
			
			for (ContactModel _p : _localList) {
				_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p.getId()).delete();
				assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			}
		}

		@Test
		public void testContactCreate() {	
			// new(custom attributes1) -> _p1
			Date _bdate1 = new Date(1000);
			ContactModel _p1 = setDefaultValues(new ContactModel(), _bdate1, "1");
			// new(custom attributes2) -> _p2
			Date _bdate2 = new Date(2000);
			ContactModel _p2 = setDefaultValues(new ContactModel(), _bdate2, "2");
			
			// create(_p1)  -> _p3
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_p1);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _p3 = _response.readEntity(ContactModel.class);

			// create(_p2) -> _p4
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_p2);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _p4 = _response.readEntity(ContactModel.class);		
			assertNotNull("ID should be set", _p3.getId());
			assertNotNull("ID should be set", _p4.getId());
			assertThat(_p4.getId(), not(equalTo(_p3.getId())));
			
			// validate attributes of _p3
			assertEquals("birthday should be unchanged", _bdate1, _p3.getBirthday());
			assertEquals("company should be unchanged", "MY_COMPANY1", _p3.getCompany());
			assertEquals("department should be unchanged", "MY_DEPT1", _p3.getDepartment());
			assertEquals("firstName should be unchanged", "MY_FNAME1", _p3.getFirstName());
			assertEquals("fn should be unchanged", "MY_FN1", _p3.getFn());
			assertEquals("jobTitle should be unchanged", "MY_JOBTITLE1", _p3.getJobTitle());
			assertEquals("lastName should be unchanged", "MY_LNAME1", _p3.getLastName());
			assertEquals("maidenName should be unchanged", "MY_MNAME1", _p3.getMaidenName());
			assertEquals("middleName should be unchanged", "MY_MNAME1", _p3.getMiddleName());
			assertEquals("nickName should be unchanged", "MY_NNAME1", _p3.getNickName());
			assertEquals("note should be unchanged", "MY_NOTE1", _p3.getNote());
			assertEquals("photoUrl should be unchanged", "MY_PHOTO_URL1", _p3.getPhotoUrl());
			assertEquals("prefix should be unchanged", "MY_PREFIX1", _p3.getPrefix());
			assertEquals("suffix should be unchanged", "MY_SUFFIX1", _p3.getSuffix());
			
			// validate attributes of _p4
			assertEquals("birthday should be unchanged", _bdate2, _p4.getBirthday());
			assertEquals("company should be unchanged", "MY_COMPANY2", _p4.getCompany());
			assertEquals("department should be unchanged", "MY_DEPT2", _p4.getDepartment());
			assertEquals("firstName should be unchanged", "MY_FNAME2", _p4.getFirstName());
			assertEquals("fn should be unchanged", "MY_FN2", _p4.getFn());
			assertEquals("jobTitle should be unchanged", "MY_JOBTITLE2", _p4.getJobTitle());
			assertEquals("lastName should be unchanged", "MY_LNAME2", _p4.getLastName());
			assertEquals("maidenName should be unchanged", "MY_MNAME2", _p4.getMaidenName());
			assertEquals("middleName should be unchanged", "MY_MNAME2", _p4.getMiddleName());
			assertEquals("nickName should be unchanged", "MY_NNAME2", _p4.getNickName());
			assertEquals("note should be unchanged", "MY_NOTE2", _p4.getNote());
			assertEquals("photoUrl should be unchanged", "MY_PHOTO_URL2", _p4.getPhotoUrl());
			assertEquals("prefix should be unchanged", "MY_PREFIX2", _p4.getPrefix());
			assertEquals("suffix should be unchanged", "MY_SUFFIX2", _p4.getSuffix());

			// delete(_p3) -> NO_CONTENT
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p3.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

			// delete(_p4) -> NO_CONTENT
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p4.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactCreateDouble() {		
			// create(new()) -> _p
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(new ContactModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _p = _response.readEntity(ContactModel.class);
			assertNotNull("ID should be set:", _p.getId());		
			
			// create(_p) -> CONFLICT
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_p);
			assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

			// delete(_p) -> NO_CONTENT
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactRead() {
			ArrayList<ContactModel> _localList = new ArrayList<ContactModel>();
			Response _response = null;
			webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT);
			for (int i = 0; i < LIMIT; i++) {
				_response = webclient.post(new ContactModel());
				assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_localList.add(_response.readEntity(ContactModel.class));
			}
		
			// test read on each local element
			for (ContactModel _p : _localList) {
				_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p.getId()).get();
				assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_response.readEntity(ContactModel.class);
			}

			// test read on each listed element
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).get();
			List<ContactModel> _remoteList = new ArrayList<ContactModel>(webclient.getCollection(ContactModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

			ContactModel _tmpObj = null;
			for (ContactModel _p : _remoteList) {
				_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p.getId()).get();
				assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
				_tmpObj = _response.readEntity(ContactModel.class);
				assertEquals("ID should be unchanged when reading a project", _p.getId(), _tmpObj.getId());						
			}

			// TODO: "reading a project with ID = null should fail" -> ValidationException
			// TODO: "reading a non-existing project should fail"

			for (ContactModel _p : _localList) {
				_response = webclient.replacePath(_p.getId()).delete();
				assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			}
		}
			
		@Test
		public void testContactMultiRead() {
			// new() -> _p1
			Date _bdate1 = new Date(1000);
			ContactModel _p1 = setDefaultValues(new ContactModel(), _bdate1, "1");
			
			// create(_p1) -> _p2
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_p1);
			ContactModel _p2 = _response.readEntity(ContactModel.class);

			// read(_p2) -> _p3
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p2.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _p3 = _response.readEntity(ContactModel.class);
			assertEquals("ID should be unchanged after read:", _p2.getId(), _p3.getId());		

			// read(_p2) -> _p4
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p2.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _p4 = _response.readEntity(ContactModel.class);
			
			// but: the two objects are not equal !
			assertEquals("ID should be the same:", _p3.getId(), _p4.getId());
			assertEquals("birthday should be the same", _p3.getBirthday(), _p4.getBirthday());
			assertEquals("company should be the same", _p3.getCompany(), _p4.getCompany());
			assertEquals("department should be the same", _p3.getDepartment(), _p4.getDepartment());
			assertEquals("firstName should be the same", _p3.getFirstName(), _p4.getFirstName());
			assertEquals("fn should be the same", _p3.getFn(), _p4.getFn());
			assertEquals("jobTitle should be the same", _p3.getJobTitle(), _p4.getJobTitle());
			assertEquals("lastName should be the same", _p3.getLastName(), _p4.getLastName());
			assertEquals("maidenName should be the same", _p3.getMaidenName(), _p4.getMaidenName());
			assertEquals("middleName should be the same", _p3.getMiddleName(), _p4.getMiddleName());
			assertEquals("nickName should be the same", _p3.getNickName(), _p4.getNickName());
			assertEquals("note should be the same", _p3.getNote(), _p4.getNote());
			assertEquals("photoUrl should be the same", _p3.getPhotoUrl(), _p4.getPhotoUrl());
			assertEquals("prefix should be the same", _p3.getPrefix(), _p4.getPrefix());
			assertEquals("suffix should be the same", _p3.getSuffix(), _p4.getSuffix());
			
			
			assertEquals("ID should be the same:", _p3.getId(), _p2.getId());
			assertEquals("birthday should be the same", _p3.getBirthday(), _p2.getBirthday());
			assertEquals("company should be the same", _p3.getCompany(), _p2.getCompany());
			assertEquals("department should be the same", _p3.getDepartment(), _p2.getDepartment());
			assertEquals("firstName should be the same", _p3.getFirstName(), _p2.getFirstName());
			assertEquals("fn should be the same", _p3.getFn(), _p2.getFn());
			assertEquals("jobTitle should be the same", _p3.getJobTitle(), _p2.getJobTitle());
			assertEquals("lastName should be the same", _p3.getLastName(), _p2.getLastName());
			assertEquals("maidenName should be the same", _p3.getMaidenName(), _p2.getMaidenName());
			assertEquals("middleName should be the same", _p3.getMiddleName(), _p2.getMiddleName());
			assertEquals("nickName should be the same", _p3.getNickName(), _p2.getNickName());
			assertEquals("note should be the same", _p3.getNote(), _p2.getNote());
			assertEquals("photoUrl should be the same", _p3.getPhotoUrl(), _p2.getPhotoUrl());
			assertEquals("prefix should be the same", _p3.getPrefix(), _p2.getPrefix());
			assertEquals("suffix should be the same", _p3.getSuffix(), _p2.getSuffix());
			
			// delete(_p2)
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p2.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactUpdate() {
			// new() -> _p1
			ContactModel _p1 = new ContactModel();
			
			// create(_p1) -> _p2
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_p1);
			ContactModel _p2 = _response.readEntity(ContactModel.class);
			
			// change the attributes
			// update(_p2) -> _p3
			Date _bdate3 = new Date(3000);
			webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p2.getId()).put(setDefaultValues(_p2, _bdate3, "3"));
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _p3 = _response.readEntity(ContactModel.class);

			assertNotNull("ID should be set", _p3.getId());
			assertEquals("ID should be unchanged", _p2.getId(), _p3.getId());	
			assertEquals("birthday should be set correctly", _bdate3, _p3.getBirthday());
			assertEquals("company should be set correctly", "MY_COMPANY3", _p3.getCompany());
			assertEquals("department should be set correctly", "MY_DEPT3", _p3.getDepartment());
			assertEquals("firstName should be set correctly", "MY_FNAME3", _p3.getFirstName());
			assertEquals("fn should be set correctly", "MY_FN3", _p3.getFn());
			assertEquals("jobTitle should be set correctly", "MY_JOBTITLE3", _p3.getJobTitle());
			assertEquals("lastName should be set correctly", "MY_LNAME3", _p3.getLastName());
			assertEquals("maidenName should be set correctly", "MY_MNAME3", _p3.getMaidenName());
			assertEquals("middleName should be set correctly", "MY_MNAME3", _p3.getMiddleName());
			assertEquals("nickName should be set correctly", "MY_NNAME3", _p3.getNickName());
			assertEquals("note should be set correctly", "MY_NOTE3", _p3.getNote());
			assertEquals("photoUrl should be set correctly", "MY_PHOTO_URL3", _p3.getPhotoUrl());
			assertEquals("prefix should be set correctly", "MY_PREFIX3", _p3.getPrefix());
			assertEquals("suffix should be set correctly", "MY_SUFFIX3", _p3.getSuffix());

			// reset the attributes
			// update(_c2) -> _p4
			Date _bdate4 = new Date(4000);
			webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p2.getId()).put(setDefaultValues(_p2, _bdate4, "4"));
			assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _p4 = _response.readEntity(ContactModel.class);

			assertNotNull("ID should be set", _p4.getId());
			assertEquals("ID should be unchanged", _p2.getId(), _p4.getId());
			assertEquals("birthday should be set correctly", _bdate4, _p4.getBirthday());
			assertEquals("company should be set correctly", "MY_COMPANY4", _p4.getCompany());
			assertEquals("department should be set correctly", "MY_DEPT4", _p4.getDepartment());
			assertEquals("firstName should be set correctly", "MY_FNAME4", _p4.getFirstName());
			assertEquals("fn should be set correctly", "MY_FN4", _p4.getFn());
			assertEquals("jobTitle should be set correctly", "MY_JOBTITLE4", _p4.getJobTitle());
			assertEquals("lastName should be set correctly", "MY_LNAME4", _p4.getLastName());
			assertEquals("maidenName should be set correctly", "MY_MNAME4", _p4.getMaidenName());
			assertEquals("middleName should be set correctly", "MY_MNAME4", _p4.getMiddleName());
			assertEquals("nickName should be set correctly", "MY_NNAME4", _p4.getNickName());
			assertEquals("note should be set correctly", "MY_NOTE4", _p4.getNote());
			assertEquals("photoUrl should be set correctly", "MY_PHOTO_URL4", _p4.getPhotoUrl());
			assertEquals("prefix should be set correctly", "MY_PREFIX4", _p4.getPrefix());
			assertEquals("suffix should be set correctly", "MY_SUFFIX4", _p4.getSuffix());
			
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_p2.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}

		@Test
		public void testContactDelete(
		) {
			// new() -> _c0
			ContactModel _c0 = new ContactModel();
			// create(_c0) -> _c1
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_c0);
			ContactModel _c1 = _response.readEntity(ContactModel.class);
			
			// read(_c0) -> _tmpObj
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_c1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			ContactModel _tmpObj = _response.readEntity(ContactModel.class);
			assertEquals("ID should be unchanged when reading a project (remote):", _c1.getId(), _tmpObj.getId());						

			// read(_c1) -> _tmpObj
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_c1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(ContactModel.class);
			assertEquals("ID should be unchanged when reading a project (remote):", _c1.getId(), _tmpObj.getId());						
			
			// delete(_c1) -> OK
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_c1.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
			// read the deleted object twice
			// read(_c1) -> NOT_FOUND
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_c1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
			
			// read(_c1) -> NOT_FOUND
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_c1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		}
		
		@Test
		public void testContactDoubleDelete() {
			// new() -> _c0
			ContactModel _c0 = new ContactModel();
			
			// create(_c0) -> _c1
			Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_c0);
			ContactModel _c1 = _response.readEntity(ContactModel.class);

			// read(_c1) -> OK
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_c1.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			
			// delete(_c1) -> OK
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_c1.getId()).delete();		
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
			
			// read(_c1) -> NOT_FOUND
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_c1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
			
			// delete _c1 -> NOT_FOUND
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_c1.getId()).delete();		
			assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
			
			// read _c1 -> NOT_FOUND
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(_c1.getId()).get();
			assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		}
		
		/********************************** helper methods *********************************/			
		private ContactModel setDefaultValues(ContactModel cm, Date bdate, String suffix) {
			cm.setBirthday(bdate);
			cm.setCompany("MY_COMPANY" + suffix);
			cm.setDepartment("MY_DEPT" + suffix);
			cm.setFirstName("MY_FNAME" + suffix);
			cm.setFn("MY_FN" + suffix);
			cm.setJobTitle("MY_JOBTITLE" + suffix);
			cm.setLastName("MY_LNAME" + suffix);
			cm.setMaidenName("MY_MNAME" + suffix);
			cm.setMiddleName("MY_MNAME" + suffix);
			cm.setNickName("MY_NNAME" + suffix);
			cm.setNote("MY_NOTE" + suffix);
			cm.setPhotoUrl("MY_PHOTO_URL" + suffix);
			cm.setPrefix("MY_PREFIX" + suffix);
			cm.setSuffix("MY_SUFFIX" + suffix);
			return cm;
		}
}
