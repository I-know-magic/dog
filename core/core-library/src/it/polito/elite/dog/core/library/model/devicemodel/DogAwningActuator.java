/*
 * Dog - Core
 *
 * Copyright (c) 2011-2016 Dario Bonino and Luigi De Russis
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
//
// Automatically generated by the DogOnt2Dog utility
//

package it.polito.elite.dog.core.library.model.devicemodel;


 
/**
* DogAwningActuatorModel - automatically generated by DogOnt2Dog
*/

import it.polito.elite.dog.core.library.model.devicecategory.AwningActuator;
import it.polito.elite.dog.core.library.model.AbstractDevice;
import it.polito.elite.dog.core.library.model.DeviceStatus;
import it.polito.elite.dog.core.library.model.DeviceDescriptor;
import org.osgi.framework.BundleContext;
import org.osgi.service.device.Device;
import it.polito.elite.dog.core.library.model.notification.*;
public class DogAwningActuator extends AbstractDevice implements AwningActuator
{

	public DogAwningActuator(BundleContext context, DeviceDescriptor deviceDescriptor)
	{
		super(context);


		this.setDeviceProperties(deviceDescriptor);


		this.registerDevice(Device.class.getName());
	}


	public void open()
	{
		if(this.driver!=null)
		{
			((AwningActuator) this.driver).open();
		}
	}

	public DeviceStatus getState()
	{
		if(this.driver!=null)
		{
			return ((AwningActuator) this.driver).getState();
		}
		 return null;
	}

	public void close()
	{
		if(this.driver!=null)
		{
			((AwningActuator) this.driver).close();
		}
	}



	/*Generated Notifications*/

	/*Notification: OpenNotification*/
	public void notifyOpen(){
		OpenNotification notificationEvent=new OpenNotification();
		notificationEvent.setDeviceUri(this.deviceId);
		// Send the notification through the EventAdmin
		notifyEventAdmin(notificationEvent);
	}
	/*Notification: SimpleNoMovementNotification*/
	public void notifyCeasedMovement(){
		SimpleNoMovementNotification notificationEvent=new SimpleNoMovementNotification();
		notificationEvent.setDeviceUri(this.deviceId);
		// Send the notification through the EventAdmin
		notifyEventAdmin(notificationEvent);
	}
	/*Notification: SimpleMovementNotification*/
	public void notifyStartedMovement(){
		SimpleMovementNotification notificationEvent=new SimpleMovementNotification();
		notificationEvent.setDeviceUri(this.deviceId);
		// Send the notification through the EventAdmin
		notifyEventAdmin(notificationEvent);
	}
	/*Notification: CloseNotification*/
	public void notifyClose(){
		CloseNotification notificationEvent=new CloseNotification();
		notificationEvent.setDeviceUri(this.deviceId);
		// Send the notification through the EventAdmin
		notifyEventAdmin(notificationEvent);
	}
	@Override
	public void updateStatus()
	{
		super.updateStatus();
	}
}
