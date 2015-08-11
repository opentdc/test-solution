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

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opentdc.addressbooks.AddressModel;
import org.opentdc.addressbooks.AddressType;
import org.opentdc.addressbooks.AddressbookModel;
import org.opentdc.addressbooks.AddressbooksService;
import org.opentdc.addressbooks.AttributeType;
import org.opentdc.addressbooks.MessageType;
import org.opentdc.addressbooks.OrgModel;
import org.opentdc.addressbooks.OrgType;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

public class OrgAddressTest extends AbstractTestClient {
	private static AddressbookModel adb = null;
	private static OrgModel org = null;
	private WebClient wc = null;
		
	@Before
	public void initializeTests() {
		wc = createWebClient(ServiceUtil.ADDRESSBOOKS_API_URL, AddressbooksService.class);
		adb = AddressbookTest.createAddressbook(wc, this.getClass().getName(), Status.OK);
		org = OrgTest.create(wc, adb.getId(), "OrgAddressTest", OrgType.getDefaultOrgType());
	}
	
	@After
	public void cleanupTest() {
		AddressbookTest.delete(wc, adb.getId(), Status.NO_CONTENT);
		System.out.println("deleted 1 addressbook with 1 org");
		wc.close();
	}
	
	/********************************** address attributes tests *********************************/
	// not necessary, they are already tested in ContactAddressTest

	/********************************* REST service tests *********************************/	
	// create:  POST p "api/addressbooks/{abid}/org/{oid}/address"
	// read:    GET "api/addressbooks/{abid}/org/{oid}/address/{adrid}"
	// update:  PUT p "api/addressbooks/{abid}/org/{oid}/address/{adrid}"
	// delete:  DELETE "api/addressbooks/{abid}/org/{oid}/address/{ardid}"


	@Test
	public void testCreateReadDeleteWithEmptyConstructor() {
		AddressModel _model1 = new AddressModel();
		assertNull("id should not be set by empty constructor", _model1.getId());
		assertNull("addressType should not be set by empty constructor", _model1.getAddressType());
		assertNull("attributeType should not be set by empty constructor", _model1.getAttributeType());
		assertNull("msgType should not be set by empty constructor", _model1.getMsgType());
		assertNull("value should not be set by empty constructor", _model1.getValue());
		assertNull("street should not be set by empty constructor", _model1.getStreet());
		assertNull("postalCode should not be set by empty constructor", _model1.getPostalCode());
		assertNull("city should not be set by empty constructor", _model1.getCity());
		assertEquals("countryCode should be set to zero by empty constructor", 0, _model1.getCountryCode());
		
		Response _response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setAddressType(AddressType.EMAIL);
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setAttributeType(AttributeType.OTHER);

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setValue("MY_VALUE");
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _model2 = _response.readEntity(AddressModel.class);
		
		assertNull("create() should not change the id of the local object", _model1.getId());
		assertEquals("create() should not change the addressType of the local object", AddressType.EMAIL, _model1.getAddressType());
		assertEquals("create() should not change the attributeType of the local object", AttributeType.OTHER, _model1.getAttributeType());
		assertNull("create() should not change the msgType of the local object", _model1.getMsgType());
		assertEquals("create() should not change the value of the local object", "MY_VALUE", _model1.getValue());
		assertNull("create() should not change the street of the local object", _model1.getStreet());
		assertNull("create() should not change the postalCode of the local object", _model1.getPostalCode());
		assertNull("create() should not change the city of the local object", _model1.getCity());
		assertEquals("create() should not change the countryCode of the local object", 0, _model1.getCountryCode());

		assertNotNull("create() should set a valid id on the remote object returned", _model2.getId());
		assertEquals("addressType of returned object should be unchanged after remote create", AddressType.EMAIL, _model2.getAddressType()); 
		assertEquals("attributeType of returned object should still be unchanged after remote create", AttributeType.OTHER, _model2.getAttributeType());
		assertNull("msgType of returned object should still be null after remote create", _model2.getMsgType());
		assertEquals("value of returned object should still be unchanged after remote create", "MY_VALUE", _model2.getValue());
		assertNull("street of returned object should still be null after remote create", _model2.getStreet());
		assertNull("postalCode of returned object should still be null after remote create", _model2.getPostalCode());
		assertNull("city of returned object should still be null after remote create", _model2.getCity());
		assertEquals("countryCode of returned object should still be zero after remote create", 0, _model2.getCountryCode());

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model2.getId()).get();
		assertEquals("read(" + _model2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _model3 = _response.readEntity(AddressModel.class);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("addressType of returned object should be unchanged after remote create", _model2.getAddressType(), _model3.getAddressType());
		assertEquals("attributeType of returned object should be unchanged after remote create", _model2.getAttributeType(), _model3.getAttributeType());
		assertEquals("msgType of returned object should be unchanged after remote create", _model2.getMsgType(), _model3.getMsgType());
		assertEquals("value of returned object should be unchanged after remote create", _model2.getValue(), _model3.getValue());
		assertEquals("street of returned object should be unchanged after remote create", _model2.getStreet(), _model3.getStreet());
		assertEquals("postalCode of returned object should be unchanged after remote create", _model2.getPostalCode(), _model3.getPostalCode());
		assertEquals("city of returned object should be unchanged after remote create", _model2.getCity(), _model3.getCity());
		assertEquals("countryCode of returned object should be unchanged after remote create", _model2.getCountryCode(), _model3.getCountryCode());

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model3.getId()).delete();
		assertEquals("delete(" + _model3.getId() + ") should return with status NO_CONTENT:", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
//	"MY_ATTR_TYPE"		setAddressType			getAddressType		addressType
//	"MY_TYPE"			setType					getType				type
//	"MY_MSG_TYPE"		setMsgType				getMsgType			msgType
//	"MY_VALUE"			setValue				getValue			value
//	"MY_STREET"			setStreet				getStreet			street
//	"MY_POSTAL_CODE"	setPostalCode			getPostalCode		postalCode
//	"MY_CITY"			setCity					getCity				city
//	0					setCountryCode			getCountryCode		countryCode
	
	@Test
	public void testCreateReadDelete() {
		AddressModel _model1 = setPostalAddressDefaultValues(new AddressModel(), 0);
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("addressType should be set correctly", AddressType.POSTAL, _model1.getAddressType());
		assertEquals("attributeType should be set correctly", AttributeType.getDefaultAttributeType(), _model1.getAttributeType());
		assertNull("msgType should not be set", _model1.getMsgType());
		assertNull("value should be not be set", _model1.getValue());
		assertEquals("street should be set correctly", "MY_STREET0", _model1.getStreet());
		assertEquals("postalCode should be set correctly", "MY_POSTAL_CODE0", _model1.getPostalCode());
		assertEquals("city should be set correctly", "MY_CITY0", _model1.getCity());
		assertEquals("countryCode should be set correctly", 0, _model1.getCountryCode());

		Response _response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _model2 = _response.readEntity(AddressModel.class);
		
		assertNull("id should not be set by constructor", _model1.getId());
		assertEquals("addressType should be unchanged", AddressType.POSTAL, _model1.getAddressType());
		assertEquals("attributeType should be unchanged", AttributeType.getDefaultAttributeType(), _model1.getAttributeType());
		assertNull("msgType should be unchanged", _model1.getMsgType());
		assertNull("value should be be unchanged", _model1.getValue());
		assertEquals("street should be unchanged", "MY_STREET0", _model1.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE0", _model1.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY0", _model1.getCity());
		assertEquals("countryCode should be unchanged", 0, _model1.getCountryCode());
		
		assertNotNull("id of returned object should be set", _model2.getId());
		assertEquals("addressType should be unchanged", AddressType.POSTAL, _model2.getAddressType());
		assertEquals("attributeType should be unchanged", AttributeType.getDefaultAttributeType(), _model2.getAttributeType());
		assertNull("msgType should be unchanged", _model2.getMsgType());
		assertNull("value should be be unchanged", _model2.getValue());
		assertEquals("street should be unchanged", "MY_STREET0", _model2.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE0", _model2.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY0", _model2.getCity());
		assertEquals("countryCode should be unchanged", 0, _model2.getCountryCode());
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model2.getId()).get();
		assertEquals("read(" + _model2.getId() + ") should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _model3 = _response.readEntity(AddressModel.class);
		assertEquals("id of returned object should be the same", _model2.getId(), _model3.getId());
		assertEquals("addressType should be the same", _model2.getAddressType(), _model3.getAddressType());
		assertEquals("attributeType should be the same", _model2.getAttributeType(), _model3.getAttributeType());
		assertNull("msgType should be the same", _model3.getMsgType());
		assertNull("value should be be the same", _model3.getValue());
		assertEquals("street should be the same", _model2.getStreet(), _model3.getStreet());
		assertEquals("postalCode should be the same", _model2.getPostalCode(), _model3.getPostalCode());
		assertEquals("city should be the same", _model2.getCity(), _model3.getCity());
		assertEquals("countryCode should be the same", _model2.getCountryCode(), _model3.getCountryCode());
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model3.getId()).delete();
		assertEquals("delete(" + _model3.getId() + ") should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testClientSideId() {
		AddressModel _model1 = createUrlAddress(AttributeType.HOME, "testClientSideId");
		_model1.setId("LOCAL_ID");
		assertEquals("id should have changed", "LOCAL_ID", _model1.getId());
		// create(_am1) -> BAD_REQUEST
		Response _response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() with an id generated by the client should be denied by the server", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testDuplicateId() {
		Response _response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(
				createUrlAddress(AttributeType.HOME, "testDuplicateId"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _model1 = _response.readEntity(AddressModel.class);

		AddressModel _model2 = createUrlAddress(AttributeType.HOME, "testAddressProjectWithDuplicateId2");
		_model2.setId(_model1.getId());		// wrongly create a 2nd AddressModel object with the same ID
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model2);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testList() {
		ArrayList<AddressModel> _localList = new ArrayList<AddressModel>();		
		Response _response = null;
		wc.replacePath("/").path(adb.getId()).
			path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
			path(ServiceUtil.ADDRESS_PATH_EL);
		for (int i = 0; i < LIMIT; i++) {
			_response = wc.post(createUrlAddress(AttributeType.HOME, "testAddressList" + i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(AddressModel.class));
		}
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).get();
		List<AddressModel> _remoteList = new ArrayList<AddressModel>(wc.getCollection(AddressModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		ArrayList<String> _remoteListIds = new ArrayList<String>();
		for (AddressModel _model : _remoteList) {
			_remoteListIds.add(_model.getId());
		}
		
		for (AddressModel _model : _localList) {
			assertTrue("project <" + _model.getId() + "> should be listed", _remoteListIds.contains(_model.getId()));
		}
		
		for (AddressModel _model : _localList) {
			_response = wc.replacePath("/").path(adb.getId()).
					path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
					path(ServiceUtil.ADDRESS_PATH_EL).path(_model.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(AddressModel.class);
		}
		
		for (AddressModel _model : _localList) {
			_response = wc.replacePath("/").path(adb.getId()).
					path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
					path(ServiceUtil.ADDRESS_PATH_EL).path(_model.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}

	@Test
	public void testCreate() {	
		AddressModel _model1 = setPostalAddressDefaultValues(new AddressModel(), 1);
		AddressModel _model2 = setPostalAddressDefaultValues(new AddressModel(), 2);
		
		Response _response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _model3 = _response.readEntity(AddressModel.class);

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model2);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _model4 = _response.readEntity(AddressModel.class);		
		assertNotNull("ID should be set", _model3.getId());
		assertNotNull("ID should be set", _model4.getId());
		assertThat(_model4.getId(), not(equalTo(_model3.getId())));
		
		assertEquals("addressType should be unchanged", AddressType.POSTAL, _model3.getAddressType());
		assertEquals("attributeType should be unchanged", AttributeType.getDefaultAttributeType(), _model3.getAttributeType());
		assertNull("msgType should be unchanged", _model3.getMsgType());
		assertNull("value should be unchanged", _model3.getValue());
		assertEquals("street should be unchanged", "MY_STREET1", _model3.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE1", _model3.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY1", _model3.getCity());
		assertEquals("countryCode should be unchanged", 1, _model3.getCountryCode());
		
		assertEquals("addressType should be unchanged", AddressType.POSTAL, _model4.getAddressType());
		assertEquals("attributeType should be unchanged", AttributeType.getDefaultAttributeType(), _model4.getAttributeType());
		assertNull("msgType should be unchanged", _model4.getMsgType());
		assertNull("value should be unchanged", _model4.getValue());
		assertEquals("street should be unchanged", "MY_STREET2", _model4.getStreet());
		assertEquals("postalCode should be unchanged", "MY_POSTAL_CODE2", _model4.getPostalCode());
		assertEquals("city should be unchanged", "MY_CITY2", _model4.getCity());
		assertEquals("countryCode should be unchanged", 2, _model4.getCountryCode());
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model3.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model4.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testCreateDouble() {		
		Response _response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(createUrlAddress(AttributeType.HOME, "testAddressCreateDouble"));
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _model = _response.readEntity(AddressModel.class);
		assertNotNull("ID should be set:", _model.getId());		
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model);
		assertEquals("create() with a duplicate id should be denied by the server", Status.CONFLICT.getStatusCode(), _response.getStatus());

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testRead() {
		ArrayList<AddressModel> _localList = new ArrayList<AddressModel>();
		Response _response = null;
		wc.replacePath("/").path(adb.getId()).
			path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
			path(ServiceUtil.ADDRESS_PATH_EL);
		for (int i = 0; i < LIMIT; i++) {
			_response = wc.post(createUrlAddress(AttributeType.HOME, "testAddressRead" + i));
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(AddressModel.class));
		}
	
		for (AddressModel _model : _localList) {
			_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_response.readEntity(AddressModel.class);
		}

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).get();
		List<AddressModel> _remoteList = new ArrayList<AddressModel>(wc.getCollection(AddressModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		AddressModel _model1 = null;
		for (AddressModel _model : _remoteList) {
			_response = wc.replacePath("/").path(adb.getId()).
					path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
					path(ServiceUtil.ADDRESS_PATH_EL).path(_model.getId()).get();
			assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_model1 = _response.readEntity(AddressModel.class);
			assertEquals("ID should be unchanged when reading a project", _model.getId(), _model1.getId());						
		}

		for (AddressModel _model : _localList) {
			_response = wc.replacePath("/").path(adb.getId()).
					path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
					path(ServiceUtil.ADDRESS_PATH_EL).path(_model.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}
	}
		
	@Test
	public void testMultiRead() {
		AddressModel _model1 = setPostalAddressDefaultValues(new AddressModel(), 1);
		
		Response _response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		AddressModel _model2 = _response.readEntity(AddressModel.class);

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _model3 = _response.readEntity(AddressModel.class);
		assertEquals("ID should be unchanged after read:", _model2.getId(), _model3.getId());		

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model2.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _model4 = _response.readEntity(AddressModel.class);
		
		assertEquals("ID should be the same:", _model3.getId(), _model4.getId());
		assertEquals("addressType should be the same", _model3.getAddressType(), _model4.getAddressType());
		assertEquals("type should be the same", _model3.getAttributeType(), _model4.getAttributeType());
		assertEquals("msgType should be the same", _model3.getMsgType(), _model4.getMsgType());
		assertEquals("value should be the same", _model3.getValue(), _model4.getValue());
		assertEquals("street should be the same", _model3.getStreet(), _model4.getStreet());
		assertEquals("postalCode should be the same", _model3.getPostalCode(), _model4.getPostalCode());
		assertEquals("city should be the same", _model3.getCity(), _model4.getCity());
		assertEquals("countryCode should be the same", _model3.getCountryCode(), _model4.getCountryCode());
		
		assertEquals("ID should be the same:", _model3.getId(), _model2.getId());
		assertEquals("addressType should be the same", _model3.getAddressType(), _model2.getAddressType());
		assertEquals("type should be the same", _model3.getAttributeType(), _model2.getAttributeType());
		assertEquals("msgType should be the same", _model3.getMsgType(), _model2.getMsgType());
		assertEquals("value should be the same", _model3.getValue(), _model2.getValue());
		assertEquals("street should be the same", _model3.getStreet(), _model2.getStreet());
		assertEquals("postalCode should be the same", _model3.getPostalCode(), _model2.getPostalCode());
		assertEquals("city should be the same", _model3.getCity(), _model2.getCity());
		assertEquals("countryCode should be the same", _model3.getCountryCode(), _model2.getCountryCode());
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testUpdate() {
		Response _response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(
				createPostalAddress(AttributeType.WORK, "testUpdate2", "MY_POSTALCODE", "MY_CITY", 100));
		AddressModel _model1 = _response.readEntity(AddressModel.class);
		
		wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model1.getId()).put(setPostalAddressDefaultValues(_model1, 2));
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _model2 = _response.readEntity(AddressModel.class);

		assertNotNull("ID should be set", _model2.getId());
		assertEquals("ID should be unchanged", _model1.getId(), _model2.getId());
		assertEquals("addressType should be set correctly", AddressType.POSTAL, _model2.getAddressType());
		assertEquals("attributeType should be set correctly", AttributeType.getDefaultAttributeType(), _model2.getAttributeType());
		assertNull("msgType should not be set", _model2.getMsgType());
		assertNull("value should not be set", _model2.getValue());
		assertEquals("street should be set correctly", "MY_STREET2", _model2.getStreet());
		assertEquals("postalCode should be set correctly", "MY_POSTAL_CODE2", _model2.getPostalCode());
		assertEquals("city should be set correctly", "MY_CITY2", _model2.getCity());
		assertEquals("countryCode should be set correctly", 2, _model2.getCountryCode());
		
		// try to change the addressType (from POSTAL to PHONE) -> BAD_REQUEST (ValidationException)
		wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model2.getId()).put(setPhoneDefaultValues(_model2, 3));
		assertEquals("update() should return with status BAD_REQUEST(400)", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
				
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testAddressTypePhone() {
		// PHONE:   addressType, attributeType and value are mandatory, rest of fields are ignored
		AddressModel _model1 = new AddressModel();
		
		Response _response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setAddressType(AddressType.PHONE);

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setAttributeType(AttributeType.HOME);
		
		 _response = wc.replacePath("/").path(adb.getId()).
				 path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				 path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setValue("testAddressTypePhone");

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		AddressModel _model2 = _response.readEntity(AddressModel.class);
		assertEquals("addressType should be set correctly", AddressType.PHONE, _model2.getAddressType());
		assertEquals("attributeType should be set correctly", AttributeType.HOME, _model2.getAttributeType());
		assertEquals("value should be set correctly", "testAddressTypePhone", _model2.getValue());

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
		
	@Test
	public void testAddressTypeEmail() {
		// EMAIL:   addressType, attributeType and value are mandatory, rest of fields are ignored
		AddressModel _model1 = new AddressModel();
		
		Response _response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setAddressType(AddressType.EMAIL);

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setAttributeType(AttributeType.HOME);
		
		 _response = wc.replacePath("/").path(adb.getId()).
				 path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				 path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setValue("testAddressTypeEmail");

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		AddressModel _model2 = _response.readEntity(AddressModel.class);
		assertEquals("addressType should be set correctly", AddressType.EMAIL, _model2.getAddressType());
		assertEquals("attributeType should be set correctly", AttributeType.HOME, _model2.getAttributeType());
		assertEquals("value should be set correctly", "testAddressTypeEmail", _model2.getValue());

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
}
	
	@Test
	public void testAddressTypeWeb() {
		// WEB:   addressType, attributeType and value are mandatory, rest of fields are ignored
		AddressModel _model1 = new AddressModel();
		
		Response _response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setAddressType(AddressType.WEB);

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setAttributeType(AttributeType.HOME);
		
		 _response = wc.replacePath("/").path(adb.getId()).
				 path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				 path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setValue("testAddressTypeWeb");

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		AddressModel _model2 = _response.readEntity(AddressModel.class);
		assertEquals("addressType should be set correctly", AddressType.WEB, _model2.getAddressType());
		assertEquals("attributeType should be set correctly", AttributeType.HOME, _model2.getAttributeType());
		assertEquals("value should be set correctly", "testAddressTypeWeb", _model2.getValue());

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
}

	@Test
	public void testAddressTypeMessaging() {
		// MESSAGING:   addressType, attributeType, msgType and value are mandatory, rest of fields are ignored
		AddressModel _model1 = new AddressModel();
		
		Response _response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setAddressType(AddressType.MESSAGING);

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setAttributeType(AttributeType.HOME);
		
		 _response = wc.replacePath("/").path(adb.getId()).
				 path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				 path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setMsgType(MessageType.FACEBOOK);

		 _response = wc.replacePath("/").path(adb.getId()).
				 path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				 path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setValue("testAddressTypeMessaging");

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		AddressModel _model2 = _response.readEntity(AddressModel.class);
		assertEquals("addressType should be set correctly", AddressType.MESSAGING, _model2.getAddressType());
		assertEquals("type should be set correctly", AttributeType.HOME, _model2.getAttributeType());
		assertEquals("msgType should be set correctly", MessageType.FACEBOOK, _model2.getMsgType());
		assertEquals("value should be set correctly", "testAddressTypeMessaging", _model2.getValue());

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}

	@Test
	public void testAddressTypePostal() {
		// POSTAL: 	only addressType and attributeType are mandatory, all other fields can be empty (empty address)
		AddressModel _model1 = new AddressModel();
		
		Response _response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setAddressType(AddressType.POSTAL);

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status BAD_REQUEST", Status.BAD_REQUEST.getStatusCode(), _response.getStatus());
		_model1.setAttributeType(AttributeType.HOME);
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model1);
		assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());

		AddressModel _model2 = _response.readEntity(AddressModel.class);
		assertEquals("addressType should be set correctly", AddressType.POSTAL, _model2.getAddressType());
		assertEquals("type should be set correctly", AttributeType.HOME, _model2.getAttributeType());

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model2.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testDelete(
	) {
		Response _response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(
				createUrlAddress(AttributeType.WORK, "testAddressDelete"));
		AddressModel _model1 = _response.readEntity(AddressModel.class);
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _model2 = _response.readEntity(AddressModel.class);
		assertEquals("ID should be unchanged when reading a project (remote):", _model1.getId(), _model2.getId());						

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		_model2 = _response.readEntity(AddressModel.class);
		assertEquals("ID should be unchanged when reading a project (remote):", _model1.getId(), _model2.getId());						
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model1.getId()).delete();
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testDoubleDelete() {
		AddressModel _model = createUrlAddress(AttributeType.OTHER, "testDoubleDelete");
		
		Response _response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(_model);
		AddressModel _model1 = _response.readEntity(AddressModel.class);

		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model1.getId()).get();
		assertEquals("read() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model1.getId()).delete();		
		assertEquals("delete() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model1.getId()).get();
		assertEquals("read() should return with status NOT_FOUND", Status.NOT_FOUND.getStatusCode(), _response.getStatus());
	}
	
	@Test
	public void testModifications() {
		Response _response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).post(
				createUrlAddress(AttributeType.HOME, "testAddressModifications"));
		AddressModel _model1 = _response.readEntity(AddressModel.class);
		
		assertNotNull("create() should set createdAt", _model1.getCreatedAt());
		assertNotNull("create() should set createdBy", _model1.getCreatedBy());
		assertNotNull("create() should set modifiedAt", _model1.getModifiedAt());
		assertNotNull("create() should set modifiedBy", _model1.getModifiedBy());
		assertTrue("createdAt should be less than or identical to modifiedAt after create()", _model1.getCreatedAt().compareTo(_model1.getModifiedAt()) <= 0);
		assertEquals("createdBy and modifiedBy should be identical after create()", _model1.getCreatedBy(), _model1.getModifiedBy());
		
		_model1.setValue("MY_VALUE");
		wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model1.getId()).put(_model1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _model2 = _response.readEntity(AddressModel.class);

		assertEquals("update() should not change createdAt", _model1.getCreatedAt(), _model2.getCreatedAt());
		assertEquals("update() should not change createdBy", _model1.getCreatedBy(), _model2.getCreatedBy());
		
		assertThat(_model2.getModifiedAt(), not(equalTo(_model2.getCreatedAt())));
		// TODO: in our case, the modifying user will be the same; how can we test, that modifiedBy really changed ?

		_model1.setModifiedBy("MYSELF");
		_model1.setModifiedAt(new Date(1000));
		wc.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model1.getId()).put(_model1);
		assertEquals("update() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		AddressModel _model3 = _response.readEntity(AddressModel.class);
		
		assertThat(_model1.getModifiedBy(), not(equalTo(_model3.getModifiedBy())));
		assertThat(_model1.getModifiedAt(), not(equalTo(_model3.getModifiedAt())));
		
		_response = wc.replacePath("/").path(adb.getId()).
				path(ServiceUtil.ORG_PATH_EL).path(org.getId()).
				path(ServiceUtil.ADDRESS_PATH_EL).path(_model1.getId()).delete();		
		assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
	}
	
	/********************************** helper methods *********************************/			
	private AddressModel setPostalAddressDefaultValues(AddressModel model, int suffix) {
		model.setAddressType(AddressType.POSTAL);
		model.setAttributeType(AttributeType.getDefaultAttributeType());
		model.setStreet("MY_STREET" + suffix);
		model.setPostalCode("MY_POSTAL_CODE" + suffix);
		model.setCity("MY_CITY" + suffix);
		model.setCountryCode((short) suffix);
		return model;
	}
	
	private AddressModel setPhoneDefaultValues(AddressModel model, int suffix) {
		model.setAddressType(AddressType.PHONE);
		model.setAttributeType(AttributeType.getDefaultAttributeType());
		model.setValue("MY_PHONE" + suffix);
		return model;
	}
	
	/*
	private AddressModel setMsgDefaultValues(AddressModel am, String suffix) {
		am.setAddressType(AddressType.MESSAGING);
		am.setAttributeType(AttributeType.getDefaultAttributeType());
		am.setMsgType(MessageType.getDefaultMessageType());
		am.setValue("MY_VALUE" + suffix);
		return am;
	}
	*/
	
	public static AddressModel createAddress(
			AddressType adrType, 
			AttributeType attrType, 
			String value) {
		AddressModel _model = new AddressModel();
		_model.setAddressType(adrType);
		_model.setAttributeType(attrType);
		_model.setValue(value);
		return _model;
	}
	
	public static AddressModel createUrlAddress(
			AttributeType attrType,
			String value) {
		AddressModel _model = new AddressModel();
		_model.setAddressType(AddressType.WEB);
		_model.setAttributeType(attrType);
		_model.setValue(value);
		return _model;
	}

	public static AddressModel createPostalAddress(
			AttributeType attrType, 
			String street, 
			String postalCode, 
			String city, 
			int countryCode) 
	{
		AddressModel _model = new AddressModel();
		_model.setAddressType(AddressType.POSTAL);
		_model.setAttributeType(attrType);
		_model.setStreet(street);
		_model.setPostalCode(postalCode);
		_model.setCity(city);
		_model.setCountryCode((short) countryCode);
		return _model;
	}
	
	public static AddressModel createMsgAddress(
			AttributeType attrType,
			MessageType msgType,
			String value) {
		AddressModel _model = new AddressModel();
		_model.setAddressType(AddressType.MESSAGING);
		_model.setAttributeType(attrType);
		_model.setMsgType(msgType);
		_model.setValue(value);
		return _model;
	}
	
	protected int calculateMembers() {
		return 1;
	}
}
