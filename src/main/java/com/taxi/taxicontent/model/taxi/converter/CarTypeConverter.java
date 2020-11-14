package com.taxi.taxicontent.model.taxi.converter;

import com.google.common.collect.ImmutableMap;
import com.taxi.taxicontent.type.CarType;

import javax.persistence.AttributeConverter;
import java.util.Map;

public class CarTypeConverter implements AttributeConverter<CarType, String> {

    private Map<CarType, String> map = ImmutableMap.of(
            CarType.BUSINESS, "business",
            CarType.COMFORT, "comfort",
            CarType.ECONOM, "econom"
    );

    @Override
    public String convertToDatabaseColumn(CarType carType) {
        if (carType == null) {
            return null;
        }

        return map.get(carType);
    }

    @Override
    public CarType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return map.entrySet().stream()
                .filter(entry -> dbData.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().orElse(null);
    }
}
