package com.taxi.taxicontent.model.trucks.converter;

import com.google.common.collect.ImmutableMap;
import com.taxi.taxicontent.type.TruckOrderStatus;

import javax.persistence.AttributeConverter;
import java.util.Map;

public class TruckOrderStatusConverter implements AttributeConverter<TruckOrderStatus, String> {

    private Map<TruckOrderStatus, String> map = ImmutableMap.of(
            TruckOrderStatus.PENDING, TruckOrderStatus.PENDING.getStatus(),
            TruckOrderStatus.REJECTED, TruckOrderStatus.REJECTED.getStatus(),
            TruckOrderStatus.PROCESSING, TruckOrderStatus.PROCESSING.getStatus(),
            TruckOrderStatus.COMPLETED, TruckOrderStatus.COMPLETED.getStatus()
    );

    @Override
    public String convertToDatabaseColumn(TruckOrderStatus truckOrderStatus) {
        if (truckOrderStatus == null) {
            return null;
        }

        return map.get(truckOrderStatus);
    }

    @Override
    public TruckOrderStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return map.entrySet().stream()
                .filter(entry -> dbData.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().orElse(null);
    }
}
