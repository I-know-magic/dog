/*
 * Dog - Core
 * 
 * Copyright (c) 2011-2013 Dario Bonino and Luigi De Russis
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
//Automatically generated by it.polito.elite.domotics.ontologies.dogont.utilities.DogOnt2Dog
//

package it.polito.elite.dog.core.library.model.devicemodel;


 
 /**
* DogDvdModel - automatically generated by DogOnt2Dog
*/

import it.polito.elite.dog.core.library.model.DeviceStatus;
import it.polito.elite.dog.core.library.model.AbstractDevice;
import it.polito.elite.dog.core.library.model.DeviceDescriptor;
import it.polito.elite.dog.core.library.model.devicecategory.Dvd;
import it.polito.elite.dog.core.library.model.state.*;

import org.osgi.framework.BundleContext;
import org.osgi.service.device.Device;
public class DogDvd extends AbstractDevice implements Dvd
{

	public DogDvd(BundleContext context, DeviceDescriptor dogDeviceDescriptor)
	{
		super(context);


		this.setDeviceProperties(dogDeviceDescriptor);


		this.registerDevice(Device.class.getName());
	}


	public void setVolume(Integer volume)
	{
		if(this.driver!=null){
((Dvd) this.driver).setVolume(volume);
		}
	}

	public void next()
	{
		if(this.driver!=null){
((Dvd) this.driver).next();
		}
	}

	public void stepDown()
	{
		if(this.driver!=null){
((Dvd) this.driver).stepDown();
		}
	}

	public void prev()
	{
		if(this.driver!=null){
((Dvd) this.driver).prev();
		}
	}

	public void rew()
	{
		if(this.driver!=null){
((Dvd) this.driver).rew();
		}
	}

	public DeviceStatus getState()
	{
		if(this.driver!=null){
		return ((Dvd) this.driver).getState();
		}
		 return null;
	}

	public void on()
	{
		if(this.driver!=null){
((Dvd) this.driver).on();
		}
	}

	public void goToTrack(Integer trackNumber)
	{
		if(this.driver!=null){
((Dvd) this.driver).goToTrack(trackNumber);
		}
	}

	public void off()
	{
		if(this.driver!=null){
((Dvd) this.driver).off();
		}
	}

	public void stepUp()
	{
		if(this.driver!=null){
((Dvd) this.driver).stepUp();
		}
	}

	public void stop()
	{
		if(this.driver!=null){
((Dvd) this.driver).stop();
		}
	}

	public void fwd()
	{
		if(this.driver!=null){
((Dvd) this.driver).fwd();
		}
	}

	public void set(Object value)
	{
		if(this.driver!=null){
((Dvd) this.driver).set(value);
		}
	}

	public void down()
	{
		if(this.driver!=null){
((Dvd) this.driver).down();
		}
	}

	public void pause()
	{
		if(this.driver!=null){
((Dvd) this.driver).pause();
		}
	}

	public void play()
	{
		if(this.driver!=null){
((Dvd) this.driver).play();
		}
	}

	public void up()
	{
		if(this.driver!=null){
((Dvd) this.driver).up();
		}
	}

	public void standBy()
	{
		if(this.driver!=null){
((Dvd) this.driver).standBy();
		}
	}



	/*Generated Notifications*/

	/**
	/* Implements the DeviceCategory interface requirement 
	**/
	@Override
	public void notifyStateChanged(State newState)
	{
		super.notifyStateChanged(newState);
	}
}