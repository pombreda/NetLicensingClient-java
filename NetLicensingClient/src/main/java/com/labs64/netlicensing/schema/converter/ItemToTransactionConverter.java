package com.labs64.netlicensing.schema.converter;

import javax.xml.bind.DatatypeConverter;

import com.labs64.netlicensing.domain.entity.Transaction;
import com.labs64.netlicensing.domain.Constants;
import com.labs64.netlicensing.util.DateUtils;
import com.labs64.netlicensing.schema.SchemaFunction;
import com.labs64.netlicensing.schema.context.Item;
import com.labs64.netlicensing.schema.context.Property;

/**
 * Convert {@link Item} entity into {@link Transaction} object.
 */
public class ItemToTransactionConverter extends ItemToEntityBaseConverter<Transaction> {

    @Override
    public Transaction convert(final Item source) {
        final Transaction target = super.convert(source);

        target.setStatus(Transaction.Status.valueOf(SchemaFunction.propertyByName(source.getProperty(),
                Constants.Transaction.STATUS)
                .getValue()));
        target.setSource(Transaction.Source.valueOf(SchemaFunction.propertyByName(source.getProperty(),
                Constants.Transaction.SOURCE)
                .getValue()));
        if (SchemaFunction.propertyByName(source.getProperty(), Constants.PRICE).getValue() != null) {
            target.setPrice(DatatypeConverter.parseDecimal(SchemaFunction.propertyByName(source.getProperty(),
                    Constants.PRICE).getValue()));
        }
        if (SchemaFunction.propertyByName(source.getProperty(), Constants.DISCOUNT).getValue() != null) {
            target.setDiscount(DatatypeConverter.parseDecimal(SchemaFunction.propertyByName(source.getProperty(),
                    Constants.DISCOUNT).getValue()));
        }
        target.setCurrency(SchemaFunction.propertyByName(source.getProperty(), Constants.CURRENCY).getValue());

        if (SchemaFunction.propertyByName(source.getProperty(), Constants.Transaction.DATE_CREATED).getValue() != null) {
            target.setDateCreated(DateUtils.parseDate(SchemaFunction.propertyByName(
                    source.getProperty(), Constants.Transaction.DATE_CREATED).getValue()).getTime());
        }

        if (SchemaFunction.propertyByName(source.getProperty(), Constants.Transaction.DATE_CLOSED).getValue() != null) {
            target.setDateClosed(DateUtils.parseDate(SchemaFunction.propertyByName(
                    source.getProperty(), Constants.Transaction.DATE_CLOSED).getValue()).getTime());
        }

        // Custom properties
        for (final Property property : source.getProperty()) {
            if (!Transaction.getReservedProps().contains(property.getName())) {
                target.getTransactionProperties().put(property.getName(), property.getValue());
            }
        }

        return target;
    }

    @Override
    public Transaction newTarget() {
        return new Transaction();
    }

}
