package mk.ukim.finki.emt.service.impl;

import mk.ukim.finki.emt.model.jpa.StorePicture;
import mk.ukim.finki.emt.service.StorePictureHelper;
import org.springframework.stereotype.Service;

@Service
public class StorePictureHelperImpl implements StorePictureHelper {
    @Override
    public StorePicture createStorePicture() {
        return new StorePicture();
    }
}
