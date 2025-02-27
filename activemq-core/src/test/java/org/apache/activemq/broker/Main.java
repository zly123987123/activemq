/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.broker;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.util.UDPTraceBrokerPlugin;
import org.apache.activemq.broker.view.ConnectionDotFilePlugin;
import org.apache.activemq.broker.view.DestinationDotFilePlugin;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.demo.DefaultQueueSender;

import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.Session;

/**
 * A helper class which can be handy for running a broker in your IDE from the
 * activemq-core module.
 * 
 * @version $Revision$
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String brokerURI = "broker:(tcp://localhost:61616,stomp://localhost:61613)?persistent=false&useJmx=true";
        if (args.length > 0) {
            brokerURI = args[0];
        }
        try {
            // TODO - this seems to break interceptors for some reason
            // BrokerService broker = BrokerFactory.createBroker(new
            // URI(brokerURI));
            BrokerService broker = new BrokerService();
            broker.setPersistent(false);
            broker.setUseJmx(true);
            broker.setPlugins(new BrokerPlugin[] { new ConnectionDotFilePlugin(), new UDPTraceBrokerPlugin() });
            broker.addConnector("tcp://localhost:61616");
            broker.addConnector("stomp://localhost:61613");
            broker.start();

            // lets create a dummy couple of consumers
            Connection connection = new ActiveMQConnectionFactory().createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer1 = session.createConsumer(new ActiveMQQueue("Orders.IBM"));
            MessageConsumer consumer2 = session.createConsumer(new ActiveMQQueue("Orders.MSFT"), "price > 100");
            Session session2 = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer3 = session2.createConsumer(new ActiveMQQueue("Orders.MSFT"), "price > 200");

            // lets publish some messages so that there is some stuff to browse
            DefaultQueueSender.main(new String[] { "Prices.Equity.IBM" });
            DefaultQueueSender.main(new String[] { "Prices.Equity.MSFT" });
        }
        catch (Exception e) {
            System.out.println("Failed: " + e);
            e.printStackTrace();
        }
    }

}
