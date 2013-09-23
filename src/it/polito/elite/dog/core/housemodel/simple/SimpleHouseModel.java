/*
 * Dog - Core
 * 
 * Copyright (c) 2010-2013 Dario Bonino, Luigi De Russis and Emiliano Castellina
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
package it.polito.elite.dog.core.housemodel.simple;

import it.polito.elite.dog.core.housemodel.api.HouseModel;
import it.polito.elite.dog.core.housemodel.api.ModelUpdate;
import it.polito.elite.dog.core.library.jaxb.Configcommand;
import it.polito.elite.dog.core.library.jaxb.Confignotification;
import it.polito.elite.dog.core.library.jaxb.Configparam;
import it.polito.elite.dog.core.library.jaxb.ControlFunctionality;
import it.polito.elite.dog.core.library.jaxb.Device;
import it.polito.elite.dog.core.library.jaxb.DogHomeConfiguration;
import it.polito.elite.dog.core.library.jaxb.NotificationFunctionality;
import it.polito.elite.dog.core.library.jaxb.ObjectFactory;
import it.polito.elite.dog.core.library.model.DeviceCostants;
import it.polito.elite.dog.core.library.model.DeviceDescriptor;
import it.polito.elite.dog.core.library.model.notification.core.AddedNewDeviceNotification;
import it.polito.elite.dog.core.library.model.notification.core.RemovedDeviceNotification;
import it.polito.elite.dog.core.library.model.notification.core.UpdatedModelNotification;
import it.polito.elite.dog.core.library.util.ElementDescription;
import it.polito.elite.dog.core.library.util.EventFactory;
import it.polito.elite.dog.core.library.util.LogHelper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.log.LogService;

/**
 * This class implements the HouseModel interface.<br/>
 * It is named SimpleHouseModel since it works without using the ontology.
 * 
 * @author Emiliano Castellina
 * @author <a href="mailto:dario.bonino@polito.it">Dario Bonino</a>
 * @author <a href="mailto:luigi.derussis@polito.it">Luigi De Russis</a>
 * @see <a href="http://elite.polito.it">http://elite.polito.it</a>
 * 
 */
public class SimpleHouseModel implements HouseModel, ManagedService, ModelUpdate
{
	// bundle context
	private BundleContext context;
	// the device list read from file
	private Hashtable<String, DeviceDescriptor> deviceList;
	// devices group by their type
	private Hashtable<String, HashSet<String>> deviceCategoriesUriList;
	// the SVG house plan
	private String svgPlan;
	// the XML configuration
	private DogHomeConfiguration xlmConfiguration;
	// the logger
	private LogHelper logger;
	// reference to the Event Admin
	private AtomicReference<EventAdmin> eventAdmin;
	
	// ModelUpdate service registration
	private ServiceRegistration<?> srModelUpdate;
	// HouseModel service registration
	private ServiceRegistration<?> srHouseModel;
	
	/**
	 * Class constructor
	 */
	public SimpleHouseModel()
	{
		this.eventAdmin = new AtomicReference<EventAdmin>();
	}
	
	/**
	 * Activate this component (after its bind)
	 * 
	 * @param bundleContext
	 *            the bundle context
	 */
	public void activate(BundleContext bundleContext)
	{
		// init
		this.context = bundleContext;
		this.logger = new LogHelper(bundleContext);
		
		this.deviceCategoriesUriList = new Hashtable<String, HashSet<String>>();
		this.deviceList = new Hashtable<String, DeviceDescriptor>();
		
		this.svgPlan = "no map loaded";
	}
	
	/**
	 * Deactivate this component (before its unbind)
	 */
	public void deactivate()
	{
		// unregister services
		this.unRegister();
		
		// set everything to null
		this.context = null;
		this.logger = null;
		this.deviceCategoriesUriList = null;
		this.deviceList = null;
		this.svgPlan = null;
		
		this.srHouseModel = null;
		this.srModelUpdate = null;
	}
	
	/***
	 * Unregister its services from OSGi framework
	 */
	public void unRegister()
	{
		if (this.srHouseModel != null)
		{
			this.srHouseModel.unregister();
		}
		if (this.srModelUpdate != null)
		{
			this.srModelUpdate.unregister();
		}
	}
	
	/**
	 * Bind this component to the EventAdmin
	 * 
	 * @param ea
	 *            the EventAdmin service to bind
	 */
	public void addedEventAdmin(EventAdmin ea)
	{
		this.eventAdmin.set(ea);
	}
	
	/**
	 * Unbind this component
	 * 
	 * @param ea
	 *            the EventAdmin instance to remove
	 */
	public void removedEventAdmin(EventAdmin ea)
	{
		this.eventAdmin.compareAndSet(ea, null);
	}
	
	/**
	 * Listen for the configuration and start the XML parsing...
	 */
	@Override
	public void updated(Dictionary<String, ?> properties)
	{
		if (properties != null)
		{
			String devicesFileName = (String) properties.get(DeviceCostants.DEVICES);
			String svgFileName = (String) properties.get(DeviceCostants.SVGPLAN);
			
			if (devicesFileName != null && !devicesFileName.isEmpty())
			{
				this.loadXmlConfiguration(devicesFileName, DeviceCostants.DEVICES);
			}
			
			if (svgFileName != null && !svgFileName.isEmpty())
			{
				this.svgPlan = SimpleHouseModel.fileToString(svgFileName);
			}
			
			this.registerServices();
		}
	}
	
	/**
	 * Load and parse the XML configuration of the current environment.
	 * 
	 * @param xmlData
	 *            the XML file storing the configuration
	 * @param type
	 *            the configuration type (it supports {@link DeviceCostants}
	 *            .DEVICES only, at the moment)
	 * @return true if the parsing has been successful or the configuration is
	 *         not empty; false, otherwise.
	 */
	private boolean loadXmlConfiguration(String xmlData, String type)
	{
		try
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(DogHomeConfiguration.class.getPackage().getName());
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			
			if (type.equals(DeviceCostants.DEVICES))
			{
				this.xlmConfiguration = (DogHomeConfiguration) ((JAXBElement<DogHomeConfiguration>) unmarshaller
						.unmarshal(new StreamSource(System.getProperty("configFolder") + "/" + xmlData),
								DogHomeConfiguration.class)).getValue();
			}
			
		}
		catch (JAXBException e)
		{
			this.logger.log(LogService.LOG_ERROR, "JAXB Error", e);
		}
		
		long t1 = System.currentTimeMillis();
		
		this.getDeviceDescriptionFromXml();
		
		long t2 = System.currentTimeMillis();
		this.logger.log(LogService.LOG_INFO, String.format("Parsing time: JAXB %.2f s\n", (float) (t2 - t1) / 1000f));
		
		return this.deviceList.size() > 0;
		
	}
	
	/***
	 * Retrieve information about DeviceDescriptor from XML data
	 */
	private void getDeviceDescriptionFromXml()
	{
		if (this.xlmConfiguration != null)
		{
			// for each device
			for (Device dev : this.xlmConfiguration.getControllables().get(0).getDevice())
			{
				DeviceDescriptor deviceDescriptor = new DeviceDescriptor(dev.getName());
				deviceDescriptor.setDevTechnology(dev.getDomoticSystem());
				deviceDescriptor.setDevCategory(dev.getClazz());
				deviceDescriptor.setGateway(dev.getGateway());
				
				// actuator
				deviceDescriptor.setActuatorOf(dev.getActuatorOf());
				
				// controlled objects
				List<String> allControls = dev.getControls();
				if ((allControls != null) && (!allControls.isEmpty()))
					deviceDescriptor.setControlledObjects(new HashSet<String>(allControls));
				
				// has meter
				deviceDescriptor.setHasMeter(dev.getHasMeter());
				
				// set parameters
				for (Configparam param : dev.getParam())
				{
					deviceDescriptor.addDevSimpleConfigurationParam(param.getName(), param.getValue());
				}
				HashSet<ElementDescription> dogCommandsParameter = new HashSet<ElementDescription>();
				// parameter of the commands
				for (ControlFunctionality controlF : dev.getControlFunctionality())
				{
					for (Configcommand command : controlF.getCommands().getCommand())
					{
						
						ElementDescription dogElementDescription = new ElementDescription(command.getName(),
								command.getClazz());
						for (Configparam param : command.getParam())
						{
							dogElementDescription.addElementParam(param.getName(), param.getValue());
						}
						dogCommandsParameter.add(dogElementDescription);
						
					}
				} // end for controlfunctionality
				
				// notification parameters
				HashSet<ElementDescription> dogNotificationsParameter = new HashSet<ElementDescription>();
				for (NotificationFunctionality notificatinF : dev.getNotificationFunctionality())
				{
					for (Confignotification notification : notificatinF.getNotifications().getNotification())
					{
						ElementDescription dogElementDescription = new ElementDescription(notification.getName(),
								notification.getClazz());
						
						for (Configparam param : notification.getParam())
						{
							dogElementDescription.addElementParam(param.getName(), param.getValue());
						}
						dogNotificationsParameter.add(dogElementDescription);
						
					}
					
				}
				
				deviceDescriptor.setDevNotificationSpecificParams(dogNotificationsParameter);
				deviceDescriptor.setDevCommandSpecificParams(dogCommandsParameter);
				
				// add the Device Descriptor to the device list
				this.deviceList.put(deviceDescriptor.getDevURI(), deviceDescriptor);
				// add the element to the map that stores information grouped by
				// Device Category
				HashSet<String> devicesForDevCategory = this.deviceCategoriesUriList.get(deviceDescriptor
						.getDevCategory());
				if (devicesForDevCategory == null)
				{
					devicesForDevCategory = new HashSet<String>();
					this.deviceCategoriesUriList.put(deviceDescriptor.getDevCategory(), devicesForDevCategory);
				}
				devicesForDevCategory.add(deviceDescriptor.getDevURI());
			}
			
		}
		// final log
		this.logger.log(LogService.LOG_INFO,
				String.format("SimpleHouseModel contains %d device descriptions.", this.deviceList.size()));
		
	}
	
	/**
	 * Register the services provided by this bundle
	 */
	private void registerServices()
	{
		if (this.srHouseModel == null && this.srModelUpdate == null)
		{
			// register offered service
			this.srHouseModel = this.context.registerService(HouseModel.class.getName(), this, null);
			this.srModelUpdate = this.context.registerService(ModelUpdate.class.getName(), this, null);
		}
	}
	
	/*********************************************************************************
	 * 
	 * HouseModel service - implemented methods
	 * 
	 ********************************************************************************/
	@Override
	public Vector<DeviceDescriptor> getConfiguration()
	{
		return this.getConfigDevice();
	}
	
	@Override
	public String getSVGPlan()
	{
		return this.svgPlan;
	}
	
	/**
	 * Implementation of the getConfiguration() method. It inspects the list of
	 * devices to retrieve their information.
	 * 
	 * @return a list of {@link DeviceDescriptor}, containing devices
	 *         information
	 */
	private Vector<DeviceDescriptor> getConfigDevice()
	{
		Hashtable<String, HashSet<String>> condition = null;
		return this.getConfigDevice(condition);
	}
	
	/**
	 * Implementation of the getConfiguration() method. It inspects the list of
	 * devices to retrieve their information.
	 * 
	 * @param condition
	 *            null for getting all the devices, a list of device URI
	 *            otherwise
	 * @return a list of {@link DeviceDescriptor}, containing devices
	 *         information
	 */
	private Vector<DeviceDescriptor> getConfigDevice(Hashtable<String, HashSet<String>> condition)
	{
		Vector<DeviceDescriptor> devicesProp = new Vector<DeviceDescriptor>();
		HashSet<String> conditionDevices = null;
		HashSet<String> conditionDeviceCategories = null;
		
		if (condition == null)
		{ // get all the devices
			conditionDevices = new HashSet<String>(this.deviceList.keySet());
		}
		else
		{
			conditionDevices = condition.get(DeviceCostants.DEVICEURI);
			conditionDeviceCategories = condition.get(DeviceCostants.DEVICE_CATEGORY);
			if (conditionDeviceCategories.size() == 0 && conditionDevices.size() == 0)
			{
				conditionDevices = new HashSet<String>(this.deviceList.keySet());
			}
		}
		
		if (conditionDevices != null)
		{
			// select only the requested devices
			for (String uri : conditionDevices)
			{
				DeviceDescriptor uriProp = this.deviceList.get(uri);
				if (uriProp != null)
				{
					devicesProp.add(uriProp);
				}
				
			}
		}
		if (conditionDeviceCategories != null)
		{
			for (String deviceCategory : conditionDeviceCategories)
			{
				HashSet<String> devices = this.deviceCategoriesUriList.get(deviceCategory);
				if (devices != null)
				{
					for (String uri : devices)
					{
						DeviceDescriptor uriProp = this.deviceList.get(uri);
						if (uriProp != null)
						{
							
							devicesProp.add(uriProp);
						}
					}
				}
				
			}
			
		}
		return devicesProp;
	}
	
	/*********************************************************************************
	 * 
	 * ModelUpdate service - implemented methods
	 * 
	 ********************************************************************************/
	
	@Override
	public void addNewDevice(DeviceDescriptor descriptor)
	{
		this.addNewDeviceImpl(descriptor);
	}
	
	@Override
	public void removeDevice(String deviceURI)
	{
		this.removeDeviceImpl(deviceURI);
	}
	
	@Override
	public void setDeviceLocation(String deviceURI, String deviceLocation)
	{
		this.setDeviceLocationImpl(deviceURI, deviceLocation);
	}
	
	/**
	 * Effective implementation of the setDeviceLocation method. By using this
	 * method, it is possible to change/set the location of an existing device.
	 * 
	 * @param deviceURI
	 *            the device URI
	 * @param deviceLocation
	 *            the new device location
	 */
	private void setDeviceLocationImpl(String deviceURI, String deviceLocation)
	{
		DeviceDescriptor deviceProp = this.deviceList.get(deviceURI);
		if (deviceProp != null)
		{
			deviceProp.put(DeviceCostants.DEVICELOCATION, deviceLocation);
		}
		// notify the update
		this.notifyModelUpdated();
	}
	
	/**
	 * Effective implementation of the removeDevice method. It allows to remove
	 * an existing device from the running framework.
	 * 
	 * @param deviceURI
	 *            the device URI to remove
	 */
	private void removeDeviceImpl(String deviceURI)
	{
		DeviceDescriptor deviceProp = this.deviceList.remove(deviceURI);
		if (deviceProp != null)
		{
			String deviceCategory = deviceProp.getProperty(DeviceCostants.DEVICE_CATEGORY);
			if (deviceCategory != null)
			{
				HashSet<String> devices = this.deviceCategoriesUriList.get(deviceCategory);
				if (devices != null)
				{
					devices.remove(deviceURI);
				}
			}
		}
		if (this.xlmConfiguration != null)
		{
			Device removedDevice = null;
			List<Device> devices = this.xlmConfiguration.getControllables().get(0).getDevice();
			boolean found = false;
			for (int i = 0; i < devices.size() && !found; i++)
			{
				Device device = devices.get(i);
				if (device.getName().equals(deviceURI))
				{
					removedDevice = device;
					found = true;
				}
				
			}
			if (removedDevice != null)
			{
				this.xlmConfiguration.getControllables().get(0).getDevice().remove(removedDevice);
			}
		}
		// notify the change
		this.notifyRemovedDevice(new DeviceDescriptor(deviceURI));
		
	}
	
	/**
	 * Effective implementation of the addNewDevice method. It adds a new device
	 * to the running framework.
	 * 
	 * @param deviceDescriptor
	 *            the information about the device to add
	 */
	private void addNewDeviceImpl(DeviceDescriptor deviceDescriptor)
	{
		this.deviceList.put(deviceDescriptor.getDevURI(), deviceDescriptor);
		HashSet<String> deviceUris = null;
		
		if (this.deviceCategoriesUriList.containsKey(deviceDescriptor.getDevCategory()))
		{
			deviceUris = this.deviceCategoriesUriList.get(deviceDescriptor.getDevCategory());
		}
		else
		{
			deviceUris = new HashSet<String>();
			this.deviceCategoriesUriList.put(deviceDescriptor.getDevCategory(), deviceUris);
		}
		deviceUris.add(deviceDescriptor.getDevURI());
		
		if (this.xlmConfiguration != null)
		{
			ObjectFactory factory = new ObjectFactory();
			Device newDevice = factory.createDevice();
			
			newDevice.setName(deviceDescriptor.getDevURI());
			newDevice.setClazz(deviceDescriptor.getDevCategory());
			newDevice.setDomoticSystem(deviceDescriptor.getDevTechnology());
			if (deviceDescriptor.getDevLocations().size() == 0)
			{
				// set a defaul location
				newDevice.setIsIn("Flat");
			}
			else
			{
				newDevice.setIsIn(deviceDescriptor.getDevLocations().iterator().next());
			}
			newDevice.setDescription(deviceDescriptor.getDevDescription());
			
			this.xlmConfiguration.getControllables().get(0).getDevice().add(newDevice);
			
		}
		// notify the change
		this.notifyAddedNewDeviced(deviceDescriptor);
	}
	
	/*********************************************************************************
	 * 
	 * Methods for supporting the EventAdmin notification
	 * 
	 ********************************************************************************/
	/**
	 * Notify that the model has been updated (e.g., a new location has been set
	 * for a device).
	 */
	public synchronized void notifyModelUpdated()
	{
		// get a pointer to the EventAdmin service
		EventAdmin ea = eventAdmin.get();
		
		// if the EventAdmin is available
		if (ea != null)
		{
			// Create the notification
			UpdatedModelNotification notification = new UpdatedModelNotification();
			// Create the event
			Event event = EventFactory.createEvent(notification, this.getClass().getSimpleName());
			// Send the event to the EventAdmin
			ea.postEvent(event);
		}
		
	}
	
	/**
	 * Notify that a new device has been added.
	 * 
	 * @param desc
	 *            the {@link DeviceDescriptor} describing the newly added device
	 */
	public synchronized void notifyAddedNewDeviced(DeviceDescriptor desc)
	{
		// get a pointer to the EventAdmin service
		EventAdmin ea = eventAdmin.get();
		
		// if the EventAdmin is available
		if (ea != null)
		{
			// Create the notification
			AddedNewDeviceNotification notification = new AddedNewDeviceNotification(desc);
			// Create the event
			Event event = EventFactory.createEvent(notification, this.getClass().getSimpleName());
			// Send the event to the EventAdmin
			ea.postEvent(event);
		}
		
		// send also a generic update notification
		this.notifyModelUpdated();
		
	}
	
	/**
	 * Notify that a device has been removed from the framework.
	 * 
	 * @param desc
	 *            the {@link DeviceDescriptor} describing the removed device
	 */
	public void notifyRemovedDevice(DeviceDescriptor desc)
	{
		// get a pointer to the EventAdmin service
		EventAdmin ea = eventAdmin.get();
		
		// if the EventAdmin is available
		if (ea != null)
		{
			// Create the notification
			RemovedDeviceNotification notification = new RemovedDeviceNotification(desc);
			// Create the event
			Event event = EventFactory.createEvent(notification, this.getClass().getSimpleName());
			// Send the event to the EventAdmin
			ea.postEvent(event);
		}
		
		// send also a generic update notification
		this.notifyModelUpdated();
		
	}
	
	/*********************************************************************************
	 * 
	 * Bundle utilities
	 * 
	 ********************************************************************************/
	
	/**
	 * Read a XML-like file and convert it into a String. Used for reading the
	 * SVG house plan.
	 * 
	 * @param fileName
	 *            the XML-like file to read
	 * @return the file content into a String object
	 */
	protected static String fileToString(String fileName)
	{
		
		FileInputStream inputStream;
		StringBuffer buffer = null;
		try
		{
			inputStream = new FileInputStream(System.getProperty("configFolder") + "/" + fileName);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			buffer = new StringBuffer();
			
			while ((line = reader.readLine()) != null)
			{
				buffer.append(line);
				
			}
			reader.close();
		}
		catch (IOException e)
		{
			
			e.printStackTrace();
		}
		
		return buffer.toString();
	}
	
}
