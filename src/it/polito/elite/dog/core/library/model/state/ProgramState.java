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

package it.polito.elite.dog.core.library.model.state;


import it.polito.elite.dog.core.library.model.statevalue.NotProgrammedStateValue;
import it.polito.elite.dog.core.library.model.statevalue.ProgramInterruptedStateValue;
import it.polito.elite.dog.core.library.model.statevalue.ProgrammedStateValue;
import it.polito.elite.dog.core.library.model.statevalue.ProgrammedAndWaitingToStartStateValue;
import it.polito.elite.dog.core.library.model.statevalue.StateValue;


import it.polito.elite.dog.core.library.model.statevalue.EndProgrammedStateValue;



/**
* ProgramState - automatically generated by it.polito.elite.domotics.ontologies.dogont.utilities.DogOnt2Dog
*
* @author it.polito.elite.domotics.ontologies.dogont.utilities.DogOnt2Dog
*
*/
public class ProgramState extends DiscreteState
{
	/**
	 * The unique class version for serialization
	 */

	private static final long serialVersionUID = 1L;

	public static final String PROGRAMMEDANDWAITINGTOSTART = "programmedAndWaitingToStart";
	public static final String ENDPROGRAMMED = "endProgrammed";
	public static final String PROGRAMINTERRUPTED = "programInterrupted";
	public static final String PROGRAMMED = "programmed";
	public static final String NOTPROGRAMMED = "notProgrammed";


	/**
	 * Class constructor for states inheriting from DiscreteState.
	 */
	public ProgramState(ProgrammedAndWaitingToStartStateValue programmedandwaitingtostartstatevalue)
	{
		//call the super class constructor
		super(new StateValue[]{programmedandwaitingtostartstatevalue});
	}


	/**
	 * Class constructor for states inheriting from DiscreteState.
	 */
	public ProgramState(EndProgrammedStateValue endprogrammedstatevalue)
	{
		//call the super class constructor
		super(new StateValue[]{endprogrammedstatevalue});
	}


	/**
	 * Class constructor for states inheriting from DiscreteState.
	 */
	public ProgramState(ProgramInterruptedStateValue programinterruptedstatevalue)
	{
		//call the super class constructor
		super(new StateValue[]{programinterruptedstatevalue});
	}


	/**
	 * Class constructor for states inheriting from DiscreteState.
	 */
	public ProgramState(ProgrammedStateValue programmedstatevalue)
	{
		//call the super class constructor
		super(new StateValue[]{programmedstatevalue});
	}


	/**
	 * Class constructor for states inheriting from DiscreteState.
	 */
	public ProgramState(NotProgrammedStateValue notprogrammedstatevalue)
	{
		//call the super class constructor
		super(new StateValue[]{notprogrammedstatevalue});
	}

	@Deprecated
	private void initCommon()
	{
		this.old2NewMap.put(ProgramState.PROGRAMMEDANDWAITINGTOSTART, new ProgrammedAndWaitingToStartStateValue());
		this.old2NewMap.put(ProgramState.ENDPROGRAMMED, new EndProgrammedStateValue());
		this.old2NewMap.put(ProgramState.PROGRAMINTERRUPTED, new ProgramInterruptedStateValue());
		this.old2NewMap.put(ProgramState.PROGRAMMED, new ProgrammedStateValue());
		this.old2NewMap.put(ProgramState.NOTPROGRAMMED, new NotProgrammedStateValue());
	}
	/**
	 * Creates a state object in the Dog2.0 old way (before May 2012).
	 * 
	 * @param valueOfTheCurrentStateValue
	 */
	@Deprecated
	public ProgramState(Object valueOfTheCurrentStateValue)
	{
		this.initCommon();
		this.valueOfTheCurrentStateValue = valueOfTheCurrentStateValue;
		if(valueOfTheCurrentStateValue instanceof String)
		{
			this.currentStateValue[0]=this.old2NewMap.get((String)valueOfTheCurrentStateValue);
		}
	}
	/**
	 * Class constructor.
	 */
	public ProgramState(StateValue... currentStateValue)
	{
		super(currentStateValue);
	}

	/**
	 * Default constructor.
	 */
	public ProgramState()
	{
		super();
	}
}
