package com.fiwio.iot.demeter.hw.mock

import com.fiwio.iot.demeter.hw.model.DigitalIO
import com.fiwio.iot.demeter.hw.model.DigitalIoType
import com.fiwio.iot.demeter.hw.model.DigitalValue

class MockDigitalIO(override var value: DigitalValue, override val name: String, override val type: DigitalIoType) : DigitalIO