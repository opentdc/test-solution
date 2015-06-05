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
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.opentdc.workrecords.WorkRecordModel;
import org.opentdc.workrecords.WorkRecordsService;
import org.opentdc.service.GenericService;

import test.org.opentdc.AbstractTestClient;

public class WorkRecordBatchedListTest extends AbstractTestClient<WorkRecordsService> {
	public static final String API = "api/workrecord/";

	@Before
	public void initializeTests(
	) {
		initializeTest(API, WorkRecordsService.class);
	}

	@Test
	public void testWorkRecordBatchedList() {
		ArrayList<WorkRecordModel> _localList = new ArrayList<WorkRecordModel>();		
		Response _response = null;
		System.out.println("***** testWorkRecordListBatchDefSizeStatic:");
		webclient.replacePath("/");
		// we want to allocate more than double the amount of default list size objects
		int _batchSize = GenericService.DEF_SIZE;
		int _increment = 5;
		int _limit2 = 2 * _batchSize + _increment;		// if DEF_SIZE == 25 -> _limit2 = 55
		WorkRecordModel _res = null;
		for (int i = 0; i < _limit2; i++) {
			// create(new()) -> _localList
			_res = new WorkRecordModel();
			_res.setComment(String.format("%2d", i));
			_response = webclient.post(_res);
			assertEquals("create() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_localList.add(_response.readEntity(WorkRecordModel.class));
			System.out.println("posted WorkRecordModel " + _res.getComment());
		}
		System.out.println("****** locallist:");
		for (WorkRecordModel _rm : _localList) {
			System.out.println(_rm.getComment());
		}

		// get first batch
		// list(position=0, size=25) -> elements 0 .. 24
		webclient.resetQuery();
		_response = webclient.replacePath("/").get();
		List<WorkRecordModel> _remoteList1 = new ArrayList<WorkRecordModel>(webclient.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 1st Batch:");
		for (WorkRecordModel _rm : _remoteList1) {
			System.out.println(_rm.getComment());
		}
		assertEquals("size of lists should be the same", _batchSize, _remoteList1.size());
		
		// get second batch
		// list(position=25, size=25) -> elements 25 .. 49
		webclient.resetQuery();
		_response = webclient.replacePath("/").query("position", 25).get();
		List<WorkRecordModel> _remoteList2 = new ArrayList<WorkRecordModel>(webclient.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("size of lists should be the same", _batchSize, _remoteList2.size());
		System.out.println("****** 2nd Batch:");
		for (WorkRecordModel _rm : _remoteList2) {
			System.out.println(_rm.getComment());
		}
		
		// get rest 
		// list(position=50, size=25) ->   elements 50 .. 54
		webclient.resetQuery();
		_response = webclient.replacePath("/").query("position", 50).get();
		List<WorkRecordModel> _remoteList3 = new ArrayList<WorkRecordModel>(webclient.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		System.out.println("****** 3rd Batch:");
		for (WorkRecordModel _rm : _remoteList3) {
			System.out.println(_rm.getComment());
		}
		assertEquals("size of lists should be the same", _increment, _remoteList3.size());
		
		// testing the batches
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
		List<WorkRecordModel> _remoteList = null;
		System.out.println("***** testWorkRecordListIterate:");
		while(true) {
			_numberOfBatches++;
			webclient.resetQuery();
			_response = webclient.replacePath("/").query("position", _position).get();
			_remoteList = new ArrayList<WorkRecordModel>(webclient.getCollection(WorkRecordModel.class));
			assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
			_numberOfReturnedObjects += _remoteList.size();
			System.out.println("batch " + _numberOfBatches + ": position=" + _position + ", returnedObjects=" + _numberOfReturnedObjects);
			if (_remoteList.size() < GenericService.DEF_SIZE) {
				break;
			} else {
				_position += GenericService.DEF_SIZE;					
			}
		}
		assertEquals("number of batches should be as expected", 3, _numberOfBatches);
		assertEquals("should have returned all objects", _limit2, _numberOfReturnedObjects);
		assertEquals("last batch size should be as expected", _increment, _remoteList.size());
	
		// testing some explicit positions and sizes
		webclient.resetQuery();
		// get next 5 elements from position 5
		_response = webclient.replacePath("/").query("position", 5).query("size", 5).get();
		_remoteList = new ArrayList<WorkRecordModel>(webclient.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// get last 4 elements 
		webclient.resetQuery();
		_response = webclient.replacePath("/").query("position", _limit2-4).query("size", 4).get();
		_remoteList = new ArrayList<WorkRecordModel>(webclient.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 4, _remoteList.size());
		
		// read over end of list
		webclient.resetQuery();
		_response = webclient.replacePath("/").query("position", _limit2-5).query("size", 10).get();
		_remoteList = new ArrayList<WorkRecordModel>(webclient.getCollection(WorkRecordModel.class));
		assertEquals("list() should return with status OK", Status.OK.getStatusCode(), _response.getStatus());
		assertEquals("list() should return correct number of elements", 5, _remoteList.size());
		
		// removing all test objects
		for (WorkRecordModel _c : _localList) {
			_response = webclient.replacePath(_c.getId()).delete();
			assertEquals("delete() should return with status NO_CONTENT", Status.NO_CONTENT.getStatusCode(), _response.getStatus());
		}		
	}
}