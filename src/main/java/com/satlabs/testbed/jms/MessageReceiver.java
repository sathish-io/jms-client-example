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

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * 
 * JMS text Message Sender using JNDI lookup.
 * 
 * @author Sathishkumar Kandasamy
 * 
 */

public class MessageReceiver {

	private String jmsProviderUrl;
	private String connectionFactoryName;
	private String queueName;

	private static final Logger logger = Logger.getLogger(MessageReceiver.class);
	private static final long MSG_RECEIVE_WAIT = 1000; 	

	public MessageReceiver() {
		// TODO Auto-generated constructor stub
	}

	public String receive() {

		String messageText = null;
		
		Connection connection = null;
		InitialContext initialContext = null;

		try {
			Properties props = new Properties();
			props.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
			props.put("java.naming.provider.url", getJmsProviderUrl());
			props.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");

			// Create an initial context to perform JNDI lookup.
			initialContext = new InitialContext(props);

			// Perfom a lookup on the queue
			Queue queue = (Queue) initialContext.lookup(getQueueName());

			// Perform a lookup on the Connection Factory
			ConnectionFactory factory = (ConnectionFactory) initialContext.lookup(getConnectionFactoryName());

			// Create a Connection
			connection = factory.createConnection();

			// Create a Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create a Message Producer
			MessageConsumer  consumer = session.createConsumer(queue);

			//start the connection
			connection.start();
			
			// Receive Message
			TextMessage  message = (TextMessage)consumer.receive(MSG_RECEIVE_WAIT);

			if(message != null) {
				messageText = message.getText();	
			}
			
			logger.info("Message received: " + messageText);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			logger.error("Failed to get message.", e);

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			logger.error("Failed to get message.", e);

		} catch (Exception e) {
			logger.error("Unknow error while get message.", e);
		} finally {
			try {
				// close the JMS connection
				if (initialContext != null) {
					initialContext.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return messageText;
	}

	/**
	 * @return the connectionFactoryName
	 */
	public String getConnectionFactoryName() {
		return connectionFactoryName;
	}

	/**
	 * @param connectionFactoryName
	 *            the connectionFactoryName to set
	 */
	public void setConnectionFactoryName(String connectionFactoryName) {
		this.connectionFactoryName = connectionFactoryName;
	}

	/**
	 * @return the queueName
	 */
	public String getQueueName() {
		return queueName;
	}

	/**
	 * @param queueName
	 *            the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	/**
	 * @return the jmsProviderUrl
	 */
	public String getJmsProviderUrl() {
		return jmsProviderUrl;
	}

	/**
	 * @param jmsProviderUrl the jmsProviderUrl to set
	 */
	public void setJmsProviderUrl(String jmsProviderUrl) {
		this.jmsProviderUrl = jmsProviderUrl;
	}


}