package com.taxi.taxicontent.model.taxi.converter;

import com.google.common.collect.ImmutableMap;
import com.taxi.taxicontent.type.DriverStatus;

import javax.persistence.AttributeConverter;
import java.util.Map;

public class DriverStatusConverter implements AttributeConverter<DriverStatus, String> {

    private Map<DriverStatus, String> map = ImmutableMap.of(
            DriverStatus.APPROVED, "approved",
            DriverStatus.REJECTED, "rejected",
            DriverStatus.PENDING, "pending"
    );

    @Override
    public String convertToDatabaseColumn(DriverStatus driverStatus) {
        if (driverStatus == null) {
            return null;
        }

        return map.get(driverStatus);
    }

    @Override
    public DriverStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return map.entrySet().stream()
                .filter(entry -> dbData.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().orElse(null);
    }
}
