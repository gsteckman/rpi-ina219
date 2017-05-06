package io.github.gsteckman.rpi_ina219;

/*
 * I2CRegisterImpl.java
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

/**
 * An implementation of the INA219RegisterIf that uses the Raspberry Pi I2C bus.
 */
class I2CRegisterImpl implements INA219RegisterIF {
    private static final Log LOG = LogFactory.getLog(INA219RegisterIF.class);
    private I2CDevice device;

    /**
     * Create a new I2CRegisterImple using the specified device address.
     * 
     * @param address
     *            Address of the device with which this instance communications.
     * @throws IOException
     *             If the I2C device could not be created.
     */
    I2CRegisterImpl(final INA219.Address address) throws IOException {
        try {
            device = I2CFactory.getInstance(I2CBus.BUS_1).getDevice(address.getValue());
        } catch (UnsupportedBusNumberException e) {
            LOG.error("BUS_1 no supported", e);
            return;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void writeRegister(final RegisterAddress ra, final int value) throws IOException {
        device.write(ra.getValue(), new byte[] { (byte) ((value >> 8) & 0xFF), (byte) (value & 0xFF) });
    }

    /**
     * {@inheritDoc}
     */
    public int readRegister(final RegisterAddress ra) throws IOException {
        byte[] buf = new byte[2];
        device.read(ra.getValue(), buf, 0, buf.length);
        return ((buf[0] & 0xFF) << 8) | (buf[1] & 0xFF);
    }

    /**
     * {@inheritDoc}
     */
    public short readSignedRegister(final RegisterAddress ra) throws IOException {
        byte[] buf = new byte[2];
        device.read(ra.getValue(), buf, 0, buf.length);
        return (short) ((buf[0] << 8) | (buf[1] & 0xFF));
    }

}
