package io.github.gsteckman.rpi_ina219;

/*
 * INA219RegisterIF.java
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
 * This interface abstracts the reading/writing of INA219 registers. It's primary purpose is to allow the testing of the INA219Base class without the use of I2C hardware.
 */
interface INA219RegisterIF {
    /**
     * Write a register with provided value.
     * @param ra The address of the register to be written.
     * @param value The value to write to the register.
     * @throws IOException If the register could not be written.
     */
    void writeRegister(final RegisterAddress ra, final int value) throws IOException;

    /**
     * Reads the register at the specified address as an unsigned 16 bit value.
     * @param ra The address of the register to be read.
     * @return The read value.
     * @throws IOException If the register could not be read.
     */
    int readRegister(final RegisterAddress ra) throws IOException;

    /**
     * Reads the register at the specified address as a signed 16 bit value.
     * @param ra The address of the register to be read.
     * @return The read value.
     * @throws IOException If the register could not be read.
     */
    short readSignedRegister(final RegisterAddress ra) throws IOException;
}
