/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.deltaspike.test.core.impl.exception.control.flow;

import org.apache.deltaspike.core.api.exception.control.BeforeHandles;
import org.apache.deltaspike.core.api.exception.control.CaughtException;
import org.apache.deltaspike.core.api.exception.control.ExceptionHandler;
import org.apache.deltaspike.core.api.exception.control.Handles;

@ExceptionHandler
public class ProceedCauseHandler
{
    public static int BREADTH_FIRST_NPE_CALLED = 0;
    public static int BREADTH_FIRST_NPE_LOWER_PRECEDENCE_CALLED = 0;

    public static int DEPTH_FIRST_NPE_CALLED = 0;
    public static int DEPTH_FIRST_NPE_HIGHER_PRECEDENCE_CALLED = 0;

    public void npeInboundHandler(
            @BeforeHandles CaughtException<NullPointerException> event)
    {
        BREADTH_FIRST_NPE_CALLED++;
        event.skipCause();
    }

    public void npeLowerPrecedenceInboundHandler(
            @BeforeHandles(ordinal = -50) CaughtException<NullPointerException> event)
    {
        BREADTH_FIRST_NPE_LOWER_PRECEDENCE_CALLED++;
        event.handledAndContinue();
    }

    public void npeOutboundHandler(@Handles CaughtException<NullPointerException> event)
    {
        DEPTH_FIRST_NPE_CALLED++;
        event.skipCause();
    }

    public void npeHigherPrecedenceOutboundHandler(@Handles(ordinal = -10) CaughtException<NullPointerException> event)
    {
        DEPTH_FIRST_NPE_HIGHER_PRECEDENCE_CALLED++;
        event.handledAndContinue();
    }
}