package io.github.gsteckman.rpi_ina219;

/*
 * INA219SimulatorTest.java
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
 * Class to test the INA219Simulator.
 */
public class INA219SimulatorTest {

    /**
     * This tests the INA219Simulator using the example application in TI's INA219 data sheet.
     * 
     * @throws IOException
     *             Not thrown due to use of the simulator.
     */
    @Test
    public void testSimulatorExampleApplication() throws IOException {
        INA219Simulator s = new INA219Simulator(20e-3, 11.98);
        s.writeRegister(RegisterAddress.CONFIGURATION, 0x019F);
        s.writeRegister(RegisterAddress.CALIBRATION, 20480);

        Assert.assertEquals(0x19f, s.readRegister(RegisterAddress.CONFIGURATION));
        Assert.assertEquals(2000, s.readSignedRegister(RegisterAddress.SHUNT_VOLTAGE));
        Assert.assertEquals(2995, s.readRegister(RegisterAddress.BUS_VOLTAGE) >> 3);
        Assert.assertEquals(20480, s.readRegister(RegisterAddress.CALIBRATION));
        Assert.assertEquals(10000, s.readSignedRegister(RegisterAddress.CURRENT));
        Assert.assertEquals(5990, s.readRegister(RegisterAddress.POWER));
    }

    /**
     * This tests proper read and conversion of negative current values from the INA219Simulator.
     * 
     * @throws IOException
     *             Not thrown due to use of the simulator.
     */
    @Test
    public void testNegativeCurrent() throws IOException {
        INA219Simulator s = new INA219Simulator(-320e-3, 11.98);
        s.writeRegister(RegisterAddress.CONFIGURATION, 0x019F);
        s.writeRegister(RegisterAddress.CALIBRATION, 20480);
        Assert.assertEquals(0x19f, s.readRegister(RegisterAddress.CONFIGURATION));
        Assert.assertEquals(-32000, s.readSignedRegister(RegisterAddress.SHUNT_VOLTAGE));
        Assert.assertEquals(2995, s.readRegister(RegisterAddress.BUS_VOLTAGE) >> 3);
        Assert.assertEquals(20480, s.readRegister(RegisterAddress.CALIBRATION));
        Assert.assertEquals(-28928, s.readSignedRegister(RegisterAddress.CURRENT));
        Assert.assertEquals(35232, s.readRegister(RegisterAddress.POWER));
    }
}
