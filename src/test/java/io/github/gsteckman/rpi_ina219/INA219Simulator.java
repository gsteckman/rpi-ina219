package io.github.gsteckman.rpi_ina219;

/*
 * INA219Simulator.java
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

/**
 * Simulates an INA219 without requiring I2C hardware for test purposes.
 */
public class INA219Simulator implements INA219RegisterIF {
    private int configurationRegister;
    private int calibrationRegister;
    private double shuntVoltage;
    private double busVoltage;

    /**
     * Constructs a new INA219 simulator using the provided voltage values.
     * 
     * @param shuntVoltage
     *            The voltage present across the shunt registor to be simulated.
     * @param busVoltage
     *            The bus voltage to be simulated.
     */
    public INA219Simulator(final double shuntVoltage, final double busVoltage) {
        configurationRegister = 0x399F;
        calibrationRegister = 0;
        this.shuntVoltage = shuntVoltage;
        this.busVoltage = busVoltage;
    }

    /**
     * {@inheritDoc}
     */
    public void writeRegister(RegisterAddress ra, int value) throws IOException {
        switch (ra) {
        case CONFIGURATION:
            configurationRegister = value & 0xBFFF; // clear bit 14
            break;
        case CALIBRATION:
            calibrationRegister = value & 0xFFFE;
            break;
        default:
            break;
        }
    }

    /**
     * {@inheritDoc}
     */
    public short readSignedRegister(RegisterAddress ra) throws IOException {
        switch (ra) {
        case CONFIGURATION:
            return (short) configurationRegister;
        case SHUNT_VOLTAGE:
            int sv = calculateShuntVoltage();
            return (short) sv;
        case BUS_VOLTAGE:
            int bv = calculateBusVoltageRegister();
            if (calculateShuntVoltage() > 32000 || calculateShuntVoltage() < -32000) {
                bv = bv | 0x1; // set overflow bit
            }
            return (short) bv;
        case POWER:
            return (short) ((calculateCurrentRegister() * (calculateBusVoltageRegister() >> 3)) / 5000);
        case CURRENT:
            return (short) calculateCurrentRegister();
        case CALIBRATION:
            return (short) calibrationRegister;
        default:
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    public int readRegister(RegisterAddress ra) throws IOException {
        return readSignedRegister(ra) & 0xFFFF;
    }

    private int calculateBusVoltageRegister() {
        return ((int) (busVoltage / 4E-3)) << 3;
    }

    private int calculateShuntVoltage() {
        return (int) Math.round(shuntVoltage / 10E-6);
    }

    private int calculateCurrentRegister() {
        return (int) ((calculateShuntVoltage() * calibrationRegister) / 4096);
    }
}
