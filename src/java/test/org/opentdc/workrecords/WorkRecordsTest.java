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

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opentdc.workrecords.WorkRecordModel;

import test.org.opentdc.AbstractTestClient;

public class WorkRecordsTest extends AbstractTestClient {

	private static final String APP_URI = "http://localhost:8080/opentdc-services-test/api/workrecord/";

	@BeforeClass
	public static void initializeTests(
	) {
		initializeTests(APP_URI);
	}
	
	private List<WorkRecordModel> list(
	) {
		System.out.println("listing all resources");
		webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		return (List<WorkRecordModel>)webclient.replacePath("/").getCollection(WorkRecordModel.class);
	}

	@Test
	public void test(
	) {
		List<WorkRecordModel> rates = list();
		System.out.println("list() = <" + rates + ">");
	}

}
