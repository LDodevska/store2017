package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.jpa.Checkout;
import mk.ukim.finki.emt.model.jpa.DeliveryPackage;
import mk.ukim.finki.emt.model.jpa.Invoice;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by user on 14.3.2017.
 */
public interface InvoiceHelper {
    Invoice createInvoice(Checkout checkout,
                          Double grossPrice,
                          Double taxAmount,
                          LocalDateTime expiryDate);

    void markInvoiceAsExpired(Long invoiceId);

    DeliveryPackage markInvoiceAsPayed(Long invoiceId);

    List<Invoice> getAll();

    void markInvoiceAsIssued(Long id);
}
