package io.github.gsteckman.rpi_ina219;

/*
 * INA219.java
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
 * This class provides a high level interface to the Texas Instruments INA219 current monitor over the Raspberry Pi I2C bus.
 */
public class INA219 extends INA219Base {
    /**
     * Constructs a new INA219 instance.
     * 
     * @param address
     *            I2C Address of the INA219.
     * @param shuntResistance
     *            Value in ohms of the current sense shunt resistor.
     * @param maxExpectedCurrent
     *            Maximum expected current, in Amps.
     * @param busVoltageRange
     *            Either 16V or 32V.
     * @param pga
     *            Gain range.
     * @param badc
     *            Bus voltage ADC sample size and averaging setting.
     * @param sadc
     *            Shunt resistor voltage ADC sample size and averaging setting.
     * @throws IOException
     *             If the configuration or calibration registers cannot be written.
     */
    public INA219(final INA219.Address address, double shuntResistance, double maxExpectedCurrent,
            INA219.Brng busVoltageRange, INA219.Pga pga, INA219.Adc badc, INA219.Adc sadc) throws IOException {
        super(new I2CRegisterImpl(address), shuntResistance, maxExpectedCurrent, busVoltageRange, pga, badc, sadc);
    }

    /**
     * Enumeration of the valid I2C bus addresses to use with the INA219.
     * 
     * Note: this implementation does not include support addresses that have the A1 or A0 pins connected to SDA or SCL.
     *
     */
    public enum Address {
        ADDR_40(0x40), ADDR_41(0x41), ADDR_44(0x44), ADDR_45(0x45);

        private final int addr;

        Address(final int a) {
            addr = a;
        }

        int getValue() {
            return addr;
        }

        static public Address getAddress(int value) {
            switch (value) {
            case 0x40:
                return ADDR_40;
            case 0x41:
                return ADDR_41;
            case 0x44:
                return ADDR_44;
            case 0x45:
                return ADDR_45;
            default:
                return null;
            }

        }
    }

    /**
     * Enum for the Bus Voltage Range setting (BRNG)
     */
    public enum Brng {
        V16(0), // 16 Volts
        V32(1); // 32 Volts

        private int value;

        Brng(int val) {
            value = val;
        }

        int getValue() {
            return value;
        }
    }

    /**
     * Enum for the PGA gain and range setting options.
     */
    public enum Pga {
        GAIN_1(0), // 1
        GAIN_2(1), // /2
        GAIN_4(2), // /4
        GAIN_8(3); // /8

        private int value;

        Pga(int val) {
            value = val;
        }

        int getValue() {
            return value;
        }
    }

    /**
     * Enum for the Bus and Shunt ADC Resolution/Averaging settings.
     */
    public enum Adc {
        BITS_9(0), //9 bit samples
        BITS_10(1), //10 bit samples
        BITS_11(2), //11 bit samples
        BITS_12(3), //12 bit samples
        SAMPLES_2(9), //2 sample average
        SAMPLES_4(10), //4 sample average
        SAMPLES_8(11), //8 sample average
        SAMPLES_16(12), //16 sample average
        SAMPLES_32(13), //32 sample average
        SAMPLES_64(14), //64 sample average
        SAMPLES_128(15); //128 sample average

        private int value;

        Adc(int val) {
            value = val;
        }

        int getValue() {
            return value;
        }
    }
}
