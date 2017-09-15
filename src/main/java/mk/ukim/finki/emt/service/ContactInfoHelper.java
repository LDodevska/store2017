package mk.ukim.finki.emt.service;

import mk.ukim.finki.emt.model.jpa.ContactInfo;

import java.util.List;

/**
 * Created by Viktor on 10-Apr-17.
 */
public interface ContactInfoHelper {
    ContactInfo createContactInfo(String firstName, String lastName, String phone);
    ContactInfo updateContactInfo(Long id, String firstName, String lastName, String phone);
    public List<ContactInfo> getAll();
}
