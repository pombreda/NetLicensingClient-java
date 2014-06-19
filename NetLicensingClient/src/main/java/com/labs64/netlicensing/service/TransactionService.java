package com.labs64.netlicensing.service;

import com.labs64.netlicensing.domain.entity.Transaction;
import com.labs64.netlicensing.domain.vo.Page;
import com.labs64.netlicensing.exception.BaseCheckedException;
import com.labs64.netlicensing.domain.vo.Context;

/**
 * Provides transaction handling routines.
 * <p>
 * Transaction is created each time change to {@linkplain LicenseService licenses} happens. For instance licenses are
 * obtained by a licensee, licenses disabled by vendor, licenses deleted, etc. Transaction is created no matter what
 * source has initiated the change to licenses: it can be either a direct purchase of licenses by a licensee via
 * NetLicensing Shop, or licenses can be given to a licensee by a vendor. Licenses can also be assigned implicitly by
 * NetLicensing if it is defined so by a license model (e.g. evaluation license may be given automatically). All these
 * events are reflected in transactions. Of all the transaction handling routines only read-only routines are exposed to
 * the public API, as transactions are only allowed to be created and modified by NetLicensing internally.
 */
public interface TransactionService {

    /**
     * Creates new transaction object with given properties.
     * 
     * This routine is for internal use by NetLicensing. Where appropriate, transactions will be created by NetLicensing
     * automatically.
     * 
     * @param context
     *            determines the vendor on whose behalf the call is performed
     * @param newTransaction
     *            non-null properties will be taken for the new object, null properties will either stay null, or will
     *            be set to a default value, depending on property.
     * @return the newly created transaction object
     * @throws BaseCheckedException
     *             any subclass of {@linkplain BaseCheckedException}. These exceptions will be transformed to the
     *             corresponding service response messages.
     */
    Transaction create(Context context, Transaction newTransaction) throws BaseCheckedException;

    /**
     * Creates new transaction object with default properties.
     * 
     * This routine is for internal use by NetLicensing. Where appropriate, transactions will be created by NetLicensing
     * automatically.
     * 
     * @param context
     *            determines the vendor on whose behalf the call is performed
     * @param source
     *            source creating transaction
     * @return the newly created transaction object
     * @throws BaseCheckedException
     *             any subclass of {@linkplain BaseCheckedException}. These exceptions will be transformed to the
     *             corresponding service response messages.
     */
    Transaction createDefault(Context context, Transaction.Source source) throws BaseCheckedException;

    /**
     * Gets transaction by its number.
     * 
     * Use this operation for getting details about certain transaction. List of all transactions can be obtained by the
     * {@link #list(Context, String)} operation.
     * 
     * @param context
     *            determines the vendor on whose behalf the call is performed
     * @param number
     *            the transaction number
     * @return the transaction
     * @throws BaseCheckedException
     *             any subclass of {@linkplain BaseCheckedException}. These exceptions will be transformed to the
     *             corresponding service response messages.
     */
    Transaction get(Context context, String number) throws BaseCheckedException;

    /**
     * Returns all transactions of a vendor.
     * 
     * Use this operation to get the list of all transactions.
     * 
     * @param context
     *            determines the vendor on whose behalf the call is performed
     * @param filter
     *            reserved for the future use, must be omitted / set to NULL
     * @return list of transactions (of all products/licensees) or null/empty list if nothing found.
     * @throws BaseCheckedException
     *             any subclass of {@linkplain BaseCheckedException}. These exceptions will be transformed to the
     *             corresponding service response messages.
     */
    Page<Transaction> list(Context context, String filter) throws BaseCheckedException;

    /**
     * Updates transaction properties.
     * 
     * This routine is for internal use by NetLicensing. Where appropriate, transactions will be modified by
     * NetLicensing automatically.
     * 
     * @param context
     *            determines the vendor on whose behalf the call is performed
     * @param number
     *            transaction number
     * @param updateTransaction
     *            non-null properties will be updated to the provided values, null properties will stay unchanged.
     * @return updated transaction.
     * @throws BaseCheckedException
     *             any subclass of {@linkplain BaseCheckedException}. These exceptions will be transformed to the
     *             corresponding service response messages.
     */
    Transaction update(Context context, String number, Transaction updateTransaction)
            throws BaseCheckedException;

    /**
     * Sends the confirmation email for the order associated with the passed transaction
     * 
     * @param context
     *            determines the vendor on whose behalf the call is performed
     * @param number
     *            number of transaction for which the order confirmation email to be sent
     * @throws BaseCheckedException
     *             any subclass of {@linkplain BaseCheckedException}. These exceptions will be transformed to the
     *             corresponding service response messages.
     */
    void sendOrderConfirmation(Context context, String number) throws BaseCheckedException;
}
