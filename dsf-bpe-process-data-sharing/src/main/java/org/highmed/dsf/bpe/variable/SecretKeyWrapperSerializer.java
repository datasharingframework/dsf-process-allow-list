package org.highmed.dsf.bpe.variable;

import java.io.IOException;
import java.util.Objects;

import org.camunda.bpm.engine.impl.variable.serializer.PrimitiveValueSerializer;
import org.camunda.bpm.engine.impl.variable.serializer.ValueFields;
import org.camunda.bpm.engine.variable.impl.value.UntypedValueImpl;
import org.highmed.dsf.bpe.variable.SecretKeyWrapperValues.SecretKeyWrapperValue;
import org.springframework.beans.factory.InitializingBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SecretKeyWrapperSerializer extends PrimitiveValueSerializer<SecretKeyWrapperValue>
		implements InitializingBean
{
	private final ObjectMapper objectMapper;

	public SecretKeyWrapperSerializer(ObjectMapper objectMapper)
	{
		super(SecretKeyWrapperValues.VALUE_TYPE);

		this.objectMapper = objectMapper;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		Objects.requireNonNull(objectMapper, "objectMapper");
	}

	@Override
	public void writeValue(SecretKeyWrapperValue value, ValueFields valueFields)
	{
		SecretKeyWrapper target = value.getValue();
		try
		{
			if (target != null)
				valueFields.setByteArrayValue(objectMapper.writeValueAsBytes(target));
		}
		catch (JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public SecretKeyWrapperValue convertToTypedValue(UntypedValueImpl untypedValue)
	{
		return SecretKeyWrapperValues.create((SecretKeyWrapper) untypedValue.getValue());
	}

	@Override
	public SecretKeyWrapperValue readValue(ValueFields valueFields, boolean asTransientValue)
	{
		byte[] bytes = valueFields.getByteArrayValue();

		try
		{
			SecretKeyWrapper target = (bytes == null || bytes.length <= 0) ? null
					: objectMapper.readValue(bytes, SecretKeyWrapper.class);
			return SecretKeyWrapperValues.create(target);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
