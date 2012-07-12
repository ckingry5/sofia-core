package sofia.data.internal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sofia.data.PropertyEditor;

public class Inspector
{
	private Class<?> type;
	
	// TODO consider caching this information.
	private List<PropertyEditor> properties;


	// ----------------------------------------------------------
	public Inspector(Class<?> type)
	{
		this.type = type;
		
		properties = new ArrayList<PropertyEditor>();

		for (Method method : type.getMethods())
		{
			tryToAddProperty(type, method);
		}
		
		Collections.sort(properties);
	}
	
	
	// ----------------------------------------------------------
	private void tryToAddProperty(Class<?> type, Method method)
	{
		String name = method.getName();
		Class<?> valueType = method.getReturnType();

		// We're going to look for a method that looks like a getter. If it is,
		// then we look for a matching setter. If we find both, then we treat
		// it like a property.

		if (method.getParameterTypes().length == 0
				&& !Void.TYPE.equals(valueType))
		{
			// So far so good. The method is parameterless and returns
			// something other than void.

			String propertyName = name.replaceFirst("^(get|is)", "");

			if (Character.isLowerCase(propertyName.charAt(0)))
			{
				propertyName = Character.toUpperCase(propertyName.charAt(0))
						+ propertyName.substring(1);
			}

			try
			{
				// Find a setter with a matching name.

				Method possibleSetter = type.getMethod(
						"set" + propertyName, valueType);

				if (Void.TYPE.equals(possibleSetter.getReturnType())
						&& possibleSetter.getParameterTypes().length == 1
						&& possibleSetter.getParameterTypes()[0].equals(
								valueType))
				{
					// If the return type of the setter is void, and it takes
					// a single argument whose type is the same as the return
					// type of the getter, then we'll consider this to be a
					// property.

					PropertyEditor editor = PropertyEditor.create(valueType,
							propertyName, method, possibleSetter);
					
					if (editor != null)
					{
						properties.add(editor);
					}
				}
			}
			catch (Exception e)
			{
				// Do nothing.
			}
		}
	}


	// ----------------------------------------------------------
	public Class<?> getType()
	{
		return type;
	}
	
	
	// ----------------------------------------------------------
	public List<PropertyEditor> getProperties()
	{
		return properties;
	}
}
