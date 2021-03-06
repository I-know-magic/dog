/*
 * Dog - Device Driver
 * 
 * Copyright (c) 2012-2014 Dario Bonino and Luigi De Russis
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package it.polito.elite.dog.drivers.modbus.singlephaseactivepowermeter;

import it.polito.elite.dog.core.library.model.CNParameters;
import it.polito.elite.dog.core.library.model.ControllableDevice;
import it.polito.elite.dog.core.library.model.DeviceStatus;
import it.polito.elite.dog.core.library.model.devicecategory.SinglePhaseActivePowerMeter;
import it.polito.elite.dog.core.library.model.notification.SinglePhaseActivePowerMeasurementNotification;
import it.polito.elite.dog.core.library.model.state.SinglePhaseActivePowerMeasurementState;
import it.polito.elite.dog.core.library.model.statevalue.ActivePowerStateValue;
import it.polito.elite.dog.core.library.util.LogHelper;
import it.polito.elite.dog.drivers.modbus.network.ModbusDriverInstance;
import it.polito.elite.dog.drivers.modbus.network.info.ModbusRegisterInfo;
import it.polito.elite.dog.drivers.modbus.network.interfaces.ModbusNetwork;

import java.lang.reflect.Method;
import java.util.Set;

import javax.measure.DecimalMeasure;
import javax.measure.Measure;
import javax.measure.unit.SI;

import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;

/**
 * @author <a href="mailto:dario.bonino@polito.it">Dario Bonino</a>
 * @see <a href="http://elite.polito.it">http://elite.polito.it</a>
 * 
 */
public class ModbusSinglePhaseActivePowerMeterDriverInstance extends ModbusDriverInstance implements
		SinglePhaseActivePowerMeter
{
	// the class logger
	private LogHelper logger;
	
	/**
	 * @param network
	 * @param device
	 * @param gatewayAddress
	 * @param context
	 */
	public ModbusSinglePhaseActivePowerMeterDriverInstance(ModbusNetwork network, ControllableDevice device,
			String gatewayAddress, String gatewayPort, String gatewayProtocol, BundleContext context)
	{
		super(network, device, gatewayAddress, gatewayPort, gatewayProtocol);
		
		// create a logger
		this.logger = new LogHelper(context);
		
		// TODO: get the initial state of the device....(states can be updated
		// by reading notification group addresses)
		this.initializeStates();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polito.elite.domotics.model.devicecategory.SinglePhaseActivePowerMeter
	 * #getActivePower()
	 */
	@Override
	public Measure<?, ?> getActivePower()
	{
		return (Measure<?, ?>) this.currentState.getState(SinglePhaseActivePowerMeasurementState.class.getSimpleName())
				.getCurrentStateValue()[0].getValue();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polito.elite.domotics.model.devicecategory.SinglePhaseActivePowerMeter
	 * #getState()
	 */
	@Override
	public DeviceStatus getState()
	{
		return this.currentState;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polito.elite.domotics.model.devicecategory.SinglePhaseActivePowerMeter
	 * #notifyNewActivePowerValue(javax.measure.Measure)
	 */
	@Override
	public void notifyNewActivePowerValue(Measure<?, ?> powerValue)
	{
		// update the state
		ActivePowerStateValue pValue = new ActivePowerStateValue();
		pValue.setValue(powerValue);
		this.currentState.setState(SinglePhaseActivePowerMeasurementState.class.getSimpleName(),
				new SinglePhaseActivePowerMeasurementState(pValue));
		
		// notify the new measure
		((SinglePhaseActivePowerMeter) this.device).notifyNewActivePowerValue(powerValue);
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polito.elite.dog.core.library.model.devicecategory.
	 * SinglePhaseActivePowerMeter#updateStatus()
	 */
	@Override
	public void updateStatus()
	{
		((SinglePhaseActivePowerMeter) this.device).updateStatus();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polito.elite.dog.drivers.modbus.network.ModbusDriver#
	 * newMessageFromHouse (it.polito.elite.dog.drivers.modbus.network.info
	 * .ModbusRegisterInfo, java.lang.String)
	 */
	@Override
	public void newMessageFromHouse(ModbusRegisterInfo register, String value)
	{
		if (value != null)
		{
			// gets the corresponding notification set...
			Set<CNParameters> notificationInfos = this.register2Notification.get(register);
			
			// handle the notifications
			for (CNParameters notificationInfo : notificationInfos)
			{
				// black magic here...
				String notificationName = notificationInfo.getName();
				
				// get the hypothetical class method name
				String notifyMethod = "notify" + Character.toUpperCase(notificationName.charAt(0))
						+ notificationName.substring(1);
				
				// search the method and execute it
				try
				{
					// log notification
					this.logger.log(LogService.LOG_DEBUG, ModbusSinglePhaseActivePowerMeterDriver.logId + "Device: "
							+ this.device.getDeviceId() + " is notifying " + notificationName + " value:"
							+ register.getXlator().getValue());
					// get the method
					
					Method notify = ModbusSinglePhaseActivePowerMeterDriverInstance.class.getDeclaredMethod(
							notifyMethod, Measure.class);
					// invoke the method
					notify.invoke(this, DecimalMeasure.valueOf(register.getXlator().getValue()));
					
				}
				catch (Exception e)
				{
					// log the error
					this.logger.log(LogService.LOG_WARNING, ModbusSinglePhaseActivePowerMeterDriver.logId
							+ "Unable to find a suitable notification method for the datapoint: " + register + ":\n"
							+ e);
					e.printStackTrace();
				}
				
				// notify the monitor admin
				this.updateStatus();
			}
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polito.elite.dog.drivers.modbus.network.ModbusDriver#
	 * specificConfiguration()
	 */
	@Override
	protected void specificConfiguration()
	{
		// prepare the device state map
		this.currentState = new DeviceStatus(device.getDeviceId());
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polito.elite.dog.drivers.modbus.network.ModbusDriver#
	 * addToNetworkDriver (it.polito.elite.dog.drivers.modbus.network.info.
	 * ModbusRegisterInfo)
	 */
	@Override
	protected void addToNetworkDriver(ModbusRegisterInfo register)
	{
		this.network.addDriver(register, this);
	}
	
	private void initializeStates()
	{
		// The power unit
		String activePowerUOM = SI.WATT.toString();
		
		// search the energy unit of measures declared in the device
		// configuration
		for (ModbusRegisterInfo register : this.register2Notification.keySet())
		{
			Set<CNParameters> notificationInfos = this.register2Notification.get(register);
			
			for (CNParameters notificationInfo : notificationInfos)
			{
				
				if (notificationInfo.getName().equalsIgnoreCase(
						SinglePhaseActivePowerMeasurementNotification.notificationName))
				{
					activePowerUOM = register.getXlator().getUnitOfMeasure();
				}
			}
		}
		
		// create all the states
		ActivePowerStateValue pValue = new ActivePowerStateValue();
		pValue.setValue(DecimalMeasure.valueOf("0 " + activePowerUOM));
		this.currentState.setState(SinglePhaseActivePowerMeasurementState.class.getSimpleName(),
				new SinglePhaseActivePowerMeasurementState(pValue));
		
		// read the initial state
		this.network.readAll(this.register2Notification.keySet());
		
	}
	
}
