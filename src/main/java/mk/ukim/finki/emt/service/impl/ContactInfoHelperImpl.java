package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.jpa.ContactInfo;
import mk.ukim.finki.emt.persistence.ContactInfoRepository;
import mk.ukim.finki.emt.service.ContactInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Viktor on 10-Apr-17.
 */
@Service
public class ContactInfoHelperImpl implements ContactInfoHelper {
    private final ContactInfoRepository contactInfoRepository;

    @Autowired
    public ContactInfoHelperImpl(ContactInfoRepository contactInfoRepository)
    {
        this.contactInfoRepository= contactInfoRepository;
    }

    @Override
    public ContactInfo createContactInfo(String firstName, String lastName, String phone) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.firstName = firstName;
        contactInfo.lastName = lastName;
        contactInfo.phone = phone;
        return contactInfoRepository.save(contactInfo);
    }

    @Override
    public ContactInfo updateContactInfo(Long id, String firstName, String lastName, String phone) {
        ContactInfo contactInfo = contactInfoRepository.findOne(id);
        contactInfo.firstName = firstName;
        contactInfo.lastName = lastName;
        contactInfo.phone = phone;
        return contactInfoRepository.save(contactInfo);
    }


    @Override
    public List<ContactInfo> getAll() {
        return (List<ContactInfo>) contactInfoRepository.findAll();
    }
}
