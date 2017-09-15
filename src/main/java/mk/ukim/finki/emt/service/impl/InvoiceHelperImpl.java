package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.enums.DeliveryStatus;
import mk.ukim.finki.emt.model.enums.InvoiceStatus;
import mk.ukim.finki.emt.model.jpa.Checkout;
import mk.ukim.finki.emt.model.jpa.DeliveryPackage;
import mk.ukim.finki.emt.model.jpa.Invoice;
import mk.ukim.finki.emt.persistence.DeliveryPackageRepository;
import mk.ukim.finki.emt.persistence.InvoiceRepository;
import mk.ukim.finki.emt.service.InvoiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by user on 14.3.2017.
 */
@Service
public class InvoiceHelperImpl implements InvoiceHelper {
    private final InvoiceRepository repository;
    private final DeliveryPackageRepository deliveryPackageRepository;

    @Autowired
    public InvoiceHelperImpl(InvoiceRepository repository, DeliveryPackageRepository deliveryPackageRepository)
    {
        this.repository = repository;
        this.deliveryPackageRepository = deliveryPackageRepository;
    }

    @Override
    public Invoice createInvoice(Checkout checkout, Double grossPrice, Double taxAmount, LocalDateTime expiryDate) {
        Invoice invoice = new Invoice();
        invoice.checkout = checkout;
        invoice.grossPrice = grossPrice;
        invoice.taxAmount = taxAmount;
        invoice.expiryDate = expiryDate;
        invoice.status = InvoiceStatus.ISSUED;
        return repository.save(invoice);
    }

    @Override
    public void markInvoiceAsExpired(Long invoiceId) {
        Invoice invoice = repository.findOne(invoiceId);
        invoice.status = InvoiceStatus.EXPIRED;
        repository.save(invoice);
    }

    @Override
    public DeliveryPackage markInvoiceAsPayed(Long invoiceId) {
        Invoice invoice = repository.findOne(invoiceId);
        invoice.status = InvoiceStatus.PAYED;
        invoice = repository.save(invoice);

        DeliveryPackage deliveryPackage = new DeliveryPackage();
        deliveryPackage.checkout = invoice.checkout;
        deliveryPackage.status = DeliveryStatus.PENDING_PACKAGE_CREATION;
        return deliveryPackageRepository.save(deliveryPackage);
    }



    @Override
    public List<Invoice> getAll() {
        return (List<Invoice>) repository.findAll();
    }

    @Override
    public void markInvoiceAsIssued(Long id) {
        Invoice invoice = repository.findOne(id);
        if(invoice.status == InvoiceStatus.PAYED)
        {
            DeliveryPackage deliveryPackage = deliveryPackageRepository.findByCheckoutId(invoice.checkout.id);
            deliveryPackageRepository.delete(deliveryPackage.id);
        }
        invoice.status = InvoiceStatus.ISSUED;
        repository.save(invoice);
    }


}
