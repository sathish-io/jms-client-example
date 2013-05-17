/*******************************************************************************
 * Copyright (c)  2013 Sathishkumar Kandasamy.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ******************************************************************************/
package com.satlabs.testbed.jms;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 * Test class for example configs
 */
@ContextConfiguration(locations="classpath:com/satlabs/testbed/ExampleConfigurationTests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JMSSendReceiveTest {
	
	//service
	@Autowired
	private  MessageSender messageSender;
	@Autowired
	private  MessageReceiver messageReceiver;

	/*
	 * test simple properties
	 */
	@Test
	public void testSpringload() throws Exception {
		assertNotNull(messageSender);
		assertNotNull(messageReceiver);
	}
	
	@Test
	public void testSend() throws Exception {
		
		String textMessage = "Message1";
		boolean status = messageSender.send(textMessage);
		assertTrue(status);
	}
	
	@Test
	public void testReceive() throws Exception {
		
		String received = messageReceiver.receive();
		assertNotNull(received);
	}
	
	@Test
	public void testSendAndReceive() throws Exception {
		
		String textMessage = "Hello world!";
		boolean status = messageSender.send(textMessage);
		assertTrue(status);
		
		
		String received = messageReceiver.receive();
		
		assertEquals(textMessage,received);
		
	}
	
}
