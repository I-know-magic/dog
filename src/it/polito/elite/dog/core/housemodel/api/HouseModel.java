/*
 * Dog - Core
 * 
 * Copyright (c) 2013 Luigi De Russis
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
package it.polito.elite.dog.core.housemodel.api;

import it.polito.elite.dog.core.library.jaxb.BuildingEnvironment;
import it.polito.elite.dog.core.library.jaxb.Controllables;
import it.polito.elite.dog.core.library.jaxb.DogHomeConfiguration;
import it.polito.elite.dog.core.library.model.DeviceDescriptor;

import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * The API for providing the HouseModel service, i.e., the configuration of the
 * environment and its update.
 * 
 * @author <a href="mailto:luigi.derussis@polito.it">Luigi De Russis</a>
 * @see <a href="http://elite.polito.it">http://elite.polito.it</a>
 * 
 */
public interface HouseModel
{
	/**
	 * Update the list of devices configured in the real environment (only in
	 * the HouseModel, not in the framework)
	 * 
	 * @param descriptors
	 *            a {@link Vector} of {@link DeviceDescriptor} containing the
	 *            information about the devices to update
	 */
	public void updateConfiguration(Vector<DeviceDescriptor> updatedDescriptors);
	
	/**
	 * Update a device configured in the real environment (only in the
	 * HouseModel, not in the framework)
	 * 
	 * @param updatedDescriptor
	 *            the information about the device to update
	 */
	public void updateConfiguration(DeviceDescriptor updatedDescriptor);
	
	/**
	 * Add a list of devices to the current configuration (only in the
	 * HouseModel, not in the framework)
	 * 
	 * @param newDescriptors
	 *            a {@link Vector} of {@link DeviceDescriptor} containing the
	 *            information about the devices to add
	 */
	public void addToConfiguration(Vector<DeviceDescriptor> newDescriptors);
	
	/**
	 * Add a device to the current configuration (only in the HouseModel, not in
	 * the framework)
	 * 
	 * @param newDescriptor
	 *            the information about the device to add
	 */
	public void addToConfiguration(DeviceDescriptor newDescriptor);
	
	/**
	 * Remove a list of devices from the current configuration (only in the
	 * HouseModel, not in the framework)
	 * 
	 * @param removedDescriptors
	 *            a {@link Set} of {@link String} representing the device URIs
	 */
	public void removeFromConfiguration(Set<String> deviceURIs);
	
	/**
	 * Remove a device from the current configuration (only in the HouseModel,
	 * not in the framework)
	 * 
	 * @param removedDescriptor
	 *            the URI of the device to remove
	 */
	public void removeFromConfiguration(String deviceURI);
	
	/**
	 * Get the list of the devices configured in the real environment.
	 * 
	 * @return a {@link Vector} of {@link DeviceDescriptor} containing the
	 *         information about the configured devices
	 */
	public Vector<DeviceDescriptor> getConfiguration();
	
	/**
	 * Get the JAXB representation of the environment: a list of devices, with
	 * their properties, and a list of rooms/places.
	 * 
	 * @return {@link DogHomeConfiguration}
	 */
	public DogHomeConfiguration getJAXBConfiguration();
	
	/**
	 * Get the JAXB representation of the devices configured in the environment,
	 * with their properties
	 * 
	 * @return a {@link List} of {@link Controllables}, i.e., all the configured
	 *         devices
	 */
	public List<Controllables> getJAXBDevices();
	
	/**
	 * Get the JAXB representation of the building environment (flats, rooms,
	 * etc.)
	 * 
	 * @return a {@link List} of {@link BuildingEnvironment}, i.e., all the
	 *         building-related environments (flats, rooms, etc.)
	 */
	public List<BuildingEnvironment> getJAXBBuildingEnvironment();
	
	/**
	 * Get the SVG house plan.
	 * 
	 * @return a SVG image representing the current environment
	 */
	public String getSVGPlan();
	
	/**
	 * Update the description (i.e., long name) of a building environment
	 * present in the current configuration
	 * 
	 * @param URI
	 *            the unique name of the building environment to update
	 * @param description
	 *            the new description of the building environment to update
	 */
	//public void updateBuildingConfiguration(String URI, String description);
	
	/**
	 * Add a room to an existing flat or storey present in the configuration
	 * 
	 * @param URI
	 *            the unique name of the room to add
	 * @param description
	 *            the (optional) description of the room to add
	 * @param type
	 *            the room type
	 * @param isIn
	 *            the unique name representing where the room is located
	 */
	//public void addRoomToBuilding(String URI, String description, String type, String isIn);
	
	/**
	 * Add a flat to the building environment
	 * 
	 * @param name
	 *            the unique name of the flat to add
	 */
	//public void addFlatToBuilding(String URI);
	
	/**
	 * Add a storey to the building environment
	 * 
	 * @param name
	 *            the unique name of the storey to add
	 */
	//public void addStoreyToBuilding(String URI);
	
	/**
	 * Remove an existing building environment in the configuration with all its
	 * children (e.g., if a storey has been removed, all the rooms present in
	 * the storey are removed)
	 * 
	 * @param name
	 *            the unique name of the building environment to remove
	 */
	//public void removeFromBuildingConfiguration(String URI);
}
