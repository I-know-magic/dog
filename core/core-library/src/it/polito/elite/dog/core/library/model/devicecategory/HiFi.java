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

package it.polito.elite.dog.core.library.model.devicecategory;


 
 /**
* HiFiCategory - automatically generated by DogOnt2Dog
*/


import it.polito.elite.dog.core.library.model.DeviceStatus;
import it.polito.elite.dog.core.library.model.state.*;

import javax.measure.Measure;

public interface HiFi extends Entertainment
{
	public static int MATCH_TYPE=100;
	public static int MATCH_SUB_TYPE=50;
	public static int MATCH_MANUFACTURER=0;

	public void setVolume(Integer volume);
	public void next();
	public void stepDown();
	public void prev();
	public void rew();
	public DeviceStatus getState();
	public void timedOn(Measure<?,?>  timeToOff);
	public void on();
	public void setChannel(Integer channel);
	public void goToTrack(Integer trackNumber);
	public void off();
	public void stepUp();
	public void stop();
	public void fwd();
	public void set(Object value);
	public void source(String source);
	public void down();
	public void pause();
	public void play();
	public void up();
	public void standBy();


	/*Generated Notifications*/

	/*Notification: StateChangeNotification*/
	public void notifyStateChanged(State newState);
}