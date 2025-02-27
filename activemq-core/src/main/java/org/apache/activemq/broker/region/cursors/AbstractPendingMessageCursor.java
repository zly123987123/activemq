/**
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.apache.activemq.broker.region.cursors;

import java.io.IOException;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.MessageReference;

/**
 * Abstract method holder for pending message (messages awaiting disptach to a consumer) cursor
 * 
 * @version $Revision$
 */
public class AbstractPendingMessageCursor implements PendingMessageCursor{

    protected int maxBatchSize=100;

    public void start() throws Exception{
    }

    public void stop() throws Exception{
    }

    public void add(ConnectionContext context,Destination destination) throws Exception{
    }

    public void remove(ConnectionContext context,Destination destination) throws Exception{
    }

    public boolean isRecoveryRequired(){
        return true;
    }

    public void addMessageFirst(MessageReference node) throws Exception{
    }

    public void addMessageLast(MessageReference node) throws Exception{
    }

    public void clear(){
    }

    public boolean hasNext(){
        return false;
    }

    public boolean isEmpty(){
        return false;
    }

    public MessageReference next(){
        return null;
    }

    public void remove(){
    }

    public void reset(){
    }

    public int size(){
        return 0;
    }

    public int getMaxBatchSize(){
        return maxBatchSize;
    }

    public void setMaxBatchSize(int maxBatchSize){
        this.maxBatchSize=maxBatchSize;
    }

    protected void fillBatch() throws Exception{
    }

    /**
     * Give the cursor a hint that we are about to remove messages from memory only
     */
    public void resetForGC(){
        reset();
    }
}
