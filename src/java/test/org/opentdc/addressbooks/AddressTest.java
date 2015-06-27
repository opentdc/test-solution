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
import org.opentdc.addressbooks.AddressType;
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
	private static final boolean TEST_HYBRID_ADDRESSES = false;
	
	@Before
	public void initializeTest(
	) {
		initializeTest(API, AddressbooksService.class);
		Response _response = webclient.replacePath("/").post(new AddressbookModel("AddressTest"));
		adb = _response.readEntity(AddressbookModel.class);
		System.out.println("AddressTest posted AddressbookModel " + adb.getId());
		ContactModel _cm = new ContactModel();
		_cm.setFirstName("Address");
		_cm.setLastName("Test");
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).post(_cm);
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
		// new() -> _am
		AddressModel _am = new AddressModel();
		assertNull("id should not be set by empty constructor", _am.getId());
		assertNull("attributeType should not be set by empty constructor", _am.getAttributeType());
		assertNull("type should not be set by empty constructor", _am.getType());
		assertNull("msgType should not be set by empty constructor", _am.getMsgType());
		assertNull("value should not be set by empty constructor", _am.getValue());
		assertNull("street should not be set by empty constructor", _am.getStreet());
		assertNull("postalCode should not be set by empty constructor", _am.getPostalCode());
		assertNull("city should not be set by empty constructor", _am.getCity());
		assertNull("country should not be set by empty constructor", _am.getCountry());
	}
			
	@Test
	public void testAddressIdAttributeChange() {
		// new() -> _am -> _am.setId()
		AddressModel _am = new AddressModel();
		assertNull("id should not be set by constructor", _am.getId());
		_am.setId("MY_ID");
		assertEquals("id should have changed:", "MY_ID", _am.getId());
	}

	// AttributeType
	@Test
	public void testAddressAttributeTypeChange() {
		// new() -> _am -> _am.setAttributeType()
		AddressModel _am = new AddressModel();
		assertNull("attributeType should not be set by empty constructor", _am.getAttributeType());
		_am.setAttributeType("MY_ATTR_TYPE");
		assertEquals("attributeType should have changed:", "MY_ATTR_TYPE", _am.getAttributeType());
	}
	
	// Type
	@Test
	public void testAddressTypeAttributeChange() {
		// new() -> _am -> _am.setType()
		AddressModel _am = new AddressModel();
		assertNull("type should not be set by empty constructor", _am.getType());
		_am.setType(AddressType.OTHER);
		assertEquals("type should have changed:", AddressType.OTHER, _am.getType());
	}

	// MsgType
	@Test
	public void testAddressMsgTypeAttributeChange() {
		// new() -> _am -> _am.setMsgType()
		AddressModel _am = new AddressModel();
		assertNull("msgType should not be set by empty constructor", _am.getMsgType());
		_am.setMsgType("MY_MSG_TYPE");
		assertEquals("msgType should have changed:", "MY_MSG_TYPE", _am.getMsgType());
	}
	
	// Value
	@Test
	public void testAddressValueAttributeChange() {
		// new() -> _am -> _am.setValue()
		AddressModel _am = new AddressModel();
		assertNull("value should not be set by empty constructor", _am.getValue());
		_am.setValue("MY_VALUE");
		assertEquals("value should have changed:", "MY_VALUE", _am.getValue());
	}
	
	// Street
	@Test
	public void testAddressStreetAttributeChange() {
		// new() -> _am -> _am.setStreet()
		AddressModel _am = new AddressModel();
		assertNull("street should not be set by empty constructor", _am.getStreet());
		_am.setStreet("MY_STREET");
		assertEquals("street should have changed:", "MY_STREET", _am.getStreet());
	}
	
	// PostalCode
	@Test
	public void testAddressPostalCodeAttributeChange() {
		// new() -> _am -> _am.setPostalCode()
		AddressModel _am = new AddressModel();
		assertNull("postalCode should not be set by empty constructor", _am.getPostalCode());
		_am.setPostalCode("MY_POSTAL_CODE");
		assertEquals("postalCode should have changed:", "MY_POSTAL_CODE", _am.getPostalCode());
	}
	
	// City
	@Test
	public void testAddressCityAttributeChange() {
		// new() -> _am -> _am.setCity()
		AddressModel _am = new AddressModel();
		assertNull("city should not be set by empty constructor", _am.getCity());
		_am.setCity("MY_CITY");
		assertEquals("city should have changed:", "MY_CITY", _am.getCity());
	}

	// Country
	@Test
	public void testAddressCountryAttributeChange() {
		// new() -> _am -> _am.setCountry()
		AddressModel _am = new AddressModel();
		assertNull("country should not be set by empty constructor", _am.getCountry());
		_am.setCountry("MY_COUNTRY");
		assertEquals("country should have changed:", "MY_COUNTRY", _am.getCountry());
	}
	
	@Test
	public void testAddressCreatedBy() {
		// new() -> _am -> _am.setCreatedBy()
		AddressModel _am = new AddressModel();
		assertNull("createdBy should not be set by empty constructor", _am.getCreatedBy());
		_am.setCreatedBy("MY_NAME");
		assertEquals("createdBy should have changed", "MY_NAME", _am.getCreatedBy());	
	}
	
	@Test
	public void testAddressCreatedAt() {
		// new() -> _am -> _am.setCreatedAt()
		AddressModel _am = new AddressModel();
		assertNull("createdAt should not be set by empty constructor", _am.getCreatedAt());
		_am.setCreatedAt(new Date());
		assertNotNull("createdAt should have changed", _am.getCreatedAt());
	}
		
	@Test
	public void testAddressModifiedBy() {
		// new() -> _am -> _am.setModifiedBy()
		AddressModel _am = new AddressModel();
		assertNull("modifiedBy should not be set by empty constructor", _am.getModifiedBy());
		_am.setModifiedBy("MY_NAME");
		assertEquals("modifiedBy should have changed", "MY_NAME", _am.getModifiedBy());	
	}
	
	@Test
	public void testAddressModifiedAt() {
		// new() -> _am -> _am.setModifiedAt()
		AddressModel _am = new AddressModel();
		assertNull("modifiedAt should not be set by empty constructor", _am.getModifiedAt());
		_am.setModifiedAt(new Date());
		assertNotNull("modifiedAt should have changed", _am.getModifiedAt());
	}

	/********************************* REST service tests *********************************/	
	
	// create:  POST p "api/addressbooks/{abid}/contact/{cid}/address"
	// read:    GET "api/addressbooks/{abid}/contact/{cid}/address/{adrid}"
	// update:  PUT p "api/addressbooks/{abid}/contact/{cid}/address/{adrid}"
	// delete:  DELETE "api/addressbooks/{abid}/contact/{cid}/address/{ardid}"


	@Test
	public void testAddressCreateReadDeleteWithEmptyConstructor() {
		// new() -> _am1
		AddressModel _am1 = new AddressModel();
		assertNull("id should not be set by empty constructor", _am1.getId());
		assertNull("attributeType should not be set by empty constructor", _am1.getAttributeType());
		assertNull("type should not be set by empty constructor", _am1.getType());
		assertNull("msgType should not be set by empty constructor", _am1.getMsgType());
		assertNull("value should not be set by empty constructor", _am1.getValue());
		assertNull("street should not be set by empty constructor", _am1.getStreet());
		assertNull("postalCode should not be set by empty constructor", _am1.getPostalCode());
		assertNull("city should not be set by empty constructor", _am1.getCity());
		assertNull("country should not be set by empty constructor", _am1.getCountry());
		
		// create(_am1) -> BAD_REQUEST (because of empty attributeType)
		Response _response = webclient.replacePath("/").post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setAttributeType("testAddressCreateReadDeleteWithEmptyConstructor");
		
		// create(_am1) -> BAD_REQUEST (because of empty type)
		_response = webclient.replacePath("/").post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setType(AddressType.OTHER);
		
		// create(_am1) -> BAD_REQUEST (because of empty value)
		_response = webclient.replacePath("/").post(_am1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_am1.setValue("MY_VALUE");
		
		// create(_am1) -> _am2
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am2 = _response.readEntity(AddressModel.class);
		
		// validate _am1
		assertNull("create() should not change the id of the local object", _am1.getId());
		assertEquals("create() should not change the attributeType of the local object", "testAddressCreateReadDeleteWithEmptyConstructor", _am1.getAttributeType());
		assertEquals("create() should not change the type of the local object", AddressType.OTHER, _am1.getType());
		assertNull("create() should not change the msgType of the local object", _am1.getMsgType());
		assertEquals("create() should not change the value of the local object", "MY_VALUE", _am1.getValue());
		assertNull("create() should not change the street of the local object", _am1.getStreet());
		assertNull("create() should not change the postalCode of the local object", _am1.getPostalCode());
		assertNull("create() should not change the city of the local object", _am1.getCity());
		assertNull("create() should not change the country of the local object", _am1.getCountry());

		// validate _am2
		assertNotNull("create() should set a valid id on the remote object returned", _am2.getId());
		assertEquals("attributeType of returned object should be unchanged after remote create", "testAddressCreateReadDeleteWithEmptyConstructor", _am2.getAttributeType());   // what is the correct value of a date?
		assertEquals("type of returned object should still be unchanged after remote create", AddressType.OTHER, _am2.getType());
		assertNull("msgType of returned object should still be null after remote create", _am2.getMsgType());
		assertEquals("value of returned object should still be unchanged after remote create", "MY_VALUE", _am2.getValue());
		assertNull("street of returned object should still be null after remote create", _am2.getStreet());
		assertNull("postalCode of returned object should still be null after remote create", _am2.getPostalCode());
		assertNull("city of returned object should still be null after remote create", _am2.getCity());
		assertNull("country of returned object should still be null after remote create", _am2.getCountry());

		// read(_am2) -> _am3
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am2.getId()).get();
		assertEquals("read(" + _am2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am3 = _response.readEntity(AddressModel.class);
		assertEquals("id of returned object should be the same", _am2.getId(), _am3.getId());
		assertEquals("attributeType of returned object should be unchanged after remote create", _am2.getAttributeType(), _am3.getAttributeType());
		assertEquals("type of returned object should be unchanged after remote create", _am2.getType(), _am3.getType());
		assertEquals("msgType of returned object should be unchanged after remote create", _am2.getMsgType(), _am3.getMsgType());
		assertEquals("value of returned object should be unchanged after remote create", _am2.getValue(), _am3.getValue());
		assertEquals("street of returned object should be unchanged after remote create", _am2.getStreet(), _am3.getStreet());
		assertEquals("postalCode of returned object should be unchanged after remote create", _am2.getPostalCode(), _am3.getPostalCode());
		assertEquals("city of returned object should be unchanged after remote create", _am2.getCity(), _am3.getCity());
		assertEquals("country of returned object should be unchanged after remote create", _am2.getCountry(), _am3.getCountry());
		// delete(_p3)
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am3.getId()).delete();
		assertEquals("delete(" + _am3.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
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
		// new(with custom attributes) -> _am1
		AddressModel _am1 = setPostalAddressDefaultValues(new AddressModel(), "");
		assertNull("id should not be set by constructor", _am1.getId());
		assertEquals("attributeType should be set correctly", "MY_ATTR_TYPE", _am1.getAttributeType());
		assertEquals("type should be set correctly", AddressType.OTHER, _am1.getType());
		if(TEST_HYBRID_ADDRESSES) {
			assertEquals("msgType should be set correctly", "MY_MSG_TYPE", _am1.getMsgType());
			assertEquals("value should be set correctly", "MY_VALUE", _am1.getValue());
		}
		assertEquals("street should be set correctly", "MY_STREET", _am1.getStreet());
		assertEquals("postalCode should be set correctly", "MY_POSTAL_CODE", _am1.getPostalCode());
		assertEquals("city should be set correctly", "MY_CITY", _am1.getCity());
		assertEquals("country should be set correctly", "MY_COUNTRY", _am1.getCountry());

		// create(_am1) -> _am2
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am2 = _response.readEntity(AddressModel.class);
		
		// validate the local object after the remote create()
		assertNull("id should not be set by constructor", _am1.getId());
		assertEquals("attributeType should be unchanged", "MY_ATTR_TYPE", _am1.getAttributeType());
		assertEquals("type should be unchanged", AddressType.OTHER, _am1.getType());
		if(TEST_HYBRID_ADDRESSES) {
			assertEquals("msgType should be unchanged", "MY_MSG_TYPE", _am1.getMsgType());
			assertEquals("value should be unchanged", "MY_VALUE", _am1.getValue());
		}
		assertEquals("street should be unchanged", "MY_STREET", _am1.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE", _am1.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY", _am1.getCity());
		assertEquals("country should be unchanged", "MY_COUNTRY", _am1.getCountry());
		
		// validate the returned remote object
		assertNotNull("id of returned object should be set", _am2.getId());
		assertEquals("attributeType should be unchanged", "MY_ATTR_TYPE", _am2.getAttributeType());
		assertEquals("type should be unchanged", AddressType.OTHER, _am2.getType());
		if(TEST_HYBRID_ADDRESSES) {
			assertEquals("msgType should be unchanged", "MY_MSG_TYPE", _am2.getMsgType());
			assertEquals("value should be unchanged", "MY_VALUE", _am2.getValue());
		}
		assertEquals("street should be unchanged", "MY_STREET", _am2.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE", _am2.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY", _am2.getCity());
		assertEquals("country should be unchanged", "MY_COUNTRY", _am2.getCountry());
		
		// read(_am1)  -> _am3
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am2.getId()).get();
		assertEquals("read(" + _am2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am3 = _response.readEntity(AddressModel.class);
		assertEquals("id of returned object should be the same", _am2.getId(), _am3.getId());
		assertEquals("attributeType should be the same", _am2.getAttributeType(), _am3.getAttributeType());
		assertEquals("type should be the same", _am2.getType(), _am3.getType());
		if(TEST_HYBRID_ADDRESSES) {
			assertEquals("msgType should be the same", _am2.getMsgType(), _am3.getMsgType());
			assertEquals("value should be the same", _am2.getValue(), _am3.getValue());
		}
		assertEquals("street should be the same", _am2.getStreet(), _am3.getStreet());
		assertEquals("postalCode should be the same", _am2.getPostalCode(), _am3.getPostalCode());
		assertEquals("city should be the same", _am2.getCity(), _am3.getCity());
		assertEquals("country should be the same", _am2.getCountry(), _am3.getCountry());
		
		// delete(_am3)
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am3.getId()).delete();
		assertEquals("delete(" + _am3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressProjectWithClientSideId() {
		// new() -> _am1 -> _am1.setId()
		AddressModel _am1 = createUrlAddress("testAddressProjectWithClientSideId", AddressType.OTHER, "MY_VALUE");
		_am1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _am1.getId());
		// create(_am1) -> BAD_REQUEST
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressProjectWithDuplicateId() {
		// create(new()) -> _am1
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(
				createUrlAddress("testAddressProjectWithDuplicateId", AddressType.OTHER, "MY_VALUE"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am1 = _response.readEntity(AddressModel.class);

		// new() -> _am2 -> _am2.setId(_am1.getId())
		AddressModel _am2 = createUrlAddress("testAddressProjectWithDuplicateId2", AddressType.OTHER, "MY_VALUE2");
		_am2.setId(_am1.getId());		// wrongly create a 2nd AddressModel object with the same ID
		
		// create(_am2) -> CONFLICT
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_am2);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressList() {
		ArrayList<AddressModel> _localList = new ArrayList<AddressModel>();		
		Response _response = null;
		webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS);
		for (int i = 0; i < LIMIT; i++) {
			// create(new()) -> _localList
			_response = webclient.post(createUrlAddress("testAddressList" + i, AddressType.OTHER, "MY_VALUE" + i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(AddressModel.class));
		}
		
		// list(/) -> _remoteList
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).get();
		List<AddressModel> _remoteList = new ArrayList<AddressModel>(webclient.getCollection(AddressModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (AddressModel _am : _remoteList) {
			_remoteListIds.add(_am.getId());
		}
		
		for (AddressModel _am : _localList) {
			assertTrue("project <" + _am.getId() + "> should be listed", _remoteListIds.contains(_am.getId()));
		}
		
		for (AddressModel _am : _localList) {
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(AddressModel.class);
		}
		
		for (AddressModel _am : _localList) {
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}

	@Test
	public void testAddressCreate() {	
		// new(custom attributes1) -> _am1
		AddressModel _am1 = setPostalAddressDefaultValues(new AddressModel(), "1");
		// new(custom attributes2) -> _am2
		AddressModel _am2 = setPostalAddressDefaultValues(new AddressModel(), "2");
		
		// create(_am1)  -> _am3
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_am1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am3 = _response.readEntity(AddressModel.class);

		// create(_am2) -> _am4
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_am2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am4 = _response.readEntity(AddressModel.class);		
		assertNotNull("ID should be set", _am3.getId());
		assertNotNull("ID should be set", _am4.getId());
		assertThat(_am4.getId(), not(equalTo(_am3.getId())));
		
		// validate attributes of _am3
		assertEquals("attributeType should be unchanged", "MY_ATTR_TYPE1", _am3.getAttributeType());
		assertEquals("type should be unchanged", AddressType.OTHER, _am3.getType());
		if(TEST_HYBRID_ADDRESSES) {
			assertEquals("msgType should be unchanged", "MY_MSG_TYPE1", _am3.getMsgType());
			assertEquals("value should be unchanged", "MY_VALUE1", _am3.getValue());
		}
		assertEquals("street should be unchanged", "MY_STREET1", _am3.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE1", _am3.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY1", _am3.getCity());
		assertEquals("country should be unchanged", "MY_COUNTRY1", _am3.getCountry());
		
		// validate attributes of _am4
		assertEquals("attributeType should be unchanged", "MY_ATTR_TYPE2", _am4.getAttributeType());
		assertEquals("type should be unchanged", AddressType.OTHER, _am4.getType());
		if(TEST_HYBRID_ADDRESSES) {
			assertEquals("msgType should be unchanged", "MY_MSG_TYPE2", _am4.getMsgType());
			assertEquals("value should be unchanged", "MY_VALUE2", _am4.getValue());
		}
		assertEquals("street should be unchanged", "MY_STREET2", _am4.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE2", _am4.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY2", _am4.getCity());
		assertEquals("country should be unchanged", "MY_COUNTRY2", _am4.getCountry());
		
		// delete(_am3) -> NO_CONTENT
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		// delete(_am4) -> NO_CONTENT
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testAddressCreateDouble() {		
		// create(new()) -> _am
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(createUrlAddress("testAddressCreateDouble", AddressType.OTHER, "MY_VALUE"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am = _response.readEntity(AddressModel.class);
		assertNotNull("ID should be set:", _am.getId());		
		
		// create(_am) -> CONFLICT
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_am);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		// delete(_am) -> NO_CONTENT
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressRead() {
		ArrayList<AddressModel> _localList = new ArrayList<AddressModel>();
		Response _response = null;
		webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS);
		for (int i = 0; i < LIMIT; i++) {
			_response = webclient.post(createUrlAddress("testAddressRead" + i, AddressType.OTHER, "MY_VALUE" + i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(AddressModel.class));
		}
	
		// test read on each local element
		for (AddressModel _am : _localList) {
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(AddressModel.class);
		}

		// test read on each listed element
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).get();
		List<AddressModel> _remoteList = new ArrayList<AddressModel>(webclient.getCollection(AddressModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		AddressModel _tmpObj = null;
		for (AddressModel _am : _remoteList) {
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_tmpObj = _response.readEntity(AddressModel.class);
			assertEquals("ID should be unchanged when reading a project", _am.getId(), _tmpObj.getId());						
		}

		for (AddressModel _am : _localList) {
			_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}
		
	@Test
	public void testAddressMultiRead() {
		// new() -> _am1
		AddressModel _am1 = setPostalAddressDefaultValues(new AddressModel(), "1");
		
		// create(_am1) -> _am2
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_am1);
		AddressModel _am2 = _response.readEntity(AddressModel.class);

		// read(_am2) -> _am3
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am3 = _response.readEntity(AddressModel.class);
		assertEquals("ID should be unchanged after read:", _am2.getId(), _am3.getId());		

		// read(_am2) -> _am4
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am4 = _response.readEntity(AddressModel.class);
		
		// but: the two objects are not equal !
		assertEquals("ID should be the same:", _am3.getId(), _am4.getId());
		assertEquals("attributeType should be the same", _am3.getAttributeType(), _am4.getAttributeType());
		assertEquals("type should be the same", _am3.getType(), _am4.getType());
		assertEquals("msgType should be the same", _am3.getMsgType(), _am4.getMsgType());
		assertEquals("value should be the same", _am3.getValue(), _am4.getValue());
		assertEquals("street should be the same", _am3.getStreet(), _am4.getStreet());
		assertEquals("postalCode should be the same", _am3.getPostalCode(), _am4.getPostalCode());
		assertEquals("city should be the same", _am3.getCity(), _am4.getCity());
		assertEquals("country should be the same", _am3.getCountry(), _am4.getCountry());
		
		assertEquals("ID should be the same:", _am3.getId(), _am2.getId());
		assertEquals("attributeType should be the same", _am3.getAttributeType(), _am2.getAttributeType());
		assertEquals("type should be the same", _am3.getType(), _am2.getType());
		assertEquals("msgType should be the same", _am3.getMsgType(), _am2.getMsgType());
		assertEquals("value should be the same", _am3.getValue(), _am2.getValue());
		assertEquals("street should be the same", _am3.getStreet(), _am2.getStreet());
		assertEquals("postalCode should be the same", _am3.getPostalCode(), _am2.getPostalCode());
		assertEquals("city should be the same", _am3.getCity(), _am2.getCity());
		assertEquals("country should be the same", _am3.getCountry(), _am2.getCountry());
		
		// delete(_am2)
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressUpdate() {
		// create() -> _am2
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(createPostalAddress("testAddressUpdate", AddressType.OTHER, "MY_STREET"));
		AddressModel _am2 = _response.readEntity(AddressModel.class);
		
		// change the attributes
		// update(_am2) -> _am3
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am2.getId()).put(setPostalAddressDefaultValues(_am2, "3"));
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am3 = _response.readEntity(AddressModel.class);

		assertNotNull("ID should be set", _am3.getId());
		assertEquals("ID should be unchanged", _am2.getId(), _am3.getId());
		assertEquals("attributeType should be set correctly", "MY_ATTR_TYPE3", _am3.getAttributeType());
		assertEquals("type should be set correctly", AddressType.OTHER, _am3.getType());
		if(TEST_HYBRID_ADDRESSES) {
			assertEquals("msgType should be set correctly", "MY_MSG_TYPE3", _am3.getMsgType());
			assertEquals("value should be set correctly", "MY_VALUE3", _am3.getValue());
		}
		assertEquals("street should be set correctly", "MY_STREET3", _am3.getStreet());
		assertEquals("postalCode should be set correctly", "MY_POSTAL_CODE3", _am3.getPostalCode());
		assertEquals("city should be set correctly", "MY_CITY3", _am3.getCity());
		assertEquals("country should be set correctly", "MY_COUNTRY3", _am3.getCountry());
		
		// reset the attributes
		// update(_am2) -> _am4
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am2.getId()).put(setPostalAddressDefaultValues(_am2, "4"));
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am4 = _response.readEntity(AddressModel.class);

		assertNotNull("ID should be set", _am4.getId());
		assertEquals("ID should be unchanged", _am2.getId(), _am4.getId());
		assertEquals("attributeType should be set correctly", "MY_ATTR_TYPE4", _am4.getAttributeType());
		assertEquals("type should be set correctly", AddressType.OTHER, _am4.getType());
		if(TEST_HYBRID_ADDRESSES) {		
			assertEquals("msgType should be set correctly", "MY_MSG_TYPE4", _am4.getMsgType());
			assertEquals("value should be set correctly", "MY_VALUE4", _am4.getValue());
		}
		assertEquals("street should be set correctly", "MY_STREET4", _am4.getStreet());
		assertEquals("postalCode should be set correctly", "MY_POSTAL_CODE4", _am4.getPostalCode());
		assertEquals("city should be set correctly", "MY_CITY4", _am4.getCity());
		assertEquals("country should be set correctly", "MY_COUNTRY4", _am4.getCountry());
				
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testAddressDelete(
	) {
		// create() -> _am1
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(createUrlAddress("testAddressDelete", AddressType.OTHER, "MY_VALUE"));
		AddressModel _am1 = _response.readEntity(AddressModel.class);
		
		// read(_am1) -> _tmpObj
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _tmpObj = _response.readEntity(AddressModel.class);
		assertEquals("ID should be unchanged when reading a project (remote):", _am1.getId(), _tmpObj.getId());						

		// read(_am1) -> _tmpObj
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		_tmpObj = _response.readEntity(AddressModel.class);
		assertEquals("ID should be unchanged when reading a project (remote):", _am1.getId(), _tmpObj.getId());						
		
		// delete(_am1) -> OK
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		// read the deleted object twice
		// read(_am1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read(_am1) -> NOT_FOUND
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressDoubleDelete() {
		// new() -> _am
		AddressModel _am = createUrlAddress("testAddressDoubleDelete", AddressType.OTHER, "MY_VALUE");
		
		// create(_am) -> _am1
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(_am);
		AddressModel _am1 = _response.readEntity(AddressModel.class);

		// read(_am1) -> OK
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		// delete(_am1) -> OK
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		// read(_am1) -> NOT_FOUND   (try to read a deleted address)
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// delete _am1 -> NOT_FOUND  (try to delete an already deleted address)
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am1.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		// read _am1 -> NOT_FOUND
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressModifications() {
		// create(new AddressModel()) -> _am1
		Response _response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).post(createUrlAddress("testAddressModifications", AddressType.OTHER, "MY_VALUE"));
		AddressModel _am1 = _response.readEntity(AddressModel.class);
		
		// test createdAt and createdBy
		assertNotNull("create() should set createdAt", _am1.getCreatedAt());
		assertNotNull("create() should set createdBy", _am1.getCreatedBy());
		// test modifiedAt and modifiedBy (= same as createdAt/createdBy)
		assertNotNull("create() should set modifiedAt", _am1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _am1.getModifiedBy());
		assertTrue("createdAt should be less than or identical to modifiedAt after create()", _am1.getCreatedAt().compareTo(_am1.getModifiedAt()) <= 0);
		assertEquals("createdBy and modifiedBy should be identical after create()", _am1.getCreatedBy(), _am1.getModifiedBy());
		
		// update(_am1)  -> _am2
		_am1.setValue("MY_VALUE");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am1.getId()).put(_am1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _am2 = _response.readEntity(AddressModel.class);

		// test createdAt and createdBy (unchanged)
		assertEquals("update() should not change createdAt", _am1.getCreatedAt(), _am2.getCreatedAt());
		assertEquals("update() should not change createdBy", _am1.getCreatedBy(), _am2.getCreatedBy());
		
		// test modifiedAt and modifiedBy (= different from createdAt/createdBy)
		assertThat(_am2.getModifiedAt(), not(equalTo(_am2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?
		// assertThat(_am2.getModifiedBy(), not(equalTo(_am2.getCreatedBy())));

		// update(_am1) with modifiedBy/At set on client side -> ignored by server
		_am1.setModifiedBy("MYSELF");
		_am1.setModifiedAt(new Date(1000));
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am1.getId()).put(_am1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _o3 = _response.readEntity(AddressModel.class);
		
		// test, that modifiedBy really ignored the client-side value "MYSELF"
		assertThat(_am1.getModifiedBy(), not(equalTo(_o3.getModifiedBy())));
		// check whether the client-side modifiedAt() is ignored
		assertThat(_am1.getModifiedAt(), not(equalTo(_o3.getModifiedAt())));
		
		// delete(_am1) -> NO_CONTENT
		_response = webclient.replacePath("/").path(adb.getId()).path(PATH_EL_CONTACT).path(contact.getId()).path(PATH_EL_ADDRESS).path(_am1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	/********************************** helper methods *********************************/			
	private AddressModel setPostalAddressDefaultValues(AddressModel cm, String suffix) {
		cm.setAttributeType("MY_ATTR_TYPE" + suffix);
		cm.setType(AddressType.OTHER);
		if(TEST_HYBRID_ADDRESSES) {
			cm.setValue("MY_VALUE" + suffix);
			cm.setMsgType("MY_MSG_TYPE" + suffix);
		}
		cm.setStreet("MY_STREET" + suffix);
		cm.setPostalCode("MY_POSTAL_CODE" + suffix);
		cm.setCity("MY_CITY" + suffix);
		cm.setCountry("MY_COUNTRY" + suffix);
		return cm;
	}
	
	public static AddressModel createUrlAddress(String attrType, AddressType type, String value) {
		AddressModel _am = new AddressModel();
		_am.setAttributeType(attrType);
		_am.setType(type);
		_am.setValue(value);
		return _am;
	}

	public static AddressModel createPostalAddress(String attrType, AddressType type, String value) {
		AddressModel _am = new AddressModel();
		_am.setAttributeType(attrType);
		_am.setType(type);
		_am.setStreet(value);
		return _am;
	}
}
