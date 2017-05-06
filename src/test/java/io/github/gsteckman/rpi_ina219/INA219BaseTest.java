package io.github.gsteckman.rpi_ina219;

/*
 * INA219BaseTest.java
 * 
 * Copyright 2017 Greg Steckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test of the INA219Base class using an INA219 Simulator.
 */
public class INA219BaseTest {
    
    /**
     * Performs a very basic test of the INA219Base class using the simulator.
     * @throws IOException Not thrown due to use of the simulator.
     */
    @Test
    public void basic() throws IOException {
        INA219Simulator s = new INA219Simulator(20e-3, 12.0);
        INA219Base i = new INA219Base(s, 0.1, 3.0, INA219.Brng.V32, INA219.Pga.GAIN_8, INA219.Adc.BITS_12,
                INA219.Adc.BITS_12);

        Assert.assertEquals(20e-3, i.getShuntVoltage(), 1E-9);
        Assert.assertEquals(12.0, i.getBusVoltage(), 1E-9);
        Assert.assertEquals(200E-3 * 12.0, i.getPower(), 0.01);
        Assert.assertEquals(200E-3, i.getCurrent(), 0.001);
    }
}
