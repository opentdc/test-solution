package test.org.opentdc.addressbooks;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.addressbooks.AddressModel;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.ContactModel;

import test.org.opentdc.AbstractTestClient;

public class AddressTest extends AbstractTestClient<AddressbooksService> {
	
	private static final String API = "api/addressbooks/";
	public static final String PATH_EL_CONTACT = "contact";
	public static final String PATH_EL_ADDRESS = "address";
	private static AddressbookModel adb = null;
	private static ContactModel contact = null;

	@Before
	public void initializeTest(
	) {
		initializeTest(API, AddressbooksService.class);
		Response _response = webclient.replacePath("/").post(new AddressbookModel());
		adb = _response.readEntity(AddressbookModel.class);
		System.out.println("AddressTest posted AddressbookModel " + adb.getId());
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(new ContactModel());
		contact = _response.readEntity(ContactModel.class);
		System.out.println("AddressTest posted ContactModel " + contact.getId());
	}
	
	@After
	public void cleanupTest() {
		webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).delete();
		System.out.println("AddressTest deleted ContactModel " + contact.getId());
		webclient.replacePath("/").path(adb.getId()).delete();
		System.out.println("AddressTest deleted AddressbookModel " + adb.getId());
	}

	/********************************** address attributes tests *********************************/			
	@Test
	public void testAddressModelEmptyConstructor() {
		// new() -> _p
		AddressModel _p = new AddressModel();
		assertNull("id should not be set by empty constructor", _p.getId());
		assertNull("attributeType should not be set by empty constructor", _p.getAttributeType());
		assertNull("type should not be set by empty constructor", _p.getType());
		assertNull("msgType should not be set by empty constructor", _p.getMsgType());
		assertNull("value should not be set by empty constructor", _p.getValue());
		assertNull("street should not be set by empty constructor", _p.getStreet());
		assertNull("postalCode should not be set by empty constructor", _p.getPostalCode());
		assertNull("city should not be set by empty constructor", _p.getCity());
		assertNull("country should not be set by empty constructor", _p.getCountry());
	}
			
	@Test
	public void testAddressIdAttributeChange() {
		// new() -> _p -> _p.setId()
		AddressModel _p = new AddressModel();
		assertNull("id should not be set by constructor", _p.getId());
		_p.setId("MY_ID");
		assertEquals("id should have changed:", "MY_ID", _p.getId());
	}

	// AttributeType
	@Test
	public void testAddressAttributeTypeChange() {
		// new() -> _p -> _p.setAttributeType()
		AddressModel _p = new AddressModel();
		assertNull("attributeType should not be set by empty constructor", _p.getAttributeType());
		_p.setAttributeType("MY_ATTR_TYPE");
		assertEquals("attributeType should have changed:", "MY_ATTR_TYPE", _p.getAttributeType());
	}
	
	// Type
	@Test
	public void testAddressTypeAttributeChange() {
		// new() -> _p -> _p.setType()
		AddressModel _p = new AddressModel();
		assertNull("type should not be set by empty constructor", _p.getType());
		_p.setType("MY_TYPE");
		assertEquals("type should have changed:", "MY_TYPE", _p.getType());
	}
	
	// MsgType
	@Test
	public void testAddressMsgTypeAttributeChange() {
		// new() -> _p -> _p.setMsgType()
		AddressModel _p = new AddressModel();
		assertNull("msgType should not be set by empty constructor", _p.getMsgType());
		_p.setMsgType("MY_MSG_TYPE");
		assertEquals("msgType should have changed:", "MY_MSG_TYPE", _p.getMsgType());
	}
	
	// Value
	@Test
	public void testAddressValueAttributeChange() {
		// new() -> _p -> _p.setValue()
		AddressModel _p = new AddressModel();
		assertNull("value should not be set by empty constructor", _p.getValue());
		_p.setValue("MY_VALUE");
		assertEquals("value should have changed:", "MY_VALUE", _p.getValue());
	}
	
	// Street
	@Test
	public void testAddressStreetAttributeChange() {
		// new() -> _p -> _p.setStreet()
		AddressModel _p = new AddressModel();
		assertNull("street should not be set by empty constructor", _p.getStreet());
		_p.setStreet("MY_STREET");
		assertEquals("street should have changed:", "MY_STREET", _p.getStreet());
	}
	
	// PostalCode
	@Test
	public void testAddressPostalCodeAttributeChange() {
		// new() -> _p -> _p.setPostalCode()
		AddressModel _p = new AddressModel();
		assertNull("postalCode should not be set by empty constructor", _p.getPostalCode());
		_p.setPostalCode("MY_POSTAL_CODE");
		assertEquals("postalCode should have changed:", "MY_POSTAL_CODE", _p.getPostalCode());
	}
	
	// City
	@Test
	public void testAddressCityAttributeChange() {
		// new() -> _p -> _p.setCity()
		AddressModel _p = new AddressModel();
		assertNull("city should not be set by empty constructor", _p.getCity());
		_p.setCity("MY_CITY");
		assertEquals("city should have changed:", "MY_CITY", _p.getCity());
	}

	// Country
	@Test
	public void testAddressCountryAttributeChange() {
		// new() -> _p -> _p.setCountry()
		AddressModel _p = new AddressModel();
		assertNull("country should not be set by empty constructor", _p.getCountry());
		_p.setCountry("MY_COUNTRY");
		assertEquals("country should have changed:", "MY_COUNTRY", _p.getCountry());
	}
	
	@Test
	public void testAddressCreatedBy() {
		// new() -> _o -> _o.setCreatedBy()
		AddressModel _o = new AddressModel();
		assertNull("createdBy should not be set by empty constructor", _o.getCreatedBy());
		_o.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _o.getCreatedBy());	
	}
	
	@Test
	public void testAddressCreatedAt() {
		// new() -> _o -> _o.setCreatedAt()
		AddressModel _o = new AddressModel();
		assertNull("createdAt should not be set by empty constructor", _o.getCreatedAt());
		_o.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _o.getCreatedAt());
	}
		
	@Test
	public void testAddressModifiedBy() {
		// new() -> _o -> _o.setModifiedBy()
		AddressModel _o = new AddressModel();
		assertNull("modifiedBy should not be set by empty constructor", _o.getModifiedBy());
		_o.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _o.getModifiedBy());	
	}
	
	@Test
	public void testAddressModifiedAt() {
		// new() -> _o -> _o.setModifiedAt()
		AddressModel _o = new AddressModel();
		assertNull("modifiedAt should not be set by empty constructor", _o.getModifiedAt());
		_o.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _o.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	
	// create:  POST p "api/addressbooks/{abid}/contact/{cid}/address"
	// read:    GET "api/addressbooks/{abid}/contact/{cid}/address/{adrid}"
	// update:  PUT p "api/addressbooks/{abid}/contact/{cid}/address/{adrid}"
	// delete:  DELETE "api/addressbooks/{abid}/contact/{cid}/address/{ardid}"


	@Test
	public void testAddressCreateReadDeleteWithEmptyConstructor() {
		// new() -> _p1
		AddressModel _p1 = new AddressModel();
		assertNull("id should not be set by empty constructor", _p1.getId());
		assertNull("attributeType should not be set by empty constructor", _p1.getAttributeType());
		assertNull("type should not be set by empty constructor", _p1.getType());
		assertNull("msgType should not be set by empty constructor", _p1.getMsgType());
		assertNull("value should not be set by empty constructor", _p1.getValue());
		assertNull("street should not be set by empty constructor", _p1.getStreet());
		assertNull("postalCode should not be set by empty constructor", _p1.getPostalCode());
		assertNull("city should not be set by empty constructor", _p1.getCity());
		assertNull("country should not be set by empty constructor", _p1.getCountry());
		
		// create(_p1) -> _p2
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_p1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _p2 = _response.readEntity(AddressModel.class);
		assertNull("create() should not change the id of the local object", _p1.getId());
		assertNull("create() should not change the attributeType of the local object", _p1.getAttributeType());
		assertNull("create() should not change the type of the local object", _p1.getType());
		assertNull("create() should not change the msgType of the local object", _p1.getMsgType());
		assertNull("create() should not change the value of the local object", _p1.getValue());
		assertNull("create() should not change the street of the local object", _p1.getStreet());
		assertNull("create() should not change the postalCode of the local object", _p1.getPostalCode());
		assertNull("create() should not change the city of the local object", _p1.getCity());
		assertNull("create() should not change the country of the local object", _p1.getCountry());

		assertNotNull("create() should set a valid id on the remote object returned", _p2.getId());
		assertNull("attributeType of returned object should be null after remote create", _p2.getAttributeType());   // what is the correct value of a date?
		assertNull("type of returned object should still be null after remote create", _p2.getType());
		assertNull("msgType of returned object should still be null after remote create", _p2.getMsgType());
		assertNull("value of returned object should still be null after remote create", _p2.getValue());
		assertNull("street of returned object should still be null after remote create", _p2.getStreet());
		assertNull("postalCode of returned object should still be null after remote create", _p2.getPostalCode());
		assertNull("city of returned object should still be null after remote create", _p2.getCity());
		assertNull("country of returned object should still be null after remote create", _p2.getCountry());

		// read(_p2) -> _p3
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p2.getId()).get();
		assertEquals("read(" + _p2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _p3 = _response.readEntity(AddressModel.class);
		assertEquals("id of returned object should be the same", _p2.getId(), _p3.getId());
		assertEquals("attributeType of returned object should be unchanged after remote create", _p2.getAttributeType(), _p3.getAttributeType());
		assertEquals("type of returned object should be unchanged after remote create", _p2.getType(), _p3.getType());
		assertEquals("msgType of returned object should be unchanged after remote create", _p2.getMsgType(), _p3.getMsgType());
		assertEquals("value of returned object should be unchanged after remote create", _p2.getValue(), _p3.getValue());
		assertEquals("street of returned object should be unchanged after remote create", _p2.getStreet(), _p3.getStreet());
		assertEquals("postalCode of returned object should be unchanged after remote create", _p2.getPostalCode(), _p3.getPostalCode());
		assertEquals("city of returned object should be unchanged after remote create", _p2.getCity(), _p3.getCity());
		assertEquals("country of returned object should be unchanged after remote create", _p2.getCountry(), _p3.getCountry());
		// delete(_p3)
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p3.getId()).delete();
		assertEquals("delete(" + _p3.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
//	"MY_ATTR_TYPE"		setAttributeType		getAttributeType	attributeType
//	"MY_TYPE"			setType					getType				type
//	"MY_MSG_TYPE"		setMsgType				getMsgType			msgType
//	"MY_VALUE"			setValue				getValue			value
//	"MY_STREET"			setStreet				getStreet			street
//	"MY_POSTAL_CODE"	setPostalCode			getPostalCode		postalCode
//	"MY_CITY"			setCity					getCity				city
//	"MY_COUNTRY"		setCountry				getCountry			country
	
	@Test
	public void testAddressCreateReadDelete() {
		// new(with custom attributes) -> _p1
		AddressModel _p1 = setDefaultValues(new AddressModel(), "");
		assertNull("id should not be set by constructor", _p1.getId());
		assertEquals("attributeType should be set correctly", "MY_ATTR_TYPE", _p1.getAttributeType());
		assertEquals("type should be set correctly", "MY_TYPE", _p1.getType());
		assertEquals("msgType should be set correctly", "MY_MSG_TYPE", _p1.getMsgType());
		assertEquals("value should be set correctly", "MY_VALUE", _p1.getValue());
		assertEquals("street should be set correctly", "MY_STREET", _p1.getStreet());
		assertEquals("postalCode should be set correctly", "MY_POSTAL_CODE", _p1.getPostalCode());
		assertEquals("city should be set correctly", "MY_CITY", _p1.getCity());
		assertEquals("country should be set correctly", "MY_COUNTRY", _p1.getCountry());

		// create(_p1) -> _p2
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_p1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _p2 = _response.readEntity(AddressModel.class);
		
		// validate the local object after the remote create()
		assertNull("id should not be set by constructor", _p1.getId());
		assertEquals("attributeType should be unchanged", "MY_ATTR_TYPE", _p1.getAttributeType());
		assertEquals("type should be unchanged", "MY_TYPE", _p1.getType());
		assertEquals("msgType should be unchanged", "MY_MSG_TYPE", _p1.getMsgType());
		assertEquals("value should be unchanged", "MY_VALUE", _p1.getValue());
		assertEquals("street should be unchanged", "MY_STREET", _p1.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE", _p1.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY", _p1.getCity());
		assertEquals("country should be unchanged", "MY_COUNTRY", _p1.getCountry());
		
		// validate the returned remote object
		assertNotNull("id of returned object should be set", _p2.getId());
		assertEquals("attributeType should be unchanged", "MY_ATTR_TYPE", _p2.getAttributeType());
		assertEquals("type should be unchanged", "MY_TYPE", _p2.getType());
		assertEquals("msgType should be unchanged", "MY_MSG_TYPE", _p2.getMsgType());
		assertEquals("value should be unchanged", "MY_VALUE", _p2.getValue());
		assertEquals("street should be unchanged", "MY_STREET", _p2.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE", _p2.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY", _p2.getCity());
		assertEquals("country should be unchanged", "MY_COUNTRY", _p2.getCountry());
		
		// read(_p2)  -> _p3
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p2.getId()).get();
		assertEquals("read(" + _p2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _p3 = _response.readEntity(AddressModel.class);
		assertEquals("id of returned object should be the same", _p2.getId(), _p3.getId());
		assertEquals("attributeType should be the same", _p2.getAttributeType(), _p3.getAttributeType());
		assertEquals("type should be the same", _p2.getType(), _p3.getType());
		assertEquals("msgType should be the same", _p2.getMsgType(), _p3.getMsgType());
		assertEquals("value should be the same", _p2.getValue(), _p3.getValue());
		assertEquals("street should be the same", _p2.getStreet(), _p3.getStreet());
		assertEquals("postalCode should be the same", _p2.getPostalCode(), _p3.getPostalCode());
		assertEquals("city should be the same", _p2.getCity(), _p3.getCity());
		assertEquals("country should be the same", _p2.getCountry(), _p3.getCountry());
		
		// delete(_p3)
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p3.getId()).delete();
		assertEquals("delete(" + _p3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressProjectWithClientSideId() {
		// new() -> _p1 -> _p1.setId()
		AddressModel _p1 = new AddressModel();
		_p1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _p1.getId());
		// create(_p1) -> BAD_REQUEST
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_p1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressProjectWithDuplicateId() {
		// create(new()) -> _p2
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(new AddressModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _p2 = _response.readEntity(AddressModel.class);

		// new() -> _p3 -> _p3.setId(_p2.getId())
		AddressModel _p3 = new AddressModel();
		_p3.setId(_p2.getId());		// wrongly create a 2nd AddressModel object with the same ID
		
		// create(_p3) -> CONFLICT
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_p3);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressList() {
		ArrayList<AddressModel> _localList = new ArrayList<AddressModel>();		
		Response _response = null;
		webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS);
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = webclient.post(new AddressModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(AddressModel.class));
		}
		
		// list(/) -> _remoteList
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).get();
		List<AddressModel> _remoteList = new ArrayList<AddressModel>(webclient.getCollection(AddressModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (AddressModel _p : _remoteList) {
			_remoteListIds.add(_p.getId());
		}
		
		for (AddressModel _p : _localList) {
			assertTrue("project <" + _p.getId() + "> should be listed", _remoteListIds.contains(_p.getId()));
		}
		
		for (AddressModel _p : _localList) {
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(AddressModel.class);
		}
		
		for (AddressModel _p : _localList) {
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}

	@Test
	public void testAddressCreate() {	
		// new(custom attributes1) -> _p1
		AddressModel _p1 = setDefaultValues(new AddressModel(), "1");
		// new(custom attributes2) -> _p2
		AddressModel _p2 = setDefaultValues(new AddressModel(), "2");
		
		// create(_p1)  -> _p3
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_p1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _p3 = _response.readEntity(AddressModel.class);

		// create(_p2) -> _p4
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_p2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _p4 = _response.readEntity(AddressModel.class);		
		assertNotNull("ID should be set", _p3.getId());
		assertNotNull("ID should be set", _p4.getId());
		assertThat(_p4.getId(), not(equalTo(_p3.getId())));
		
		// validate attributes of _p3
		assertEquals("attributeType should be unchanged", "MY_ATTR_TYPE1", _p3.getAttributeType());
		assertEquals("type should be unchanged", "MY_TYPE1", _p3.getType());
		assertEquals("msgType should be unchanged", "MY_MSG_TYPE1", _p3.getMsgType());
		assertEquals("value should be unchanged", "MY_VALUE1", _p3.getValue());
		assertEquals("street should be unchanged", "MY_STREET1", _p3.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE1", _p3.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY1", _p3.getCity());
		assertEquals("country should be unchanged", "MY_COUNTRY1", _p3.getCountry());
		
		// validate attributes of _p4
		assertEquals("attributeType should be unchanged", "MY_ATTR_TYPE2", _p4.getAttributeType());
		assertEquals("type should be unchanged", "MY_TYPE2", _p4.getType());
		assertEquals("msgType should be unchanged", "MY_MSG_TYPE2", _p4.getMsgType());
		assertEquals("value should be unchanged", "MY_VALUE2", _p4.getValue());
		assertEquals("street should be unchanged", "MY_STREET2", _p4.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE2", _p4.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY2", _p4.getCity());
		assertEquals("country should be unchanged", "MY_COUNTRY2", _p4.getCountry());
		
		// delete(_p3) -> NO_CONTENT
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_p4) -> NO_CONTENT
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testAddressCreateDouble() {		
		// create(new()) -> _p
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(new AddressModel());
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _p = _response.readEntity(AddressModel.class);
		assertNotNull("ID should be set:", _p.getId());		
		
		// create(_p) -> CONFLICT
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_p);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_p) -> NO_CONTENT
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressRead() {
		ArrayList<AddressModel> _localList = new ArrayList<AddressModel>();
		Response _response = null;
		webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS);
		for (int i = 0; i < LIMIT; i++) {
			_response = webclient.post(new AddressModel());
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(AddressModel.class));
		}
	
		// test read on each local element
		for (AddressModel _p : _localList) {
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(AddressModel.class);
		}

		// test read on each listed element
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).get();
		List<AddressModel> _remoteList = new ArrayList<AddressModel>(webclient.getCollection(AddressModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		AddressModel _tmpObj = null;
		for (AddressModel _p : _remoteList) {
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(AddressModel.class);
			assertEquals("ID should be unchanged when reading a project", _p.getId(), _tmpObj.getId());						
		}

		// TODO: "reading a project with ID = null should fail" -> ValidationException
		// TODO: "reading a non-existing project should fail"

		for (AddressModel _p : _localList) {
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}
		
	@Test
	public void testAddressMultiRead() {
		// new() -> _p1
		AddressModel _p1 = setDefaultValues(new AddressModel(), "1");
		
		// create(_p1) -> _p2
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_p1);
		AddressModel _p2 = _response.readEntity(AddressModel.class);

		// read(_p2) -> _p3
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _p3 = _response.readEntity(AddressModel.class);
		assertEquals("ID should be unchanged after read:", _p2.getId(), _p3.getId());		

		// read(_p2) -> _p4
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _p4 = _response.readEntity(AddressModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same:", _p3.getId(), _p4.getId());
		assertEquals("attributeType should be the same", _p3.getAttributeType(), _p4.getAttributeType());
		assertEquals("type should be the same", _p3.getType(), _p4.getType());
		assertEquals("msgType should be the same", _p3.getMsgType(), _p4.getMsgType());
		assertEquals("value should be the same", _p3.getValue(), _p4.getValue());
		assertEquals("street should be the same", _p3.getStreet(), _p4.getStreet());
		assertEquals("postalCode should be the same", _p3.getPostalCode(), _p4.getPostalCode());
		assertEquals("city should be the same", _p3.getCity(), _p4.getCity());
		assertEquals("country should be the same", _p3.getCountry(), _p4.getCountry());
		
		assertEquals("ID should be the same:", _p3.getId(), _p2.getId());
		assertEquals("attributeType should be the same", _p3.getAttributeType(), _p2.getAttributeType());
		assertEquals("type should be the same", _p3.getType(), _p2.getType());
		assertEquals("msgType should be the same", _p3.getMsgType(), _p2.getMsgType());
		assertEquals("value should be the same", _p3.getValue(), _p2.getValue());
		assertEquals("street should be the same", _p3.getStreet(), _p2.getStreet());
		assertEquals("postalCode should be the same", _p3.getPostalCode(), _p2.getPostalCode());
		assertEquals("city should be the same", _p3.getCity(), _p2.getCity());
		assertEquals("country should be the same", _p3.getCountry(), _p2.getCountry());
		
		// delete(_p2)
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressUpdate() {
		// new() -> _p1
		AddressModel _p1 = new AddressModel();
		
		// create(_p1) -> _p2
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_p1);
		AddressModel _p2 = _response.readEntity(AddressModel.class);
		
		// change the attributes
		// update(_p2) -> _p3
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p2.getId()).put(setDefaultValues(_p2, "3"));
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _p3 = _response.readEntity(AddressModel.class);

		assertNotNull("ID should be set", _p3.getId());
		assertEquals("ID should be unchanged", _p2.getId(), _p3.getId());
		assertEquals("attributeType should be set correctly", "MY_ATTR_TYPE3", _p3.getAttributeType());
		assertEquals("type should be set correctly", "MY_TYPE3", _p3.getType());
		assertEquals("msgType should be set correctly", "MY_MSG_TYPE3", _p3.getMsgType());
		assertEquals("value should be set correctly", "MY_VALUE3", _p3.getValue());
		assertEquals("street should be set correctly", "MY_STREET3", _p3.getStreet());
		assertEquals("postalCode should be set correctly", "MY_POSTAL_CODE3", _p3.getPostalCode());
		assertEquals("city should be set correctly", "MY_CITY3", _p3.getCity());
		assertEquals("country should be set correctly", "MY_COUNTRY3", _p3.getCountry());
		
		// reset the attributes
		// update(_c2) -> _p4
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p2.getId()).put(setDefaultValues(_p2, "4"));
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _p4 = _response.readEntity(AddressModel.class);

		assertNotNull("ID should be set", _p4.getId());
		assertEquals("ID should be unchanged", _p2.getId(), _p4.getId());
		assertEquals("attributeType should be set correctly", "MY_ATTR_TYPE4", _p4.getAttributeType());
		assertEquals("type should be set correctly", "MY_TYPE4", _p4.getType());
		assertEquals("msgType should be set correctly", "MY_MSG_TYPE4", _p4.getMsgType());
		assertEquals("value should be set correctly", "MY_VALUE4", _p4.getValue());
		assertEquals("street should be set correctly", "MY_STREET4", _p4.getStreet());
		assertEquals("postalCode should be set correctly", "MY_POSTAL_CODE4", _p4.getPostalCode());
		assertEquals("city should be set correctly", "MY_CITY4", _p4.getCity());
		assertEquals("country should be set correctly", "MY_COUNTRY4", _p4.getCountry());
				
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_p2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testAddressDelete(
	) {
		// new() -> _c0
		AddressModel _c0 = new AddressModel();
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_c0);
		AddressModel _c1 = _response.readEntity(AddressModel.class);
		
		// read(_c0) -> _tmpObj
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _tmpObj = _response.readEntity(AddressModel.class);
		assertEquals("ID should be unchanged when reading a project (remote):", _c1.getId(), _tmpObj.getId());						

		// read(_c1) -> _tmpObj
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		_tmpObj = _response.readEntity(AddressModel.class);
		assertEquals("ID should be unchanged when reading a project (remote):", _c1.getId(), _tmpObj.getId());						
		
		// delete(_c1) -> OK
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_c1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_c1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressDoubleDelete() {
		// new() -> _c0
		AddressModel _c0 = new AddressModel();
		
		// create(_c0) -> _c1
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_c0);
		AddressModel _c1 = _response.readEntity(AddressModel.class);

		// read(_c1) -> OK
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_c1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_c1) -> OK
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_c1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_c1) -> NOT_FOUND   (try to read a deleted address)
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _c1 -> NOT_FOUND  (try to delete an already deleted address)
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_c1.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _c1 -> NOT_FOUND
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_c1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	/********************************** helper methods *********************************/			
	private AddressModel setDefaultValues(AddressModel cm, String suffix) {
		cm.setAttributeType("MY_ATTR_TYPE" + suffix);
		cm.setType("MY_TYPE" + suffix);
		cm.setMsgType("MY_MSG_TYPE" + suffix);
		cm.setValue("MY_VALUE" + suffix);
		cm.setStreet("MY_STREET" + suffix);
		cm.setPostalCode("MY_POSTAL_CODE" + suffix);
		cm.setCity("MY_CITY" + suffix);
		cm.setCountry("MY_COUNTRY" + suffix);
		return cm;
	}
}
