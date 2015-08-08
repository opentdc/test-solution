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
package test.org.opentdc.texts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opentdc.texts.SingleLangText;
import org.opentdc.texts.TextModel;
import org.opentdc.texts.TextsService;
import org.opentdc.util.LanguageCode;
import org.opentdc.service.GenericService;
import org.opentdc.service.LocalizedTextModel;
import org.opentdc.service.ServiceUtil;

import test.org.opentdc.AbstractTestClient;

/*
 * Tests for batched listing of texts
 * @author Bruno Kaiser
 */
public class TextsListTest extends AbstractTestClient {
	private static WebClient wc = null;
	private static ArrayList<TextModel> testObjects = null;

	/**
	 * Initialize the test with several texts each containing some LocalizedTexts.
	 * More than double the amount of default list size objects are allocated.
	 */
	@BeforeClass
	public static void initializeTests() {
		wc = initializeTest(ServiceUtil.TEXTS_API_URL, TextsService.class);
		System.out.println("***** TextsBatchedListTest:");
		testObjects = new ArrayList<TextModel>();
		for (int i = 0; i < (2 * GenericService.DEF_SIZE + 5); i++) { // if DEF_SIZE == 25 -> _limit2 = 55
			TextModel _model = TextsTest.post(wc, new TextModel(String.format("%2d", i), "DESC"), Status.OK);
			LocalizedTextTest.post(wc, _model, new LocalizedTextModel(LanguageCode.DE, "eins" + i), Status.OK);
			LocalizedTextTest.post(wc, _model, new LocalizedTextModel(LanguageCode.EN, "two" + i), Status.OK);
			LocalizedTextTest.post(wc, _model, new LocalizedTextModel(LanguageCode.FR, "trois" + i), Status.OK);
			testObjects.add(_model);
		}
		System.out.println("created " + testObjects.size() + " test objects");
		TextsTest.printModelList("testObjects", testObjects);
	}

	@AfterClass
	public static void cleanupTest() {
		// removing all test objects
		for (TextModel _model : testObjects) {
			TextsTest.delete(wc, _model.getId(), Status.NO_CONTENT);
		}		
		System.out.println("deleted " + testObjects.size() + " test objects");
		wc.close();
	}
	
	@Test
	public void testAllListed() {
		List<SingleLangText> _list = TextsTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK);
		TextsTest.printSingleLangTextList("testAllListed", _list);
		ArrayList<String> _ids = new ArrayList<String>();
		for (SingleLangText _model : _list) {
			_ids.add(_model.getTextId());
		}		
		for (TextModel _model : testObjects) {
			assertTrue("Text <" + _model.getId() + "> should be listed", _ids.contains(_model.getId()));
		}
	}
	
	@Test
	public void testAllReadable() {
		for (TextModel _model : testObjects) {
			TextsTest.get(wc, _model.getId(), Status.OK);
		}			
	}

	@Test
	public void testBatchedList() {
		int _numberOfBatches = 0;
		int _numberOfReturnedObjects = 0;
		int _position = 0;
	
		List<SingleLangText> _batch = null;
		while(true) {
			_numberOfBatches++;
			_batch = TextsTest.list(wc, null, _position, -1, Status.OK);
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
	
	@Test
	public void testNextElements() {
		List<SingleLangText> _objs = TextsTest.list(wc, null, 5, 5, Status.OK);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
	
	@Test
	public void testLastElements() {
		int _totalMembers = calculateMembers();
		List<SingleLangText> _objs = TextsTest.list(wc, null, (_totalMembers - 4), 4, Status.OK);
		assertEquals("list() should return correct number of elements", 4, _objs.size());		
	}
	
	@Test 
	public void testOverEndOfList() {
		int _totalMembers = calculateMembers();
		List<SingleLangText> _objs = TextsTest.list(wc, null, (_totalMembers - 5), 10, Status.OK);
		assertEquals("list() should return correct number of elements", 5, _objs.size());		
	}
		
	protected int calculateMembers() {
		List<SingleLangText> _objs = TextsTest.list(wc, null, 0, Integer.MAX_VALUE, Status.OK);
		return _objs.size();
	}
}