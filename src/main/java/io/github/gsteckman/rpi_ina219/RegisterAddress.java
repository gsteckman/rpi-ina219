package io.github.gsteckman.rpi_ina219;

/*
 * RegisterAddress.java
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

/**
 * Enumeration of the register addresses used by the INA219.
 */
public enum RegisterAddress {
    CONFIGURATION(0), SHUNT_VOLTAGE(1), BUS_VOLTAGE(2), POWER(3), CURRENT(4), CALIBRATION(5);

    private final int addr;

    RegisterAddress(final int a) {
        addr = a;
    }

    int getValue() {
        return addr;
    }

}